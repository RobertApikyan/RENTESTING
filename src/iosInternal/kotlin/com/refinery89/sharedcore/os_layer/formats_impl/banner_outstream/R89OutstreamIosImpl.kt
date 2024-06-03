package com.refinery89.sharedcore.os_layer.formats_impl.banner_outstream

import cocoapods.Google_Mobile_Ads_SDK.GADAdSizeFromCGSize
import cocoapods.Google_Mobile_Ads_SDK.GADBannerView
import cocoapods.Google_Mobile_Ads_SDK.GADBannerViewDelegateProtocol
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import cocoapods.PrebidMobile.AdViewUtils
import cocoapods.PrebidMobile.PBApi
import cocoapods.PrebidMobile.PBPlacement
import cocoapods.PrebidMobile.PBPlaybackMethod
import cocoapods.PrebidMobile.PBProtocols
import cocoapods.PrebidMobile.ResultCode
import cocoapods.PrebidMobile.SingleContainerInt
import cocoapods.PrebidMobile.VideoAdUnit
import cocoapods.PrebidMobile.VideoParameters
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner_outstream.IR89OutstreamOS
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89AdSize
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.os_layer.extensions.toCGSizeCValue
import com.refinery89.sharedcore.os_layer.extensions.toR89AdSize
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import platform.Foundation.NSUUID
import platform.UIKit.accessibilityLabel
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
internal class R89OutstreamIosImpl : IR89OutstreamOS
{
	private var prebidOutstreamAdUnit: VideoAdUnit? = null
	private var gamOutstreamAdUnitView: GADBannerView = GADBannerView()
	private val gamRequest: GADRequest = GADRequest()
	
	internal lateinit var internalEventListener: BannerEventListenerInternal
	
	private var adSize = R89AdSize(0, 0)
	
	override fun configureAdObject(internalEventListener: BannerEventListenerInternal, gamUnitId: String, width: Int, height: Int, pbConfigId: String)
	{
		this.internalEventListener = internalEventListener
		
		this.adSize = R89AdSize(width, height)
		
		// Configure Google AdView
		gamOutstreamAdUnitView.adUnitID = gamUnitId
		gamOutstreamAdUnitView.setAdSize(GADAdSizeFromCGSize(adSize.toCGSizeCValue()))
		gamOutstreamAdUnitView.delegate = GADBannerViewDelegate(internalEventListener, adSize) { r89AdSize ->
			this.adSize = r89AdSize
		}
		gamOutstreamAdUnitView.accessibilityLabel = NSUUID().UUIDString
		
		//Configure Prebid Ad Object
		prebidOutstreamAdUnit = VideoAdUnit(pbConfigId, adSize.toCGSizeCValue())
		prebidOutstreamAdUnit!!.setParameters(createVideoParameters())
	}
	
	private fun createVideoParameters() = VideoParameters(listOf("video/x-flv", "video/mp4")).apply {
		setPlacement(PBPlacement.InArticle())
		setApi(
			listOf(
				PBApi.VPAID_1(),
				PBApi.VPAID_2()
			)
		)
		setMaxBitrate(SingleContainerInt(1500))
		setMinBitrate(SingleContainerInt(300))
		setMaxDuration(SingleContainerInt(167))
		setMinDuration(SingleContainerInt(5))
		setPlaybackMethod(listOf(PBPlaybackMethod.AutoPlaySoundOn(), PBPlaybackMethod.AutoPlaySoundOff()))
		setProtocols(listOf(PBProtocols.VAST_2_0()))
	}
	
	override fun loadAd(tag: String)
	{
		prebidOutstreamAdUnit?.fetchDemandWithAdObject(gamRequest) { result: ResultCode ->
			val message = "ResultCode: $result"
			R89LogUtil.logFetchDemandResult(tag, result.toString(), message)
			gamOutstreamAdUnitView.loadRequest(gamRequest)
		}
	}
	
	override fun getView(): Any
	{
		return gamOutstreamAdUnitView
	}
	
	override fun getViewId(): String
	{
		return gamOutstreamAdUnitView.accessibilityLabel ?: ""
	}
	
	override fun destroy()
	{
		prebidOutstreamAdUnit?.stopAutoRefresh()
		prebidOutstreamAdUnit = null
	}
	
	override fun invalidate()
	{
		prebidOutstreamAdUnit?.stopAutoRefresh()
		gamOutstreamAdUnitView.setNeedsDisplay()
	}
}

@OptIn(ExperimentalForeignApi::class)
private class GADBannerViewDelegate(
	private val internalEventListener: BannerEventListenerInternal,
	private val adSize: R89AdSize,
	private val onLayoutChange: (R89AdSize) -> Unit,
) : NSObject(), GADBannerViewDelegateProtocol
{
	
	override fun bannerViewDidDismissScreen(bannerView: GADBannerView)
	{
		internalEventListener.onClose()
	}
	
	override fun bannerView(bannerView: GADBannerView, didFailToReceiveAdWithError: NSError)
	{
		internalEventListener.onFailedToLoad(R89LoadError(didFailToReceiveAdWithError.description ?: didFailToReceiveAdWithError.localizedDescription))
	}
	
	override fun bannerViewDidReceiveAd(bannerView: GADBannerView)
	{
		AdViewUtils.findPrebidCreativeSize(bannerView, success = { cValue ->
			bannerView.setAdSize(GADAdSizeFromCGSize(cValue))
			val size = cValue.toR89AdSize()
			onLayoutChange(R89AdSize(size.width, size.height))
			internalEventListener.onLayoutChange(size.width, size.height)
		}, failure = {
			internalEventListener.onLayoutChange(adSize.width, adSize.height)
		})
	}
	
	override fun bannerViewDidRecordClick(bannerView: GADBannerView)
	{
		internalEventListener.onClick()
	}
	
	override fun bannerViewDidRecordImpression(bannerView: GADBannerView)
	{
		internalEventListener.onImpression()
	}
	
	override fun bannerViewWillPresentScreen(bannerView: GADBannerView)
	{
		internalEventListener.onOpen()
	}
}