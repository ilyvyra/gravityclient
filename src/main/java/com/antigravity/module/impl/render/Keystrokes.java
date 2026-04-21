package com.antigravity.module.impl.render;

import com.antigravity.module.Module;
import com.antigravity.setting.impl.BooleanSetting;
import com.antigravity.setting.impl.NumberSetting;
import com.antigravity.utils.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class Keystrokes extends Module {

    private final NumberSetting x = new NumberSetting("X", 10, 0, 1000, 1);
    private final NumberSetting y = new NumberSetting("Y", 10, 0, 1000, 1);
    private final NumberSetting size = new NumberSetting("Size", 25, 10, 50, 1);
    private final BooleanSetting mouseButtons = new BooleanSetting("Mouse Buttons", true);

    public Keystrokes() {
        super("Keystrokes", "Shows your keystrokes on screen.", Category.RENDER, Keyboard.KEY_NONE);
        addSettings(x, y, size, mouseButtons);
        setHudX(x.getValue());
        setHudY(y.getValue());
        setHudWidth(size.getValue() * 3 + 4);
        setHudHeight(size.getValue() * 4 + 6);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
        if (!isToggled()) return;

        double curX = x.getValue();
        double curY = y.getValue();
        double s = size.getValue();
        double gap = 2;

        // W
        drawKey("W", curX + s + gap, curY, s, s, mc.gameSettings.keyBindForward.isKeyDown());
        // A
        drawKey("A", curX, curY + s + gap, s, s, mc.gameSettings.keyBindLeft.isKeyDown());
        // S
        drawKey("S", curX + s + gap, curY + s + gap, s, s, mc.gameSettings.keyBindBack.isKeyDown());
        // D
        drawKey("D", curX + (s + gap) * 2, curY + s + gap, s, s, mc.gameSettings.keyBindRight.isKeyDown());

        if (mouseButtons.isEnabled()) {
            // LMB
            drawKey("LMB", curX, curY + (s + gap) * 2, (s * 3 + gap * 2) / 2 - gap/2, s, Mouse.isButtonDown(0));
            // RMB
            drawKey("RMB", curX + (s * 3 + gap * 2) / 2 + gap/2, curY + (s + gap) * 2, (s * 3 + gap * 2) / 2 - gap/2, s, Mouse.isButtonDown(1));
        }
        
        // Space
        drawKey("SPACE", curX, curY + (s + gap) * (mouseButtons.isEnabled() ? 3 : 2), s * 3 + gap * 2, s / 2, mc.gameSettings.keyBindJump.isKeyDown());
    }

    private void drawKey(String name, double x, double y, double width, double height, boolean pressed) {
        int bgColor = pressed ? new Color(255, 255, 255, 150).getRGB() : new Color(0, 0, 0, 100).getRGB();
        int textColor = pressed ? Color.BLACK.getRGB() : Color.WHITE.getRGB();

        RenderUtils.drawRect(x, y, width, height, bgColor);
        
        double textX = x + (width - mc.fontRendererObj.getStringWidth(name)) / 2;
        double textY = y + (height - mc.fontRendererObj.FONT_HEIGHT) / 2;
        
        mc.fontRendererObj.drawStringWithShadow(name, (float) textX, (float) textY, textColor);
    }
}
