package com.antigravity.module.impl.gameplay;

import com.antigravity.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ZoomMod extends Module {

    private boolean wasZooming;
    private float oldFOV;

    public ZoomMod() {
        super("Zoom", "Zoom in like Optifine.", Category.UTILITY, Keyboard.KEY_C);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!isToggled()) return;

        boolean isKeyDown = Keyboard.isKeyDown(getKey());

        if (isKeyDown && !wasZooming) {
            oldFOV = mc.gameSettings.fovSetting;
            mc.gameSettings.fovSetting = 30.0F;
            mc.gameSettings.smoothCamera = true;
            wasZooming = true;
        } else if (!isKeyDown && wasZooming) {
            mc.gameSettings.fovSetting = oldFOV;
            mc.gameSettings.smoothCamera = false;
            wasZooming = false;
        }
    }

    @Override
    public void onDisable() {
        if (wasZooming) {
            mc.gameSettings.fovSetting = oldFOV;
            mc.gameSettings.smoothCamera = false;
            wasZooming = false;
        }
    }
}
