package com.refinery89.sharedcore.domain_layer.ad_factory

import com.refinery89.sharedcore.domain_layer.adFormats.formats_impl.R89BaseAd
import com.refinery89.sharedcore.domain_layer.ad_factory.ad_request_system.AdRequestValidator

internal interface IR89BaseAdBuilder<T : R89BaseAd>
{
	fun build(adRequestValidationResult: AdRequestValidator.AdRequestValidationResult): T
	
	companion object
	{
		fun <T : R89BaseAd> createBuilder(block: (AdRequestValidator.AdRequestValidationResult) -> T): IR89BaseAdBuilder<T> = object : IR89BaseAdBuilder<T>
		{
			override fun build(adRequestValidationResult: AdRequestValidator.AdRequestValidationResult): T
			{
				return block(adRequestValidationResult)
			}
		}
	}
}