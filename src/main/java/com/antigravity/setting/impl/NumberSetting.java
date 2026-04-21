package com.antigravity.setting.impl;

import com.antigravity.setting.Setting;

public class NumberSetting extends Setting {
    private double value;
    private final double minimum, maximum, increment;

    public NumberSetting(String name, double value, double minimum, double maximum, double increment) {
        super(name);
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        double precision = 1.0 / increment;
        this.value = Math.round(Math.max(minimum, Math.min(maximum, value)) * precision) / precision;
    }

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public double getIncrement() {
        return increment;
    }
}
