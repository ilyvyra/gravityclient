package com.antigravity.module.impl.hud;

import com.antigravity.module.Module;
import com.antigravity.utils.RenderUtils;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class SpeedMod extends Module {

    public SpeedMod() {
        super("Speed", "Shows your current movement speed.", Category.RENDER, Keyboard.KEY_NONE);
        setHudX(100);
        setHudY(65);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
        if (!isToggled()) return;

        double xDiff = mc.thePlayer.posX - mc.thePlayer.prevPosX;
        double zDiff = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
        net.minecraft.util.Timer timer = com.antigravity.utils.ReflectionUtils.getTimer();
        double timerSpeed = (timer != null) ? timer.timerSpeed : 1.0;
        double speed = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff) * 20.0 * timerSpeed;
        
        String text = String.format("Speed: %.2f m/s", speed);

        int width = mc.fontRendererObj.getStringWidth(text);
        int height = mc.fontRendererObj.FONT_HEIGHT;

        setHudWidth(width + 4);
        setHudHeight(height + 4);

        RenderUtils.drawRect(getHudX(), getHudY(), getHudWidth(), getHudHeight(), new Color(0, 0, 0, 100).getRGB());
        mc.fontRendererObj.drawStringWithShadow(text, (float) getHudX() + 2, (float) getHudY() + 2, -1);
    }
}
