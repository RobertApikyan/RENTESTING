package com.refinery89.sharedcore.domain_layer.analytics

/**
 * @suppress
 * @property priority String
 * @property tag String
 * @property message String
 * @property deviceUUID String
 * @property publisherId String
 * @property appId String
 */
internal data class LogRequestBody(
	val priority: String,
	val tag: String,
	val message: String,
	val deviceUUID: String,
	val publisherId: String,
	val appId: String,
)
