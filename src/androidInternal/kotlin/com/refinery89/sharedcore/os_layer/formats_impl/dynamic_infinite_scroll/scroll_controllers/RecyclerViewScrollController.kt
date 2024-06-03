package com.refinery89.sharedcore.os_layer.formats_impl.dynamic_infinite_scroll.scroll_controllers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.refinery89.sharedcore.os_layer.formats_impl.dynamic_infinite_scroll.IDynamicScrollController

internal class RecyclerViewScrollController(private val rv: RecyclerView) : IDynamicScrollController
{
	/**
	 * Uses the recycler view default method to get a stable id
	 * @param view View
	 * @return Int
	 */
	override fun getStableIdFromView(view: View): Int
	{
		return rv.getChildAdapterPosition(view)
	}
}