package com.antigravity.gui.voidui

import com.antigravity.Antigravity
import com.antigravity.module.Module
import com.neptuneclient.voidui.framework.Widget
import com.neptuneclient.voidui.widgets.*
import java.awt.Color

class VoidHudEditorScreen : VoidScreen() {

    private val canvas = HudCanvasWidget()

    override fun build(): Widget {
        val hudModules = Antigravity.INSTANCE.moduleManager.getModulesByCategory(Module.Category.HUD)

        return Stack(
            children = mutableListOf(
                // 1. Fullscreen Dark Overlay + Draggable Canvas
                Container(
                    color = Color(0, 0, 0, 100).rgb,
                    child = canvas
                ),

                // 2. Bottom Center Panel (Logo + Open Mods button)
                Container(
                    child = Column(
                        children = mutableListOf(
                            Container(color = 0).apply { height = 500.0 }, // Spacer to push down
                            Row(
                                children = mutableListOf(
                                    Container(color = 0).apply { width = 400.0 }, // Spacer to push right (center approximation)
                                    Container(
                                        padding = 20.0,
                                        radius = 6.0,
                                        color = Color(18, 16, 24, 180).rgb,
                                        child = Column(
                                            spacing = 15.0,
                                            children = mutableListOf(
                                                Text("Antigravity", Color.WHITE.rgb),
                                                VoidButton(
                                                    text = "Open Mods",
                                                    onClick = {
                                                        mc.displayGuiScreen(VoidModMenuScreen())
                                                    },
                                                    color = Color(30, 30, 35, 180).rgb
                                                )
                                            )
                                        )
                                    ).apply {
                                        width = 160.0
                                        height = 120.0
                                    }
                                )
                            )
                        )
                    )
                ),

                // 3. Right Sidebar Visibility Panel
                Container(
                    padding = 10.0,
                    child = Row(
                        spacing = 0.0,
                        children = mutableListOf(
                            Container(color = 0).apply { width = 1000.0 }, // Spacer hack to push to right
                            Container(
                                padding = 10.0,
                                radius = 4.0,
                                color = Color(18, 16, 24, 160).rgb,
                                child = Column(
                                    spacing = 5.0,
                                    children = mutableListOf(
                                        Text("Visibility", Color(180, 170, 200).rgb),
                                        Column(
                                            spacing = 2.0,
                                            children = hudModules.map { m ->
                                                VoidButton(
                                                    text = "${if (m.isToggled) "◉" else "○"} ${m.name}",
                                                    onClick = { 
                                                        m.toggle() 
                                                        initGui() // rebuild UI
                                                    },
                                                    color = Color(0,0,0,0).rgb // transparent btn
                                                )
                                            }.toMutableList()
                                        )
                                    )
                                )
                            ).apply { width = 120.0 }
                        )
                    )
                ),

                // 4. Bottom Grid Toggle
                Container(
                    padding = 10.0,
                    child = Column(
                        spacing = 0.0,
                        children = mutableListOf(
                            Container(color = 0).apply { height = 1000.0 }, // Spacer hack
                            VoidButton(
                                text = "Grid",
                                onClick = { 
                                    canvas.gridVisible = !canvas.gridVisible 
                                    initGui()
                                },
                                color = if (canvas.gridVisible) Color(60, 55, 75, 200).rgb else Color(30, 28, 38, 180).rgb
                            )
                        )
                    )
                )
            )
        )
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    override fun onGuiClosed() {
        Antigravity.INSTANCE.configManager.save()
    }
}
