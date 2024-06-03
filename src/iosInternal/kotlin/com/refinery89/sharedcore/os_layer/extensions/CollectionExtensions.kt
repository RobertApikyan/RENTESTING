package com.refinery89.sharedcore.os_layer.extensions

fun Map<Any?, *>.toStringSetMap(): Map<String, Set<String>> {
	return this.mapNotNull { (key, value) ->
		// Safely cast the key to String
		val stringKey = key as? String ?: return@mapNotNull null
		
		// Safely cast the value to Set<String>
		val stringSetValue = (value as? Set<*>)?.mapNotNull { it as? String }?.toSet() ?: return@mapNotNull null
		
		// Return the pair if both key and value are successfully cast
		stringKey to stringSetValue
	}.toMap()
}