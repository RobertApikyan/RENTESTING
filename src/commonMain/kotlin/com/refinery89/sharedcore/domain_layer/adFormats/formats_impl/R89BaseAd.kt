package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl

import com.refinery89.sharedcore.domain_layer.models.unit_config.AdUnitConfigData

/**
 * The responsibility of each ad object is to create, show and destroy itself when it is mandated,
 * also invalidate itself when it can't be shown or used as intended so the factory can safe destroy it.
 *
 * Ad Objects API is not directly available to the publisher to avoid extra complexity, they can access or configure it through the Factory
 * @property isValid set by the object inside [invalidate] when the ad is not valid anymore
 * Read by the factory to purge ad objects when they are not valid any more
 * (e.g. impression has not happened in more than half an hour)
 */
internal abstract class R89BaseAd
{
	protected var isValid: Boolean = true
	
	/**
	 * children use this to extract data from the adConfigObject
	 * @param adConfigObject AdUnitConfig
	 * @return Unit
	 */
	protected abstract fun cacheConfigurationValues(adConfigObject: AdUnitConfigData)
	
	/**
	 * Used by the factory to tell the unit which configuration it has, also if the SDK is in debug mode we will use StoredAuctionResponses to Test The Ads
	 * @param adConfigObject AdUnitConfig
	 */
	open fun configureAd(
		adConfigObject: AdUnitConfigData,
	)
	{
		cacheConfigurationValues(adConfigObject)
	}
	
	/**
	 * Used by the factory to show the ad, internally it checks if it can do it or not calling corresponding events
	 */
	abstract fun show(newWrapper: Any? = null)
	
	/**
	 * Hides the ad, may not be implemented in all ads
	 */
	abstract fun hide()
	
	/**
	 * invalidates the object setting [isValid] to true, and calls invalidated event
	 * this is done when the ad can no longer be shown or is no longer safe to show according to google ad manager and prebid rules
	 */
	abstract fun invalidate()    //todo call event to notify the factory
	
	/**
	 * Used by the factory to destroy the ad, each ad object has responsibility to destroy any resources
	 */
	abstract fun destroy()
	
	/**
	 * Used by the sdk to read [isValid]
	 * @return Boolean
	 */
	fun adObjectIsValid(): Boolean
	{
		return isValid
	}
}