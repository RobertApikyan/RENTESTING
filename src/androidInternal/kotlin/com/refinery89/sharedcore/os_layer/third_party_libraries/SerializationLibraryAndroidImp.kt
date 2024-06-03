package com.refinery89.sharedcore.os_layer.third_party_libraries

import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89AdSize
import com.refinery89.sharedcore.domain_layer.internalToolBox.extensions.getTypeSimpleName
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.third_party_libraries.SerializationLibrary
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

internal class SerializationLibraryAndroidImp : SerializationLibrary
{
	override fun <T> toPrettyJson(serializer: SerializationStrategy<T>, data: T): String
	{
		val s = Json {
			prettyPrint = true
			isLenient = true
			explicitNulls = false
			ignoreUnknownKeys = true
		}
		
		return try
		{
			s.encodeToString(serializer, data)
		} catch (e: Exception)
		{
			R89LogUtil.e("SerializationLib", e)
			data.toString()
		}
	}
	
	/**
	 * Due to an error while getting the data from a Json it converts it to treemap so we need to make sure domain models only have domain objects in them.
	 * Only use at time of this comment is in banner for getting the Sizes from the Json and then parsing them to AdSize.
	 * Transforms a either:
	 * - A List of Gson coming data from the API
	 * - Local Inputted R89AdSize
	 * @param dataList Any
	 * @return List<R89AdSize>
	 */
	override fun getR89AdSizeList(dataList: Any): List<R89AdSize>
	{
		if (dataList is List<*>)
		{
			val element = dataList.firstOrNull()
			if (element == null)
			{
				R89LogUtil.wtf("SerializationLib", "dataList is empty", true)
				return listOf()
			}
			
			if (element.getTypeSimpleName() == R89AdSize::class.simpleName)
			{
				@Suppress("UNCHECKED_CAST")
				return dataList as List<R89AdSize>
				
			} else if (element.getTypeSimpleName() == LinkedHashMap::class.simpleName)
			{
				@Suppress("UNCHECKED_CAST")
				val castedList: List<LinkedHashMap<String, Any>> = dataList as List<LinkedHashMap<String, Any>>
				
				return castedList.map {
					val widthValue: Any = it.getValue("width")
					val heightValue: Any = it.getValue("height")
					
					// TODO REMOVE this and use just the it.getValue() method without converting back to string
					//  this is this way because refinery api returns the values as strings for some reason
					try
					{
						val width = convertValueToInt(widthValue)
						val height = convertValueToInt(heightValue)
						
						R89AdSize(width, height)
					} catch (e: Exception)
					{
						R89LogUtil.e("SerializationLib", e)
						return emptyList()
					}
				}
				
			} else
			{
				R89LogUtil.wtf(
					"SerializationLib",
					"dataList is not a List of R89AdSize or LinkedTreeMap",
					true
				)
			}
		} else
		{
			R89LogUtil.wtf("SerializationLib", "dataList is not a List", true)
		}
		
		return listOf()
	}
	
	
	override fun convertValueToInt(value: Any): Int
	{
		return when (value)
		{
			is String -> value.toInt()
			is Double -> value.toInt()
			is Int    -> value
			else      -> throw IllegalArgumentException("Unsupported type for value: $value")
		}
	}
	
}