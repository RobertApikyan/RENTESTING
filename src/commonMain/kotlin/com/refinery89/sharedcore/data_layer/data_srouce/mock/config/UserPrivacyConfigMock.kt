package com.refinery89.sharedcore.data_layer.data_srouce.mock.config

import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.consent_config.CMPScheme

/** @suppress */
internal class UserPrivacyConfigMock
{
	companion object
	{
		const val CMP_TEST_ID = 54483
		const val CMP_TEST_CODE_ID = "e0d9c2efae9e"
		
		fun getUserPrivacyConfigMockData(): CMPScheme
		{
			return CMPScheme(CMP_TEST_ID, CMP_TEST_CODE_ID)
		}
	}
}