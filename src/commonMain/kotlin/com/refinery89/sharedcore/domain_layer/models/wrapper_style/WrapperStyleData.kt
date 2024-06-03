package com.refinery89.sharedcore.domain_layer.models.wrapper_style

import com.refinery89.sharedcore.domain_layer.wrapper_features.WrapperPosition
import kotlinx.serialization.Serializable

@Serializable
internal data class WrapperStyleData(
	val position: WrapperPosition,
	val matchParentHeight: Boolean,
	val matchParentWidth: Boolean,
	val wrapContent: Boolean,
	val height: Int,
	val width: Int,
)