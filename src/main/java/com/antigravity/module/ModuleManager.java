package com.antigravity.module;

import com.antigravity.module.impl.hud.*;
import com.antigravity.module.impl.visual.*;
import com.antigravity.module.impl.gameplay.*;
import com.antigravity.module.impl.render.Keystrokes;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {

    private final List<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        // HUD
        register(new FpsMod());
        register(new CpsMod());
        register(new CoordinatesMod());
        register(new ArmorStatusMod());
        register(new PotionEffectsMod());
        register(new PingMod());
        register(new ServerAddressMod());
        register(new DayCounterMod());
        register(new TimeMod());
        register(new SpeedMod());
        register(new DirectionMod());
        register(new ReachDisplayMod());
        register(new Keystrokes());

        // Visual
        register(new FullbrightMod());

        // Gameplay
        register(new ToggleSprintMod());
        register(new ToggleSneakMod());
        register(new ZoomMod());
        register(new NoHurtCamMod());
        register(new NickHiderMod());
        
        // Performance (Legacy)
        register(new com.antigravity.module.impl.performance.PerformanceSettings());
    }

    private void register(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getModulesByCategory(Module.Category category) {
        return modules.stream()
                .filter(m -> m.getCategory() == category)
                .collect(Collectors.toList());
    }

    public List<Module> getFavoriteModules() {
        return modules.stream()
                .filter(Module::isFavorite)
                .collect(Collectors.toList());
    }

    public Module getModuleByName(String name) {
        return modules.stream()
                .filter(m -> m.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void handleKeyInput(int key) {
        for (Module module : modules) {
            if (module.getKey() == key && key != Keyboard.KEY_NONE) {
                module.toggle();
            }
        }
    }
}
