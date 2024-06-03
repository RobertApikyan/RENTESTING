package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner_outstream

import com.refinery89.sharedcore.data_layer.repositories_impl.FormatConfigValidator
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.unit_config.AdUnitConfigScheme
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.R89AdObject
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError
import com.refinery89.sharedcore.domain_layer.frequency_cap.IFrequencyCap
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.unit_config.AdUnitConfigData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.SerializationLibrary
import com.refinery89.sharedcore.domain_layer.wrapper_features.IR89Wrapper


internal class R89BannerVideoOutstream(
	private val osAdObject: IR89OutstreamOS,
	private val r89Wrapper: IR89Wrapper,
	private val frequencyCap: IFrequencyCap,
) : R89AdObject()
{
	private val internalEventListener: BannerEventListenerInternal = createInternalEventListener()
	
	private var width: Int = 0
	private var height: Int = 0
	
	private var closeButtonIsActive: Boolean = false
	private var isLabelEnabled: Boolean = false
	private var closeButtonImageURL = ""
	
	//TODO change listener to a video appropriate one
	internal var publisherEventListener: BannerEventListenerInternal? = null
	
	private var lastWidth = -1
	private var lastHeight = -1
	
	override fun cacheConfigurationValues(adConfigObject: AdUnitConfigData)
	{
		//get the common config parameters for all formats
		pbConfigId = adConfigObject.prebidConfigId
		gamUnitId = adConfigObject.gamUnitId
		
		//get specific data from config object
		width = adConfigObject.getData("width")
		height = adConfigObject.getData("height")
		
		closeButtonIsActive = try
		{
			adConfigObject.getData("closeButtonIsActive")
		} catch (e: Exception)
		{
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
		
		osAdObject.configureAdObject(
			internalEventListener,
			gamUnitId,
			width,
			height,
			pbConfigId,
		)
		
		// Configure Our wrapper
		r89Wrapper.addAdView(osAdObject.getView())
		
		if (closeButtonIsActive)
		{
			r89Wrapper.addAdCloseButton(osAdObject.getViewId(), width, closeButtonImageURL) {
				invalidate()
				destroy()
			}
		}
		
		if (isLabelEnabled)
		{
			r89Wrapper.addLabel()
		}
		
		r89Wrapper.reserveSpace(height, width)
		
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
	
	override fun invalidate()
	{
		hide()
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
		private const val TAG = "Outstream"
		override fun isInValidConfig(
			adUnitConfigData: AdUnitConfigScheme,
			serializationLibrary: SerializationLibrary
		): Boolean
		{
			adUnitConfigData.getData<Int>("width").getOrElse {
				R89LogUtil.e(TAG, it)
				return@isInValidConfig true
			}
			
			adUnitConfigData.getData<Int>("height").getOrElse {
				R89LogUtil.e(TAG, it)
				return@isInValidConfig true
			}
			if (adUnitConfigData.getData<Boolean>("closeButtonIsActive").getOrNull() == true)
			{
				adUnitConfigData.getData<Int>("closeButtonImageURL").getOrElse {
					R89LogUtil.e(TAG, it)
					return@isInValidConfig true
				}
			}
			
			return adUnitConfigData.unitId.isNullOrBlank() ||
					adUnitConfigData.configId.isNullOrBlank()
		}
		
		override fun getErrorMessage(
			adUnitConfigData: AdUnitConfigScheme,
			serializationLibrary: SerializationLibrary
		): String
		{
			
			//TODO Also make the sizes invalid if the sizes don't surpass some threshold Ask Alex Pombo for this thresholds to see if he knows
			//only invalid if it can't get the data
			var widthIsInvalid = false
			var heightIsInvalid = false
			var closeButtonImageURLIsInvalid = false
			
			adUnitConfigData.getData<Int>("width").getOrElse {
				widthIsInvalid = true
			}
			adUnitConfigData.getData<Int>("height").getOrElse {
				heightIsInvalid = true
			}
			adUnitConfigData.getData<Int>("closeButtonImageURL").getOrElse {
				closeButtonImageURLIsInvalid = true
			}
			
			return "Outstream is invalid because: " +
					"unitId: ${adUnitConfigData.unitId} is invalid?: ${adUnitConfigData.unitId.isNullOrBlank()} " +
					"configId: ${adUnitConfigData.configId} is invalid?: ${adUnitConfigData.configId.isNullOrBlank()} " +
					"Width: ${adUnitConfigData.dataMapValue?.get("width")} is invalid?: $widthIsInvalid" +
					"Height: ${adUnitConfigData.dataMapValue?.get("height")} conversion is invalid: $heightIsInvalid" +
					"Close Button Image URL: ${adUnitConfigData.dataMapValue?.get("closeButtonImageURL")} is invalid?: $closeButtonImageURLIsInvalid"
		}
	}
}