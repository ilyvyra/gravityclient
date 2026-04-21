package com.antigravity.cosmetics.impl;

import com.antigravity.cosmetics.Cosmetic;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.GL11;

/**
 * Halo cosmetic – renders a glowing ring above the player's head.
 */
public class HaloCosmetic extends Cosmetic {

    public HaloCosmetic() {
        super("Halo", Category.HEAD);
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

        // Position above head
        GlStateManager.translate(0.0f, -0.4f, 0.0f);
        GlStateManager.scale(this.scale, this.scale, this.scale);

        // Slow rotation
        float rotation = ageInTicks * 2.0f;
        GlStateManager.rotate(rotation, 0, 1, 0);
        // Tilt slightly
        GlStateManager.rotate(15, 1, 0, 0);

        float alpha = this.opacity / 255f;
        float r = backgroundColor.getRed() / 255f;
        float g = backgroundColor.getGreen() / 255f;
        float b = backgroundColor.getBlue() / 255f;

        // Draw ring using line strip
        float innerRadius = 0.3f;
        float outerRadius = 0.38f;
        int segments = 36;

        GL11.glLineWidth(2.0f);

        // Outer ring (bright)
        GL11.glColor4f(r, g, b, alpha * 0.9f);
        GL11.glBegin(GL11.GL_QUAD_STRIP);
        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);
            GL11.glVertex3f(cos * outerRadius, 0, sin * outerRadius);
            GL11.glVertex3f(cos * innerRadius, 0, sin * innerRadius);
        }
        GL11.glEnd();

        // Glow highlight
        GL11.glColor4f(1f, 1f, 0.8f, alpha * 0.3f);
        GL11.glBegin(GL11.GL_QUAD_STRIP);
        float glowRadius = outerRadius + 0.05f;
        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);
            GL11.glVertex3f(cos * glowRadius, 0, sin * glowRadius);
            GL11.glVertex3f(cos * outerRadius, 0, sin * outerRadius);
        }
        GL11.glEnd();

        GL11.glLineWidth(1.0f);
        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
