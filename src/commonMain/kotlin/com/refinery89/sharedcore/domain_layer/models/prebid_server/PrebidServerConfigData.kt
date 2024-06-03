package com.refinery89.sharedcore.domain_layer.models.prebid_server

import kotlinx.serialization.Serializable

/**
 * Data model for Domain Uses.
 *
 * @property accountId the account id within the prebid server
 * @property host the prebid server entry point
 * @property serverTimeout how long until an auction fails
 */
@Serializable
internal data class PrebidServerConfigData(
	val accountId: String,
	val host: String,
	val serverTimeout: Int,
)