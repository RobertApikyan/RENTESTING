package com.refinery89.sharedcore.os_layer.third_party_library

import cocoapods.PrebidMobile.Prebid
import cocoapods.PrebidMobile.Targeting
import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.domain_layer.models.prebid_server.PrebidServerConfigData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.PrebidLibrary
import com.refinery89.sharedcore.os_layer.extensions.toStringSetMap
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
internal class PrebidLibraryIosImpl : PrebidLibrary
{
	override fun configurePrebid()
	{
		Prebid.shared().setPbsDebug(R89SDKCommon.isDebug)
		Prebid.shared().setIncludeWinners(true)
		Prebid.shared().setIncludeBidderKeys(false)
		
		//native assets can have ids
		Prebid.shared().setShouldAssignNativeAssetID(true)
		//TODO(@Apikyan):Check why we are not calling Prebid.initializeSDK
		Prebid.initializeSDK { l, nsError ->  }
	}
	
	override fun configurePrebidServer(prebidServerConfigData: PrebidServerConfigData)
	{
		Prebid.shared().setPrebidServerAccountId(prebidServerConfigData.accountId)
		Prebid.shared().setCustomPrebidServerWithUrl(prebidServerConfigData.host,error = null)
		Prebid.shared().setTimeoutMillis(prebidServerConfigData.serverTimeout.toLong())
	}
	
	override fun setPublisherName(publisherName: String)
	{
		Targeting.shared().setPublisherName(publisherName)
	}
	
	override fun setDomain(domain: String?)
	{
		Targeting.shared().setDomain(domain)
	}
	
	override fun setStoreUrl(storeUrl: String)
	{
		Targeting.shared().setStoreURL(storeUrl)
	}
	
	override fun setBundleName()
	{
		//TODO(@Apikyan):Define
		Targeting.shared().setItunesID(Targeting.shared().itunesID())
		Targeting.shared().setStoreURL(Targeting.shared().storeURL())
	}
	
	override fun addContextKeywords(keyWords: Set<String>)
	{
		Targeting.shared().addContextKeywords(keyWords)
	}
	
	override fun getContextDataDictionary(): Map<String, Set<String>>
	{
		return Targeting.shared().getContextData().toStringSetMap()
	}
	
	override fun updateContextData(key: String, value: Set<String>)
	{
		Targeting.shared().updateContextDataWithKey(key,value)
	}
	
	override fun addContextData(key: String, value: String)
	{
		Targeting.shared().addContextDataWithKey(key,value)
	}
	
	override fun addBidderToAccessControlList(bidder: String)
	{
		Targeting.shared().addBidderToAccessControlList(bidder)
	}
	
	override fun setPrivacyConfig()
	{
//		Prebid.shared().setShareGeoLocation(true)
		
		//TODO(@Apikyan): TBD
		//TODO this shouldn't exist
		if (R89SDKCommon.isDebug && !R89SDKCommon.usingCMP)
		{
//			PreferenceManager.getDefaultSharedPreferences(PrebidMobile.getApplicationContext()).edit()
//				.apply {
//					putInt("IABTCF_gdprApplies", 0)
//					putInt("IABTCF_CmpSdkID", 123)
//					apply()
//				}
		}
	}
}