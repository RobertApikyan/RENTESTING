package com.refinery89.sharedcore.domain_layer.ad_factory.ad_request_system

import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll.InfiniteScrollEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.InterstitialEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial.RewardItem
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.formatsBasesEvents.BasicEventListener

/**
 * Creates and Stores ad requests that are made before the SDK is ready to resolve
 * @property adRequestList MutableList<R89AdRequest>
 * @property requestCounter Int
 */
internal class UninitializedSdkAdRequestHandler
{
	private val adRequestList: MutableList<R89AdRequest> = ArrayList()
	private var requestCounter = 0
	
	/**
	 * Empties the [adRequestList] and resolve creates the adObjects that the user expect to be usable
	 * @return Unit
	 */
	fun resolveAdRequestsAfterInitialization(resolveAdRequest: (R89AdRequest) -> Unit)
	{
		for (adRequest in adRequestList)
		{
			resolveAdRequest(adRequest)
		}
		adRequestList.clear()
	}
	
	/**
	 * Creates a request for any format
	 * @param format the format to be created
	 * @param r89ConfigId the formats configuration id to be used
	 * @param wrapper some formats require a publisher wrapper to be added
	 * @param infiniteScrollTag some formats require the tag to find the publisher wrapper in each child of the scroll
	 * @property lifeCycleListener BasicEventListener? the user can pass a listener to be added to all formats
	 * @property activity Activity? some formats require an activity to be passed
	 * @property afterInterstitial Function0<Unit>? only used to tell the user to notify when the interstitial has ended
	 * @return Unit
	 */
	fun createRequestWithFormat(
		format: Formats,
		r89ConfigId: String,
		wrapper: Any?,
		infiniteScrollTag: String?,
		lifeCycleListener: BasicEventListener?,
		activity: Any?,
		afterInterstitial: (() -> Unit)?,
		infiniteScrollLifecycleListener: InfiniteScrollEventListenerInternal?,
		rewardReceived: ((RewardItem) -> Unit)?,
	)
	{
		when (format)
		{
			Formats.BANNER                       ->
			{
				createRequest(
					Formats.BANNER,
					r89ConfigId,
					wrapper,
					null,
					lifeCycleListener as BannerEventListener?,
					null,
					null,
					null,
					null
				)
			}
			
			Formats.INTERSTITIAL                 ->
			{
				createRequest(
					Formats.INTERSTITIAL,
					r89ConfigId,
					null,
					null,
					lifeCycleListener as InterstitialEventListener?,
					activity,
					afterInterstitial,
					null,
					null
				)
				
			}
			
			Formats.VIDEO_OUTSTREAM_BANNER       ->
			{
				createRequest(
					Formats.VIDEO_OUTSTREAM_BANNER,
					r89ConfigId,
					wrapper,
					null,
					lifeCycleListener as BannerEventListener?,
					null,
					null,
					null,
					null
				)
			}
			
			Formats.VIDEO_OUTSTREAM_INTERSTITIAL ->
			{
				createRequest(
					Formats.VIDEO_OUTSTREAM_INTERSTITIAL,
					r89ConfigId,
					null,
					null,
					lifeCycleListener as InterstitialEventListener?,
					activity,
					afterInterstitial,
					null,
					null
				)
			}
			
			Formats.INFINITE_SCROLL              ->
			{
				createRequest(
					Formats.INFINITE_SCROLL,
					r89ConfigId,
					wrapper,
					infiniteScrollTag,
					null,
					null,
					null,
					infiniteScrollLifecycleListener,
					null
				)
			}
			
			Formats.REWARDED_INTERSTITIAL        ->
			{
				createRequest(
					Formats.REWARDED_INTERSTITIAL,
					r89ConfigId,
					null,
					null,
					lifeCycleListener as InterstitialEventListener?,
					activity,
					afterInterstitial,
					null,
					rewardReceived
				)
			}
		}
	}
	
	/**
	 * Adds a request to the queue with the provided information
	 * @param format the format to be created
	 * @param r89ConfigId the formats configuration id to be used
	 * @param wrapper some formats require a publisher wrapper to be added
	 * @param infiniteScrollTag some formats require the tag to find the publisher wrapper in each child of the scroll
	 * @property lifeCycleListener BasicEventListener? the user can pass a listener to be added to all formats
	 * @property activity Activity? some formats require an activity to be passed
	 * @property afterInterstitial Function0<Unit>? only used to tell the user to notify when the interstitial has ended
	 * @return Unit
	 */
	private fun createRequest(
		format: Formats,
		r89ConfigId: String,
		wrapper: Any?,
		infiniteScrollTag: String?,
		lifeCycleListener: BasicEventListener?,
		activity: Any?,
		afterInterstitial: (() -> Unit)?,
		infiniteScrollLifecycleListener: InfiniteScrollEventListenerInternal?,
		rewardReceived: ((RewardItem) -> Unit)?,
	)// TODO continue, implement from ad_factory/ad_request_system
	{
		adRequestList.add(
			R89AdRequest(
				format,
				r89ConfigId,
				requestCounter,
				wrapper,
				infiniteScrollTag,
				lifeCycleListener,
				activity,
				afterInterstitial,
				infiniteScrollLifecycleListener,
				rewardReceived
			)
		)
		requestCounter++
	}
}