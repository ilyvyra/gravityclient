package com.antigravity.module.impl.hud;

import com.antigravity.module.Module;
import com.antigravity.utils.RenderUtils;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class DirectionMod extends Module {

    public DirectionMod() {
        super("Direction", "Shows your current facing direction.", Category.RENDER, Keyboard.KEY_NONE);
        setHudX(100);
        setHudY(80);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
        if (!isToggled()) return;

        int direction = MathHelper.floor_double((double) (mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        String dir = "";
        switch (direction) {
            case 0: dir = "South (+Z)"; break;
            case 1: dir = "West (-X)"; break;
            case 2: dir = "North (-Z)"; break;
            case 3: dir = "East (+X)"; break;
        }

        String text = dir;
        int width = mc.fontRendererObj.getStringWidth(text);
        int height = mc.fontRendererObj.FONT_HEIGHT;

        setHudWidth(width + 4);
        setHudHeight(height + 4);

        RenderUtils.drawRect(getHudX(), getHudY(), getHudWidth(), getHudHeight(), new Color(0, 0, 0, 100).getRGB());
        mc.fontRendererObj.drawStringWithShadow(text, (float) getHudX() + 2, (float) getHudY() + 2, -1);
    }
}
