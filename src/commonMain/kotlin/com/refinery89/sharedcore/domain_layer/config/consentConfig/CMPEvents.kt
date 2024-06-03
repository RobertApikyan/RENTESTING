package com.refinery89.sharedcore.domain_layer.config.consentConfig

/** This interface is for working with the CMPLibrary OS events so these are used by the os to notify the domain layer. */
internal interface CMPEvents
{
	/**
	 * This function is called when the CMP is opened, because it requires user interaction
	 *
	 * @return Unit
	 */
	fun onCMPOpened()
	
	/**
	 * This function is called when the CMP is closed with of the user
	 *
	 * @return Unit
	 */
	fun onCMPClosed()
	
	/**
	 * Normally because the user already gave consent or because the user is not required to give consent
	 *
	 * @return Unit
	 */
	fun onCMPNotOpened()
	
	/**
	 * This function is called when the CMP has an error
	 *
	 * @param sdkEventMessage String
	 * @return Unit
	 */
	fun onCMPError(sdkEventMessage: String)
	
	/**
	 * This function is called when the user clicks on a button in the CMP Consent layer
	 *
	 * @param buttonEvent CMPButtonClicks
	 * @return Unit
	 */
	fun onCMPButtonClicked(buttonEvent: CMPButtonClicks)
}
