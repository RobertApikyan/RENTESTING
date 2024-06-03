package com.refinery89.sharedcore.os_layer.third_party_libraries

import android.app.Application
import android.os.Build
import android.webkit.CookieManager
import com.google.android.gms.ads.MobileAds
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.third_party_libraries.GoogleAdsLibrary


// TODO we could Unify this along with the [PrebidLibraryAndroidImpl] into a single class that implements both interfaces or a new interface called [monetization library]
/**
 * Uses The OS Google Library to comply with [GoogleAdsLibrary] interface contract
 * @property appContext Context
 */
internal class GoogleAdsLibraryAndroidImpl(private val appContext: Application) : GoogleAdsLibrary
{
	/**
	 * @suppress
	 * @return Unit
	 */
	override fun initializeGoogleAds()
	{
		MobileAds.initialize(appContext) {
			R89LogUtil.i(TAG, "Google Mobile Ads sdk Initialization complete.")
		}
	}
	
	/**
	 * @suppress
	 * @param webView Any?
	 * @return Unit
	 */
	override fun configureWebViewForGoogleLibrary(webView: Any?)
	{
		if (webView is android.webkit.WebView)
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			{
				CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
				MobileAds.registerWebView(webView)
			}
			
			// Let the web view use JavaScript.
			webView.settings.javaScriptEnabled = true
			// Let the web view access local storage.
			webView.settings.domStorageEnabled = true
			// Let HTML videos play automatically.
			webView.settings.mediaPlaybackRequiresUserGesture = false
		}
	}
	
	/**
	 * @suppress
	 * @return Unit
	 */
	companion object
	{
		private const val TAG = "Google"
	}
}