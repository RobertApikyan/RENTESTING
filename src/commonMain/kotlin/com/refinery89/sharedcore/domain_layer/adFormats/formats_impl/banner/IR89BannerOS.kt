package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner

import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89AdSize

/**
 * This is the interface [R89Banner] uses to communicate with the OS layer
 */
internal interface IR89BannerOS
{
	/**
	 * This is used from [com.refinery89.androidSdk.domain_layer.adFormats.formats_impl.R89BaseAd.configureAd] for it's same purpose
	 */
	fun configureAdObject(
		internalEventListener: BannerEventListenerInternal,
		gamUnitId: String,
		r89SizeList: List<R89AdSize>,
		pbConfigId: String,
		autoRefreshSeconds: Int,
	)
	
	/**
	 * This starts the loading of the ad, it will first run the prebid auction and then when the server has responded and modified the gamRequest.customTargeting vars
	 * we run the google auction and then something is rendered with google web view implementation inside gam ad view
	 * @param tag String this is used for log purposes since the os ad object does not log anything by himself
	 * @return Unit
	 */
	fun loadAd(tag: String)
	
	/**
	 * This will give you the object that is suppose to live inside the [com.refinery89.androidSdk.domain_layer.wrapper_features.IR89Wrapper]
	 * it's of type [Any] because we need to support any platform from the domain layer it's responsibility of the OS layer to know what object was passed
	 * @return Any
	 */
	fun getView(): Any
	
	/**
	 * Gives access to the ID of the view, this view is the AD and is suppose to be used to position anything around it
	 * @return Int
	 */
	fun getViewId(): String
	
	
	/**
	 * This is used from [R89Banner] for when we don't want to reload the ad at infinitum.
	 * When called the current ad will keep being displayed until hidden but it will not refresh itself
	 */
	fun stopAutoRefresh()
	
	/**
	 * This is used from [com.refinery89.androidSdk.domain_layer.adFormats.formats_impl.R89BaseAd.destroy] for it's same purpose
	 */
	fun destroy()
	
	/**
	 * This is used from [com.refinery89.androidSdk.domain_layer.adFormats.formats_impl.R89BaseAd.invalidate] for it's same purpose
	 */
	fun invalidate()
}