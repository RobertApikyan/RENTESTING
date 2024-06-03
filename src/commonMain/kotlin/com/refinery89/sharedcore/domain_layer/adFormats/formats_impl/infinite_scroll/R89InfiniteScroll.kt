package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll

import com.refinery89.sharedcore.R89AdFactoryCommon
import com.refinery89.sharedcore.R89SDKCommon
import com.refinery89.sharedcore.data_layer.repositories_impl.FormatConfigValidator
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.unit_config.AdUnitConfigScheme
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.R89BaseAd
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError
import com.refinery89.sharedcore.domain_layer.internalToolBox.dataStructures.queue.ReQueueQueue
import com.refinery89.sharedcore.domain_layer.internalToolBox.extensions.getTypeSimpleName
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.unit_config.AdUnitConfigData
import com.refinery89.sharedcore.domain_layer.third_party_libraries.SerializationLibrary
import kotlin.random.Random

/**
 *
 * @property lastResetNewStartID
 * @property minItems No ads will be shown before this much items have been scrolled
 * @property maxItems After this much items have been scrolled an ad will be shown
 * @property variableProbability range that is ADDED to the final rolled probability to make it a bit random, so the user can't make probability
 * patterns and predict where the ads are going to be
 * @property availableUnitIds The available ad units to be used in the infinite scroll
 * @property generalEventListener events of the infinite scroll
 * but we also add views (our ads), we don't want to process events for our own ads, so we keep track of the views we add
 * @property itemsWithAds The Items for the current scrollView that have an ad
 * @property rolledItems The Items that have been rolled, this is so we don't roll for them again if they have an ad they are also in [itemsWithAds]
 * @property logProbability this number is used in Logs to see the probability of an ad being shown after the roll
 */
internal class R89InfiniteScroll(
	private val osAdObject: IR89InfiniteScrollOS,
) : R89BaseAd()
{
	
	private var lastResetNewStartID: Int = 0
	private var minItems: Int = -1
	private var maxItems: Int = -1
	private var variableProbability: IntRange = 0..0
	private val availableUnitIds: ReQueueQueue<Pair<Formats, String>> = ReQueueQueue()
	internal var generalEventListener: InfiniteScrollEventListenerInternal? = null
	
	//Variable use to control the scroll ads
	private val itemsWithAds: MutableList<Pair<Int, Int>> = mutableListOf()
	private val rolledItems: MutableList<Int> = mutableListOf()
	
	//Debugging purposes
	private var logProbability: Double = 0.0
	
	override fun cacheConfigurationValues(adConfigObject: AdUnitConfigData)
	{
		minItems = adConfigObject.getData("minItems")
		maxItems = adConfigObject.getData("maxItems")
		val minVariableProbability: Int = adConfigObject.getData("variableProbabilityMin")
		val maxVariableProbability: Int = adConfigObject.getData("variableProbabilityMax")
		variableProbability = minVariableProbability..maxVariableProbability
		val unitIdsToDisplay: List<String> = adConfigObject.getData("configsOfAdsToUse")
		
		val shuffledList: MutableList<String> = unitIdsToDisplay.toMutableList()
		shuffledList.shuffle()
		
		shuffledList.forEach { r89configId ->
			
			val configResult = R89SDKCommon.globalConfig.getConfig(r89configId)
			val fetchedConfig = configResult.getOrElse {
				R89LogUtil.e(TAG, it.message + { it.getTypeSimpleName() }, false)
				return@forEach
			}
			
			val format = fetchedConfig.formatTypeString
			
			availableUnitIds.enqueue(format to r89configId)
		}
		
		if (availableUnitIds.count <= 0)
		{
			invalidate()
			R89LogUtil.e(TAG, "There are no units to display in the Infinite Scroll configuration: ${adConfigObject.r89configId}", false)
			return
		}
	}
	
	internal fun getAdForIndex(itemIndex: Int, itemAdWrapper: Any?, childAdLifecycle: BannerEventListenerInternal?): Boolean
	{
		if (!isValid || itemAdWrapper == null) return false
		
		val alreadyRolledForThisItem = rolledItems.contains(itemIndex)
		if (alreadyRolledForThisItem)
		{
			val itemWithAd = itemsWithAds.firstOrNull { it.first == itemIndex }
			//if this is null it means we rolled for this item but it didn't have an ad
			itemWithAd?.let { (_, adId) ->
				R89LogUtil.d(TAG, "Item with id $itemIndex already has an ad, going to show it.")
				osAdObject.show(TAG, adId, itemAdWrapper)
			} ?: run {
				R89LogUtil.d(TAG, "Item with id $itemIndex already rolled but didn't have an ad, do nothing.")
			}
			return true
		}
		
		generalEventListener?.onRoll(itemIndex)
		R89LogUtil.d(TAG, "Roll for id $itemIndex we have scrolled ${getScrolledItemsSinceLastResetForId(itemIndex)} items since last Roll TRUE")
		if (rollForAd(itemIndex))
		{
			R89LogUtil.d(TAG, "Roll for id $itemIndex Returned TRUE")
			
			val nextStartItemId = itemIndex + 1
			resetScrolledItemsCount(nextStartItemId)
			
			val adItemLifecycle: BannerEventListenerInternal = createAdItemLifecycle(itemIndex, childAdLifecycle)
			
			val adId = createAdView(itemAdWrapper, adItemLifecycle)
			
			itemsWithAds.add(itemIndex to adId)
			generalEventListener?.onAdItemCreated(itemIndex, logProbability)
			
			R89LogUtil.d(TAG, "Item with id $itemIndex SHOULD have an AD")
		} else
		{
			R89LogUtil.d(TAG, "Roll for id $itemIndex Returned FALSE")
			generalEventListener?.onRollFailed(itemIndex)
			return false
		}
		
		R89LogUtil.d(TAG, "Roll for id $itemIndex ended with Probability of $logProbability %")
		
		rolledItems.add(itemIndex)
		return true
	}
	
	internal fun hideAdForIndex(itemIndex: Int)
	{
		if (!isValid) return
		
		val itemWithAd = itemsWithAds.firstOrNull { it.first == itemIndex }
		//if this is null it means we rolled for this item but it didn't have an ad
		itemWithAd?.let { (_, adId) ->
			R89LogUtil.d(TAG, "Hiding Ad in item with id $itemIndex")
			R89AdFactoryCommon.hide(adId)
		}
	}
	
	private fun rollForAd(idToRoll: Int): Boolean
	{
		val scrolledItemsUpToThisPosition = getScrolledItemsSinceLastResetForId(idToRoll)
		
		return if (minItems == maxItems)
		{
			(scrolledItemsUpToThisPosition % minItems == 0).also {
				logProbability = if (it)
				{
					100.0
				} else
				{
					0.0
				}
			}
		} else
		{
			val calculatedProbability: Double = (scrolledItemsUpToThisPosition - minItems) * (100.00 / (maxItems - minItems))
			val variableProbability = variableProbability.random()
			val finalProbability: Double = calculatedProbability + variableProbability
			
			val clampedProbability = finalProbability.coerceIn(0.0, 100.0)
			
			logProbability = clampedProbability
			
			Random.nextDouble(100.00) < clampedProbability
		}
	}
	
	private fun getScrolledItemsSinceLastResetForId(itemId: Int): Int
	{
		return itemId - lastResetNewStartID + 1
	}
	
	private fun resetScrolledItemsCount(newStartItemId: Int)
	{
		lastResetNewStartID = newStartItemId
	}
	
	private fun createAdItemLifecycle(itemIdInData: Int, childAdLifecycle: BannerEventListenerInternal?): BannerEventListenerInternal
	{
		return object : BannerEventListenerInternal
		{
			override fun onLayoutChange(width: Int, height: Int)
			{
				childAdLifecycle?.onLayoutChange(width, height)
			}
			
			override fun onLoaded()
			{
				generalEventListener?.onAdItemLoaded(itemIdInData)
				childAdLifecycle?.onLoaded()
			}
			
			override fun onFailedToLoad(error: R89LoadError)
			{
				generalEventListener?.onAdItemFailedToLoad(itemIdInData, error)
				childAdLifecycle?.onFailedToLoad(error)
			}
			
			override fun onImpression()
			{
				generalEventListener?.onAdItemImpression(itemIdInData)
				childAdLifecycle?.onImpression()
			}
			
			override fun onClick()
			{
				generalEventListener?.onAdItemClick(itemIdInData)
				childAdLifecycle?.onClick()
			}
			
			override fun onOpen()
			{
				generalEventListener?.onAdItemOpen(itemIdInData)
				childAdLifecycle?.onOpen()
			}
			
			override fun onClose()
			{
				generalEventListener?.onAdItemClose(itemIdInData)
				childAdLifecycle?.onClose()
			}
			
		}
	}
	
	private fun createAdView(adWrapper: Any, lifecycle: BannerEventListenerInternal): Int
	{
		//Should be impossible that this is empty should have crashed before
		val (format, unitId) = availableUnitIds.dequeue() ?: return -1
		
		return osAdObject.createAdView(TAG, adWrapper, lifecycle, format, unitId)
	}
	
	override fun show(newWrapper: Any?)
	{
		if (isValid) return
		itemsWithAds.forEach { (_, adId) ->
			R89AdFactoryCommon.show(adId)
		}
	}
	
	override fun hide()
	{
		itemsWithAds.forEach { (_, adId) ->
			R89AdFactoryCommon.hide(adId)
		}
	}
	
	override fun invalidate()
	{
		hide()
		isValid = false
		
		itemsWithAds.forEach { (_, adId) ->
			R89AdFactoryCommon.invalidate(adId)
		}
	}
	
	override fun destroy()
	{
		itemsWithAds.forEach { (_, adId) ->
			R89AdFactoryCommon.destroyAd(adId)
		}
	}
	
	/**
	 * @suppress
	 */
	companion object
		: FormatConfigValidator
	{
		private const val TAG = "Infinite"
		override fun isInValidConfig(adUnitConfigData: AdUnitConfigScheme, serializationLibrary: SerializationLibrary): Boolean
		{
			adUnitConfigData.getData<Int>("minItems").getOrElse {
				R89LogUtil.e(TAG, it)
				return@isInValidConfig true
			}
			
			adUnitConfigData.getData<Int>("maxItems").getOrElse {
				R89LogUtil.e(TAG, it)
				return@isInValidConfig true
			}
			
			adUnitConfigData.getData<Int>("variableProbabilityMin").getOrElse {
				R89LogUtil.e(TAG, it)
				return@isInValidConfig true
			}
			
			adUnitConfigData.getData<Int>("variableProbabilityMax").getOrElse {
				R89LogUtil.e(TAG, it)
				return@isInValidConfig true
			}
			
			adUnitConfigData.getData<List<String>>("configsOfAdsToUse").getOrElse {
				R89LogUtil.e(TAG, it)
				return@isInValidConfig true
			}
			
			return false
		}
		
		override fun getErrorMessage(adUnitConfigData: AdUnitConfigScheme, serializationLibrary: SerializationLibrary): String
		{
			return "Infinite Scroll is invalid because: " +
					"minItems: ${adUnitConfigData.dataMapValue?.get("minItems")} is invalid?: ${adUnitConfigData.getData<Int>("minItems").isFailure} " +
					"maxItems: ${adUnitConfigData.dataMapValue?.get("maxItems")} is invalid?: ${adUnitConfigData.getData<Int>("maxItems").isFailure} " +
					"variableProbabilityMin: ${adUnitConfigData.dataMapValue?.get("variableProbabilityMin")} is invalid?: ${adUnitConfigData.getData<Int>("variableProbabilityMin").isFailure} " +
					"variableProbabilityMax: ${adUnitConfigData.dataMapValue?.get("variableProbabilityMax")} is invalid?: ${adUnitConfigData.getData<Int>("variableProbabilityMax").isFailure} " +
					"configsOfAdsToUse data: ${adUnitConfigData.dataMapValue?.get("configsOfAdsToUse")} is invalid?: ${adUnitConfigData.getData<List<String>>("configsOfAdsToUse").isFailure}"
		}
	}
}