package com.antigravity.cosmetics.impl;

import com.antigravity.cosmetics.Cosmetic;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.GL11;

/**
 * Crown cosmetic – renders a small crown on the player's head.
 */
public class CrownCosmetic extends Cosmetic {

    public CrownCosmetic() {
        super("Crown", Category.HEAD);
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

        // Position on top of head
        GlStateManager.translate(0.0f, -0.55f, 0.0f);
        GlStateManager.scale(this.scale * 0.5f, this.scale * 0.5f, this.scale * 0.5f);

        float alpha = this.opacity / 255f;
        float r = backgroundColor.getRed() / 255f;
        float g = backgroundColor.getGreen() / 255f;
        float b = backgroundColor.getBlue() / 255f;

        // Crown base – flat ring
        GL11.glColor4f(r, g, b, alpha);
        GL11.glBegin(GL11.GL_QUAD_STRIP);
        int segments = 20;
        float baseR = 0.35f;
        float topR = 0.35f;
        float crownH = 0.25f;
        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);
            GL11.glVertex3f(cos * baseR, 0, sin * baseR);
            GL11.glVertex3f(cos * topR, crownH, sin * topR);
        }
        GL11.glEnd();

        // Crown peaks
        int peaks = 5;
        float peakHeight = 0.15f;
        GL11.glColor4f(Math.min(1f, r * 1.3f), Math.min(1f, g * 1.1f), b * 0.6f, alpha);
        GL11.glBegin(GL11.GL_TRIANGLES);
        for (int i = 0; i < peaks; i++) {
            double a1 = 2.0 * Math.PI * i / peaks;
            double a2 = 2.0 * Math.PI * (i + 0.5) / peaks;
            double a3 = 2.0 * Math.PI * (i + 1) / peaks;

            GL11.glVertex3f((float)(Math.cos(a1) * topR), crownH, (float)(Math.sin(a1) * topR));
            GL11.glVertex3f((float)(Math.cos(a2) * topR * 0.8f), crownH + peakHeight, (float)(Math.sin(a2) * topR * 0.8f));
            GL11.glVertex3f((float)(Math.cos(a3) * topR), crownH, (float)(Math.sin(a3) * topR));
        }
        GL11.glEnd();

        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
