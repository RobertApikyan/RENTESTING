package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.video_interstitial

import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.InterstitialEventListenerInternal


/**
 * This is the interface [R89VideoInterstitial] uses to communicate with the OS layer
 */
internal interface IR89VideoInterstitialOS
{
	/**
	 * This is used from [com.refinery89.androidSdk.domain_layer.adFormats.formats_impl.R89BaseAd.configureAd] for it's same purpose
	 * @param tag String this is used for log purposes since the os ad object does not log anything by himself
	 * @param gamAdUnitId gam ad unit id used to create gam ad object
	 * @param prebidConfigId prebid config id used to create prebid ad object
	 * @param internalEventListener to communicate with the domain layer that triggers the publisher events or other internal code
	 */
	fun configureAdObject(
		tag: String,
		gamAdUnitId: String,
		prebidConfigId: String,
		internalEventListener: InterstitialEventListenerInternal,
	)
	
	/**
	 * This tries to show the interstitial with the OS layer methods and if it fails it will call the [onError] callback
	 * @param onError get a detailed error of why we couldn't show the ad, also use this to keep the navigation in case of
	 * fail to show continue with the app
	 */
	fun show(onError: (error: String) -> Unit)
	
	/**
	 * This is used from [com.refinery89.androidSdk.domain_layer.adFormats.formats_impl.R89BaseAd.destroy] for it's same purpose
	 */
	fun destroy()
}