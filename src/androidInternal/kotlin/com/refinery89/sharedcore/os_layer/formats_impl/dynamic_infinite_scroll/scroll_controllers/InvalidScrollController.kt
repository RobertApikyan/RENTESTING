package com.refinery89.sharedcore.os_layer.formats_impl.dynamic_infinite_scroll.scroll_controllers

import android.view.View
import com.refinery89.sharedcore.os_layer.formats_impl.dynamic_infinite_scroll.IDynamicScrollController

/**
 * Invalid scroll controller used as a placeholder when the Configuration is not correct
 *
 * @constructor Create empty Invalid scroll controller
 */
internal class InvalidScrollController : IDynamicScrollController
{
	/**
	 * always returns invalid id
	 * @param view View
	 * @return Int
	 */
	override fun getStableIdFromView(view: View): Int
	{
		return -1
	}
}