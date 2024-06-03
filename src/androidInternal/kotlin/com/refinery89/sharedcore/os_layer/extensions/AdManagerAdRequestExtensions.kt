package com.refinery89.sharedcore.os_layer.extensions

import android.content.Context
import com.google.android.gms.ads.admanager.AdManagerAdRequest

internal fun AdManagerAdRequest.asString(context: Context?): String
{
	return """request content: {
		Content Url -> $contentUrl,
		Keywords -> $keywords
		CustomTargeting -> $customTargeting
		Publisher Provider Id -> $publisherProvidedId
		NeighboringContentUrls -> $neighboringContentUrls
		JavaClass -> $javaClass
		Is Test Device -> ${context?.let { isTestDevice(it) } ?: " no Context was specified, this parameter is unknown"}""".trimIndent()
}