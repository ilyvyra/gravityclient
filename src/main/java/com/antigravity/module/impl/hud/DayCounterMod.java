package com.antigravity.module.impl.hud;

import com.antigravity.module.Module;
import com.antigravity.utils.RenderUtils;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class DayCounterMod extends Module {

    public DayCounterMod() {
        super("Day Counter", "Shows the number of in-game days passed.", Category.RENDER, Keyboard.KEY_NONE);
        setHudX(100);
        setHudY(35);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
        if (!isToggled()) return;

        long day = mc.theWorld.getTotalWorldTime() / 24000L;
        String text = "Day: " + day;

        int width = mc.fontRendererObj.getStringWidth(text);
        int height = mc.fontRendererObj.FONT_HEIGHT;

        setHudWidth(width + 4);
        setHudHeight(height + 4);

        RenderUtils.drawRect(getHudX(), getHudY(), getHudWidth(), getHudHeight(), new Color(0, 0, 0, 100).getRGB());
        mc.fontRendererObj.drawStringWithShadow(text, (float) getHudX() + 2, (float) getHudY() + 2, -1);
    }
}
