package com.refinery89.sharedcore.os_layer.formats_impl.dynamic_infinite_scroll

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.dynamic_infinite_scroll.IR89DynamicInfiniteScrollOS
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.os_layer.formats_impl.dynamic_infinite_scroll.scroll_controllers.InvalidScrollController
import com.refinery89.sharedcore.os_layer.formats_impl.dynamic_infinite_scroll.scroll_controllers.RecyclerViewScrollController

/**
 *
 * @property scrollWrapper the view that contains the scrollItems, the parent, the one that handles the scroll input logic
 * @property scrollController this is the controller that handles the scroll, it's different for every [scrollWrapper]
 */
internal class R89DynamicInfiniteScrollAndroidImpl(
	private val scrollWrapper: ViewGroup,
) : IR89DynamicInfiniteScrollOS
{
	private val scrollController: IDynamicScrollController = getScrollController()
	
	private fun getScrollController(): IDynamicScrollController
	{
		return if (scrollWrapper is RecyclerView)
		{
			RecyclerViewScrollController(scrollWrapper)
		} else
		{
			R89LogUtil.e(
				"D-Infinite-OS",
				"ScrollWrapper is not a valid type. Available Ones are RecyclerView",
				false
			)
			//This are just default values so the code compiles
			InvalidScrollController()
		}
	}
	
	override fun setOnScrollChildrenChanged(
		scrollItemAdWrapperTag: String,
		onChildAdded: (itemId: Int, wrapper: Any) -> Unit,
		onChildDoesNotHaveWrapper: (itemId: Int) -> Unit,
		onChildRemoved: (itemId: Int) -> Unit,
	)
	{
		scrollWrapper.setOnHierarchyChangeListener(object : ViewGroup.OnHierarchyChangeListener
		{
			override fun onChildViewAdded(parent: View, child: View)
			{
				val itemIdInData = scrollController.getStableIdFromView(child)
				if (itemHasWrapper(child, scrollItemAdWrapperTag))
				{
					val itemAdWrapper = getItemWrapper(child, scrollItemAdWrapperTag)
					
					//because we have checked that the child has a wrapper we can safely assert that is not null
					onChildAdded(itemIdInData, itemAdWrapper!!)
				} else
				{
					onChildDoesNotHaveWrapper(itemIdInData)
				}
			}
			
			override fun onChildViewRemoved(parent: View, child: View)
			{
				val itemPositionInDataToSearch = scrollController.getStableIdFromView(child)
				onChildRemoved(itemPositionInDataToSearch)
			}
		})
	}
	
	private fun getItemWrapper(itemView: Any, scrollItemAdWrapperTag: String): Any?
	{
		if (itemView !is View) return null
		
		return itemView.findViewWithTag(scrollItemAdWrapperTag)
	}
	
	private fun itemHasWrapper(itemView: Any, scrollItemAdWrapperTag: String): Boolean
	{
		if (itemView !is View) return false
		
		return itemView.findViewWithTag<ViewGroup>(scrollItemAdWrapperTag) != null
	}
}