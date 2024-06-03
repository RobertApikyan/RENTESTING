package com.refinery89.sharedcore.domain_layer.models.target_config

import kotlinx.serialization.Serializable

/**
 * Data used to configure the user for the SDK
 *
 * @property storeUrl store of the publisher
 * @property domain publisher domain
 * @property appInventoryKeywords keywords that will be applied to all the bids
 * @property bidderAccessControlList this bidders will have access to the First Party data
 * @property globalFPInventoryData data used to target the bids applied to all units
 */
@Serializable
internal data class TargetConfigAppData(
	val storeUrl: String,
	val domain: String?,
	val appInventoryKeywords: Set<String>?,
	val bidderAccessControlList: List<String>?,
	val globalFPInventoryData: Map<String, Set<String>>?,
)

