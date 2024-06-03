package com.refinery89.sharedcore.os_layer.web_view

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.domain_layer.internalToolBox.custom_types.MutableString
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.web_view.IWebViewHelper

/**
 * This class is used to add the consent data to the url that is being loaded in the WebView.
 * And Prevent the CMP to show up in the web, which would mean that the CMP is prompted twice.
 *
 * For this we are injecting a script in the header of the web to load. This script is just setting a variable to true so the CMP doesn't show up.
 * And also whenever we call a [WebView.loadUrl] we are adding the consent data to the url and overriding the load to load it again.
 *
 * if you need to override [shouldInterceptRequest] override [r89ShouldInterceptRequest] instead.
 *
 * @property appHostName String
 * @property lastUrlThaTriedToAddConsent String
 * @constructor
 */
open class R89WebViewClient(
	private val appHostName: String,
) : WebViewClient()
{
	private var lastUrlThaTriedToAddConsent: MutableString = MutableString()
	
	private val webViewHelper: IWebViewHelper
	
	init
	{
		if (!R89SDKCommon.isInitialized(TAG))
		{
			R89LogUtil.e(TAG, "Please initialize the SDK before creating the R89WebViewClient", false)
		}
		
		webViewHelper = R89SDKCommon.webViewHelper
		
	}
	
	/**
	 * This function is called when the web starts loading a url.
	 * look at [WebViewClient.onPageStarted]
	 *
	 * we are overriding the default behaviour to add the script to the web. so the cmp does not show twice
	 * @param webView WebView
	 * @param url String
	 * @param favicon Bitmap
	 * @return Unit
	 */
	final override fun onPageStarted(webView: WebView?, url: String?, favicon: Bitmap?)
	{
		R89LogUtil.d(TAG, "OnPageStarted for url: $url", false)
		webViewHelper.addScriptToNotShowCMPInWebView(webView)
		r89OnPageStarted(webView, url, favicon)
		super.onPageStarted(webView, url, favicon)
	}
	
	/**
	 * we are overriding the default behaviour to add the consent data to the url and load it again.
	 *
	 * instead of overriding this function override [r89ShouldInterceptRequest]
	 * @param view WebView
	 * @param request WebResourceRequest
	 * @return Boolean
	 */
	@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
	final override fun shouldInterceptRequest(
		view: WebView?,
		request: WebResourceRequest?
	): WebResourceResponse?
	{
		
		val pubModifiedRequest = r89ShouldInterceptRequest(view, request)
		if (pubModifiedRequest != null)
		{
			R89LogUtil.i(TAG, "Request was intercepted by publisher", false)
			return pubModifiedRequest
		}
		
		// NOTE This also checks for request being null
		if (request?.isForMainFrame != true)
		{
			return null
		}
		
		val url = request.url.toString()
		
		val uriHost = Uri.parse(url).host
		
		webViewHelper.handleUrl(view, url, uriHost, appHostName, lastUrlThaTriedToAddConsent)
		
		return super.shouldInterceptRequest(view, request)
	}
	
	/**
	 * we are overriding the default behaviour to add the consent data to the url and load it again.
	 *
	 * instead of overriding this function override [r89ShouldInterceptRequest]
	 * @param view WebView
	 * @param url String
	 * @return Boolean
	 */
	@Suppress("DEPRECATION")
	@Deprecated("Deprecated in Java")
	final override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse?
	{
		val pubModifiedRequest = r89ShouldInterceptRequest(view, url)
		if (pubModifiedRequest != null)
		{
			R89LogUtil.i(TAG, "Request was intercepted by publisher", false)
			return pubModifiedRequest
		}
		
		if (url == null)
		{
			return null
		}
		
		val uriHost = Uri.parse(url).host
		
		webViewHelper.handleUrl(view, url, uriHost, appHostName, lastUrlThaTriedToAddConsent)
		
		return super.shouldInterceptRequest(view, url)
	}
	
	/**
	 * This will be called before the [shouldInterceptRequest] but we can't allow overriding [shouldInterceptRequest] because we need it to include cmp data when loading a
	 * web inside [appHostName] domain.
	 *
	 * Otherwise use it as [shouldInterceptRequest], look at [WebViewClient.shouldInterceptRequest]
	 *
	 * @param view WebView?
	 * @param request WebResourceRequest?
	 * @return Boolean If not overwritten always returns null to continue loading the [request] normally
	 */
	@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
	open fun r89ShouldInterceptRequest(
		view: WebView?,
		request: WebResourceRequest?
	): WebResourceResponse?
	{
		return null
	}
	
	/**
	 * This will be called before the [shouldInterceptRequest] but we can't allow overriding [shouldInterceptRequest] because we need it to include cmp data when loading a
	 * web inside [appHostName] domain.
	 *
	 * Otherwise use it as [shouldInterceptRequest], look at [WebViewClient.shouldInterceptRequest]
	 * @param view WebView?
	 * @param url String?
	 * @return Boolean If not overwritten always returns null e to continue loading the [url] normally
	 */
	@Deprecated(
		"Deprecated in Java",
		ReplaceWith("r89ShouldInterceptRequest(view: WebView?, request: WebResourceRequest): WebResourceResponse?")
	)
	open fun r89ShouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse?
	{
		return null
	}
	
	/**
	 * The way to get notified when [WebViewClient.onPageStarted] is called
	 *
	 * This function is called when the web starts loading a url.
	 *
	 * we are overriding the default behaviour of the [WebViewClient.onPageStarted] to add the script to the web, so the cmp does not show twice
	 *
	 * @param webView WebView?
	 * @param url String?
	 * @param favicon Bitmap?
	 * @return Unit
	 */
	open fun r89OnPageStarted(webView: WebView?, url: String?, favicon: Bitmap?)
	{
	
	}
	
	/**
	 * @suppress
	 */
	companion object
	{
		private const val TAG = "R89WebClient"
	}
}
