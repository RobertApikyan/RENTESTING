package com.refinery89.sharedcore.data_layer.data_srouce.mock

import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.data_layer.data_srouce.PublisherDataSource
import com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats.R89BannerMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats.R89BannerVideoOutstreamMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats.R89DynamicInfiniteScrollMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats.R89InterstitialMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats.R89RewardedInterstitialMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats.R89VideoInterstitialMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.config.PrebidServerConfigMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.config.TargetConfigAppMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.config.UserPrivacyConfigMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.single_tag.SingleTagConfiguratorMock
import com.refinery89.sharedcore.data_layer.schemes_models.GeneralDataScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.PublisherRequestBody
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.PublisherScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag.AdScreenScheme

/**
 * This Data Source is used to retrieve debug Data for the SDK.
 */
internal class ManagerMockData : PublisherDataSource
{
	/**
	 * This function is used to retrieve debug Data for the SDK.
	 * @param body PublisherRequestBody
	 * @param apiVersion String version of the api
	 * @return PublisherScheme
	 */
	override suspend fun getPublisherInfo(
		body: PublisherRequestBody,
		apiVersion: String,
	): GeneralDataScheme<PublisherScheme>
	{
		val testBannerConfig = R89BannerMock.getR89BannerMockData()
		val testBannerOutstreamConfig =
			R89BannerVideoOutstreamMock.getR89BannerVideoOutstreamMockData()
		val testInterstitialConfig = R89InterstitialMock.getR89InterstitialMockData()
		val testVideoInterstitialConfig = R89VideoInterstitialMock.getR89VideoInterstitialMockData()
		val testRewardedInterstitialConfig = R89RewardedInterstitialMock.getR89RewardedInterstitialMockData()
		val testInfiniteScroll = R89DynamicInfiniteScrollMock.getR89InfiniteScrollMockData()
		
		// add all the formats data into a list
		val adUnitConfigurations = listOf(
			testBannerConfig, testInterstitialConfig,
			testBannerOutstreamConfig, testVideoInterstitialConfig, testRewardedInterstitialConfig,
			testInfiniteScroll
		)
		
		///<---- AppContextData ---->///
		val appContextData = TargetConfigAppMock.getTargetConfigAppMockData()
		
		///<---- Single Tag ---->///
		val singleTagData: List<AdScreenScheme>? = if (R89SDKCommon.isSingleLineOn)
		{
			SingleTagConfiguratorMock.getSingleTagConfiguratorMockData()
		} else
		{
			null
		}
		
		///<---- CMP ---->///
		val cmpData = UserPrivacyConfigMock.getUserPrivacyConfigMockData()
		
		///<---- Prebid Server ---->///
		val prebidServerConfig = PrebidServerConfigMock.getPrebidServerConfigMock()
		
		///<---- Publisher Data ---->///
		val publisherId = "TestPublisherID"
		val appId = "TestAppId"
		val name = "TestPublisherName"
		
		return GeneralDataScheme(
			PublisherScheme(
				publisherId,
				appId,
				name,
				adUnitConfigurations,
				appContextData,
				singleTagData,
				cmpData,
				false,
				prebidServerConfig
			)
		)
	}
}