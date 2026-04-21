package com.antigravity.setting.impl;

import com.antigravity.setting.Setting;

public class BooleanSetting extends Setting {
    private boolean enabled;

    public BooleanSetting(String name, boolean enabled) {
        super(name);
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }
}
