package com.refinery89.sharedcore.domain_layer.frequency_cap

import com.refinery89.sharedcore.domain_layer.internalToolBox.date_time.R89DateFactory
import com.refinery89.sharedcore.domain_layer.models.unit_config.AdUnitConfigData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.ISimpleDataSave

/**
 * Responsibilities are:
 * - Storing [IFrequencyCap] instances
 * - Retrieving [IFrequencyCap] instances
 * - Middle man between Static API from [FrequencyCapNormal]
 * @property adFrequencyCap MutableMap<String, IFrequencyCap>
 * @property prefs ISimpleDataSave library provided that interacts with the os for simple data storage
 */
internal class FrequencyCapHandler
{
	
	//TODO we should store this in the cloud and restore it when the SDK initializes,
	// because the user can delete shared prefs cleaning the data of the app and then we lose all caps
	// this would require storing or using user id's to identify the user and then restore the caps of that user which....
	private val adFrequencyCap: MutableMap<String, IFrequencyCap> = mutableMapOf()
	private var prefs: ISimpleDataSave? = null
	private var dateFactory: R89DateFactory? = null
	
	/**
	 * Providing Dependencies to this object
	 * @param simpleDataSave ISimpleDataSave
	 * @return Unit
	 */
	fun provideDependencies(simpleDataSave: ISimpleDataSave, dateFactory: R89DateFactory)
	{
		this.prefs = simpleDataSave
		this.dateFactory = dateFactory
	}
	
	/**
	 * Returns a [IFrequencyCap] instance to be used.
	 *
	 * Do not call this before you are sure the sdk has been initialized, for this use [isInitialized] method at some point previously of calling this.
	 * @param configData AdUnitConfigData
	 * @return IFrequencyCap
	 * @throws NullPointerException if the sdk has not been initialized
	 */
	fun getFrequencyCap(configData: AdUnitConfigData): IFrequencyCap
	{
		val r89configId = configData.r89configId
		
		if (adFrequencyCap.containsKey(r89configId))
		{
			return adFrequencyCap[r89configId]!!
		} else
		{
			return if (configData.frequencyCapData.isCapped)
			{
				val frequencyCap = FrequencyCapNormal(
					prefs!!,
					configData.r89configId,
					configData.frequencyCapData.adAmountPerTimeSlot,
					configData.frequencyCapData.timeSlotSize,
					dateFactory!!,
				)
				
				adFrequencyCap[r89configId] = frequencyCap
				frequencyCap
			} else
			{
				FrequencyCapNoCap()
			}
			
		}
	}
	
	/**
	 * Checks if a configuration has reached the cap in the current local data.
	 *
	 * Do not call this before you are sure the sdk has been initialized, for this use [isInitialized] method at some point previously of calling this
	 * @param r89ConfigId String
	 * @return Boolean
	 * @throws NullPointerException if the sdk has not been initialized
	 */
	fun hasReachedCapInSavedData(r89ConfigId: String): Boolean
	{
		return FrequencyCapNormal.hasReachedCapInSavedData(prefs!!, r89ConfigId, dateFactory!!)
	}
}