package com.refinery89.sharedcore.os_layer.formats_impl.banner

import cocoapods.Google_Mobile_Ads_SDK.GADAdSizeFromCGSize
import cocoapods.Google_Mobile_Ads_SDK.GADBannerView
import cocoapods.Google_Mobile_Ads_SDK.GADBannerViewDelegateProtocol
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import cocoapods.PrebidMobile.AdViewUtils
import cocoapods.PrebidMobile.BannerAdUnit
import cocoapods.PrebidMobile.BannerParameters
import cocoapods.PrebidMobile.PBApi
import cocoapods.PrebidMobile.ResultCode
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.IR89BannerOS
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
internal class R89BannerIosImpl : IR89BannerOS
{
	private var prebidBannerAdUnit: BannerAdUnit? = null
	private var gadBannerView: GADBannerView = GADBannerView()
	private val gamRequest: GADRequest = GADRequest()
	
	private lateinit var internalEventListener: BannerEventListenerInternal
	private lateinit var sizeList: List<R89AdSize>
	
	override fun configureAdObject(internalEventListener: BannerEventListenerInternal, gamUnitId: String, r89SizeList: List<R89AdSize>, pbConfigId: String, autoRefreshSeconds: Int)
	{
		
		this.internalEventListener = internalEventListener
		this.sizeList = r89SizeList
		val firstCGSize = sizeList.first().toCGSizeCValue()
		gadBannerView.adUnitID = gamUnitId
		gadBannerView.setAdSize(GADAdSizeFromCGSize(firstCGSize))
		gadBannerView.delegate = GADBannerViewDelegate(internalEventListener, sizeList)
		gadBannerView.accessibilityLabel = NSUUID().UUIDString
		
		//configure Prebid Ad Object
		prebidBannerAdUnit = BannerAdUnit(pbConfigId, firstCGSize)
		prebidBannerAdUnit!!.addAdditionalSizeWithSizes(sizeList.drop(1))
		prebidBannerAdUnit!!.setAutoRefreshMillisWithTime(autoRefreshSeconds.toDouble())
		prebidBannerAdUnit!!.setParameters(BannerParameters().apply {
			setApi(listOf(PBApi.MRAID_3(), PBApi.OMID_1()))
		})
	}
	
	override fun loadAd(tag: String)
	{
		prebidBannerAdUnit!!.fetchDemandWithAdObject(gamRequest) { result: ResultCode ->
			val message = "ResultCode: $result"
			R89LogUtil.logFetchDemandResult(tag, result.toString(), message)
			gadBannerView.loadRequest(gamRequest)
		}
	}
	
	override fun getView(): Any
	{
		return gadBannerView
	}
	
	override fun getViewId(): String
	{
		return gadBannerView.accessibilityLabel ?: ""
	}
	
	override fun stopAutoRefresh()
	{
		prebidBannerAdUnit?.stopAutoRefresh()
	}
	
	override fun destroy()
	{
		prebidBannerAdUnit?.stopAutoRefresh()
		prebidBannerAdUnit = null
	}
	
	override fun invalidate()
	{
		prebidBannerAdUnit?.stopAutoRefresh()
		gadBannerView.setNeedsDisplay()
	}
}

@OptIn(ExperimentalForeignApi::class)
private class GADBannerViewDelegate(
	private val internalEventListener: BannerEventListenerInternal,
	private val sizeList: List<R89AdSize>
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
			internalEventListener.onLayoutChange(size.width, size.height)
		}, failure = {
			val largestWidth = sizeList.maxBy { it.width }.width
			val largestHeight = sizeList.maxBy { it.height }.height
			internalEventListener.onLayoutChange(largestWidth, largestHeight)
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