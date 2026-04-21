package com.antigravity.gui.clickgui.components;

import com.antigravity.setting.Setting;
import net.minecraft.client.Minecraft;

public abstract class SettingComponent {
    protected final Minecraft mc = Minecraft.getMinecraft();
    protected final Setting setting;
    protected double x, y, width, height;

    public SettingComponent(Setting setting) {
        this.setting = setting;
    }

    public abstract void draw(int mouseX, int mouseY);
    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);
    public abstract void mouseReleased(int mouseX, int mouseY, int state);
    public abstract void keyTyped(char typedChar, int keyCode);

    public void setPos(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public Setting getSetting() {
        return setting;
    }
}
