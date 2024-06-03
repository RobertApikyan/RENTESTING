package com.refinery89.sharedcore

import android.app.Activity
import android.view.ViewGroup
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll.InfiniteScrollEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.InterstitialEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial.RewardItem
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial.RewardedInterstitialEventListener

object RefineryAdFactory
{
	/**
	 * Creates banner type ad inside of a [wrapper]. Banner is a fixed size static ad. First we cache the ad, load it and when it loads we display it in [wrapper]
	 *
	 * @param configurationID id used to fetch the unit configuration
	 * @param wrapper the container of the banner if it dies banner will be
	 *     invalidated and destroyed
	 * @param lifecycleCallbacks (optional) go to [BannerEventListener] to know
	 *     when the callbacks are called
	 * @return Int the id of the banner for the factory to manage it
	 */
	fun createBanner(
		configurationID: String,
		wrapper: ViewGroup,
		lifecycleCallbacks: BannerEventListener? = null,
	): Int
	{
		return RefineryAdFactoryInternal.createBanner(configurationID, wrapper, lifecycleCallbacks)
	}
	
	/**
	 * Create a video banner (OutStream) type ad inside of a [wrapper]. Video banner (OutStream) is a fixed size video ad. First we cache the ad, load it and when it loads we display it in [wrapper]
	 * cleans the wrapper from all the views that it might have and creates a video
	 * banner inside it
	 *
	 * @param configurationID id used to fetch the unit configuration
	 * @param wrapper the container of the video banner if it dies video banner will be
	 *     invalidated and destroyed
	 * @param lifecycleCallbacks (optional) go to [BannerEventListener] to know
	 *     when the callbacks are called
	 * @return Int the id of the video banner for the factory to manage it
	 */
	fun createVideoOutstreamBanner(
		configurationID: String,
		wrapper: ViewGroup,
		lifecycleCallbacks: BannerEventListener? = null,
	): Int
	{
		return RefineryAdFactoryInternal.createVideoOutstreamBanner(configurationID, wrapper, lifecycleCallbacks)
	}
	
	
	/**
	 * Adds banner and video banner type of ads to [scrollView] infinite scroll. Ads are placed inside of an infinite scrolls individual item.
	 * Number and frequency of ads shown are set by the configuration corresponding to [configurationID]
	 *
	 * @param configurationID id used to fetch the unit configuration
	 * @param scrollView View of infinite scroll
	 * @param scrollItemAdWrapperTag Tag of a wrapper inside of an infinite scroll item
	 * @param lifecycleCallbacks (optional) go to [BannerEventListener] to know
	 *     when the callbacks are called
	 * @return Int the id of the infinite scroll for the factory to manage it
	 */
	fun createInfiniteScroll(
		configurationID: String,
		scrollView: ViewGroup,
		scrollItemAdWrapperTag: String,
		lifecycleCallbacks: InfiniteScrollEventListener? = null,
	): Int
	{
		return RefineryAdFactoryInternal.createInfiniteScroll(configurationID, scrollView, scrollItemAdWrapperTag, lifecycleCallbacks)
	}
	
	
	/**
	 * Creates an infinite scroll for manual configuration. Ads are placed inside of an infinite scrolls individual item.
	 * Number and frequency of ads shown are set by the configuration corresponding to [configurationID]
	 *
	 * @param configurationID id used to fetch the unit configuration
	 * @param lifecycleCallbacks (optional) go to [BannerEventListener] to know
	 *     when the callbacks are called
	 * @return Int the id of the infinite scroll for the factory to manage it
	 */
	fun createManualInfiniteScroll(
		configurationID: String,
		lifecycleCallbacks: InfiniteScrollEventListener? = null,
	): Int
	{
		return RefineryAdFactoryInternal.createManualInfiniteScroll(configurationID, lifecycleCallbacks)
	}
	
	/**
	 * Creates Interstitial ad which will be shown in the process of transition from one activity to another. You can also configure what to do after Interstitial in [afterInterstitial]
	 *
	 * @param configurationID id used to fetch the unit configuration
	 * @param activity current activity where the interstitial must be shown,
	 *     if activity dies the interstitial will be invalidated and destroyed
	 * @param afterInterstitial this is called after ad fails to show or user
	 *     closes the fullscreen
	 * @param lifecycleCallbacks (optional) go to [InterstitialEventListener]
	 *     to know when the callbacks are called
	 * @return the id [Int] of the object for the factory to manage it
	 */
	fun createInterstitial(
		configurationID: String,
		activity: Activity,
		afterInterstitial: () -> Unit,
		lifecycleCallbacks: InterstitialEventListener? = null,
	): Int
	{
		return RefineryAdFactoryInternal.createInterstitial(
			configurationID = configurationID,
			activity = activity,
			afterInterstitial = afterInterstitial,
			lifecycleCallbacks = lifecycleCallbacks
		)
	}
	
	/**
	 * Creates Rewarded Interstitial ad which will be shown when ever you want to provide a reward in exchange for showing an ad.
	 * - You MUST configure how to resume you app on the [afterInterstitial] event. This is equivalent to
	 * [RewardedInterstitialEventListener.onAdDismissedFullScreenContent] But safer because we guarantee that with [afterInterstitial] you will be able to resume your app.
	 * - You MUST configure what to do when the user receives the reward in [rewardReceived] event. This is equivalent to
	 * [RewardedInterstitialEventListener.onRewardEarned] But we force you to implement it here in [rewardReceived] since it's an essential part of the format, you can
	 * still use the [RewardedInterstitialEventListener] to know when the events are called, debug. Just **BE CAREFUL not to give the reward twice**.
	 *
	 * @param configurationID id used to fetch the unit configuration
	 * @param activity current activity where the interstitial must be shown,
	 *     if activity dies the interstitial will be invalidated and destroyed
	 * @param afterInterstitial this is called after ad fails to show or user
	 *     closes the fullscreen
	 * @param rewardReceived this is called when the user receives the reward after seeing the ad properly.
	 * @param lifecycleCallbacks (optional) go to [RewardedInterstitialEventListener]
	 *     to know when the callbacks are called
	 * @return the id [Int] of the object for the factory to manage it
	 */
	fun createRewarded(
		configurationID: String,
		activity: Activity,
		afterInterstitial: () -> Unit,
		rewardReceived: (RewardItem) -> Unit,
		lifecycleCallbacks: RewardedInterstitialEventListener? = null,
	): Int
	{
		return RefineryAdFactoryInternal.createRewarded(
			configurationID = configurationID,
			activity = activity,
			afterInterstitial = afterInterstitial,
			rewardReceived = rewardReceived,
			lifecycleCallbacks = lifecycleCallbacks
		)
	}
	
	/**
	 * Creates Video Interstitial ad which will be shown in the process of transition from one activity to another. You can also configure what to do after Interstitial in [afterInterstitial]
	 *
	 * @param configurationID id used to fetch the unit configuration
	 * @param activity current activity where the video interstitial must be shown,
	 *     if activity dies the interstitial will be invalidated and destroyed
	 * @param afterInterstitial this is called after ad fails to show or user
	 *     closes the fullscreen
	 * @param lifecycleCallbacks (optional) go to [InterstitialEventListener]
	 *     to know when the callbacks are called
	 * @return the id [Int] of the object for the factory to manage it
	 */
	fun createVideoInterstitial(
		configurationID: String,
		activity: Activity,
		afterInterstitial: () -> Unit,
		lifecycleCallbacks: InterstitialEventListener? = null,
	): Int
	{
		return RefineryAdFactoryInternal.createVideoInterstitial(configurationID, activity, afterInterstitial, lifecycleCallbacks)
	}
	
	/**
	 * Adds R89Wrapper with ad view to publishers wrapper. If publishers wrapper already contains R89Wrapper then this function replaces it with new R89Wrapper
	 *
	 * @param index Ad index which you get after creating any format ad
	 */
	fun show(index: Int)
	{
		RefineryAdFactoryInternal.show(index)
	}
	
	/**
	 * Removes R89Wrapper with ad view from publishers wrapper.
	 *
	 * @param index Ad index which you get after creating any format ad
	 */
	fun hide(index: Int)
	{
		RefineryAdFactoryInternal.hide(index)
	}
	
	/**
	 * Removes the ad with this Index from the available objects cache and deletes it from any memory.
	 * This means you are no longer able to show or hide this ad
	 * @param index Int
	 * @return Unit
	 */
	fun destroyAd(index: Int) //todo make private
	{
		RefineryAdFactoryInternal.destroyAd(index)
	}
	
	/**
	 * Empties memory from any ad objects and returns all the wrappers to the original state
	 * @return Unit
	 */
	fun destroyAds() //todo make private
	{
		RefineryAdFactoryInternal.destroyAds()
	}
	
	/**
	 *
	 * @param infiniteScrollId id provided when [createManualInfiniteScroll] is called
	 * @param itemIndex the ordered id of your data in the list, array, etc
	 * @param itemAdWrapper the wrapper / place where the ad should be placed in you item
	 * @return if we could create an ad or not
	 */
	fun getInfiniteScrollAdForIndex(
		infiniteScrollId: Int,
		itemIndex: Int,
		itemAdWrapper: ViewGroup,
		childAdLifecycle: BannerEventListener? = null,
	): Boolean
	{
		return RefineryAdFactoryInternal.getInfiniteScrollAdForIndex(infiniteScrollId, itemIndex, itemAdWrapper, childAdLifecycle)
	}
	
	/**
	 * Hides the ad for an item if existing
	 * @param infiniteScrollId Int
	 * @param itemIndex Int
	 */
	fun hideInfiniteScrollAdForIndex(infiniteScrollId: Int, itemIndex: Int)
	{
		RefineryAdFactoryInternal.hideInfiniteScrollAdForIndex(infiniteScrollId, itemIndex)
	}
}