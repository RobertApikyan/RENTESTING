package com.refinery89.sharedcore.os_layer.extensions

import org.prebid.mobile.ResultCode

internal fun ResultCode.asString(): String
{
	return "Result code: $this result name: ${this.name} result ordinal: ${this.ordinal}"
}