package com.refinery89.sharedcore.data_layer.schemes_models.pub_request.response.single_tag

import kotlinx.serialization.Serializable

/**
 * API Scheme for getting the Response.
 * @property wrapperTag the tag to search for in the view hierarchy
 * @property getAllWithTag should we get all of them with the same tag or only the first one?
 * @property relativePos where to place our wrapper relative to the pubs wrapper
 */

@Serializable
internal data class WrapperScheme(
	val wrapperTag: String?,
	val getAllWithTag: Boolean?,
	val relativePos: WrapperRelativePosition?,
)
{
	/**
	 * Where to place our wrapper relative to the pubs wrapper
	 */
	internal enum class WrapperRelativePosition
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
	}
}
