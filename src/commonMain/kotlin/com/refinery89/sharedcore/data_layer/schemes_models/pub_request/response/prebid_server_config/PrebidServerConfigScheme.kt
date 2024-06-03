package com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.prebid_server_config

import kotlinx.serialization.Serializable

/**
 * API Scheme for getting the Response
 *
 * @property accountId the account id within the prebid server
 * @property host the prebid server entry point
 * @property serverTimeout how long until an auction fails
 */
@Serializable
internal data class PrebidServerConfigScheme(
	val accountId: String?,
	val host: String?,
	val serverTimeout: Int?,
)