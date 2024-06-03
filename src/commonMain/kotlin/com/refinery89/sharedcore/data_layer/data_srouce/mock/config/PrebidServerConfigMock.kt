package com.refinery89.sharedcore.data_layer.data_srouce.mock.config

import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.prebid_server_config.PrebidServerConfigScheme

/**
 * @suppress
 */
internal class PrebidServerConfigMock
{
	companion object
	{
		
		private const val prebidServerTimeout = 30000
		private const val testServerAccountId = "0689a263-318d-448b-a3d4-b02e8a709d9d"
		private const val testServerHost =
			"https://prebid-server-test-j.prebid.org/openrtb2/auction"
		
		fun getPrebidServerConfigMock(): PrebidServerConfigScheme
		{
			return PrebidServerConfigScheme(
				testServerAccountId,
				testServerHost,
				prebidServerTimeout
			)
		}
	}
}