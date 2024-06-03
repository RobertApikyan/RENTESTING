package com.refinery89.sharedcore.os_layer.formats_impl.infinite_scroll

import android.view.ViewGroup
import com.refinery89.sharedcore.R89AdFactoryCommon
import com.refinery89.sharedcore.RefineryAdFactoryInternal
import com.refinery89.sharedcore.domain_layer.adFormats.Formats
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.infinite_scroll.IR89InfiniteScrollOS
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil

internal class R89InfiniteScrollAndroidImpl
	: IR89InfiniteScrollOS
{
	override fun show(tag: String, adId: Int, itemWrapper: Any)
	{
		if (itemWrapper !is ViewGroup)
		{
			R89LogUtil.e(tag, "ItemWrapper is not a ViewGroup", false)
			return
		}
		
		R89AdFactoryCommon.show(adId, itemWrapper)
	}
	
	override fun createAdView(tag: String, adWrapper: Any, lifecycle: BannerEventListenerInternal, format: Formats, unitId: String): Int
	{
		if (adWrapper !is ViewGroup)
		{
			R89LogUtil.e(tag, "AdWrapper is not a ViewGroup", false)
			return -1
		}
		return when (format)
		{
			Formats.BANNER                 -> RefineryAdFactoryInternal.createBanner(
				unitId,
				adWrapper,
				lifecycle
			)
			
			Formats.VIDEO_OUTSTREAM_BANNER -> RefineryAdFactoryInternal.createVideoOutstreamBanner(
				unitId,
				adWrapper,
				lifecycle
			)
			
			else                           -> -1
		}
	}
}