package com.refinery89.sharedcore.data_layer.schemes_models

import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89AdSize
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.float
import kotlinx.serialization.json.floatOrNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.long
import kotlinx.serialization.json.longOrNull

internal fun Map<*, *>.toJsonObject() = buildJsonObject {
	this@toJsonObject.forEach { (key, value) ->
		if (value != null)
		{
			put(key.toString(), value.toJsonElement())
		}
	}
}

private fun R89AdSize.toJsonObject(): JsonObject
{
	return buildJsonObject {
		put("width", width.toJsonElement())
		put("height", height.toJsonElement())
	}
}

private fun List<*>.toJsonArray(): JsonArray
{
	return buildJsonArray {
		this@toJsonArray.forEach { item ->
			if (item != null)
			{
				add(item.toJsonElement())
			}
		}
	}
}

private fun Any.toJsonElement(): JsonElement
{
	return when (this)
	{
		is Map<*, *> -> this.toJsonObject()
		is List<*>   -> this.toJsonArray()
		is R89AdSize -> this.toJsonObject()
		is String    -> JsonPrimitive(this)
		is Int       -> JsonPrimitive(this)
		is Long      -> JsonPrimitive(this)
		is Double    -> JsonPrimitive(this)
		is Float     -> JsonPrimitive(this)
		is Boolean   -> JsonPrimitive(this)
		else         -> throw IllegalArgumentException("Unsupported type for conversion to JsonElement: $this")
	}
}

internal fun JsonObject.jsonObjectToMap(): Map<String, Any> = jsonObject.entries.associate { (key, value) ->
	key to value.jsonElementToAny()
}

private fun JsonElement.jsonElementToAny(): Any
{
	return when (this)
	{
		is JsonObject    -> jsonObjectToMap()
		is JsonArray     -> jsonArray.map { it.jsonElementToAny() }
		is JsonPrimitive ->
		{
			when
			{
				isString              -> content
				booleanOrNull != null -> boolean
				intOrNull != null     -> int
				doubleOrNull != null  -> double
				floatOrNull != null   -> float
				longOrNull != null    -> long
				else                  -> content // fallback for other cases
			}
		}
		
		else             -> throw IllegalArgumentException("Unsupported JSON element type")
	}
}

