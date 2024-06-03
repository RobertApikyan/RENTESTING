package com.refinery89.sharedcore.os_layer.log_tool

import com.refinery89.sharedcore.domain_layer.log_tool.IOSLogger
import com.refinery89.sharedcore.domain_layer.log_tool.LogLevels
import platform.Foundation.NSLog

internal class IOSLoggerIosImpl : IOSLogger
{
	/**
	 *
	 * @param logLevel LogLevels what level of log is this message
	 * @param tag where is this log coming from
	 * @param message information to be logged
	 * @return Unit
	 */
	override fun print(logLevel: LogLevels, tag: String, message: String)
	{
		// Convert to iOS compatible log level
		val logMessage = "[$tag] $message"
		when (logLevel)
		{
			LogLevels.VERBOSE -> println("[VERBOSE] $logMessage")
			LogLevels.DEBUG   -> println("[DEBUG] $logMessage")
			LogLevels.INFO    -> println("[INFO] $logMessage")
			LogLevels.WARN    -> println("[WARN] $logMessage")
			LogLevels.ERROR   -> println("[ERROR] $logMessage")
			LogLevels.ASSERT  -> println("[ASSERT] $logMessage")
		}
	}
}