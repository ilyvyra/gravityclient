package com.neptuneclient.voidui.framework

import com.neptuneclient.voidui.rendering.Renderer

abstract class Widget {
    var x: Double = 0.0
    var y: Double = 0.0
    var width: Double = 0.0
    var height: Double = 0.0

    abstract fun draw(renderer: Renderer, mouseX: Double, mouseY: Double)
    abstract fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int)
    
    open fun layout(availableWidth: Double, availableHeight: Double) {
        // Default layout does nothing
    }
}
