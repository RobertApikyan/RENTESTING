package com.refinery89.sharedcore.os_layer.single_tag

import com.refinery89.sharedcore.domain_layer.models.single_tag.AdPlaceData
import com.refinery89.sharedcore.domain_layer.singleTag.ISingleTagOS
import com.refinery89.sharedcore.domain_layer.singleTag.SingleTagScreenEvents

internal class SingleTagOsLibraryIosImpl:ISingleTagOS
{
	override fun registerActivityLifecycle(lifeCycle: SingleTagScreenEvents)
	{

	}
	
	override fun resolveAdPlaceInfiniteScroll(adPlace: AdPlaceData, screenName: String)
	{

	}
	
	override fun resolveAdPlaceWrapper(adPlace: AdPlaceData, screenName: String)
	{

	}
	
	override fun resolveAdPlaceEvents(adPlace: AdPlaceData, screenName: String, interstitialList: MutableMap<String, Int>)
	{

	}
}