package com.refinery89.sharedcore

import com.refinery89.sharedcore.domain_layer.config.ConfigBuilder
import com.refinery89.sharedcore.domain_layer.initialization_state.InitializationEvents

object R89SDK : R89SDKCommonApi by R89SDKInternal {
	fun initialize(
		publisherId: String,
		appId: String,
		singleLine: Boolean,
		publisherInitializationEvents: InitializationEvents?
	)
	{
		R89SDKInternal.initialize(publisherId, appId, singleLine, publisherInitializationEvents)
	}
	
	fun initializeWithConfigBuilder(
		publisherId: String,
		appId: String,
		configBuilder: ConfigBuilder,
		publisherInitializationEvents: InitializationEvents?
	)
	{
		R89SDKInternal.initializeWithConfigBuilder(publisherId, appId, configBuilder, publisherInitializationEvents)
	}
}
