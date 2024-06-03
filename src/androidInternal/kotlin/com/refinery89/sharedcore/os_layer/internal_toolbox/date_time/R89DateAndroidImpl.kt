package com.refinery89.sharedcore.os_layer.internal_toolbox.date_time

import com.refinery89.sharedcore.domain_layer.internalToolBox.date_time.R89Date

internal class R89DateAndroidImpl(private val date: java.util.Date) : R89Date
{
	override fun toEpochMilliseconds(): Long
	{
		return date.time
	}
	
	override fun toString(): String
	{
		return date.toString()
	}
}