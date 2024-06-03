package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner

import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.formatsBasesEvents.BasicEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.formatsBasesEvents.CloseEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.formatsBasesEvents.OpenEventListener

/**
 * Banner life cycle goes as follows:
 * 1. [BasicEventListener.onLoaded]: Called when the banner is loaded and ready to be shown. If this goes wrong, the [BasicEventListener.onFailedToLoad] is called.
 * 2. [BasicEventListener.onImpression] Ad Is Shown and ready to be Clicked.
 * 3. [BasicEventListener.onClick] A Creative of the Ad is clicked. It can be a call to action creative or not.
 * 4. [OpenEventListener.onOpen] happens after [BasicEventListener.onClick], If it opens an Intent in the App to see the advertisement.
 * 5. [CloseEventListener.onClose] That Intent was closed and now we are back to your app.
 * - [BasicEventListener.onFailedToLoad] When this occurs, the factory marks the object for deletion and then invokes this event with an R89LoadError. You can use that to know
 * what happened.
 */
abstract class BannerEventListener : BannerEventListenerInternal
{
	override fun onLayoutChange(width: Int, height: Int)
	{
	}
	
	override fun onLoaded()
	{
	}
	
	override fun onFailedToLoad(error: R89LoadError)
	{
	}
	
	override fun onImpression()
	{
	}
	
	override fun onClick()
	{
	}
	
	override fun onClose()
	{
	}
	
	override fun onOpen()
	{
	}
}
