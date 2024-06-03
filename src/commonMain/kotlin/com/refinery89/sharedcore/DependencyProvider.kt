package com.refinery89.sharedcore

import com.refinery89.sharedcore.domain_layer.analytics.SendLogRequest
import com.refinery89.sharedcore.domain_layer.config.GetPublisherDataRequest
import com.refinery89.sharedcore.domain_layer.config.R89GlobalConfigurator
import com.refinery89.sharedcore.domain_layer.internalToolBox.date_time.R89DateFactory
import com.refinery89.sharedcore.domain_layer.log_tool.IOSLogger
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.third_party_libraries.ISimpleDataSave
import com.refinery89.sharedcore.domain_layer.third_party_libraries.SerializationLibrary
import com.refinery89.sharedcore.domain_layer.third_party_libraries.UUIDLibrary
import com.refinery89.sharedcore.domain_layer.third_party_libraries.UiExecutorLibrary
import com.refinery89.sharedcore.domain_layer.web_view.IWebViewHelper
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal interface DependencyProvider
{
	companion object
	{
		//		private const val BASE_URL = "https://4378cbc2-0c79-43b3-b8b2-b26211da2c7e.mock.pstmn.io"
		private const val BASE_URL = "https://manager.refinery89.com/api/"
	}
	
	fun provideGetPublisherData(): GetPublisherDataRequest
	fun provideSendLogs(): SendLogRequest
	fun provideGlobalConfigurator(): R89GlobalConfigurator
	fun provideOSUtilLibrary(): IOSLogger
	fun provideUUIDLibrary(): UUIDLibrary
	fun provideSimpleDataStore(prefsName: String): ISimpleDataSave
	fun provideUiExecutor(): UiExecutorLibrary
	fun provideSerializationLibrary(): SerializationLibrary
	fun provideDateFactory(): R89DateFactory
	fun provideWebViewHelper(): IWebViewHelper
	
	fun <T : HttpClientEngineConfig> commonKtorClientSettings(): HttpClientConfig<T>.() -> Unit
	{
		return {
			expectSuccess = true
			defaultRequest {
				header("Content-Type", "application/json")
				url(BASE_URL)
			}
			//Timeout plugin to set up timeout milliseconds for client
			install(HttpTimeout) {
				socketTimeoutMillis = 60_000
				requestTimeoutMillis = 60_000
			}
			
			install(Logging) {
				logger = Logger.DEFAULT
				level = LogLevel.ALL
				logger = object : Logger
				{
					override fun log(message: String)
					{
						R89LogUtil.d("NetworkClientsFactory", message)
					}
				}
			}
			
			install(ContentNegotiation) {
				json(Json {
					prettyPrint = true
					isLenient = true
					explicitNulls = false
					ignoreUnknownKeys = true
				})
			}
		}
	}
}

