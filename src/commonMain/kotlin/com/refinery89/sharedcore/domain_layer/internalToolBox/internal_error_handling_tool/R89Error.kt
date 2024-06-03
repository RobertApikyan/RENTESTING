package com.refinery89.sharedcore.domain_layer.internalToolBox.internal_error_handling_tool

import com.refinery89.sharedcore.domain_layer.internalToolBox.extensions.getTypeSimpleName
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil

//TODO: Add more error cases
/**
 * Error class for R89SDK
 * @param message error message, you can specify a custom message when creating the error or specify a default message in this class
 * @param shouldLog whether the error should log the message or not
 * @param logToDatabase whether the error should log the message to the server
 */
internal sealed class R89Error(
	override val message: String,
	private val shouldLog: Boolean,
	private val logToDatabase: Boolean,
) : Throwable()
{
	/**
	 * Generic Network Error
	 * @constructor
	 */
	class NetworkError(message: String = "Generic Network Error") : R89Error(message, true, false)
	
	/**
	 * Manager Api Response is not valid for the current version of the SDK
	 * @constructor
	 */
	class PublisherDataNotValid(message: String) : R89Error(message, false, false)
	
	/**
	 * Data Validation Failed and conversion can not Continue
	 * @constructor
	 */
	class DataValidationFailed(message: String) : R89Error(message, false, false)
	
	/**
	 * The Id of the configuration is not present in the current configuration list, check if the id is correct or if you have created that configuration
	 * @constructor
	 */
	class NoConfigWasFound(message: String) : R89Error(message, false, false)
	
	class PublisherNotReceived : R89Error("Publisher Data was null on arrival from DB", true, false)
	
	//Uncomment this to log every error message when the error is created
	//init {
	//    if (shouldLog) {
	//        logMessage()
	//    }
	//}
	
	/**
	 * we can automatically log an error on creation if we wanted to
	 * @param tag String
	 * @return Unit
	 */
	fun logMessage(tag: String)
	{
		R89LogUtil.e(tag, message + { this.getTypeSimpleName() }, logToDatabase)
	}
}