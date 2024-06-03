package com.refinery89.sharedcore

import R___Mobile_Native_Library.sharedCore.BuildConfig
import android.annotation.SuppressLint
import android.app.Application
import android.webkit.WebView
import com.refinery89.sharedcore.domain_layer.config.ConfigBuilder
import com.refinery89.sharedcore.domain_layer.initialization_state.InitializationEvents
import com.refinery89.sharedcore.domain_layer.log_tool.IR89Logger
import com.refinery89.sharedcore.os_layer.DependencyProviderAndroidImpl
import com.refinery89.sharedcore.os_layer.web_view.R89WebViewClient

internal object R89SDKInternal : R89SDKCommonApi by R89SDKCommon
{
	fun initialize(
		appContext: Application,
		publisherId: String,
		appId: String,
		singleLine: Boolean,
		publisherInitializationEvents: InitializationEvents?
	)
	{
		R89SDKCommon.initialize(
			DependencyProviderAndroidImpl(appContext, apiVersion = BuildConfig.API_VERSION_STRING),
			publisherId,
			appId,
			singleLine,
			publisherInitializationEvents,
		)
	}
	
	fun initializeWithConfigBuilder(
		appContext: Application,
		publisherId: String,
		appId: String,
		configBuilder: ConfigBuilder,
		publisherInitializationEvents: InitializationEvents?
	)
	{
		R89SDKCommon.initializeWithConfigBuilder(
			DependencyProviderAndroidImpl(appContext, apiVersion = BuildConfig.API_VERSION_STRING),
			publisherId,
			appId,
			configBuilder,
			publisherInitializationEvents,
		)
	}
	
	fun getUserAgent(webView: WebView, siteName: String): String
	{
		return R89SDKCommon.getUserAgent(webView, siteName)
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	fun configureWebView(webView: WebView, userAgent: String, webViewClient: R89WebViewClient)
	{
		R89SDKCommon.configureWebView(webView, userAgent, webViewClient)
	}
	
	fun loadUrlWithConsentDataOrWait(webView: WebView, url: String)
	{
		R89SDKCommon.loadUrlWithConsentDataOrWait(webView, url, "R89SDKBase")
	}
	
	fun setR89HelperLogger(logger: IR89Logger, replaceOsLogger: Boolean = false)
	{
		R89SDKCommon.setR89HelperLogger(logger, replaceOsLogger)
	}
}