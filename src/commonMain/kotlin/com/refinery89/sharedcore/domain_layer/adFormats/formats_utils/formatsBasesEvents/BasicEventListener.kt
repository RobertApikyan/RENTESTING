package com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.formatsBasesEvents

import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError

/** Base event listener for all format specific event listeners */
internal interface BasicEventListener
{
	/** Called when an ad is loaded. */
	fun onLoaded()
	
	/** Called when an ad is failed to load. */
	fun onFailedToLoad(error: R89LoadError)
	
	/** Called when an ad is shown in the screen for the first time. */
	fun onImpression()
	
	/** Called when an ad is clicked. */
	fun onClick()
}