package com.refinery89.sharedcore.domain_layer.internalToolBox.auxiliarEvents

internal class VoidEventManager
{
	private val events: MutableList<() -> Unit> = ArrayList()
	
	/**
	 * invokes all the subscribed events
	 */
	fun invoke()
	{
		for (event in events)
		{
			event.invoke()
		}
	}
	
	/**
	 * subscribes an event to the observer/Manager
	 * @param event VoidEvent
	 * @return Unit
	 */
	fun subscribe(event: () -> Unit)
	{
		events.add(event)
	}
	
	/**
	 * unsubscribes an event from the observer/Manager
	 * @param event VoidEvent
	 */
	fun unSubscribe(event: () -> Unit)
	{
		events.remove(event)
	}
	
	/**
	 * unsubscribes/clears all the events from the observer/Manager
	 */
	fun unSubscribeAll()
	{
		events.clear()
	}
}