package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll

import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListenerInternal

/**
 * This is the interface [R89InfiniteScroll] uses to communicate with the OS layer
 */
internal interface IR89InfiniteScrollOS
{
	/**
	 * This is used by [R89InfiniteScroll] to pass information to the OS layer to show the ad
	 * @param tag that is used for logging purposes from the OS layer
	 * @param adId this is the ad object ID given by the [com.refinery89.androidSdk.domain_layer.RefineryAdFactory]
	 * @param itemWrapper this is Any type because we need to support any platform from the domain layer it's responsibility of the OS layer to know what object was passed
	 * @return Unit
	 */
	fun show(tag: String, adId: Int, itemWrapper: Any)
	
	/**
	 * This is used by [R89InfiniteScroll] to pass information to the OS layer to create the ad
	 * @param tag that is used for logging purposes from the OS layer
	 * @param adWrapper this is Any type because we need to support any platform from the domain layer it's responsibility of the OS layer to know what object was passed
	 * @param lifecycle this is the event listener that the OS layer is going to use to communicate with the domain layer
	 * @param format this is the format which is going to be created
	 * @param unitId this is the r89ConfigurationID for getting the configuration to use in the format
	 */
	fun createAdView(
		tag: String,
		adWrapper: Any,
		lifecycle: BannerEventListenerInternal,
		format: Formats,
		unitId: String
	): Int
}