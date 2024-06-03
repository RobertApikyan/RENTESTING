package com.refinery89.sharedcore.data_layer.repositories_impl

import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.data_layer.data_srouce.PublisherDataSource
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.PublisherRequestBody
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.PublisherScheme
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil

/**
 * Responsibilities of this class are:
 * - Decide where is the data coming from (fake or server)
 * - If [R89SDKCommon.useFakeLocalData] is true, then the data will be fetched from [fakeDataSource]
 * - If [R89SDKCommon.useFakeLocalData] is false, then the data will be fetched from [remoteDataSource]
 */
internal class DataRequester(
	private val remoteDataSource: PublisherDataSource,
	private val fakeDataSource: PublisherDataSource,
	private val apiVersion: String,
)
{
	
	/**
	 * The function that does the responsibility of the object [DataRequester]
	 *
	 * @param pubId
	 * @param appId
	 * @param debugCall
	 * @return
	 */
	suspend fun getData(pubId: String, appId: String, debugCall: Boolean): PublisherScheme?
	{
		val requestBody = PublisherRequestBody(pubId, appId, debugCall)
		
		return if (R89SDKCommon.useFakeLocalData)
		{
			R89LogUtil.i(TAG, "Publisher Data fetch from local data")
			fakeDataSource.getPublisherInfo(requestBody, apiVersion).data
		} else
		{
			R89LogUtil.i(TAG, "Publisher PRODUCTION Data fetch from server")
			remoteDataSource.getPublisherInfo(requestBody, apiVersion).data
		}
	}
	
	/**
	 * @suppress
	 */
	companion object
	{
		private const val TAG = "DataRequester"
	}
}