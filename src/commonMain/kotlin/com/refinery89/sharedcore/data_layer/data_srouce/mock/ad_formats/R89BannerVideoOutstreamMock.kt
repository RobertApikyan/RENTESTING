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
internal class R89BannerVideoOutstreamMock
{
	companion object
	{
		const val bannerOutstreamR89configId = "300-250-generic-demo-outstream-banner"
		const val bannerOutstreamUnitId = "/21808260008/prebid-demo-original-api-video-banner"
		const val bannerOutstreamConfigId = "prebid-demo-video-outstream-original-api"
		const val bannerOutstreamWidth = 300
		const val bannerOutstreamHeight = 250
		private val wrapperStyleScheme =
			WrapperStyleScheme(WrapperPosition.CENTER, matchParentHeight = false, matchParentWidth = false, wrapContent = true, height = -1, width = -1)
		
		fun getR89BannerVideoOutstreamMockData(): AdUnitConfigScheme
		{
			val bannerOutstreamFormat = Formats.VIDEO_OUTSTREAM_BANNER
			//TODO change mutableMapOf for mapOf
			val testBannerOutstreamDataMap = mutableMapOf<String, Any>(
				"width" to bannerOutstreamWidth, "height" to bannerOutstreamHeight,
				"closeButtonIsActive" to false,
				"closeButtonImageURL" to "https://cdn-icons-png.flaticon.com/512/106/106830.png",
				"isLabelEnabled" to false
			)
			
			val testFrequencyCap = FrequencyCapScheme(
				false, 0, 0
			)
			
			return AdUnitConfigScheme(
				bannerOutstreamR89configId,
				bannerOutstreamUnitId,
				bannerOutstreamConfigId,
				bannerOutstreamFormat,
				30000,
				testFrequencyCap,
				null,
				null,
				testBannerOutstreamDataMap.toJsonObject(),
				wrapperStyleScheme
			)
		}
	}
}