package com.refinery89.sharedcore.domain_layer.models.unit_config

import com.refinery89.sharedcore.data_layer.schemes_models.jsonObjectToMap
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.models.frequency_cap.FrequencyCapData
import com.refinery89.sharedcore.domain_layer.models.wrapper_style.WrapperStyleData
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * This objects are placed inside [com.refinery89.androidSdk.domain_layer.config.unitConfig.AdUnitConfigRepository] and then used
 * by the [com.refinery89.androidSdk.domain_layer.RefineryAdFactory] to configure the
 * different ads
 *
 * @property r89configId the id of the units that will use this configuration
 * @property gamUnitId the id of the units that will use this configuration
 * @property prebidConfigId config Id for prebid
 * @property formatTypeString checking against this is done to avoid errors
 * @property autoRefreshMillis this as with [prebidConfigId] is common to all
 *     formats and is indicated in milliseconds just to give publisher more
 *     control in exact timings and simplifying the job of working with
 *     floating point values
 * @property frequencyCapData object declaring how to cap formats displayed with this configuration
 * @property inventoryKeywords specific unit keywords
 * @property unitFPID this specific unit first party data
 * @property dataMap custom data for the SDK like width, height, other ids.
 * @property wrapperStyleData custom style for r89wrapper.
 */
@Serializable
internal data class AdUnitConfigData(
	val r89configId: String,
	val gamUnitId: String,
	val prebidConfigId: String,
	val formatTypeString: Formats,
	val autoRefreshMillis: Int,
	val frequencyCapData: FrequencyCapData,
	val inventoryKeywords: List<String>?,
	val unitFPID: Map<String, Set<String>>?,
	private val dataMap: JsonObject?,
	// TODO val nativeViewConfig: NativeViewConfig?
	val wrapperStyleData: WrapperStyleData?,
)
{
	//TODO remove this always use JSON object for the getData
	private val dataMapValue by lazy { dataMap?.jsonObjectToMap() }
	
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