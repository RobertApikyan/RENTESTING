package com.refinery89.sharedcore.os_layer.third_party_libraries

import android.content.Context
import android.os.Build
import android.os.LocaleList
import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.domain_layer.config.consentConfig.CMPButtonClicks
import com.refinery89.sharedcore.domain_layer.config.consentConfig.CMPEvents
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.consent_config.CMPData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.CMPLibrary
import net.consentmanager.sdk.CMPConsentTool
import net.consentmanager.sdk.common.CmpError
import net.consentmanager.sdk.common.callbacks.OnCMPNotOpenedCallback
import net.consentmanager.sdk.common.callbacks.OnCloseCallback
import net.consentmanager.sdk.common.callbacks.OnCmpButtonClickedCallback
import net.consentmanager.sdk.common.callbacks.OnErrorCallback
import net.consentmanager.sdk.common.callbacks.OnOpenCallback
import net.consentmanager.sdk.consentlayer.model.CMPConfig
import net.consentmanager.sdk.consentlayer.model.valueObjects.CmpButtonEvent
import java.util.Locale

/**
 * Uses The OS CMP Library to comply with [CMPLibrary] interface contract
 *
 * @property appContext Context
 * @property consentTool CMPConsentTool
 */
internal class CMPLibraryAndroidImpl(private val appContext: Context) : CMPLibrary
{
	private lateinit var consentTool: CMPConsentTool
	
	/** Uses the CMP OS Library to show a CMP in the OS View Hierarchy. */
	override fun initializeAndShow(appName: String, cmpData: CMPData, cmpEvents: CMPEvents)
	{
		val consentLibraryEvents = convertToLibraryEventObject(cmpEvents)
		
		val language = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
		{
			LocaleList.getDefault().get(0).language
		} else
		{
			Locale.getDefault().language
		}.uppercase()
		
		val config = CMPConfig.apply {
			this.id = cmpData.cmpCodeId
			this.domain = "delivery.consentmanager.net"
			this.appName = appName
			this.language = language
			this.gaid = null // TODO add here the AAID or the IDFA
			this.isDebugMode = R89SDKCommon.isDebug
		}
		
		consentTool = CMPConsentTool.createInstance(
			context = appContext,
			config = config,
			openListener = consentLibraryEvents,
			closeListener = consentLibraryEvents,
			cmpNotOpenedCallback = consentLibraryEvents,
			errorCallback = consentLibraryEvents,
		).initialize(appContext)
	}
	
	/**
	 * Resets the consent deleting all local data
	 *
	 * @return Unit
	 */
	override fun resetConsent()
	{
		R89LogUtil.i("CMP", "reset")
		CMPConsentTool.reset(appContext)
	}
	
	/**
	 * Reopens the cmp selection screen for the user to change settings
	 *
	 * @return Unit
	 */
	override fun reOpenConsent()
	{
		R89LogUtil.i("CMP", "reOpen")
		consentTool.openConsentLayer(appContext)
	}
	
	/**
	 * Returns a string that is usable in url for web apps urls so the cmp is not prompted two times
	 *
	 * [More info](https://help.consentmanager.net/books/cmp/page/combining-native-and-web-content-in-an-app)
	 *
	 * @return String
	 */
	override fun getConsentDataForUrl(): String
	{
		val cmpString = if (this::consentTool.isInitialized)
		{
			consentTool.exportCmpString()
			
		} else
		{
			R89LogUtil.i("CMP", "Consent has not been yet Tried to Open, Use SDK Initialization function")
			""
		}
		
		val cmpStringIsEmpty = if (cmpString.isEmpty()) "Yes" else "No"
		
		R89LogUtil.i("CMP", "cmpString: $cmpString \n is empty: $cmpStringIsEmpty")
		
		return "#cmpimport=$cmpString"
	}
	
	override fun doesTheUrlHasConsentData(url: String): Boolean
	{
		return url.contains("#cmpimport=")
	}
	
	/**
	 * returns a string that is javascript code that can be injected and executed in a web to add a script HTML tag (node) that disable CMP to show in undesired situations
	 *
	 * [More info](https://help.consentmanager.net/books/cmp/page/combining-native-and-web-content-in-an-app)
	 *
	 * @return String
	 */
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
	
	/**
	 * Maps Domain CMPEvents to specific CMPConsentTool callbacks
	 *
	 * @param sdkEvents CMPEvents
	 */
	private fun convertToLibraryEventObject(sdkEvents: CMPEvents) = object : OnOpenCallback,
	                                                                         OnCloseCallback,
	                                                                         OnCMPNotOpenedCallback,
	                                                                         OnErrorCallback,
	                                                                         OnCmpButtonClickedCallback
	{
		override fun onCMPNotOpened()
		{
			sdkEvents.onCMPNotOpened()
		}
		
		override fun onWebViewClosed()
		{
			sdkEvents.onCMPClosed()
		}
		
		override fun onWebViewOpened()
		{
			sdkEvents.onCMPOpened()
		}
		
		override fun errorOccurred(type: CmpError, message: String)
		{
			val sdkEventMessage = when (type)
			{
				CmpError.NetworkError          -> "Network Error, Message: $message"
				CmpError.TimeoutError          -> "Timeout Error, Message: $message"
				CmpError.ConsentReadWriteError -> "Consent Read/Write Error, Message: $message"
				CmpError.ConsentLayerError     -> "Consent Layer Error, Message: $message"
				else                           -> "Unknown Error, Message: $message"
			}
			
			sdkEvents.onCMPError(sdkEventMessage)
		}
		
		override fun onButtonClicked(event: CmpButtonEvent)
		{
			val sdkButtonClick = when (event)
			{
				CmpButtonEvent.AcceptAll -> CMPButtonClicks.AcceptAll()
				CmpButtonEvent.RejectAll -> CMPButtonClicks.RejectAll()
				CmpButtonEvent.Save      -> CMPButtonClicks.SaveCustomSettings()
				CmpButtonEvent.Close     -> CMPButtonClicks.Close()
				else                     -> CMPButtonClicks.Unknown()
			}
			
			sdkEvents.onCMPButtonClicked(sdkButtonClick)
		}
	}
}