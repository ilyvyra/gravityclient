package com.antigravity;

import com.antigravity.event.EventManager;
import com.antigravity.module.ModuleManager;
import com.antigravity.config.ConfigManager;
import com.antigravity.cosmetics.CosmeticManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "antigravity", name = "Antigravity", version = "1.0", acceptedMinecraftVersions = "[1.8.9]")
public class Antigravity {

    @Mod.Instance("antigravity")
    public static Antigravity INSTANCE;

    private ModuleManager moduleManager;
    private ConfigManager configManager;
    private EventManager eventManager;
    private CosmeticManager cosmeticManager;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        moduleManager = new ModuleManager();
        configManager = new ConfigManager();
        eventManager = new EventManager();
        cosmeticManager = new CosmeticManager();

        MinecraftForge.EVENT_BUS.register(eventManager);

        configManager.load();
    }

    public void stop() {
        configManager.save();
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public CosmeticManager getCosmeticManager() {
        return cosmeticManager;
    }
}
