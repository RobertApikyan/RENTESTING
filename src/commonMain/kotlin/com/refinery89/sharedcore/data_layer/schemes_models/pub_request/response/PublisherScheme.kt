package com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response


import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.consent_config.CMPScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.prebid_server_config.PrebidServerConfigScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag.AdScreenScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.target_config.TargetConfigAppScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.unit_config.AdUnitConfigScheme
import kotlinx.serialization.Serializable

/**
 * API Scheme for getting the Response.
 *
 * @property publisherId the pubs id in our manager db
 * @property appId the apps id in our manager db
 * @property name name of the publisher
 * @property unitConfigList list of configs coming from server
 * @property targetConfigAppData object that holds the global app target data
 * @property singleTagAdScreenData object holding all the single tag data
 * @property cmpData object that holds the needed data for the cmp to be displayed
 * @property sendAllLogs if true then all logs will be saved to DB
 * @property prebidServerConfigData prebid server configuration data (accountId, host, serverTimeout)
 */
@Serializable
internal data class PublisherScheme(
	val publisherId: String?,
	val appId: String?,
	val name: String?,
	val unitConfigList: List<AdUnitConfigScheme>?,
	val targetConfigAppData: TargetConfigAppScheme?,
	val singleTagAdScreenData: List<AdScreenScheme>?,
	val cmpData: CMPScheme?,
	val sendAllLogs: Boolean?,
	val prebidServerConfigData: PrebidServerConfigScheme?,
)
