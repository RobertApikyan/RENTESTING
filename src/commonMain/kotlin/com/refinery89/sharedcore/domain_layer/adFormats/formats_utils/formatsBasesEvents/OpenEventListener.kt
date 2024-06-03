package com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.formatsBasesEvents

/**
 * This is created because there are some formats that are don't open or close any Intent
 */
internal interface OpenEventListener
{
	/**
	 * Called when an ad starts an Intent.
	 */
	fun onOpen()
}