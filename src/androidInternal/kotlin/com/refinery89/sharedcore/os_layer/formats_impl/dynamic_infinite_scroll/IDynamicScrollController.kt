package com.refinery89.sharedcore.os_layer.formats_impl.dynamic_infinite_scroll

import android.view.View

/**
 * Used to create Different ways to manage children inside infinite scroll views in the Android
 */
internal interface IDynamicScrollController
{
	/**
	 * When provided a child that is inside the scroll view this will return a stable id, stable meaning it will always be the same id no matter how many children get added or
	 * removed from the scroll view
	 * @param view View
	 * @return Int
	 */
	fun getStableIdFromView(view: View): Int
}