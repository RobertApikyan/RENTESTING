package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl

/**
 * Classes that inherit from this are classes that directly interact with the GAM and Prebid Libraries
 * @property hasBeenImpressed used to invalidate the ad after a time if it is not shown
 * @property gamUnitId gam unit id it's cached when [configureAd] is called
 * @property pbConfigId prebid config id it's cached when [configureAd] is called
 */
internal abstract class R89AdObject : R89BaseAd()
{
	protected var hasBeenImpressed = false //TODO Use this for the thing is suppose to be used from the factory when we need to purge the ads stack
	protected var gamUnitId: String = ""
	protected var pbConfigId: String = ""
}