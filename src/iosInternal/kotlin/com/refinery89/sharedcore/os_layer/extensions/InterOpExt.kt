package com.refinery89.sharedcore.os_layer.extensions

import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89AdSize
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cValue
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGSize

@OptIn(ExperimentalForeignApi::class)
internal fun R89AdSize.toCGSizeCValue() = cValue<CGSize> {
	width = this@toCGSizeCValue.width.toDouble()
	height = this@toCGSizeCValue.height.toDouble()
}

@OptIn(ExperimentalForeignApi::class)
internal fun CValue<CGSize>.toR89AdSize(): R89AdSize = useContents {
	return R89AdSize(width.toInt(), height.toInt())
}
