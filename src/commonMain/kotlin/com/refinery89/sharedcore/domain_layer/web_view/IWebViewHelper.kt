package com.refinery89.sharedcore.domain_layer.web_view

import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.domain_layer.initialization_state.SDKInitState
import com.refinery89.sharedcore.domain_layer.internalToolBox.custom_types.MutableString
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil

internal abstract class IWebViewHelper
{
	abstract fun isWebViewType(webView: Any, tag: String): Boolean
	abstract fun isWebViewClientType(webViewClient: Any, tag: String): Boolean
	abstract fun loadUrl(webView: Any, url: String)
	abstract fun getUserAgent(webView: Any): String
	abstract fun setUserAgent(webView: Any, userAgent: String)
	abstract fun setWebViewClient(webView: Any, webViewClient: Any)
	abstract fun evaluateJavascript(webView: Any, script: String)
	protected abstract fun stopLoading(webView: Any)
	
	/**
	 * Use this on the onPageStart of the WebViewClient to avoid the CMP to show up in the web.
	 *
	 * What does this do?
	 * Adds a <script> tag to the header of the web to load.
	 * This script is just setting a variable to true so the CMP doesn't show up.
	 *
	 * Why?
	 * This is because sometimes the CMP will still open in the web even if we send the consent data,
	 * this will avoid the behaviour.
	 *
	 * [More Info](https://help.consentmanager.net/books/cmp/page/combining-native-and-web-content-in-an-app)
	 * @return String
	 */
	fun addScriptToNotShowCMPInWebView(webView: Any?)
	{
		if (!R89SDKCommon.isInitialized(TAG))
		{
			R89LogUtil.e(
				TAG,
				"Please initialize the SDK before calling loading a url into the Web View",
				false
			)
			return
		}
		
		if (webView == null)
		{
			R89LogUtil.w(TAG, "The webView is null on addScriptToNotShowCMPInWebView method", false)
			return
		}
		
		if (isWebViewType(webView, TAG))
		{
			val script = R89SDKCommon.globalConfig.getUserPrivacyTool().getHTMLScriptToAvoidDisplayingCMP()
			evaluateJavascript(webView, script)
		}
	}
	
	/**
	 * Adds the consent data to the url if it can't it will return false and log the error
	 * @param url String
	 * @return String
	 */
	private fun handleUrl(webView: Any, url: String, urlHost: String?, appHostName: String, lastUrlThaTriedToAddConsent: MutableString): Boolean
	{
		if (urlHost == appHostName)
		{
			R89LogUtil.d(TAG, "Url to handle: $url", false)
			if (!R89SDKCommon.isInitialized(TAG))
			{
				//This will happened if the publisher configures the WebView with our client, then loads a url and has not called any initialization
				R89LogUtil.e(
					TAG,
					"Trying to load url on WebView that has R89WebViewClient before the SDK is initialize method",
					true
				)
				return false
			}
			
			val urlDoesNotHaveConsentData = !R89SDKCommon.globalConfig.getUserPrivacyTool().doesTheUrlHasConsentData(url)
			val haveNotTriedToAddConsentDataToThisUrl = url != lastUrlThaTriedToAddConsent.value
			
			if (urlDoesNotHaveConsentData && haveNotTriedToAddConsentDataToThisUrl)
			{
				lastUrlThaTriedToAddConsent.value = url
				
				//Check current state of sdk:
				// - if sdk is STARTED_INITIALIZATION wait until it changes state so return TRUE and wait for either fail or continuation
				// - if sdk is STARTED_DATA_FETCH wait until it changes state so return TRUE and wait for either fail or continuation
				// - if sdk is DATA_FETCH_FAILED load the url without consent so return FALSE and load the url without consent
				// - if sdk is STARTED_CMP wait until it changes state so return TRUE and wait for either fail or continuation
				// - if sdk is CMP_FINISHED | INITIALIZATION_FINISHED load the url with consent return TRUE
				
				val cantTakeConsentStateBitMask = SDKInitState.DATA_FETCH_FAILED
				
				return if (R89SDKCommon.stateIs(cantTakeConsentStateBitMask))
				{
					R89LogUtil.w(
						TAG,
						"SDK is in a state that can't take consent data, loading url without consent",
						false
					)
					false
				} else
				{
					R89LogUtil.d(
						TAG,
						"SDK is in a state that it needs to wait for consent, waiting for consent resolution",
						false
					)
					R89SDKCommon.loadUrlWithConsentDataOrWait(webView, url, TAG)
					true
				}
			}
		}
		return false
	}
	
	/**
	 * Adds the consent data to the url if it can't it will return and will give you the chance to continue loading the url normally
	 * otherwise (if it can get consent data or it may be able in the future) it will stop the loading of the current url and load the new url
	 * once it's sure it can or it can not get the consent data.
	 * and log the error
	 * @param url String
	 * @return String
	 */
	fun handleUrl(webView: Any?, url: String, urlHost: String?, appHostName: String, lastUrlThaTriedToAddConsent: MutableString)
	{
		if (webView == null)
		{
			R89LogUtil.w(TAG, "The webView is null on handleUrl method", false)
			return
		}
		
		if (isWebViewType(webView, TAG))
		{
			if (handleUrl(webView, url, urlHost, appHostName, lastUrlThaTriedToAddConsent))
			{
				R89LogUtil.i(TAG, "Request was intercepted and stopped by R89", false)
				
				R89SDKCommon.executeInMainThread {
					stopLoading(webView)
				}
			}
		}
	}
	
	companion object
	{
		private const val TAG = "IWebViewHelper"
	}
}

