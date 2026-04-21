package com.antigravity.module.impl.performance;

import com.antigravity.module.Module;
import com.antigravity.setting.impl.BooleanSetting;
import org.lwjgl.input.Keyboard;

public class PerformanceSettings extends Module {

    public static final BooleanSetting FAST_MATH = new BooleanSetting("Fast Math", true);
    public static final BooleanSetting SMART_ENTITIES = new BooleanSetting("Smart Entities", true);
    public static final BooleanSetting REDUCED_PARTICLES = new BooleanSetting("Reduced Particles", false);

    public PerformanceSettings() {
        super("Performance", "Optimizes your game for higher FPS.", Category.UTILITY, Keyboard.KEY_NONE);
        addSettings(FAST_MATH, SMART_ENTITIES, REDUCED_PARTICLES);
        setToggled(true); // Always on by default
    }
}
