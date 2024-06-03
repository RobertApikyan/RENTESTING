package com.refinery89.sharedcore.domain_layer.ad_factory

import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.R89BaseAd
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll.R89InfiniteScroll
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil


/**
 * The responsibility of this object is to:
 * - hold the ad object data and access to it
 * - Serve an easy to use code api for the [R89BaseAd] operations
 * @property TAG String
 * @property adObjects MutableMap<Int, R89BaseAd>
 * @constructor
 */
internal class AdObjectOperator(private val TAG: String)
{
	private var lastAdId: Int = 0
	
	//TODO change this to a map so we can delete members without having to place fake objects when deleting them or having to notify the user
	private val adObjects: MutableMap<Int, R89BaseAd> = mutableMapOf()
	
	/**
	 * Ads an object to store and operate over it
	 * @param adObject R89BaseAd
	 * @return the returned ID of the ad is the previous amount of ads that were in the list before
	 */
	fun addAdObject(adObject: R89BaseAd): Int
	{
		adObjects[lastAdId] = adObject
		lastAdId++
		return lastAdId - 1
	}
	
	/**
	 * gives the next ID that the next added ad will have
	 * @return Int
	 */
	fun getLastAdId(): Int
	{
		return lastAdId
	}
	
	/**
	 *
	 * @param index the id / index of the ad that wants to be shown
	 * @param newWrapper if we want to show it in a new publisher wrapper if not this is null
	 * @return Unit
	 */
	fun show(index: Int, newWrapper: Any?)
	{
		if (!R89SDKCommon.hasConsentData(TAG)) return
		
		val adObject = adObjects.getValue(index)
		adObject.show(newWrapper)
	}
	
	/**
	 *
	 * @param index the id / index of the ad that wants to be shown
	 * @return Unit
	 */
	fun show(index: Int)
	{
		if (!R89SDKCommon.hasConsentData(TAG)) return
		
		val adObject = adObjects.getValue(index)
		adObject.show()
	}
	
	/**
	 *
	 * @param index the id / index of the ad that wants to be hidden
	 * @return Unit
	 */
	fun hide(index: Int)
	{
		if (!R89SDKCommon.hasConsentData(TAG) || !objectIsValid(index)) return
		
		val adObject = adObjects.getValue(index)
		
		R89LogUtil.d(TAG, "Hiding ad with index: $index")
		
		adObject.hide()
	}
	
	/**
	 * Invalidating an ads means is ready to be destroyed
	 * @param index the id / index of the ad that wants to be invalidated
	 * @return Unit
	 */
	fun invalidate(index: Int)
	{
		val adObject = adObjects.getValue(index)
		adObject.invalidate()
	}
	
	/** Safely destroys the ad object identified by [index]. */
	fun destroyAd(index: Int)
	{
		val adObject = adObjects.getValue(index)
		adObject.destroy()
		adObjects.remove(index)
	}
	
	/** Safely destroys all the ad objects. */
	fun destroyAds()
	{
		for ((_, adObject) in adObjects)
		{
			adObject.destroy()
		}
		adObjects.clear()
	}
	
	/** Checks and destroys adObjects if they are Invalid to show. */
	// TODO use this with some kind of automatic coroutine to keep freeing memory so the GB collector collects it and use this to notify out user if it's needed
	//  aka when on auto config or manual config
	fun cleanInvalidatedAdObjects()
	{
		//todo update the user if an ad with a using ID is removed
		val iterator = adObjects.iterator()
		while (iterator.hasNext())
		{
			val (_, adObject) = iterator.next()
			if (!adObject.adObjectIsValid())
			{
				iterator.remove()
				adObject.destroy()
			}
		}
	}
	
	/**
	 * Get in this case means create new or pull from existing data,
	 * if the same item we are trying to get the ad for was already tried we will have already caught the data.
	 *
	 * @param infiniteScrollId ad id / index of the infinite scroll that we want to pull an ad from
	 * @param itemIndex the publisher's item order in their data
	 * @param itemAdWrapper the publisher's wrapper for the specific item
	 * @return if we could create an ad or not
	 */
	fun getInfiniteScrollAdForIndex(
		infiniteScrollId: Int,
		itemIndex: Int,
		itemAdWrapper: Any,
		childAdLifecycle: BannerEventListenerInternal?,
	): Boolean
	{
		if (!R89SDKCommon.hasConsentData(TAG) || !objectIsValid(infiniteScrollId)) return false
		
		val adObject = adObjects.getValue(infiniteScrollId)
		if (adObject !is R89InfiniteScroll)
		{
			R89LogUtil.e(
				TAG,
				"Format with index: $infiniteScrollId is not an Infinite Scroll, you can't use getAdForIndex() fun with these",
				false
			)
			return false
		}
		
		return adObject.getAdForIndex(itemIndex, itemAdWrapper, childAdLifecycle)
	}
	
	/**
	 * Hides the ad for an item if existing
	 * @param infiniteScrollId Int
	 * @param itemIndex Int
	 */
	fun hideInfiniteScrollAdForIndex(infiniteScrollId: Int, itemIndex: Int)
	{
		if (!R89SDKCommon.hasConsentData(TAG) || !objectIsValid(infiniteScrollId)) return
		
		val adObject = adObjects.getValue(infiniteScrollId)
		if (adObject !is R89InfiniteScroll)
		{
			R89LogUtil.e(
				TAG,
				"Format with index: $infiniteScrollId is not an Infinite Scroll, you can't use hideAdForIndex() fun with these",
				false
			)
			return
		}
		
		adObject.hideAdForIndex(itemIndex)
	}
	
	/**
	 * Validates that the provided index is a valid index and if the ad corresponding to that index is valid
	 * @param index Int
	 * @return Boolean
	 */
	private fun objectIsValid(index: Int): Boolean
	{
		val indexIsInRange = (index in 0..<lastAdId)
		
		if (!indexIsInRange)
		{
			R89LogUtil.e(TAG, "Index is out of Range index: $index", false)
			return false
		}
		
		val keyExists = adObjects.containsKey(index)
		if (!keyExists)
		{
			R89LogUtil.e(TAG, "Index: $index is not set", false)
			return false
		}
		
		try
		{
			val adObject = adObjects.getValue(index)
			val objectIsValid = adObject.adObjectIsValid()
			if (!objectIsValid)
			{
				R89LogUtil.e(TAG, "Ad Object with Index: $index Is invalidated", false)
				return false
			}
			
			return true
		} catch (e: Exception)
		{
			R89LogUtil.e(TAG, "Ad Object Is null at Index: $index", false)
			return false
		}
		
	}
}