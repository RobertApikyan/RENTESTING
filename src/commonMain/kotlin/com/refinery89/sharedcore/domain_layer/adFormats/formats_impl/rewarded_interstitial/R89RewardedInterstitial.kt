package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial

import com.refinery89.sharedcore.data_layer.repositories_impl.FormatConfigValidator
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.unit_config.AdUnitConfigScheme
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.R89AdObject
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError
import com.refinery89.sharedcore.domain_layer.frequency_cap.IFrequencyCap
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.unit_config.AdUnitConfigData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.SerializationLibrary

internal class R89RewardedInterstitial(
	private val osAdObject: IR89RewardedInterstitialOS,
	private val frequencyCap: IFrequencyCap,
) : R89AdObject()
{
	private var loading: Boolean = false
	private var finishLoadingCycle: Boolean = false
	
	private var internalEventListener: RewardedInterstitialEventListenerInternal = createInternalEventListener()
	
	internal var publisherEventListener: RewardedInterstitialEventListenerInternal? = null
	internal lateinit var afterDismissEventListener: (() -> Unit)
	internal lateinit var rewardReceivedEventListener: ((RewardItem) -> Unit)
	
	override fun cacheConfigurationValues(adConfigObject: AdUnitConfigData)
	{
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
	
	override fun invalidate()
	{
		//todo invalidate when activity is dead
		isValid = false
	}
	
	override fun destroy()
	{
		osAdObject.destroy()
	}
	
	private fun createInternalEventListener(): RewardedInterstitialEventListenerInternal
	{
		return object : RewardedInterstitialEventListenerInternal
		{
			override fun onRewardEarned(rewardAmount: Int, rewardType: String)
			{
				R89LogUtil.d(TAG, "onRewardEarned: $rewardAmount $rewardType")
				rewardReceivedEventListener.invoke(RewardItem(rewardAmount, rewardType))
				publisherEventListener?.onRewardEarned(rewardAmount, rewardType)
			}
			
			override fun onAdDismissedFullScreenContent()
			{
				R89LogUtil.d(TAG, "onAdDismissedFullScreenContent")
				afterDismissEventListener.invoke()
				publisherEventListener?.onAdDismissedFullScreenContent()
			}
			
			override fun onAdFailedToShowFullScreen(errorMsg: String)
			{
				R89LogUtil.d(TAG, "onAdFailedToShowFullScreenContent error: $errorMsg")
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
				R89LogUtil.e(TAG, "onAdFailedToLoad with error: $error", false)
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
				R89LogUtil.d(TAG, "onAdShowedFullScreenContent")
				publisherEventListener?.onOpen()
			}
			
		}
	}
	
	companion object : FormatConfigValidator
	{
		private const val TAG = "Rewarded"
		
		override fun isInValidConfig(adUnitConfigData: AdUnitConfigScheme, serializationLibrary: SerializationLibrary): Boolean
		{
			return adUnitConfigData.unitId.isNullOrBlank()
		}
		
		override fun getErrorMessage(adUnitConfigData: AdUnitConfigScheme, serializationLibrary: SerializationLibrary): String
		{
			return "Rewarded is invalid because: " +
					"unitId: ${adUnitConfigData.unitId} is invalid?: ${adUnitConfigData.unitId.isNullOrBlank()}"
		}
	}
}