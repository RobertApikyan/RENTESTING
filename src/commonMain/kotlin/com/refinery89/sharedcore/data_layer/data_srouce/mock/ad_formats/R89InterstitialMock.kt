package com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats

import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.frequency_cap.FrequencyCapScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.unit_config.AdUnitConfigScheme
import com.refinery89.sharedcore.domain_layer.adFormats.Formats

/**
 * @suppress
 *
 */
internal class R89InterstitialMock
{
	companion object
	{
		const val interstitialR89configId = "generic-demo-display-interstitial"
		const val interstitialUnitId =
			"/21808260008/prebid-demo-app-original-api-display-interstitial"
		const val interstitialConfigId = "prebid-demo-display-interstitial-320-480"
		
		fun getR89InterstitialMockData(): AdUnitConfigScheme
		{
			val interstitialFormat = Formats.INTERSTITIAL
			
			val testFrequencyCap = FrequencyCapScheme(
				true, 2, 3600000
			)
			return AdUnitConfigScheme(
				interstitialR89configId,
				interstitialUnitId,
				interstitialConfigId, interstitialFormat,
				null, null, null, null, null, null
			)
		}
	}
}