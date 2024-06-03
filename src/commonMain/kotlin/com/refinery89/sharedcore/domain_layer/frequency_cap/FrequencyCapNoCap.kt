package com.refinery89.sharedcore.domain_layer.frequency_cap

/**
 * Frequency cap implementation that always returns true like there is no cap
 */
internal class FrequencyCapNoCap : IFrequencyCap
{
	/**
	 * always returns true like there is no cap
	 * @return Boolean
	 */
	override fun tryIncreaseCounter(): Boolean
	{
		return true
	}
	
	/**
	 * always returns true like there is no cap
	 * @param millisUntilNextRequest Long
	 * @return Boolean
	 */
	override fun nextIncreaseCounterIsGoingToSucceed(millisUntilNextRequest: Long): Boolean
	{
		return true
	}
	
	/**
	 * @suppress
	 */
	override fun timeForCapToEndString(): String
	{
		return "No Cap"
	}
}