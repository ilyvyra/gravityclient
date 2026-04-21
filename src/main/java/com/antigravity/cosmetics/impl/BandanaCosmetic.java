package com.antigravity.cosmetics.impl;

import com.antigravity.cosmetics.Cosmetic;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.GL11;

/**
 * Bandana cosmetic – renders a colored strip around the player's head.
 */
public class BandanaCosmetic extends Cosmetic {

    public BandanaCosmetic() {
        super("Bandana", Category.HEAD);
    }

    @Override
    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch,
                       float scale, RenderPlayer renderPlayer) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        // Position on head
        GlStateManager.translate(0.0f, -0.05f, 0.0f);
        GlStateManager.scale(this.scale * 0.65f, this.scale * 0.65f, this.scale * 0.65f);

        float alpha = this.opacity / 255f;
        float r = backgroundColor.getRed() / 255f;
        float g = backgroundColor.getGreen() / 255f;
        float b = backgroundColor.getBlue() / 255f;

        // Band ring around the head
        float bandWidth = 0.08f;
        float radius = 0.37f;
        int segments = 24;

        GL11.glColor4f(r, g, b, alpha);
        GL11.glBegin(GL11.GL_QUAD_STRIP);
        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);
            GL11.glVertex3f(cos * radius, -bandWidth, sin * radius);
            GL11.glVertex3f(cos * radius, bandWidth, sin * radius);
        }
        GL11.glEnd();

        // Tail flowing part at the back
        float tailLen = 0.3f + (float)(Math.sin(ageInTicks * 0.1) * 0.05);
        GL11.glColor4f(r, g, b, alpha * 0.85f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(-0.08f, -bandWidth, -radius);
        GL11.glVertex3f(0.08f, -bandWidth, -radius);
        GL11.glVertex3f(0.06f, -bandWidth - tailLen, -radius - 0.1f);
        GL11.glVertex3f(-0.06f, -bandWidth - tailLen, -radius - 0.1f);
        GL11.glEnd();

        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
