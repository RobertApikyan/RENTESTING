package com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.consent_config

import kotlinx.serialization.Serializable

/** @property cmpId Int Id to be passes to the CMP */
@Serializable
internal data class CMPScheme(
	val cmpId: Int,
	val cmpCodeId: String,
)
