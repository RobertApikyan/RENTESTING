package com.refinery89.sharedcore.domain_layer.third_party_libraries

import com.refinery89.sharedcore.domain_layer.models.prebid_server.PrebidServerConfigData


/**
 * Used to create OS Prebid libraries that we can use from the Domain
 */
internal interface PrebidLibrary
{
	/**
	 * Calls all the functions needed to configure the Prebid SDK coming from the prebid documentation
	 * @return Unit
	 */
	fun configurePrebid()
	
	/**
	 * Configures the Prebid Server
	 * @param prebidServerConfigData PrebidServerConfigData
	 * @return Unit
	 */
	fun configurePrebidServer(prebidServerConfigData: PrebidServerConfigData)
	
	/**
	 * Sets the publisher name to be sent to the auction server
	 * @param publisherName String
	 * @return Unit
	 */
	fun setPublisherName(publisherName: String)
	
	/**
	 * Sets the web domain of the publisher to be sent to the auction server
	 * @param domain String
	 * @return Unit
	 */
	fun setDomain(domain: String?)
	
	/**
	 * Sets the store url of the publisher to be sent to the auction server
	 * @param storeUrl String
	 * @return Unit
	 */
	fun setStoreUrl(storeUrl: String)
	
	/**
	 * Sets the bundle name of the app from the publisher to be sent to the auction server
	 * @return Unit
	 */
	fun setBundleName()
	
	/**
	 * Targeting keywords to give information ABOUT THE APP CONTENT to the bidders
	 * @param keyWords Set<String>
	 * @return Unit
	 */
	fun addContextKeywords(keyWords: Set<String>)
	
	/**
	 * Returns Targeting data to give information ABOUT THE APP CONTENT to the bidders in the formats the bidder request
	 * @return Map<String, Set<String>>
	 */
	fun getContextDataDictionary(): Map<String, Set<String>>
	
	/**
	 * Updates Targeting data to give information ABOUT THE APP CONTENT to the bidders in the formats the bidder request
	 * @return Map<String, Set<String>>
	 */
	fun updateContextData(key: String, value: Set<String>)
	
	/**
	 * Adds Targeting data to give information ABOUT THE APP CONTENT to the bidders in the formats the bidder request
	 * @return Map<String, Set<String>>
	 */
	fun addContextData(key: String, value: String)
	
	/**
	 * adds a bidder that can access Context Data, First Party Data, Keywords
	 * @param bidder String
	 * @return Unit
	 */
	fun addBidderToAccessControlList(bidder: String)
	
	// TODO remove this
	/**
	 * Sets any extra configurations for privacy that we might need
	 * @return Unit
	 */
	fun setPrivacyConfig()
}