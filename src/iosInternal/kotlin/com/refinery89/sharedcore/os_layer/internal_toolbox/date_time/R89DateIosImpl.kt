package com.refinery89.sharedcore.os_layer.internal_toolbox.date_time

import com.refinery89.sharedcore.domain_layer.internalToolBox.date_time.R89Date
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

internal class R89DateIosImpl(private val date: NSDate) : R89Date
{
	override fun toEpochMilliseconds(): Long
	{
		return (date.timeIntervalSince1970 * 1000).toLong()
	}
	
	override fun toString(): String
	{
		return date.description ?: ""
	}
}