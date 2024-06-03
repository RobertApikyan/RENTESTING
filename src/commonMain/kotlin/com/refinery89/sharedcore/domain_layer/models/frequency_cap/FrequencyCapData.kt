package com.refinery89.sharedcore.domain_layer.models.frequency_cap

import kotlinx.serialization.Serializable

/**
 * Data model for Domain Uses.
 * @property isCapped if a frequency cap should be applied or not
 * @property adAmountPerTimeSlot times the ad is allowed to be requested in the timeSlot
 * @property timeSlotSize amount of time that passes between start and end
 */
@Serializable
internal data class FrequencyCapData(
	val isCapped: Boolean,
	val adAmountPerTimeSlot: Int,
	val timeSlotSize: Long,
)
