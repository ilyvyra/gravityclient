package com.neptuneclient.voidui.widgets

import com.neptuneclient.voidui.framework.Widget
import com.neptuneclient.voidui.rendering.Renderer

class Stack(
    var children: MutableList<Widget> = mutableListOf()
) : Widget() {

    override fun draw(renderer: Renderer, mouseX: Double, mouseY: Double) {
        for (child in children) {
            child.x = this.x
            child.y = this.y
            child.width = this.width
            child.height = this.height
            child.draw(renderer, mouseX, mouseY)
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {
        // Iterate backwards so top-most widgets handle clicks first
        for (i in children.indices.reversed()) {
            children[i].mouseClicked(mouseX, mouseY, mouseButton)
        }
    }

    override fun layout(availableWidth: Double, availableHeight: Double) {
        this.width = availableWidth
        this.height = availableHeight
        for (child in children) {
            child.layout(availableWidth, availableHeight)
        }
    }
}
