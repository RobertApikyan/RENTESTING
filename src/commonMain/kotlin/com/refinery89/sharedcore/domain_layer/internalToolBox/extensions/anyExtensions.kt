package com.refinery89.sharedcore.domain_layer.internalToolBox.extensions

internal fun Any.getTypeName(): String = this::class.simpleName ?: ""
internal fun Any.getTypeSimpleName(): String = this::class.simpleName ?: ""
