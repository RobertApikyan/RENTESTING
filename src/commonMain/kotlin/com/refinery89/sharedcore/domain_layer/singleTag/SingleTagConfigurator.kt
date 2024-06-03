package com.refinery89.sharedcore.domain_layer.singleTag

import com.refinery89.sharedcore.R89AdFactoryCommon
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.internalToolBox.dataStructures.queue.ArrayQueue
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.single_tag.AdPlaceData
import com.refinery89.sharedcore.domain_layer.models.single_tag.AdScreenData

/**
 *
 * @property screenAdDataRepository  the repository of all the screens that have ads with its corresponding ad Units and wrappers
 * @property hasPublisherData used inside th callbacks to know if we can place the ads or if we should cache the current state until we finish fetching the publisher Data
 * @property screenLifecycleEventRegistry tracking events for each screen to resolve them once we have publisher, purpose of this is to cache the
 * information object about the event, name of the screen, and if it is a fragment or not when an event is registered in its name for later access.
 * @property events the screens have events in the os these are used from the OS Layer to give us information about what's happening in the app
 */
internal class SingleTagConfigurator(
	private val singleTagOSLibrary: ISingleTagOS,
)
{
	private var hasPublisherData = false
	
	private val screenAdDataRepository = ScreenAdDataRepository()
	private val screenLifecycleEventRegistry: ArrayQueue<Triple<String, String, Boolean>> = ArrayQueue()
	
	private val screenInterstitials: MutableMap<String, Int> = mutableMapOf()
	private var lastScreenCreated: String? = null
	private var lastScreenResumed: String? = null
	
	private var lastSubScreenCreated: String? = null
	private var lastSubScreenResumed: String? = null
	
	
	val events = object : SingleTagScreenEvents
	{
		override fun onScreenCreated(createdScreenName: String, isFragment: Boolean)
		{
			logInfoScreenWithText("created", createdScreenName, isFragment)
			registerScreenEventIfPublisherDataIsMissing("created", createdScreenName, isFragment)
			
			resolveScreenOnCreateEvent(createdScreenName, isFragment)
		}
		
		override fun onScreenResumed(resumedScreenName: String, isFragment: Boolean)
		{
			logInfoScreenWithText("resumed", resumedScreenName, isFragment)
			registerScreenEventIfPublisherDataIsMissing("resumed", resumedScreenName, isFragment)
			
			resolveScreenOnResumedEvent(resumedScreenName, isFragment)
		}
		
		override fun onScreenDestroyed(screenName: String, isFragment: Boolean)
		{
			resolveScreenOnDestroyEvent(screenName, isFragment)
		}
	}
	
	/**
	 * Sets the initial state of the single tag
	 * and registers the callbacks to the app
	 * @return Unit
	 */
	fun initializeSingleTag()
	{
		hasPublisherData = false
		screenAdDataRepository.hasFilledTheContextData = false
		singleTagOSLibrary.registerActivityLifecycle(events)
	}
	
	
	/**
	 * This is Used when the data is fetched from the DB.
	 * It Checks that the single tag data is present and then caches it in this class
	 * If single tag data is null or any Context.adPlaceList is empty then it doesn't fill anything
	 * @param adScreenData PublisherData
	 * @return Unit
	 */
	fun provideConfiguration(adScreenData: List<AdScreenData>)
	{
		hasPublisherData = true
		
		screenAdDataRepository.fill(adScreenData)
		
		resolveRegisteredEvents()
	}
	
	/**
	 * Enqueued events are resolved after initialization with this function
	 * @return Unit
	 */
	private fun resolveRegisteredEvents()
	{
		screenLifecycleEventRegistry.forEach { (event, screenName, isFragment) ->
			
			R89LogUtil.i(TAG, "Resolving event $event for activity $screenName", false)
			when (event)
			{
				"created" -> resolveScreenOnCreateEvent(screenName, isFragment)
				"resumed" -> resolveScreenOnResumedEvent(screenName, isFragment)
				else      -> R89LogUtil.w(TAG, "Event $event not Processed", false)
			}
		}
	}
	
	/**
	 * Logs the screen name with the event
	 * @param text String
	 * @param screenName String
	 * @param isFragment Boolean
	 * @return Unit
	 */
	private fun logInfoScreenWithText(text: String, screenName: String, isFragment: Boolean)
	{
		val message = if (isFragment)
		{
			"Fragment: $text $screenName"
		} else
		{
			"Activity: $text $screenName"
		}
		R89LogUtil.i(TAG, message, false)
	}
	
	/**
	 * register an activity lifecycle event for later resolving it
	 * @param eventName String
	 */
	private fun registerScreenEventIfPublisherDataIsMissing(eventName: String, screenName: String, isFragment: Boolean)
	{
		if (hasPublisherData) return
		
		screenLifecycleEventRegistry.enqueue(Triple(eventName, screenName, isFragment))
		
		logInfoScreenWithText("saved $eventName event for", screenName, isFragment)
	}
	
	/**
	 * Resolves the any screen created event from the OS
	 * @param createdScreen String
	 * @param isFragment Boolean
	 * @return Unit
	 */
	private fun resolveScreenOnCreateEvent(createdScreen: String, isFragment: Boolean)
	{
		if (screenAdDataRepository.screenIsInsideData(createdScreen, isFragment))
		{
			val lastScreenCreatedTemp = if (isFragment)
			{
				lastSubScreenCreated
			} else
			{
				lastScreenCreated
			}
			// if this is not the first activity ever Show ads that were suppose to appear in this transitions
			if (lastScreenCreatedTemp != null)
			{
				val (transitionExists, adPlacesToResolve) =
					screenAdDataRepository.transitionExists(lastScreenCreatedTemp, createdScreen, isFragment)
				
				if (transitionExists)
				{
					for (adPlace in adPlacesToResolve!!)
					{
						if (screenInterstitials[adPlace.r89configId] != -1)
						{
							R89AdFactoryCommon.show(screenInterstitials[adPlace.r89configId]!!)
						}
					}
					screenInterstitials.clear()
				}
//				RefineryAdFactory.cleanInvalidatedAdObjects()
			}
			
			if (isFragment)
			{
				lastSubScreenCreated = createdScreen
			} else
			{
				lastScreenCreated = createdScreen
			}
		}
	}
	
	/**
	 * Resolves the any screen resumed event from the OS
	 * @param resumedScreen String
	 * @param isFragment Boolean
	 * @return Unit
	 */
	private fun resolveScreenOnResumedEvent(resumedScreen: String, isFragment: Boolean)
	{
		val lastScreenResumedTemp = if (isFragment)
		{
			lastSubScreenResumed
		} else
		{
			lastScreenResumed
		}
		
		//Activities can be paused and resumed, in this cases we don't want to load the ads again
		val activityIsNotTheSame = lastScreenResumedTemp != resumedScreen
		val screenIsInsideData = screenAdDataRepository.screenIsInsideData(resumedScreen, isFragment)
		
		if (screenIsInsideData && activityIsNotTheSame)
		{
			if (isFragment)
			{
				lastSubScreenResumed = resumedScreen
			} else
			{
				lastScreenResumed = resumedScreen
			}
			
			// Get the AdContextData for the resumedActivity Activity
			val adContextData = screenAdDataRepository.getScreenDataFor(resumedScreen, isFragment)
			
			adContextData?.let { notNullAdContextData ->
				
				for (adPlace in notNullAdContextData.adPlaceList)
				{
					loadAdPlace(adPlace, resumedScreen, screenInterstitials)
				}
				
			} ?: R89LogUtil.e(TAG, "The context with name: $resumedScreen was not found", false)
		}
	}
	
	/**
	 * Resolves the ad place when wanted
	 * @param adPlace AdPlaceData
	 * @param screenName String
	 * @param interstitialList MutableMap<String, Int>
	 * @return Unit
	 */
	private fun loadAdPlace(adPlace: AdPlaceData, screenName: String, interstitialList: MutableMap<String, Int>)
	{
		if (adPlace.adFormat == Formats.INFINITE_SCROLL)
		{
			singleTagOSLibrary.resolveAdPlaceInfiniteScroll(adPlace, screenName)
		} else if (adPlace.isWrapperDependant() && adPlace.hasWrapperData())
		{
			singleTagOSLibrary.resolveAdPlaceWrapper(adPlace, screenName)
		} else if (adPlace.hasEventsToTrack())
		{
			singleTagOSLibrary.resolveAdPlaceEvents(adPlace, screenName, interstitialList)
		}
	}
	
	/**
	 * Resolves the any screen destroyed event from the OS
	 * @param destroyedScreen String
	 * @param isFragment Boolean
	 * @return Unit
	 */
	private fun resolveScreenOnDestroyEvent(destroyedScreen: String, isFragment: Boolean)
	{
		val lastScreenResumedTemp = if (isFragment)
		{
			lastSubScreenResumed
		} else
		{
			lastScreenResumed
		}
		
		val screenResumedIsTheSameAsDestroyed = lastScreenResumedTemp != destroyedScreen
		
		if (screenResumedIsTheSameAsDestroyed)
		{
			if (isFragment)
			{
				lastSubScreenResumed = null
			} else
			{
				lastScreenResumed = null
			}
		}
	}
	
	/**
	 * @suppress
	 */
	companion object
	{
		private const val TAG = "SingleTagConfigurator"
	}
}
