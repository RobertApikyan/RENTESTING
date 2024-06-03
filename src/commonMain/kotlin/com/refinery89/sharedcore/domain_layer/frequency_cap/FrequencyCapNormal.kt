package com.refinery89.sharedcore.domain_layer.frequency_cap

//import com.google.type.R89Date//TODO(@Apikyan):R89Date in KMP
import com.refinery89.sharedcore.domain_layer.internalToolBox.date_time.R89Date
import com.refinery89.sharedcore.domain_layer.internalToolBox.date_time.R89DateFactory
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.third_party_libraries.ISimpleDataSave

/**
 *
 * @property prefs ISimpleDataSave way to store and retrieve data from the device
 * @property r89ConfigId String the configuration id that we are capping
 * @property timeSlotSize how much time will be the time slots
 * @property userPrefCounterId id for [prefs] to store the counter
 * @property userPrefAdAmountPerTimeSlotId id for [prefs] to store the counter limit
 * @property userPrefTimeSlotStartId id for [prefs] to store the start of the current time slot
 * @property userPrefTimeSlotEndId id for [prefs] to store the end of the current time slot
 * @property adCounterMaxAmountPerTimeSlot max amount of times can [tryIncreaseCounter] should be called before it returns false
 * @property currentAdCounter current counter for the current time slot, it's increased by 1 every time [tryIncreaseCounter] is called
 * @property currentTimeSlot if the [currentAdCounter] reaches the [adCounterMaxAmountPerTimeSlot] when the current time of the device is within this two dates
 * then [tryIncreaseCounter] will return false
 * @constructor
 */
internal class FrequencyCapNormal(
	private val prefs: ISimpleDataSave,
	private val r89ConfigId: String,
	adAmountPerTimeSlot: Int,
	private var timeSlotSize: Long,
	private val dateFactory: R89DateFactory,
) : IFrequencyCap
{
	
	private val userPrefCounterId: String = r89ConfigId + CURRENT_COUNTER_SUFFIX
	private val userPrefAdAmountPerTimeSlotId: String =
		r89ConfigId + MAX_AD_AMOUNT_PER_TIME_SLOT_SUFFIX
	private val userPrefTimeSlotStartId: String = r89ConfigId + CURRENT_TIME_SLOT_START_SUFFIX
	private val userPrefTimeSlotEndId: String = r89ConfigId + CURRENT_TIME_SLOT_END_SUFFIX
	
	//	private val isCapped: Boolean
	private val adCounterMaxAmountPerTimeSlot: Int = adAmountPerTimeSlot
	
	private var currentAdCounter: Int
	private var currentTimeSlot: Pair<R89Date, R89Date>
	
	/**
	 * @suppress
	 */
	init
	{
		
		//This variable act as a default in case no stored variable is present
		val currentTime = dateFactory.now()
		
		// Get the data from shared Prefs of from the device, otherwise use current time
		val timeSlotStartIsSaved = prefs.contains(userPrefTimeSlotStartId)
		val timeSlotEndIsSaved = prefs.contains(userPrefTimeSlotEndId)
		
		val (
			currentTimeIsInTimeSlot,
			currentTimeSlotStart,
			currentTimeSlotEnd,
		) = if (timeSlotStartIsSaved && timeSlotEndIsSaved)
		{

//			val savedTimeSlotStart = Instant.fromEpochMilliseconds(prefs.getLong(userPrefTimeSlotStartId, 0))
			val savedTimeSlotStart = dateFactory.fromEpochMilliseconds(prefs.getLong(userPrefTimeSlotStartId, 0))
			
			val savedTimeSlotEnd = dateFactory.fromEpochMilliseconds(prefs.getLong(userPrefTimeSlotEndId, 0))

//			val currentTimeIsInSavedTimeSlot = range.contains(currentTime)
			val currentTimeIsInSavedTimeSlot = currentTime in savedTimeSlotStart..savedTimeSlotEnd
			
			Triple(currentTimeIsInSavedTimeSlot, savedTimeSlotStart, savedTimeSlotEnd)
		} else
		{
			val nonSavedTimeSlotEndMillis = currentTime.toEpochMilliseconds() + timeSlotSize
			//This date act as a default in case no stored date is present
			val nonSavedTimeSlotEndDate = dateFactory.fromEpochMilliseconds(nonSavedTimeSlotEndMillis)
			Triple(false, currentTime, nonSavedTimeSlotEndDate)
		}
		
		//If the current time slot is not outdated, use everything stored, else reset everything
		if (currentTimeIsInTimeSlot)
		{
			currentTimeSlot = currentTimeSlotStart to currentTimeSlotEnd
			currentAdCounter = prefs.getInt(userPrefCounterId, -1)
		} else
		{
			//the next two lines is so the compiler doesn't complain about uninitialized variables
			currentTimeSlot = currentTimeSlotStart to currentTimeSlotEnd
			currentAdCounter = 0
			reset(prefs, currentTimeSlotStart, currentTimeSlotEnd)
		}
	}
	
	//TODO: Build Unit Test for this
	/**
	 * Checks if the ad can be displayed or not,
	 * if the ad can be displayed it will increase the counter
	 * @return Boolean if successful or not, go ahead requesting the ad if true else don't request ad
	 */
	override fun tryIncreaseCounter(): Boolean
	{
		val currentTime = dateFactory.now()
		
		val (currentTimeIsInsideTimeSlot, adCounterLimitNOTReached, currentTimeIsLaterThanTimeSlot) = getConditionsToPass(
			currentTime
		)
		
		if (currentTimeIsInsideTimeSlot && adCounterLimitNOTReached.not())
		{
			return false
		} else
		{
			if (currentTimeIsLaterThanTimeSlot)
			{
				R89LogUtil.i(
					"FrequencyCapNormal",
					"Frequency cap Time slot ended, reset for $r89ConfigId"
				)
				//Set everything to 0
				val currentTimeSlotEnd = dateFactory.fromEpochMilliseconds(currentTime.toEpochMilliseconds() + timeSlotSize)
				reset(prefs, currentTime, currentTimeSlotEnd)
			}
			R89LogUtil.i("FrequencyCapNormal", "Frequency cap Counter increased for $r89ConfigId")
			//Add 1 after reset
			increaseCounter(prefs)
			return true
		}
	}
	
	//TODO: Build Unit Test for this
	/**
	 * Simulates [tryIncreaseCounter] but without increasing the counter.
	 * @param millisUntilNextRequest if inputted any value this value will be used to simulate a call to [tryIncreaseCounter] that amount of milliseconds
	 * @return Boolean
	 */
	override fun nextIncreaseCounterIsGoingToSucceed(millisUntilNextRequest: Long): Boolean
	{
		val currentTime = dateFactory.now()
		R89LogUtil.d("FrequencyCapNormal", "$r89ConfigId: currentDate $currentTime")
		R89LogUtil.d(
			"FrequencyCapNormal",
			"$r89ConfigId: millis until next $millisUntilNextRequest"
		)
		
		val someTimeInFuture = dateFactory.fromEpochMilliseconds(currentTime.toEpochMilliseconds() + millisUntilNextRequest)
		
		R89LogUtil.d(
			"FrequencyCapNormal",
			"$r89ConfigId: R89Date to evaluate against: $someTimeInFuture currentTimeSlot: $currentTimeSlot"
		)
		
		val (currentTimeIsInsideTimeSlot, adCounterLimitNOTReached, currentTimeIsLaterThanTimeSlot) = getConditionsToPass(
			someTimeInFuture
		)
		
		R89LogUtil.d(
			"FrequencyCapNormal",
			"$r89ConfigId: currentTimeIsInsideTimeSlot: $currentTimeIsInsideTimeSlot currentTimeIsLaterThanTimeSlot: $currentTimeIsLaterThanTimeSlot adCounterLimitNOTReached: " +
					"$adCounterLimitNOTReached"
		)
		return ((currentTimeIsInsideTimeSlot && adCounterLimitNOTReached) || currentTimeIsLaterThanTimeSlot)
	}
	
	/**
	 * Returns an object with all the conditions to pass the cap
	 * @param currentTime R89Date
	 * @return Triple<Boolean, Boolean, Boolean>
	 */
	private fun getConditionsToPass(currentTime: R89Date): Triple<Boolean, Boolean, Boolean>
	{
		val currentTimeIsInsideTimeSlot = currentTime == currentTimeSlot.first ||
				currentTime == currentTimeSlot.second ||
				(currentTime >= currentTimeSlot.first && currentTime <= currentTimeSlot.second)
		val adCounterLimitNOTReached = currentAdCounter + 1 <= adCounterMaxAmountPerTimeSlot
		
		val currentTimeIsLaterThanTimeSlot = currentTime > currentTimeSlot.second
		
		return Triple(
			currentTimeIsInsideTimeSlot,
			adCounterLimitNOTReached,
			currentTimeIsLaterThanTimeSlot
		)
	}
	
	/**
	 * Resets the request counter and puts the time slot to the current time
	 * @param prefs ISimpleDataSave
	 * @param currentTime R89Date
	 * @param currentTimeSlotEnd R89Date
	 * @return Unit
	 */
	private fun reset(prefs: ISimpleDataSave, currentTime: R89Date, currentTimeSlotEnd: R89Date)
	{
		currentTimeSlot = currentTime to currentTimeSlotEnd
		currentAdCounter = 0
		
		prefs.edit().apply {
			putInt(userPrefCounterId, currentAdCounter)
			putInt(userPrefAdAmountPerTimeSlotId, adCounterMaxAmountPerTimeSlot)
			putLong(userPrefTimeSlotStartId, currentTime.toEpochMilliseconds())
			putLong(userPrefTimeSlotEndId, currentTimeSlotEnd.toEpochMilliseconds())
		}.apply()
	}
	
	/**
	 * @return String with information about how much time is left for the cap to end
	 */
	override fun timeForCapToEndString(): String
	{
		
		val millis = millisForCapToEnd()
		val seconds = millis / 1000 % 60
		val minutes = millis / (1000 * 60) % 60
		val hours = millis / (1000 * 60 * 60) % 24
		val days = millis / (1000 * 60 * 60 * 24)
		
		return formatTime(days, hours, minutes, seconds)
	}
	
	/**
	 * Amount of time in millis until the cap ends for the current time slot
	 * @return Long
	 */
	private fun millisForCapToEnd(): Long
	{
		return currentTimeSlot.second.toEpochMilliseconds() - dateFactory.now().toEpochMilliseconds()
	}
	
	/**
	 * Formats the time in a human readable way for the logs
	 * @param days Long
	 * @param hours Long
	 * @param minutes Long
	 * @param seconds Long
	 * @return String
	 */
	private fun formatTime(days: Long, hours: Long, minutes: Long, seconds: Long): String
	{
		return (if (days > 0) "${days}d " else "") +
				(if (hours > 0) "${hours}h " else "") +
				(if (minutes > 0) "${minutes}m " else "") +
				"${seconds}s"
	}
	
	/**
	 * Just increases the counter and stores the value in memory
	 * @param prefs ISimpleDataSave
	 * @return Unit
	 */
	private fun increaseCounter(prefs: ISimpleDataSave)
	{
		currentAdCounter += 1
		prefs.edit().putInt(userPrefCounterId, currentAdCounter).apply()
	}
	
	/**
	 * static library for checking local frequency cap data before we have data about how to cap a configuration from the server
	 */
	companion object
	{
		private const val CURRENT_COUNTER_SUFFIX = "_counter"
		private const val MAX_AD_AMOUNT_PER_TIME_SLOT_SUFFIX = "_ad_amount_per_time_slot"
		private const val CURRENT_TIME_SLOT_START_SUFFIX = "_time_slot_start"
		private const val CURRENT_TIME_SLOT_END_SUFFIX = "_time_slot_end"
		
		/**
		 * Looks if the r89ConfigId has reached the cap in the current local data without
		 * @param prefs ISimpleDataSave
		 * @param r89ConfigId String
		 * @return Boolean
		 */
		fun hasReachedCapInSavedData(prefs: ISimpleDataSave, r89ConfigId: String, dateFactory: R89DateFactory): Boolean
		{
			if (existsInPrefs(prefs, r89ConfigId))
			{
				val savedAdAmount = getAdAmountPerTimeSlot(prefs, r89ConfigId)
				val savedTimeSlotSize = getTimeSlotSize(prefs, r89ConfigId)
				
				val frequencyCap = FrequencyCapNormal(
					prefs,
					r89ConfigId,
					adAmountPerTimeSlot = savedAdAmount,
					timeSlotSize = savedTimeSlotSize,
					dateFactory = dateFactory
				)
				
				return frequencyCap.nextIncreaseCounterIsGoingToSucceed(0)
			}
			return false
		}
		
		/**
		 * Just checks if the counter exists, if it does then we assume everything else exists
		 * @param prefs SharedPreferences
		 * @param r89ConfigId String
		 * @return Boolean
		 */
		private fun existsInPrefs(prefs: ISimpleDataSave, r89ConfigId: String): Boolean
		{
			return prefs.contains(r89ConfigId + CURRENT_COUNTER_SUFFIX)
		}
		
		/**
		 * Returns the time slot size that was set in the previous session
		 * @param prefs ISimpleDataSave
		 * @param r89ConfigId String
		 * @return Long
		 */
		private fun getTimeSlotSize(prefs: ISimpleDataSave, r89ConfigId: String): Long
		{
			val timeSlotStart = prefs.getLong(r89ConfigId + CURRENT_TIME_SLOT_START_SUFFIX, -1)
			val timeSlotEnd = prefs.getLong(r89ConfigId + CURRENT_TIME_SLOT_END_SUFFIX, -1)
			
			return timeSlotEnd - timeSlotStart
		}
		
		/**
		 * Returns the ad amount per time slot that was set in the previous session
		 * @param prefs ISimpleDataSave
		 * @param r89ConfigId String
		 * @return Int
		 */
		private fun getAdAmountPerTimeSlot(prefs: ISimpleDataSave, r89ConfigId: String): Int
		{
			return prefs.getInt(r89ConfigId + MAX_AD_AMOUNT_PER_TIME_SLOT_SUFFIX, -1)
		}
	}
}
