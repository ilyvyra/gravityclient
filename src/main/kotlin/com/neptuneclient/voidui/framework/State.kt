package com.neptuneclient.voidui.framework

import kotlin.reflect.KProperty

class State<T>(private var value: T) {
    private val listeners = mutableListOf<(T) -> Unit>()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: T) {
        if (value != newValue) {
            value = newValue
            listeners.forEach { it(value) }
        }
    }

    fun addListener(listener: (T) -> Unit) {
        listeners.add(listener)
    }
}
