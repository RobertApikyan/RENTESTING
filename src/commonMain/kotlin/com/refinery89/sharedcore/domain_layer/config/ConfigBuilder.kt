package com.refinery89.sharedcore.domain_layer.config

import R___Mobile_Native_Library.sharedCore.BuildConfig
import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats.R89BannerMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats.R89BannerVideoOutstreamMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats.R89DynamicInfiniteScrollMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats.R89InterstitialMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats.R89RewardedInterstitialMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats.R89VideoInterstitialMock
import com.refinery89.sharedcore.data_layer.data_srouce.mock.config.UserPrivacyConfigMock
import com.refinery89.sharedcore.data_layer.schemes_models.toJsonObject
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89AdSize
import com.refinery89.sharedcore.domain_layer.config.unitConfig.AdUnitConfigRepository
import com.refinery89.sharedcore.domain_layer.models.PublisherData
import com.refinery89.sharedcore.domain_layer.models.consent_config.CMPData
import com.refinery89.sharedcore.domain_layer.models.frequency_cap.FrequencyCapData
import com.refinery89.sharedcore.domain_layer.models.prebid_server.PrebidServerConfigData
import com.refinery89.sharedcore.domain_layer.models.target_config.TargetConfigAppData
import com.refinery89.sharedcore.domain_layer.models.unit_config.AdUnitConfigData
import com.refinery89.sharedcore.domain_layer.models.wrapper_style.WrapperStyleData
import com.refinery89.sharedcore.domain_layer.wrapper_features.WrapperPosition

/**
 * Builder in charge of making easy to configure the SDK. All the methods are optional to call.
 * This Class can be Used either when manualConfig is ON or OFF.
 * If its off, only Unfetchable configs will be built into the configuration:
 * This configurations are:
 * - consent configurations.
 *
 * If its ON you can add unitConfigs and APP
 *   global target Configs.
 */
class ConfigBuilder(private val publisherName: String)
{
	
	private val unitConfigList: MutableList<AdUnitConfigData> = mutableListOf()
	private lateinit var targetConfigAppScheme: TargetConfigAppData
	private lateinit var cmpScheme: CMPData
	private lateinit var prebidServerConfigData: PrebidServerConfigData
	
	/** Applies the configuration to the SDK. */
	internal fun build(): PublisherData
	{
		val repo = AdUnitConfigRepository(unitConfigList)
		return PublisherData(
			R89SDKCommon.sdkPublisherId,
			R89SDKCommon.sdkAppId,
			publisherName,
			repo,
			targetConfigAppScheme,
			null,
			cmpScheme,
			false,
			prebidServerConfigData
		)
	}
	
	/**
	 * Adds a banner configuration to the sdk
	 *
	 * @param configurationId Configuration ID
	 * @param bannerUnitId unit id from the manager
	 * @param bannerConfigId config id from the manager
	 * @param sizeList list of pair indicating width and height
	 * @param autoRefreshMillis Ad auto refresh timer (default = 30000 millis / 30 seconds)
	 * @param isCapped If ad is capped (default = false)
	 * @param adAmountPerTimeSlot Capping amount of ads per time slot (default = 1)
	 * @param timeSlotSize Size of the time slot (default = 3600000 millis / 1 hour)
	 * @param keywords this is for adding specific keywords to the unit
	 * @param unitFPID this is for adding specific first party data to the unit
	 * @param closeButtonIsActive Activate ad close button (default = false)
	 * @param closeButtonImageURL Url of custom icon for ad close button
	 * @param wrapperWrapContent Wrap r89Wrapper around the content (ad)
	 * @param wrapperMatchParentHeight Match r89Wrapper height with publishers wrapper height
	 * @param wrapperMatchParentWidth Match r89Wrapper width with publishers wrapper width
	 * @param wrapperHeight Change r89Wrapper's height in dp
	 * @param wrapperWidth Change r89Wrapper's width in dp
	 * @param wrapperPosition Change r89Wrapper's position inside of publishers wrapper WrapperPosition.(CENTER/START/END/TOP/BOTTOM)
	 * @return ConfigBuilder after caching this configuration
	 */
	fun addBannerConfiguration(
		configurationId: String,
		bannerUnitId: String,
		bannerConfigId: String,
		sizeList: List<Pair<Int, Int>>,
		autoRefreshMillis: Int = 30000,
		isCapped: Boolean = false,
		adAmountPerTimeSlot: Int = 1,
		timeSlotSize: Long = 3600000,
		keywords: List<String>? = null,
		unitFPID: Map<String, Set<String>>? = null,
		closeButtonIsActive: Boolean = false,
		closeButtonImageURL: String = "",
		wrapperWrapContent: Boolean = true,
		wrapperMatchParentHeight: Boolean = false,
		wrapperMatchParentWidth: Boolean = false,
		wrapperHeight: Int = -1,
		wrapperWidth: Int = -1,
		wrapperPosition: WrapperPosition = WrapperPosition.CENTER,
	): ConfigBuilder
	{
		val bannerFormat = Formats.BANNER
		
		val r89SizeList = sizeList.map {
			R89AdSize(it.first, it.second)
		}
		val wrapperStyleData = WrapperStyleData(
			wrapperPosition,
			wrapperMatchParentHeight,
			wrapperMatchParentWidth,
			wrapperWrapContent,
			wrapperHeight,
			wrapperWidth
		)
		
		val testBannerDataMap = mapOf<String, Any>(
			"sizes" to r89SizeList,
			"closeButtonIsActive" to closeButtonIsActive,
			"closeButtonImageURL" to closeButtonImageURL
		)
		
		val testFrequencyCap = FrequencyCapData(
			isCapped, adAmountPerTimeSlot, timeSlotSize
		)
		
		val testBannerConfig = AdUnitConfigData(
			configurationId,
			bannerUnitId,
			bannerConfigId,
			bannerFormat,
			autoRefreshMillis,
			testFrequencyCap,
			keywords,
			unitFPID,
			testBannerDataMap.toJsonObject(),
			wrapperStyleData
		)
		unitConfigList.add(testBannerConfig)
		
		return this
	}
	
	
	/**
	 * Adds a video banner configuration to the sdk
	 *
	 * @param configurationId Configuration ID
	 * @param outstreamUnitId Unit id from the manager
	 * @param outstreamConfigId Config id from the manager
	 * @param size Size of the video outstream banner in px
	 * @param autoRefreshMillis Ad auto refresh timer (default = 30000 millis / 30 seconds)
	 * @param isCapped If ad is capped (default = false)
	 * @param adAmountPerTimeSlot Capping amount of ads per time slot (default = 1)
	 * @param timeSlotSize Size of the time slot (default = 3600000 millis / 1 hour)
	 * @param keywords Advertisement keywords for better targeting
	 * @param unitFPID This is for adding specific first party data to the unit
	 * @param closeButtonIsActive Activate ad close button (default = false)
	 * @param closeButtonImageURL Url of custom icon for ad close button
	 * @param wrapperWrapContent Wrap r89Wrapper around the content (ad)
	 * @param wrapperMatchParentHeight Match r89Wrapper height with publishers wrapper height
	 * @param wrapperMatchParentWidth Match r89Wrapper width with publishers wrapper width
	 * @param wrapperHeight Change r89Wrapper's height in dp
	 * @param wrapperWidth Change r89Wrapper's width in dp
	 * @param wrapperPosition Change r89Wrapper's position inside of publishers wrapper WrapperPosition.(CENTER/START/END/TOP/BOTTOM)
	 * @return ConfigBuilder after caching this configuration
	 */
	fun addVideoOutstreamBannerConfiguration(
		configurationId: String,
		outstreamUnitId: String,
		outstreamConfigId: String,
		size: Pair<Int, Int>,
		autoRefreshMillis: Int = 30000,
		isCapped: Boolean = false,
		adAmountPerTimeSlot: Int = 1,
		timeSlotSize: Long = 3600000,
		keywords: List<String>? = null,
		unitFPID: Map<String, Set<String>>? = null,
		closeButtonIsActive: Boolean = false,
		closeButtonImageURL: String? = null,
		wrapperWrapContent: Boolean = true,
		wrapperMatchParentHeight: Boolean = false,
		wrapperMatchParentWidth: Boolean = false,
		wrapperHeight: Int = -1,
		wrapperWidth: Int = -1,
		wrapperPosition: WrapperPosition = WrapperPosition.CENTER,
	): ConfigBuilder
	{
		val format = Formats.VIDEO_OUTSTREAM_BANNER

//		val r89SizeList = R89AdSize(size.first, size.second)
		
		val wrapperStyleData = WrapperStyleData(
			wrapperPosition,
			wrapperMatchParentHeight,
			wrapperMatchParentWidth,
			wrapperWrapContent,
			wrapperHeight,
			wrapperWidth
		)
		
		
		val dataMap =
			mapOf<String, Any>(
				"width" to size.first,
				"height" to size.second,
				"closeButtonIsActive" to closeButtonIsActive,
				"closeButtonImageURL" to (closeButtonImageURL ?: "")
			)
		
		val testFrequencyCap = FrequencyCapData(
			isCapped, adAmountPerTimeSlot, timeSlotSize
		)
		
		val outstreamConfiguration = AdUnitConfigData(
			configurationId,
			outstreamUnitId, outstreamConfigId, format,
			autoRefreshMillis,
			testFrequencyCap,
			keywords,
			unitFPID,
			dataMap.toJsonObject(),
			wrapperStyleData
		)
		
		unitConfigList.add(outstreamConfiguration)
		
		return this
	}
	
	/**
	 * Adds a infinite scroll configuration to the sdk
	 *
	 * @param configurationId Configuration ID
	 * @param minItems Minimum number of items in the row that should be ad-free after last item with an ad shown
	 * @param maxItems Maximum number of items in the row that must contain at least one ad
	 * @param variableProbability Probability of an item containing an ad
	 * @param configsOfAdsToUse List if configurations of ads which should be used in this infinite scroll
	 * @param wrapperWrapContent Wrap r89Wrapper around the content (ad)
	 * @param wrapperMatchParentHeight Match r89Wrapper height with publishers wrapper height
	 * @param wrapperMatchParentWidth Match r89Wrapper width with publishers wrapper width
	 * @param wrapperHeight Change r89Wrapper's height in dp
	 * @param wrapperWidth Change r89Wrapper's width in dp
	 * @param wrapperPosition Change r89Wrapper's position inside of publishers wrapper WrapperPosition.(CENTER/START/END/TOP/BOTTOM)
	 * @return ConfigBuilder after caching this configuration
	 */
	fun addInfiniteScrollConfiguration(
		configurationId: String,
		minItems: Int,
		maxItems: Int,
		variableProbability: IntRange = 0..0,
		configsOfAdsToUse: List<String>,
		wrapperWrapContent: Boolean = true,
		wrapperMatchParentHeight: Boolean = false,
		wrapperMatchParentWidth: Boolean = false,
		wrapperHeight: Int = -1,
		wrapperWidth: Int = -1,
		wrapperPosition: WrapperPosition = WrapperPosition.CENTER,
	): ConfigBuilder
	{
		val format = Formats.INFINITE_SCROLL
		
		val wrapperStyleData = WrapperStyleData(
			wrapperPosition,
			wrapperMatchParentHeight,
			wrapperMatchParentWidth,
			wrapperWrapContent,
			wrapperHeight,
			wrapperWidth
		)
		
		
		val dataMap = mapOf<String, Any>(
			"minItems" to minItems,
			"maxItems" to maxItems,
			"variableProbabilityMin" to variableProbability.first,
			"variableProbabilityMax" to variableProbability.last,
			"configsOfAdsToUse" to configsOfAdsToUse
		)
		
		val testFrequencyCap = FrequencyCapData(
			false, 0, 0
		)
		
		val infiniteScrollConfiguration = AdUnitConfigData(
			configurationId,
			"",
			"",
			format,
			30000,
			testFrequencyCap,
			null,
			null,
			dataMap.toJsonObject(),
			wrapperStyleData
		)
		
		unitConfigList.add(infiniteScrollConfiguration)
		
		return this
		
	}
	
	
	/**
	 * Add an interstitial configuration to the sdk
	 *
	 * @param configurationId Configuration ID
	 * @param interstitialUnitId unit id from the manager
	 * @param interstitialConfigId config id from the manager
	 * @param isCapped If ad is capped (default = false)
	 * @param adAmountPerTimeSlot Capping amount of ads per time slot (default = 1)
	 * @param timeSlotSize Size of the time slot (default = 3600000 millis / 1 hour)
	 * @param keywords this is for adding specific keywords to the unit
	 * @param unitFPID this is for adding specific first party data to the unit
	 * @return ConfigBuilder after caching this configuration
	 */
	fun addInterstitialConfiguration(
		configurationId: String,
		interstitialUnitId: String,
		interstitialConfigId: String,
		isCapped: Boolean = false,
		adAmountPerTimeSlot: Int = 1,
		timeSlotSize: Long = 3600000,
		keywords: List<String>? = null,
		unitFPID: Map<String, Set<String>>? = null,
	): ConfigBuilder
	{
		
		val format = Formats.INTERSTITIAL
		
		
		val testFrequencyCap = FrequencyCapData(
			isCapped, adAmountPerTimeSlot, timeSlotSize
		)
		
		val testInterstitialConfig =
			AdUnitConfigData(
				configurationId,
				interstitialUnitId, interstitialConfigId, format,
				-1, testFrequencyCap, keywords, unitFPID, null, null
			)
		
		unitConfigList.add(testInterstitialConfig)
		return this
	}
	
	/**
	 * Add an video interstitial configuration to the sdk
	 *
	 * @param isCapped If ad is capped (default = false)
	 * @param adAmountPerTimeSlot Capping amount of ads per time slot (default = 1)
	 * @param timeSlotSize Size of the time slot (default = 3600000 millis / 1 hour)
	 * @param keywords this is for adding specific keywords to the unit
	 * @param unitFPID this is for adding specific first party data to the unit
	 * @return ConfigBuilder after caching this configuration
	 */
	fun addVideoInterstitialConfiguration(
		configurationId: String,
		videoInterstitialUnitId: String,
		videoInterstitialConfigId: String,
		isCapped: Boolean = false,
		adAmountPerTimeSlot: Int = 1,
		timeSlotSize: Long = 3600000,
		keywords: List<String>? = null,
		unitFPID: Map<String, Set<String>>? = null,
	): ConfigBuilder
	{
		
		val interstitialFormat = Formats.VIDEO_OUTSTREAM_INTERSTITIAL
		
		val testFrequencyCap = FrequencyCapData(
			isCapped, adAmountPerTimeSlot, timeSlotSize
		)
		
		val testInterstitialConfig =
			AdUnitConfigData(
				configurationId,
				videoInterstitialUnitId, videoInterstitialConfigId, interstitialFormat,
				-1, testFrequencyCap, keywords, unitFPID, null, null
			)
		
		unitConfigList.add(testInterstitialConfig)
		return this
	}
	
	/**
	 * Add a Rewarded interstitial configuration to the sdk
	 *
	 * @param configurationId Configuration ID
	 * @param rewardedInterstitialUnitId unit id from the manager
	 * @param isCapped If ad is capped (default = false)
	 * @param adAmountPerTimeSlot Capping amount of ads per time slot (default = 1)
	 * @param timeSlotSize Size of the time slot (default = 3600000 millis / 1 hour)
	 * @param keywords this is for adding specific keywords to the unit
	 * @param unitFPID this is for adding specific first party data to the unit
	 * @return ConfigBuilder
	 */
	fun addRewardedInterstitialConfiguration(
		configurationId: String,
		rewardedInterstitialUnitId: String,
		isCapped: Boolean = false,
		adAmountPerTimeSlot: Int = 1,
		timeSlotSize: Long = 3600000,
		keywords: List<String>? = null,
		unitFPID: Map<String, Set<String>>? = null,
	): ConfigBuilder
	{
		
		val interstitialFormat = Formats.REWARDED_INTERSTITIAL
		
		
		val testFrequencyCap = FrequencyCapData(
			isCapped, adAmountPerTimeSlot, timeSlotSize
		)
		
		val testInterstitialConfig =
			AdUnitConfigData(
				configurationId,
				rewardedInterstitialUnitId, REWARDED_INTERSTITIAL_TEST_CONFIG_ID, interstitialFormat,
				-1, testFrequencyCap, keywords, unitFPID, null,
				null
			)
		
		unitConfigList.add(testInterstitialConfig)
		return this
	}
	
	/**
	 * Adds keywords for better ad targeting
	 *
	 * @param storeUrl Play store url of publishers application
	 * @param domain Publishers domain
	 * @param appContextKeywords set of keywords for better ad targeting
	 */
	fun addAppTargeting(storeUrl: String, domain: String, appContextKeywords: Set<String>?)
	{
		targetConfigAppScheme = TargetConfigAppData(
			storeUrl = storeUrl,
			domain = domain,
			appInventoryKeywords = appContextKeywords,
			bidderAccessControlList = null,
			globalFPInventoryData = null
		)
	}
	
	/** caches the cmp needed data to be shown, */
	fun addCMPData(id: Int, cmpCodeId: String)
	{
		cmpScheme = CMPData(id, cmpCodeId)
	}
	
	fun addPrebidServerConfig(useRealAuctionServer: Boolean = false): ConfigBuilder
	{
		prebidServerConfigData = if (useRealAuctionServer)
		{
			PrebidServerConfigData(
				BuildConfig.PREBID_SERVER_ACCOUNT_ID,
				PRODUCTION_PREBID_SERVER,
				PREBID_SERVER_TIMEOUT
			)
		} else
		{
			PrebidServerConfigData(TEST_SERVER_ACCOUNT_ID, TEST_SERVER_HOST, PREBID_SERVER_TIMEOUT)
		}
		return this
	}
	
	companion object
	{
		private const val TAG = "ConfigBuilder"
		
		internal const val PREBID_SERVER_TIMEOUT = 30000
		internal const val TEST_SERVER_ACCOUNT_ID = "0689a263-318d-448b-a3d4-b02e8a709d9d"
		internal const val TEST_SERVER_HOST =
			"https://prebid-server-test-j.prebid.org/openrtb2/auction"
		internal const val PRODUCTION_PREBID_SERVER = "https://ib.adnxs.com/openrtb2/prebid"
		
		//This information if from https://github.com/prebid/prebid-mobile-android/blob/d90c622a111e7b055f13aed5b448da4cdba02012/Example/PrebidDemoKotlin/src/main/java/org/prebid/mobile/prebidkotlindemo/AdTypesRepository.kt
		const val BANNER_TEST_R89_CONFIG_ID = R89BannerMock.bannerR89configId
		const val BANNER_TEST_UNIT_ID = R89BannerMock.bannerUnitId
		const val BANNER_TEST_CONFIG_ID = R89BannerMock.bannerConfigId
		const val BANNER_TEST_WIDTH = R89BannerMock.bannerWidth
		const val BANNER_TEST_HEIGHT = R89BannerMock.bannerHeight
		
		const val VIDEO_OUTSTREAM_TEST_R89_CONFIG_ID =
			R89BannerVideoOutstreamMock.bannerOutstreamR89configId
		const val VIDEO_OUTSTREAM_TEST_UNIT_ID = R89BannerVideoOutstreamMock.bannerOutstreamUnitId
		const val VIDEO_OUTSTREAM_TEST_CONFIG_ID =
			R89BannerVideoOutstreamMock.bannerOutstreamConfigId
		const val VIDEO_OUTSTREAM_TEST_WIDTH = R89BannerVideoOutstreamMock.bannerOutstreamWidth
		const val VIDEO_OUTSTREAM_TEST_HEIGHT = R89BannerVideoOutstreamMock.bannerOutstreamHeight
		
		const val INFINITE_SCROLL_TEST_R89_CONFIG_ID =
			R89DynamicInfiniteScrollMock.infiniteScrollR89configId
		val INFINITE_SCROLL_TEST_VARIABLE_PROBABILITY =
			R89DynamicInfiniteScrollMock.variableProbability
		
		const val INTERSTITIAL_TEST_R89_CONFIG_ID = R89InterstitialMock.interstitialR89configId
		const val INTERSTITIAL_TEST_UNIT_ID = R89InterstitialMock.interstitialUnitId
		const val INTERSTITIAL_TEST_CONFIG_ID = R89InterstitialMock.interstitialConfigId
		
		const val VIDEO_INTERSTITIAL_TEST_R89_CONFIG_ID =
			R89VideoInterstitialMock.videoInterstitialR89configId
		const val VIDEO_INTERSTITIAL_TEST_UNIT_ID = R89VideoInterstitialMock.videoInterstitialUnitId
		const val VIDEO_INTERSTITIAL_TEST_CONFIG_ID =
			R89VideoInterstitialMock.videoInterstitialConfigId
		
		const val REWARDED_INTERSTITIAL_TEST_R89_CONFIG_ID = R89RewardedInterstitialMock.rewardedInterstitialR89configId
		const val REWARDED_INTERSTITIAL_TEST_UNIT_ID = R89RewardedInterstitialMock.rewardedInterstitialUnitId
		private const val REWARDED_INTERSTITIAL_TEST_CONFIG_ID = R89RewardedInterstitialMock.rewardedInterstitialConfigId
		
		const val CMP_TEST_ID = UserPrivacyConfigMock.CMP_TEST_ID
		const val CMP_TEST_CODE_ID = UserPrivacyConfigMock.CMP_TEST_CODE_ID
	}
}