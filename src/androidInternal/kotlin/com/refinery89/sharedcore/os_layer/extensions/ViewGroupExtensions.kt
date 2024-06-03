package com.refinery89.sharedcore.os_layer.extensions

import android.view.ViewGroup

internal fun ViewGroup.getParentAsViewGroup(): ViewGroup?
{
	return this.parent as? ViewGroup
}

internal fun ViewGroup.getIndexOnParent(): Int
{
	return this.getParentAsViewGroup()?.indexOfChild(this) ?: -1
}