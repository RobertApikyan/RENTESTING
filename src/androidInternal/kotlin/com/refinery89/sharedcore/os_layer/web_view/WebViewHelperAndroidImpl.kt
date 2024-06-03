package com.refinery89.sharedcore.os_layer.web_view

import android.webkit.WebView
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.web_view.IWebViewHelper

internal class WebViewHelperAndroidImpl : IWebViewHelper()
{
	override fun isWebViewType(webView: Any, tag: String): Boolean
	{
		if (webView !is WebView)
		{
			R89LogUtil.e(tag, "The webView is not a android.webkit.WebView", false)
			return false
		}
		return true
	}
	
	override fun isWebViewClientType(webViewClient: Any, tag: String): Boolean
	{
		if (webViewClient !is R89WebViewClient)
		{
			R89LogUtil.e(tag, "The webViewClient is not of R89WebViewClient type", false)
			return false
		}
		return true
	}
	
	override fun loadUrl(webView: Any, url: String)
	{
		webView as WebView
		webView.loadUrl(url)
	}
	
	override fun getUserAgent(webView: Any): String
	{
		webView as WebView
		return webView.settings.userAgentString
	}
	
	override fun setUserAgent(webView: Any, userAgent: String)
	{
		webView as WebView
		webView.settings.userAgentString = userAgent
	}
	
	override fun setWebViewClient(webView: Any, webViewClient: Any)
	{
		webView as WebView
		webView.webViewClient = webViewClient as android.webkit.WebViewClient
	}
	
	override fun evaluateJavascript(webView: Any, script: String)
	{
		webView as WebView
		webView.evaluateJavascript(script, null)
	}
	
	override fun stopLoading(webView: Any)
	{
		webView as WebView
		webView.stopLoading()
	}
}