package com.refinery89.sharedcore.domain_layer.adFormats.formats_utils

/**
 * The Error object that you get when an ad fails to load
 */
class R89LoadError internal constructor(private val error: String)
{
	/**
	 * gets a comprehensive error message with ALL the information
	 * @return String
	 */
	override fun toString(): String
	{
		return error
	}
}