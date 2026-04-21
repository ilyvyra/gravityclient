package com.antigravity.cosmetics;

import com.antigravity.cosmetics.impl.*;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CosmeticManager {
    
    private final List<Cosmetic> cosmetics = new ArrayList<>();

    public CosmeticManager() {
        // Register built-in cosmetics
        register(new WingsCosmetic());
        register(new HaloCosmetic());
        register(new CrownCosmetic());
        register(new BandanaCosmetic());
        
        MinecraftForge.EVENT_BUS.register(new CosmeticRenderHandler(this));
    }

    public void register(Cosmetic cosmetic) {
        cosmetics.add(cosmetic);
    }

    public List<Cosmetic> getCosmetics() {
        return cosmetics;
    }

    public List<Cosmetic> getCosmeticsByCategory(Cosmetic.Category category) {
        return cosmetics.stream()
                .filter(c -> c.getCategory() == category)
                .collect(Collectors.toList());
    }

    public Cosmetic getCosmeticByName(String name) {
        return cosmetics.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
