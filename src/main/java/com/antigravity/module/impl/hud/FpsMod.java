package com.antigravity.module.impl.hud;

import com.antigravity.module.Module;
import com.antigravity.setting.impl.BooleanSetting;
import com.antigravity.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class FpsMod extends Module {

    private final BooleanSetting shadow = new BooleanSetting("Shadow", true);

    public FpsMod() {
        super("FPS", "Shows your current frames per second.", Category.RENDER, Keyboard.KEY_NONE);
        addSettings(shadow);
        setHudX(5);
        setHudY(5);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
        if (!isToggled()) return;

        String text = "FPS: " + Minecraft.getDebugFPS();
        int width = mc.fontRendererObj.getStringWidth(text);
        int height = mc.fontRendererObj.FONT_HEIGHT;

        setHudWidth(width + 4);
        setHudHeight(height + 4);

        RenderUtils.drawRect(getHudX(), getHudY(), getHudWidth(), getHudHeight(), new Color(0, 0, 0, 100).getRGB());
        
        if (shadow.isEnabled()) {
            mc.fontRendererObj.drawStringWithShadow(text, (float) getHudX() + 2, (float) getHudY() + 2, -1);
        } else {
            mc.fontRendererObj.drawString(text, (int) getHudX() + 2, (int) getHudY() + 2, -1);
        }
    }
}
