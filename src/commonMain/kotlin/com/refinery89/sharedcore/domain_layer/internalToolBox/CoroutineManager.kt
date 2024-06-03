package com.refinery89.sharedcore.domain_layer.internalToolBox

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * Class responsible in creating a coroutine scope for the r89 sdk in which all the
 * coroutines from the sdk are wrapped in
 */
internal object CoroutineManager
{
	private val r89CoroutineScope: CoroutineScope = CoroutineScope(
		Dispatchers.IO +
				SupervisorJob() +
				CoroutineName("R89CoroutineScope")
	)
	
	/**
	 * All the coroutines launched from the sdk should be called from this scope
	 */
	fun getR89Scope(): CoroutineScope
	{
		return r89CoroutineScope
	}
	
	/**
	 * Method for canceling the sdk coroutine scope that cancels all running coroutines
	 */
	fun cancelScope()
	{
		r89CoroutineScope.cancel()
	}
}