package com.refinery89.sharedcore.domain_layer.models.single_tag

import kotlinx.serialization.Serializable

/**
 * This right now would only be used for the Interstitial Format but it should be open to change so we can use similar structure for other formats that need to be displayed when
 * a specific event outside of the lifecycle of the Context.
 * @property to Name of the fragment or activity that is the target context
 * @property buttonTag Id of the button that will be tracked for the event
 */
@Serializable
internal data class ScreenEventData(
	val to: String?,
	val buttonTag: String?,
)
{
	/**
	 * @suppress
	 * @return Boolean
	 */
	fun isTransitionEvent(): Boolean
	{
		return to != null
	}
	
	/**
	 * @suppress
	 * @param toName String
	 * @return Boolean
	 */
	fun isTransitionTo(toName: String): Boolean
	{
		return isTransitionEvent() && to == toName
	}
	
	/**
	 * @suppress
	 * @return Boolean
	 */
	fun isButtonEvent(): Boolean
	{
		return buttonTag != null
	}
	
}