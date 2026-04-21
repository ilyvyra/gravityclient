package com.antigravity.config;

import com.antigravity.Antigravity;
import com.antigravity.cosmetics.Cosmetic;
import com.antigravity.module.Module;
import com.antigravity.setting.Setting;
import com.antigravity.setting.impl.BooleanSetting;
import com.antigravity.setting.impl.NumberSetting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;

import java.awt.Color;
import java.io.*;

public class ConfigManager {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final File configFile = new File(Minecraft.getMinecraft().mcDataDir, "antigravity.json");

    public void save() {
        try {
            JsonObject json = new JsonObject();

            // ── Save Modules ──
            JsonObject modulesJson = new JsonObject();
            for (Module m : Antigravity.INSTANCE.getModuleManager().getModules()) {
                JsonObject moduleJson = new JsonObject();
                moduleJson.addProperty("toggled", m.isToggled());
                moduleJson.addProperty("key", m.getKey());
                moduleJson.addProperty("hudX", m.getHudX());
                moduleJson.addProperty("hudY", m.getHudY());

                JsonObject settingsJson = new JsonObject();
                for (Setting s : m.getSettings()) {
                    if (s instanceof BooleanSetting) settingsJson.addProperty(s.getName(), ((BooleanSetting) s).isEnabled());
                    else if (s instanceof NumberSetting) settingsJson.addProperty(s.getName(), ((NumberSetting) s).getValue());
                }
                moduleJson.add("settings", settingsJson);
                modulesJson.add(m.getName(), moduleJson);
            }
            json.add("modules", modulesJson);

            // ── Save Cosmetics ──
            JsonObject cosmeticsJson = new JsonObject();
            for (Cosmetic c : Antigravity.INSTANCE.getCosmeticManager().getCosmetics()) {
                JsonObject cosmeticJson = new JsonObject();
                cosmeticJson.addProperty("enabled", c.isEnabled());
                cosmeticJson.addProperty("showBackground", c.showBackground);
                cosmeticJson.addProperty("bgColorR", c.backgroundColor.getRed());
                cosmeticJson.addProperty("bgColorG", c.backgroundColor.getGreen());
                cosmeticJson.addProperty("bgColorB", c.backgroundColor.getBlue());
                cosmeticJson.addProperty("opacity", c.opacity);
                cosmeticJson.addProperty("scale", c.scale);
                cosmeticJson.addProperty("blurEffect", c.blurEffect);
                cosmeticJson.addProperty("shadow", c.shadow);
                cosmeticsJson.add(c.getName(), cosmeticJson);
            }
            json.add("cosmetics", cosmeticsJson);

            try (Writer writer = new FileWriter(configFile)) {
                gson.toJson(json, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        if (!configFile.exists()) return;

        try (Reader reader = new FileReader(configFile)) {
            JsonObject json = new JsonParser().parse(reader).getAsJsonObject();

            // ── Load Modules ──
            // Support both old format (flat) and new format (nested under "modules")
            JsonObject modulesJson = json.has("modules") ? json.getAsJsonObject("modules") : json;
            for (Module m : Antigravity.INSTANCE.getModuleManager().getModules()) {
                if (modulesJson.has(m.getName())) {
                    JsonObject moduleJson = modulesJson.getAsJsonObject(m.getName());
                    if (moduleJson.has("toggled")) m.setToggled(moduleJson.get("toggled").getAsBoolean());
                    if (moduleJson.has("key")) m.setKey(moduleJson.get("key").getAsInt());
                    if (moduleJson.has("hudX")) m.setHudX(moduleJson.get("hudX").getAsDouble());
                    if (moduleJson.has("hudY")) m.setHudY(moduleJson.get("hudY").getAsDouble());

                    if (moduleJson.has("settings")) {
                        JsonObject settingsJson = moduleJson.getAsJsonObject("settings");
                        for (Setting s : m.getSettings()) {
                            if (settingsJson.has(s.getName())) {
                                if (s instanceof BooleanSetting) ((BooleanSetting) s).setEnabled(settingsJson.get(s.getName()).getAsBoolean());
                                else if (s instanceof NumberSetting) ((NumberSetting) s).setValue(settingsJson.get(s.getName()).getAsDouble());
                            }
                        }
                    }
                }
            }

            // ── Load Cosmetics ──
            if (json.has("cosmetics")) {
                JsonObject cosmeticsJson = json.getAsJsonObject("cosmetics");
                for (Cosmetic c : Antigravity.INSTANCE.getCosmeticManager().getCosmetics()) {
                    if (cosmeticsJson.has(c.getName())) {
                        JsonObject cosmeticJson = cosmeticsJson.getAsJsonObject(c.getName());
                        if (cosmeticJson.has("enabled")) c.setEnabled(cosmeticJson.get("enabled").getAsBoolean());
                        if (cosmeticJson.has("showBackground")) c.showBackground = cosmeticJson.get("showBackground").getAsBoolean();
                        if (cosmeticJson.has("opacity")) c.opacity = cosmeticJson.get("opacity").getAsInt();
                        if (cosmeticJson.has("scale")) c.scale = cosmeticJson.get("scale").getAsFloat();
                        if (cosmeticJson.has("blurEffect")) c.blurEffect = cosmeticJson.get("blurEffect").getAsBoolean();
                        if (cosmeticJson.has("shadow")) c.shadow = cosmeticJson.get("shadow").getAsBoolean();

                        int bgR = cosmeticJson.has("bgColorR") ? cosmeticJson.get("bgColorR").getAsInt() : 0;
                        int bgG = cosmeticJson.has("bgColorG") ? cosmeticJson.get("bgColorG").getAsInt() : 0;
                        int bgB = cosmeticJson.has("bgColorB") ? cosmeticJson.get("bgColorB").getAsInt() : 0;
                        c.backgroundColor = new Color(bgR, bgG, bgB, c.opacity);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
