package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.dynamic_infinite_scroll

/**
 * This is the interface [R89DynamicInfiniteScroll] uses to communicate with the OS layer
 */
internal interface IR89DynamicInfiniteScrollOS
{
	/**
	 * Used to get the events from the OS when the infinite scroll childs change
	 * @param scrollItemAdWrapperTag the tag to search for in the view hierarchy of the os to find the wrapper inside each child
	 * @param onChildAdded when a child is added to the hierarchy and it has a wrapper this will be called
	 * @param onChildDoesNotHaveWrapper when a child is added to the hierarchy and it does not have a wrapper this will be called
	 * @param onChildRemoved when a child is removed from the hierarchy this will be called
	 */
	fun setOnScrollChildrenChanged(
		scrollItemAdWrapperTag: String,
		onChildAdded: (itemId: Int, wrapper: Any) -> Unit,
		onChildDoesNotHaveWrapper: (itemId: Int) -> Unit,
		onChildRemoved: (itemId: Int) -> Unit,
	)
}