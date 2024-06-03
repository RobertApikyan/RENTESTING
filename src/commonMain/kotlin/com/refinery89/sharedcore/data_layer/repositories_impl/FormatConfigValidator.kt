package com.refinery89.sharedcore.data_layer.repositories_impl

import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.unit_config.AdUnitConfigScheme
import com.refinery89.sharedcore.domain_layer.third_party_libraries.SerializationLibrary

/**
 * Each format companion object implements this so the validator can use it to validate the config that format is assign
 */
internal interface FormatConfigValidator
{
	/**
	 * Data is passed to the and now is responsibility of the implementation to validate and return false if not valid or true if valid
	 * @param adUnitConfigData AdUnitConfigScheme
	 * @return Boolean
	 */
	fun isInValidConfig(
		adUnitConfigData: AdUnitConfigScheme,
		serializationLibrary: SerializationLibrary
	): Boolean
	
	/**
	 * If the config is not valid this method will return the error message to be shown to the user
	 * @param adUnitConfigData AdUnitConfigScheme
	 * @return String
	 */
	fun getErrorMessage(
		adUnitConfigData: AdUnitConfigScheme,
		serializationLibrary: SerializationLibrary
	): String
	
	/**
	 * @suppress
	 */
	companion object
	{
		val Default = object : FormatConfigValidator
		{
			override fun isInValidConfig(
				adUnitConfigData: AdUnitConfigScheme,
				serializationLibrary: SerializationLibrary
			): Boolean
			{
				return true
			}
			
			override fun getErrorMessage(
				adUnitConfigData: AdUnitConfigScheme,
				serializationLibrary: SerializationLibrary
			): String
			{
				return "this is the default format validator"
			}
		}
	}
}