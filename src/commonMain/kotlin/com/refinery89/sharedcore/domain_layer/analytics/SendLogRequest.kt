package com.refinery89.sharedcore.domain_layer.analytics

import com.refinery89.sharedcore.R89SDKCommon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Responsibilities of this class are:
 * - Execute the request in a coroutine, for abstracting the way coroutines work
 * - Send the log to the server
 * @property scope CoroutineScope
 * @property dispatcher CoroutineDispatcher
 * @property analyticsService ManagerRepository
 */
internal class SendLogRequest(
	private val scope: CoroutineScope,
	private val dispatcher: CoroutineDispatcher,
	private val analyticsService: AnalyticsApiService,
)
{
	/**
	 * Executes the responsibilities of the class
	 * @param priority priority of the Log read more in [com.refinery89.androidSdk.domain_layer.log_tool.LogLevels]
	 * @param tag the context of the information
	 * @param message the information to be logged
	 * @param deviceUUID the device UUID authorized to be logging to the server
	 * @param onSuccess server responded with 200
	 * @param onFailure server responded with something
	 */
	operator fun invoke(priority: String, tag: String, message: String, deviceUUID: String, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit)
	{
		scope.launch(dispatcher) {
			val result = sendLog(priority, tag, message, deviceUUID, R89SDKCommon.sdkPublisherId, R89SDKCommon.sdkAppId)
			
			result.onSuccess {
				onSuccess()
			}.onFailure {
				onFailure(it)
			}
		}
	}
	
	private suspend fun sendLog(priority: String, tag: String, message: String, deviceUUID: String, pbId: String, appId: String): Result<Unit>
	{
		return try
		{
			val bodyToSend = LogRequestBody(priority, tag, message, deviceUUID, pbId, appId)
			Result.success(analyticsService.sendLog(bodyToSend))
		} catch (e: Exception)
		{
			Result.failure(e)
		}
	}
}