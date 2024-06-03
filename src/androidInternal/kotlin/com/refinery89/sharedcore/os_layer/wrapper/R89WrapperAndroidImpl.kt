package com.refinery89.sharedcore.os_layer.wrapper

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.wrapper_style.WrapperStyleData
import com.refinery89.sharedcore.domain_layer.wrapper_features.IR89Wrapper
import com.refinery89.sharedcore.domain_layer.wrapper_features.WrapperPosition
import com.refinery89.sharedcore.os_layer.AndroidViewFactory
import com.refinery89.sharedcore.os_layer.extensions.dpToPx

internal class R89WrapperAndroidImpl(
	private var publishersWrapper: ViewGroup,
	wrapperStyleData: WrapperStyleData?,
) : IR89Wrapper
{
	private var r89Wrapper: ConstraintLayout = ConstraintLayout(publishersWrapper.context)
	
	private lateinit var adCloseButton: ImageView
	private lateinit var labelView: TextView
	
	init
	{
		//TODO create a Style object inside AdUnitConfiguration to indicate styles of the ad and then apply it here
		AndroidViewFactory.centerViewHorizontallyInParent(publishersWrapper)
		AndroidViewFactory.centerContentOfView(publishersWrapper)
		
		r89Wrapper.id = View.generateViewId()
		r89Wrapper.layoutParams = ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT,
			ViewGroup.LayoutParams.WRAP_CONTENT
		)
		with(wrapperStyleData) {
			if (this != null)
			{
				//Set r89Wrapper position inside of publishers wrapper
				if (position != null)
				{
					position(position)
				}
				//Match r89Wrapper's height with publishers wrapper
				if (matchParentHeight != null && matchParentHeight)
				{
					matchParentHeight()
				}
				//Match r89Wrapper's width with publishers wrapper
				if (matchParentWidth != null && matchParentWidth)
				{
					matchParentWidth()
				}
				//Wrap content of an r89Wrapper
				if (wrapContent != null && wrapContent)
				{
					wrapContent()
				}
				//TODO maybe separate width and height
				if (height != null && width != null)
				{
					setSize(height, width)
				}
			}
		}
		
		publishersWrapper.addView(r89Wrapper)
		
	}
	
	override fun addAdCloseButton(
		adViewId: String,
		adLargestWidth: Int,
		imageUrl: String,
		onClick: () -> Unit,
	)
	{
		//Add button only if adView has been initialized by calling addAdView function
		if (!this::adCloseButton.isInitialized)
		{
			//16 is a width of the ad close button
			adCloseButton = AndroidViewFactory.getCloseButton(
				r89Wrapper.context,
				adViewId.toInt(),
				adLargestWidth,
				imageUrl
			)
			
			adCloseButton.setOnClickListener {
				onClick()
			}
			adCloseButton.visibility = View.GONE
			r89Wrapper.addView(adCloseButton)
		} else
		{
			R89LogUtil.w(
				TAG,
				"AdView has not been initialized yet, please call addAdView first or you already added a close button"
			)
		}
	}
	
	override fun addLabel()
	{
		labelView = TextView(publishersWrapper.context)
		labelView.id = View.generateViewId()
		
		//TODO: Extract to publisherInfo object with a field called labelText, this field is a map of language_code and label_text
		labelView.apply {
			text = "Advertising by refinery89"
			textSize = 14F
		}
		val labelLayoutParams = ConstraintLayout.LayoutParams(
			ConstraintLayout.LayoutParams.WRAP_CONTENT,
			ConstraintLayout.LayoutParams.WRAP_CONTENT
		).apply {
			startToStart = r89Wrapper.id
			topToTop = r89Wrapper.id
			endToEnd = r89Wrapper.id
			bottomToBottom = r89Wrapper.id
			horizontalBias = 0.5f
			verticalBias = 1f
		}
		
		labelView.layoutParams = labelLayoutParams
		
		r89Wrapper.addView(labelView)
		labelView.visibility = View.GONE
	}
	
	override fun show(newPublisherWrapper: Any?)
	{
		
		if (newPublisherWrapper !is ViewGroup?)
		{
			R89LogUtil.e(TAG, "newPublisherWrapper is not a ViewGroup", false)
			return
		}
		
		if (newPublisherWrapper != null)
		{
			publishersWrapper.removeView(r89Wrapper)
			publishersWrapper = newPublisherWrapper
		}
		
		publishersWrapper.addView(r89Wrapper)
	}
	
	override fun hide()
	{
		publishersWrapper.removeView(r89Wrapper)
	}
	
	override fun destroy()
	{
		r89Wrapper.removeAllViews()
		publishersWrapper.removeView(r89Wrapper)
	}
	
	override fun addAdView(adView: Any)
	{
		if (adView !is View)
		{
			R89LogUtil.e(TAG, "AdView is not a View", false)
			return
		}
		
		r89Wrapper.addView(adView)
		//whenever we ad an adView we are going to use it as our anchor view so we put it at the bottom center of the wrapper
		adView.layoutParams = ConstraintLayout.LayoutParams(
			ConstraintLayout.LayoutParams.WRAP_CONTENT,
			ConstraintLayout.LayoutParams.WRAP_CONTENT
		)
		
		adView.updateLayoutParams<ConstraintLayout.LayoutParams> {
			topToTop = r89Wrapper.id
			bottomToBottom = r89Wrapper.id
			startToStart = r89Wrapper.id
			endToEnd = r89Wrapper.id
			verticalBias = 1.0f
			horizontalBias = 0.5f
		}
		
	}
	
	/**
	 * This reserves the space for the ad taking into account all the configurations made to the wrapper so:
	 * - if we have close button we reserve the space for it too
	 * - etc.
	 * @param largestHeightDp Int
	 * @param largestWidthDp Int
	 * @return Unit
	 */
	override fun reserveSpace(largestHeightDp: Int, largestWidthDp: Int)
	{
		//TODO reserve also in width and take into account button state
		var heightToReserve = largestHeightDp
		
		if (this::adCloseButton.isInitialized)
		{
			heightToReserve += AndroidViewFactory.getCloseButtonSizeInDp()
		}
		
		r89Wrapper.updateLayoutParams<ViewGroup.LayoutParams> {
			height = publishersWrapper.context.dpToPx(heightToReserve)
		}
	}
	
	override fun wrapContent()
	{
		r89Wrapper.updateLayoutParams<ViewGroup.LayoutParams> {
			height = ViewGroup.LayoutParams.WRAP_CONTENT
			width = ViewGroup.LayoutParams.WRAP_CONTENT
		}
	}
	
	override fun showAdCloseButton()
	{
		if (this::adCloseButton.isInitialized)
		{
			adCloseButton.visibility = View.VISIBLE
		}
	}
	
	override fun hideAdCloseButton()
	{
		if (this::adCloseButton.isInitialized)
		{
			adCloseButton.visibility = View.GONE
		}
	}
	
	override fun showLabel()
	{
		if (this::labelView.isInitialized)
		{
			labelView.visibility = View.VISIBLE
		}
	}
	
	override fun hideLabel()
	{
		if (this::labelView.isInitialized)
		{
			labelView.visibility = View.GONE
		}
	}
	
	private fun setSize(height: Int, width: Int)
	{
		r89Wrapper.layoutParams.height = r89Wrapper.context.dpToPx(height)
		r89Wrapper.layoutParams.width = r89Wrapper.context.dpToPx(width)
	}
	
	private fun matchParentHeight()
	{
		r89Wrapper.updateLayoutParams<ViewGroup.LayoutParams> {
			height = ViewGroup.LayoutParams.MATCH_PARENT
		}
	}
	
	private fun matchParentWidth()
	{
		r89Wrapper.updateLayoutParams<ViewGroup.LayoutParams> {
			width = ViewGroup.LayoutParams.MATCH_PARENT
		}
	}
	
	private fun position(wrapperPosition: WrapperPosition)
	{
		//TODO we are changing publishers wrapper and that is not a good idea.
		//TODO plus we can not specify if we want it to be in the corner
		when (publishersWrapper)
		{
			is LinearLayout     ->
			{
				when (wrapperPosition)
				{
					WrapperPosition.CENTER -> (publishersWrapper as LinearLayout).gravity =
						Gravity.CENTER
					
					WrapperPosition.TOP    -> (publishersWrapper as LinearLayout).gravity = Gravity.TOP
					WrapperPosition.BOTTOM -> (publishersWrapper as LinearLayout).gravity =
						Gravity.BOTTOM
					
					WrapperPosition.START  -> (publishersWrapper as LinearLayout).gravity =
						Gravity.START
					
					WrapperPosition.END    -> (publishersWrapper as LinearLayout).gravity = Gravity.END
				}
			}
			
			is ConstraintLayout ->
			{
				r89Wrapper.id = View.generateViewId()
				val set = ConstraintSet()
				set.clone(publishersWrapper as ConstraintLayout)
				when (wrapperPosition)
				{
					WrapperPosition.TOP    -> set.connect(
						r89Wrapper.id,
						ConstraintSet.RIGHT,
						ConstraintSet.PARENT_ID,
						ConstraintSet.TOP
					)
					
					WrapperPosition.BOTTOM -> set.connect(
						r89Wrapper.id,
						ConstraintSet.RIGHT,
						ConstraintSet.PARENT_ID,
						ConstraintSet.BOTTOM
					)
					
					WrapperPosition.START  -> set.connect(
						r89Wrapper.id,
						ConstraintSet.RIGHT,
						ConstraintSet.PARENT_ID,
						ConstraintSet.START
					)
					
					WrapperPosition.END    -> set.connect(
						r89Wrapper.id,
						ConstraintSet.RIGHT,
						ConstraintSet.PARENT_ID,
						ConstraintSet.END
					)
					
					WrapperPosition.CENTER ->
					{
						set.connect(
							r89Wrapper.id,
							ConstraintSet.RIGHT,
							ConstraintSet.PARENT_ID,
							ConstraintSet.END
						)
						set.connect(
							r89Wrapper.id,
							ConstraintSet.RIGHT,
							ConstraintSet.PARENT_ID,
							ConstraintSet.START
						)
						set.connect(
							r89Wrapper.id,
							ConstraintSet.RIGHT,
							ConstraintSet.PARENT_ID,
							ConstraintSet.TOP
						)
						set.connect(
							r89Wrapper.id,
							ConstraintSet.RIGHT,
							ConstraintSet.PARENT_ID,
							ConstraintSet.BOTTOM
						)
					}
				}
				set.applyTo(publishersWrapper as ConstraintLayout)
			}
			
			is RelativeLayout   ->
			{
			
			}
		}
	}
	
	companion object
	{
		private const val TAG = "R89WrapperAndroidImpl"
	}
	
}