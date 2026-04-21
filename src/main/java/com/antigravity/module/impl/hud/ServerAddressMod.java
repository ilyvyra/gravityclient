package com.antigravity.module.impl.hud;

import com.antigravity.module.Module;
import com.antigravity.utils.RenderUtils;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ServerAddressMod extends Module {

    public ServerAddressMod() {
        super("Server Address", "Shows the current server IP.", Category.RENDER, Keyboard.KEY_NONE);
        setHudX(100);
        setHudY(20);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
        if (!isToggled()) return;

        String server = mc.isSingleplayer() ? "Singleplayer" : mc.getCurrentServerData().serverIP;
        String text = "IP: " + server;

        int width = mc.fontRendererObj.getStringWidth(text);
        int height = mc.fontRendererObj.FONT_HEIGHT;

        setHudWidth(width + 4);
        setHudHeight(height + 4);

        RenderUtils.drawRect(getHudX(), getHudY(), getHudWidth(), getHudHeight(), new Color(0, 0, 0, 100).getRGB());
        mc.fontRendererObj.drawStringWithShadow(text, (float) getHudX() + 2, (float) getHudY() + 2, -1);
    }
}
