package com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.wrapper_style

import com.refinery89.sharedcore.domain_layer.wrapper_features.WrapperPosition
import kotlinx.serialization.Serializable

//import com.refinery89.androidSdk.domain_layer.wrapper_features.WrapperPosition

@Serializable
internal data class WrapperStyleScheme(
	val position: WrapperPosition?,
	val matchParentHeight: Boolean?,
	val matchParentWidth: Boolean?,
	val wrapContent: Boolean?,
	val height: Int?,
	val width: Int?,
)