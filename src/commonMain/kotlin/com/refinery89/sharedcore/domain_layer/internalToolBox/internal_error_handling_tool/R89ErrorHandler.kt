package com.refinery89.sharedcore.domain_layer.internalToolBox.internal_error_handling_tool

import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil

/**
 * All R89SDK errors will get handled by this class
 */
internal object R89ErrorHandler
{
	/**
	 * Default handle error in which we log the error message
	 * @param tag String
	 * @param error Throwable
	 * @return Unit
	 */
	fun handleError(tag: String, error: Throwable)
	{
		when (error)
		{
			is R89Error ->
			{
				error.logMessage(tag)
			}
			
			else        ->
			{
				R89LogUtil.e(tag, "Unknown Error: $error", false)
			}
		}
	}
}