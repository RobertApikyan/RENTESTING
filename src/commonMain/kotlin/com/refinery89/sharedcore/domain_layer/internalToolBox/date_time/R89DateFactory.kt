package com.refinery89.sharedcore.domain_layer.internalToolBox.date_time

internal interface R89DateFactory
{
	fun now(): R89Date
	fun fromEpochMilliseconds(epochMillis: Long): R89Date
}