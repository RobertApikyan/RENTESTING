package com.refinery89.sharedcore.os_layer.third_party_library

import cocoapods.CmpSdk.CMPConsentTool
import cocoapods.CmpSdk.CmpConfig
import com.refinery89.sharedcore.domain_layer.config.consentConfig.CMPEvents
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.consent_config.CMPData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.CMPLibrary

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle

@OptIn(ExperimentalForeignApi::class)
internal class CMPLibraryIosImpl: CMPLibrary
{
	private lateinit var consentTool: CMPConsentTool
	
	override fun initializeAndShow(appName: String, cmpData: CMPData, cmpEvents: CMPEvents)
	{
		val language = NSBundle.mainBundle.preferredLocalizations[0]
		val cmpConfig = CmpConfig.sharedConfig.setupWithId(id = cmpData.cmpId.toString(),
			appName = appName,
			domain = "delivery.consentmanager.net", language = language.toString())
		val cmpConsentTool = CMPConsentTool(cmpConfig)
		cmpConsentTool.withOpenListener(cmpEvents::onCMPOpened)
			?.withCloseListener(cmpEvents::onCMPClosed)
			?.withOnCMPNotOpenedListener(cmpEvents::onCMPNotOpened)
		cmpConsentTool.initialize()
	}
	
	override fun resetConsent()
	{
		R89LogUtil.i("CMP", "reset")
		CMPConsentTool.reset()
	}
	
	override fun getConsentDataForUrl(): String
	{
		val cmpString = if (this::consentTool.isInitialized)
		{
			consentTool.getConsentString()
		} else
		{
			R89LogUtil.i("CMP", "Consent has not been yet Tried to Open, Use SDK Initialization function")
			""
		}
		
		val cmpStringIsEmpty = if (cmpString.isNullOrEmpty()) "Yes" else "No"
		
		R89LogUtil.i("CMP", "cmpString: $cmpString \n is empty: $cmpStringIsEmpty")
		
		return "#cmpimport=$cmpString"
	}
	
	override fun getHTMLScriptToAvoidDisplayingCMP(): String
	{
		return """(function() {
			 var parent = document.getElementsByTagName('head').item(0);
			 var script = document.createElement('script');
			 script.type = 'text/javascript';
			 script.innerHTML = 'window.cmp_noscreen = true;';
			 parent.appendChild(script);
			})()""".trimMargin()
	}
	
	override fun reOpenConsent()
	{
		consentTool.openCmpConsentToolView()
	}
	
	override fun doesTheUrlHasConsentData(url: String): Boolean
	{
		return url.contains("#cmpimport=")
	}
}