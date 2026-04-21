package me.hobbyshop.lunar.ui.buttons;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import me.hobbyshop.lunar.util.ClientGuiUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Modernized QuitButton with red-tinted hover and smooth animation.
 */
public class QuitButton extends ImageButton {

	public QuitButton(int x, int y) {
		super("QUIT", new ResourceLocation("lunar/icons/exit.png"), x, y);
	}
	
	@Override
	public void drawButton(int mouseX, int mouseY) {
		boolean hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		
		// Smooth fade
		float fadeSpeed = 5f;
		if (hovered) {
			if (hoverFade < 50) hoverFade += fadeSpeed;
			drawHoverEffect();
		} else {
			if (hoverFade > 0) hoverFade -= fadeSpeed;
		}
		hoverFade = Math.max(0, Math.min(50, hoverFade));
		
		// Outer shadow
		ClientGuiUtils.drawRoundedRect(this.x - 1, this.y - 1, this.width + 2, this.height + 2, 2, new Color(20, 18, 28, 70));
		
		// Button body – tints red on hover
		int redTint = (int)(hoverFade * 3.5f);
		ClientGuiUtils.drawRoundedRect(this.x, this.y, this.width, this.height, 2, new Color(255, Math.max(0, 255 - redTint * 3), Math.max(0, 255 - redTint * 3), 38 + (int)hoverFade));
		
		// Border
		ClientGuiUtils.drawRoundedOutline(this.x, this.y, this.x + this.width, this.y + this.height, 2, 3, new Color(255, 255, 255, 30).getRGB());
		
		// Icon
		int color = new Color(232, 232, 232, 183).getRGB();
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
	
	@Override
	protected void drawHoverEffect() {
		int w = Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.text);
		ClientGuiUtils.drawRoundedRect(this.x + (this.width - w) / 2, this.y + 17, w, 8, 2, new Color(16, 14, 22, 200));
		
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.9f, 0.9f, 1f);
        float scaledX = (this.x + this.width / 2f) / 0.9f;
        float scaledY = (this.y + 18) / 0.9f;
        int textAlpha = Math.min(220, 100 + (int)(hoverFade * 2.4f));
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.text, scaledX - w / 2f, scaledY, new Color(255, 140, 140, textAlpha).getRGB());
        GlStateManager.popMatrix();
	}

}
