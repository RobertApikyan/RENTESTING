package com.refinery89.sharedcore.domain_layer.config.targetConfig

import com.refinery89.sharedcore.domain_layer.internalToolBox.extensions.getTypeName
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.target_config.TargetConfigAppData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.PrebidLibrary

/**
 * This class receives Publisher Data object and configures prebid and sdk
 * parameters related with:
 * - Inventory/Context global Keywords
 * - Inventory/Context global First Party Data
 * - publisher's name, domain, url, bundle
 */
internal class TargetAppConfigurator(
	private val prebidLibrary: PrebidLibrary,
)
{
	/**
	 * @suppress
	 */
	init
	{
		setBundleName()
	}
	
	/** The name of the publisher should be used to target ads from R89 server. */
	private fun setPublisherName(publisherName: String)
	{
		prebidLibrary.setPublisherName(publisherName)
	}
	
	/**
	 * if you have a domain then this is used to target your ads to that
	 * domain, **example for parameter: your-web.com**
	 */
	private fun setDomain(domain: String?)
	{
		prebidLibrary.setDomain(domain)
	}
	
	/**
	 * If you have a store then this is used to target your ads to that store,
	 * **example for parameter: https://www.youwb.org**
	 */
	private fun setStoreUrl(storeUrl: String)
	{
		prebidLibrary.setStoreUrl(storeUrl)
	}
	
	/**
	 * This is used to catch and set the current bundle name, if it is not set
	 * it just sets it to the context bundle (app bundle name)
	 */
	private fun setBundleName()
	{
		prebidLibrary.setBundleName()
	}
	
	/**
	 * This app context keywords R89 sends it to its servers so bidders bid
	 * higher for keywords that match theirs needs.
	 */
	private fun addGlobalContextKeyWords(keyWords: Set<String>?)
	{
		keyWords?.let {
			prebidLibrary.addContextKeywords(it)
		}
	}
	
	/**
	 * fp = first party inventory = units in this app fpid = first party
	 * inventory data Inventory is also known as context in prebid documentation
	 * This is used so bidders can bid higher when
	 * data that is relevant to them is present
	 *
	 * @throws IllegalArgumentException if [fpidMap] is empty or if the
	 *     [Set<String>] is empty, keys of the map and string inside set can be
	 *     blank
	 */
	private fun addGlobalFPInventoryData(fpidMap: Map<String, Set<String>>)
	{
		if (fpidMap.isEmpty())
		{
			R89LogUtil.e(TAG, "map is empty in setGlobalFPInventoryData", false)
			return
		}
		
		var keyIsInRegistered: Boolean
		var keyHasTheSameData: Boolean
		
		fpidMap.forEach { (key, newDataSet) ->
			//Throw error indicating type of the newDataSet and which key threw the error
			if (newDataSet.isEmpty())
			{
				R89LogUtil.e(
					TAG,
					"value of type ${newDataSet.getTypeName()} is size is 0 for map key: $key",
					false
				)
				return@forEach
			}
			
			val dictionary = prebidLibrary.getContextDataDictionary()
			keyIsInRegistered = dictionary.containsKey(key)
			
			if (keyIsInRegistered) //if the key exists
			{
				keyHasTheSameData = dictionary.getValue(key).containsAll(newDataSet)
				
				if (!keyHasTheSameData) //if key has different set, different = more, less, changed values inside the set<string>
				{
					prebidLibrary.updateContextData(key, newDataSet)
				}
			} else //if the key does not exist then add it to the dictionary with all the data
			{
				newDataSet.forEach { data ->
					prebidLibrary.addContextData(key, data)
				}
			}
		}
	}
	
	/**
	 * This data is either in global inventory data, user fp data, or specific
	 * unit inventory fp data.
	 */
	private fun addBidderToFPAccessControlList(bidder: String)
	{
		prebidLibrary.addBidderToAccessControlList(bidder)
	}
	
	internal fun provideConfiguration(data: TargetConfigAppData, name: String)
	{
		setPublisherName(name)
		setDomain(data.domain)
		setStoreUrl(data.storeUrl)
		addGlobalContextKeyWords(data.appInventoryKeywords?.toSet())
		
		data.bidderAccessControlList?.let { bidderAccessControlList ->
			bidderAccessControlList.forEach { bidder ->
				addBidderToFPAccessControlList(bidder)
			}
		}
		data.globalFPInventoryData?.let { globalInventoryData ->
			addGlobalFPInventoryData(globalInventoryData)
		}
	}
	
	/**
	 * @suppress
	 */
	companion object
	{
		private const val TAG = "TargetApp"
	}
}