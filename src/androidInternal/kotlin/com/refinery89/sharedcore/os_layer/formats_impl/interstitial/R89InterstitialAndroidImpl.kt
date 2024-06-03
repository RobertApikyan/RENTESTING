package com.refinery89.sharedcore.os_layer.formats_impl.interstitial

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.IR89InterstitialOS
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.InterstitialEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.os_layer.extensions.asString
import org.prebid.mobile.InterstitialAdUnit
import org.prebid.mobile.ResultCode

/**
 *
 * @property activity activity where the interstitial ad is shown, if the
 *     activity dies [invalidate] is called
 * @property prebidInterstitialAdUnit Prebid Object for Interstitial Ad Unit
 * @property gamInterstitialAdUnitView gam view for displaying the interstitial ad
 * @property gamRequest AdManagerAdRequest
 * @property interstitialEventListener InterstitialInternalEventListener
 */
internal class R89InterstitialAndroidImpl(private val activity: Activity) : IR89InterstitialOS
{
	
	private var prebidInterstitialAdUnit: InterstitialAdUnit? = null
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
		
		//configure unitObject
		prebidInterstitialAdUnit = InterstitialAdUnit(prebidConfigId)
		
		//we pass the request to prebid and fetch it
		prebidInterstitialAdUnit!!.fetchDemand(gamRequest) { result: ResultCode ->
			
			val message = "${result.asString()} with ${gamRequest.asString(activity)}"
			R89LogUtil.logFetchDemandResult(tag, result.toString(), message)
			
			AdManagerInterstitialAd.load(
				activity.application,
				gamAdUnitId,
				gamRequest,
				createAdListener()
			)
		}
	}
	
	override fun show(onError: (error: String) -> Unit)
	{
		gamInterstitialAdUnitView?.show(activity) ?: run {
			onError.invoke("Interstitial adView is null")
		}
	}
	
	private fun createAdListener(): AdManagerInterstitialAdLoadCallback
	{
		return object : AdManagerInterstitialAdLoadCallback()
		{
			override fun onAdLoaded(adManagerInterstitialAd: AdManagerInterstitialAd)
			{
				super.onAdLoaded(adManagerInterstitialAd)
				
				gamInterstitialAdUnitView = adManagerInterstitialAd
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
		prebidInterstitialAdUnit?.stopAutoRefresh()
		prebidInterstitialAdUnit = null
		gamInterstitialAdUnitView = null
	}
}