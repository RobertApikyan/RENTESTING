package com.refinery89.sharedcore.domain_layer.config.consentConfig

internal sealed class CMPButtonClicks
{
	class Unknown : CMPButtonClicks()
	class AcceptAll : CMPButtonClicks()
	class RejectAll : CMPButtonClicks()
	class SaveCustomSettings : CMPButtonClicks()
	class Close : CMPButtonClicks()
	
	override fun toString(): String
	{
		return when (this)
		{
			is Unknown            -> "No Button Event Logic Needed"
			is AcceptAll          -> "AcceptAll"
			is RejectAll          -> "RejectAll"
			is SaveCustomSettings -> "SaveCustomSettings"
			is Close              -> "Close"
		}
	}
}