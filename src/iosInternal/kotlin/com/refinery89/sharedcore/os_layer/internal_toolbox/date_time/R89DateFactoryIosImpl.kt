package com.refinery89.sharedcore.os_layer.internal_toolbox.date_time

import com.refinery89.sharedcore.domain_layer.internalToolBox.date_time.R89Date
import com.refinery89.sharedcore.domain_layer.internalToolBox.date_time.R89DateFactory
import platform.Foundation.NSDate

internal class R89DateFactoryIosImpl : R89DateFactory
{
	override fun now(): R89Date
	{
		val currentDate = NSDate()
		return R89DateIosImpl(currentDate)
	}
	
	override fun fromEpochMilliseconds(epochMillis: Long): R89Date
	{
		val date = NSDate(epochMillis.toDouble() / 1000.0)
		return R89DateIosImpl(date)
	}
}