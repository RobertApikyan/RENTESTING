package com.refinery89.sharedcore.domain_layer.frequency_cap

/**
 * Responsibilities are:
 * - Providing the factory and the formats ways to tell if the request should be avoided or not
 */
internal interface IFrequencyCap
{
	/**
	 * Checks if the ad request counter can be increased can be displayed or not,
	 * if not this means we have reached the cap for this ad for now
	 * @return Boolean if successful go ahead requesting the ad if true else don't request ad
	 */
	fun tryIncreaseCounter(): Boolean
	
	/**
	 * Simulates [tryIncreaseCounter] but without increasing the counter.
	 * @param millisUntilNextRequest if inputted any value this value will be used to simulate a call to [tryIncreaseCounter] that amount of milliseconds
	 * @return Boolean
	 */
	fun nextIncreaseCounterIsGoingToSucceed(millisUntilNextRequest: Long): Boolean
	
	/**
	 * @return String with information about how much time is left for the cap to end
	 */
	fun timeForCapToEndString(): String
}