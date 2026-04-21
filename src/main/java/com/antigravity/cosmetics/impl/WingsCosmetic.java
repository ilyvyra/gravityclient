package com.antigravity.cosmetics.impl;

import com.antigravity.cosmetics.Cosmetic;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.GL11;

/**
 * Wings cosmetic – renders stylized translucent wings on the player's back.
 */
public class WingsCosmetic extends Cosmetic {

    public WingsCosmetic() {
        super("Wings", Category.BACK);
    }

    @Override
    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch,
                       float scale, RenderPlayer renderPlayer) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        // Position wings on the back
        GlStateManager.translate(0.0f, 0.2f, 0.15f);
        GlStateManager.scale(this.scale, this.scale, this.scale);

        // Subtle wing flap animation
        float flapAngle = (float) (Math.sin(ageInTicks * 0.15) * 10.0);

        float alpha = this.opacity / 255f;
        float r = backgroundColor.getRed() / 255f;
        float g = backgroundColor.getGreen() / 255f;
        float b = backgroundColor.getBlue() / 255f;

        // Left wing
        GlStateManager.pushMatrix();
        GlStateManager.rotate(180f + flapAngle, 0, 1, 0);
        GL11.glColor4f(r, g, b, alpha * 0.7f);
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex3f(-0.8f, -0.6f, -0.1f);
        GL11.glVertex3f(-0.3f, 0.5f, -0.05f);

        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex3f(-0.3f, 0.5f, -0.05f);
        GL11.glVertex3f(-1.0f, 0.2f, -0.15f);

        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex3f(-1.0f, 0.2f, -0.15f);
        GL11.glVertex3f(-0.8f, -0.6f, -0.1f);
        GL11.glEnd();
        GlStateManager.popMatrix();

        // Right wing
        GlStateManager.pushMatrix();
        GlStateManager.rotate(180f - flapAngle, 0, 1, 0);
        GL11.glColor4f(r, g, b, alpha * 0.7f);
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex3f(0.8f, -0.6f, -0.1f);
        GL11.glVertex3f(0.3f, 0.5f, -0.05f);

        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex3f(0.3f, 0.5f, -0.05f);
        GL11.glVertex3f(1.0f, 0.2f, -0.15f);

        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex3f(1.0f, 0.2f, -0.15f);
        GL11.glVertex3f(0.8f, -0.6f, -0.1f);
        GL11.glEnd();
        GlStateManager.popMatrix();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
