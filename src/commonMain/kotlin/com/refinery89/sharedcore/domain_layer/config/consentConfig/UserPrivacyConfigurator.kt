package com.refinery89.sharedcore.domain_layer.config.consentConfig

import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.domain_layer.internalToolBox.auxiliarEvents.VoidEventManager
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.consent_config.CMPData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.CMPLibrary
import com.refinery89.sharedcore.domain_layer.third_party_libraries.PrebidLibrary


/** this receives CMP info and sets all the flags for prebid consent management */
internal class UserPrivacyConfigurator(
	private val prebidLibrary: PrebidLibrary,
	private val cmpLibrary: CMPLibrary,
) : CMPEvents
{
	
	internal val onCMPFinished: VoidEventManager = VoidEventManager()
	
	/**
	 * This is to choose between showing the CMP or not
	 *
	 * @return Unit
	 */
	fun initializeAndShowCMP(
		cmpData: CMPData, // Custom interface that implements all of the CMP library callbacks
	)
	{
		if (R89SDKCommon.usingCMP)
		{
			initializeCMP(cmpData)
		} else
		{
			
			R89LogUtil.d(TAG, "CMP is disabled")
			onCMPFinished.invoke()
		}
	}
	
	
	/**
	 * Deletes all local data saved about the CMP, and next time the CMP is asked if it needs to show or not it will require showing since all consent that was previously given has
	 * been deleted.
	 */
	fun resetCMP()
	{
		cmpLibrary.resetConsent()
	}
	
	/**
	 * Returns a string to be inputted in the url in web apps urls so the cmp is not triggered two times from a native app where consent is already required from the app
	 *
	 * [More info](https://help.consentmanager.net/books/cmp/page/combining-native-and-web-content-in-an-app)
	 *
	 * @return String
	 */
	fun getConsentDataForUrl(): String
	{
		return cmpLibrary.getConsentDataForUrl()
	}
	
	/**
	 * returns a string that is javascript code that can be injected and executed in a web to add a script HTML tag (node) that disable CMP to show in undesired situations
	 *
	 * [More info](https://help.consentmanager.net/books/cmp/page/combining-native-and-web-content-in-an-app)
	 *
	 * @return String
	 */
	fun getHTMLScriptToAvoidDisplayingCMP(): String
	{
		return cmpLibrary.getHTMLScriptToAvoidDisplayingCMP()
	}
	
	fun doesTheUrlHasConsentData(url: String): Boolean
	{
		return cmpLibrary.doesTheUrlHasConsentData(url)
	}
	
	/**
	 * This is to show the CMP to the user must be called after we know the CMPData from the Refinery89 DB.
	 *
	 * @return Unit
	 */
	private fun initializeCMP(cmpData: CMPData)
	{
		val appName = if (R89SDKCommon.isDebug)
		{
			"SDK On Debug"
		} else
		{
			R89SDKCommon.sdkAppId
		}
		
		cmpLibrary.initializeAndShow(appName, cmpData, this)
	}
	
	/** @suppress */
	private fun setPrivacyConfig()
	{
		prebidLibrary.setPrivacyConfig()
	}
	
	/** @suppress */
	override fun onCMPOpened()
	{
		R89LogUtil.d(TAG, "CMP WebView Opened")
	}
	
	/** @suppress */
	override fun onCMPClosed()
	{
		R89LogUtil.d(TAG, "CMP WebView Closed")
		setPrivacyConfig()
		onCMPFinished.invoke()
	}
	
	/** @suppress */
	override fun onCMPNotOpened()
	{
		R89LogUtil.d(TAG, "CMP WebView Not Opened")
		setPrivacyConfig()
		onCMPFinished.invoke()
	}
	
	override fun onCMPError(sdkEventMessage: String)
	{
		R89LogUtil.e(TAG, "CMP Error: $sdkEventMessage", false)
		onCMPFinished.invoke()
	}
	
	override fun onCMPButtonClicked(buttonEvent: CMPButtonClicks)
	{
		R89LogUtil.d(TAG, "CMP Button Clicked: $buttonEvent")
	}
	
	/** @suppress */
	companion object
	{
		private const val TAG = "ConsentConfig"
	}
}