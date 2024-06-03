package com.refinery89.sharedcore

import android.annotation.SuppressLint
import android.app.Application
import android.webkit.WebView
import com.refinery89.sharedcore.R89SDKCommon.setDebug
import com.refinery89.sharedcore.R89SDKInternal.configureWebView
import com.refinery89.sharedcore.R89SDKInternal.setDebug
import com.refinery89.sharedcore.domain_layer.config.ConfigBuilder
import com.refinery89.sharedcore.domain_layer.initialization_state.InitializationEvents
import com.refinery89.sharedcore.domain_layer.log_tool.LogLevels
import com.refinery89.sharedcore.os_layer.web_view.R89WebViewClient

object R89SDK
{
	
	/**
	 * This is to use Auto Configuration where the SDK will fetch the configuration from the server,
	 * and apply those to itself. it still requires to use the [RefineryAdFactory] to place the ads.
	 * in the Views
	 *
	 * If you used [setDebug] you are not going to get your info but a fake PublisherData because
	 * we assume you are testing. We don't recommend using real data while testing can be done
	 * through [initializeWithConfigBuilder]
	 *
	 * @param appContext Application this is needed to provide a way to check for lifecycle of the app
	 * @param publisherId String this will be used to fetch the configuration from the server
	 * @param singleLine if you use this bool you WONT need to use the [RefineryAdFactory]
	 * to place the ads but first you need to follow [Link](Guide to single line config)
	 */
	fun initialize(
		appContext: Application,
		publisherId: String,
		appId: String,
		singleLine: Boolean,
		initializationEvents: InitializationEvents? = null,
	)
	{
		R89SDKInternal.initialize(appContext, publisherId, appId, singleLine, initializationEvents)
	}
	
	/**
	 * This is to use Manual Configuration you must pass a [ConfigBuilder] to use this method.
	 * Ensure you follow [Link](Guide to manual configuration) to set all the required values
	 * in the [ConfigBuilder].
	 *
	 * With manual configuration you take control of the AdUnit Configurations and
	 * the Targeting Configurations.
	 *
	 * Remember that you still need Internet Connection for the CMP to work. and also we send data
	 * about the logs to our Db to better client support
	 *
	 * Can use [setDebug] to test real ad-units and real information setting to true the
	 * useRealServer bool in the function otherwise leave it to false and use the [ConfigBuilder]
	 * test ad unit constants to build your test data for testing the manual configuration
	 *
	 * @param appContext Application this is needed to provide a way to check for lifecycle of the app
	 * @param publisherId String this will be used to send info about your use to our server
	 * @param configBuilder
	 */
	fun initializeWithConfigBuilder(
		appContext: Application,
		publisherId: String,
		appId: String,
		configBuilder: ConfigBuilder,
		initializationEvents: InitializationEvents? = null,
	)
	{
		
		R89SDKInternal.initializeWithConfigBuilder(appContext, publisherId, appId, configBuilder, initializationEvents)
	}
	
	/**
	 * if Set to true makes the SDK debug mode which means:
	 * - test host and account id will be used from the configuration unless realServer flag is changed
	 * - all cmp configurations will be fake
	 * - global configuration will be fake including publisher and user configurations, unless realServer flag is true
	 */
	fun setDebug(
		getLocalFakeData: Boolean = true,
		forceCMP: Boolean = false,
		useProductionAuctionServer: Boolean = false,
	)
	{
		R89SDKInternal.setDebug(getLocalFakeData, forceCMP, useProductionAuctionServer)
	}
	
	/**
	 * Shows you different logs depending on what you indicate
	 * @param level LogLevels
	 */
	fun setLogLevel(level: LogLevels)
	{
		R89SDKInternal.setLogLevel(level)
	}
	
	/**
	 * Deletes all local data saved about the CMP, and next time the CMP is asked if it needs to show or not it will require showing since all consent that was previously given has been deleted
	 */
	fun resetConsent()
	{
		R89SDKInternal.resetConsent()
	}
	
	/**
	 * This build a user agent that allow the SDK to track what ads are coming from the native web-app and which ones are coming from browsers.
	 * @param webView WebView
	 * @param siteName String
	 * @return String
	 */
	fun getUserAgent(webView: WebView, siteName: String): String
	{
		return R89SDKInternal.getUserAgent(webView, siteName)
	}
	
	/**
	 * This is to configure the WebView with the settings we need for Ads to work properly
	 * we are turning doing the following:
	 * - javascriptEnabled = true
	 * - domStorageEnabled = true
	 * - mediaPlaybackRequiresUserGesture = false
	 * - userAgentString = [userAgent]
	 * - webViewClient = [webViewClient]
	 * - enabling third party cookies when possible (only on API 21 and above)
	 * - registering the webView with the Google Ads to allow the sending of notifications
	 *
	 * @param webView WebView
	 * @param userAgent String
	 * @param webViewClient R89WebViewClient
	 * @return Unit
	 */
	@SuppressLint("SetJavaScriptEnabled")
	fun configureWebView(webView: WebView, userAgent: String, webViewClient: R89WebViewClient)
	{
		R89SDKInternal.configureWebView(webView, userAgent, webViewClient)
	}
	
	/**
	 * This will check if we can load the url with consent if not it will wait until cmp data is available and then load the url.
	 *
	 * Note that we recommend using just [configureWebView], because the client
	 * will handle the loading of the url with consent BUT if for some reason you can't use [configureWebView] we offer this function.
	 * @param webView WebView
	 * @param url String
	 * @return Unit
	 */
	fun loadUrlWithConsentDataOrWait(webView: WebView, url: String)
	{
		R89SDKInternal.loadUrlWithConsentDataOrWait(webView, url)
	}
}
