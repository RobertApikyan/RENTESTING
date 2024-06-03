package com.refinery89.sharedcore.data_layer.repositories_impl

import R___Mobile_Native_Library.sharedCore.BuildConfig
import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.PublisherScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.frequency_cap.FrequencyCapScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag.AdPlaceScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag.AdScreenScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag.WrapperScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.wrapper_style.WrapperStyleScheme
import com.refinery89.sharedcore.domain_layer.config.ConfigBuilder
import com.refinery89.sharedcore.domain_layer.config.unitConfig.AdUnitConfigRepository
import com.refinery89.sharedcore.domain_layer.internalToolBox.extensions.getTypeSimpleName
import com.refinery89.sharedcore.domain_layer.internalToolBox.internal_error_handling_tool.R89Error
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.PublisherData
import com.refinery89.sharedcore.domain_layer.models.consent_config.CMPData
import com.refinery89.sharedcore.domain_layer.models.frequency_cap.FrequencyCapData
import com.refinery89.sharedcore.domain_layer.models.prebid_server.PrebidServerConfigData
import com.refinery89.sharedcore.domain_layer.models.single_tag.AdPlaceData
import com.refinery89.sharedcore.domain_layer.models.single_tag.AdScreenData
import com.refinery89.sharedcore.domain_layer.models.single_tag.ScreenEventData
import com.refinery89.sharedcore.domain_layer.models.single_tag.WrapperData
import com.refinery89.sharedcore.domain_layer.models.target_config.TargetConfigAppData
import com.refinery89.sharedcore.domain_layer.models.unit_config.AdUnitConfigData
import com.refinery89.sharedcore.domain_layer.models.wrapper_style.WrapperStyleData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.SerializationLibrary
import com.refinery89.sharedcore.domain_layer.wrapper_features.WrapperPosition

/**
 * Responsibilities of this class are:
 * - Convert the data from the schemes to the domain models. **It assumes all necessary validations are done** This makes sense since these conversion take so many lines we don't
 *   want to make validations with in this class
 */
internal class DataConverter(
	private val serializationLibrary: SerializationLibrary,
)
{
	/** Converts the PublisherScheme to the PublisherData domain model. */
	fun convertToDomainModel(data: PublisherScheme): PublisherData
	{
		val convertedUnitConfig = convertAdUnitConfigurations(data)
		
		val convertedTargetConfigAppData = convertTargetConfigAppData(data)
		
		val convertedPrebidServerConfigData = convertPrebidServerConfigData(data)
		
		val convertedSingleTagData = convertSingleTagData(data.singleTagAdScreenData)
		
		val convertedCMPData = CMPData(
			cmpId = data.cmpData!!.cmpId,
			cmpCodeId = data.cmpData.cmpCodeId
		)
		val repo = AdUnitConfigRepository(convertedUnitConfig)
		return PublisherData(
			publisherId = data.publisherId!!,
			appId = data.appId!!,
			name = data.name!!,
			unitConfigRepo = repo,
			targetConfigAppData = convertedTargetConfigAppData,
			singleTagData = convertedSingleTagData,
			cmpData = convertedCMPData,
			sendAllLogs = data.sendAllLogs ?: false,
			prebidServerConfigData = convertedPrebidServerConfigData
		)
	}
	
	/** Converts the AdUnitConfigurations from the scheme to the domain model. */
	private fun convertAdUnitConfigurations(data: PublisherScheme): List<AdUnitConfigData>
	{
		return data.unitConfigList!!.map {
			AdUnitConfigData(
				r89configId = it.r89configId!!,
				gamUnitId = it.unitId ?: "",
				prebidConfigId = it.configId ?: "",
				formatTypeString = it.formatTypeString!!,
				autoRefreshMillis = it.autoRefreshMillis ?: 30000,
				inventoryKeywords = it.inventoryKeywords,
				unitFPID = it.unitFPID,
				frequencyCapData = convertFrequencyCapData(it.frequencyCapScheme),
				dataMap = it.dataMap,
				wrapperStyleData = convertAdUnitWrapperStyleData(it.wrapperStyleScheme)
			)
		}
	}
	
	private fun convertFrequencyCapData(data: FrequencyCapScheme?): FrequencyCapData
	{
		return if (data == null)
		{
			FrequencyCapData(
				isCapped = false,
				adAmountPerTimeSlot = 0,
				timeSlotSize = 0
			)
		} else
		{
			FrequencyCapData(
				isCapped = data.isCapped,
				adAmountPerTimeSlot = data.adAmountPerTimeSlot,
				timeSlotSize = data.timeSlotSize
			)
		}
	}
	
	private fun convertAdUnitWrapperStyleData(data: WrapperStyleScheme?): WrapperStyleData
	{
		return if (data != null)
		{
			WrapperStyleData(
				position = data.position ?: WrapperPosition.CENTER,
				matchParentHeight = data.matchParentHeight ?: false,
				matchParentWidth = data.matchParentWidth ?: false,
				wrapContent = data.wrapContent ?: true,
				height = data.height ?: -1,
				width = data.width ?: -1,
			)
		} else
		{
			WrapperStyleData(
				WrapperPosition.BOTTOM,
				matchParentHeight = false,
				matchParentWidth = false,
				wrapContent = true,
				height = -1,
				width = -1
			)
		}
		
	}
	
	/** Converts the TargetConfigAppData from the scheme to the domain model. */
	private fun convertTargetConfigAppData(data: PublisherScheme): TargetConfigAppData
	{
		val convertedTargetConfigAppData = data.targetConfigAppData!!.let { targetAppData ->
			TargetConfigAppData(
				storeUrl = targetAppData.storeUrl!!,
				domain = targetAppData.domain,
				appInventoryKeywords = targetAppData.appInventoryKeywords,
				bidderAccessControlList = targetAppData.bidderAccessControlList,
				globalFPInventoryData = targetAppData.globalFPInventoryData
			)
		}
		return convertedTargetConfigAppData
	}
	
	
	private fun convertPrebidServerConfigData(data: PublisherScheme): PrebidServerConfigData
	{
		
		return data.prebidServerConfigData?.let { prebidServerConfig ->
			PrebidServerConfigData(
				accountId = prebidServerConfig.accountId!!,
				host = prebidServerConfig.host!!,
				serverTimeout = prebidServerConfig.serverTimeout!!
			)
		} ?: run {
			//TODO Remove this shit once we release 2.0
			if (R89SDKCommon.productionAuctionServer)
			{
				R89LogUtil.d("Converter", "Using production auction server")
				PrebidServerConfigData(
					BuildConfig.PREBID_SERVER_ACCOUNT_ID,
					ConfigBuilder.PRODUCTION_PREBID_SERVER,
					ConfigBuilder.PREBID_SERVER_TIMEOUT
				)
			} else
			{
				R89LogUtil.d("Converter", "Using testing auction server")
				PrebidServerConfigData(
					ConfigBuilder.TEST_SERVER_ACCOUNT_ID,
					ConfigBuilder.TEST_SERVER_HOST,
					ConfigBuilder.PREBID_SERVER_TIMEOUT
				)
			}
		}
	}
	
	/** Converts the SingleTagData from the scheme to the domain model. */
	private fun convertSingleTagData(data: List<AdScreenScheme>?): List<AdScreenData>?
	{
		if (!R89SDKCommon.isSingleLineOn) return null
		
		return data!!.map {
			AdScreenData(
				isFragment = it.isFragment!!,
				screenName = it.screenName!!,
				adPlaceList = convertAdPlaceData(it.adPlaceList!!)
			)
		}
	}
	
	/** Converts the AdPlaceData from the scheme to the domain model. */
	private fun convertAdPlaceData(adPlaceList: List<AdPlaceScheme>): List<AdPlaceData>
	{
		fun convertWrapperDataRelativePos(schemePos: WrapperScheme.WrapperRelativePosition): WrapperData.WrapperRelativePosition
		{
			return when (schemePos)
			{
				WrapperScheme.WrapperRelativePosition.BEFORE -> WrapperData.WrapperRelativePosition.BEFORE
				WrapperScheme.WrapperRelativePosition.AFTER  -> WrapperData.WrapperRelativePosition.AFTER
				WrapperScheme.WrapperRelativePosition.INSIDE -> WrapperData.WrapperRelativePosition.INSIDE
			}
		}
		//Implement a conversion from AdPlaceScheme to AdPlaceData
		return adPlaceList.map {
			val (wrapperData, events) = if (it.wrapperData != null)
			{
				val wrapperData = WrapperData(
					wrapperTag = it.wrapperData.wrapperTag!!,
					getAllWithTag = it.wrapperData.getAllWithTag!!,
					relativePos = convertWrapperDataRelativePos(it.wrapperData.relativePos!!)
				)
				wrapperData to null
			} else if (it.eventsToTrack != null)
			{
				val events = it.eventsToTrack.map { event ->
					ScreenEventData(
						to = event.to,
						buttonTag = event.buttonId
					)
				}
				null to events
			} else
			{
				throw R89Error.DataValidationFailed("Validation of Ad Places Failed, error thrown in ${it.getTypeSimpleName()} Conversion")
			}
			AdPlaceData(
				r89configId = it.r89configId!!,
				adFormat = it.adFormat!!,
				wrapperData = wrapperData,
				eventsToTrack = events,
				dataMap = it.dataMap,
			)
		}
		
	}
	
}