package com.antigravity.module.impl.hud;

import com.antigravity.module.Module;
import com.antigravity.utils.RenderUtils;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CpsMod extends Module {

    private final List<Long> leftClicks = new ArrayList<Long>();
    private final List<Long> rightClicks = new ArrayList<Long>();
    private boolean leftWasDown;
    private boolean rightWasDown;

    public CpsMod() {
        super("CPS", "Shows your clicks per second.", Category.RENDER, Keyboard.KEY_NONE);
        setHudX(5);
        setHudY(20);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
        if (!isToggled()) return;

        updateClicks();

        String text = "CPS: " + leftClicks.size() + " | " + rightClicks.size();
        int width = mc.fontRendererObj.getStringWidth(text);
        int height = mc.fontRendererObj.FONT_HEIGHT;

        setHudWidth(width + 4);
        setHudHeight(height + 4);

        RenderUtils.drawRect(getHudX(), getHudY(), getHudWidth(), getHudHeight(), new Color(0, 0, 0, 100).getRGB());
        mc.fontRendererObj.drawStringWithShadow(text, (float) getHudX() + 2, (float) getHudY() + 2, -1);
    }

    private void updateClicks() {
        boolean leftDown = Mouse.isButtonDown(0);
        boolean rightDown = Mouse.isButtonDown(1);

        if (leftDown && !leftWasDown) {
            leftClicks.add(System.currentTimeMillis());
        }
        if (rightDown && !rightWasDown) {
            rightClicks.add(System.currentTimeMillis());
        }

        leftWasDown = leftDown;
        rightWasDown = rightDown;

        long time = System.currentTimeMillis();
        leftClicks.removeIf(t -> t < time - 1000);
        rightClicks.removeIf(t -> t < time - 1000);
    }
}
