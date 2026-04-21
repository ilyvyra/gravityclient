package com.antigravity.module.impl.hud;

import com.antigravity.module.Module;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class ArmorStatusMod extends Module {

    public ArmorStatusMod() {
        super("Armor Status", "Shows your current armor durability.", Category.RENDER, Keyboard.KEY_NONE);
        setHudX(5);
        setHudY(50);
        setHudWidth(20);
        setHudHeight(80);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
        if (!isToggled()) return;

        double curX = getHudX();
        double curY = getHudY();

        RenderHelper.enableGUIStandardItemLighting();
        for (int i = 0; i < 4; i++) {
            ItemStack is = mc.thePlayer.inventory.armorItemInSlot(3 - i);
            if (is != null) {
                mc.getRenderItem().renderItemAndEffectIntoGUI(is, (int) curX, (int) curY + (i * 18));
                mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, is, (int) curX, (int) curY + (i * 18));
            }
        }
        RenderHelper.disableStandardItemLighting();
    }
}
