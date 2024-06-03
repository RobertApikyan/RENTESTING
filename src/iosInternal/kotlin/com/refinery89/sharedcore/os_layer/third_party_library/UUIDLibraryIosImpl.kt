package com.refinery89.sharedcore.os_layer.third_party_library

import com.refinery89.sharedcore.domain_layer.third_party_libraries.UUIDLibrary
import platform.Foundation.NSUUID

class UUIDLibraryIosImpl : UUIDLibrary
{
	override fun generate(): String
	{
		return NSUUID().UUIDString
	}
}