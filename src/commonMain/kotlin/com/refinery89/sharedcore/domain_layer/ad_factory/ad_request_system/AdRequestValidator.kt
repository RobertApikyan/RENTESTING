package com.refinery89.sharedcore.domain_layer.ad_factory.ad_request_system

import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll.InfiniteScrollEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial.RewardItem
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.formatsBasesEvents.BasicEventListener
import com.refinery89.sharedcore.domain_layer.frequency_cap.FrequencyCapHandler
import com.refinery89.sharedcore.domain_layer.frequency_cap.IFrequencyCap
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.unit_config.AdUnitConfigData

/**
 * Responsibilities are:
 * - validate if a request can continue or not
 * - Log Information about the validation process
 * - Enqueue Ad Requests for later resolution
 */
internal class AdRequestValidator(private val TAG: String)
{
	/**
	 * Validation State object Used internally to enqueue the request or not
	 * @property canContinue Boolean
	 * @property capReached [canContinue] is false because cap was reached
	 * @property errorHappened [canContinue] is false because error happen
	 * @property frequencyCap when validating if frequency cap was reached we stored it here
	 * @property adUnitConfigData when validating configuration that config object was stored here
	 * @constructor
	 */
	private data class AdRequestCanContinueResult(
		val canContinue: Boolean,
		val capReached: Boolean,
		val errorHappened: Boolean,
		val frequencyCap: IFrequencyCap?,
		val adUnitConfigData: AdUnitConfigData?,
	)
	
	/**
	 * Data returned to the user of the Validator. It should inform if the request can continue or not, has data stored into it so it does not need to be pulled again once
	 * the request is resumed
	 * @property canContinue if the request can continue or not
	 * @property adId the ID of the ad if [canContinue] is false, otherwise it's -1.
	 * @property frequencyCap frequencyCap to be used if [canContinue] is true null otherwise
	 * @property adUnitConfigData config data to be used if [canContinue] is true null otherwise
	 */
	internal data class AdRequestValidationResult(
		val canContinue: Boolean,
		val adId: Int,
		val frequencyCap: IFrequencyCap?,
		val adUnitConfigData: AdUnitConfigData?,
	)
	
	/**
	 *
	 * @param format the requested format
	 * @param configurationID the r89 configuration ID of the request
	 * @param frequencyCapHandler frequency cap handler that encapsulates logic about frequency capping
	 * @param lastAdId if the request is enqueued the id that the ad will have in the future
	 * @param uninitializedSdkAdRequestHandler ad request handler for when the request must be enqueued
	 * @param wrapper parameter for when the request must be enqueued
	 * @param lifecycleCallbacks parameter for when the request must be enqueued
	 * @param infiniteScrollTag parameter for when the request must be enqueued
	 * @param activity parameter for when the request must be enqueued
	 * @param afterInterstitial parameter for when the request must be enqueued
	 * @return AdRequestValidationResult if the [AdRequestValidationResult.canContinue] is true follow with your normal request and use data inside the object, if false give the
	 * publisher the [AdRequestValidationResult.adId] for their ad it will have the [lastAdId] if the request was enqueue otherwise it will have -1 id for error and logs have
	 * already been done by the validator
	 */
	fun adObjectCreationValidation(
		format: Formats,
		configurationID: String,
		frequencyCapHandler: FrequencyCapHandler,
		lastAdId: Int,
		uninitializedSdkAdRequestHandler: UninitializedSdkAdRequestHandler,
		wrapper: Any? = null,
		lifecycleCallbacks: BasicEventListener? = null,
		infiniteScrollTag: String? = null,
		activity: Any? = null,
		afterInterstitial: (() -> Unit)? = null,
		infiniteScrollEventListener: InfiniteScrollEventListenerInternal? = null,
		rewardReceived: ((RewardItem) -> Unit)? = null,
	): AdRequestValidationResult
	{
		
		val (canContinue, hasReachedTheCap, errorHappened, frequencyCap, adUnitConfigData) = canContinueWithRequest(
			format = format,
			configurationID = configurationID,
			frequencyCapHandler = frequencyCapHandler
		)
		
		val adId = if (!canContinue && !errorHappened && !hasReachedTheCap)
		{
			uninitializedSdkAdRequestHandler.createRequestWithFormat(
				format,
				configurationID,
				wrapper = wrapper,
				lifeCycleListener = lifecycleCallbacks,
				infiniteScrollTag = infiniteScrollTag,
				activity = activity,
				afterInterstitial = afterInterstitial,
				infiniteScrollLifecycleListener = infiniteScrollEventListener,
				rewardReceived = rewardReceived
			)
			//can't continue but we can enqueue the request
			lastAdId
		} else
		{
			// can continue normally, or error happen, or cap reached
			-1
		}
		
		return AdRequestValidationResult(
			canContinue = canContinue,
			adId = adId,
			frequencyCap = frequencyCap,
			adUnitConfigData = adUnitConfigData
		)
	}
	
	/**
	 * - validate if the sdk is initialized and the frequency cap is not reached to enqueue the call
	 * - validated that the configuration exists.
	 * - validates that the config data for this id is for this format.
	 * - validates that the frequency cap has not been reached.
	 *
	 * @param format Formats
	 * @param configurationID String
	 * @param frequencyCapHandler FrequencyCapHandler
	 * @return AdRequestCanContinueResult
	 */
	private fun canContinueWithRequest(
		format: Formats,
		configurationID: String,
		frequencyCapHandler: FrequencyCapHandler,
	): AdRequestCanContinueResult
	{
		val canNotContinueEnqueueRequest = AdRequestCanContinueResult(
			canContinue = false,
			capReached = false,
			errorHappened = false,
			frequencyCap = null,
			adUnitConfigData = null
		)
		
		val canNotContinueDontEnqueueRequest = AdRequestCanContinueResult(
			canContinue = false,
			capReached = true,
			errorHappened = false,
			frequencyCap = null,
			adUnitConfigData = null
		)
		
		val canNotContinueErrorHappenedDontEnqueueRequest = AdRequestCanContinueResult(
			canContinue = false,
			capReached = false,
			errorHappened = true,
			frequencyCap = null,
			adUnitConfigData = null
		)
		
		if (!R89SDKCommon.isInitialized(TAG))
		{
			R89LogUtil.e(
				TAG, "You have to call any initialization method from the sdk just before calling other methods from the AdFactory or the R89SDK objects.\n" +
						"So call initialization method and then other stuff", false
			)
			return canNotContinueDontEnqueueRequest
		}
		
		// validate if the sdk is initialized and the frequency cap is not reached to enqueue the call
		if (!R89SDKCommon.hasConsentData(TAG))
		{
			if (frequencyCapHandler.hasReachedCapInSavedData(configurationID)) // This is checked only against shared prefs before we get any data from the server
			{
				R89LogUtil.e(TAG, "Frequency Cap reached for configId: $configurationID", false)
				return canNotContinueDontEnqueueRequest
			}
			return canNotContinueEnqueueRequest
		}
		
		// validates that configuration exists. This needs to be done after initialization otherwise will always fail because no data will be present
		val adUnitConfigData: AdUnitConfigData =
			R89SDKCommon.globalConfig.getConfig(configurationID).getOrElse {// we can assert that global config is not null because we have passed the isInitialized check
				R89LogUtil.e(TAG, it)
				return@canContinueWithRequest canNotContinueErrorHappenedDontEnqueueRequest
			}
		
		// validates that the config data for this id is for this format. This needs to be done after initialization otherwise will always fail because no data will be present
		if (!configIsForFormat(adUnitConfigData, format))
		{
			R89LogUtil.e(
				TAG,
				"This $adUnitConfigData is for ${adUnitConfigData.formatTypeString} not for: $format",
				false
			)
			return canNotContinueErrorHappenedDontEnqueueRequest
		}
		
		// validates that the frequency cap has not been reached. This needs to be done after initialization otherwise will always fail because no data will be present
		val frequencyCap = frequencyCapHandler.getFrequencyCap(adUnitConfigData)
		val canDisplayAd = frequencyCap.nextIncreaseCounterIsGoingToSucceed(0)
		if (!canDisplayAd)
		{
			R89LogUtil.e(
				TAG,
				"""Frequency Cap reached for configId: ${adUnitConfigData.r89configId}.
					|You should have called this in: ${frequencyCap.timeForCapToEndString()} later from now""".trimMargin(),
				false
			)
			return canNotContinueDontEnqueueRequest
		}
		
		// can continue normally cache all data that was fetched in this call that is needed for the actual request
		return AdRequestCanContinueResult(
			canContinue = true,
			capReached = false,
			errorHappened = false,
			frequencyCap = frequencyCap,
			adUnitConfigData = adUnitConfigData
		)
	}
	
	/**
	 * Checks [AdUnitConfigData.formatTypeString] == [formatToCompare]
	 * @param config AdUnitConfigData
	 * @param formatToCompare the format we hop
	 * @return Result<Unit>
	 */
	private fun configIsForFormat(config: AdUnitConfigData, formatToCompare: Formats): Boolean
	{
		val configFormat = config.formatTypeString
		
		return configFormat == formatToCompare
	}
}