package com.refinery89.sharedcore.os_layer.web_veiw

import com.refinery89.sharedcore.domain_layer.web_view.IWebViewHelper

internal class WebViewHelperIosImpl: IWebViewHelper()
{
	override fun isWebViewType(webView: Any, tag: String): Boolean
	{
		TODO("Not yet implemented")
	}
	
	override fun isWebViewClientType(webViewClient: Any, tag: String): Boolean
	{
		TODO("Not yet implemented")
	}
	
	override fun loadUrl(webView: Any, url: String)
	{
		TODO("Not yet implemented")
	}
	
	override fun getUserAgent(webView: Any): String
	{
		TODO("Not yet implemented")
	}
	
	override fun setUserAgent(webView: Any, userAgent: String)
	{
		TODO("Not yet implemented")
	}
	
	override fun setWebViewClient(webView: Any, webViewClient: Any)
	{
		TODO("Not yet implemented")
	}
	
	override fun evaluateJavascript(webView: Any, script: String)
	{
		TODO("Not yet implemented")
	}
	
	override fun stopLoading(webView: Any)
	{
		TODO("Not yet implemented")
	}
}