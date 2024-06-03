package com.refinery89.sharedcore.os_layer.third_party_library

import com.refinery89.sharedcore.domain_layer.third_party_libraries.GoogleAdsLibrary
import cocoapods.Google_Mobile_Ads_SDK.GADMobileAds

import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import kotlinx.cinterop.ExperimentalForeignApi
import platform.WebKit.WKWebView
import platform.WebKit.javaScriptEnabled
import platform.WebKit.mediaPlaybackRequiresUserAction

internal class GoogleAdsLibraryIosImpl: GoogleAdsLibrary
{
	companion object
	{
		private const val TAG = "Google"
	}
	
	@OptIn(ExperimentalForeignApi::class)
	override fun initializeGoogleAds()
	{
		GADMobileAds.sharedInstance().startWithCompletionHandler {
			R89LogUtil.i(TAG,"Google Mobile Ads sdk Initialization complete.")
		}
	}
	
	@OptIn(ExperimentalForeignApi::class)
	override fun configureWebViewForGoogleLibrary(webView: Any?)
	{
		if(webView is WKWebView){
			GADMobileAds.sharedInstance().registerWebView(webView)
			// Let the web view use JavaScript.
			webView.configuration.preferences.javaScriptEnabled = true
			// Dom storage is enabled for WKWebView by default
			// Let HTML videos play automatically.
			webView.configuration.mediaPlaybackRequiresUserAction = false
		}
	}
}