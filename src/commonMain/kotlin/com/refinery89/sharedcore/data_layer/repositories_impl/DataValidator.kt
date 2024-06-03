package com.refinery89.sharedcore.data_layer.repositories_impl

import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.PublisherScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag.AdScreenScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag.ScreenEventScheme
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.R89Banner
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner_outstream.R89BannerVideoOutstream
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll.R89InfiniteScroll
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.R89Interstitial
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial.R89RewardedInterstitial
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.video_interstitial.R89VideoInterstitial
import com.refinery89.sharedcore.domain_layer.internalToolBox.internal_error_handling_tool.R89Error
import com.refinery89.sharedcore.domain_layer.third_party_libraries.SerializationLibrary

/**
 * Responsibilities of this class are:
 * - validate the data coming from the Refinery 89 SDK API for the Publisher Info Object
 * - if invalid it throws an error with **DETAILED** information about why didn't pass validation
 */
internal class DataValidator(private val serializationLibrary: SerializationLibrary)
{
	/**
	 * Calls private functions to validate the data if something is invalid it throws [R89Error.PublisherDataNotValid] error with **DETAILED** information about why didn't pass validation
	 * and does the thing the [DataValidator] is required to do.
	 * @param data data to be validated
	 */
	fun checkOrThrowPublisherData(data: PublisherScheme)
	{
		checkOrThrowDirectBasicTypes(data)
		checkOrThrowPrebidServerConfig(data)
		checkOrThrowUnitConfigData(data)
		checkOrThrowTargetAppData(data)
		checkOrThrowSingleTagData(data)
	}
	
	private fun checkOrThrowPrebidServerConfig(data: PublisherScheme)
	{
		with(data.prebidServerConfigData) {
			if (this != null)
			{
				if (this.accountId.isNullOrEmpty())
				{
					throw R89Error.PublisherDataNotValid("Prebid server config data accountId is null or blank")
				}
				if (this.host.isNullOrEmpty())
				{
					throw R89Error.PublisherDataNotValid("Prebid server config data host is null or blank")
				}
				if (this.serverTimeout == null)
				{
					throw R89Error.PublisherDataNotValid("Prebid server config data serverTimeout is null or blank")
				}
			} else
			{
				//TODO: uncomment when release to 2.0 of api
//				throw R89Error.PublisherDataNotValid("Prebid server config data is null or blank")
			}
		}
		
	}
	
	//TODO refactor this function to be less code
	private fun checkOrThrowAdPlaceData(data: AdScreenScheme)
	{
		var errorMessage = ""
		data.adPlaceList!!.forEach {
			when (it.adFormat)
			{
				Formats.BANNER                 ->
				{
					val missingR89ConfigId = it.r89configId.isNullOrEmpty()
					val containsEventsToTrack = !it.eventsToTrack.isNullOrEmpty()
					val containsDataMap = !it.dataMapValue.isNullOrEmpty()
					val missingWrapperData = it.wrapperData == null
					val missingWrapperTag =
						it.wrapperData == null || it.wrapperData.wrapperTag.isNullOrEmpty()
					
					if (missingR89ConfigId || containsEventsToTrack || containsDataMap || missingWrapperData || missingWrapperTag)
					{
						errorMessage += "AdPlace with invalid wrapper: ${it.r89configId ?: "R89ConfigId is null"}\n" +
								"\tFormat: BANNER\n" +
								(if (missingR89ConfigId) "\t-R89ConfigId is missing\n" else "") +
								(if (containsEventsToTrack) "\t-contains eventsToTrack, but should not\n" else "") +
								(if (containsDataMap) "\t-contains dataMap, but should not\n" else "") +
								(if (missingWrapperData) "\t-wrapperData is missing\n" else "") +
								(if (missingWrapperTag) "\t-wrapperTag in wrapperData is missing\n" else "") +
								"--------------------------------------------------------------------------------\n\n"
					}
				}
				
				Formats.VIDEO_OUTSTREAM_BANNER ->
				{
					val missingR89ConfigId = it.r89configId.isNullOrEmpty()
					val containsEventsToTrack = !it.eventsToTrack.isNullOrEmpty()
					val containsDataMap = !it.dataMapValue.isNullOrEmpty()
					val missingWrapperData = it.wrapperData == null
					val missingWrapperTag =
						it.wrapperData == null || it.wrapperData.wrapperTag.isNullOrEmpty()
					
					if (missingR89ConfigId || containsEventsToTrack || containsDataMap || missingWrapperData || missingWrapperTag)
					{
						errorMessage += "AdPlace with invalid wrapper: ${it.r89configId ?: "R89ConfigId is null"}\n" +
								"\tFormat: VIDEO_OUTSTREAM_BANNER\n" +
								(if (missingR89ConfigId) "\t-R89ConfigId is missing\n" else "") +
								(if (containsEventsToTrack) "\t-contains eventsToTrack, but should not\n" else "") +
								(if (containsDataMap) "\t-contains dataMap, but should not\n" else "") +
								(if (missingWrapperData) "\t-wrapperData is missing\n" else "") +
								(if (missingWrapperTag) "\t-wrapperTag in wrapperData is missing\n" else "") +
								"--------------------------------------------------------------------------------\n\n"
					}
				}
				
				Formats.INTERSTITIAL           ->
				{
					val missingR89ConfigId = it.r89configId.isNullOrEmpty()
					val containsDataMap = !it.dataMapValue.isNullOrEmpty()
					val containsWrapperData = it.wrapperData != null
					val missingEventsToTrack = it.eventsToTrack.isNullOrEmpty()
					val invalidScreenEventScreenScheme: MutableList<ScreenEventScheme> =
						mutableListOf()
					
					if (!it.eventsToTrack.isNullOrEmpty())
					{
						it.eventsToTrack.forEach { eventToTrack ->
							if ((eventToTrack.to != null && eventToTrack.buttonId != null) || (eventToTrack.to == null && eventToTrack.buttonId == null))
							{
								invalidScreenEventScreenScheme.add(eventToTrack)
							}
						}
					}
					
					if (missingR89ConfigId || containsDataMap || containsWrapperData || missingEventsToTrack || invalidScreenEventScreenScheme.isNotEmpty())
					{
						errorMessage += "AdPlace with invalid wrapper: ${it.r89configId ?: "R89ConfigId is null"}\n" +
								"Format: INTERSTITIAL\n" +
								(if (missingR89ConfigId) "\t-R89ConfigId is missing\n" else "") +
								(if (containsDataMap) "\t-contains dataMap, but should not\n" else "") +
								(if (containsWrapperData) "\t-contains wrapperData, but should not\n" else "") +
								if (invalidScreenEventScreenScheme.isNotEmpty()) "\t-eventsToTrack contains invalid parameters:\n" else ""
						
						invalidScreenEventScreenScheme.forEach { invalidEventToTrack ->
							if (invalidEventToTrack.to == null && invalidEventToTrack.buttonId == null)
							{
								errorMessage += "\t\t*to and buttonId both are null\n"
							} else if (invalidEventToTrack.to != null && invalidEventToTrack.buttonId != null)
							{
								errorMessage += "\t\t*to and buttonId are both present, only one should be present at a time\n"
							}
						}
						errorMessage += "\n---------------------------------------------------------------------------------\n\n"
						errorMessage.trim()
					}
				}
				
				Formats.INFINITE_SCROLL        ->
				{
					val missingR89ConfigId = it.r89configId.isNullOrEmpty()
					val containsEventsToTrack = !it.eventsToTrack.isNullOrEmpty()
					val missingDataMap = it.dataMapValue.isNullOrEmpty()
					val dataMapValue = it.dataMapValue
					var missingInfiniteScrollItemWrapper =
						dataMapValue.isNullOrEmpty() || dataMapValue["infiniteScrollItemWrapper"] == null
					var wrongTypeInfiniteScrollItemWrapper = false
					val missingWrapperData = it.wrapperData == null
					val missingWrapperTag =
						it.wrapperData == null || it.wrapperData.wrapperTag.isNullOrEmpty()
					
					if (!dataMapValue.isNullOrEmpty())
					{
						if (dataMapValue["infiniteScrollItemWrapper"] != null)
						{
							when (dataMapValue["infiniteScrollItemWrapper"])
							{
								is String ->
								{
									if (dataMapValue["infiniteScrollItemWrapper"].toString()
											.isEmpty()
									)
									{
										missingInfiniteScrollItemWrapper = true
									}
								}
								
								else      ->
								{
									wrongTypeInfiniteScrollItemWrapper = true
								}
								//Add other formats of dataMap items if we will use them
							}
						}
					}
					
					if (missingR89ConfigId || containsEventsToTrack || missingDataMap || missingInfiniteScrollItemWrapper || wrongTypeInfiniteScrollItemWrapper || missingWrapperData || missingWrapperTag)
					{
						errorMessage += "AdPlace with invalid wrapper: ${it.r89configId ?: "R89ConfigId is null"}\n" +
								"\tFormat: INFINITE_SCROLL\n" +
								(if (missingR89ConfigId) "\t-R89ConfigId is missing\n" else "") +
								(if (containsEventsToTrack) "\t-contains eventsToTrack, but should not\n" else "") +
								(if (missingDataMap) "\t-dataMap is missing\n" else "") +
								(if (missingWrapperData) "\t-wrapperData is missing\n" else "") +
								(if (missingWrapperTag) "\t-wrapperTag in wrapperData is missing\n" else "") +
								(if (missingInfiniteScrollItemWrapper) "\t-infiniteScrollItemWrapper in dataMap is missing\n" else "") +
								(if (wrongTypeInfiniteScrollItemWrapper) "\t-infiniteScrollItemWrapper is of wrong type, should be String\n" else "") +
								"------------------------------------------------------------------------------------------------------------------------\n\n"
					}
				}
				
				else                           ->
				{
				}
			}
		}
		if (errorMessage.isNotEmpty())
		{
			throw R89Error.PublisherDataNotValid(errorMessage)
		}
	}
	
	private fun checkOrThrowDirectBasicTypes(data: PublisherScheme)
	{
		if (data.publisherId.isNullOrBlank())
		{
			throw R89Error.PublisherDataNotValid("Publisher Id is null or blank")
		}
		if (data.appId.isNullOrBlank())
		{
			throw R89Error.PublisherDataNotValid("App Id is null or blank")
		}
		if (data.name.isNullOrBlank())
		{
			throw R89Error.PublisherDataNotValid("Name is null or blank")
		}
		if (data.sendAllLogs == null)
		{
			//TODO: Uncomment when released 2.0 api
//			throw R89Error.PublisherDataNotValid("SendAllLogs is null")
		}
	}
	
	private fun checkOrThrowUnitConfigData(data: PublisherScheme)
	{
		if (data.unitConfigList.isNullOrEmpty())
		{
			throw R89Error.PublisherDataNotValid("UnitConfigList is null or empty")
		}
		
		val foundedInvalidItems = data.unitConfigList.filter {
			it.r89configId.isNullOrBlank() ||
					it.formatTypeString == null ||
					getFormatConfigValidator(it.formatTypeString).isInValidConfig(
						it,
						serializationLibrary
					)
			//TODO uncomment when we will use new API version
//                   ||  (it.wrapperStyleScheme == null && (it.formatTypeString == Formats.BANNER || it.formatTypeString == Formats.VIDEO_OUTSTREAM_BANNER))
		}
		
		//if found an invalid item, throw an error
		if (foundedInvalidItems.isNotEmpty())
		{
			var errorString = ""
			foundedInvalidItems.forEach { screenWithEmptyData ->
				errorString += """UnitConfigList has an invalid item, detailed info:
				- r89configId: ${screenWithEmptyData.r89configId} is invalid: ${screenWithEmptyData.r89configId.isNullOrBlank()}
                - formatTypeString: ${screenWithEmptyData.formatTypeString} is invalid: ${screenWithEmptyData.formatTypeString == null}
                - wrapperStyleScheme: ${screenWithEmptyData.wrapperStyleScheme} is invalid: ${screenWithEmptyData.wrapperStyleScheme == null}
                ---------------------------------------------------------------------------------------------------------
				Format Validation message:
				- ${
					getFormatConfigValidator(screenWithEmptyData.formatTypeString).getErrorMessage(
						screenWithEmptyData, serializationLibrary
					)
				}
                """.trim()
			}
			
			
			throw R89Error.PublisherDataNotValid(errorString)
		}
	}
	
	private fun getFormatConfigValidator(formatTypeString: Formats?): FormatConfigValidator
	{
		if (formatTypeString == null)
		{
			return FormatConfigValidator.Default
		}
		
		return when (formatTypeString)
		{
			Formats.BANNER                       -> R89Banner
			Formats.VIDEO_OUTSTREAM_BANNER       -> R89BannerVideoOutstream
			Formats.INFINITE_SCROLL              -> R89InfiniteScroll
			Formats.INTERSTITIAL                 -> R89Interstitial
			Formats.VIDEO_OUTSTREAM_INTERSTITIAL -> R89VideoInterstitial
			Formats.REWARDED_INTERSTITIAL        -> R89RewardedInterstitial
		}
	}
	
	private fun checkOrThrowTargetAppData(data: PublisherScheme)
	{
		if (data.targetConfigAppData == null)
		{
			throw R89Error.PublisherDataNotValid("TargetConfigAppData is null")
		}
		
		if (data.targetConfigAppData.storeUrl.isNullOrBlank())
		{
			throw R89Error.PublisherDataNotValid("StoreUrl is null or blank")
		}
		//TODO: Add validator for domain right now the api can send a null value so we had to remove until the next version of the api
	}
	
	//TODO make better validations with the new object dataMap for single tag ad places it is used for the infinite scroll
	// make each format validate an ad place that is suppose to be it's format
	private fun checkOrThrowSingleTagData(data: PublisherScheme)
	{
		if (!R89SDKCommon.isSingleLineOn) return
		
		if (data.singleTagAdScreenData.isNullOrEmpty())
		{
			throw R89Error.PublisherDataNotValid("SingleTagAdScreenData is null or empty and Single Tag is ON")
		}
		
		val foundScreensWithEmptyData = data.singleTagAdScreenData.filter {
			it.screenName.isNullOrBlank() ||
					it.adPlaceList.isNullOrEmpty() ||
					it.isFragment == null
		}
		
		if (foundScreensWithEmptyData.isNotEmpty())
		{
			var errorString = ""
			foundScreensWithEmptyData.forEach { screenWithEmptyData ->
				errorString += """SingleTagAdScreenData has an invalid item, detailed info:
                - screenName: ${screenWithEmptyData.screenName} is invalid: ${screenWithEmptyData.screenName.isNullOrBlank()}
                - adPlaceList: ${screenWithEmptyData.adPlaceList} is invalid: ${screenWithEmptyData.adPlaceList.isNullOrEmpty()}
                - isFragment: ${screenWithEmptyData.isFragment} is invalid: ${screenWithEmptyData.isFragment == null}
                ---------------------------------------------------------------------------------------------------------
                """.trim()
			}
			throw R89Error.PublisherDataNotValid(errorString)
		}
		
		
		data.singleTagAdScreenData.forEach { screen ->
			checkOrThrowAdPlaceData(screen)
		}
		
	}
}