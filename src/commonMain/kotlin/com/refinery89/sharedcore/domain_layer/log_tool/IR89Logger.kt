package com.refinery89.sharedcore.domain_layer.log_tool

interface IR89Logger
{
	/**
	 * @param logLevel LogLevels what level of log is this message
	 * @param tag where is this log coming from
	 * @param message information to be logged
	 * @return Unit
	 */
	fun print(logLevel: LogLevels, tag: String, message: String)
}
