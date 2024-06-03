package com.refinery89.sharedcore

import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.R89Banner
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner_outstream.R89BannerVideoOutstream
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll.InfiniteScrollEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.InterstitialEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.R89Interstitial
import com.refinery89.sharedcore.domain_layer.ad_factory.IR89BaseAdBuilder
import com.refinery89.sharedcore.domain_layer.ad_factory.ad_request_system.R89AdRequest
import com.refinery89.sharedcore.domain_layer.initialization_state.InitializationEvents
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.os_layer.extensions.asyncAfter
import com.refinery89.sharedcore.os_layer.formats_impl.banner.R89BannerIosImpl
import com.refinery89.sharedcore.os_layer.formats_impl.banner_outstream.R89OutstreamIosImpl
import com.refinery89.sharedcore.os_layer.formats_impl.interstitial.R89InterstitialIosImpl
import com.refinery89.sharedcore.os_layer.third_party_library.UiExecutorIosImpl
import com.refinery89.sharedcore.os_layer.wrapper.R89WrapperIosImpl
import platform.UIKit.UIView
import platform.UIKit.UIViewController

internal object RefineryAdFactoryInternal : R89AdFactoryCommonApi by R89AdFactoryCommon
{
	
	const val PREBID_INIT_WAIT_TIMEOUT_MS = 500UL
	
	init
	{
		R89SDKCommon.subscribeToInitEvents(object : InitializationEvents()
		{
			override fun initializationFinished()
			{
				//TODO(@Apikyan): Remove the delay when 3rd party Prebid and GoogleAds initialization listeners will
				// be included to R89SDK initialization process.
				UiExecutorIosImpl.asyncAfter(PREBID_INIT_WAIT_TIMEOUT_MS) {
					R89LogUtil.d(R89AdFactoryCommon.TAG, "Resolving ad requests after initialization")
					R89AdFactoryCommon.uninitializedSdkAdRequestHandler.resolveAdRequestsAfterInitialization(
						resolveAdRequest = ::resolveRequestsAfterInitialization
					)
				}
			}
		})
	}
	
	fun createBanner(
		configurationID: String,
		wrapper: UIView,
		lifecycleCallbacks: BannerEventListener? = null,
	): Int
	{
		
		val builder = IR89BaseAdBuilder.createBuilder { (_, _, frequencyCap, adUnitConfigData) ->
			val osAdObject = R89BannerIosImpl()
			val r89Wrapper = R89WrapperIosImpl(wrapper, adUnitConfigData!!.wrapperStyleData)
			R89Banner(osAdObject, r89Wrapper, frequencyCap!!,R89AdFactoryCommon.serializationLibrary)
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
		wrapper: UIView,
		lifecycleCallbacks: BannerEventListener? = null,
	): Int
	{
		val builder = IR89BaseAdBuilder.createBuilder { (_, _, frequencyCap, adUnitConfigData) ->
			val osAdObject = R89OutstreamIosImpl()
			val r89Wrapper = R89WrapperIosImpl(wrapper, adUnitConfigData!!.wrapperStyleData)
			R89BannerVideoOutstream(osAdObject, r89Wrapper, frequencyCap!!)
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
		scrollView: UIView,
		scrollItemAdWrapperTag: String,
		lifecycleCallbacks: InfiniteScrollEventListener? = null,
	): Int = TODO()
	
	fun createManualInfiniteScroll(
		configurationID: String,
		lifecycleCallbacks: InfiniteScrollEventListener? = null,
	): Int = TODO()
	
	fun createInterstitial(
		configurationID: String,
		rootViewController: UIViewController,
		afterInterstitial: () -> Unit,
		lifecycleCallbacks: InterstitialEventListener? = null,
	): Int
	{
		val builder = IR89BaseAdBuilder.createBuilder { (_, _, frequencyCap, _) ->
			val osAdObject = R89InterstitialIosImpl(rootViewController)
			R89Interstitial(osAdObject, frequencyCap!!)
		}
		
		return R89AdFactoryCommon.createInterstitial(
			configurationID,
			rootViewController,
			afterInterstitial,
			lifecycleCallbacks,
			builder
		)
	}
	
	fun createVideoInterstitial(
		configurationID: String,
		rootViewController: UIViewController,
		afterInterstitial: () -> Unit,
		lifecycleCallbacks: InterstitialEventListener? = null,
	): Int = TODO()
	
	fun getInfiniteScrollAdForIndex(
		infiniteScrollId: Int,
		itemIndex: Int,
		itemAdWrapper: UIView,
		childAdLifecycle: BannerEventListener? = null,
	): Boolean = TODO()
	
	private fun resolveRequestsAfterInitialization(adRequest: R89AdRequest)
	{
		when (adRequest.type)
		{
			Formats.BANNER                       ->
			{
				createBanner(
					adRequest.r89ConfigId,
					adRequest.wrapper!! as UIView,
					adRequest.lifeCycleListener as BannerEventListener?
				)
			}
			
			Formats.INTERSTITIAL                 ->
			{
				createInterstitial(
					adRequest.r89ConfigId,
					adRequest.activity!! as UIViewController,
					adRequest.afterInterstitial!!,
					adRequest.lifeCycleListener as InterstitialEventListener?
				)
			}
			
			Formats.VIDEO_OUTSTREAM_BANNER       ->
			{
				createVideoOutstreamBanner(
					adRequest.r89ConfigId,
					adRequest.wrapper!! as UIView,
					adRequest.lifeCycleListener as BannerEventListener?
				)
				
			}
			
			Formats.VIDEO_OUTSTREAM_INTERSTITIAL ->
			{
			
			}
			
			Formats.INFINITE_SCROLL              ->
			{
			
			}
			
			Formats.REWARDED_INTERSTITIAL        -> {
			
			}
		}
	}
}