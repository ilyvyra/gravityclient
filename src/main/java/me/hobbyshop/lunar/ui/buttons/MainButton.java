package me.hobbyshop.lunar.ui.buttons;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import me.hobbyshop.lunar.util.ClientGuiUtils;
import net.minecraft.client.renderer.GlStateManager;

/**
 * Modernized MainButton with smooth hover animation and glassmorphism styling.
 */
public class MainButton {
	
	protected String text;
	protected int x, y;
	protected int width, height;
	
	protected float hoverFade = 0;
	protected float hoverScale = 0f; // for subtle scale animation
	
	public MainButton(String text, int x, int y) {
		this.text = text;
		this.x = x;
		this.y = y;
		
		this.width = 150;
		this.height = 20;
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	}
	
	public void drawButton(int mouseX, int mouseY) {
		boolean hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		
		float fadeSpeed = 5f;
		if (hovered) {
			if (hoverFade < 60) hoverFade += fadeSpeed;
		} else {
			if (hoverFade > 0) hoverFade -= fadeSpeed;
		}
		hoverFade = Math.max(0, Math.min(60, hoverFade));
		
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		
		// Button body
		int bgAlpha = 80 + (int) hoverFade;
		ClientGuiUtils.drawRoundedRect(this.x, this.y, this.width, this.height, 2, new Color(0, 0, 0, bgAlpha));
		
		// Text
		int w = Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.text);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.text, this.x + this.width / 2f - w / 2f, this.y + (this.height - 8) / 2f, -1);
	}

}
