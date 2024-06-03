package com.refinery89.sharedcore.os_layer.formats_impl.rewarded_interstitial

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial.IR89RewardedInterstitialOS
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial.RewardedInterstitialEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError


internal class R89RewardedInterstitialAndroidImpl(private val activity: Activity) : IR89RewardedInterstitialOS
{
	private var gamRewardedAdUnitView: RewardedAd? = null
	private val gamRequest: AdManagerAdRequest = AdManagerAdRequest.Builder().build()
	
	private lateinit var rewardedEventListener: RewardedInterstitialEventListenerInternal
	override fun configureAdObject(tag: String, gamUnitId: String, pbConfigId: String, internalEventListener: RewardedInterstitialEventListenerInternal)
	{
		this.rewardedEventListener = internalEventListener
		
		RewardedAd.load(activity, gamUnitId, gamRequest, createAdListener())
	}
	
	private fun createAdListener(): RewardedAdLoadCallback
	{
		return object : RewardedAdLoadCallback()
		{
			override fun onAdFailedToLoad(adError: LoadAdError)
			{
				super.onAdFailedToLoad(adError)
				val errorString = adError.toString()
				gamRewardedAdUnitView = null
				rewardedEventListener.onFailedToLoad(R89LoadError(errorString))
			}
			
			override fun onAdLoaded(ad: RewardedAd)
			{
				super.onAdLoaded(ad)
				
				gamRewardedAdUnitView = ad
				gamRewardedAdUnitView!!.fullScreenContentCallback = createFullScreenListener()
				
				rewardedEventListener.onLoaded()
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
				rewardedEventListener.onImpression()
			}
			
			override fun onAdClicked()
			{
				super.onAdClicked()
				rewardedEventListener.onClick()
			}
			
			override fun onAdDismissedFullScreenContent()
			{
				super.onAdDismissedFullScreenContent()
				rewardedEventListener.onAdDismissedFullScreenContent()
			}
			
			override fun onAdFailedToShowFullScreenContent(adError: AdError)
			{
				super.onAdFailedToShowFullScreenContent(adError)
				rewardedEventListener.onAdFailedToShowFullScreen(adError.toString())
			}
			
			override fun onAdShowedFullScreenContent()
			{
				super.onAdShowedFullScreenContent()
				rewardedEventListener.onOpen()
			}
		}
	}
	
	override fun show(onError: (error: String) -> Unit)
	{
		gamRewardedAdUnitView?.show(activity) { rewardItem ->
			// Handle the reward.
			val rewardAmount = rewardItem.amount
			val rewardType = rewardItem.type
			rewardedEventListener.onRewardEarned(rewardAmount, rewardType)
			
		} ?: run {
			onError.invoke("Interstitial adView is null")
		}
	}
	
	override fun destroy()
	{
		gamRewardedAdUnitView = null
	}
}