package com.refinery89.sharedcore.domain_layer.analytics

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

/**
 * @suppress
 */
internal class AnalyticsApiService(private val ktorClient: HttpClient)
{
	suspend fun sendLog(info: LogRequestBody)
	{
		ktorClient.post {
			url("analytics/sendLogs")
			setBody(info)
		}
	}
}