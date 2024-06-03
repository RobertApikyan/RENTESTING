package com.refinery89.sharedcore.domain_layer.third_party_libraries

import com.refinery89.sharedcore.domain_layer.config.consentConfig.CMPEvents
import com.refinery89.sharedcore.domain_layer.models.consent_config.CMPData

/**
 * Used to create OS CMP libraries that we can use from the Domain
 */
internal interface CMPLibrary
{
	/**
	 * Uses the CMP OS Library to show a CMP in the OS View Hierarchy
	 */
	fun initializeAndShow(appName: String, cmpData: CMPData, cmpEvents: CMPEvents)
	
	/**
	 * Deletes all local data saved about the CMP, and next time the CMP is asked if it needs to show or not it will require showing since all consent that was previously given has been deleted
	 */
	fun resetConsent()
	
	/**
	 * Returns a string to be inputted in the url in web apps urls so the cmp is not triggered two times from a native app where consent is already required from the app
	 *
	 * [More info](https://help.consentmanager.net/books/cmp/page/combining-native-and-web-content-in-an-app)
	 * @return String
	 */
	fun getConsentDataForUrl(): String
	
	/**
	 * returns a string that is javascript code that can be injected and executed in a web to add a script HTML tag (node)
	 * that disable CMP to show in undesired situations
	 *
	 * [More info](https://help.consentmanager.net/books/cmp/page/combining-native-and-web-content-in-an-app)
	 * @return String
	 */
	fun getHTMLScriptToAvoidDisplayingCMP(): String
	
	/**
	 * Reopens the cmp selection screen for the user to change settings
	 * @return Unit
	 */
	fun reOpenConsent()
	
	/**
	 * checks if the Url has consent data
	 * @param url String
	 * @return Boolean
	 */
	fun doesTheUrlHasConsentData(url: String): Boolean
}