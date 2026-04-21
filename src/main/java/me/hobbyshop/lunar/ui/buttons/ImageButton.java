package me.hobbyshop.lunar.ui.buttons;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.hobbyshop.lunar.util.ClientGuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Modernized ImageButton with smooth hover animations and accent effects.
 */
public class ImageButton extends MainButton {
	
	protected ResourceLocation image;

	public ImageButton(String text, ResourceLocation image, int x, int y) {
		super(text, x, y);
		this.width = 12;
		this.height = 12;
		this.image = image;
	}
	
	@Override
	public void drawButton(int mouseX, int mouseY) {
		boolean hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		
		// Smooth hover fade
		float fadeSpeed = 4f;
		if (hovered) {
			if (hoverFade < 50) hoverFade += fadeSpeed;
			drawHoverEffect();
		} else {
			if (hoverFade > 0) hoverFade -= fadeSpeed;
		}
		hoverFade = Math.max(0, Math.min(50, hoverFade));
		
		// Outer shadow
		ClientGuiUtils.drawRoundedRect(this.x - 1, this.y - 1, this.width + 2, this.height + 2, 2, new Color(20, 18, 28, 70));
		// Button body
		ClientGuiUtils.drawRoundedRect(this.x, this.y, this.width, this.height, 2, new Color(255, 255, 255, 38 + (int)hoverFade));
		// Border
		int borderAlpha = 30 + (int)(hoverFade * 0.5f);
		ClientGuiUtils.drawRoundedOutline(this.x, this.y, this.x + this.width, this.y + this.height, 2, 3, new Color(255, 255, 255, borderAlpha).getRGB());
		
		// Bottom accent on hover
		if (hoverFade > 10) {
			int accentAlpha = (int)(hoverFade * 3f);
			ClientGuiUtils.drawRoundedRect(this.x, this.y + this.height - 2, this.width, 2, 1, new Color(130, 100, 255, Math.min(180, accentAlpha)));
		}
		
		// Image icon
		int color = new Color(232, 232, 232, 183 + (int)(hoverFade * 0.8f)).getRGB();
		float f1 = (color >> 24 & 0xFF) / 255.0F;
        float f2 = (color >> 16 & 0xFF) / 255.0F;
        float f3 = (color >> 8 & 0xFF) / 255.0F;
        float f4 = (color & 0xFF) / 255.0F;
		GL11.glColor4f(f2, f3, f4, f1);
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(image);
		Gui.drawModalRectWithCustomSizedTexture(this.x + 3, this.y + 3, 0, 0, 6, 6, 6, 6);
		
		GlStateManager.disableBlend();
		GlStateManager.disableAlpha();
	}
	
	protected void drawHoverEffect() {
		int w = (int) (Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.text) * 0.9F);
		ClientGuiUtils.drawRoundedRect(this.x + (this.width - w) / 2, this.y - 13, w, 8, 2, new Color(16, 14, 22, 200));
		
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.9f, 0.9f, 1f);
        float scaledX = (this.x + this.width / 2f) / 0.9f;
        float scaledY = (this.y - 12) / 0.9f;
        int textAlpha = Math.min(220, 100 + (int)(hoverFade * 2.4f));
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.text, scaledX - w / 2f, scaledY, new Color(210, 200, 240, textAlpha).getRGB());
        GlStateManager.popMatrix();
	}

}
