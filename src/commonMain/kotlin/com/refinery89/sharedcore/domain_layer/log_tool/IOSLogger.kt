package com.refinery89.sharedcore.domain_layer.log_tool

/**
 * Allows the use of the OS logger library
 * @constructor Create empty O s util library android impl
 */
internal interface IOSLogger : IR89Logger
{
	override fun print(logLevel: LogLevels, tag: String, message: String)
}