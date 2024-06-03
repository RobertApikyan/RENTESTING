package com.refinery89.sharedcore.domain_layer.log_tool

/**
 * levels available use google definition of them [Google Definition of the levels](https://developer.android.com/studio/debug/am-logcat#level)
 */
enum class LogLevels
{
	/**
	 * Low Level Information that's important for debugging a lot of information
	 * this logs don't go into release builds
	 */
	VERBOSE,
	
	/**
	 * Low Level Information that's important for debugging containing less info than verbose
	 * this logs don't go into release builds
	 */
	DEBUG,
	
	/**
	 * Logs High Level Information of anything that went as expected in the Execution of the SDK initialization,
	 * normal lifecycle of the formats, and other normal stuff
	 */
	INFO,
	
	/**
	 * Logs High Level Information of Something went wrong but the SDK is able to continue showing ads and generate some revenue
	 * but is on suboptimal conditions
	 */
	WARN,
	
	/**
	 * Logs High Level Information when the SDK is not able to continue showing ads nor generate revenue
	 */
	ERROR,
	
	/**
	 * Catastrophic, horrible, stressful, and not funny at tall succession of events
	 * we are very sorry of these and we ask you to please contact us with this log under your arm
	 * we promise this is fixable though <3
	 */
	ASSERT;
	
	/**
	 * The lower the ordinal(order in which they are defined) the higher the priority so VERBOSE has the highest priority
	 * @param limitPriority Log level you want to compare to current log level
	 * @return Returns true if [limitPriority] log level ordinal is lower then the current log levels ordinal, aka returns true if priority is higher than [limitPriority]
	 */
	fun hasEqualOrHigherPriority(limitPriority: LogLevels): Boolean
	{
		return this.ordinal <= limitPriority.ordinal
	}
	
}