package com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag

import com.refinery89.sharedcore.data_layer.schemes_models.jsonObjectToMap
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * API Scheme for getting the Response.
 *
 * @property r89configId Config to be used for the format
 * @property adFormat format that we are going to display in this ad place
 * @property wrapperData
 * @property eventsToTrack
 * @property dataMapValue
 */
@Serializable
internal data class AdPlaceScheme(
	val r89configId: String?,
	val adFormat: Formats?,
	val wrapperData: WrapperScheme?,
	val eventsToTrack: List<ScreenEventScheme>?,
	//	val dataMap: Map<String,Any?>
	val dataMap: JsonObject?,
)
{
	//TODO remove this
	val dataMapValue by lazy { dataMap?.jsonObjectToMap() }
}