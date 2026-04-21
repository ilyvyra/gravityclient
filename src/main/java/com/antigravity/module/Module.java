package com.antigravity.module;

import com.antigravity.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {

    protected final Minecraft mc = Minecraft.getMinecraft();
    private final String name;
    private final String description;
    private final Category category;
    private int key;
    private boolean toggled;
    private boolean favorite;
    private final List<Setting> settings = new ArrayList<Setting>();

    // HUD positioning
    private double hudX = 5, hudY = 5;
    private double hudWidth = 80, hudHeight = 20;

    public Module(String name, String description, Category category, int key) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.key = key;
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void onToggle() {}
    public void onUpdate() {}
    public void onRender2D() {}

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
        if (toggled) {
            onEnable();
        } else {
            onDisable();
        }
        onToggle();
    }

    public void toggle() {
        setToggled(!toggled);
    }

    public void addSettings(Setting... settings) {
        for (Setting s : settings) {
            this.settings.add(s);
        }
    }

    // Getters & setters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }
    public boolean isToggled() { return toggled; }
    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }
    public List<Setting> getSettings() { return settings; }

    public String getSummary() {
        if (settings.isEmpty()) return description;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(settings.size(), 3); i++) {
            Setting s = settings.get(i);
            if (i > 0) sb.append(", ");
            // We can add logic here to pull values from settings if they are Boolean/Number
            sb.append(s.getName());
        }
        return sb.length() > 0 ? sb.toString() : description;
    }

    // HUD position
    public double getHudX() { return hudX; }
    public double getHudY() { return hudY; }
    public void setHudX(double hudX) { this.hudX = hudX; }
    public void setHudY(double hudY) { this.hudY = hudY; }
    public double getHudWidth() { return hudWidth; }
    public double getHudHeight() { return hudHeight; }
    public void setHudWidth(double w) { this.hudWidth = w; }
    public void setHudHeight(double h) { this.hudHeight = h; }

    public enum Category {
        COMBAT("Combat", "icon/combat.png", "\u2694"),
        RENDER("Render", "icon/render.png", "\u2701"),
        UTILITY("Utility", "icon/utility.png", "\u2699"),
        MOVEMENT("Movement", "icon/movement.png", "\u21CB"),
        FAVORITES("Favorites", "icon/favorites.png", "\u2605");

        private final String displayName;
        private final String iconPath;
        private final String iconChar;

        Category(String displayName, String iconPath, String iconChar) {
            this.displayName = displayName;
            this.iconPath = iconPath;
            this.iconChar = iconChar;
        }

        public String getDisplayName() { return displayName; }
        public String getIconPath() { return iconPath; }
        public String getIconChar() { return iconChar; }
    }
}
