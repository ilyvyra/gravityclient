package com.antigravity.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderUtils {

    public static void drawRect(double x, double y, double width, double height, int color) {
        Gui.drawRect((int) x, (int) y, (int) (x + width), (int) (y + height), color);
    }

    public static void drawBorderedRect(double x, double y, double width, double height, double thickness, int rectColor, int borderColor) {
        drawRect(x, y, width, height, rectColor);
        drawRect(x, y, thickness, height, borderColor);
        drawRect(x + width - thickness, y, thickness, height, borderColor);
        drawRect(x, y, width, thickness, borderColor);
        drawRect(x, y + height - thickness, width, thickness, borderColor);
    }

    public static void drawGradientRect(double x, double y, double width, double height, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(x + width, y, 0.0D).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(x, y, 0.0D).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(x, y + height, 0.0D).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0D).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawRoundedRect(double x, double y, double width, double height, double radius, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        setColor(color);

        GL11.glBegin(GL11.GL_POLYGON);

        double[][] corners = {
            {x + width - radius, y + radius},
            {x + radius, y + radius},
            {x + radius, y + height - radius},
            {x + width - radius, y + height - radius}
        };

        for (int i = 0; i < 4; i++) {
            double angleStart = i * 90;
            for (int j = 0; j <= 90; j += 10) {
                double rad = Math.toRadians(angleStart + j);
                GL11.glVertex2d(corners[i][0] + Math.sin(rad) * radius, corners[i][1] - Math.cos(rad) * radius);
            }
        }

        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawSmoothRect(double x, double y, double width, double height, int color) {
        drawRect(x, y, width, height, color);
    }

    public static void setColor(int color) {
        float f = (float) (color >> 24 & 255) / 255.0F;
        float f1 = (float) (color >> 16 & 255) / 255.0F;
        float f2 = (float) (color >> 8 & 255) / 255.0F;
        float f3 = (float) (color & 255) / 255.0F;
        GlStateManager.color(f1, f2, f3, f);
    }

    public static int getRGBA(int r, int g, int b, int a) {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
    }

    public static void loadBlurShader(Minecraft mc) {
        try {
            if (mc.entityRenderer.getShaderGroup() == null) {
                mc.entityRenderer.loadShader(new net.minecraft.util.ResourceLocation("shaders/post/blur.json"));
            }
        } catch (Exception e) {
            // Ignore if shaders aren't supported or Optifine breaks it
        }
    }

    public static void unloadBlurShader(Minecraft mc) {
        if (mc.entityRenderer.getShaderGroup() != null) {
            mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
}
