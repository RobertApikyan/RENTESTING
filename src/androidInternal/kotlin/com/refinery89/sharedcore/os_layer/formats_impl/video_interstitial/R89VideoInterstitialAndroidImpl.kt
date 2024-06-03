package com.refinery89.sharedcore.os_layer.formats_impl.video_interstitial

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.InterstitialEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.video_interstitial.IR89VideoInterstitialOS
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.os_layer.extensions.asString
import org.prebid.mobile.ResultCode
import org.prebid.mobile.Signals
import org.prebid.mobile.VideoBaseAdUnit
import org.prebid.mobile.VideoInterstitialAdUnit

internal class R89VideoInterstitialAndroidImpl(private val activity: Activity) :
	IR89VideoInterstitialOS
{
	private var prebidVideoInterstitialAdUnit: VideoInterstitialAdUnit? = null
	private var gamInterstitialAdUnitView: AdManagerInterstitialAd? = null
	private val gamRequest: AdManagerAdRequest = AdManagerAdRequest.Builder().build()
	
	private lateinit var interstitialEventListener: InterstitialEventListenerInternal
	
	override fun configureAdObject(
		tag: String,
		gamAdUnitId: String,
		prebidConfigId: String,
		internalEventListener: InterstitialEventListenerInternal,
	)
	{
		this.interstitialEventListener = internalEventListener
		
		// 1. Create VideoInterstitialAdUnit
		prebidVideoInterstitialAdUnit = VideoInterstitialAdUnit(prebidConfigId)
		
		// 2. Configure video ad unit
		prebidVideoInterstitialAdUnit?.parameters = configureVideoParameters()
		
		// 3. Make a bid request to Prebid Server
		prebidVideoInterstitialAdUnit?.fetchDemand(gamRequest) { result: ResultCode ->
			
			val message = "${result.asString()} with ${gamRequest.asString(activity)}"
			R89LogUtil.logFetchDemandResult(tag, result.toString(), message)
			
			// 4. Load a GAM ad
			AdManagerInterstitialAd.load(
				activity.application,
				gamAdUnitId,
				gamRequest,
				createAdListener()
			)
		}
	}
	
	private fun configureVideoParameters(): VideoBaseAdUnit.Parameters
	{
		return VideoBaseAdUnit.Parameters().apply {
			placement = Signals.Placement.Interstitial
			
			api = listOf(
				Signals.Api.VPAID_1,
				Signals.Api.VPAID_2
			)
			
			maxBitrate = 1500
			minBitrate = 300
			maxDuration = 30
			minDuration = 5
			mimes = listOf("video/x-flv", "video/mp4")
			playbackMethod = listOf(Signals.PlaybackMethod.AutoPlaySoundOn)
			protocols = listOf(
				Signals.Protocols.VAST_2_0
			)
		}
	}
	
	override fun show(onError: (error: String) -> Unit)
	{
		gamInterstitialAdUnitView?.show(activity) ?: run {
			onError("Interstitial adView is null")
		}
	}
	
	private fun createAdListener(): AdManagerInterstitialAdLoadCallback
	{
		return object : AdManagerInterstitialAdLoadCallback()
		{
			override fun onAdLoaded(interstitialAd: AdManagerInterstitialAd)
			{
				super.onAdLoaded(interstitialAd)
				
				// 5. Display an interstitial ad
				gamInterstitialAdUnitView = interstitialAd
				gamInterstitialAdUnitView!!.fullScreenContentCallback = createFullScreenListener()
				
				interstitialEventListener.onLoaded()
			}
			
			override fun onAdFailedToLoad(loadAdError: LoadAdError)
			{
				super.onAdFailedToLoad(loadAdError)
				val errorString = loadAdError.toString()
				interstitialEventListener.onFailedToLoad(R89LoadError(errorString))
			}
		}
	}
	
	private fun createFullScreenListener(): FullScreenContentCallback
	{
		return object : FullScreenContentCallback()
		{
			override fun onAdImpression()
			{
				super.onAdImpression()
				interstitialEventListener.onImpression()
			}
			
			override fun onAdClicked()
			{
				super.onAdClicked()
				interstitialEventListener.onClick()
			}
			
			override fun onAdDismissedFullScreenContent()
			{
				super.onAdDismissedFullScreenContent()
				interstitialEventListener.onAdDismissedFullScreenContent()
			}
			
			override fun onAdFailedToShowFullScreenContent(adError: AdError)
			{
				super.onAdFailedToShowFullScreenContent(adError)
				interstitialEventListener.onAdFailedToShowFullScreen(adError.toString())
			}
			
			override fun onAdShowedFullScreenContent()
			{
				super.onAdShowedFullScreenContent()
				interstitialEventListener.onOpen()
			}
		}
	}
	
	override fun destroy()
	{
		prebidVideoInterstitialAdUnit?.stopAutoRefresh()
		prebidVideoInterstitialAdUnit = null
		gamInterstitialAdUnitView = null
	}
}