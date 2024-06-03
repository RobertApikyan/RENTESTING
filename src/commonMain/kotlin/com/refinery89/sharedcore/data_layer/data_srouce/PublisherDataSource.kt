package com.refinery89.sharedcore.data_layer.data_srouce

import com.refinery89.sharedcore.data_layer.schemes_models.GeneralDataScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.PublisherRequestBody
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.PublisherScheme

/**
 * This is the Interface for retrieving publisher data that the domain layer uses.
 *
 */
internal interface PublisherDataSource
{
	/**
	 * This function is used to retrieve publisher data. for later conversion
	 * @param body PublisherRequestBody
	 * @param apiVersion String version of the api
	 * @return PublisherScheme
	 */
	suspend fun getPublisherInfo(body: PublisherRequestBody, apiVersion: String): GeneralDataScheme<PublisherScheme>
}