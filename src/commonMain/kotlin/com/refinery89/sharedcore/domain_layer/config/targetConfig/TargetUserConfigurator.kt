package com.refinery89.sharedcore.domain_layer.config.targetConfig

import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.target_config.TargetConfigUserScheme

//TODO provide access to this class after the sdk is initialized or before it.
/**
 * This class is only used for setting user private date when allowed
 * - User's age, year of birth, gender, location,
 * - First Party data
 * - User Keywords
 */
internal class TargetUserConfigurator
{
	/**
	 * takes a string FEMALE or MALE or female or male puts it in uppercase so
	 * prebid function works and then searches a value from [genderKey] and sets
	 * it on prebid sdk
	 *
	 * @param genderKey String
	 */
	private fun setGender(genderKey: String)
	{
		//ensure format is right for prebid
		/*val prebidGenderKey = genderKey.uppercase()
		val prebidGenderVal = TargetingParams.GENDER.genderByKey(prebidGenderKey)
		TargetingParams.setGender(prebidGenderVal)*/
		
	}
	
	/**
	 * This adds or Updates the key being added. Users FirstParty data needs
	 * consent and it will be used only By ACL Bidders.
	 */
	private fun addFPUserData(fpud: Map<String, Set<String>>)
	{
		/*if (fpud.isEmpty())
		{
			throw IllegalArgumentException("map is empty in setGlobalFPInventoryData")
		}

		var keyIsInRegistered: Boolean
		var keyHasTheSameData: Boolean

		fpud.forEach { (key, newDataSet) ->
			//Throw error indicating type of the newDataSet and which key threw the error
			if (newDataSet.isEmpty())
			{
				throw IllegalArgumentException("value of type ${newDataSet.getTypeName()} is size is 0 for map key: $key")
			}

			keyIsInRegistered = TargetingParams.getUserDataDictionary().containsKey(key)

			if (keyIsInRegistered) //if the key exists
			{
				TargetingParams.getUserDataDictionary()[key]?.let { nonNullableCurrentDataSet ->
					keyHasTheSameData = nonNullableCurrentDataSet.containsAll(newDataSet)

					if (!keyHasTheSameData) //if key has different set, different = more, less, changed values inside the set<string>
					{
						TargetingParams.updateUserData(key, newDataSet)
					}
				}
			} else //if the key does not exist then add it to the dictionary with all the data
			{
				newDataSet.forEach { data ->

					TargetingParams.addUserData(key, data)
				}
			}
		}*/
	}
	
	private fun addUserKeywords(keywords: Set<String>)
	{
//		TargetingParams.addUserKeywords(keywords)
	}
	
	/**
	 * used to set the data coming from global config
	 */
	fun provideConfiguration(data: TargetConfigUserScheme?)
	{
		/*data?.let { notNullData ->
			notNullData.age?.let { age ->
				TargetingParams.setUserAge(age)
			}
			notNullData.gender?.let { gender ->
				setGender(gender)
			}
			notNullData.latLng?.let { (lat, lng) ->
				TargetingParams.setUserLatLng(lat, lng)
			}
			notNullData.FPud?.let { fpud ->
				addFPUserData(fpud)
			}
			notNullData.keywords?.let { keywords ->
				addUserKeywords(keywords)
			}
		}*/
	}
}
