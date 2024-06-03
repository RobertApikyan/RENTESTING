package com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.unit_config

import com.refinery89.sharedcore.data_layer.schemes_models.jsonObjectToMap
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.frequency_cap.FrequencyCapScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.wrapper_style.WrapperStyleScheme
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * API Scheme for getting the Response.
 * This objects are placed inside [com.refinery89.androidSdk.domain_layer.config.unitConfig.AdUnitConfigRepository] and then used
 * by the [com.refinery89.androidSdk.domain_layer.RefineryAdFactory] to configure the
 * different ads
 *
 * @property r89configId the id of to identify this config object
 * @property unitId the id of the units that will use this configuration
 * @property configId config Id for prebid
 * @property formatTypeString checking against this is done to avoid errors
 * @property autoRefreshMillis this as with [configId] is common to all
 *     formats and is indicated in milliseconds just to give publisher more
 *     control in exact timings and simplifying the job of working with
 *     floating point values
 * @property frequencyCapScheme object declaring how to cap formats displayed with this configuration
 * @property inventoryKeywords specific unit keywords
 * @property unitFPID this specific unit first party data
 * @property dataMapValue custom data for the SDK like width, height, other ids.
 * @property wrapperStyleScheme custom style for r89wrapper.
 */
@Serializable
internal data class AdUnitConfigScheme(
	val r89configId: String?,
	val unitId: String?,
	val configId: String?,
	val formatTypeString: Formats?,
	val autoRefreshMillis: Int?,
	val frequencyCapScheme: FrequencyCapScheme?,
	val inventoryKeywords: List<String>?,
	val unitFPID: Map<String, Set<String>>?,
	val dataMap: JsonObject?,
	// TODO val nativeViewConfig: NativeViewConfig?
	val wrapperStyleScheme: WrapperStyleScheme?,
)
{
	
	val dataMapValue by lazy { dataMap?.jsonObjectToMap() }
	
	/**
	 * returns data casted from Any to [T]
	 *
	 * @param key the key to get the value from config object, the Key must
	 *     exist in the map and its value can't be null
	 * @throws ClassCastException if the value is not the correct type
	 * @throws NullPointerException if [dataMapValue] is null, because unitConfig
	 *     object is not provided with any data OR the data inside the key is
	 *     null
	 * @throws IllegalArgumentException if the key doesn't exist in the map
	 */
	fun <T> getData(key: String): Result<T>
	{
		dataMapValue?.let { notNullDataMap ->
			if (notNullDataMap.containsKey(key))
			{
				notNullDataMap[key]?.let { dataValue ->
					try
					{
						@Suppress("UNCHECKED_CAST")
						return Result.success(dataValue as T)
					} catch (e: Exception)
					{
						return Result.failure(
							ClassCastException(
								"Couldn't cast value of: $key to the requested" +
										" Type. unitConfig with id: $r89configId"
							)
						)
					}
				}.run {
					return Result.failure(NullPointerException("Data inside $key is Null for unitConfig with id: $r89configId"))
				}
			} else
			{
				return Result.failure(
					IllegalArgumentException(
						"$key does not exist in dataMap here are the " +
								"existing keys: ${dataMapValue?.keys} for the unitConfig with id: $r89configId"
					)
				)
			}
		}
		
		return Result.failure(NullPointerException("DataMap is Null for unitConfig with id: $r89configId"))
	}
}