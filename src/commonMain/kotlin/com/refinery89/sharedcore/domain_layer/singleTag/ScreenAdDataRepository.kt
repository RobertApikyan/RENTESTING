package com.refinery89.sharedcore.domain_layer.singleTag

import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.single_tag.AdPlaceData
import com.refinery89.sharedcore.domain_layer.models.single_tag.AdScreenData

/**
 * ScreenAd or AdScreen is an App Screen that has one or more AdPlaces where ads can be shown.
 *
 * It's a way for the [SingleTagConfigurator] to retrieve the information from the [adScreenDataList],
 * it acts as a repository so every CRUD operation to the [adScreenDataList] should be done through this class
 * and also any consults or check should be also performed here
 * @property hasFilledTheContextData Boolean
 * @property adScreenDataList MutableList<AdContextData>
 */
internal class ScreenAdDataRepository
{
	var hasFilledTheContextData = false
	private var adScreenDataList: MutableList<AdScreenData> = mutableListOf()
	
	/**
	 * Fills the repository with the data
	 * @param adContextList List<AdScreenData>
	 * @return Unit
	 */
	fun fill(adContextList: List<AdScreenData>)
	{
		/**
		 * Checks if the AdContext List Provided has empty Data and Logs every AdContext Name
		 * @param adContextList List<AdContextData>
		 * @return Boolean
		 */
		fun anyScreenHasEmptyData(adContextList: List<AdScreenData>): Boolean
		{
			//Get all the empty Lists
			val emptyScreenList = adContextList.filter { it.adPlaceList.isEmpty() }
			//If there are any empty lists, return without doing anything
			if (emptyScreenList.isNotEmpty())
			{
				emptyScreenList.forEach { emptyContext ->
					R89LogUtil.e(TAG, "Empty Placement list ${emptyContext.screenName}", false)
				}
				return true
			}
			return false
		}
		if (anyScreenHasEmptyData(adContextList)) return
		
		adScreenDataList.addAll(adContextList)
		hasFilledTheContextData = true
	}
	
	/**
	 * Checks if the [componentName] screen has any AdPlace
	 * @param componentName String
	 * @param isFragment Boolean
	 * @return Boolean
	 */
	fun screenIsInsideData(componentName: String, isFragment: Boolean): Boolean
	{
		fun isScreenPresentForTransitions(componentName: String, isFragment: Boolean): Boolean
		{
			fun getFirstScreenDataWithTransitionTo(name: String) = adScreenDataList.firstOrNull {
				it.hasTransitionTo(name)
			}
			
			val screenDataFoundAsFrom: AdScreenData? = getScreenDataFor(componentName, isFragment)
			val screenDataFoundAsTo: AdScreenData? = getFirstScreenDataWithTransitionTo(componentName)
			
			val foundAsFrom: Boolean = screenDataFoundAsFrom != null
			val foundAsTo: Boolean = screenDataFoundAsTo != null
			
			return foundAsFrom || foundAsTo
		}
		
		return hasFilledTheContextData && isScreenPresentForTransitions(componentName, isFragment)
	}
	
	/**
	 * Gets the Data for a screen
	 * @param fromScreenName String
	 * @param isFragment Boolean
	 * @return AdScreenData?
	 */
	fun getScreenDataFor(fromScreenName: String, isFragment: Boolean): AdScreenData?
	{
		return if (isFragment)
		{
			adScreenDataList.firstOrNull {
				it.isFragmentScreenFor(fromScreenName)
			}
		} else
		{
			adScreenDataList.firstOrNull {
				it.isActivityScreenFor(fromScreenName)
			}
		}
	}
	
	/**
	 * checks if there is a transition for the combination of activities provided.
	 * @return Pair<Boolean, List<AdPlace>?> if so, it returns true and the list of adPlaces to resolve, otherwise it returns false and null
	 */
	fun transitionExists(fromName: String, toName: String, isFragment: Boolean): Pair<Boolean, List<AdPlaceData>?>
	{
		
		val screenData: AdScreenData? = getScreenDataFor(fromName, isFragment)
		
		if (screenData == null)
		{
			R89LogUtil.e(TAG, "No Context Data for $fromName", false)
			return false to null
		}
		
		val outTransitionExists = screenData.hasTransitionTo(toName)
		
		if (!outTransitionExists)
		{
			R89LogUtil.e(TAG, "No Out Transition for $fromName to $toName", false)
			return false to null
		}
		
		return true to screenData.getAllAdPlacesWithTransitionTo(toName)
	}
	
	/**
	 * @suppress
	 */
	companion object
	{
		private const val TAG = "ContextDataRepo"
	}
}