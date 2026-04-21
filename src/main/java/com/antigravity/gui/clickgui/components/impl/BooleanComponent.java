package com.antigravity.gui.clickgui.components.impl;

import com.antigravity.gui.clickgui.components.SettingComponent;
import com.antigravity.setting.impl.BooleanSetting;
import com.antigravity.utils.RenderUtils;

import java.awt.*;

public class BooleanComponent extends SettingComponent {
    
    public BooleanComponent(BooleanSetting setting) {
        super(setting);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        BooleanSetting s = (BooleanSetting) setting;
        int color = s.isEnabled() ? new Color(0, 150, 255).getRGB() : new Color(255, 255, 255, 20).getRGB();
        
        RenderUtils.drawRect(x, y, width, height, new Color(0, 0, 0, 100).getRGB());
        
        // Draw checkbox
        RenderUtils.drawRect(x + 5, y + 4, 10, 10, new Color(255, 255, 255, 20).getRGB());
        if (s.isEnabled()) {
            RenderUtils.drawRect(x + 6, y + 5, 8, 8, color);
        }
        
        mc.fontRendererObj.drawStringWithShadow(s.getName(), (float) (x + 20), (float) (y + 5), -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            ((BooleanSetting) setting).toggle();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {}

    @Override
    public void keyTyped(char typedChar, int keyCode) {}

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
