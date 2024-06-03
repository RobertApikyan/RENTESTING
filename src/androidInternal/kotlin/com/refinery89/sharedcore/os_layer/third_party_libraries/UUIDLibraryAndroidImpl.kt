package com.refinery89.sharedcore.os_layer.third_party_libraries

import com.refinery89.sharedcore.domain_layer.third_party_libraries.UUIDLibrary
import java.util.UUID

internal class UUIDLibraryAndroidImpl : UUIDLibrary
{
	override fun generate() = UUID.randomUUID().toString()
}