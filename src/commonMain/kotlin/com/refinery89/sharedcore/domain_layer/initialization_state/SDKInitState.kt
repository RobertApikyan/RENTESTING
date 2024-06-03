package com.refinery89.sharedcore.domain_layer.initialization_state

internal enum class SDKInitState
{
	NOT_INITIALIZED,
	STARTED_INITIALIZATION,
	STARTED_DATA_FETCH,
	DATA_FETCH_SUCCESS, // if this happens ALL of the rest should be called
	DATA_FETCH_FAILED, // if this happens NONE of the rest should be called
	STARTED_CMP,
	CMP_FINISHED,
	INITIALIZATION_FINISHED
}