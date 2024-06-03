package com.refinery89.sharedcore.data_layer.repositories_impl

import com.refinery89.sharedcore.domain_layer.internalToolBox.internal_error_handling_tool.R89Error
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.PublisherData
import com.refinery89.sharedcore.domain_layer.repositories.ManagerRepository

/**
 * Responsibilities of this class are:
 * - Get the data with the [dataRequester]
 * - Check if the data is valid for domain with [dataValidator]
 * - Convert the data to domain models with [dataConverter]
 * - throw errors for each of these steps
 *
 * @property dataRequester ManagerApiService
 */
internal class ManagerRepositoryImpl(
	private val dataRequester: DataRequester,
	private val dataValidator: DataValidator,
	private val dataConverter: DataConverter,
) : ManagerRepository
{
	/**
	 *
	 * @param pubId the publisher id with the [appId] is used to get the data for the app
	 * @param appId the app id with the [pubId] is used to get the data for the app
	 * @param debugCall flag that is sent to the server to return a special response
	 * @return Result<PublisherData>
	 */
	override suspend fun getPublisherInfo(
		pubId: String,
		appId: String,
		debugCall: Boolean
	): Result<PublisherData>
	{
		return try
		{
			val result = dataRequester.getData(pubId, appId, debugCall)
				?: throw R89Error.PublisherNotReceived()
			
			dataValidator.checkOrThrowPublisherData(result)
			val convertedResult = dataConverter.convertToDomainModel(result)
			R89LogUtil.logPublisherScheme(result)
			R89LogUtil.logPublisherData(convertedResult)
			
			Result.success(convertedResult)
		} catch (e: Exception)
		{
			Result.failure(e)
		}
	}
	
}