package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial

import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError

/**
 * Interstitial life cycle goes as follows:
 * 1. [onLoaded]: Called when the banner is loaded and ready to be shown. Or If this goes wrong, the [onFailedToLoad] when this happens the factory marks the object for
 * deletion, and calls [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createInterstitial] afterInterstitial
 * 2. [onOpen]: Something happen in your app and you call the factory [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.show] so this is the event is invoked if
 * everything goes according to plan. Otherwise [onAdFailedToShowFullScreen] is called, you will receive and error message string with information about it.
 * 3. [onImpression]: This means it did finish loading the creatives in the screen and they can be clicked
 * 4. [onClick]: The ad opens another Intent with the advertiser link
 * 5. [onAdDismissedFullScreenContent]: Called when the interstitial is closed normally
 *
 * - [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createInterstitial] **afterInterstitial**: Is called when some situations happen. So the app flow can be recovered.
 * Situations when called are:
 *      - Everything went right and the user just closed the fullscreen ad
 *      - The ad hasn't loaded yet and you tried to show it
 *      - The ad has been Invalidated and you tried to show it. Gets invalidated when:
 *      - Fails to load
 *      - Already shown
 *      - Too Long without showing it
 *      - The ad failed to show
 */
abstract class InterstitialEventListener : InterstitialEventListenerInternal
{
	override fun onAdFailedToShowFullScreen(errorMsg: String)
	{
	}
	
	override fun onAdDismissedFullScreenContent()
	{
	}
	
	override fun onLoaded()
	{
	}
	
	override fun onFailedToLoad(error: R89LoadError)
	{
	}
	
	override fun onImpression()
	{
	}
	
	override fun onClick()
	{
	}
	
	override fun onOpen()
	{
	}
}