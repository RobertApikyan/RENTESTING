package com.refinery89.sharedcore.os_layer.formats_impl.interstitial

import cocoapods.Google_Mobile_Ads_SDK.GADFullScreenContentDelegateProtocol
import cocoapods.Google_Mobile_Ads_SDK.GADFullScreenPresentingAdProtocol
import cocoapods.Google_Mobile_Ads_SDK.GADInterstitialAd
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import cocoapods.PrebidMobile.InterstitialAdUnit
import cocoapods.PrebidMobile.ResultCode
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.IR89InterstitialOS
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.InterstitialEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
internal class R89InterstitialIosImpl(
	private val rootViewController: UIViewController
) : IR89InterstitialOS
{
	
	private var interstitialViewController: InterstitialViewController? = null
	private var prebidInterstitialAdUnit: InterstitialAdUnit? = null
	private var gamInterstitialAdUnitView: GADInterstitialAd? = null
	private val gamRequest: GADRequest = GADRequest()
	
	private lateinit var interstitialEventListener: InterstitialEventListenerInternal
	
	override fun configureAdObject(tag: String, gamAdUnitId: String, prebidConfigId: String, internalEventListener: InterstitialEventListenerInternal)
	{
		this.interstitialEventListener = internalEventListener
		
		//configure unitObject
		prebidInterstitialAdUnit = InterstitialAdUnit(prebidConfigId)
		
		//we pass the request to prebid and fetch it
		prebidInterstitialAdUnit!!.fetchDemandWithAdObject(gamRequest) { result: ResultCode ->
			R89LogUtil.logFetchDemandResult(tag, result.toString(), "ResultCode: $result")
			GADInterstitialAd.loadWithAdUnitID(gamAdUnitId, gamRequest, ::onGADInterstitialAdLoadCompletion)
		}
	}
	
	private fun onGADInterstitialAdLoadCompletion(adManagerInterstitialAd: GADInterstitialAd?, loadAdError: NSError?)
	{
		if (adManagerInterstitialAd == null)
		{
			val errorString = loadAdError.toString()
			interstitialEventListener.onFailedToLoad(R89LoadError(errorString))
		} else
		{
			gamInterstitialAdUnitView = adManagerInterstitialAd
			interstitialViewController = InterstitialViewController(adManagerInterstitialAd, interstitialEventListener)
			gamInterstitialAdUnitView?.fullScreenContentDelegate = interstitialViewController
			interstitialEventListener.onLoaded()
		}
	}
	
	override fun show(onError: (error: String) -> Unit)
	{
		val interstitialAdUnit = gamInterstitialAdUnitView
		if (interstitialAdUnit == null)
		{
			onError.invoke("Interstitial adView is null")
			return
		}
		
		rootViewController.presentViewController(viewControllerToPresent = interstitialViewController!!, true, null)
	}
	
	override fun destroy()
	{
		prebidInterstitialAdUnit?.stopAutoRefresh()
		interstitialViewController = null
		prebidInterstitialAdUnit = null
		gamInterstitialAdUnitView = null
	}
	
	
}

@OptIn(ExperimentalForeignApi::class)
private class InterstitialViewController// Custom initializer
	(
	private val interstitialAdUnit: GADInterstitialAd,
	private val interstitialEventListener: InterstitialEventListenerInternal
) : UIViewController(nibName = null, bundle = null), GADFullScreenContentDelegateProtocol
{
	
	override fun viewDidAppear(animated: Boolean)
	{
		super.viewDidAppear(animated)
		interstitialAdUnit.presentFromRootViewController(this)
	}
	
	override fun ad(ad: GADFullScreenPresentingAdProtocol, didFailToPresentFullScreenContentWithError: NSError)
	{
		interstitialEventListener.onAdFailedToShowFullScreen(
			didFailToPresentFullScreenContentWithError.description ?: didFailToPresentFullScreenContentWithError.localizedDescription
		)
	}
	
	override fun adDidDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol)
	{
		dismissViewControllerAnimated(flag = true, null)
		interstitialEventListener.onAdDismissedFullScreenContent()
	}
	
	override fun adWillDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol)
	{
		/*No need to implement*/
	}
	
	override fun adDidRecordClick(ad: GADFullScreenPresentingAdProtocol)
	{
		interstitialEventListener.onClick()
	}
	
	override fun adDidRecordImpression(ad: GADFullScreenPresentingAdProtocol)
	{
		interstitialEventListener.onImpression()
	}
	
	override fun adWillPresentFullScreenContent(ad: GADFullScreenPresentingAdProtocol)
	{
		interstitialEventListener.onOpen()
	}
}
