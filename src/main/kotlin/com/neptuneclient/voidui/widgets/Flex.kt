package com.neptuneclient.voidui.widgets

import com.neptuneclient.voidui.framework.Widget
import com.neptuneclient.voidui.rendering.Renderer

class Column(
    var spacing: Double = 0.0,
    var children: MutableList<Widget> = mutableListOf()
) : Widget() {

    override fun draw(renderer: Renderer, mouseX: Double, mouseY: Double) {
        var currentY = y
        for (child in children) {
            child.x = x
            child.y = currentY
            child.draw(renderer, mouseX, mouseY)
            currentY += child.height + spacing
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {
        for (child in children) {
            child.mouseClicked(mouseX, mouseY, mouseButton)
        }
    }

    override fun layout(availableWidth: Double, availableHeight: Double) {
        var maxWidth = 0.0
        var totalHeight = 0.0
        for (child in children) {
            child.layout(availableWidth, availableHeight)
            maxWidth = maxOf(maxWidth, child.width)
            totalHeight += child.height + spacing
        }
        width = maxWidth
        height = totalHeight - spacing
    }
}

class Row(
    var spacing: Double = 0.0,
    var children: MutableList<Widget> = mutableListOf()
) : Widget() {

    override fun draw(renderer: Renderer, mouseX: Double, mouseY: Double) {
        var currentX = x
        for (child in children) {
            child.x = currentX
            child.y = y
            child.draw(renderer, mouseX, mouseY)
            currentX += child.width + spacing
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {
        for (child in children) {
            child.mouseClicked(mouseX, mouseY, mouseButton)
        }
    }

    override fun layout(availableWidth: Double, availableHeight: Double) {
        var totalWidth = 0.0
        var maxHeight = 0.0
        for (child in children) {
            child.layout(availableWidth, availableHeight)
            totalWidth += child.width + spacing
            maxHeight = maxOf(maxHeight, child.height)
        }
        width = totalWidth - spacing
        height = maxHeight
    }
}
