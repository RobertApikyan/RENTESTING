package com.refinery89.sharedcore.domain_layer.config.prebid

import com.refinery89.sharedcore.domain_layer.models.prebid_server.PrebidServerConfigData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.PrebidLibrary


/**
 * Uses The OS Prebid Library to comply with [PrebidLibrary] interface contract
 * @property prebidLibrary PrebidLibrary
 */
internal class PrebidConfigurator(private val prebidLibrary: PrebidLibrary)
{
	/**
	 * - Calls all the functions needed to configure the Prebid SDK coming from the prebid documentation
	 * - Configures the Prebid Server
	 * @return Unit
	 */
	fun configurePrebid(prebidServerConfigData: PrebidServerConfigData)
	{
		prebidLibrary.configurePrebid()
		prebidLibrary.configurePrebidServer(prebidServerConfigData)
	}
}