package com.antigravity.module.impl.hud;

import com.antigravity.module.Module;
import com.antigravity.utils.RenderUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Collection;

public class PotionEffectsMod extends Module {

    public PotionEffectsMod() {
        super("Potion Effects", "Shows your active potion effects.", Category.RENDER, Keyboard.KEY_NONE);
        setHudX(5);
        setHudY(130);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
        if (!isToggled()) return;

        double curX = getHudX();
        double curY = getHudY();
        int offset = 0;

        Collection<PotionEffect> effects = mc.thePlayer.getActivePotionEffects();
        if (effects.isEmpty()) return;

        for (PotionEffect effect : effects) {
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            String name = I18n.format(potion.getName());
            String text = name + " " + (effect.getAmplifier() + 1) + " (" + Potion.getDurationString(effect) + ")";
            
            mc.fontRendererObj.drawStringWithShadow(text, (float) curX, (float) curY + offset, -1);
            offset += mc.fontRendererObj.FONT_HEIGHT + 2;
        }
        
        setHudWidth(100);
        setHudHeight(offset);
    }
}
