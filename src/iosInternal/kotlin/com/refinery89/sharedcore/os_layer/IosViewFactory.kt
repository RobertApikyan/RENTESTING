package com.refinery89.sharedcore.os_layer

import com.refinery89.sharedcore.os_layer.extensions.findViewByLabel
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.NSUUID
import platform.Foundation.create
import platform.Foundation.dataWithContentsOfURL
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIImage
import platform.UIKit.UIImageView
import platform.UIKit.UIScreen
import platform.UIKit.UIView
import platform.UIKit.accessibilityLabel

internal object IosViewFactory
{
	
	private const val closeImageB64 =
		"iVBORw0KGgoAAAANSUhEUgAAAgAAAAIACAMAAADDpiTIAAAAA3NCSVQICAjb4U/gAAAACXBIWXMAAbrqAAG66gHB8Tn1AAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAc5QTFRF////RERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERE5J3yWQAAAJl0Uk5TAAECAwQFBwgKCw4PEBESExQWFxgZGhwfICEiJCUmJykrLC0uLzEyMzQ1Njc6Ozw9QEFCQ0RFRk1OUFRVWltdXl9gY2ZnaWpucHZ6fH1/gIWIiYqLjY6PkJGUlZaYmqChpKepqqusra+wsbKztLe5ury+wMLDxcfJy83T1NXW19jZ2tvc3+Hj5OXn6Ons7e7v8fT1+fr7/P3+RsFkBQAAFlFJREFUeNrtnXlfFEcQhguygKIBbxGDGiFK1IhoDIoHHmC8AmJAYjyIMmoCBhC8NiIqAiorQUA3W982f8Qo6AJ7zHRXT73vB/BX9vOwM91T3U2kJvmrN++qrT/dcvVGZ3ff/eijoZGxial4fGpibGToUfR+X3fnjastp+trd21enU9IWJK7pupIY3tPdGSS08jkSLSnvfFI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"
	
	fun centerViewHorizontallyInParent(view: UIView)
	{
		val parent = view.superview
		if (parent != null)
		{
			view.setTranslatesAutoresizingMaskIntoConstraints(false)
			view.centerXAnchor.constraintEqualToAnchor(parent.centerXAnchor).setActive(true)
		}
	}
	
	fun getCloseButtonSizeInPt(): Int
	{
		return 16
	}
	
	@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
	fun getCloseButton(r89Wrapper: UIView, anchorViewId: String, adLargestWidth: Int, imageUrl: String): UIImageView
	{
		val screenWidthPt = UIScreen.mainScreen.bounds.useContents {
			size.width
		}
		val buttonSize = getCloseButtonSizeInPt().toDouble()
		
		val placeButtonTop = adLargestWidth + buttonSize > screenWidthPt
		
		val closeButton = UIImageView()
		r89Wrapper.addSubview(closeButton)
		closeButton.accessibilityLabel = NSUUID().UUIDString()
		closeButton.setTranslatesAutoresizingMaskIntoConstraints(false)
		NSLayoutConstraint.activateConstraints(
			listOf(
				closeButton.widthAnchor.constraintEqualToConstant(buttonSize),
				closeButton.heightAnchor.constraintEqualToConstant(buttonSize),
			)
		)
		
		val data = if (imageUrl.isEmpty())
		{
			NSData.create(base64EncodedString = closeImageB64, options = 0UL)
		} else
		{
			//TODO(@Apikyan):Switch to asynchronous url loading API such as URLSession.
			NSData.dataWithContentsOfURL(NSURL(string = imageUrl))
		}
		
		if (data != null)
		{
			val image = UIImage(data = data)
			closeButton.setImage(image)
		}
		
		val anchorView = r89Wrapper.findViewByLabel(anchorViewId)
		
		if (anchorView != null)
		{
			if (placeButtonTop)
			{
				NSLayoutConstraint.activateConstraints(
					listOf(
						closeButton.bottomAnchor.constraintEqualToAnchor(anchorView.topAnchor),
						closeButton.trailingAnchor.constraintEqualToAnchor(anchorView.trailingAnchor),
					),
				)
			} else
			{
				NSLayoutConstraint.activateConstraints(
					listOf(
						closeButton.topAnchor.constraintEqualToAnchor(anchorView.topAnchor),
						closeButton.leadingAnchor.constraintEqualToAnchor(anchorView.trailingAnchor),
					),
				)
			}
		}
		
		return closeButton
	}
	
}

