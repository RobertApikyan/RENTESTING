package com.refinery89.sharedcore


import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.R89Banner
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner_outstream.R89BannerVideoOutstream
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.dynamic_infinite_scroll.R89DynamicInfiniteScroll
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll.InfiniteScrollEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll.R89InfiniteScroll
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.InterstitialEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.InterstitialEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.R89Interstitial
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial.R89RewardedInterstitial
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial.RewardItem
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial.RewardedInterstitialEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.video_interstitial.R89VideoInterstitial
import com.refinery89.sharedcore.domain_layer.ad_factory.AdObjectOperator
import com.refinery89.sharedcore.domain_layer.ad_factory.IR89BaseAdBuilder
import com.refinery89.sharedcore.domain_layer.ad_factory.ad_request_system.AdRequestValidator
import com.refinery89.sharedcore.domain_layer.ad_factory.ad_request_system.UninitializedSdkAdRequestHandler
import com.refinery89.sharedcore.domain_layer.frequency_cap.FrequencyCapHandler
import com.refinery89.sharedcore.domain_layer.internalToolBox.date_time.R89DateFactory
import com.refinery89.sharedcore.domain_layer.third_party_libraries.ISimpleDataSave
import com.refinery89.sharedcore.domain_layer.third_party_libraries.SerializationLibrary

internal interface R89AdFactoryCommonApi
{
	fun show(index: Int)
	
	fun hide(index: Int)
	
	fun destroyAd(index: Int)
	
	fun destroyAds()
	
	fun hideInfiniteScrollAdForIndex(infiniteScrollId: Int, itemIndex: Int)
}


internal object R89AdFactoryCommon : R89AdFactoryCommonApi
{
	////// CONSTS //////
	
	/** @suppress */
	internal const val TAG = "Factory"
	
	////// HELPERS //////
	internal val uninitializedSdkAdRequestHandler: UninitializedSdkAdRequestHandler = UninitializedSdkAdRequestHandler()
	internal lateinit var serializationLibrary: SerializationLibrary
	private val adRequestValidator: AdRequestValidator = AdRequestValidator(TAG)
	private val adObjectOperations: AdObjectOperator = AdObjectOperator(TAG)
	private val frequencyCapHandler: FrequencyCapHandler = FrequencyCapHandler()
	
	internal fun provideContextDependantDependencies(simpleDataStore: ISimpleDataSave, dateFactory: R89DateFactory, serializationLibrary: SerializationLibrary)
	{
		frequencyCapHandler.provideDependencies(simpleDataStore, dateFactory)
		this.serializationLibrary = serializationLibrary
	}
	
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
		wrapper: Any,
		lifecycleCallbacks: BannerEventListenerInternal? = null,
		adObjectBuilder: IR89BaseAdBuilder<R89Banner>,
	): Int
	{
		
		val validationResult = adRequestValidator.adObjectCreationValidation(
			lastAdId = adObjectOperations.getLastAdId(),
			format = Formats.BANNER,
			configurationID = configurationID,
			frequencyCapHandler = frequencyCapHandler,
			uninitializedSdkAdRequestHandler = uninitializedSdkAdRequestHandler,
			wrapper = wrapper,
			lifecycleCallbacks = lifecycleCallbacks,
		)
		
		if (!validationResult.canContinue)
		{
			return validationResult.adId
		}
		
		val adObject = adObjectBuilder.build(validationResult)
		
		adObject.publisherEventListener = lifecycleCallbacks
		
		adObject.configureAd(validationResult.adUnitConfigData!!)
		
		return adObjectOperations.addAdObject(adObject)
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
		wrapper: Any,
		lifecycleCallbacks: BannerEventListenerInternal? = null,
		adObjectBuilder: IR89BaseAdBuilder<R89BannerVideoOutstream>,
	): Int
	{
		
		val validationResult = adRequestValidator.adObjectCreationValidation(
			lastAdId = adObjectOperations.getLastAdId(),
			format = Formats.VIDEO_OUTSTREAM_BANNER,
			configurationID = configurationID,
			frequencyCapHandler = frequencyCapHandler,
			uninitializedSdkAdRequestHandler = uninitializedSdkAdRequestHandler,
			wrapper = wrapper,
			lifecycleCallbacks = lifecycleCallbacks,
		)
		
		if (!validationResult.canContinue)
		{
			return validationResult.adId
		}
		
		val adObject = adObjectBuilder.build(validationResult)
		
		adObject.publisherEventListener = lifecycleCallbacks
		
		adObject.configureAd(validationResult.adUnitConfigData!!)
		
		return adObjectOperations.addAdObject(adObject)
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
		scrollView: Any,
		scrollItemAdWrapperTag: String,
		lifecycleCallbacks: InfiniteScrollEventListenerInternal? = null,
		adObjectBuilder: IR89BaseAdBuilder<R89DynamicInfiniteScroll>,
	): Int
	{
		val validationResult = adRequestValidator.adObjectCreationValidation(
			lastAdId = adObjectOperations.getLastAdId(),
			format = Formats.INFINITE_SCROLL,
			configurationID = configurationID,
			frequencyCapHandler = frequencyCapHandler,
			uninitializedSdkAdRequestHandler = uninitializedSdkAdRequestHandler,
			wrapper = scrollView,
			infiniteScrollEventListener = lifecycleCallbacks,
			infiniteScrollTag = scrollItemAdWrapperTag
		)
		
		if (!validationResult.canContinue)
		{
			return validationResult.adId
		}
		
		val adObject =
			adObjectBuilder.build(validationResult)
		
		adObject.generalEventListener = lifecycleCallbacks
		
		adObject.configureAd(validationResult.adUnitConfigData!!)
		
		return adObjectOperations.addAdObject(adObject)
	}
	
	
	/**
	 * Creates an infinite scroll for manual configuration. Ads are placed inside of an infinite scrolls individual item.
	 * Number and frequency of ads shown are set by the configuration corresponding to [configurationID]
	 *
	 * @param configurationID id used to fetch the unit configuration
	 * @param lifecycleCallbacks (optional) go to [InfiniteScrollEventListenerInternal] to know
	 *     when the callbacks are called
	 * @return Int the id of the infinite scroll for the factory to manage it
	 */
	fun createManualInfiniteScroll(
		configurationID: String,
		lifecycleCallbacks: InfiniteScrollEventListenerInternal? = null,
		adObjectBuilder: IR89BaseAdBuilder<R89InfiniteScroll>,
	): Int
	{
		val validationResult = adRequestValidator.adObjectCreationValidation(
			lastAdId = adObjectOperations.getLastAdId(),
			format = Formats.INFINITE_SCROLL,
			configurationID = configurationID,
			frequencyCapHandler = frequencyCapHandler,
			uninitializedSdkAdRequestHandler = uninitializedSdkAdRequestHandler,
			infiniteScrollEventListener = lifecycleCallbacks,
		)
		
		if (!validationResult.canContinue)
		{
			return validationResult.adId
		}
		
		val adObject = adObjectBuilder.build(validationResult)
		
		adObject.generalEventListener = lifecycleCallbacks
		
		adObject.configureAd(validationResult.adUnitConfigData!!)
		
		return adObjectOperations.addAdObject(adObject)
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
		activity: Any,
		afterInterstitial: () -> Unit,
		lifecycleCallbacks: InterstitialEventListenerInternal? = null,
		adObjectBuilder: IR89BaseAdBuilder<R89Interstitial>,
	): Int
	{
		val validationResult = adRequestValidator.adObjectCreationValidation(
			lastAdId = adObjectOperations.getLastAdId(),
			format = Formats.INTERSTITIAL,
			configurationID = configurationID,
			frequencyCapHandler = frequencyCapHandler,
			uninitializedSdkAdRequestHandler = uninitializedSdkAdRequestHandler,
			activity = activity,
			afterInterstitial = afterInterstitial,
			lifecycleCallbacks = lifecycleCallbacks
		)
		
		if (!validationResult.canContinue)
		{
			return validationResult.adId
		}
		
		val adObject = adObjectBuilder.build(validationResult)
		adObject.publisherEventListener = lifecycleCallbacks
		
		adObject.afterDismissEventListener = {
			R89SDKCommon.executeInMainThread {
				afterInterstitial()
			}
		}
		
		adObject.configureAd(validationResult.adUnitConfigData!!)
		
		return adObjectOperations.addAdObject(adObject)
	}
	
	/**
	 * Creates Rewarded Interstitial ad which will be shown when ever you want to provide a reward in exchange for showing an ad.
	 * - You MUST configure how to resume you app on the [afterInterstitial] event. This is equivalent to
	 * [RewardedInterstitialEventListenerInternal.onAdDismissedFullScreenContent] But safer because we guarantee that with [afterInterstitial] you will be able to resume your app.
	 * - You MUST configure what to do when the user receives the reward in [rewardReceived] event. This is equivalent to
	 * [RewardedInterstitialEventListenerInternal.onRewardEarned] But we force you to implement it here in [rewardReceived] since it's an essential part of the format, you can
	 * still use the [RewardedInterstitialEventListenerInternal] to know when the events are called, debug, just be careful not to give the reward twice.
	 *
	 * @param configurationID id used to fetch the unit configuration
	 * @param activity current activity where the interstitial must be shown,
	 *     if activity dies the interstitial will be invalidated and destroyed
	 * @param afterInterstitial this is called after ad fails to show or user
	 *     closes the fullscreen
	 * @param rewardReceived this is called when the user receives the reward after seeing the ad properly.
	 * @param lifecycleCallbacks (optional) go to [RewardedInterstitialEventListenerInternal]
	 *     to know when the callbacks are called
	 * @return the id [Int] of the object for the factory to manage it
	 */
	fun createRewarded(
		configurationID: String,
		activity: Any,
		afterInterstitial: () -> Unit,
		rewardReceived: (RewardItem) -> Unit,
		lifecycleCallbacks: RewardedInterstitialEventListenerInternal? = null,
		adObjectBuilder: IR89BaseAdBuilder<R89RewardedInterstitial>,
	): Int
	{
		
		val validationResult = adRequestValidator.adObjectCreationValidation(
			lastAdId = adObjectOperations.getLastAdId(),
			format = Formats.REWARDED_INTERSTITIAL,
			configurationID = configurationID,
			frequencyCapHandler = frequencyCapHandler,
			uninitializedSdkAdRequestHandler = uninitializedSdkAdRequestHandler,
			activity = activity,
			afterInterstitial = afterInterstitial,
			rewardReceived = rewardReceived,
			lifecycleCallbacks = lifecycleCallbacks
		)
		
		if (!validationResult.canContinue)
		{
			return validationResult.adId
		}
		
		val adObject = adObjectBuilder.build(validationResult)
		adObject.publisherEventListener = lifecycleCallbacks
		
		adObject.afterDismissEventListener = {
			R89SDKCommon.executeInMainThread {
				afterInterstitial()
			}
		}
		
		adObject.rewardReceivedEventListener = {
			R89SDKCommon.executeInMainThread {
				rewardReceived(it)
			}
		}
		
		adObject.configureAd(validationResult.adUnitConfigData!!)
		
		return adObjectOperations.addAdObject(adObject)
	}
	
	/**
	 * Creates Video Interstitial ad which will be shown in the process of transition from one activity to another. You can also configure what to do after Interstitial in [afterInterstitial]
	 * Use [getInfiniteScrollAdForIndex] with return id of this function to set views.
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
		activity: Any,
		afterInterstitial: () -> Unit,
		lifecycleCallbacks: InterstitialEventListenerInternal? = null,
		adObjectBuilder: IR89BaseAdBuilder<R89VideoInterstitial>,
	): Int
	{
		val validationResult = adRequestValidator.adObjectCreationValidation(
			lastAdId = adObjectOperations.getLastAdId(),
			format = Formats.VIDEO_OUTSTREAM_INTERSTITIAL,
			configurationID = configurationID,
			frequencyCapHandler = frequencyCapHandler,
			uninitializedSdkAdRequestHandler = uninitializedSdkAdRequestHandler,
			activity = activity,
			afterInterstitial = afterInterstitial,
			lifecycleCallbacks = lifecycleCallbacks
		)
		
		if (!validationResult.canContinue)
		{
			return validationResult.adId
		}
		
		//TODO add an object to control how the video player control is shown:
		// -skip delay -skip button pos -mute on start -close button pos
		val adObject = adObjectBuilder.build(validationResult)
		adObject.publisherEventListener = lifecycleCallbacks
		
		adObject.afterDismissEventListener = {
			R89SDKCommon.executeInMainThread {
				afterInterstitial()
			}
		}
		
		adObject.configureAd(validationResult.adUnitConfigData!!)
		
		return adObjectOperations.addAdObject(adObject)
	}
	
	/**
	 * Adds R89Wrapper with ad view to publishers wrapper. If publishers wrapper already contains R89Wrapper then this function replaces it with new R89Wrapper
	 *
	 * @param index Ad index which you get after creating any format ad
	 */
	override fun show(index: Int)
	{
		adObjectOperations.show(index)
	}
	
	/**
	 * Removes R89Wrapper with ad view from publishers wrapper.
	 *
	 * @param index Ad index which you get after creating any format ad
	 */
	override fun hide(index: Int)
	{
		adObjectOperations.hide(index)
	}
	
	/**
	 * Removes the ad with this Index from the available objects cache and deletes it from any memory.
	 * This means you are no longer able to show or hide this ad
	 * @param index Int
	 * @return Unit
	 */
	override fun destroyAd(index: Int) //todo make private
	{
		adObjectOperations.destroyAd(index)
	}
	
	/**
	 * Empties memory from any ad objects and returns all the wrappers to the original state
	 * @return Unit
	 */
	override fun destroyAds() //todo make private
	{
		adObjectOperations.destroyAds()
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
		itemAdWrapper: Any,
		childAdLifecycle: BannerEventListener? = null,
	): Boolean
	{
		return adObjectOperations.getInfiniteScrollAdForIndex(
			infiniteScrollId,
			itemIndex,
			itemAdWrapper,
			childAdLifecycle
		)
	}
	
	/**
	 * Hides the ad for an item if existing
	 * @param infiniteScrollId Int
	 * @param itemIndex Int
	 */
	override fun hideInfiniteScrollAdForIndex(infiniteScrollId: Int, itemIndex: Int)
	{
		adObjectOperations.hideInfiniteScrollAdForIndex(infiniteScrollId, itemIndex)
	}
	
	////////////////////////////////Internal Operations
	/**
	 * this show if passed a new publisher wrapper will swap it with the current one
	 * this is useful for when the pub can't guarantee an item or some view will be the same after something happens.
	 *
	 * An Example of this is when using RecyclerView the publisher viewHolder won't always be the same View for the same data
	 * @param index Int
	 * @param newWrapper ViewGroup?
	 * @return Unit
	 */
	internal fun show(index: Int, newWrapper: Any?)
	{
		adObjectOperations.show(index, newWrapper)
	}
	
	/**
	 * Invalidating an ads means is ready to be destroyed
	 * @param index the id / index of the ad that wants to be invalidated
	 * @return Unit
	 */
	internal fun invalidate(index: Int)
	{
		adObjectOperations.invalidate(index)
	}
	
	//TODO actually use this more information in the [adObjectOperations]
	/** Checks and destroys adObjects if they are Invalid to show. */
	internal fun cleanInvalidatedAdObjects()
	{
		adObjectOperations.cleanInvalidatedAdObjects()
	}
}