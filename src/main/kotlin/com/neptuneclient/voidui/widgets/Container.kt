package com.neptuneclient.voidui.widgets

import com.neptuneclient.voidui.framework.Widget
import com.neptuneclient.voidui.rendering.Renderer

class Container(
    var padding: Double = 0.0,
    var radius: Double = 0.0,
    var color: Int = 0,
    var child: Widget? = null
) : Widget() {

    override fun draw(renderer: Renderer, mouseX: Double, mouseY: Double) {
        if (color != 0) {
            if (radius > 0.0) {
                renderer.roundedRectangle(x, y, width, height, radius, color)
            } else {
                renderer.rectangle(x, y, width, height, color)
            }
        }
        child?.let {
            it.x = x + padding
            it.y = y + padding
            it.draw(renderer, mouseX, mouseY)
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {
        child?.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun layout(availableWidth: Double, availableHeight: Double) {
        child?.let {
            it.layout(availableWidth - padding * 2, availableHeight - padding * 2)
            width = it.width + padding * 2
            height = it.height + padding * 2
        }
    }
}
