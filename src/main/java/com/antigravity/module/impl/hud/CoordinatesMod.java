package com.antigravity.module.impl.hud;

import com.antigravity.module.Module;
import com.antigravity.utils.RenderUtils;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class CoordinatesMod extends Module {

    public CoordinatesMod() {
        super("Coordinates", "Shows your current XYZ coordinates.", Category.RENDER, Keyboard.KEY_NONE);
        setHudX(5);
        setHudY(35);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
        if (!isToggled()) return;

        String x = String.format("%.1f", mc.thePlayer.posX);
        String y = String.format("%.1f", mc.thePlayer.posY);
        String z = String.format("%.1f", mc.thePlayer.posZ);
        String text = "XYZ: " + x + " / " + y + " / " + z;

        int width = mc.fontRendererObj.getStringWidth(text);
        int height = mc.fontRendererObj.FONT_HEIGHT;

        setHudWidth(width + 4);
        setHudHeight(height + 4);

        RenderUtils.drawRect(getHudX(), getHudY(), getHudWidth(), getHudHeight(), new Color(0, 0, 0, 100).getRGB());
        mc.fontRendererObj.drawStringWithShadow(text, (float) getHudX() + 2, (float) getHudY() + 2, -1);
    }
}
