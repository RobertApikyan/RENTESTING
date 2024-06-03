package com.refinery89.sharedcore.os_layer.internal_toolbox.date_time

import com.refinery89.sharedcore.domain_layer.internalToolBox.date_time.R89Date
import com.refinery89.sharedcore.domain_layer.internalToolBox.date_time.R89DateFactory
import java.util.Date

internal class R89DateFactoryAndroidImpl : R89DateFactory
{
	override fun now(): R89Date
	{
		return R89DateAndroidImpl(Date())
	}
	
	override fun fromEpochMilliseconds(epochMillis: Long): R89Date
	{
		val date = Date(epochMillis)
		return R89DateAndroidImpl(date)
	}
}