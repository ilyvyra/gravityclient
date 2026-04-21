package com.antigravity.module.impl.visual;

import com.antigravity.module.Module;
import org.lwjgl.input.Keyboard;

public class FullbrightMod extends Module {

    private float oldGamma;

    public FullbrightMod() {
        super("Fullbright", "Makes everything bright.", Category.RENDER, Keyboard.KEY_NONE);
    }

    @Override
    public void onEnable() {
        oldGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100f;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = oldGamma;
    }
}
