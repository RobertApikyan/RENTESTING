package com.refinery89.sharedcore.domain_layer.models

import com.refinery89.sharedcore.domain_layer.config.unitConfig.AdUnitConfigRepository
import com.refinery89.sharedcore.domain_layer.models.consent_config.CMPData
import com.refinery89.sharedcore.domain_layer.models.prebid_server.PrebidServerConfigData
import com.refinery89.sharedcore.domain_layer.models.single_tag.AdScreenData
import com.refinery89.sharedcore.domain_layer.models.target_config.TargetConfigAppData
import kotlinx.serialization.Serializable

/**
 * Data model for Domain Uses.
 *
 * @property publisherId the pubs id in our manager db
 * @property appId the apps id in our manager db
 * @property name name of the publisher
 * @property unitConfigRepo repository to interact with the unit config data
 * @property targetConfigAppData object that holds the global app target data
 * @property singleTagData object holding all the single tag data
 * @property cmpData object that holds the needed data for the cmp to be displayed
 * @property sendAllLogs if true then all logs will be saved to DB
 * @property prebidServerConfigData prebid server configuration data (accountId, host, serverTimeout)
 */
@Serializable
internal data class PublisherData(
	val publisherId: String,
	val appId: String,
	val name: String,
	val unitConfigRepo: AdUnitConfigRepository,
	val targetConfigAppData: TargetConfigAppData,
	val singleTagData: List<AdScreenData>?,
	val cmpData: CMPData,
	val sendAllLogs: Boolean,
	val prebidServerConfigData: PrebidServerConfigData,
)