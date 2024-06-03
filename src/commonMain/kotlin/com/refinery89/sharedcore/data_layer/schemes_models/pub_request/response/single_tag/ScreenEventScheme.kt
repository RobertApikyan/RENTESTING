package com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag

import kotlinx.serialization.Serializable

/**
 * API Scheme for getting the Response.
 * @property to Name of the screen that is going to be transitioned into
 * @property buttonId Id of the button that will be tracked for the event
 */
@Serializable
internal data class ScreenEventScheme(
	val to: String?,
	val buttonId: String?,
)