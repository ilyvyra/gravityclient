package com.antigravity.gui.voidui

import com.antigravity.Antigravity
import com.antigravity.module.Module
import com.neptuneclient.voidui.framework.Widget
import com.neptuneclient.voidui.rendering.Renderer
import org.lwjgl.input.Mouse
import java.awt.Color
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

class HudCanvasWidget : Widget() {
    private var selectedModule: Module? = null
    private var dragX = 0.0
    private var dragY = 0.0
    var gridVisible = true

    override fun draw(renderer: Renderer, mouseX: Double, mouseY: Double) {
        // Draw grid lines
        if (gridVisible) {
            drawGrid(renderer)
        }

        // Draw HUD modules
        val modules = Antigravity.INSTANCE.moduleManager.modules
        for (m in modules) {
            if (m.category == Module.Category.HUD && m.isToggled) {
                val x = m.hudX
                val y = m.hudY
                val w = m.hudWidth
                val h = m.hudHeight

                val hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h
                val isSelected = m == selectedModule

                // Fill color
                val fillColor = when {
                    isSelected -> Color(130, 100, 255, 60).rgb
                    hovered -> Color(0, 150, 255, 50).rgb
                    else -> Color(255, 255, 255, 20).rgb
                }
                renderer.rectangle(x, y, w, h, fillColor)

                // Selection outline
                if (isSelected) {
                    val glow = (Math.sin(System.currentTimeMillis() / 200.0) * 0.2 + 0.8).toFloat()
                    val outlineColor = Color(130, 100, 255, (255 * glow).toInt()).rgb
                    // Top, Bottom, Left, Right borders (using rectangle logic as a workaround)
                    renderer.rectangle(x - 1, y - 1, w + 2, 1.0, outlineColor)
                    renderer.rectangle(x - 1, y + h, w + 2, 1.0, outlineColor)
                    renderer.rectangle(x - 1, y, 1.0, h, outlineColor)
                    renderer.rectangle(x + w, y, 1.0, h, outlineColor)
                    
                    // Corner dots
                    drawCornerDot(renderer, x - 1.5, y - 1.5)
                    drawCornerDot(renderer, x + w - 1.5, y - 1.5)
                    drawCornerDot(renderer, x - 1.5, y + h - 1.5)
                    drawCornerDot(renderer, x + w - 1.5, y + h - 1.5)
                } else {
                    val color = if (hovered) Color(255, 255, 255, 100).rgb else Color(255, 255, 255, 30).rgb
                    renderer.rectangle(x, y, w, 0.5, color)
                    renderer.rectangle(x, y + h, w, 0.5, color)
                    renderer.rectangle(x, y, 0.5, h, color)
                    renderer.rectangle(x + w, y, 0.5, h, color)
                }

                // Render name text scaled
                renderer.text(m.name, x, y - 8.0, if (isSelected) Color(130, 100, 255).rgb else Color.WHITE.rgb)
            }
        }

        // Handle dragging natively in draw loop
        if (selectedModule != null) {
            if (!Mouse.isButtonDown(0)) {
                selectedModule = null // released
            } else {
                var newX = mouseX - dragX
                var newY = mouseY - dragY

                if (gridVisible) {
                    newX = round(newX / 10.0) * 10
                    newY = round(newY / 10.0) * 10
                }

                newX = max(0.0, min(this.width - selectedModule!!.hudWidth, newX))
                newY = max(0.0, min(this.height - selectedModule!!.hudHeight, newY))

                selectedModule!!.hudX = newX
                selectedModule!!.hudY = newY
            }
        }
    }

    private fun drawGrid(renderer: Renderer) {
        val gap = 20.0
        var i = 0.0
        val gridColor = Color(255, 255, 255, 15).rgb
        while (i < this.width) {
            renderer.rectangle(i, 0.0, 0.5, this.height, gridColor)
            i += gap
        }
        var j = 0.0
        while (j < this.height) {
            renderer.rectangle(0.0, j, this.width, 0.5, gridColor)
            j += gap
        }
        val crossColor = Color(130, 100, 255, 30).rgb
        renderer.rectangle(this.width / 2.0, 0.0, 0.5, this.height, crossColor)
        renderer.rectangle(0.0, this.height / 2.0, this.width, 0.5, crossColor)
    }

    private fun drawCornerDot(renderer: Renderer, x: Double, y: Double) {
        renderer.rectangle(x, y, 4.0, 4.0, Color(130, 100, 255, 220).rgb)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {
        if (mouseButton == 0) {
            val modules = Antigravity.INSTANCE.moduleManager.modules
            for (m in modules) {
                if (m.category == Module.Category.HUD && m.isToggled) {
                    val x = m.hudX
                    val y = m.hudY
                    val w = m.hudWidth
                    val h = m.hudHeight
                    if (mouseX in x..(x + w) && mouseY in y..(y + h)) {
                        selectedModule = m
                        dragX = mouseX - x
                        dragY = mouseY - y
                        return
                    }
                }
            }
            selectedModule = null
        }
    }

    override fun layout(availableWidth: Double, availableHeight: Double) {
        this.width = availableWidth
        this.height = availableHeight
    }
}
