package com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag

import kotlinx.serialization.Serializable

/**
 * API Scheme for getting the Response.
 * @property isFragment false = activity true = fragment
 * @property screenName the activity or fragment name
 * @property adPlaceList All the ads that should be visible in this view
 */

@Serializable
internal data class AdScreenScheme(
	val isFragment: Boolean?,
	val screenName: String?,
	val adPlaceList: List<AdPlaceScheme>?,
)


