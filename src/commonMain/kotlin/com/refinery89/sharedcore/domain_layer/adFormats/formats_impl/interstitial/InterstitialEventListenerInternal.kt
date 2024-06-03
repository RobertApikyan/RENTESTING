package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial

import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.formatsBasesEvents.BasicEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.formatsBasesEvents.OpenEventListener

internal interface InterstitialEventListenerInternal : BasicEventListener, OpenEventListener
{
	/**
	 * Called when is going to open at fullScreen but fails to do so in full
	 * screen
	 */
	fun onAdFailedToShowFullScreen(errorMsg: String)
	
	/**
	 * Called when the interstitial is closed normally. This is just internal callback since [R89Interstitial.afterDismissEventListener] is used to notify the publisher it can
	 * re-engage the app with the normal navigation flow and is not optional like providing an [InterstitialEventListener]
	 */
	fun onAdDismissedFullScreenContent()
}