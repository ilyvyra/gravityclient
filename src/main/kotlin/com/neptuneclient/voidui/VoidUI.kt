package com.neptuneclient.voidui

import com.neptuneclient.voidui.framework.Widget
import com.neptuneclient.voidui.rendering.Renderer

class VoidUI(val renderer: Renderer) {
    var root: Widget? = null

    fun draw(mouseX: Double, mouseY: Double) {
        root?.let {
            it.layout(renderer.getFontWidth("W") * 100, renderer.getFontHeight() * 100) // Rough estimation
            it.draw(renderer, mouseX, mouseY)
        }
    }

    fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {
        root?.mouseClicked(mouseX, mouseY, mouseButton)
    }
}
