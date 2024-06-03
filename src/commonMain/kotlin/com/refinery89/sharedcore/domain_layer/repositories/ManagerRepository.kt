package com.refinery89.sharedcore.domain_layer.repositories

import com.refinery89.sharedcore.domain_layer.models.PublisherData

/**
 * Responsibilities of this class are:
 * - allow testings of the SDK
 * - yeah this interface is not used as a contract to another entities more that to us, for using it on tests
 */
internal interface ManagerRepository
{
	/**
	 *  look to [com.refinery89.androidSdk.data_layer.repositories_impl.ManagerRepositoryImpl] for more information
	 * @param pubId String
	 * @param appId String
	 * @param debugCall Boolean
	 * @return Result<PublisherData>
	 */
	suspend fun getPublisherInfo(
		pubId: String,
		appId: String,
		debugCall: Boolean
	): Result<PublisherData>
}