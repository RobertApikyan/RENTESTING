package com.refinery89.sharedcore.os_layer.formats_impl.banner

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.BannerEventListenerInternal
import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.banner.IR89BannerOS
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89AdSize
import com.refinery89.sharedcore.domain_layer.adFormats.formats_utils.R89LoadError
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import com.refinery89.sharedcore.os_layer.extensions.asString
import org.prebid.mobile.BannerAdUnit
import org.prebid.mobile.BannerBaseAdUnit
import org.prebid.mobile.ResultCode
import org.prebid.mobile.Signals
import org.prebid.mobile.addendum.AdViewUtils
import org.prebid.mobile.addendum.PbFindSizeError

/**
 *
 * @property context Context the context that is needed in android to create the objects
 * @property prebidBannerAdUnit prebid unit object to run auctions
 * @property gamBannerAdUnitView google banner view that is going to be used to run the google auction and also render the winner creative/bidder
 * @property gamRequest AdManagerAdRequest the way to configure the google auction
 * @property sizeList List<AdSize>
 * @property internalEventListener BannerEventListener this is the event that MUST BE provided by the domain ad object to receive the events,
 *    this is used then by domain to expose the events to the client in case they indicated it so in the domain object
 */
internal class R89BannerAndroidImpl(private val context: Context) : IR89BannerOS
{
	private var prebidBannerAdUnit: BannerAdUnit? = null
	private val gamBannerAdUnitView: AdManagerAdView = AdManagerAdView(context)
	private val gamRequest: AdManagerAdRequest = AdManagerAdRequest.Builder().build()
	
	private lateinit var sizeList: List<AdSize>
	private lateinit var internalEventListener: BannerEventListenerInternal
	
	private val layoutChangeListener: View.OnLayoutChangeListener =
		View.OnLayoutChangeListener { v, _, _, _, _, _, _, _, _ ->
			internalEventListener.onLayoutChange(v.measuredWidth, v.measuredHeight)
		}
	
	override fun configureAdObject(
		internalEventListener: BannerEventListenerInternal,
		gamUnitId: String,
		r89SizeList: List<R89AdSize>,
		pbConfigId: String,
		autoRefreshSeconds: Int,
	)
	{
		this.internalEventListener = internalEventListener
		sizeList = r89SizeList.map { AdSize(it.width, it.height) }
		
		//configure Google AdView
		gamBannerAdUnitView.adUnitId = gamUnitId
		gamBannerAdUnitView.setAdSizes(*sizeList.toTypedArray())
		gamBannerAdUnitView.adListener = createListener()
		gamBannerAdUnitView.id = View.generateViewId()
		
		//configure Prebid Ad Object
		prebidBannerAdUnit = BannerAdUnit(pbConfigId, sizeList[0].width, sizeList[0].height)
		prebidBannerAdUnit!!.parameters = configurePrebidAdObject(autoRefreshSeconds)
	}
	
	private fun configurePrebidAdObject(autoRefreshSeconds: Int): BannerBaseAdUnit.Parameters
	{
		sizeList.drop(1).forEach {
			prebidBannerAdUnit!!.addAdditionalSize(it.width, it.height)
		}
		prebidBannerAdUnit!!.setAutoRefreshInterval(autoRefreshSeconds)
		
		return BannerBaseAdUnit.Parameters().apply {
			api = listOf(Signals.Api.MRAID_3, Signals.Api.OMID_1)
		}
	}
	
	/**
	 * This uses google lifecycle events object to create an expose our own events.
	 * we don't expose the google object directly just in case we want to change to use other platform that is not google
	 * and also this way it looks more clean and it's easier to replace any other platform.
	 *
	 * Also if expose this setter we would give the user the chance to set it to null which we cant do because
	 * we use the google lifecycle to do other stuff
	 * @return AdListener
	 */
	private fun createListener(): AdListener
	{
		return object : AdListener()
		{
			override fun onAdLoaded()
			{
				super.onAdLoaded()
				
				
				// 6. Resize ad view if needed
				AdViewUtils.findPrebidCreativeSize(
					gamBannerAdUnitView,
					object : AdViewUtils.PbFindSizeListener
					{
						override fun success(width: Int, height: Int)
						{
							gamBannerAdUnitView.setAdSizes(AdSize(width, height))
							internalEventListener.onLayoutChange(width, height)

//						gamBannerAdUnitView.removeOnLayoutChangeListener(layoutChangeListener)
//						gamBannerAdUnitView.addOnLayoutChangeListener(layoutChangeListener)
						}
						
						override fun failure(error: PbFindSizeError)
						{
							val largestWidth = sizeList.maxBy { it.width }.width
							val largestHeight = sizeList.maxBy { it.height }.height
							internalEventListener.onLayoutChange(largestWidth, largestHeight)
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
	
	override fun getView(): Any
	{
		return gamBannerAdUnitView
	}
	
	@SuppressLint("MissingPermission")
	override fun loadAd(tag: String)
	{
		prebidBannerAdUnit!!.fetchDemand(gamRequest) { result: ResultCode ->

//			request.customTargeting.putString("hb_bidder", "appnexus")
			val message = "${result.asString()} with ${gamRequest.asString(context)}"
			R89LogUtil.logFetchDemandResult(tag, result.toString(), message)
			
			gamBannerAdUnitView.loadAd(gamRequest)
		}
	}
	
	override fun getViewId(): String
	{
		return gamBannerAdUnitView.id.toString()
	}
	
	override fun stopAutoRefresh()
	{
		prebidBannerAdUnit?.stopAutoRefresh()
	}
	
	override fun invalidate()
	{
		gamBannerAdUnitView.invalidate()
		prebidBannerAdUnit?.stopAutoRefresh()
	}
	
	override fun destroy()
	{
		gamBannerAdUnitView.destroy()
		prebidBannerAdUnit?.stopAutoRefresh()
		prebidBannerAdUnit = null
	}
}