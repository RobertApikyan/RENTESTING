package com.refinery89.sharedcore.domain_layer.models.single_tag

import kotlinx.serialization.Serializable

/**
 * Data model for Domain Uses.
 * @property isFragment false = activity true = fragment
 * @property screenName the activity or fragment name
 * @property adPlaceList All the ads that should be visible in this view
 */
@Serializable
internal data class AdScreenData(
	val isFragment: Boolean,
	val screenName: String,
	val adPlaceList: List<AdPlaceData>,
)
{
	/**
	 * Checks if the screen is a fragment and if it is the same as the one passed in
	 * @param fragmentName String
	 * @return Boolean
	 */
	fun isFragmentScreenFor(fragmentName: String): Boolean
	{
		return isFragment && screenName == fragmentName
	}
	
	/**
	 * Checks if the screen is an activity and if it is the same as the one passed in
	 * @param activityName String
	 * @return Boolean
	 */
	fun isActivityScreenFor(activityName: String): Boolean
	{
		return !isFragment && screenName == activityName
	}
	
	/**
	 * Checks if the screen has a transition to the name passed in
	 * checking each adPlace in this screen if it has a transition
	 * @param toName String
	 * @return Boolean
	 */
	fun hasTransitionTo(toName: String): Boolean
	{
		return adPlaceList.any { adPlace ->
			adPlace.hasTransitionTo(toName)
		}
	}
	
	/**
	 * Gets all the adPlaces that have a transition to the name passed in
	 * @param toName String
	 * @return List<AdPlaceData>
	 */
	fun getAllAdPlacesWithTransitionTo(toName: String): List<AdPlaceData>
	{
		return adPlaceList.filter { adPlace ->
			adPlace.hasTransitionTo(toName)
		}
	}
	
}
