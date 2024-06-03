package com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats

import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.frequency_cap.FrequencyCapScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.unit_config.AdUnitConfigScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.wrapper_style.WrapperStyleScheme
import com.refinery89.sharedcore.data_layer.schemes_models.toJsonObject
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.wrapper_features.WrapperPosition

/**
 * @suppress
 *
 */
internal class R89BannerMock
{
	
	companion object
	{
		
		const val bannerR89configId = "320-50-generic-demo-display-banner"
		const val bannerUnitId = "/21808260008/prebid_demo_app_original_api_banner"
		const val bannerConfigId = "prebid-demo-banner-320-50"
		const val bannerWidth = 320
		const val bannerHeight = 50
		private val wrapperStyleScheme =
			WrapperStyleScheme(WrapperPosition.CENTER, matchParentHeight = false, matchParentWidth = false, wrapContent = true, height = -1, width = -1)
		
		fun getR89BannerMockData(): AdUnitConfigScheme
		{
			val bannerFormat = Formats.BANNER
			
			val testInventoryKeywords = listOf("keyword1", "keyword2", "keyword3")
			
			val testUnitFPID = mapOf(
				"cookieKey1" to setOf("cookieData1", "cookieData2"),
				"cookieKey2" to setOf("cookieData1", "cookieData2")
			)
			
			//TODO change mutableMapOf for mapOf
			val testBannerDataMap = mutableMapOf(
				"sizes" to
						listOf(mapOf("width" to bannerWidth, "height" to bannerHeight)),
				"closeButtonIsActive" to
						false,
//				"closeButtonImageURL" to
//						"https://cdn-icons-png.flaticon.com/512/106/106830.png",
				"closeButtonImageURL" to
						"",
				"isLabelEnabled" to
						false
			)
			
			val testFrequencyCap = FrequencyCapScheme(
				false, 2, 90000
			)
			
			return AdUnitConfigScheme(
				bannerR89configId,
				bannerUnitId, bannerConfigId, bannerFormat,
				30000,
				testFrequencyCap,
				null,
				null,
				testBannerDataMap.toJsonObject(),
				wrapperStyleScheme
			)
		}
	}
}