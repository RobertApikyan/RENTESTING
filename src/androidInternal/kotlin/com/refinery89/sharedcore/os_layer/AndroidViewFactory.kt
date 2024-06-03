package com.refinery89.sharedcore.os_layer

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import com.refinery89.sharedcore.R
import com.refinery89.sharedcore.domain_layer.models.single_tag.WrapperData
import com.refinery89.sharedcore.os_layer.extensions.dpToPx
import com.refinery89.sharedcore.os_layer.extensions.getFirstViewWithTag
import com.refinery89.sharedcore.os_layer.extensions.getOnClickListener
import com.refinery89.sharedcore.os_layer.extensions.getParentAsViewGroup
import com.refinery89.sharedcore.os_layer.extensions.getViewsByTag
import com.refinery89.sharedcore.os_layer.extensions.pxToDp
import com.squareup.picasso.Picasso

// TODO DELETE THIS CLASS MOST OF THE METHODS ARE USED IN ONLY ONE OTHER FILE WHICH ALREADY HAS PERMISSION TO USE ANDROID DEPENDENCIES,
//  so there is not point anymore in having this class until we reuse methods or something.
//  going to put comments on how to delete methods
/**
 * @suppress
 */
internal object AndroidViewFactory
{
	//TODO just use this method as is in the Single Tag Library
	fun getCurrentOnClickListenerFromView(view: View): () -> Unit
	{
		val currentListener = view.getOnClickListener()
		return {
			currentListener?.onClick(view)
		}
	}
	
	//TODO just use this method as is in the Single Tag Library
	fun setViewOnClickListener(view: View, event: () -> Unit)
	{
		view.setOnClickListener {
			event()
		}
	}
	
	//TODO just use this method as is in the R89Wrapper
	fun getCloseButton(wrapperContext: Context, anchorViewId: Int, adLargestWidth: Int, imageUrl: String): ImageView
	{
		//Define all the inputs for a button
		val resources = wrapperContext.resources
		val screenWidthPx = resources.displayMetrics.widthPixels
		val screenWidthDp = wrapperContext.pxToDp(screenWidthPx)
		val buttonSize = getCloseButtonSizeInDp()
		
		//placeButtonOnTop is true if the full width of the ad + the button size is bigger than the screen width
		val placeButtonOnTop = adLargestWidth + buttonSize > screenWidthDp
		
		val closeButton = ImageView(wrapperContext)
		
		closeButton.id = View.generateViewId()
		//Create and modify default layout params
		closeButton.layoutParams = ConstraintLayout.LayoutParams(
			ConstraintLayout.LayoutParams.WRAP_CONTENT,
			ConstraintLayout.LayoutParams.WRAP_CONTENT
		)
		//Modify layout params to set the image size
		closeButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
			width = wrapperContext.dpToPx(buttonSize)
			height = wrapperContext.dpToPx(buttonSize)
		}
		
		//Load the image for the close button or use the default one if the image url is empty
		val defaultImage = ResourcesCompat.getDrawable(resources, R.drawable.close_button, null)!!
		if (imageUrl.isEmpty())
		{
			closeButton.setImageDrawable(defaultImage)
		} else
		{
			Picasso.get()
				.load(imageUrl)
				.error(defaultImage)
				.into(closeButton)
		}
		
		if (placeButtonOnTop)
		{
			closeButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
				endToEnd = anchorViewId
				bottomToTop = anchorViewId
			}
			
		} else
		{
			closeButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
				startToEnd = anchorViewId
				topToTop = anchorViewId
			}
		}
		
		ViewCompat.setElevation(closeButton, 10.0f)
		
		return closeButton
	}
	
	//TODO just use this method as is in the R89Wrapper
	fun getCloseButtonSizeInDp(): Int
	{
		return 16
	}
	
	//TODO use this method from the styling framework dmitry is building
	fun centerViewHorizontallyInParent(view: View)
	{
		//TODO make this method receive a style to be formatted
		when (view.parent)
		{
			is RelativeLayout -> view.updateLayoutParams<RelativeLayout.LayoutParams> {
				addRule(RelativeLayout.CENTER_HORIZONTAL)
			}
			//TODO add more layouts
//			is LinearLayout     -> wrapper.layoutParams
//			is ConstraintLayout -> wrapper.layoutParams
//			is FrameLayout      -> wrapper.layoutParams
		}
	}
	
	//TODO use this method from the styling framework dmitry is building
	fun centerContentOfView(view: View)
	{
		when (view)
		{
			is LinearLayout   -> view.gravity = Gravity.CENTER
			is RelativeLayout -> view.gravity = Gravity.CENTER
			//TODO add more layouts
//			is LinearLayout     -> wrapper.layoutParams
//			is ConstraintLayout -> wrapper.layoutParams
//			is FrameLayout      -> wrapper.layoutParams
		}
	}
	
	//TODO just use this method as is in the Single Tag Library
	fun createR89WrapperList(wrapperData: WrapperData, activity: Activity): List<ViewGroup>
	{
		val publisherWrappers = activity.getViewsByTag(wrapperData.wrapperTag)
		val outPutR89Wrappers = mutableListOf<ViewGroup>()
		
		publisherWrappers.forEach { publisherWrapper ->
			if (publisherWrapper is ViewGroup)
			{
				outPutR89Wrappers.add(configureWrapper(publisherWrapper, wrapperData))
			}
		}
		return outPutR89Wrappers
	}
	
	//TODO DELETE this and make sure everything is replicated and uses the official R89Wrapper
	// this is only used in the Single Tag Library
	fun createR89Wrapper(wrapperData: WrapperData, activity: Activity): ViewGroup
	{
		val publisherWrapper =
			activity.getFirstViewWithTag<ViewGroup>(wrapperData.wrapperTag) ?: throw Exception("The wrapper with tag: \"${wrapperData.wrapperTag}\" was not found")
		
		return configureWrapper(publisherWrapper, wrapperData)
	}
	
	//TODO DELETE this and make sure everything is replicated and uses the official R89Wrapper
	// this is only used in the Single Tag Library
	private fun configureWrapper(publisherWrapper: ViewGroup, wrapperData: WrapperData): ViewGroup
	{
		//Create a Basic ViewGroup that will be used to wrap the ad
		val wrapper = FrameLayout(publisherWrapper.context)
		wrapper.id = View.generateViewId()
		wrapper.layoutParams = FrameLayout.LayoutParams(
			FrameLayout.LayoutParams.WRAP_CONTENT,
			FrameLayout.LayoutParams.WRAP_CONTENT
		)
		
		//If it is not inside the publisher wrapper we need to place it in the hierarchy before or after the publisher wrapper
		if (wrapperData.relativePos.isInside())
		{
			publisherWrapper.addView(wrapper)
		} else
		{
			val insertAfter = wrapperData.relativePos.isAfter()
			
			val parent = placeWrapperInHierarchy(
				relativeTo = publisherWrapper,
				wrapper = wrapper,
				insertAfter = insertAfter,
			)
			
			setWrapperLayoutParamsDependingOnParentLayout(
				parent = parent,
				siblingRelativeTo = publisherWrapper,
				wrapper = wrapper,
				insertAfter = insertAfter
			)
			
		}
		
		return wrapper
	}
	
	//TODO DELETE this and make sure everything is replicated and uses the official R89Wrapper
	// this is only used in the Single Tag Library
	private fun placeWrapperInHierarchy(
		relativeTo: ViewGroup,
		wrapper: FrameLayout,
		insertAfter: Boolean,
	): ViewGroup
	{
		
		val errorMessage =
			"The wrapper with id: \"${wrapper.id}\" has no parent, we can't position ads ${if (insertAfter) "\"after\"" else "\"before\""} if the wrapper has no parent"
		val parent = relativeTo.getParentAsViewGroup() ?: throw Exception(errorMessage)
		
		val siblingIndex = parent.indexOfChild(relativeTo)
		
		if (siblingIndex != -1)
		{
			parent.addView(wrapper, siblingIndex + if (insertAfter) 1 else 0)
		}
		return parent
	}
	
	//TODO DELETE this and make sure everything is replicated and uses the official R89Wrapper
	// this is only used in the Single Tag Library
	private fun setWrapperLayoutParamsDependingOnParentLayout(parent: ViewGroup, siblingRelativeTo: ViewGroup, wrapper: FrameLayout, insertAfter: Boolean)
	{
		//TODO the current approach generates overlaps and we are missing tons of layouts
		when (parent)
		{
			is RelativeLayout ->
			{
				val afterOrBeforeRule = if (insertAfter) RelativeLayout.BELOW else RelativeLayout.ABOVE
				wrapper.updateLayoutParams<RelativeLayout.LayoutParams> {
					addRule(afterOrBeforeRule, siblingRelativeTo.id)
				}
			}
			//TODO add more layouts
//			is LinearLayout   -> return //Linear layout is already rendered correctly based on the view hierarchy
//			is ConstraintLayout -> wrapper.layoutParams
//			is FrameLayout      -> wrapper.layoutParams
		}
	}
	
	//TODO just use this method as is in the Single Tag Library
	fun getScrollView(wrapperData: WrapperData, activity: Activity): ViewGroup
	{
		return activity.getFirstViewWithTag(wrapperData.wrapperTag) ?: throw Exception("The scrollView with tag: \"${wrapperData.wrapperTag}\" was not found")
	}
	
	//TODO just use this method as is in the Single Tag Library
	fun getViewWithTag(buttonTag: String, activity: Activity): View
	{
		return activity.getFirstViewWithTag(buttonTag)!!
	}
}