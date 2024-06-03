package com.refinery89.sharedcore.domain_layer.models.single_tag

import kotlinx.serialization.Serializable

/**
 * Data model for Domain Uses.
 * @property wrapperTag the tag to search for in the view hierarchy
 * @property getAllWithTag should we get all of them with the same tag or only the first one?
 * @property relativePos where to place our wrapper relative to the pubs wrapper
 */
@Serializable
internal data class WrapperData(
	val wrapperTag: String,
	val getAllWithTag: Boolean,
	val relativePos: WrapperRelativePosition,
)
{
	/**
	 * Where to place our wrapper relative to the pubs wrapper
	 */
	enum class WrapperRelativePosition
	{
		/**
		 * place our wrapper before the pubs wrapper
		 */
		BEFORE,
		
		/**
		 * place our wrapper after the pubs wrapper
		 */
		AFTER,
		
		/**
		 * place our wrapper inside the pubs wrapper
		 */
		INSIDE;
		
		/**
		 * @suppress
		 */
		fun isInside(): Boolean
		{
			return this == INSIDE
		}
		
		/**
		 * @suppress
		 */
		fun isBefore(): Boolean
		{
			return this == BEFORE
		}
		
		/**
		 * @suppress
		 */
		fun isAfter(): Boolean
		{
			return this == AFTER
		}
	}
}
