package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.dynamic_infinite_scroll

import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.R89BaseAd
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll.InfiniteScrollEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll.R89InfiniteScroll
import com.refinery89.sharedcore.domain_layer.models.unit_config.AdUnitConfigData

/**
 * This class is just an easier way for the Single Tag to handle the infinite scroll
 * @property scrollItemAdWrapperTag the tag we need to search for inside the ScrollItems to put our ad Inside
 * @property r89InfiniteScroll the actual handler of the logic for rolling and showing the ads
 * @property generalEventListener lifecycle events for the infinite scroll
 */
internal class R89DynamicInfiniteScroll(
	private val osAdObject: IR89DynamicInfiniteScrollOS,
	private val r89InfiniteScroll: R89InfiniteScroll,
	private val scrollItemAdWrapperTag: String,
) : R89BaseAd()
{
	
	internal var generalEventListener: InfiniteScrollEventListenerInternal?
		set(value)
		{
			r89InfiniteScroll.generalEventListener = value
		}
		get()
		{
			return r89InfiniteScroll.generalEventListener
		}
	
	override fun cacheConfigurationValues(adConfigObject: AdUnitConfigData)
	{
		r89InfiniteScroll.configureAd(adConfigObject)
	}
	
	override fun configureAd(adConfigObject: AdUnitConfigData)
	{
		super.configureAd(adConfigObject)
		
		if (!isValid) return
		osAdObject.setOnScrollChildrenChanged(
			scrollItemAdWrapperTag,
			onChildAdded = { itemIdInData, itemAdWrapper ->
				//From 0 to X
				r89InfiniteScroll.getAdForIndex(itemIdInData, itemAdWrapper, null)
			},
			onChildDoesNotHaveWrapper = { itemIdInData ->
				generalEventListener?.onAdItemFailedToCreate("Item $itemIdInData doesn't have a wrapper with tag $scrollItemAdWrapperTag")
			},
			onChildRemoved = { itemPositionInDataToSearch ->
				
				r89InfiniteScroll.hideAdForIndex(itemPositionInDataToSearch)
			}
		)
	}
	
	override fun show(newWrapper: Any?)
	{
		if (isValid) return
		r89InfiniteScroll.show()
	}
	
	override fun hide()
	{
		r89InfiniteScroll.hide()
	}
	
	override fun invalidate()
	{
		hide()
		isValid = false
		r89InfiniteScroll.invalidate()
	}
	
	override fun destroy()
	{
		r89InfiniteScroll.destroy()
	}
}