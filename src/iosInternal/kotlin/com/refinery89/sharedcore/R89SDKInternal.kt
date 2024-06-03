package com.refinery89.sharedcore


import com.refinery89.sharedcore.domain_layer.config.ConfigBuilder
import com.refinery89.sharedcore.domain_layer.initialization_state.InitializationEvents
import com.refinery89.sharedcore.os_layer.DependencyProviderIosImpl
import R___Mobile_Native_Library.sharedCore.BuildConfig

internal object R89SDKInternal : R89SDKCommonApi by R89SDKCommon
{
	fun initialize(
		publisherId: String,
		appId: String,
		singleLine: Boolean,
		publisherInitializationEvents: InitializationEvents?
	)
	{
		R89SDKCommon.initialize(
			DependencyProviderIosImpl(apiVersion = BuildConfig.API_VERSION_STRING),
			publisherId,
			appId,
			singleLine,
			publisherInitializationEvents,
		)
	}
	
	fun initializeWithConfigBuilder(
		publisherId: String,
		appId: String,
		configBuilder: ConfigBuilder,
		publisherInitializationEvents: InitializationEvents?
	)
	{
		R89SDKCommon.initializeWithConfigBuilder(
			DependencyProviderIosImpl(apiVersion = BuildConfig.API_VERSION_STRING),
			publisherId,
			appId,
			configBuilder,
			publisherInitializationEvents,
		)
	}
}