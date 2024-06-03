package com.refinery89.sharedcore

import android.app.Activity
import android.view.ViewGroup
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
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial.RewardedInterstitialEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial.RewardedInterstitialEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.video_interstitial.R89VideoInterstitial
import com.refinery89.sharedcore.domain_layer.ad_factory.IR89BaseAdBuilder
import com.refinery89.sharedcore.domain_layer.ad_factory.ad_request_system.R89AdRequest
import com.refinery89.sharedcore.domain_layer.initialization_state.InitializationEvents
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.os_layer.formats_impl.banner.R89BannerAndroidImpl
import com.refinery89.sharedcore.os_layer.formats_impl.banner_outstream.R89OutstreamAndroidImpl
import com.refinery89.sharedcore.os_layer.formats_impl.dynamic_infinite_scroll.R89DynamicInfiniteScrollAndroidImpl
import com.refinery89.sharedcore.os_layer.formats_impl.infinite_scroll.R89InfiniteScrollAndroidImpl
import com.refinery89.sharedcore.os_layer.formats_impl.interstitial.R89InterstitialAndroidImpl
import com.refinery89.sharedcore.os_layer.formats_impl.rewarded_interstitial.R89RewardedInterstitialAndroidImpl
import com.refinery89.sharedcore.os_layer.formats_impl.video_interstitial.R89VideoInterstitialAndroidImpl
import com.refinery89.sharedcore.os_layer.wrapper.R89WrapperAndroidImpl

internal object RefineryAdFactoryInternal : R89AdFactoryCommonApi by R89AdFactoryCommon
{
	init
	{
		//TODO(@Apikyan):TBD Move post init ad handling to shared
		//subscribes to the [R89SDK.onAfterCMPInternalEvent] so when
		// the SDK is initialized it will resolve the ad requests and empty the list.
		R89SDKCommon.subscribeToInitEvents(object : InitializationEvents()
		{
			override fun initializationFinished()
			{
				R89LogUtil.d(R89AdFactoryCommon.TAG, "Resolving ad requests after initialization")
				R89AdFactoryCommon.uninitializedSdkAdRequestHandler.resolveAdRequestsAfterInitialization(
					resolveAdRequest = { adRequest ->
						resolveRequestsAfterInitialization(adRequest)
					}
				)
			}
		})
	}
	
	fun createBanner(
		configurationID: String,
		wrapper: ViewGroup,
		lifecycleCallbacks: BannerEventListenerInternal? = null,
	): Int
	{
		val builder = IR89BaseAdBuilder.createBuilder { (_, _, frequencyCap, adUnitConfigData) ->
			val osAdObject = R89BannerAndroidImpl(wrapper.context)
			val r89Wrapper = R89WrapperAndroidImpl(wrapper, adUnitConfigData!!.wrapperStyleData)
			R89Banner(osAdObject, r89Wrapper, frequencyCap!!, R89AdFactoryCommon.serializationLibrary)
		}
		
		return R89AdFactoryCommon.createBanner(
			configurationID,
			wrapper,
			lifecycleCallbacks,
			builder
		)
	}
	
	fun createVideoOutstreamBanner(
		configurationID: String,
		wrapper: ViewGroup,
		lifecycleCallbacks: BannerEventListenerInternal? = null,
	): Int
	{
		val builder = IR89BaseAdBuilder.createBuilder { (_, _, frequencyCap, adUnitConfigData) ->
			
			val osAdObject = R89OutstreamAndroidImpl(wrapper.context)
			val r89Wrapper = R89WrapperAndroidImpl(wrapper, adUnitConfigData!!.wrapperStyleData)
			
			return@createBuilder R89BannerVideoOutstream(osAdObject, r89Wrapper, frequencyCap!!)
		}
		
		return R89AdFactoryCommon.createVideoOutstreamBanner(
			configurationID,
			wrapper,
			lifecycleCallbacks,
			builder
		)
	}
	
	fun createInfiniteScroll(
		configurationID: String,
		scrollView: ViewGroup,
		scrollItemAdWrapperTag: String,
		lifecycleCallbacks: InfiniteScrollEventListenerInternal? = null,
	): Int
	{
		val builder = IR89BaseAdBuilder.createBuilder {
			val infiniteScrollDependency = R89InfiniteScrollAndroidImpl()
			val infiniteScrollBase = R89InfiniteScroll(infiniteScrollDependency)
			val osAdObject = R89DynamicInfiniteScrollAndroidImpl(scrollView)
			R89DynamicInfiniteScroll(osAdObject, infiniteScrollBase, scrollItemAdWrapperTag)
		}
		
		return R89AdFactoryCommon.createInfiniteScroll(
			configurationID,
			scrollView,
			scrollItemAdWrapperTag,
			lifecycleCallbacks,
			builder
		)
	}
	
	fun createManualInfiniteScroll(
		configurationID: String,
		lifecycleCallbacks: InfiniteScrollEventListenerInternal? = null,
	): Int
	{
		val builder = IR89BaseAdBuilder.createBuilder {
			val osAdObject = R89InfiniteScrollAndroidImpl()
			R89InfiniteScroll(osAdObject)
		}
		
		return R89AdFactoryCommon.createManualInfiniteScroll(
			configurationID,
			lifecycleCallbacks,
			builder
		)
	}
	
	fun createInterstitial(
		configurationID: String,
		activity: Activity,
		afterInterstitial: () -> Unit,
		lifecycleCallbacks: InterstitialEventListenerInternal? = null,
	): Int
	{
		val builder = IR89BaseAdBuilder.createBuilder { (_, _, frequencyCap, _) ->
			val osAdObject = R89InterstitialAndroidImpl(activity)
			R89Interstitial(osAdObject, frequencyCap!!)
		}
		
		return R89AdFactoryCommon.createInterstitial(
			configurationID,
			activity,
			afterInterstitial,
			lifecycleCallbacks,
			builder
		)
	}
	
	fun createRewarded(
		configurationID: String,
		activity: Activity,
		afterInterstitial: () -> Unit,
		rewardReceived: (RewardItem) -> Unit,
		lifecycleCallbacks: RewardedInterstitialEventListenerInternal? = null,
	): Int
	{
		val builder = IR89BaseAdBuilder.createBuilder { (_, _, frequencyCap, _) ->
			val osAdObject = R89RewardedInterstitialAndroidImpl(activity)
			R89RewardedInterstitial(osAdObject, frequencyCap!!)
		}
		
		return R89AdFactoryCommon.createRewarded(
			configurationID,
			activity,
			afterInterstitial,
			rewardReceived,
			lifecycleCallbacks,
			builder
		)
		
	}
	
	fun createVideoInterstitial(
		configurationID: String,
		activity: Activity,
		afterInterstitial: () -> Unit,
		lifecycleCallbacks: InterstitialEventListenerInternal? = null,
	): Int
	{
		val builder = IR89BaseAdBuilder.createBuilder { (_, _, frequencyCap, _) ->
			val osAdObject = R89VideoInterstitialAndroidImpl(activity)
			R89VideoInterstitial(osAdObject, frequencyCap!!)
		}
		
		return R89AdFactoryCommon.createVideoInterstitial(
			configurationID,
			activity,
			afterInterstitial,
			lifecycleCallbacks,
			builder
		)
	}
	
	fun getInfiniteScrollAdForIndex(
		infiniteScrollId: Int,
		itemIndex: Int,
		itemAdWrapper: ViewGroup,
		childAdLifecycle: BannerEventListener? = null,
	): Boolean
	{
		return R89AdFactoryCommon.getInfiniteScrollAdForIndex(infiniteScrollId, itemIndex, itemAdWrapper, childAdLifecycle)
	}
	
	private fun resolveRequestsAfterInitialization(adRequest: R89AdRequest)
	{
		when (adRequest.type)
		{
			Formats.BANNER                       ->
			{
				createBanner(
					adRequest.r89ConfigId,
					adRequest.wrapper!! as ViewGroup,
					adRequest.lifeCycleListener as BannerEventListener?
				)
			}
			
			Formats.INTERSTITIAL                 ->
			{
				createInterstitial(
					adRequest.r89ConfigId,
					adRequest.activity!! as Activity,
					adRequest.afterInterstitial!!,
					adRequest.lifeCycleListener as InterstitialEventListener?
				)
			}
			
			Formats.VIDEO_OUTSTREAM_BANNER       ->
			{
				createVideoOutstreamBanner(
					adRequest.r89ConfigId,
					adRequest.wrapper!! as ViewGroup,
					adRequest.lifeCycleListener as BannerEventListener?
				)
				
			}
			
			Formats.VIDEO_OUTSTREAM_INTERSTITIAL ->
			{
				createVideoInterstitial(
					adRequest.r89ConfigId,
					adRequest.activity!! as Activity,
					adRequest.afterInterstitial!!,
					adRequest.lifeCycleListener as InterstitialEventListener?
				)
			}
			
			Formats.INFINITE_SCROLL              ->
			{
				val isManualInfiniteScroll: Boolean = adRequest.wrapper == null && adRequest.infiniteScrollTag == null
				if (isManualInfiniteScroll)
				{
					createManualInfiniteScroll(adRequest.r89ConfigId, adRequest.infiniteScrollLifecycleListener!!)
				} else
				{
					createInfiniteScroll(
						adRequest.r89ConfigId,
						adRequest.wrapper!! as ViewGroup,
						adRequest.infiniteScrollTag!!,
						adRequest.infiniteScrollLifecycleListener!!
					)
				}
			}
			
			Formats.REWARDED_INTERSTITIAL        ->
			{
				createRewarded(
					adRequest.r89ConfigId,
					adRequest.activity!! as Activity,
					adRequest.afterInterstitial!!,
					adRequest.rewardReceived!!,
					adRequest.lifeCycleListener as RewardedInterstitialEventListener?
				)
			}
		}
	}
	
}