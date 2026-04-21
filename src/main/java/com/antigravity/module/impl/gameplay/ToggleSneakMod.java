package com.antigravity.module.impl.gameplay;

import com.antigravity.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ToggleSneakMod extends Module {

    public ToggleSneakMod() {
        super("Toggle Sneak", "Toggles your sneaking state.", Category.UTILITY, Keyboard.KEY_NONE);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (!isToggled()) return;
        com.antigravity.utils.ReflectionUtils.setPressed(mc.gameSettings.keyBindSneak, true);
    }

    @Override
    public void onDisable() {
        com.antigravity.utils.ReflectionUtils.setPressed(mc.gameSettings.keyBindSneak, Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
    }
}
