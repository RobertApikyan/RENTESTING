package com.refinery89.sharedcore.domain_layer.models.consent_config

import kotlinx.serialization.Serializable

/** @property cmpId Int Id to be passes to the CMP */
@Serializable
internal data class CMPData(
	val cmpId: Int,
	val cmpCodeId: String,
)