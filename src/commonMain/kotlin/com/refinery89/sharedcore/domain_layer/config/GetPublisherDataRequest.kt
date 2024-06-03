package com.refinery89.sharedcore.domain_layer.config

import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.domain_layer.models.PublisherData
import com.refinery89.sharedcore.domain_layer.repositories.ManagerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Responsibilities of this class are:
 * - Execute the request in a coroutine, for abstracting the way coroutines work and
 * @property scope CoroutineScope
 * @property dispatcher CoroutineDispatcher
 * @property managerRepository ManagerRepository
 * @constructor
 */
internal class GetPublisherDataRequest(
	private val scope: CoroutineScope,
	private val dispatcher: CoroutineDispatcher,
	private val managerRepository: ManagerRepository,
)
{
	/**
	 * Executes the responsibilities of the class
	 * @param pubId publisher identifier
	 * @param appId publisher's app identifier
	 * @param onSuccess receives the [PublisherData] object
	 * @param onFailure an error occurred Network, validation or conversion
	 * @return Unit
	 */
	operator fun invoke(pubId: String, appId: String, onSuccess: (data: PublisherData) -> Unit, onFailure: (error: Throwable) -> Unit)
	{
		scope.launch(dispatcher) {
			val result = managerRepository.getPublisherInfo(pubId, appId, R89SDKCommon.isDebug)
			
			result.onSuccess {
				onSuccess(it)
			}.onFailure {
				onFailure(it)
			}
		}
	}
}