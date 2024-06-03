package com.refinery89.sharedcore.domain_layer.initialization_state

internal interface IInitializationEvents
{
	
	fun startedInitialization()
	
	fun startedDataFetch()
	
	fun dataFetchSuccess()
	
	fun dataFetchFailed()
	
	fun startedCMP()
	
	fun cmpFinished()
	
	fun initializationFinished()
}