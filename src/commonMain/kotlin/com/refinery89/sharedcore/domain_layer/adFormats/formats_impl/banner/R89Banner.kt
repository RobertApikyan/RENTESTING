package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner

import com.refinery89.sharedcore.data_layer.repositories_impl.FormatConfigValidator
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.unit_config.AdUnitConfigScheme
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.R89AdObject
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89AdSize
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError
import com.refinery89.sharedcore.domain_layer.frequency_cap.IFrequencyCap
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.unit_config.AdUnitConfigData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.SerializationLibrary
import com.refinery89.sharedcore.domain_layer.wrapper_features.IR89Wrapper

/**
 * Banner Format is used to display an ad of flexible sizes, they are
 * SUPPOSE to be always visible no matter the scrolling, but this is only a
 * recommendation
 *
 * @property osAdObject this is in charge of interacting with the OS specific implementation of the ad libraries
 * @property r89Wrapper where the banner view is going to be place. If this view dies the banner calls [invalidate].
 *   This is also the way to interact with the specifics of the OS for changing the actual Look and Feel of the placement.
 *   Placement means the white space in the app where the app is going to place an ad.
 * @property frequencyCap this object controls how the ad is restricted in impressions and refreshes
 * @property internalEventListener this is the event that MUST BE provided to the [osAdObject] to receive the events from the os layer,
 * @property sizeList list of sizes that the banner can be
 * @property autoRefreshMillis refresh time in milliseconds
 * @property closeButtonImageURL url of the image to be used as the close button in case the [closeButtonIsActive] is true
 * @property closeButtonIsActive if true the close button is going to be shown
 * @property isLabelEnabled if true the label is going to be shown, the label is a text that says "Ad from..." in the top center of the ad
 * @property publisherEventListener lifecycle events object that the user can set through the factory
 */
internal class R89Banner(
	private val osAdObject: IR89BannerOS,
	private val r89Wrapper: IR89Wrapper,
	private val frequencyCap: IFrequencyCap,
	private val serializationLibrary: SerializationLibrary,
) : R89AdObject()
{
	
	private val internalEventListener: BannerEventListenerInternal = createInternalEventListener()
	private var sizeList: List<R89AdSize> = listOf()
	private var autoRefreshMillis: Int = -1
	private val autoRefreshSeconds: Int
		get() = autoRefreshMillis / 1000
	
	private var closeButtonImageURL = ""
	private var closeButtonIsActive: Boolean = false
	private var isLabelEnabled: Boolean = false
	
	internal var publisherEventListener: BannerEventListenerInternal? = null
	
	private var lastWidth = -1
	private var lastHeight = -1
	
	override fun cacheConfigurationValues(adConfigObject: AdUnitConfigData)
	{
		// get the common config parameters for all formats
		gamUnitId = adConfigObject.gamUnitId
		pbConfigId = adConfigObject.prebidConfigId
		autoRefreshMillis = adConfigObject.autoRefreshMillis
		
		val tempSizeList: Any = adConfigObject.getData("sizes")
		
		sizeList = serializationLibrary.getR89AdSizeList(tempSizeList)
		
		closeButtonIsActive = try
		{
			adConfigObject.getData("closeButtonIsActive")
		} catch (e: Exception)
		{
			R89LogUtil.e(TAG, e)
			false
		}
		if (closeButtonIsActive)
		{
			closeButtonImageURL = try
			{
				adConfigObject.getData("closeButtonImageURL")
			} catch (e: Exception)
			{
				R89LogUtil.e(TAG, e)
				""
			}
		}
		
		isLabelEnabled = try
		{
			adConfigObject.getData("isLabelEnabled")
		} catch (e: Exception)
		{
			R89LogUtil.e(TAG, e)
			false
		}
	}
	
	override fun configureAd(adConfigObject: AdUnitConfigData)
	{
		super.configureAd(adConfigObject)
		
		//Fetch stuff that we need to configure everything
		val largestHeight = sizeList.maxBy { it.height }.height
		val largestWidth = sizeList.maxBy { it.width }.width
		
		osAdObject.configureAdObject(
			internalEventListener,
			gamUnitId,
			sizeList,
			pbConfigId,
			autoRefreshSeconds,
		)
		
		//Configure our wrapper
		r89Wrapper.addAdView(osAdObject.getView())
		
		if (closeButtonIsActive)
		{
			r89Wrapper.addAdCloseButton(osAdObject.getViewId(), largestWidth, closeButtonImageURL) {
				invalidate()
				destroy()
			}
		}
		
		if (isLabelEnabled)
		{
			r89Wrapper.addLabel()
		}
		
		r89Wrapper.reserveSpace(largestHeight, largestWidth)
		
		//Call the load to execute the auction, after auction call for google request
		//we pass the request to prebid and fetch it
		osAdObject.loadAd(TAG)
	}
	
	
	override fun show(newWrapper: Any?)
	{
		if (!isValid) return
		r89Wrapper.show(newWrapper)
	}
	
	override fun hide()
	{
		r89Wrapper.hide()
		internalEventListener.onLayoutChange(0, 0)
	}
	
	/**
	 * called when:
	 * - failed to load happened
	 * - the wrapper dies (e.g. when the activity is destroyed)
	 */
	override fun invalidate()
	{
		hide()
		//TODO live data with the wrapper, if the wrapper dies invalidate this ad
		isValid = false
		osAdObject.invalidate()
	}
	
	override fun destroy()
	{
		r89Wrapper.destroy()
		osAdObject.destroy()
	}
	
	private fun createInternalEventListener(): BannerEventListenerInternal
	{
		return object : BannerEventListenerInternal
		{
			override fun onLoaded()
			{
				r89Wrapper.wrapContent()
				
				if (!frequencyCap.tryIncreaseCounter())
				{
					R89LogUtil.e(TAG, "Frequency Cap reached, SHOULD NOT have loaded", false)
				}
				
				if (!frequencyCap.nextIncreaseCounterIsGoingToSucceed(autoRefreshMillis.toLong()))
				{
					osAdObject.stopAutoRefresh()
					//TODO: make an event for when the frequency cap is reached and also one for when the frequency cap is lifted so we can show ads again
					R89LogUtil.i(TAG, "Frequency cap reached, canceling refresh timer", false)
				}
				
				if (closeButtonIsActive)
				{
					r89Wrapper.showAdCloseButton()
				}
				
				if (isLabelEnabled)
				{
					r89Wrapper.showLabel()
				}
				publisherEventListener?.onLoaded()
				R89LogUtil.d(TAG, "Ad Loaded")
			}
			
			override fun onLayoutChange(width: Int, height: Int)
			{
				if (width == lastWidth && height == lastHeight)
				{
					return
				}
				
				lastWidth = width
				lastHeight = height
				R89LogUtil.d(TAG, "Layout changed to: $width x $height")
				publisherEventListener?.onLayoutChange(width, height)
			}
			
			override fun onFailedToLoad(error: R89LoadError)
			{
				r89Wrapper.wrapContent()
				invalidate()
				publisherEventListener?.onFailedToLoad(error)
				R89LogUtil.e(TAG, "Ad Failed to Load with error: $error", false)
			}
			
			override fun onImpression()
			{
				publisherEventListener?.onImpression()
				R89LogUtil.d(TAG, "Ad Impression")
			}
			
			override fun onClick()
			{
				publisherEventListener?.onClick()
				R89LogUtil.d(TAG, "Ad Clicked")
			}
			
			override fun onClose()
			{
				publisherEventListener?.onClose()
				invalidate()
				R89LogUtil.d(TAG, "Ad Closed")
			}
			
			override fun onOpen()
			{
				publisherEventListener?.onOpen()
				R89LogUtil.d(TAG, "Ad Opened")
			}
		}
	}
	
	/** @suppress */
	companion object
		: FormatConfigValidator
	{
		private const val TAG = "Banner"
		override fun isInValidConfig(
			adUnitConfigData: AdUnitConfigScheme,
			serializationLibrary: SerializationLibrary,
		): Boolean
		{
			val sizesData: Any = adUnitConfigData.getData<Any>("sizes").getOrElse {
				R89LogUtil.e(TAG, it)
				//It's invalid to not be able to get the sizes
				return@isInValidConfig true
			}
			
			if (adUnitConfigData.getData<Boolean>("closeButtonIsActive").getOrNull() == true)
			{
				adUnitConfigData.getData<Int>("closeButtonImageURL").getOrElse {
					R89LogUtil.e(TAG, it)
					return@isInValidConfig true
				}
			}
			
			val canNotConvertToSizes = serializationLibrary.getR89AdSizeList(sizesData).isEmpty()
			
			return adUnitConfigData.unitId.isNullOrBlank() ||
					adUnitConfigData.configId.isNullOrBlank() ||
					canNotConvertToSizes
		}
		
		override fun getErrorMessage(
			adUnitConfigData: AdUnitConfigScheme,
			serializationLibrary: SerializationLibrary,
		): String
		{
			var sizesDataIsInvalid = false
			var closeButtonImageURLIsInvalid = false
			
			val sizesData: Any = adUnitConfigData.getData<Any>("sizes").getOrElse {
				R89LogUtil.e(TAG, it)
				sizesDataIsInvalid = true
			}
			if (adUnitConfigData.getData<Boolean>("closeButtonIsActive").getOrNull() == true)
			{
				adUnitConfigData.getData<Int>("closeButtonImageURL").getOrElse {
					closeButtonImageURLIsInvalid = true
				}
			}
			
			val conversionToSizesFailed = serializationLibrary.getR89AdSizeList(sizesData).isEmpty()
			
			return "Banner is invalid because: " +
					"unitId: ${adUnitConfigData.unitId} is invalid?: ${adUnitConfigData.unitId.isNullOrBlank()} " +
					"configId: ${adUnitConfigData.configId} is invalid?: ${adUnitConfigData.configId.isNullOrBlank()} " +
					"sizes: ${adUnitConfigData.dataMapValue?.get("sizes")} data is invalid?: $sizesDataIsInvalid" +
					"sizes conversion is invalid: $conversionToSizesFailed" +
					"Close Button Image URL: ${adUnitConfigData.dataMapValue?.get("closeButtonImageURL")} is invalid?: $closeButtonImageURLIsInvalid"
		}
	}
}
