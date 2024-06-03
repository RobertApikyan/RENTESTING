package com.refinery89.sharedcore.domain_layer.adFormats

/**
 * Enum listing all the formats available to the SDK
 */
internal enum class Formats
{
	/**
	 * Banner format
	 */
	BANNER,
	
	/**
	 * Interstitial format
	 */
	INTERSTITIAL,
	
	/**
	 * Video format
	 */
	VIDEO_OUTSTREAM_BANNER,
	
	/**
	 * Video interstitial format
	 */
	VIDEO_OUTSTREAM_INTERSTITIAL,
	
	/**
	 * Infinite Scroll format
	 */
	INFINITE_SCROLL,
	
	/**
	 * Rewarded Interstitial format
	 */
	REWARDED_INTERSTITIAL;
}