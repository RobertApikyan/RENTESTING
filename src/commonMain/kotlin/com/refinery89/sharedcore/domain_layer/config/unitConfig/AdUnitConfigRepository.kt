package com.refinery89.sharedcore.domain_layer.config.unitConfig

import com.refinery89.sharedcore.domain_layer.internalToolBox.internal_error_handling_tool.R89Error
import com.refinery89.sharedcore.domain_layer.models.unit_config.AdUnitConfigData
import kotlinx.serialization.Serializable

/**
 * provides [com.refinery89.androidSdk.domain_layer.config.R89GlobalConfigurator] with easy API for managing unit configurations
 * @property unitConfigMap MutableMap<String, AdUnitConfig>
 */
@Serializable
internal class AdUnitConfigRepository private constructor(private val unitConfigMap: MutableMap<String, AdUnitConfigData>)
{
	constructor(unitConfigList: List<AdUnitConfigData>) : this(
		unitConfigMap = unitConfigList.associateBy { it.r89configId }.toMutableMap()
	)
	
	/**
	 * @throws NoSuchElementException if the value of the key is null
	 */
	internal fun getConfigOrThrow(r89configId: String): AdUnitConfigData
	{
		if (unitConfigMap.containsKey(r89configId))
		{
			return unitConfigMap.getValue(r89configId)
		} else
		{
			throw R89Error.NoConfigWasFound("No unit config was found for r89 config id: $r89configId")
		}
	}
}