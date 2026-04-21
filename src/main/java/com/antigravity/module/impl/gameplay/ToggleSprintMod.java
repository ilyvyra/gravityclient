package com.antigravity.module.impl.gameplay;

import com.antigravity.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ToggleSprintMod extends Module {

    public ToggleSprintMod() {
        super("Toggle Sprint", "Automatically sprints when moving.", Category.UTILITY, Keyboard.KEY_NONE);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (!isToggled()) return;
        if (mc.thePlayer.moveForward > 0 && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally) {
            mc.thePlayer.setSprinting(true);
        }
    }
}
