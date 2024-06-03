package com.refinery89.sharedcore.domain_layer.internalToolBox.date_time

internal class R89DateRange(private val start: R89Date, private val endInclusive: R89Date)
{
	operator fun contains(date: R89Date): Boolean
	{
		return date.toEpochMilliseconds() in start.toEpochMilliseconds()..endInclusive.toEpochMilliseconds()
	}
}