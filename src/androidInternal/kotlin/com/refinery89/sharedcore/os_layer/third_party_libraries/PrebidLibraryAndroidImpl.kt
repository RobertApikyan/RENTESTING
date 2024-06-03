package com.refinery89.sharedcore.os_layer.third_party_libraries

import android.app.Application
import android.preference.PreferenceManager
import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.domain_layer.models.prebid_server.PrebidServerConfigData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.PrebidLibrary
import org.prebid.mobile.Host
import org.prebid.mobile.PrebidMobile
import org.prebid.mobile.TargetingParams

//TODO we could Unify this along with the [PrebidLibraryAndroidImpl] into a single class that implements both interfaces or a new interface called [monetization library]
/**
 * Uses The OS Prebid Library to comply with [PrebidLibrary] interface contract
 * @property app Application
 */
internal class PrebidLibraryAndroidImpl(private val app: Application) : PrebidLibrary
{
	/**
	 * Calls all the functions needed to configure the Prebid SDK coming from the prebid documentation
	 * @return Unit
	 */
	override fun configurePrebid()
	{
		PrebidMobile.setPbsDebug(R89SDKCommon.isDebug)
		
		PrebidMobile.setIncludeWinnersFlag(true)
		PrebidMobile.setIncludeBidderKeysFlag(false)
		
		PrebidMobile.setApplicationContext(app)
		
		//native assets can have ids
		PrebidMobile.assignNativeAssetID(true)
	}
	
	/**
	 * Configures the Prebid Server
	 * @param prebidServerConfigData PrebidServerConfigData
	 * @return Unit
	 */
	override fun configurePrebidServer(prebidServerConfigData: PrebidServerConfigData)
	{
		PrebidMobile.setPrebidServerAccountId(prebidServerConfigData.accountId)
		PrebidMobile.setPrebidServerHost(Host.createCustomHost(prebidServerConfigData.host))
		PrebidMobile.setTimeoutMillis(prebidServerConfigData.serverTimeout)
	}
	
	/**
	 * @suppress
	 */
	override fun setPublisherName(publisherName: String)
	{
		TargetingParams.setPublisherName(publisherName)
	}
	
	/**
	 * @suppress
	 */
	override fun setDomain(domain: String?)
	{
		TargetingParams.setDomain(domain)
	}
	
	/**
	 * @suppress
	 */
	override fun setStoreUrl(storeUrl: String)
	{
		TargetingParams.setStoreUrl(storeUrl)
	}
	
	/**
	 * @suppress
	 */
	override fun setBundleName()
	{
		TargetingParams.setBundleName(TargetingParams.getBundleName())
	}
	
	/**
	 * @suppress
	 */
	override fun addContextKeywords(keyWords: Set<String>)
	{
		TargetingParams.addContextKeywords(keyWords)
	}
	
	/**
	 * @suppress
	 */
	override fun getContextDataDictionary(): Map<String, Set<String>>
	{
		return TargetingParams.getContextDataDictionary()
	}
	
	/**
	 * @suppress
	 */
	override fun updateContextData(key: String, value: Set<String>)
	{
		TargetingParams.updateContextData(key, value)
	}
	
	/**
	 * @suppress
	 */
	override fun addContextData(key: String, value: String)
	{
		TargetingParams.addContextData(key, value)
	}
	
	/**
	 * @suppress
	 */
	override fun addBidderToAccessControlList(bidder: String)
	{
		TargetingParams.addBidderToAccessControlList(bidder)
	}
	
	
	//TODO: Remove Hardcoded values
	//TODO: Add all remaining consent values to corresponding users GAM or Prebid
	/**
	 * @suppress
	 */
	override fun setPrivacyConfig()
	{
		PrebidMobile.setShareGeoLocation(true)
		
		//TODO this shouldn't exist
		if (R89SDKCommon.isDebug && !R89SDKCommon.usingCMP)
		{
			PreferenceManager.getDefaultSharedPreferences(PrebidMobile.getApplicationContext()).edit()
				.apply {
					putInt("IABTCF_gdprApplies", 0)
					putInt("IABTCF_CmpSdkID", 123)
					apply()
				}
		}
	}
}