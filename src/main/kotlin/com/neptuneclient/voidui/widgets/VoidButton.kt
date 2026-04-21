package com.neptuneclient.voidui.widgets

import com.neptuneclient.voidui.framework.Widget
import com.neptuneclient.voidui.rendering.Renderer
import java.awt.Color

class VoidButton(
    var text: String,
    var onClick: () -> Unit,
    var color: Int = Color(0, 150, 255).rgb
) : Widget() {

    override fun draw(renderer: Renderer, mouseX: Double, mouseY: Double) {
        val hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height
        val bg = if (hovered) Color(color).brighter().rgb else color
        
        renderer.roundedRectangle(x, y, width, height, 4.0, bg)
        renderer.text(text, x + (width - renderer.getFontWidth(text)) / 2, y + (height - renderer.getFontHeight()) / 2, -1)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            onClick()
        }
    }

    override fun layout(availableWidth: Double, availableHeight: Double) {
        // Fixed size for buttons or based on text
        width = 100.0
        height = 20.0
    }
}
