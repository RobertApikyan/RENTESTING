package com.refinery89.sharedcore.os_layer.log_tool

import android.util.Log
import com.refinery89.sharedcore.domain_layer.log_tool.IOSLogger
import com.refinery89.sharedcore.domain_layer.log_tool.LogLevels

//TODO change this to ILoggerLibrary interface so it makes more sense since we already have many different interface contracts to cover different OS features
// like [ISimpleDataSave]
/**
 * Uses The OS Library to comply with [IOSLogger] interface contract
 * Android OS Utilities are extracted to here
 * @constructor Create empty O s util library android impl
 */
internal class IOSLoggerAndroidImpl : IOSLogger
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
		//convert to android compatible log level
		val priority: Int = when (logLevel)
		{
			LogLevels.VERBOSE -> Log.VERBOSE
			LogLevels.DEBUG   -> Log.DEBUG
			LogLevels.INFO    -> Log.INFO
			LogLevels.WARN    -> Log.WARN
			LogLevels.ERROR   -> Log.ERROR
			LogLevels.ASSERT  -> Log.ASSERT
		}
		
		Log.println(priority, tag, message)
	}
}