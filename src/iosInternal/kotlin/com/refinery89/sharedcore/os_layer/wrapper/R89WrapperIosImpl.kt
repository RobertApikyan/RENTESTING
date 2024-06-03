package com.refinery89.sharedcore.os_layer.wrapper

import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.wrapper_style.WrapperStyleData
import com.refinery89.sharedcore.domain_layer.wrapper_features.IR89Wrapper
import com.refinery89.sharedcore.os_layer.IosViewFactory
import com.refinery89.sharedcore.os_layer.extensions.bottomAligned
import com.refinery89.sharedcore.os_layer.extensions.constrainedEdges
import com.refinery89.sharedcore.os_layer.extensions.leadingAligned
import com.refinery89.sharedcore.os_layer.extensions.setOnClickListener
import com.refinery89.sharedcore.os_layer.extensions.topAligned
import com.refinery89.sharedcore.os_layer.extensions.trailingAligned
import platform.Foundation.NSUUID
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UIImageView
import platform.UIKit.UITextView
import platform.UIKit.UIView
import platform.UIKit.accessibilityLabel

internal class R89WrapperIosImpl(
	private var publishersWrapper: UIView,
	private val wrapperStyleData: WrapperStyleData?,
) : IR89Wrapper
{
	
	private var r89Wrapper: UIView = UIView().apply {
		backgroundColor = UIColor.greenColor
	}
	
	private lateinit var adCloseButton: UIImageView
	private lateinit var labelView: UITextView
	
	init
	{
		IosViewFactory.centerViewHorizontallyInParent(publishersWrapper)
		// we can not center the publisherWrapper's content until it has no child
		r89Wrapper.accessibilityLabel = NSUUID().UUIDString
		publishersWrapper.addSubview(r89Wrapper)
		configWrapperConstraints()
		if (wrapperStyleData != null)
		{
			setSize(wrapperStyleData.width, wrapperStyleData.height)
		}
	}
	
	override fun addAdCloseButton(adViewId: String, adLargestWidth: Int, imageUrl: String, onClick: () -> Unit)
	{
		//Add button only if adView has been initialized by calling addAdView function
		if (!this::adCloseButton.isInitialized)
		{
			adCloseButton = IosViewFactory.getCloseButton(r89Wrapper, adViewId, adLargestWidth, imageUrl)
			r89Wrapper.addSubview(adCloseButton)
			adCloseButton.setOnClickListener(onClick)
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
		labelView = UITextView().apply {
			text = "Advertising by refinery89"
			font = UIFont.systemFontOfSize(fontSize = 20.0)
			backgroundColor = UIColor.clearColor
			textAlignment = NSTextAlignmentCenter
		}
		labelView.userInteractionEnabled = false
		labelView.accessibilityLabel = NSUUID().UUIDString
		labelView.setTranslatesAutoresizingMaskIntoConstraints(false)
		r89Wrapper.setTranslatesAutoresizingMaskIntoConstraints(false)
		r89Wrapper.addSubview(labelView)
		NSLayoutConstraint.activateConstraints(
			listOf(
				labelView.leadingAnchor.constraintEqualToAnchor(r89Wrapper.leadingAnchor),
				labelView.topAnchor.constraintEqualToAnchor(r89Wrapper.topAnchor),
				labelView.bottomAnchor.constraintEqualToAnchor(r89Wrapper.bottomAnchor),
				labelView.trailingAnchor.constraintEqualToAnchor(r89Wrapper.trailingAnchor),
			)
		)
	}
	
	override fun show(newPublisherWrapper: Any?)
	{
		if (newPublisherWrapper !is UIView)
		{
			R89LogUtil.e(TAG, "newPublisherWrapper is not a UIView", false)
			return
		}
		
		r89Wrapper.removeFromSuperview()
		publishersWrapper = newPublisherWrapper
		publishersWrapper.addSubview(r89Wrapper)
	}
	
	override fun hide()
	{
		r89Wrapper.removeFromSuperview()
	}
	
	override fun destroy()
	{
		r89Wrapper.removeFromSuperview()
		for (subView in r89Wrapper.subviews)
		{
			(subView as? UIView)?.removeFromSuperview()
		}
	}
	
	override fun addAdView(adView: Any)
	{
		if (adView !is UIView)
		{
			R89LogUtil.e(TAG, "AdView is not a UIView", false)
			return
		}
		r89Wrapper.addSubview(adView)
		r89Wrapper.setTranslatesAutoresizingMaskIntoConstraints(false)
		adView.setTranslatesAutoresizingMaskIntoConstraints(false)
		
		if (wrapperStyleData != null)
		{
			configAdConstraints(adView)
		}
	}
	
	override fun reserveSpace(largestHeightDp: Int, largestWidthDp: Int)
	{
		var heightToReserve = largestHeightDp
		
		if (this::adCloseButton.isInitialized)
		{
			heightToReserve += IosViewFactory.getCloseButtonSizeInPt()
		}
		
		r89Wrapper.setTranslatesAutoresizingMaskIntoConstraints(false)
		r89Wrapper.heightAnchor.constraintEqualToConstant(heightToReserve.toDouble()).setActive(true)
	}
	
	override fun wrapContent()
	{
		// UIView by default should by default wrap it's content, like FrameLayout in Android.
		// Subject to test
	}
	
	override fun showAdCloseButton()
	{
		if (this::adCloseButton.isInitialized)
		{
			adCloseButton.setHidden(false)
		}
	}
	
	override fun hideAdCloseButton()
	{
		if (this::adCloseButton.isInitialized)
		{
			adCloseButton.setHidden(true)
		}
	}
	
	override fun showLabel()
	{
		if (this::labelView.isInitialized)
		{
			labelView.setHidden(false)
		}
	}
	
	override fun hideLabel()
	{
		if (this::labelView.isInitialized)
		{
			labelView.setHidden(true)
		}
	}
	
	private fun setSize(height: Int, width: Int)
	{
		r89Wrapper.setTranslatesAutoresizingMaskIntoConstraints(false)
		r89Wrapper.heightAnchor.constraintEqualToConstant(height.toDouble())
		r89Wrapper.widthAnchor.constraintEqualToConstant(width.toDouble())
	}
	
	private fun configWrapperConstraints()
	{
		publishersWrapper.setTranslatesAutoresizingMaskIntoConstraints(false)
		r89Wrapper.setTranslatesAutoresizingMaskIntoConstraints(false)
		
		val edges = publishersWrapper.constrainedEdges()
		R89LogUtil.d(TAG, "edges = $edges")
		
		NSLayoutConstraint.activateConstraints(
			when
			{
				edges.topAligned && edges.trailingAligned    -> listOf(
					r89Wrapper.topAnchor.constraintEqualToAnchor(publishersWrapper.topAnchor),
					r89Wrapper.trailingAnchor.constraintEqualToAnchor(publishersWrapper.trailingAnchor)
				)
				
				edges.topAligned && edges.leadingAligned     -> listOf(
					r89Wrapper.topAnchor.constraintEqualToAnchor(publishersWrapper.topAnchor),
					r89Wrapper.leadingAnchor.constraintEqualToAnchor(publishersWrapper.leadingAnchor)
				)
				
				edges.topAligned                             -> listOf(
					r89Wrapper.topAnchor.constraintEqualToAnchor(publishersWrapper.topAnchor),
					r89Wrapper.centerXAnchor.constraintEqualToAnchor(publishersWrapper.centerXAnchor)
				)
				
				edges.bottomAligned && edges.leadingAligned  -> listOf(
					r89Wrapper.bottomAnchor.constraintEqualToAnchor(publishersWrapper.bottomAnchor),
					r89Wrapper.leadingAnchor.constraintEqualToAnchor(publishersWrapper.leadingAnchor)
				)
				
				edges.bottomAligned && edges.trailingAligned -> listOf(
					r89Wrapper.bottomAnchor.constraintEqualToAnchor(publishersWrapper.bottomAnchor),
					r89Wrapper.trailingAnchor.constraintEqualToAnchor(publishersWrapper.trailingAnchor)
				)
				
				edges.bottomAligned                          -> listOf(
					r89Wrapper.bottomAnchor.constraintEqualToAnchor(publishersWrapper.bottomAnchor),
					r89Wrapper.centerXAnchor.constraintEqualToAnchor(publishersWrapper.centerXAnchor)
				)
				
				edges.leadingAligned                         -> listOf(
					r89Wrapper.leadingAnchor.constraintEqualToAnchor(publishersWrapper.leadingAnchor),
					r89Wrapper.centerYAnchor.constraintEqualToAnchor(publishersWrapper.centerYAnchor)
				)
				
				edges.trailingAligned                        -> listOf(
					r89Wrapper.trailingAnchor.constraintEqualToAnchor(publishersWrapper.trailingAnchor),
					r89Wrapper.centerYAnchor.constraintEqualToAnchor(publishersWrapper.centerYAnchor)
				)
				
				else                                         -> listOf(
					r89Wrapper.centerXAnchor.constraintEqualToAnchor(publishersWrapper.centerXAnchor),
					r89Wrapper.centerYAnchor.constraintEqualToAnchor(publishersWrapper.centerYAnchor)
				)
			}
		)
	}
	
	private fun configAdConstraints(adView: UIView)
	{
		publishersWrapper.setTranslatesAutoresizingMaskIntoConstraints(false)
		r89Wrapper.setTranslatesAutoresizingMaskIntoConstraints(false)
		
		val edges = publishersWrapper.constrainedEdges()
		
		NSLayoutConstraint.activateConstraints(
			when
			{
				edges.bottomAligned && edges.trailingAligned -> listOf(
					adView.bottomAnchor.constraintEqualToAnchor(r89Wrapper.bottomAnchor),
					adView.trailingAnchor.constraintEqualToAnchor(r89Wrapper.trailingAnchor),
				)
				
				edges.bottomAligned && edges.leadingAligned  -> listOf(
					adView.bottomAnchor.constraintEqualToAnchor(r89Wrapper.bottomAnchor),
					adView.leadingAnchor.constraintEqualToAnchor(r89Wrapper.leadingAnchor),
				)
				
				edges.topAligned && edges.leadingAligned     -> listOf(
					adView.topAnchor.constraintEqualToAnchor(r89Wrapper.topAnchor),
					adView.leadingAnchor.constraintEqualToAnchor(r89Wrapper.leadingAnchor),
				)
				
				edges.topAligned && edges.trailingAligned    -> listOf(
					adView.topAnchor.constraintEqualToAnchor(r89Wrapper.topAnchor),
					adView.trailingAnchor.constraintEqualToAnchor(r89Wrapper.trailingAnchor),
				)
				
				edges.leadingAligned                         -> listOf(
					adView.leadingAnchor.constraintEqualToAnchor(r89Wrapper.leadingAnchor),
					adView.centerYAnchor.constraintEqualToAnchor(r89Wrapper.centerYAnchor)
				)
				
				edges.trailingAligned                        -> listOf(
					adView.trailingAnchor.constraintEqualToAnchor(r89Wrapper.trailingAnchor),
					adView.centerYAnchor.constraintEqualToAnchor(r89Wrapper.centerYAnchor)
				)
				
				else                                         -> listOf(
					adView.centerYAnchor.constraintEqualToAnchor(r89Wrapper.centerYAnchor),
					adView.centerXAnchor.constraintEqualToAnchor(r89Wrapper.centerXAnchor),
				)
			}
		)
	}
	
	companion object
	{
		private const val TAG = "R89WrapperAndroidImpl"
	}
}