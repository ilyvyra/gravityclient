package com.antigravity.gui.clickgui.components;

import com.antigravity.module.Module;
import com.antigravity.setting.Setting;
import com.antigravity.setting.impl.BooleanSetting;
import com.antigravity.setting.impl.ModeSetting;
import com.antigravity.setting.impl.NumberSetting;
import com.antigravity.gui.clickgui.components.impl.BooleanComponent;
import com.antigravity.gui.clickgui.components.impl.SliderComponent;
import com.antigravity.utils.RenderUtils;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton {
    private final Module module;
    private final List<SettingComponent> components = new ArrayList<>();
    private boolean expanded = false;
    private double x, y, width, height;

    public ModuleButton(Module module) {
        this.module = module;
        for (Setting s : module.getSettings()) {
            if (s instanceof BooleanSetting) components.add(new BooleanComponent((BooleanSetting) s));
            else if (s instanceof NumberSetting) components.add(new SliderComponent((NumberSetting) s));
            // Add ModeComponent here if implemented
        }
    }

    public void draw(int mouseX, int mouseY) {
        int color = module.isToggled() ? new Color(0, 150, 255).getRGB() : new Color(255, 255, 255, 5).getRGB();
        RenderUtils.drawRect(x, y, width, height, color);
        
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(module.getName(), (float) (x + 5), (float) (y + height/2 - 4), -1);
        
        if (expanded) {
            double curY = y + height;
            for (SettingComponent c : components) {
                c.setPos(x, curY, width, 20);
                c.draw(mouseX, mouseY);
                curY += 20;
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                module.toggle();
            } else if (mouseButton == 1) {
                expanded = !expanded;
            }
        }
        
        if (expanded) {
            for (SettingComponent c : components) {
                c.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (expanded) {
            for (SettingComponent c : components) {
                c.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    public void setPos(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getHeight() {
        double h = height;
        if (expanded) {
            h += components.size() * 20;
        }
        return h;
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
