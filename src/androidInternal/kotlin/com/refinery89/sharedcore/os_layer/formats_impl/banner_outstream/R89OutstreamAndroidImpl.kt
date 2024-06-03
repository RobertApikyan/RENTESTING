package com.refinery89.sharedcore.os_layer.formats_impl.banner_outstream

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner_outstream.IR89OutstreamOS
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.os_layer.extensions.asString
import org.prebid.mobile.ResultCode
import org.prebid.mobile.Signals
import org.prebid.mobile.VideoAdUnit
import org.prebid.mobile.VideoBaseAdUnit
import org.prebid.mobile.addendum.AdViewUtils
import org.prebid.mobile.addendum.PbFindSizeError

internal class R89OutstreamAndroidImpl(private val context: Context) : IR89OutstreamOS
{
	private var prebidOutstreamAdUnit: VideoAdUnit? = null
	
	private val gamOutstreamAdUnitView: AdManagerAdView = AdManagerAdView(context)
	private val gamRequest: AdManagerAdRequest = AdManagerAdRequest.Builder().build()
	
	internal lateinit var internalEventListener: BannerEventListenerInternal
	
	private var width: Int = 0
	private var height: Int = 0
	
	private val layoutChangeListener: View.OnLayoutChangeListener = View.OnLayoutChangeListener { v, _, _, _, _, _, _, _, _ ->
		internalEventListener.onLayoutChange(v.measuredWidth, v.measuredHeight)
	}
	
	override fun configureAdObject(
		internalEventListener: BannerEventListenerInternal,
		gamUnitId: String,
		width: Int,
		height: Int,
		pbConfigId: String,
	)
	{
		this.internalEventListener = internalEventListener
		
		this.width = width
		this.height = height
		// Configure Google AdView
		gamOutstreamAdUnitView.adUnitId = gamUnitId
		gamOutstreamAdUnitView.setAdSizes(AdSize(width, height))
		gamOutstreamAdUnitView.adListener = createListener()
		gamOutstreamAdUnitView.id = View.generateViewId()
		
		//Configure Prebid Ad Object
		prebidOutstreamAdUnit = VideoAdUnit(pbConfigId, width, height)
		prebidOutstreamAdUnit!!.parameters = configureVideoParameters()
	}
	
	private fun configureVideoParameters(): VideoBaseAdUnit.Parameters
	{
		return VideoBaseAdUnit.Parameters().apply {
			placement = Signals.Placement.InArticle
			api = listOf(
				Signals.Api.VPAID_1,
				Signals.Api.VPAID_2,
			)
			maxBitrate = 1500
			minBitrate = 300
			maxDuration = 167
			minDuration = 5
			mimes = listOf("video/x-flv", "video/mp4")
			playbackMethod = listOf(
				Signals.PlaybackMethod.AutoPlaySoundOn,
				Signals.PlaybackMethod.AutoPlaySoundOff
			)
			protocols = listOf(
				Signals.Protocols.VAST_2_0
			)
		}
	}
	
	private fun createListener(): AdListener
	{
		return object : AdListener()
		{
			override fun onAdLoaded()
			{
				super.onAdLoaded()
				
				// 6. Adjust ad view size
				// TODO Not working with the test ids it throws: The HTML doesn't contain a size object
				AdViewUtils.findPrebidCreativeSize(
					gamOutstreamAdUnitView,
					object : AdViewUtils.PbFindSizeListener
					{
						override fun success(width: Int, height: Int)
						{
							gamOutstreamAdUnitView.setAdSizes(AdSize(width, height))
							this@R89OutstreamAndroidImpl.width = width
							this@R89OutstreamAndroidImpl.height = height
							
							internalEventListener.onLayoutChange(width, height)
							
							//						gamBannerAdUnitView.removeOnLayoutChangeListener(layoutChangeListener)
							//						gamBannerAdUnitView.addOnLayoutChangeListener(layoutChangeListener)
						}
						
						override fun failure(error: PbFindSizeError)
						{
							internalEventListener.onLayoutChange(width, height)
						}
					})
				
				internalEventListener.onLoaded()
			}
			
			override fun onAdFailedToLoad(p0: LoadAdError)
			{
				super.onAdFailedToLoad(p0)
				val errorString = p0.toString()
				
				internalEventListener.onFailedToLoad(R89LoadError(errorString))
			}
			
			override fun onAdImpression()
			{
				super.onAdImpression()
				internalEventListener.onImpression()
			}
			
			override fun onAdClosed()
			{
				super.onAdClosed()
				internalEventListener.onClose()
			}
			
			override fun onAdClicked()
			{
				super.onAdClicked()
				internalEventListener.onClick()
			}
			
			override fun onAdOpened()
			{
				super.onAdOpened()
				internalEventListener.onOpen()
			}
		}
	}
	
	@SuppressLint("MissingPermission")
	override fun loadAd(tag: String)
	{
		
		// call the load to execute the auction, after auction call for google request
		prebidOutstreamAdUnit?.fetchDemand(gamRequest) { result: ResultCode ->
			val message = "${result.asString()} with ${gamRequest.asString(context)}"
			R89LogUtil.logFetchDemandResult(tag, result.toString(), message)
			
			gamOutstreamAdUnitView.loadAd(gamRequest)
		}
	}
	
	override fun getView(): Any
	{
		return gamOutstreamAdUnitView
	}
	
	override fun getViewId(): String
	{
		return gamOutstreamAdUnitView.id.toString()
	}
	
	override fun destroy()
	{
		gamOutstreamAdUnitView.destroy()
		prebidOutstreamAdUnit?.stopAutoRefresh()
		prebidOutstreamAdUnit = null
	}
	
	override fun invalidate()
	{
		prebidOutstreamAdUnit?.stopAutoRefresh()
		gamOutstreamAdUnitView.invalidate()
	}
	
}