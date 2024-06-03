package com.refinery89.sharedcore.domain_layer.initialization_state

internal class InitializationEventsManager : IInitializationEvents
{
	private val events: MutableList<InitializationEvents> = mutableListOf()
	
	override fun startedInitialization()
	{
		events.forEach { it.startedInitialization() }
	}
	
	override fun startedDataFetch()
	{
		events.forEach { it.startedDataFetch() }
	}
	
	override fun dataFetchSuccess()
	{
		events.forEach { it.dataFetchSuccess() }
	}
	
	override fun dataFetchFailed()
	{
		events.forEach { it.dataFetchFailed() }
	}
	
	override fun startedCMP()
	{
		events.forEach { it.startedCMP() }
	}
	
	override fun cmpFinished()
	{
		events.forEach { it.cmpFinished() }
	}
	
	override fun initializationFinished()
	{
		events.forEach { it.initializationFinished() }
	}
	
	fun subscribe(event: InitializationEvents)
	{
		events.add(event)
	}
	
	fun unSubscribe(event: InitializationEvents)
	{
		events.remove(event)
	}
	
	fun clear()
	{
		events.clear()
	}
}