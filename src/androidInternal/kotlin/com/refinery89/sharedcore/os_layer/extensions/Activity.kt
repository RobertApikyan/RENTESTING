package com.refinery89.sharedcore.os_layer.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

internal fun <T : View> Activity.getViewByNameId(id: String): T?
{
	return findViewById(getViewNumberIdByNameId(id))
}

@SuppressLint("DiscouragedApi")
internal fun Activity.getViewNumberIdByNameId(id: String): Int
{
	return resources.getIdentifier(id, "id", packageName)
}

internal fun <T : View> Activity.getFirstViewWithTag(tag: String): T?
{
	return window.decorView.findViewWithTag(tag)
}

internal fun Activity.getViewsByTag(tag: String): List<View>
{
	val firstRoot = window.decorView.rootView as ViewGroup
	
	fun recursiveGetViewsByTag(root: ViewGroup, tag: String): List<View>
	{
		val outputList = mutableListOf<View>()
		
		for (childView in root.children)
		{
			val tagObj = childView.tag.toString()
			if (tagObj.contains(tag))
			{
				outputList.add(childView)
			}
			
			if (childView is ViewGroup)
			{
				outputList.addAll(recursiveGetViewsByTag(childView, tag))
			}
		}
		
		return outputList
	}
	
	return recursiveGetViewsByTag(firstRoot, tag)
}