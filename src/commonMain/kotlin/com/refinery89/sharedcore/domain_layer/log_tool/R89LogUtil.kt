package com.refinery89.sharedcore.domain_layer.log_tool

import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.PublisherScheme
import com.refinery89.sharedcore.domain_layer.analytics.SendLogRequest
import com.refinery89.sharedcore.domain_layer.internalToolBox.internal_error_handling_tool.R89ErrorHandler
import com.refinery89.sharedcore.domain_layer.models.PublisherData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.SerializationLibrary

/**
 * Log Utility it enables easier filter for unclogging the logcat and also is the entry point
 * to the analytics api, capable of sending logs to the server for every event in the SDK
 *
 * @constructor Create empty R89LogUtil
 */
internal object R89LogUtil
{
	
	private var serializationLibrary: SerializationLibrary? = null
	private var osLogger: IOSLogger? = null
	private var publicLogger: IR89Logger? = null
	
	private const val R89_BASE_SDK_TAG = "R89SDK: "
	
	private var logLevel: LogLevels = LogLevels.INFO
	private var sendAllLogs: Boolean = false
	private var deviceUUIDForLogs: String = ""
	
	private var logToServerUseCase: SendLogRequest? = null
	private var replaceOsLoggerWithPublic = false
	
	/**
	 *
	 * @return Unit
	 */
	fun provideDependencies(sendLogRequestsDependency: SendLogRequest, osUtilDependency: IOSLogger, serializationLibraryDependency: SerializationLibrary)
	{
		osLogger = osUtilDependency
		logToServerUseCase = sendLogRequestsDependency
		serializationLibrary = serializationLibraryDependency
	}
	
	/**
	 * sets the log level for the SDK
	 * @param level Int
	 */
	fun setLogLevel(level: LogLevels)
	{
		logLevel = level
	}
	
	/**
	 * sets the flag to force ALL the logs to be in the DB
	 * @param sendAllLogs Boolean
	 */
	fun setSendAllLogs(sendAllLogs: Boolean)
	{
		R89LogUtil.sendAllLogs = sendAllLogs
	}
	
	/**
	 * sets the device uuid to send logs to DB, used when shared preferences contain a corresponding parameter
	 * @param uuid String
	 */
	fun setDeviceUUIDForLogs(uuid: String)
	{
		deviceUUIDForLogs = uuid
	}
	
	/**
	 * Verbose log. VERBOSE is for Low Level Information that's important for debugging a lot of information
	 * this logs don't go into release builds
	 * @param tag String
	 * @param message String
	 * @param sendLogToDB Boolean
	 */
	fun v(tag: String, message: String, sendLogToDB: Boolean = false)
	{
		//todo exclude from release builds
		print(LogLevels.VERBOSE, getR89Tag(tag), message, sendLogToDB)
	}
	
	/**
	 * Debug log. DEBUG is for Low Level Information that's important for debugging containing less info than verbose
	 * this logs don't go into release builds
	 * @param tag String
	 * @param message String
	 * @param sendLogToDB Boolean
	 */
	fun d(tag: String, message: String, sendLogToDB: Boolean = false)
	{
		//todo exclude from release builds
		print(LogLevels.DEBUG, getR89Tag(tag), message, sendLogToDB)
	}
	
	/**
	 * This is used to Log events that happened in the Execution of the SDK initialization,
	 * normal lifecycle of the formats, anything that went as expected and we want to
	 * inform the user about it with a log
	 * @param tag String
	 * @param message String
	 * @param sendLogToDB Boolean
	 * @return Unit
	 */
	fun i(tag: String, message: String, sendLogToDB: Boolean = false)
	{
		print(LogLevels.INFO, getR89Tag(tag), message, sendLogToDB)
	}
	
	/**
	 * Something went wrong but the SDK is able to continue showing ads and generate some revenue
	 * but is on suboptimal conditions
	 * @param tag String
	 * @param message String
	 * @param sendLogToDB Boolean
	 * @return Unit
	 */
	fun w(tag: String, message: String, sendLogToDB: Boolean = false)
	{
		print(LogLevels.WARN, getR89Tag(tag), message, sendLogToDB)
	}
	
	/**
	 * SDK is not able to continue showing ads and generate revenue and this should happened when:
	 * - ads fail to load
	 * - Internet connection is lost
	 * - anything that makes the SDK not usable
	 * - anything that was in the configuration and failed Example: customLayout for Native Ads
	 * @param tag String
	 * @param message String
	 * @param sendLogToDB Boolean
	 * @return Unit
	 */
	fun e(tag: String, message: String, sendLogToDB: Boolean)
	{
		print(LogLevels.ERROR, getR89Tag(tag), message, sendLogToDB)
	}
	
	/**
	 * Logs a throwable error which is handled by the [R89ErrorHandler]
	 * @param tag String
	 * @param error Throwable
	 * @return Unit
	 */
	fun e(tag: String, error: Throwable)
	{
		R89ErrorHandler.handleError(tag, error)
	}
	
	/**
	 * Something went really wrong and we need to crash. and send it to the DB probably this is
	 * a contact point for a client. If a client gets one of these we should enable easy contact
	 * to resolve ASAP
	 * @param tag String
	 * @param message String
	 * @return Unit
	 */
	fun wtf(tag: String, message: String, sendLogToDB: Boolean)
	{
		//Something went incredibly wrong
		print(LogLevels.ASSERT, getR89Tag(tag), message, sendLogToDB)
	}
	
	/**
	 * Used for logging the Prebid SDK result codes when using GAM
	 * @param tag String
	 * @param resultCode ResultCode
	 * @param sendLogToDB Boolean
	 * @return Unit
	 */
	fun logFetchDemandResult(
		tag: String,
		resultCode: String,
		message: String,
		sendLogToDB: Boolean = false,
	)
	{
		if (logLevel.hasEqualOrHigherPriority(LogLevels.DEBUG))
		{
			d(tag, message, sendLogToDB)
		} else
		{
			i(tag, "Result code: $resultCode", sendLogToDB)
		}
	}
	
	/**
	 * Logs the Publisher Scheme
	 * @param data PublisherScheme
	 * @return Unit
	 */
	fun logPublisherScheme(data: PublisherScheme)
	{
		serializationLibrary?.let {
			val jsonString = it.toPrettyJson(PublisherScheme.serializer(), data)
			i("JSON Response", jsonString, false)
		} ?: run {
			e("LogUtil", "SerializationLibrary is null", false)
		}
	}
	
	/**
	 * Logs the Publisher Data
	 * @param data PublisherData
	 * @return Unit
	 */
	fun logPublisherData(data: PublisherData)
	{
		serializationLibrary?.let {
			
			val jsonString = it.toPrettyJson(PublisherData.serializer(), data)
			i("JSON Converted", jsonString, false)
		} ?: run {
			e("LogUtil", "SerializationLibrary is null", false)
		}
		
	}
	
	/**
	 * method to log anything not suppose to be used outside of the class use anything with a set priority
	 * @param priority Int
	 * @param tag String
	 * @param message String
	 * @param sendLogToDB Boolean
	 * @return Unit
	 */
	private fun print(priority: LogLevels, tag: String, message: String, sendLogToDB: Boolean)
	{
		if (osLogger == null)
		{
			println("Please initialize the SDK Before using the IOSLogger")
			return
		} else
		{
			if (replaceOsLoggerWithPublic)
			{
				publicLogger?.print(priority, tag, message)
			} else
			{
				osLogger?.print(priority, tag, message)
				publicLogger?.print(priority, tag, message)
			}
		}
		
		if ((sendLogToDB && deviceUUIDForLogs.isNotEmpty()) || sendAllLogs)
		{
			sendLogToServer(priority, tag, message, deviceUUIDForLogs)
		}
	}
	
	/**
	 * Method for sending the log to a backend service
	 *  @param priority Int
	 *  @param tag String
	 *  @param message String
	 */
	private fun sendLogToServer(priority: LogLevels, tag: String, message: String, deviceUUID: String)
	{
		if (logToServerUseCase == null)
		{
			println("Please initialize the SDK Before using the logToServerUseCase")
			return
		}
		
		logToServerUseCase?.invoke(
			priority = priority.name,
			tag = tag,
			message = message,
			deviceUUID = deviceUUID,
			onSuccess = {
				i(TAG, "Log sent to server")
			},
			onFailure = {
				e(TAG, it)
			}
		)
	}
	
	/**
	 * Ads the SDK tag to the tag provided by the user of the utility in any method
	 * usefully for filtering the logs to know which were generated by the SDK First Party
	 * others might be generated by the SDK but not directly by it
	 * @param subTag String
	 * @return String
	 */
	private fun getR89Tag(subTag: String): String
	{
		
		if (subTag.isEmpty())
		{
			return R89_BASE_SDK_TAG
		}
		
		val result = StringBuilder()
		result.append(R89_BASE_SDK_TAG).append(subTag)
		
		return if (result.length > 23)
		{
			result.substring(0, 22)
		} else
		{
			result.toString()
		}
	}
	
	fun setPublicLogger(logger: IR89Logger, replaceOsLogger: Boolean)
	{
		replaceOsLoggerWithPublic = replaceOsLogger
		publicLogger = logger
	}
	
	/**
	 * @suppress
	 */
	const val TAG = "LogUtil"
	
}