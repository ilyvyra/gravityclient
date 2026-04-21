package com.antigravity.cosmetics;

import java.awt.Color;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;

public abstract class Cosmetic {
    private final String name;
    private final Category category;
    private boolean enabled;

    // Cosmetic Customization Settings
    public boolean showBackground = true;
    public Color backgroundColor = new Color(0, 0, 0, 100);
    public int opacity = 100; // 0-255
    public float scale = 1.0f;
    public boolean blurEffect = false;
    public boolean shadow = true;
    public String fontName = "Standard";
    public float fontSize = 1.0f;

    public Cosmetic(String name, Category category) {
        this.name = name;
        this.category = category;
        this.enabled = false;
    }

    public abstract void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, RenderPlayer renderPlayer);

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }

    public enum Category {
        HEAD("Head"),
        BODY("Body"),
        BACK("Back"),
        HAND("Hand");

        private final String displayName;

        Category(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
