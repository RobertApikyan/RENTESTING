package com.refinery89.sharedcore.domain_layer.singleTag

import com.refinery89.sharedcore.domain_layer.models.single_tag.AdPlaceData


/**
 * Used to create OS Single Tag operations that we can use from the Domain
 *
 * This is needed since single tag needs to search into the view hierarchy of
 * each app and each screen so also tracks lifecycle events for many things in an app
 */
internal interface ISingleTagOS
{
	/**
	 * This takes Domain [SingleTagScreenEvents] and map those events to the apps lifecycle events in whatever OS they are
	 * @param lifeCycle SingleTagScreenEvents
	 * @return Unit
	 */
	fun registerActivityLifecycle(lifeCycle: SingleTagScreenEvents)
	
	/**
	 * This is Used from the Domain to tell the OS that we need to create an infinite scroll
	 * with the [adPlace] data in the screen with name [screenName]
	 * @param adPlace AdPlaceData
	 * @param screenName String
	 * @return Unit
	 */
	fun resolveAdPlaceInfiniteScroll(adPlace: AdPlaceData, screenName: String)
	
	/**
	 * This is Used from the Domain to tell the OS that we need to create an ad that depends on a wrapper to exist
	 * using the [adPlace] data in the screen with name [screenName]
	 * @param adPlace AdPlaceData
	 * @param screenName String
	 * @return Unit
	 */
	fun resolveAdPlaceWrapper(adPlace: AdPlaceData, screenName: String)
	
	/**
	 * This is Used from the Domain to tell the OS that we need to create an ad that exist only when an event happens
	 * and we are telling what event with the [adPlace] data in the screen with name [screenName]
	 * @param adPlace AdPlaceData
	 * @param screenName String
	 * @return Unit
	 */
	fun resolveAdPlaceEvents(
		adPlace: AdPlaceData,
		screenName: String,
		interstitialList: MutableMap<String, Int>
	)
}