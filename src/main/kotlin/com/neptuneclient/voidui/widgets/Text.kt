package com.neptuneclient.voidui.widgets

import com.neptuneclient.voidui.framework.Widget
import com.neptuneclient.voidui.rendering.Renderer

class Text(
    var text: String,
    var color: Int = -1,
    var shadow: Boolean = true
) : Widget() {

    override fun draw(renderer: Renderer, mouseX: Double, mouseY: Double) {
        renderer.text(text, x, y, color, shadow)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {}

    override fun layout(availableWidth: Double, availableHeight: Double) {
        // Implementation for width/height based on renderer font metrics
        // This is tricky as we need the renderer instance. 
        // We'll approximate for now or handle it during draw.
    }
}
