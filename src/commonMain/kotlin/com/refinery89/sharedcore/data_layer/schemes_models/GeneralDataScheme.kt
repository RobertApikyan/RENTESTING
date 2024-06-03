package com.refinery89.sharedcore.data_layer.schemes_models

import kotlinx.serialization.Serializable

/**
 * This is to wrap around the actual data needed
 * @param T
 * @property data T
 * @constructor
 */
@Serializable
internal data class GeneralDataScheme<T>(
	val data: T?,
)
