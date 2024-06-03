package com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.frequency_cap

import kotlinx.serialization.Serializable

/**
 * API Scheme for getting the Response
 * @property isCapped if a frequency cap should be applied or not
 * @property adAmountPerTimeSlot times the ad is allowed to be requested in the timeSlot
 * @property timeSlotSize amount of time that passes between start and end
 */
@Serializable
internal data class FrequencyCapScheme(
	val isCapped: Boolean,
	val adAmountPerTimeSlot: Int,
	val timeSlotSize: Long,
)
