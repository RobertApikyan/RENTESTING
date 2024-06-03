package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial

import com.refinery89.sharedcore.data_layer.repositories_impl.FormatConfigValidator
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.unit_config.AdUnitConfigScheme
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.R89AdObject
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError
import com.refinery89.sharedcore.domain_layer.frequency_cap.IFrequencyCap
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.unit_config.AdUnitConfigData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.SerializationLibrary

/**
 * @property loading prevents calling show before the ad has loaded which
 *     will cause the ad to invalidate automatically look at
 *     [R89Interstitial.show] interstitial implementation
 * @property finishLoadingCycle prevents calling show before the ad has loaded which
 *     will cause the ad to invalidate automatically look at
 *     [R89Interstitial.show] interstitial implementation
 * @property afterDismissEventListener this event is used as a guaranteed
 *     callback for the publisher so he can leave lifecycle events to null
 *     [interstitialEventListener] if so he desires. And its use is to let
 *     the publisher know when he can restart normal app. It's called when:
 * - ad is dismissed/user closed the ad
 * - ad failed to show (normally view is not null but already has been
 *   shown)
 * - ad view was null (object was shown and destroyed but was called again)
 * - ad is loading so it doesn't need to be invalid and can be used once it
 *   loads
 *
 * @property publisherEventListener lifecycle events object available to the user,
 * details about each interstitial specific event in [InterstitialEventListener]
 */
internal class R89Interstitial(
	private val osAdObject: IR89InterstitialOS,
	private val frequencyCap: IFrequencyCap,
) : R89AdObject()
{
	
	private var loading = false
	private var finishLoadingCycle = false
	
	private var internalEventListener: InterstitialEventListenerInternal =
		createInternalEventListener()
	
	internal var publisherEventListener: InterstitialEventListenerInternal? = null
	internal lateinit var afterDismissEventListener: (() -> Unit)
	
	override fun cacheConfigurationValues(adConfigObject: AdUnitConfigData)
	{
		//get the common config parameters for all formats
		pbConfigId = adConfigObject.prebidConfigId
		gamUnitId = adConfigObject.gamUnitId
	}
	
	override fun configureAd(adConfigObject: AdUnitConfigData)
	{
		super.configureAd(adConfigObject)
		
		loading = true
		finishLoadingCycle = false
		
		osAdObject.configureAdObject(
			TAG,
			gamUnitId,
			pbConfigId,
			internalEventListener,
		)
		
	}
	
	override fun show(newWrapper: Any?)
	{
		if (loading && !finishLoadingCycle)
		{
			R89LogUtil.w(TAG, "Interstitial is Not Loaded")
			afterDismissEventListener.invoke()
			return
		}
		if (!isValid)
		{
			R89LogUtil.w(TAG, "Interstitial is Not Valid")
			afterDismissEventListener.invoke()
			return
		}
		osAdObject.show { error ->
			invalidate()
			R89LogUtil.w(TAG, error)
			afterDismissEventListener.invoke()
		}
	}
	
	/**
	 * Interstitial doesn't support hiding
	 *
	 * @throws UnsupportedOperationException
	 */
	override fun hide()
	{
		R89LogUtil.e(TAG, "You can't use Hide() fun with an Interstitial", false)
	}
	
	/**
	 * called after:
	 * - recorded impression, this objects can't been shown again
	 * - activity is dead, user deleted the activity
	 * - failed to load, something went wrong
	 * - failed to show, something went wrong
	 * - tried to show when view is null
	 */
	override fun invalidate()
	{
		//todo invalidate when activity is dead
		isValid = false
	}
	
	override fun destroy()
	{
		osAdObject.destroy()
	}
	
	private fun createInternalEventListener(): InterstitialEventListenerInternal
	{
		return object : InterstitialEventListenerInternal
		{
			override fun onAdDismissedFullScreenContent()
			{
				R89LogUtil.d(TAG, "onAdDismissedFullScreenContent")
				afterDismissEventListener.invoke()
			}
			
			override fun onAdFailedToShowFullScreen(errorMsg: String)
			{
				
				R89LogUtil.d(
					TAG,
					"onAdFailedToShowFullScreenContent error: $errorMsg"
				)
				invalidate()
				publisherEventListener?.onAdFailedToShowFullScreen(errorMsg)
				afterDismissEventListener.invoke()
			}
			
			override fun onLoaded()
			{
				
				R89LogUtil.d(TAG, "onAdLoaded")
				
				loading = false
				finishLoadingCycle = true
				
				if (!frequencyCap.tryIncreaseCounter())
				{
					R89LogUtil.e(TAG, "Frequency Cap reached, SHOULD NOT have loaded", false)
				}
				
				publisherEventListener?.onLoaded()
			}
			
			override fun onFailedToLoad(error: R89LoadError)
			{
				R89LogUtil.e(
					TAG,
					"onAdFailedToLoad with error: $error",
					false
				)
				invalidate()
				
				loading = false
				finishLoadingCycle = true
				publisherEventListener?.onFailedToLoad(error)
			}
			
			override fun onImpression()
			{
				hasBeenImpressed = true
				invalidate()
				
				R89LogUtil.d(TAG, "onAdImpression")
				publisherEventListener?.onImpression()
			}
			
			override fun onClick()
			{
				R89LogUtil.d(TAG, "onAdClicked")
				publisherEventListener?.onClick()
			}
			
			override fun onOpen()
			{
				R89LogUtil.d(
					TAG,
					"onAdShowedFullScreenContent"
				)
				publisherEventListener?.onOpen()
			}
		}
	}
	
	/** @suppress */
	companion object
		: FormatConfigValidator
	{
		private const val TAG = "Interstitial"
		override fun isInValidConfig(
			adUnitConfigData: AdUnitConfigScheme,
			serializationLibrary: SerializationLibrary
		): Boolean
		{
			return adUnitConfigData.unitId.isNullOrBlank() ||
					adUnitConfigData.configId.isNullOrBlank()
		}
		
		override fun getErrorMessage(
			adUnitConfigData: AdUnitConfigScheme,
			serializationLibrary: SerializationLibrary
		): String
		{
			return "Interstitial is invalid because: " +
					"unitId: ${adUnitConfigData.unitId} is invalid?: ${adUnitConfigData.unitId.isNullOrBlank()} " +
					"configId: ${adUnitConfigData.configId} is invalid?: ${adUnitConfigData.configId.isNullOrBlank()} "
		}
	}
}