package com.refinery89.sharedcore.domain_layer.ad_factory.ad_request_system

import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll.InfiniteScrollEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial.RewardItem
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.formatsBasesEvents.BasicEventListener

/**
 * This object is responsible for HOLDING and storing the data of any adrequest made to the factory previous initialization
 * @property type String local to the factory it tells the factory which function to call
 * @property r89ConfigId String identifier provided by the user
 * @property index Int index the object was suppose to be in the list
 * @property wrapper ViewGroup? some formats require a wrapper to be passed
 * @property infiniteScrollTag some formats require the tag to find the publisher wrapper in each child of the scroll
 * @property lifeCycleListener BasicEventListener? the user can pass a listener to be added to all formats
 * @property activity Activity? some formats require an activity to be passed
 * @property afterInterstitial Function0<Unit>? only used to tell the user to notify when the interstitial has ended
 */
internal data class R89AdRequest(
	val type: Formats,
	val r89ConfigId: String,
	val index: Int,
	val wrapper: Any?,
	val infiniteScrollTag: String?,
	val lifeCycleListener: BasicEventListener?,
	val activity: Any?,
	val afterInterstitial: (() -> Unit)?,
	val infiniteScrollLifecycleListener: InfiniteScrollEventListenerInternal?,
	val rewardReceived: ((RewardItem) -> Unit)?,
)