package com.neptuneclient.voidui.rendering

interface Renderer {
    fun rectangle(x: Double, y: Double, width: Double, height: Double, color: Int)
    fun roundedRectangle(x: Double, y: Double, width: Double, height: Double, radius: Double, color: Int)
    fun text(text: String, x: Double, y: Double, color: Int, shadow: Boolean = true)
    fun image(resource: String, x: Double, y: Double, width: Double, height: Double)
    fun getFontWidth(text: String): Double
    fun getFontHeight(): Double
}
