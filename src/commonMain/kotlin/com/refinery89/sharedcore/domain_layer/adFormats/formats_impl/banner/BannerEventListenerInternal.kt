package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner

import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.formatsBasesEvents.BasicEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.formatsBasesEvents.CloseEventListener
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.formatsBasesEvents.OpenEventListener


internal interface BannerEventListenerInternal : BasicEventListener, CloseEventListener, OpenEventListener
{
	/**
	 * Called when something happens to the layout make sure to check if width and height are the same as the last time
	 * @param width Int
	 * @param height Int
	 * @return Unit
	 */
	fun onLayoutChange(width: Int, height: Int)
}
