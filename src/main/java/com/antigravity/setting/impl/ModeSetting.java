package com.antigravity.setting.impl;

import com.antigravity.setting.Setting;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting {
    private String activeMode;
    private final List<String> modes;

    public ModeSetting(String name, String defaultMode, String... modes) {
        super(name);
        this.activeMode = defaultMode;
        this.modes = Arrays.asList(modes);
    }

    public String getActiveMode() {
        return activeMode;
    }

    public void setActiveMode(String activeMode) {
        this.activeMode = activeMode;
    }

    public List<String> getModes() {
        return modes;
    }

    public void cycle() {
        int index = modes.indexOf(activeMode);
        index++;
        if (index >= modes.size()) index = 0;
        activeMode = modes.get(index);
    }
}
