package com.refinery89.sharedcore.os_layer.third_party_library

import com.refinery89.sharedcore.domain_layer.third_party_libraries.UiExecutorLibrary
import com.refinery89.sharedcore.os_layer.extensions.async
import platform.Foundation.NSThread
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

class UiExecutorIosImpl : UiExecutorLibrary
{
	
	companion object : UiExecutorLibrary by UiExecutorIosImpl()
	
	override fun executeInMainThread(block: () -> Unit)
	{
		if (NSThread.isMainThread)
		{
			block()
		} else
		{
			async(block)
		}
	}
}