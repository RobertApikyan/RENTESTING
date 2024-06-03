package com.refinery89.sharedcore.domain_layer.singleTag

/**
 * This interface makes possible communication between the OS and the Domain layer for [SingleTagConfigurator]
 */
internal interface SingleTagScreenEvents
{
	/**
	 * When an OS Screen is created this should be called when single tag is active
	 * @param createdScreenName String
	 * @param isFragment Boolean
	 * @return Unit
	 */
	fun onScreenCreated(createdScreenName: String, isFragment: Boolean)
	
	/**
	 * When an OS Screen is resumed this should be called when single tag is active
	 * @param resumedScreenName String
	 * @param isFragment Boolean
	 * @return Unit
	 */
	fun onScreenResumed(resumedScreenName: String, isFragment: Boolean)
	
	/**
	 * When an OS Screen is destroyed this should be called when single tag is active
	 * @param screenName String
	 * @param isFragment Boolean
	 * @return Unit
	 */
	fun onScreenDestroyed(screenName: String, isFragment: Boolean)
}