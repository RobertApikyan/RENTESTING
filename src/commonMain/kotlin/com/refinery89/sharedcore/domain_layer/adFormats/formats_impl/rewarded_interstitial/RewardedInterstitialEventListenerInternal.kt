package com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.rewarded_interstitial

import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.interstitial.InterstitialEventListenerInternal

internal interface RewardedInterstitialEventListenerInternal : InterstitialEventListenerInternal
{
	/**
	 * Called when the user has earned a reward it's usually called  before [onAdDismissedFullScreenContent] but it can change
	 * @param rewardAmount amount of the reward
	 * @param rewardType the type of reward you should give
	 * @return Unit
	 */
	fun onRewardEarned(rewardAmount: Int, rewardType: String)
}