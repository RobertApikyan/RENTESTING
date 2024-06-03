package com.refinery89.sharedcore.data_layer.data_srouce.remote

import com.refinery89.sharedcore.data_layer.data_srouce.PublisherDataSource
import com.refinery89.sharedcore.data_layer.schemes_models.GeneralDataScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.PublisherRequestBody
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.PublisherScheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

internal class ManagerApiService(private val ktorClient: HttpClient) : PublisherDataSource
{
	override suspend fun getPublisherInfo(body: PublisherRequestBody, apiVersion: String): GeneralDataScheme<PublisherScheme>
	{
		val response: GeneralDataScheme<PublisherScheme> = ktorClient.post {
			url("manager-crud/publisher-info/v$apiVersion")
			setBody(body)
		}.body()
		return response
	}
}