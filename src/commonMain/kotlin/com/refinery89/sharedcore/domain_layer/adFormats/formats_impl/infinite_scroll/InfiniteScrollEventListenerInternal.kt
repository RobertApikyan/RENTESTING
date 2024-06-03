package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll

import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError

internal interface InfiniteScrollEventListenerInternal
{
	/**
	 * Callback for when we roll to know if an ad should be loaded after the item,
	 * this is just an event that happens before the roll
	 *
	 * @param itemIdInData Id that is assigned to the data, order id in the data
	 */
	fun onRoll(itemIdInData: Int)
	
	
	/**
	 * Callback for when a roll for an item says there is no ad in that item.
	 * Failed might be misunderstood, since is not failed in an error or warning, but the roll simply said we should not load an ad in this item, is not for error catching is
	 * just a thing that can happen due to the probability distribution of the infinite scroll configuration
	 *
	 * @param itemIdInData Id that is assigned to the data
	 */
	fun onRollFailed(itemIdInData: Int)
	
	
	/**
	 * Callback for when a roll succeeds, so we are going to create a view inside it
	 *
	 * @param adapterId Id of an adapter
	 * @param probability Probability of the item containing an ad
	 */
	fun onAdItemCreated(adapterId: Int, probability: Double)
	
	
	/**
	 * Callback that happens when we are using more advanced infinite scroll techniques, where we might not find a valid wrapper inside the item
	 * @param message String information about the error
	 */
	fun onAdItemFailedToCreate(message: String)
	
	/**
	 * Callback for when the ad that was rolled successfully for an item, actually loads and is ready to be shown
	 *
	 * @param itemIdInData Id that is assigned to the data
	 */
	fun onAdItemLoaded(itemIdInData: Int)
	
	/**
	 * Callback for when the ad that was rolled successfully for an item, failed to load
	 *
	 * @param itemIdInData Id that is assigned to the data
	 */
	fun onAdItemFailedToLoad(itemIdInData: Int, error: R89LoadError)
	
	/**
	 * Callback for when the ad that was rolled successfully for an item, is shown
	 *
	 * @param itemIdInData Id that is assigned to the data
	 */
	fun onAdItemImpression(itemIdInData: Int)
	
	/**
	 * Callback for when the ad that was rolled successfully for an item, is clicked
	 *
	 * @param itemIdInData Id that is assigned to the data
	 */
	fun onAdItemClick(itemIdInData: Int)
	
	/**
	 * Callback for when the ad that was rolled successfully for an item, is opened
	 *
	 * @param itemIdInData Id that is assigned to the data
	 */
	fun onAdItemOpen(itemIdInData: Int)
	
	/**
	 * Callback for when the ad that was rolled successfully for an item, is closed
	 *
	 * @param itemIdInData Id that is assigned to the data
	 */
	fun onAdItemClose(itemIdInData: Int)
}