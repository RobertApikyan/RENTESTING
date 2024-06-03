package com.refinery89.sharedcore.data_layer.data_srouce.mock.single_tag

import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag.AdPlaceScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag.AdScreenScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag.ScreenEventScheme
import com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag.WrapperScheme
import com.refinery89.sharedcore.data_layer.schemes_models.toJsonObject
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.config.ConfigBuilder

/**
 * @suppress
 *
 */
internal class SingleTagConfiguratorMock
{
	companion object
	{
		fun getSingleTagConfiguratorMockData(): List<AdScreenScheme>
		{
			return getFakeActivitiesAdContextData() + getFakeFragmentsAdContextData()
		}
		
		private fun getFakeActivitiesAdContextData(): List<AdScreenScheme>
		{
			//This in the real world would be one for each adplace but for the sake of simplicity we will reuse this as long as makes sense
			
			val activityWrapperData =
				WrapperScheme("pub_wrapper_tag", false, WrapperScheme.WrapperRelativePosition.BEFORE)
			
			//Main activity
			val mainActivityBannerAdPlace = AdPlaceScheme(
				ConfigBuilder.BANNER_TEST_R89_CONFIG_ID,
				Formats.BANNER,
				activityWrapperData,
				null,
				null
			)
			
			val mainActivityInterstitialAdPlace = AdPlaceScheme(
				ConfigBuilder.INTERSTITIAL_TEST_R89_CONFIG_ID,
				Formats.INTERSTITIAL,
				null,
				eventsToTrack = listOf(ScreenEventScheme(to = "SecondActivity", null)),
				null
			)
			
			val mainActivityInterstitialButtonAdPlace = AdPlaceScheme(
				ConfigBuilder.INTERSTITIAL_TEST_R89_CONFIG_ID,
				Formats.INTERSTITIAL,
				null,
				eventsToTrack = listOf(ScreenEventScheme(null, buttonId = "button2")),
				null
			)
			
			val mainActivityContextData = AdScreenScheme(
				false, "MainActivity",
				adPlaceList = listOf(mainActivityBannerAdPlace, mainActivityInterstitialAdPlace, mainActivityInterstitialButtonAdPlace)
			)
			
			//Second activity ad places
			val secondActivityBannerAdPlace = AdPlaceScheme(
				ConfigBuilder.BANNER_TEST_R89_CONFIG_ID,
				Formats.BANNER,
				activityWrapperData,
				null,
				null
			)
			val secondActivityInterstitialAdPlace =
				AdPlaceScheme(
					ConfigBuilder.INTERSTITIAL_TEST_R89_CONFIG_ID,
					Formats.INTERSTITIAL,
					null,
					eventsToTrack = listOf(ScreenEventScheme(null, buttonId = "switchActivity")),
					null
				)
			
			val secondActivityContextData = AdScreenScheme(
				false, "SecondActivity",
				adPlaceList = listOf(secondActivityBannerAdPlace, secondActivityInterstitialAdPlace)
			)
			
			//Infinite Scroll activity ad places
			val infiniteWrapperData =
				WrapperScheme("single_tag_infinite_scroll_tag", false, WrapperScheme.WrapperRelativePosition.INSIDE)
			
			val infiniteScrollActivityInfiniteAdPlace = AdPlaceScheme(
				ConfigBuilder.INFINITE_SCROLL_TEST_R89_CONFIG_ID,
				Formats.INFINITE_SCROLL,
				infiniteWrapperData,
				null,
				dataMap = mapOf("infiniteScrollItemWrapper" to "single_tag_infinite_scroll_ad_wrapper_tag").toJsonObject()
			)
			val infiniteScrollActivity = AdScreenScheme(
				false, "InfiniteScrollActivity",
				adPlaceList = listOf(infiniteScrollActivityInfiniteAdPlace)
			)
			
			return listOf(mainActivityContextData, secondActivityContextData, infiniteScrollActivity)
		}
		
		
		private fun getFakeFragmentsAdContextData(): List<AdScreenScheme>
		{
			//This in the real world would be one for each adplace but for the sake of simplicity we will reuse this as long as makes sense
			val fragmentWrapperData = WrapperScheme("pub_fragment_wrapper_tag", false, WrapperScheme.WrapperRelativePosition.INSIDE)
			
			//Main Activity Fragment One
			val fragmentBannerAdPlace = AdPlaceScheme(
				ConfigBuilder.BANNER_TEST_R89_CONFIG_ID,
				Formats.BANNER,
				fragmentWrapperData,
				null,
				null
			)
			
			val fragmentOneInterstitialAdPlace = AdPlaceScheme(
				ConfigBuilder.INTERSTITIAL_TEST_R89_CONFIG_ID,
				Formats.INTERSTITIAL,
				null,
				eventsToTrack = listOf(ScreenEventScheme(null, buttonId = "button")),
				null
			)
			
			val oneFragmentContextData = AdScreenScheme(
				true,
				"MainActivityFragmentOne",
				adPlaceList = listOf(fragmentBannerAdPlace, fragmentOneInterstitialAdPlace)
			)
			
			//Main Activity Fragment two
			val fragmentTwoInterstitialAdPlace = AdPlaceScheme(
				ConfigBuilder.INTERSTITIAL_TEST_R89_CONFIG_ID,
				Formats.INTERSTITIAL,
				null,
				eventsToTrack = listOf(ScreenEventScheme(to = "MainActivityFragmentOne", null)),
				null
			)
			
			val secondFragmentContextData = AdScreenScheme(
				true,
				"MainActivityFragmentTwo",
				adPlaceList = listOf(fragmentBannerAdPlace, fragmentTwoInterstitialAdPlace)
			)
			
			return listOf(oneFragmentContextData, secondFragmentContextData)
		}
	}
}