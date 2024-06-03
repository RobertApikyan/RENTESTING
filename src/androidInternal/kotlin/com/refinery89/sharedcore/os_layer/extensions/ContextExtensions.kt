package com.refinery89.sharedcore.os_layer.extensions

import android.content.Context


internal fun Context.dpToPx(dp: Int): Int
{
	return (dp * resources.displayMetrics.density).toInt()
}

internal fun Context.pxToDp(px: Int): Int
{
	return (px / resources.displayMetrics.density).toInt()
}