package com.antigravity.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class ReflectionUtils {

    public static Timer getTimer() {
        try {
            return ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "timer", "field_71428_T");
        } catch (Exception e) {
            return null;
        }
    }

    public static void setPressed(net.minecraft.client.settings.KeyBinding key, boolean pressed) {
        try {
            Field field = ReflectionHelper.findField(net.minecraft.client.settings.KeyBinding.class, "pressed", "field_74513_e");
            field.setAccessible(true);
            field.setBoolean(key, pressed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
