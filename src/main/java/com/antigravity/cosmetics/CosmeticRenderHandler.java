package com.antigravity.cosmetics;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CosmeticRenderHandler {

    private final CosmeticManager cosmeticManager;

    public CosmeticRenderHandler(CosmeticManager cosmeticManager) {
        this.cosmeticManager = cosmeticManager;
    }

    @SubscribeEvent
    public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        if (event.entityPlayer == null) return;
        
        // This is safe to run with OptiFine as long as we use standard GL state logic properly
        for (Cosmetic cosmetic : cosmeticManager.getCosmetics()) {
            if (cosmetic.isEnabled()) {
                // Approximate ageInTicks since it's not directly in the event, or just pass 0 if unused.
                // Normally you'd get this from player.ticksExisted + partialTicks
                float ageInTicks = event.entityPlayer.ticksExisted + event.partialRenderTick;
                
                cosmetic.render(
                    (AbstractClientPlayer) event.entityPlayer, 
                    event.entityPlayer.limbSwing, 
                    event.entityPlayer.limbSwingAmount, 
                    event.partialRenderTick, 
                    ageInTicks, 
                    event.entityPlayer.rotationYawHead, // netHeadYaw placeholder
                    event.entityPlayer.rotationPitch, // headPitch
                    0.0625f, // scale
                    event.renderer
                );
            }
        }
    }
}
