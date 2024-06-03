package com.refinery89.sharedcore

import com.refinery89.sharedcore.R89SDKCommon.isInitialized
import com.refinery89.sharedcore.domain_layer.config.ConfigBuilder
import com.refinery89.sharedcore.domain_layer.config.GetPublisherDataRequest
import com.refinery89.sharedcore.domain_layer.config.R89GlobalConfigurator
import com.refinery89.sharedcore.domain_layer.initialization_state.InitializationEvents
import com.refinery89.sharedcore.domain_layer.initialization_state.SDKInitState
import com.refinery89.sharedcore.domain_layer.initialization_state.SDKInitStateHandler
import com.refinery89.sharedcore.domain_layer.log_tool.IR89Logger
import com.refinery89.sharedcore.domain_layer.log_tool.LogLevels
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.PublisherData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.ISimpleDataSave
import com.refinery89.sharedcore.domain_layer.third_party_libraries.UUIDLibrary
import com.refinery89.sharedcore.domain_layer.third_party_libraries.UiExecutorLibrary
import com.refinery89.sharedcore.domain_layer.web_view.IWebViewHelper

internal interface R89SDKCommonApi
{
	
	fun setDebug(
		getLocalFakeData: Boolean = true,
		forceCMP: Boolean = false,
		useProductionAuctionServer: Boolean = false
	)
	
	fun setLogLevel(level: LogLevels)
	
	fun resetConsent()
}

internal object R89SDKCommon : R89SDKCommonApi
{
	/**
	 * Backing fields for properties that are initialized after the SDK is initialized
	 */
	private var _dependencyProvider: DependencyProvider? = null
	private var _webViewHelper: IWebViewHelper? = null
	private var _globalConfig: R89GlobalConfigurator? = null
	private var _uiExecutor: UiExecutorLibrary? = null
	private var _getPublisherData: GetPublisherDataRequest? = null
	
	/**
	 * make sure to use [isInitialized] before using this
	 */
	private val dependencyProvider: DependencyProvider
		get()
		{
			if (_dependencyProvider == null || !isInitialized(TAG))
			{
				throw IllegalStateException("make sure to use [isInitialized] before using dependencyProvider")
			}
			return _dependencyProvider!!
		}
	
	/**
	 * make sure to use [isInitialized] before using this
	 */
	private val uiExecutor: UiExecutorLibrary
		get()
		{
			if (_uiExecutor == null || !isInitialized(TAG))
			{
				throw IllegalStateException("make sure to use [isInitialized] before using uiExecutor")
			}
			return _uiExecutor!!
		}
	
	
	/**
	 * make sure to use [isInitialized] before using this
	 */
	internal val globalConfig: R89GlobalConfigurator
		get()
		{
			if (_globalConfig == null || !isInitialized(TAG))
			{
				throw IllegalStateException("make sure to use [isInitialized] before using globalConfig")
			}
			return _globalConfig!!
		}
	
	
	/**
	 * make sure to use [isInitialized] before using this
	 */
	internal val webViewHelper: IWebViewHelper
		get()
		{
			if (_webViewHelper == null || !isInitialized(TAG))
			{
				throw IllegalStateException("make sure to use [isInitialized] before using webViewHelper")
			}
			return _webViewHelper!!
		}
	
	/**
	 * make sure to use [isInitialized] before using this
	 */
	private val getPublisherData: GetPublisherDataRequest
		get()
		{
			if (_getPublisherData == null || !isInitialized(TAG))
			{
				throw IllegalStateException("make sure to use [isInitialized] before using getPublisherData")
			}
			return _getPublisherData!!
		}
	
	private val sdkInitStateHandler = SDKInitStateHandler()

///////////////////////////////////////////////SDK Internal Flags OR SDK State
	
	/**
	 * User and sdk have to use the function to set this value User can't read
	 * value sdk can read value from anywhere BUT SHALL only be read on classes
	 * directly below this class in sdk flow terms.
	 */
	var isDebug = false
		private set
	
	var usingCMP = true
		private set
	
	var isSingleLineOn = false
		private set
	var useFakeLocalData = false
		private set
	
	private var isManualConfig = false
	
	//TODO remove this once the API 2.0 is released
	internal var productionAuctionServer: Boolean = true
		private set
	
	/**
	 * This is true after [applyConfigurationAfterCMP] is called
	 */
	private var hasConsentData = false
	
	/**
	 * The SDK is considered to be initialized right after calling the [initialize] function or the [initializeWithConfigBuilder] function
	 */
	private var initialized = false
	
	internal var sdkPublisherId = ""
		private set
	internal var sdkAppId = ""
		private set
	
	/**
	 * This is all the flags that need to be set from both methods [initialize] and [initializeWithConfigBuilder]
	 * @param publisherId String
	 * @param isManual Boolean
	 * @param isSingleLine Boolean
	 */
	private fun setInitializationFlags(
		publisherId: String,
		appId: String,
		isManual: Boolean,
		isSingleLine: Boolean,
	)
	{
		hasConsentData = false
		initialized = false
		isSingleLineOn = isSingleLine
		isManualConfig = isManual
		sdkPublisherId = publisherId
		sdkAppId = appId
	}
	
	/**
	 * If r89_send_logs_to_db boolean is true we set uuid in R89LogUtil
	 * @return Unit
	 */
	private fun setDeviceUUID(simpleDataStore: ISimpleDataSave, uuidLibrary: UUIDLibrary)
	{
		if (simpleDataStore.getBoolean("r89_send_logs_to_db", false))
		{
			// if r89_device_uuid already exists
			if (simpleDataStore.contains("r89_device_uuid"))
			{
				val uuid: String = simpleDataStore.getString("r89_device_uuid", null)!!
				R89LogUtil.setDeviceUUIDForLogs(uuid)
			}
			//if it does not exist we generate new one and save it to shared preferences
			else
			{
				val uuid = uuidLibrary.generate()
				simpleDataStore.edit().putString("r89_device_uuid", uuid).apply()
				R89LogUtil.setDeviceUUIDForLogs(uuid)
			}
		}
	}
	
	/**
	 * all the libraries, Third Party SDKs, and objects that don't require user consent can be initialized here
	 * if something needs consent please execute it on [applyConfigurationAfterCMP]
	 */
	private fun initializeBeforeDataAndConsent(publisherInitializationEvents: InitializationEvents?)
	{
		initialized = true
		
		sdkInitStateHandler.setPublicEventsListener(publisherInitializationEvents)
		
		val frequencyCapSimpleDataStore = dependencyProvider.provideSimpleDataStore("r89_frequency_cap")
		val sendLogPrefsSimpleDataStore = dependencyProvider.provideSimpleDataStore("r89_log_prefs")
		
		val osUtilityLibrary = dependencyProvider.provideOSUtilLibrary()
		val sendLogRequest = dependencyProvider.provideSendLogs()
		val serializationLibrary = dependencyProvider.provideSerializationLibrary()
		val dateFactory = dependencyProvider.provideDateFactory()
		val uuidLibrary = dependencyProvider.provideUUIDLibrary()
		
		_uiExecutor = dependencyProvider.provideUiExecutor()
		_webViewHelper = dependencyProvider.provideWebViewHelper()
		_globalConfig = dependencyProvider.provideGlobalConfigurator()
		_getPublisherData = dependencyProvider.provideGetPublisherData()
		
		R89AdFactoryCommon.provideContextDependantDependencies(frequencyCapSimpleDataStore, dateFactory, serializationLibrary)
		R89LogUtil.provideDependencies(sendLogRequest, osUtilityLibrary, serializationLibrary)
		
		setDeviceUUID(sendLogPrefsSimpleDataStore, uuidLibrary)
		
		globalConfig.prePublisherDataConfigurations()
		
		R89LogUtil.i(TAG, "SDK Initialized")
	}
	
	/**
	 * This is called after we receive the publisher data from the Refinery Database
	 * @param data PublisherData
	 * @return Unit
	 */
	private fun initializeAfterData(data: PublisherData)
	{
		globalConfig.postPublisherDataConfigurations(data)
		globalConfig.getUserPrivacyTool().onCMPFinished.subscribe {
			applyConfigurationAfterCMP()
		}
		
		sdkInitStateHandler.setState(SDKInitState.DATA_FETCH_SUCCESS)
		
		try
		{
			sdkInitStateHandler.setState(SDKInitState.STARTED_CMP)
			globalConfig.startCMP()
		} catch (e: Exception)
		{
			R89LogUtil.e(TAG, e)
		}
	}
	
	/**
	 * This is called after the CMP is closed and we have the consent data.
	 * Therefore also after the sdk has been initialized.
	 * @return Unit
	 */
	private fun applyConfigurationAfterCMP()
	{
		executeInMainThread {
			hasConsentData = true
			try
			{
				globalConfig.applyPublisherConfiguration()
				sdkInitStateHandler.setState(SDKInitState.CMP_FINISHED)
			} catch (e: Exception)
			{
				R89LogUtil.e(TAG, e)
			}
			sdkInitStateHandler.setState(SDKInitState.INITIALIZATION_FINISHED)
		}
		R89LogUtil.i(TAG, "SDK After CMP")
	}
	
	fun initialize(
		dependencyProvider: DependencyProvider,
		publisherId: String,
		appId: String,
		singleLine: Boolean,
		publisherInitializationEvents: InitializationEvents?,
	)
	{
		this._dependencyProvider = dependencyProvider
		setInitializationFlags(publisherId, appId, isManual = false, isSingleLine = singleLine)
		
		initializeBeforeDataAndConsent(publisherInitializationEvents)
		
		sdkInitStateHandler.setState(SDKInitState.STARTED_INITIALIZATION)
		
		//retrieve publisher data
		if (isManualConfig)
		{
			R89LogUtil.e(
				TAG,
				"Refinery SDK is configured to use Manual Config and it's trying to fetch publisher data from data base",
				sendLogToDB = true
			)
		} else
		{
			sdkInitStateHandler.setState(SDKInitState.STARTED_DATA_FETCH)
			getPublisherData.invoke(publisherId, appId,
				onSuccess = { data ->
					initializeAfterData(data)
				},
				onFailure = {
					R89LogUtil.e(TAG, it)
					sdkInitStateHandler.setState(SDKInitState.DATA_FETCH_FAILED)
				})
		}
	}
	
	fun initializeWithConfigBuilder(
		dependencyProvider: DependencyProvider,
		publisherId: String,
		appId: String,
		configBuilder: ConfigBuilder,
		publisherInitializationEvents: InitializationEvents?,
	)
	{
		this._dependencyProvider = dependencyProvider
		
		setInitializationFlags(publisherId, appId, isManual = true, isSingleLine = false)
		
		initializeBeforeDataAndConsent(publisherInitializationEvents)
		
		sdkInitStateHandler.setState(SDKInitState.STARTED_INITIALIZATION)
		
		val data = configBuilder.build()
		
		initializeAfterData(data)
	}
	
	/**
	 * if Set to true makes the SDK debug mode which means:
	 * - test host and account id will be used from the configuration unless realServer flag is changed
	 * - all cmp configurations will be fake
	 * - global configuration will be fake including publisher and user configurations, unless realServer flag is true
	 */
	override fun setDebug(
		getLocalFakeData: Boolean,
		forceCMP: Boolean,
		useProductionAuctionServer: Boolean,
	)
	{
		isDebug = true
		usingCMP = forceCMP
		useFakeLocalData = getLocalFakeData
		productionAuctionServer = useProductionAuctionServer
	}
	
	/**
	 * Shows you different logs depending on what you indicate
	 * @param level LogLevels
	 */
	override fun setLogLevel(level: LogLevels)
	{
		R89LogUtil.setLogLevel(level)
	}
	
	/**
	 * Deletes all local data saved about the CMP, and next time the CMP is asked if it needs to show or not it will require showing since all consent that was previously given has been deleted
	 */
	override fun resetConsent()
	{
		if (!isInitialized(TAG))
		{
			R89LogUtil.e(TAG, "Please initialize the SDK before calling resetConsent", false)
			return
		}
		globalConfig.getUserPrivacyTool().resetCMP()
	}
	
	
	/**
	 * Checks if the SDK has consent data if not logs an error with the tag
	 * provided so the user knows where it was thrown from
	 *
	 * @return true if the SDK has called the CMP false if not
	 */
	fun hasConsentData(tag: String): Boolean
	{
		if (!hasConsentData)
		{
			R89LogUtil.w(tag, "R89SDK has no consent data", false)
		}
		return hasConsentData
	}
	
	/**
	 * Checks if the SDK is initialized if not logs an error with the tag
	 * provided so the user knows where it was thrown from.
	 *
	 * The SDK is considered to be initialized right after calling the [initialize] function or the [initializeWithConfigBuilder] function
	 *
	 * @return true if the SDK is initialized false if not
	 */
	fun isInitialized(tag: String): Boolean
	{
		if (!initialized)
		{
			R89LogUtil.e(tag, "SDK has not been initialized", false)
		}
		return initialized
	}
	
	/**
	 * Do not call this before you are sure the sdk has been initialized, for this use [isInitialized] method
	 * @param executeThis Function0<Unit>
	 * @return Unit
	 * @throws NullPointerException if the SDK has not been initialized
	 */
	fun executeInMainThread(executeThis: () -> Unit)
	{
		uiExecutor.executeInMainThread(executeThis)
	}
	
	fun subscribeToInitEvents(listener: InitializationEvents)
	{
		sdkInitStateHandler.subscribeToInternalEvents(listener)
	}
	
	fun stateIs(state: SDKInitState): Boolean
	{
		return sdkInitStateHandler.stateIs(state)
	}
	
	fun getUserAgent(webView: Any, siteName: String): String
	{
		if (!isInitialized(TAG))
		{
			R89LogUtil.e(TAG, "Please initialize the SDK before calling getUserAgent", false)
			return ""
		}
		return "${webViewHelper.getUserAgent(webView)} [android-app-$siteName]"
	}
	
	fun configureWebView(webView: Any, userAgent: String, webViewClient: Any)
	{
		if (!isInitialized(TAG))
		{
			R89LogUtil.e(TAG, "Please initialize the SDK before calling configureWebView", false)
			return
		}
		
		if (!webViewHelper.isWebViewType(webView, TAG) && !webViewHelper.isWebViewClientType(webViewClient, TAG))
		{
			R89LogUtil.e(TAG, "Please use a WebView or R89WebViewClient when calling configureWebView", false)
			return
		}
		
		globalConfig.configureWebViewForGoogleLibrary(webView)
		
		//We add a user agent that can be tracked to mobile apps
		//TODO use regex to recognize if the pattern from [getUserAgent] is present
		webViewHelper.setUserAgent(webView, userAgent)
		
		//Our WebViewClient that ads some functionalities to the web view
		webViewHelper.setWebViewClient(webView, webViewClient)
		
	}
	
	fun loadUrlWithConsentDataOrWait(webView: Any, url: String, tag: String)
	{
		if (!isInitialized(tag))
		{
			R89LogUtil.e(tag, "Please initialize the SDK before calling loadUrlWithConsentDataOrWait", false)
			return
		}
		
		if (!webViewHelper.isWebViewType(webView, TAG))
		{
			R89LogUtil.e(tag, "Please use a WebView when calling loadUrlWithConsentDataOrWait", false)
			return
		}
		
		//checks if the SDK has failed and will not be able to obtain consent
		if (stateIs(SDKInitState.DATA_FETCH_FAILED))
		{
			R89LogUtil.e(
				tag,
				"SDK can't obtain consent data, it's impossible because something failed gracefully",
				false
			)
			webViewHelper.loadUrl(webView, url)
			return
		}
		
		//Adds the consent data to the url if it can't it will wait for the state of the sdk to change
		if (!hasConsentData(tag))
		{
			R89LogUtil.w(tag, "SDK has no consent data YET", false)
			loadUrlWaitingForState(webView, url, tag)
			return
		}
		
		loadUrlWithConsentData(webView, url, tag)
	}
	
	private fun loadUrlWaitingForState(webView: Any, url: String, tag: String)
	{
		R89LogUtil.d(tag, "Waiting for consent data to load url", false)
		subscribeToInitEvents(object : InitializationEvents()
		{
			override fun dataFetchFailed()
			{
				R89LogUtil.e(
					tag,
					"SDK can't obtain consent data, because data fetch failed",
					false
				)
				webViewHelper.loadUrl(webView, url)
			}
			
			override fun initializationFinished()
			{
				R89LogUtil.d(tag, "SDK has consent data now, loading url with it", false)
				loadUrlWithConsentData(webView, url, tag)
			}
		})
	}
	
	/**
	 * Make sure the SDK is initialized and has consent data before calling this method, use [isInitialized] and [hasConsentData] at some point before calling this.
	 *
	 * @throws NullPointerException if the SDK has not been initialized
	 * @param url String
	 * @return String this string will be empty if the SDK has not received consent data
	 */
	private fun loadUrlWithConsentData(webView: Any, url: String, tag: String)
	{
		val urlWithConsent = url + globalConfig.getUserPrivacyTool().getConsentDataForUrl()
		R89LogUtil.d(TAG, "Loading url with consent: $urlWithConsent", false)
		
		webViewHelper.loadUrl(webView, url)
	}
	
	fun setR89HelperLogger(logger: IR89Logger, replaceOsLogger: Boolean = false)
	{
		R89LogUtil.setPublicLogger(logger, replaceOsLogger)
	}
	
	private const val TAG = ""
}


