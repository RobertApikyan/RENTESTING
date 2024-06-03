package com.refinery89.sharedcore.os_layer

import com.refinery89.sharedcore.DependencyProvider
import com.refinery89.sharedcore.data_layer.data_srouce.mock.ManagerMockData
import com.refinery89.sharedcore.data_layer.data_srouce.remote.ManagerApiService
import com.refinery89.sharedcore.data_layer.repositories_impl.DataConverter
import com.refinery89.sharedcore.data_layer.repositories_impl.DataRequester
import com.refinery89.sharedcore.data_layer.repositories_impl.DataValidator
import com.refinery89.sharedcore.data_layer.repositories_impl.ManagerRepositoryImpl
import com.refinery89.sharedcore.domain_layer.analytics.AnalyticsApiService
import com.refinery89.sharedcore.domain_layer.analytics.SendLogRequest
import com.refinery89.sharedcore.domain_layer.config.GetPublisherDataRequest
import com.refinery89.sharedcore.domain_layer.config.R89GlobalConfigurator
import com.refinery89.sharedcore.domain_layer.config.consentConfig.UserPrivacyConfigurator
import com.refinery89.sharedcore.domain_layer.config.prebid.PrebidConfigurator
import com.refinery89.sharedcore.domain_layer.config.targetConfig.TargetAppConfigurator
import com.refinery89.sharedcore.domain_layer.internalToolBox.CoroutineManager
import com.refinery89.sharedcore.domain_layer.internalToolBox.date_time.R89DateFactory
import com.refinery89.sharedcore.domain_layer.log_tool.IOSLogger
import com.refinery89.sharedcore.domain_layer.singleTag.SingleTagConfigurator
import com.refinery89.sharedcore.domain_layer.third_party_libraries.ISimpleDataSave
import com.refinery89.sharedcore.domain_layer.third_party_libraries.SerializationLibrary
import com.refinery89.sharedcore.domain_layer.third_party_libraries.UUIDLibrary
import com.refinery89.sharedcore.domain_layer.third_party_libraries.UiExecutorLibrary
import com.refinery89.sharedcore.domain_layer.web_view.IWebViewHelper
import com.refinery89.sharedcore.os_layer.internal_toolbox.date_time.R89DateFactoryIosImpl
import com.refinery89.sharedcore.os_layer.log_tool.IOSLoggerIosImpl
import com.refinery89.sharedcore.os_layer.single_tag.SingleTagOsLibraryIosImpl
import com.refinery89.sharedcore.os_layer.third_party_library.CMPLibraryIosImpl
import com.refinery89.sharedcore.os_layer.third_party_library.GoogleAdsLibraryIosImpl
import com.refinery89.sharedcore.os_layer.third_party_library.PrebidLibraryIosImpl
import com.refinery89.sharedcore.os_layer.third_party_library.SerializationLibraryIosImp
import com.refinery89.sharedcore.os_layer.third_party_library.SimpleDataSaveIosImpl
import com.refinery89.sharedcore.os_layer.third_party_library.UUIDLibraryIosImpl
import com.refinery89.sharedcore.os_layer.third_party_library.UiExecutorIosImpl
import com.refinery89.sharedcore.os_layer.web_veiw.WebViewHelperIosImpl
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.engine.darwin.DarwinClientEngineConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.ExperimentalSerializationApi

internal class DependencyProviderIosImpl(
	private val apiVersion: String
) : DependencyProvider
{
	private val managerApiClient: HttpClient = createKtorClient()
	
	private val prefsMapStore: MutableMap<String, ISimpleDataSave> = mutableMapOf()
	private val osLogger: IOSLogger = IOSLoggerIosImpl()
	private val uuidLibrary: UUIDLibrary = UUIDLibraryIosImpl()
	private val uiExecutorLibrary: UiExecutorLibrary = UiExecutorIosImpl()
	private val serializationLibrary: SerializationLibrary = SerializationLibraryIosImp()
	private val dateFactory = R89DateFactoryIosImpl()
	
	@OptIn(ExperimentalSerializationApi::class)
	override fun provideGetPublisherData(): GetPublisherDataRequest
	{
		//TODO this function works as a fabricator more like a dependency provider we are not providing any dependencies here that could change in the future just using objects
		// from the domain to the domain there is no need for actual dependency injection here
		val dataRequester = DataRequester(
			remoteDataSource = ManagerApiService(managerApiClient),
			fakeDataSource = ManagerMockData(),
			apiVersion = apiVersion
		)
		val dataValidator = DataValidator(serializationLibrary)
		val dataConverter = DataConverter(serializationLibrary)
		val managerRepository = ManagerRepositoryImpl(dataRequester, dataValidator, dataConverter)
		
		val scope = CoroutineManager.getR89Scope()
		return GetPublisherDataRequest(scope, Dispatchers.IO, managerRepository)
	}
	
	override fun provideSendLogs(): SendLogRequest
	{
		val scope = CoroutineManager.getR89Scope()
		return SendLogRequest(scope, Dispatchers.IO, AnalyticsApiService(managerApiClient))
	}
	
	override fun provideGlobalConfigurator(): R89GlobalConfigurator
	{
		//TODO make this a unique object
		val prebidLibrary = PrebidLibraryIosImpl()
		val cmpLibrary = CMPLibraryIosImpl()
		val googleAdsLibrary = GoogleAdsLibraryIosImpl()
		val singleTagLibrary = SingleTagOsLibraryIosImpl()
		
		// todo this could be done from inside the R89GlobalConfigurator just passing the actual libraries that can change
		
		//TODO this function works as a fabricator more like a dependency provider we are not providing any dependencies here that could change in the future just using objects
		// from the domain to the domain there is no need for actual dependency injection here
		val prebidConfigurator = PrebidConfigurator(prebidLibrary)
		val userPrivacyConfigurator = UserPrivacyConfigurator(prebidLibrary, cmpLibrary)
		val targetAppConfigurator = TargetAppConfigurator(prebidLibrary)
		val singleTaTagConfigurator = SingleTagConfigurator(singleTagLibrary)
		
		return R89GlobalConfigurator(
			prebidConfigurator,
			userPrivacyConfigurator,
			targetAppConfigurator,
			googleAdsLibrary,
			singleTaTagConfigurator
		)
	}
	
	override fun provideOSUtilLibrary(): IOSLogger
	{
		return osLogger
	}
	
	override fun provideUUIDLibrary(): UUIDLibrary
	{
		return uuidLibrary
	}
	
	override fun provideSimpleDataStore(prefsName: String): ISimpleDataSave
	{
		//TODO(@Apikyan): Try to keep this logic inside ISimpleDataSave
		return if (prefsMapStore.containsKey(prefsName))
		{
			prefsMapStore[prefsName]!!
		} else
		{
			prefsMapStore[prefsName] = SimpleDataSaveIosImpl(prefsName)
			prefsMapStore[prefsName]!!
		}
	}
	
	override fun provideUiExecutor(): UiExecutorLibrary
	{
		return uiExecutorLibrary
	}
	
	override fun provideSerializationLibrary(): SerializationLibrary
	{
		return serializationLibrary
	}
	
	override fun provideDateFactory(): R89DateFactory
	{
		return dateFactory
	}
	
	override fun provideWebViewHelper(): IWebViewHelper
	{
		return WebViewHelperIosImpl()
	}
	
	private fun createKtorClient(): HttpClient
	{
		val appleSpecificClientConfig: HttpClientConfig<DarwinClientEngineConfig>.() -> Unit = {
			commonKtorClientSettings<DarwinClientEngineConfig>()
		}
		return HttpClient(Darwin, appleSpecificClientConfig)
	}
}