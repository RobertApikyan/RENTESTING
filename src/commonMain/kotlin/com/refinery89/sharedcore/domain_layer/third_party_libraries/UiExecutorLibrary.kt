package com.refinery89.sharedcore.domain_layer.third_party_libraries

internal interface UiExecutorLibrary
{
	fun executeInMainThread(block: () -> Unit)
}