package com.refinery89.sharedcore.domain_layer.models.single_tag

import com.refinery89.sharedcore.data_layer.schemes_models.jsonObjectToMap
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Data model for Domain Uses.
 * @property r89configId Config to be used for the format
 * @property adFormat format that we are going to display in this ad place
 * @property wrapperData
 * @property eventsToTrack
 * @property dataMap
 */
@Serializable
internal data class AdPlaceData(
	val r89configId: String,
	val adFormat: Formats,
	val wrapperData: WrapperData?,
	val eventsToTrack: List<ScreenEventData>?,
	private val dataMap: JsonObject?,
)
{
	
	private val dataMapValue by lazy { dataMap?.jsonObjectToMap() }
	
	/**
	 * To Know if the ad-place is suppose to show a wrapper dependant format/ad
	 * @return Boolean
	 */
	fun isWrapperDependant(): Boolean
	{
		return eventsToTrack == null
	}
	
	/**
	 * To Know if the ad-place is suppose to track events in different screens
	 * @return Boolean
	 */
	fun hasEventsToTrack(): Boolean
	{
		return eventsToTrack != null
	}
	
	/**
	 * To Know if the ad place is valid
	 * @return Boolean
	 */
	fun hasWrapperData(): Boolean
	{
		return wrapperData != null
	}
	
	/**
	 * Checking if this ad place has a transition to a specific screen with the [toName] name
	 * @param toName String
	 * @return Boolean
	 */
	fun hasTransitionTo(toName: String): Boolean
	{
		return if (hasEventsToTrack())
		{
			eventsToTrack!!.any { event ->
				event.isTransitionTo(toName)
			}
		} else
		{
			false
		}
	}
	
	/**
	 * returns data casted from Any to [T]
	 *
	 * @param key the key to get the value from config object, the Key must
	 *     exist in the map and its value can't be null
	 * @throws ClassCastException if the value is not the correct type
	 * @throws NullPointerException if [dataMap] is null, because unitConfig
	 *     object is not provided with any data OR the data inside the key is
	 *     null
	 * @throws IllegalArgumentException if the key doesn't exist in the map
	 */
	fun <T> getData(key: String): T
	{
		dataMapValue?.let { notNullDataMap ->
			if (notNullDataMap.containsKey(key))
			{
				notNullDataMap[key]?.let { dataValue ->
					try
					{
						@Suppress("UNCHECKED_CAST")
						return dataValue as T
					} catch (e: Exception)
					{
						throw ClassCastException(
							"Couldn't cast value of: $key to the requested" +
									" Type. unitConfig with id: $r89configId"
						)
					}
				}.run {
					throw NullPointerException("Data inside $key is Null for unitConfig with id: $r89configId")
				}
			} else
			{
				throw IllegalArgumentException(
					"$key does not exist in dataMap here are the " +
							"existing keys: ${dataMapValue?.keys} for the unitConfig with id: $r89configId"
				)
			}
		}
		
		throw NullPointerException("DataMap is Null for unitId: $r89configId")
	}
}
