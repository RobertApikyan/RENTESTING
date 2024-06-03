package com.refinery89.sharedcore.domain_layer.config

import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.domain_layer.config.consentConfig.UserPrivacyConfigurator
import com.refinery89.sharedcore.domain_layer.config.prebid.PrebidConfigurator
import com.refinery89.sharedcore.domain_layer.config.targetConfig.TargetAppConfigurator
import com.refinery89.sharedcore.domain_layer.config.targetConfig.TargetUserConfigurator
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.PublisherData
import com.refinery89.sharedcore.domain_layer.models.unit_config.AdUnitConfigData
import com.refinery89.sharedcore.domain_layer.singleTag.SingleTagConfigurator
import com.refinery89.sharedcore.domain_layer.third_party_libraries.GoogleAdsLibrary


/**
 * This Class is responsible for:
 * - storing all the configurations that are provided by the [publisherData]
 * - point of access to those configurations
 * - It's also responsible for providing with secure and controlled access to all those config APIs
 *
 * @property prebidConfigurator Everything related to configuring basic prebid functionality
 * @property userPrivacyConfigurator Everything related to user privacy and consent
 * @property targetAppConfigurator Everything related to Targeting the ads from an App content Perspective
 * @property googleAdsLibrary Everything related to configuring basic google ads functionality
 * @property singleTagConfigurator Everything related to making single tag work
 * @property targetUserConfigurator Everything related to Targeting the ads from an User Perspective (not implemented yet)
 * @property publisherData data that is provided to all configurations.
 */
internal class R89GlobalConfigurator internal constructor(
	private val prebidConfigurator: PrebidConfigurator,
	private val userPrivacyConfigurator: UserPrivacyConfigurator,
	private val targetAppConfigurator: TargetAppConfigurator,
	private val googleAdsLibrary: GoogleAdsLibrary,
	private val singleTagConfigurator: SingleTagConfigurator,
)
{
	
	//TODO make this available through a public API
	private val targetUserConfigurator: TargetUserConfigurator = TargetUserConfigurator()
	
	private var publisherData: PublisherData? = null
	
	/**
	 * This function is responsible for initializing all the things that doesn't need data from the Refinery89 DB
	 * @return Unit
	 */
	fun prePublisherDataConfigurations()
	{
		googleAdsLibrary.initializeGoogleAds()
		
		if (R89SDKCommon.isSingleLineOn)
		{
			singleTagConfigurator.initializeSingleTag()
		}
	}
	
	/**
	 * This function is responsible for storing the [data] that is provided by the publisher, this is called after
	 * the API or the ConfigBuilder responds
	 * @param data PublisherData
	 * @return Unit
	 */
	internal fun postPublisherDataConfigurations(data: PublisherData)
	{
		this.publisherData = data
	}
	
	/**
	 * This is to show the CMP to the user must be called after we know the CMPData from the Refinery89 DB
	 * @return Unit
	 */
	fun startCMP()
	{
		if (publisherData == null)
		{
			R89LogUtil.e(
				"R89GlobalConfigurator",
				"startCMP called before postPublisherDataConfigurations",
				true
			)
			throw Exception("Publisher data is not available")
		} else
		{
			userPrivacyConfigurator.initializeAndShowCMP(publisherData!!.cmpData)
		}
	}
	
	/**
	 * applies the publisher configuration to Prebid and fills unit config repository
	 */
	fun applyPublisherConfiguration()
	{
		publisherData?.let { notNullData ->
			prebidConfigurator.configurePrebid(notNullData.prebidServerConfigData)
			//feed publisher data to other sdk managers
			applyTargetAppConfig(notNullData)
			
			if (R89SDKCommon.isSingleLineOn)
			{
				applySingleTagConfig(notNullData)
			}
			
			R89LogUtil.setSendAllLogs(notNullData.sendAllLogs)
		} ?: run {
			R89LogUtil.e(
				"R89GlobalConfigurator",
				"applyPublisherConfiguration called before postPublisherDataConfigurations",
				true
			)
			throw Exception("Publisher data is not available")
		}
	}
	
	private fun applyTargetAppConfig(notNullData: PublisherData)
	{
		val targetConfigAppData = notNullData.targetConfigAppData
		val nameResult = notNullData.name
		
		targetAppConfigurator.provideConfiguration(targetConfigAppData, nameResult)
	}
	
	private fun applySingleTagConfig(notNullData: PublisherData)
	{
		singleTagConfigurator.provideConfiguration(notNullData.singleTagData!!)
	}
	
	/**
	 * Returns a configuration or a failure if the configuration is not found
	 * @param configurationId String
	 * @return Result<AdUnitConfigData>
	 */
	internal fun getConfig(configurationId: String): Result<AdUnitConfigData>
	{
		return try
		{
			publisherData?.let { notNullData ->
				val config = notNullData.unitConfigRepo.getConfigOrThrow(configurationId)
				Result.success(config)
			} ?: run {
				throw Exception("Publisher data is not available")
			}
		} catch (e: Throwable)
		{
			Result.failure(e)
		}
	}
	
	/**
	 * access to the CMP tool
	 * @return UserPrivacyConfigurator
	 */
	internal fun getUserPrivacyTool(): UserPrivacyConfigurator
	{
		return userPrivacyConfigurator
	}
	
	/**
	 * Sends a web-view to the Google Ads Library to be configured as explained in it's documentation
	 * @param webView Any?
	 * @return Unit
	 */
	fun configureWebViewForGoogleLibrary(webView: Any?)
	{
		googleAdsLibrary.configureWebViewForGoogleLibrary(webView)
	}
}