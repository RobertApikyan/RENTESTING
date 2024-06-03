package com.refinery89.sharedcore.os_layer.extensions

import com.refinery89.sharedcore.domain_layer.third_party_libraries.UiExecutorLibrary
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.NSEC_PER_MSEC
import platform.darwin.dispatch_after
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_time

internal fun UiExecutorLibrary.async(block: () -> Unit) = dispatch_async(dispatch_get_main_queue(), block)

internal fun UiExecutorLibrary.asyncAfter(delayInMillis: ULong, block: () -> Unit)
{
	val dispatchDelay = dispatch_time(DISPATCH_TIME_NOW, (delayInMillis * NSEC_PER_MSEC).toLong())
	dispatch_after(dispatchDelay, dispatch_get_main_queue(), block)
}