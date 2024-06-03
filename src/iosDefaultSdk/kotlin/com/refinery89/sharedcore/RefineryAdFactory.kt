package com.refinery89.sharedcore

import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll.InfiniteScrollEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.InterstitialEventListener
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import platform.Foundation.NSLog
import platform.UIKit.UIView
import platform.UIKit.UIViewController

object RefineryAdFactory
{
	
	fun createBanner(
		configurationID: String,
		wrapper: UIView,
		lifecycleCallbacks: BannerEventListener? = null,
	): Int
	{
		return RefineryAdFactoryInternal.createBanner(configurationID, wrapper, lifecycleCallbacks)
	}
	
	fun createVideoOutstreamBanner(
		configurationID: String,
		wrapper: UIView,
		lifecycleCallbacks: BannerEventListener? = null,
	): Int = RefineryAdFactoryInternal.createVideoOutstreamBanner(configurationID, wrapper, lifecycleCallbacks)
	
	fun createInfiniteScroll(
		configurationID: String,
		scrollView: UIView,
		scrollItemAdWrapperTag: String,
		lifecycleCallbacks: InfiniteScrollEventListener? = null,
	): Int = RefineryAdFactoryInternal.createInfiniteScroll(configurationID, scrollView, scrollItemAdWrapperTag, lifecycleCallbacks)
	
	fun createManualInfiniteScroll(
		configurationID: String,
		lifecycleCallbacks: InfiniteScrollEventListener? = null,
	): Int = RefineryAdFactoryInternal.createManualInfiniteScroll(configurationID, lifecycleCallbacks)
	
	fun createInterstitial(
		configurationID: String,
		uiViewController: UIViewController,
		afterInterstitial: () -> Unit,
		lifecycleCallbacks: InterstitialEventListener? = null,
	): Int = RefineryAdFactoryInternal.createInterstitial(configurationID, uiViewController, afterInterstitial, lifecycleCallbacks)
	
	fun createVideoInterstitial(
		configurationID: String,
		uiViewController: UIViewController,
		afterInterstitial: () -> Unit,
		lifecycleCallbacks: InterstitialEventListener? = null,
	): Int = RefineryAdFactoryInternal.createVideoInterstitial(configurationID, uiViewController, afterInterstitial, lifecycleCallbacks)
	
	fun getInfiniteScrollAdForIndex(
		infiniteScrollId: Int,
		itemIndex: Int,
		itemAdWrapper: UIView,
		childAdLifecycle: BannerEventListener? = null,
	): Boolean = RefineryAdFactoryInternal.getInfiniteScrollAdForIndex(infiniteScrollId, itemIndex, itemAdWrapper, childAdLifecycle)

	fun show(index:Int) = RefineryAdFactoryInternal.show(index)
}