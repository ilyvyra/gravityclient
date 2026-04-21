package com.antigravity.module.impl.gameplay;

import com.antigravity.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class NoHurtCamMod extends Module {

    public NoHurtCamMod() {
        super("No Hurt Cam", "Removes the screen shake when taking damage.", Category.UTILITY, Keyboard.KEY_NONE);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (!isToggled()) return;
        mc.thePlayer.hurtTime = 0;
    }
}
