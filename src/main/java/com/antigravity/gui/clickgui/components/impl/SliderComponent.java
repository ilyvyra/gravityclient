package com.antigravity.gui.clickgui.components.impl;

import com.antigravity.gui.clickgui.components.SettingComponent;
import com.antigravity.setting.impl.NumberSetting;
import com.antigravity.utils.RenderUtils;

import java.awt.*;

public class SliderComponent extends SettingComponent {
    
    private boolean dragging = false;

    public SliderComponent(NumberSetting setting) {
        super(setting);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        NumberSetting s = (NumberSetting) setting;
        
        if (dragging) {
            double diff = mouseX - x;
            double val = s.getMinimum() + (diff / width) * (s.getMaximum() - s.getMinimum());
            s.setValue(val);
        }

        RenderUtils.drawRect(x, y, width, height, new Color(0, 0, 0, 100).getRGB());
        
        double sliderWidth = (s.getValue() - s.getMinimum()) / (s.getMaximum() - s.getMinimum()) * width;
        RenderUtils.drawRect(x, y + height - 2, width, 2, new Color(255, 255, 255, 10).getRGB());
        RenderUtils.drawRect(x, y + height - 2, sliderWidth, 2, new Color(0, 150, 255).getRGB());
        
        mc.fontRendererObj.drawStringWithShadow(s.getName() + ": " + s.getValue(), (float) (x + 5), (float) (y + 5), -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {}

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
