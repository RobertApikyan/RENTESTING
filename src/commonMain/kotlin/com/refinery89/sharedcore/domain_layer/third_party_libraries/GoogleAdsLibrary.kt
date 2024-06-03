package com.refinery89.sharedcore.domain_layer.third_party_libraries

/**
 * Used to create OS Google libraries that we can use from the Domain
 */
internal interface GoogleAdsLibrary
{
	
	/**
	 * Initializes the Google Ads SDK
	 * @return Unit
	 */
	fun initializeGoogleAds()
	
	/**
	 * Configures the WebView for the Google Ads SDK to work properly as stated by it's documentation
	 * @param webView Any?
	 * @return Unit
	 */
	fun configureWebViewForGoogleLibrary(webView: Any?)
}