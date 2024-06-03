package com.refinery89.sharedcore.os_layer.extensions

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import platform.Foundation.NSSelectorFromString
import platform.UIKit.NSLayoutAttribute
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIGestureRecognizerDelegateProtocol
import platform.UIKit.UITapGestureRecognizer
import platform.UIKit.UIView
import platform.UIKit.accessibilityLabel
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
internal fun UIView.setOnClickListener(onClick: () -> Unit)
{
	setUserInteractionEnabled(true)
	val recognizer = UITapGestureRecognizer(object : NSObject(), UIGestureRecognizerDelegateProtocol
	{
		@ObjCAction
		fun onTap()
		{
			onClick()
		}
	}, action = NSSelectorFromString("onTap"))
	setGestureRecognizers(listOf(recognizer))
}

internal fun UIView.findViewByLabel(accessoryLabel: String): UIView?
{
	if (this.accessibilityLabel == accessoryLabel)
	{
		return this@findViewByLabel
	} else
	{
		for (subView in subviews)
		{
			if (subView is UIView)
			{
				val view = subView.findViewByLabel(accessoryLabel)
				if (view != null)
				{
					return view;
				}
			}
		}
	}
	return null
}

internal fun UIView.constrainedEdges(): Set<NSLayoutAttribute>
{
	val constrainedEdges = mutableSetOf<NSLayoutAttribute>()
	
	val relevantConstraints = this.constraints.toMutableList()
	this.superview?.constraints?.let { superviewConstraints ->
		relevantConstraints.addAll(superviewConstraints.filter { constraint ->
			constraint as NSLayoutConstraint
			(constraint.firstItem as? UIView) == this || (constraint.secondItem as? UIView) == this
		})
	}
	
	relevantConstraints.forEach { constraint ->
		constraint as NSLayoutConstraint
		if ((constraint.firstItem as? UIView) == this)
		{
			constraint.firstAttribute
			constrainedEdges.add(constraint.firstAttribute)
		}
		if ((constraint.secondItem as? UIView) == this)
		{
			constrainedEdges.add(constraint.secondAttribute)
		}
	}
	
	return constrainedEdges
}


internal val Set<NSLayoutAttribute>.bottomAligned get():Boolean = hasBottom && !hasTop
internal val Set<NSLayoutAttribute>.topAligned get():Boolean = hasTop && !hasBottom
internal val Set<NSLayoutAttribute>.trailingAligned get():Boolean = hasTrailing && !hasLeading
internal val Set<NSLayoutAttribute>.leadingAligned get():Boolean = hasLeading && !hasTrailing

internal val Set<NSLayoutAttribute>.hasLeading get():Boolean = contains(1L) || contains(5L)
internal val Set<NSLayoutAttribute>.hasTrailing get():Boolean = contains(2L) || contains(6L)
internal val Set<NSLayoutAttribute>.hasTop get():Boolean = contains(3L)
internal val Set<NSLayoutAttribute>.hasBottom get():Boolean = contains(4L)
internal val Set<NSLayoutAttribute>.hasCenterX get():Boolean = contains(9L)
internal val Set<NSLayoutAttribute>.hasCenterY get():Boolean = contains(10L)
