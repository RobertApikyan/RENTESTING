package com.refinery89.sharedcore.domain_layer.adFormats.formats_utils

/**
 * This class is used as an adapter to Google's com.google.android.gms.ads.AdSize class.
 * @property width Int
 * @property height Int
 */
internal data class R89AdSize(
	val width: Int,
	val height: Int,
)