package com.refinery89.sharedcore.data_layer.schemes_models.pub_request

import kotlinx.serialization.Serializable

/**
 * API Manager SDK Body to make request to the server for publisher info response
 *
 * @property publisher_id the pubs id in our manager db
 * @property app_id the apps id in our manager db
 * @property debug_flag whether to return a fake response or not
 */
@Serializable
internal data class PublisherRequestBody(
	val publisher_id: String,
	val app_id: String,
	val debug_flag: Boolean = false,
)