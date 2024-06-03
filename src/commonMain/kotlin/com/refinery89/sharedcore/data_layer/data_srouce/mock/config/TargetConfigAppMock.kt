package com.refinery89.sharedcore.data_layer.data_srouce.mock.config

import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.target_config.TargetConfigAppScheme

/**
 * @suppress
 *
 */
internal class TargetConfigAppMock
{
	
	companion object
	{
		fun getTargetConfigAppMockData(): TargetConfigAppScheme
		{
			val storeUrl = "https://play.google.com/store/apps/details?id=com.refinery.xgnapp"
			val domain = "www.xgn.nl"
			val globalInventoryKeywords = setOf("globalKeywords1", "globalKeyword2", "globalKeyword3")
			val testGlobalFPID =
				mapOf(
					"globalCookieKey1" to setOf("globalCookieData1", "globalCookieData2"),
					"globalCookieKey2" to setOf("globalCookieData3", "globalCookieData4")
				)
			val bidderAccessControlList = listOf("bidder1", "bidder2", "bidder3")
			
			return TargetConfigAppScheme(
				storeUrl,
				domain,
				null,
				null,
				null
			)
		}
	}
}