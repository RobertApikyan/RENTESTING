package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial

internal interface IR89RewardedInterstitialOS
{
	fun configureAdObject(tag: String, gamUnitId: String, pbConfigId: String, internalEventListener: RewardedInterstitialEventListenerInternal)
	fun show(onError: (error: String) -> Unit)
	fun destroy()
}