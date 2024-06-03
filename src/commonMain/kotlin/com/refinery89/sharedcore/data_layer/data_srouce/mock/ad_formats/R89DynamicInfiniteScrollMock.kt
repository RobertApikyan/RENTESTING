package com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats

import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.frequency_cap.FrequencyCapScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.unit_config.AdUnitConfigScheme
import com.refinery89.sharedcore.data_layer.schemes_models.toJsonObject
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.config.ConfigBuilder

/**
 * @suppress
 *
 */
internal class R89DynamicInfiniteScrollMock
{
	
	/**
	 * @suppress
	 *
	 */
	companion object
	{
		
		const val infiniteScrollR89configId = "display-outstream-demo-infinite-scroll"
		
		private const val minItems = 3
		private const val maxItems = 10
		val variableProbability: IntRange = -5..20
		
		fun getR89InfiniteScrollMockData(): AdUnitConfigScheme
		{
			val infiniteScrollFormat = Formats.INFINITE_SCROLL
			
			//TODO change mutableMapOf for mapOf
			val testBannerDataMap = mutableMapOf<String, Any>(
				"minItems" to minItems,
				"maxItems" to maxItems,
				"variableProbabilityMin" to variableProbability.first,
				"variableProbabilityMax" to variableProbability.last,
				"configsOfAdsToUse" to listOf(ConfigBuilder.BANNER_TEST_R89_CONFIG_ID, ConfigBuilder.VIDEO_OUTSTREAM_TEST_R89_CONFIG_ID)
			)
			
			val testFrequencyCap = FrequencyCapScheme(
				false, 0, 0
			)
			
			return AdUnitConfigScheme(
				infiniteScrollR89configId,
				"123", "123", infiniteScrollFormat,
				null, testFrequencyCap, null, null, testBannerDataMap.toJsonObject(), null
			)
		}
	}
}