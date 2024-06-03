package com.refinery89.sharedcore.data_layer.data_srouce.mock.ad_formats

import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.frequency_cap.FrequencyCapScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.unit_config.AdUnitConfigScheme
import com.refinery89.sharedcore.domain_layer.adFormats.Formats


/**
 * @suppress
 *
 */
internal class R89RewardedInterstitialMock
{
	companion object
	{
		const val rewardedInterstitialR89configId = "generic-demo-rewarded-interstitial"
		const val rewardedInterstitialUnitId = "/6499/example/rewarded"
		const val rewardedInterstitialConfigId = ""
		
		fun getR89RewardedInterstitialMockData(): AdUnitConfigScheme
		{
			val interstitialFormat = Formats.REWARDED_INTERSTITIAL
			
			
			val testFrequencyCap = FrequencyCapScheme(
				false, 0, 0
			)
			
			return AdUnitConfigScheme(
				rewardedInterstitialR89configId,
				rewardedInterstitialUnitId,
				rewardedInterstitialConfigId, interstitialFormat,
				null, testFrequencyCap, null, null, null, null
			)
		}
	}
}
