package com.refinery89.sharedcore.domain_layer.initialization_state

import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil


internal class SDKInitStateHandler
{
	
	/**
	 * This is used by the factory and anything that needs to be executed after everything has finished
	 * cmp, prebid initialization, etc.
	 */
	private val internalInitializationEventsListener: InitializationEventsManager = InitializationEventsManager()
	
	/**
	 * This is used to notify the user of the SDK, in case he implements it
	 */
	private var publicInitializationEvents: InitializationEvents? = null
	
	private var state: SDKInitState = SDKInitState.NOT_INITIALIZED
	
	
	/**
	 * Used to call for state changes in the SDK
	 */
	private val initializationEvents: IInitializationEvents = object : IInitializationEvents
	{
		override fun startedInitialization()
		{
			publicInitializationEvents?.startedInitialization()
			internalInitializationEventsListener.startedInitialization()
		}
		
		override fun startedDataFetch()
		{
			publicInitializationEvents?.startedDataFetch()
			internalInitializationEventsListener.startedDataFetch()
		}
		
		override fun dataFetchSuccess()
		{
			publicInitializationEvents?.dataFetchSuccess()
			internalInitializationEventsListener.dataFetchSuccess()
		}
		
		override fun dataFetchFailed()
		{
			publicInitializationEvents?.dataFetchFailed()
			internalInitializationEventsListener.dataFetchFailed()
		}
		
		override fun startedCMP()
		{
			publicInitializationEvents?.startedCMP()
			internalInitializationEventsListener.startedCMP()
		}
		
		override fun cmpFinished()
		{
			publicInitializationEvents?.cmpFinished()
			internalInitializationEventsListener.cmpFinished()
		}
		
		override fun initializationFinished()
		{
			publicInitializationEvents?.initializationFinished()
			internalInitializationEventsListener.initializationFinished()
		}
		
	}
	
	fun setState(state: SDKInitState)
	{
		
		R89SDKCommon.executeInMainThread {
			
			if (SDKInitState.INITIALIZATION_FINISHED == this.state)
			{
				R89LogUtil.w("SDKInitStateHandler", "SDK already finish initialization", false)
				return@executeInMainThread
			}
			
			if (this.state.ordinal > state.ordinal)
			{
				R89LogUtil.w("SDKInitStateHandler", "State can't go back", false)
				return@executeInMainThread
			}
			
			if (this.state == state)
			{
				R89LogUtil.w("SDKInitStateHandler", "State already set", false)
				return@executeInMainThread
			}
			
			this.state = state
			
			when (state)
			{
				SDKInitState.STARTED_INITIALIZATION  -> initializationEvents.startedInitialization()
				SDKInitState.STARTED_DATA_FETCH      -> initializationEvents.startedDataFetch()
				SDKInitState.DATA_FETCH_SUCCESS      -> initializationEvents.dataFetchSuccess()
				SDKInitState.DATA_FETCH_FAILED       -> initializationEvents.dataFetchFailed()
				SDKInitState.STARTED_CMP             -> initializationEvents.startedCMP()
				SDKInitState.CMP_FINISHED            -> initializationEvents.cmpFinished()
				SDKInitState.INITIALIZATION_FINISHED ->
				{
					initializationEvents.initializationFinished()
					clearInternalEvents()
				}
				
				else                                 -> R89LogUtil.e("SDKInitStateHandler", "State not found", false)
			}
		}
		
	}
	
	fun setPublicEventsListener(listener: InitializationEvents?)
	{
		publicInitializationEvents = listener
	}
	
	fun subscribeToInternalEvents(listener: InitializationEvents)
	{
		internalInitializationEventsListener.subscribe(listener)
	}
	
	
	private fun clearInternalEvents()
	{
		internalInitializationEventsListener.clear()
	}
	
	fun stateIs(other: SDKInitState): Boolean
	{
		return state == other
	}
}