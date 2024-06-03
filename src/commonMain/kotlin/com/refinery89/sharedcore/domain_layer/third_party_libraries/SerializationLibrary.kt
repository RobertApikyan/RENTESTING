package com.refinery89.sharedcore.domain_layer.third_party_libraries

import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89AdSize
import kotlinx.serialization.SerializationStrategy

internal interface SerializationLibrary
{
	fun <T> toPrettyJson(serializer: SerializationStrategy<T>, data: T): String
	
	fun getR89AdSizeList(json: Any): List<R89AdSize>
	fun convertValueToInt(value: Any): Int
}