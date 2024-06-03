package com.refinery89.sharedcore.domain_layer.internalToolBox.date_time

internal interface R89Date
{
	fun toEpochMilliseconds(): Long
	operator fun rangeTo(rangeEnd: R89Date): R89DateRange
	{
		return R89DateRange(this, rangeEnd)
	}
	
	operator fun compareTo(first: R89Date): Int
	{
		return when
		{
			this.toEpochMilliseconds() > first.toEpochMilliseconds() -> 1
			this.toEpochMilliseconds() < first.toEpochMilliseconds() -> -1
			else                                                     -> 0
		}
	}
	
	override fun toString(): String
	
}

