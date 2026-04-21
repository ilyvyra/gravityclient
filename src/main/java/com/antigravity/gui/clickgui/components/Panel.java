package com.antigravity.gui.clickgui.components;

import com.antigravity.Antigravity;
import com.antigravity.module.Module;
import com.antigravity.utils.RenderUtils;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Panel {
    private final Module.Category category;
    private final List<ModuleButton> buttons = new ArrayList<>();
    private double x, y, width, height;
    private double dragX, dragY;
    private boolean dragging = false;
    private boolean folded = false;

    public Panel(Module.Category category, double x, double y, double width, double height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        for (Module m : Antigravity.INSTANCE.getModuleManager().getModulesByCategory(category)) {
            buttons.add(new ModuleButton(m));
        }
    }

    public void draw(int mouseX, int mouseY) {
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }

        // Draw header
        RenderUtils.drawRect(x, y, width, height, new Color(0, 100, 200).getRGB());
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(category.name(), (float) (x + 5), (float) (y + height / 2 - 4), -1);

        if (!folded) {
            double curY = y + height;
            for (ModuleButton b : buttons) {
                b.setPos(x, curY, width, 25);
                b.draw(mouseX, mouseY);
                curY += b.getHeight();
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                dragging = true;
                dragX = mouseX - x;
                dragY = mouseY - y;
            } else if (mouseButton == 1) {
                folded = !folded;
            }
        }

        if (!folded) {
            for (ModuleButton b : buttons) {
                b.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        if (!folded) {
            for (ModuleButton b : buttons) {
                b.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
