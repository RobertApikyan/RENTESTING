package com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats

import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.frequency_cap.FrequencyCapScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.unit_config.AdUnitConfigScheme
import com.refinery89.sharedcore.domain_layer.adFormats.Formats

/**
 * @suppress
 *
 */
internal class R89VideoInterstitialMock
{
	
	
	companion object
	{
		const val videoInterstitialR89configId = "generic-demo-video-interstitial"
		const val videoInterstitialUnitId = "/21808260008/prebid-demo-app-original-api-video-interstitial"
		const val videoInterstitialConfigId = "prebid-demo-video-interstitial-320-480-original-api"
		
		fun getR89VideoInterstitialMockData(): AdUnitConfigScheme
		{
			val interstitialFormat = Formats.VIDEO_OUTSTREAM_INTERSTITIAL
			
			
			val testFrequencyCap = FrequencyCapScheme(
				false, 0, 0
			)
			
			return AdUnitConfigScheme(
				videoInterstitialR89configId,
				videoInterstitialUnitId,
				videoInterstitialConfigId, interstitialFormat,
				null, testFrequencyCap, null, null, null, null
			)
		}
	}
}