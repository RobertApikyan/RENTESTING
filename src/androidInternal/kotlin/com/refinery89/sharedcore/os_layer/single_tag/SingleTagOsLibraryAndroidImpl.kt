package com.refinery89.sharedcore.os_layer.single_tag

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.refinery89.sharedcore.R89AdFactoryCommon
import com.refinery89.sharedcore.RefineryAdFactoryInternal
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.internalToolBox.extensions.getTypeSimpleName
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.domain_layer.models.single_tag.AdPlaceData
import com.refinery89.sharedcore.domain_layer.singleTag.ISingleTagOS
import com.refinery89.sharedcore.domain_layer.singleTag.SingleTagScreenEvents
import com.refinery89.sharedcore.os_layer.AndroidViewFactory

/**
 *
 * @property activityLifecycle ActivityLifecycleCallbacks this are the callbacks used to know app state and place the ads
 * @property app Application
 * @property activityLifecycle ActivityLifecycleCallbacks?
 * @property fragmentLifecycle FragmentLifecycleCallbacks?
 * @property screenAvailable MutableMap<String, Activity>
 * @constructor
 */
internal class SingleTagOsLibraryAndroidImpl(private val app: Application) : ISingleTagOS
{
	private var activityLifecycle: Application.ActivityLifecycleCallbacks? = null
	private var fragmentLifecycle: FragmentManager.FragmentLifecycleCallbacks? = null
	
	private val screenAvailable: MutableMap<String, Activity> = mutableMapOf()
	
	/**
	 * @suppress
	 */
	override fun registerActivityLifecycle(lifeCycle: SingleTagScreenEvents)
	{
		app.registerActivityLifecycleCallbacks(getActivityLifecycle(lifeCycle))
	}
	
	/**
	 * This initializes the [activityLifecycle] variable at init state of the class
	 * Creates all the events we want to track from the lifecycle of any activity
	 * @return ActivityLifecycleCallbacks
	 */
	private fun getActivityLifecycle(lifeCycle: SingleTagScreenEvents): Application.ActivityLifecycleCallbacks
	{
		return if (activityLifecycle != null)
		{
			activityLifecycle!!
		} else
		{
			activityLifecycle = object : Application.ActivityLifecycleCallbacks
			{
				override fun onActivityCreated(createdActivity: Activity, p1: Bundle?)
				{
					screenAvailable[createdActivity.getTypeSimpleName()] = createdActivity
					lifeCycle.onScreenCreated(createdActivity.getTypeSimpleName(), false)
					
					registerFragmentCallbacksToNewActivity(createdActivity, lifeCycle)
				}
				
				override fun onActivityStarted(startedActivity: Activity)
				{
				}
				
				override fun onActivityResumed(resumedActivity: Activity)
				{
					lifeCycle.onScreenResumed(resumedActivity.getTypeSimpleName(), false)
				}
				
				override fun onActivityPaused(pausedActivity: Activity)
				{
				}
				
				override fun onActivityStopped(p0: Activity)
				{
				}
				
				override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle)
				{
				}
				
				override fun onActivityDestroyed(p0: Activity)
				{
					screenAvailable.remove(p0.getTypeSimpleName())
				}
			}
			
			activityLifecycle!!
		}
	}
	
	/**
	 * @suppress
	 */
	private fun registerFragmentCallbacksToNewActivity(activity: Activity, lifeCycle: SingleTagScreenEvents)
	{
		if (activity is FragmentActivity)
		{
			activity.supportFragmentManager.registerFragmentLifecycleCallbacks(getFragmentLifecycle(lifeCycle), true)
		}
	}
	
	/**
	 * @suppress
	 */
	private fun getFragmentLifecycle(lifeCycle: SingleTagScreenEvents): FragmentManager.FragmentLifecycleCallbacks
	{
		return if (fragmentLifecycle != null)
		{
			fragmentLifecycle!!
		} else
		{
			fragmentLifecycle = object : FragmentManager.FragmentLifecycleCallbacks()
			{
				override fun onFragmentCreated(fragmentManager: FragmentManager, fragmentCreated: Fragment, bundle: Bundle?)
				{
					screenAvailable[fragmentCreated.getTypeSimpleName()] = fragmentCreated.requireActivity()
					lifeCycle.onScreenCreated(fragmentCreated.getTypeSimpleName(), true)
					super.onFragmentCreated(fragmentManager, fragmentCreated, bundle)
				}
				
				override fun onFragmentResumed(fragmentManager: FragmentManager, fragmentResumed: Fragment)
				{
					lifeCycle.onScreenResumed(fragmentResumed.getTypeSimpleName(), true)
					super.onFragmentResumed(fragmentManager, fragmentResumed)
				}
				
				override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment)
				{
					screenAvailable.remove(f.getTypeSimpleName())
					lifeCycle.onScreenDestroyed(f.getTypeSimpleName(), true)
					super.onFragmentDestroyed(fm, f)
				}
			}
			
			fragmentLifecycle!!
		}
	}
	
	/**
	 * @suppress
	 */
	override fun resolveAdPlaceInfiniteScroll(adPlace: AdPlaceData, screenName: String)
	{
		val activity = screenAvailable[screenName] ?: return
		
		val scrollView = AndroidViewFactory.getScrollView(adPlace.wrapperData!!, activity)
		val itemWrapperTag: String = adPlace.getData("infiniteScrollItemWrapper")
		
		RefineryAdFactoryInternal.createInfiniteScroll(adPlace.r89configId, scrollView, itemWrapperTag)
	}
	
	/**
	 * @suppress
	 */
	override fun resolveAdPlaceWrapper(adPlace: AdPlaceData, screenName: String)
	{
		val activity = screenAvailable[screenName] ?: return
		try
		{
			//TODO the !! operator is not needed if contracts are uses in the functions off the if
			if (adPlace.wrapperData!!.getAllWithTag)
			{
				val r89Wrappers: List<ViewGroup> = AndroidViewFactory.createR89WrapperList(adPlace.wrapperData, activity)
				
				for (r89Wrapper in r89Wrappers)
				{
					createWrapperAd(adPlace.adFormat, adPlace.r89configId, r89Wrapper)
				}
			} else
			{
				val r89Wrapper: ViewGroup = AndroidViewFactory.createR89Wrapper(adPlace.wrapperData, activity)
				createWrapperAd(adPlace.adFormat, adPlace.r89configId, r89Wrapper)
			}
			
		} catch (e: Exception)
		{
			R89LogUtil.e(TAG, e.message ?: "Failed to resolve wrapper dependant Ad places exception: $e", false)
		}
		
	}
	
	/**
	 * @suppress
	 */
	private fun createWrapperAd(format: Formats, configId: String, r89Wrapper: ViewGroup)
	{
		when (format)
		{
			Formats.BANNER                       -> RefineryAdFactoryInternal.createBanner(
				configId,
				r89Wrapper
			)
			
			Formats.INTERSTITIAL                 -> throw UnsupportedOperationException("Interstitials are not supported in wrappers")
			Formats.VIDEO_OUTSTREAM_BANNER       -> RefineryAdFactoryInternal.createVideoOutstreamBanner(
				configId,
				r89Wrapper
			)
			
			Formats.VIDEO_OUTSTREAM_INTERSTITIAL -> throw UnsupportedOperationException("Interstitials are not supported in wrappers")
			Formats.INFINITE_SCROLL              -> throw UnsupportedOperationException("Infinite scroll is not supported in wrappers")
			Formats.REWARDED_INTERSTITIAL        -> throw UnsupportedOperationException("Rewarded Interstitials are not supported in wrappers")
		}
	}
	
	/**
	 * @suppress
	 */
	override fun resolveAdPlaceEvents(
		adPlace: AdPlaceData,
		screenName: String,
		interstitialList: MutableMap<String, Int>,
	)
	{
		val activity = screenAvailable[screenName] ?: return
		val configId = adPlace.r89configId
		val eventsToTrack = adPlace.eventsToTrack!!
		
		for (event in eventsToTrack)
		{
			if (event.isTransitionEvent())
			{
				interstitialList[configId] = RefineryAdFactoryInternal.createInterstitial(configId, activity, afterInterstitial = {
					//TODO this is empty because we show interstitials after activities or fragments are already loaded,
				})
			} else if (event.isButtonEvent())
			{
				//TODO: Exception if button with this tag does not exist
				val buttonTag = event.buttonTag!!
				
				val view = AndroidViewFactory.getViewWithTag(buttonTag, activity)
				
				val afterInterstitial = AndroidViewFactory.getCurrentOnClickListenerFromView(view)
				
				val interstitialID = RefineryAdFactoryInternal.createInterstitial(
					configId,
					activity,
					afterInterstitial
				)
				
				if (interstitialID != -1)
				{
					AndroidViewFactory.setViewOnClickListener(view, event = {
						R89AdFactoryCommon.show(interstitialID)
					})
				}
			}
		}
	}
	
	/**
	 * @suppress
	 */
	companion object
	{
		private const val TAG = "SingleTagOSLib"
	}
}