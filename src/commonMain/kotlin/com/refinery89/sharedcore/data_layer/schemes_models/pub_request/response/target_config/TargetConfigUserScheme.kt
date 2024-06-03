package com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.target_config

import kotlinx.serialization.Serializable

/**
 * API Scheme for getting the Response.
 * This is used to hold all the information related to the user this is
 * supposed to be filled for each user so the publisher is responsible for
 * filling this
 *
 * @property age Int?
 * @property gender String?
 * @property latLng Pair<Float, Float>?
 * @property FPud Map<String, Set<String>>?
 * @property keywords Set<String>?
 */
@Serializable
internal data class TargetConfigUserScheme(
	val age: Int?,
	val gender: String?,
	val latLng: Pair<Float, Float>?,
	val FPud: Map<String, Set<String>>?,
	val keywords: Set<String>?,
)
