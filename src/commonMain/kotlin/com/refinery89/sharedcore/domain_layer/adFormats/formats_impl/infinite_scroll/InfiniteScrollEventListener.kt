package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll

import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError

/**
 * Infinite Scroll life cycle goes as follows:
 * 1. [InfiniteScrollEventListenerInternal.onRoll] Called when the infinite scroll is about to roll for an item, this is just an event that happens before the roll or
 * [InfiniteScrollEventListenerInternal.onRollFailed] Called when the infinite scroll roll for an item says there is no ad in that item.
 * 3. [InfiniteScrollEventListenerInternal.onAdItemCreated] Called when the infinite scroll roll succeeds, so we are going to create a view inside it or
 * [InfiniteScrollEventListenerInternal.onAdItemFailedToCreate] Called when the infinite scroll roll succeeds, but we failed to create a view inside it, this is an actual error not
 * like [InfiniteScrollEventListenerInternal.onRollFailed] which is just a thing that can happen due to the probability distribution of the infinite scroll configuration
 * 4. [InfiniteScrollEventListenerInternal.onAdItemLoaded] Called when the ad that was rolled successfully for an item, actually loads and is ready to be shown or
 * [InfiniteScrollEventListenerInternal.onAdItemFailedToLoad] Called when the ad that was rolled successfully for an item, failed to load, also an error
 * 5. [InfiniteScrollEventListenerInternal.onAdItemImpression] Called when the ad loaded in the item is shown for the first time
 * 6. [InfiniteScrollEventListenerInternal.onAdItemClick] Called when the ad loaded in the item is clicked
 * 7. [InfiniteScrollEventListenerInternal.onAdItemOpen] Called when the ad loaded in the item is opened
 * 8. [InfiniteScrollEventListenerInternal.onAdItemClose] Called when the ad loaded in the item is closed
 */
abstract class InfiniteScrollEventListener : InfiniteScrollEventListenerInternal
{
	override fun onRoll(itemIdInData: Int)
	{
	}
	
	override fun onRollFailed(itemIdInData: Int)
	{
	}
	
	override fun onAdItemCreated(adapterId: Int, probability: Double)
	{
	}
	
	override fun onAdItemFailedToCreate(message: String)
	{
	}
	
	override fun onAdItemLoaded(itemIdInData: Int)
	{
	}
	
	override fun onAdItemFailedToLoad(itemIdInData: Int, error: R89LoadError)
	{
	}
	
	override fun onAdItemImpression(itemIdInData: Int)
	{
	}
	
	override fun onAdItemClick(itemIdInData: Int)
	{
	}
	
	override fun onAdItemOpen(itemIdInData: Int)
	{
	}
	
	override fun onAdItemClose(itemIdInData: Int)
	{
	}
}