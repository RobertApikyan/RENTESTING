package com.refinery89.sharedcore.os_layer

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
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
import com.refinery89.sharedcore.os_layer.internal_toolbox.date_time.R89DateFactoryAndroidImpl
import com.refinery89.sharedcore.os_layer.log_tool.IOSLoggerAndroidImpl
import com.refinery89.sharedcore.os_layer.single_tag.SingleTagOsLibraryAndroidImpl
import com.refinery89.sharedcore.os_layer.third_party_libraries.CMPLibraryAndroidImpl
import com.refinery89.sharedcore.os_layer.third_party_libraries.GoogleAdsLibraryAndroidImpl
import com.refinery89.sharedcore.os_layer.third_party_libraries.PrebidLibraryAndroidImpl
import com.refinery89.sharedcore.os_layer.third_party_libraries.SerializationLibraryAndroidImp
import com.refinery89.sharedcore.os_layer.third_party_libraries.SimpleDataSaveAndroidImpl
import com.refinery89.sharedcore.os_layer.third_party_libraries.UUIDLibraryAndroidImpl
import com.refinery89.sharedcore.os_layer.web_view.WebViewHelperAndroidImpl
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig
import kotlinx.coroutines.Dispatchers

internal class DependencyProviderAndroidImpl(
	private val application: Application,
	private val apiVersion: String,
) : DependencyProvider
{
	private val prefsMapStore: MutableMap<String, ISimpleDataSave> = mutableMapOf()
	
	private var osLogger: IOSLogger? = null
	
	private val uuidLibrary = UUIDLibraryAndroidImpl()
	private val dateFactory = R89DateFactoryAndroidImpl()
	
	private val managerApiClient: HttpClient = createKtorClient()
	
	private val serializationLibrary: SerializationLibrary = SerializationLibraryAndroidImp()
	
	private val webViewHelper: IWebViewHelper = WebViewHelperAndroidImpl()
	
	private val uiExecutorLibrary: UiExecutorLibrary = createUiExecutorLibrary()
	
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
		//TODO this function works as a fabricator more like a dependency provider we are not providing any dependencies here that could change in the future just using objects
		// from the domain to the domain there is no need for actual dependency injection here
		val scope = CoroutineManager.getR89Scope()
		return SendLogRequest(scope, Dispatchers.IO, AnalyticsApiService(managerApiClient))
	}
	
	override fun provideGlobalConfigurator(): R89GlobalConfigurator
	{
		//TODO make this a unique object
		val prebidLibrary = PrebidLibraryAndroidImpl(application)
		val cmpLibrary = CMPLibraryAndroidImpl(application)
		val googleAdsLibrary = GoogleAdsLibraryAndroidImpl(application)
		val singleTagLibrary = SingleTagOsLibraryAndroidImpl(application)
		
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
		return if (osLogger == null)
		{
			osLogger = IOSLoggerAndroidImpl()
			osLogger!!
		} else
		{
			osLogger!!
		}
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
			val prefs = application.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
			prefsMapStore[prefsName] = SimpleDataSaveAndroidImpl(prefs)
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
		return webViewHelper
	}
	
	private fun createKtorClient(): HttpClient
	{
		val androidSpecificClientConfig: HttpClientConfig<OkHttpConfig>.() -> Unit = {
			commonKtorClientSettings<OkHttpConfig>()
		}
		return HttpClient(OkHttp, androidSpecificClientConfig)
	}
	
	private fun createUiExecutorLibrary(): UiExecutorLibrary
	{
		return object : UiExecutorLibrary
		{
			private val uiHandler = Handler(Looper.getMainLooper())
			override fun executeInMainThread(block: () -> Unit)
			{
				if (Looper.getMainLooper().thread == Thread.currentThread())
				{
					block()
				} else
				{
					uiHandler.post(block)
				}
			}
		}
	}
}