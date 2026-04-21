package com.antigravity.gui.modmenu;

import com.antigravity.Antigravity;
import com.antigravity.module.Module;
import com.antigravity.utils.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

/**
 * Unified Antigravity Client GUI
 * Inspired by Vape Lite sidebar/list design.
 */
public class ModMenuScreen extends GuiScreen {

    private final GuiScreen parentScreen;

    // --- Tabs / Categories ---
    private Module.Category activeCategory = Module.Category.COMBAT;
    private final List<Module.Category> sidebarCategories = java.util.Arrays.asList(
        Module.Category.FAVORITES,
        Module.Category.COMBAT,
        Module.Category.RENDER,
        Module.Category.UTILITY,
        Module.Category.MOVEMENT
    );

    // --- Scrolling ---
    private float scrollOffset = 0;
    private float targetScrollOffset = 0;
    private float maxScroll = 0;

    // --- Animation Map ---
    private final java.util.Map<String, Float> toggleAnimations = new java.util.HashMap<>();

    // --- Settings Popup State ---
    private boolean settingsPopupOpen = false;
    private String settingsPopupTarget = null;
    private int popupX, popupY, popupW, popupH;
    private String draggingSlider = null;

    // --- Layout Constants ---
    private static final int WINDOW_WIDTH = 480;
    private static final int WINDOW_HEIGHT = 320;
    private static final int SIDEBAR_WIDTH = 90;
    private static final int TOP_NAV_HEIGHT = 45;
    private static final int LIST_ITEM_HEIGHT = 46;
    private static final int LIST_ITEM_GAP = 2;

    public ModMenuScreen(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        super.initGui();
        scrollOffset = 0;
        targetScrollOffset = 0;
        settingsPopupOpen = false;
        RenderUtils.loadBlurShader(mc);
    }

    @Override
    public void onGuiClosed() {
        RenderUtils.unloadBlurShader(mc);
        super.onGuiClosed();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtils.drawRect(0, 0, width, height, new Color(0, 0, 0, 110).getRGB());

        scrollOffset += (targetScrollOffset - scrollOffset) * 0.3f;

        int mx = width / 2 - WINDOW_WIDTH / 2;
        int my = height / 2 - WINDOW_HEIGHT / 2;

        // Main Window
        RenderUtils.drawRoundedRect(mx, my, WINDOW_WIDTH, WINDOW_HEIGHT, 4, new Color(24, 24, 27, 255).getRGB());
        
        drawSidebar(mx, my, mouseX, mouseY);
        drawTopNav(mx, my, mouseX, mouseY);
        drawModuleList(mx, my, mouseX, mouseY);

        if (settingsPopupOpen) {
            drawSettingsPopup(mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawSidebar(int mx, int my, int mouseX, int mouseY) {
        RenderUtils.drawRect(mx, my, SIDEBAR_WIDTH, WINDOW_HEIGHT, new Color(20, 20, 22, 255).getRGB());

        // Home Header
        mc.fontRendererObj.drawStringWithShadow("\u2302", mx + 16, my + 14, -1);
        mc.fontRendererObj.drawStringWithShadow("Home", mx + 36, my + 14, -1);

        int catY = my + 60;
        for (Module.Category cat : sidebarCategories) {
            boolean active = (activeCategory == cat);
            boolean hovered = mouseX >= mx && mouseX <= mx + SIDEBAR_WIDTH && mouseY >= catY && mouseY <= catY + 28;

            int color = active ? -1 : (hovered ? new Color(200, 200, 200).getRGB() : new Color(120, 120, 120).getRGB());
            mc.fontRendererObj.drawStringWithShadow(cat.getDisplayName(), mx + 16, catY + 6, color);

            if (active) {
                RenderUtils.drawRect(mx + 10, catY + 12, 2, 2, new Color(45, 120, 240).getRGB());
            }
            catY += 28;
        }

        mc.fontRendererObj.drawStringWithShadow("ANTIGRAVITY", mx + 10, my + WINDOW_HEIGHT - 18, new Color(80, 80, 80).getRGB());
    }

    private void drawTopNav(int mx, int my, int mouseX, int mouseY) {
        int iconX = mx + WINDOW_WIDTH - 30;
        String[] icons = { "\u2699", "\u21bb", "\u22EE" }; 
        for (String icon : icons) {
            mc.fontRendererObj.drawStringWithShadow(icon, iconX, my + 14, new Color(120, 120, 120).getRGB());
            iconX -= 25;
        }
    }

    private void drawModuleList(int mx, int my, int mouseX, int mouseY) {
        int listX = mx + SIDEBAR_WIDTH + 10;
        int listY = my + TOP_NAV_HEIGHT;
        int listWidth = WINDOW_WIDTH - SIDEBAR_WIDTH - 20;
        int listHeight = WINDOW_HEIGHT - TOP_NAV_HEIGHT - 10;

        enableScissor(listX, listY, listWidth, listHeight);

        List<Module> modules = (activeCategory == Module.Category.FAVORITES) 
            ? Antigravity.INSTANCE.getModuleManager().getFavoriteModules()
            : Antigravity.INSTANCE.getModuleManager().getModulesByCategory(activeCategory);

        int currentY = listY - (int) scrollOffset;
        for (Module module : modules) {
            drawModuleRow(mouseX, mouseY, listX, currentY, listWidth, module);
            currentY += LIST_ITEM_HEIGHT + LIST_ITEM_GAP;
        }

        disableScissor();
        maxScroll = Math.max(0, (modules.size() * (LIST_ITEM_HEIGHT + LIST_ITEM_GAP)) - listHeight);
    }

    private void drawModuleRow(int mouseX, int mouseY, int x, int y, int w, Module module) {
        boolean hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + LIST_ITEM_HEIGHT;
        
        Color bgColor = module.isToggled() ? new Color(45, 120, 240, 255) : new Color(34, 34, 38, 255);
        if (hovered && !module.isToggled()) bgColor = new Color(40, 40, 45, 255);
        
        RenderUtils.drawRoundedRect(x, y, w, LIST_ITEM_HEIGHT, 4, bgColor.getRGB());

        // Icon
        int iconX = x + 8;
        int iconY = y + (LIST_ITEM_HEIGHT - 30) / 2;
        if (module.isToggled()) {
            RenderUtils.drawRect(x, y, 40, LIST_ITEM_HEIGHT, new Color(55, 130, 255).getRGB());
        }
        mc.fontRendererObj.drawStringWithShadow(module.getCategory().getIconChar(), iconX + 10, iconY + 10, -1);

        // Text
        mc.fontRendererObj.drawStringWithShadow(module.getName(), x + 70, y + 10, -1);
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.8f, 0.8f, 1f);
        mc.fontRendererObj.drawString(module.getSummary(), (int)((x + 70) / 0.8f), (int)((y + 24) / 0.8f), new Color(130, 130, 130).getRGB());
        GlStateManager.popMatrix();

        drawToggleSwitch(x + w - 35, y + 15, module.isToggled(), module.getName());
        mc.fontRendererObj.drawStringWithShadow("\u22EE", x + w - 12, y + 14, new Color(100, 100, 100).getRGB());
    }

    private void drawToggleSwitch(int x, int y, boolean enabled, String key) {
        float target = enabled ? 1f : 0f;
        float current = toggleAnimations.getOrDefault(key, enabled ? 1f : 0f);
        current += (target - current) * 0.25f;
        toggleAnimations.put(key, current);

        int trackW = 18;
        int trackH = 8;
        Color trackBg = interpolateColor(new Color(50, 50, 55), new Color(100, 80, 200), current);
        RenderUtils.drawRoundedRect(x, y, trackW, trackH, 4, trackBg.getRGB());

        int knobSize = 6;
        int knobX = x + 1 + (int) ((trackW - knobSize - 2) * current);
        RenderUtils.drawRoundedRect(knobX, y + 1, knobSize, knobSize, 3, new Color(240, 240, 240).getRGB());
    }

    private void drawSettingsPopup(int mouseX, int mouseY) {
        RenderUtils.drawRect(0, 0, width, height, new Color(0, 0, 0, 150).getRGB());

        popupW = 200;
        popupX = width / 2 - popupW / 2;
        popupY = height / 2 - 100;

        Module module = Antigravity.INSTANCE.getModuleManager().getModuleByName(settingsPopupTarget);
        if (module == null) { settingsPopupOpen = false; return; }

        List<com.antigravity.setting.Setting> settings = module.getSettings();
        popupH = 40 + (settings.size() * 22);
        
        RenderUtils.drawRoundedRect(popupX, popupY, popupW, popupH, 6, new Color(20, 20, 22, 255).getRGB());
        RenderUtils.drawRect(popupX + 10, popupY + 30, popupW - 20, 1, new Color(255, 255, 255, 10).getRGB());

        mc.fontRendererObj.drawStringWithShadow(module.getName() + " Settings", popupX + 12, popupY + 12, -1);

        int yOff = popupY + 40;
        int leftMargin = popupX + 12;
        int controlX = popupX + popupW - 50;

        for (com.antigravity.setting.Setting s : settings) {
            mc.fontRendererObj.drawStringWithShadow(s.getName(), leftMargin, yOff + 2, new Color(160, 160, 160).getRGB());

            if (s instanceof com.antigravity.setting.impl.BooleanSetting) {
                drawMiniToggle(controlX + 20, yOff, ((com.antigravity.setting.impl.BooleanSetting)s).isEnabled(), s.getName());
            } else if (s instanceof com.antigravity.setting.impl.NumberSetting) {
                com.antigravity.setting.impl.NumberSetting num = (com.antigravity.setting.impl.NumberSetting)s;
                float pct = (float)((num.getValue() - num.getMinimum()) / (num.getMaximum() - num.getMinimum()));
                drawSlider(controlX - 25, yOff + 1, 60, pct, s.getName(), mouseX, mouseY);
            } else if (s instanceof com.antigravity.setting.impl.ModeSetting) {
                com.antigravity.setting.impl.ModeSetting mode = (com.antigravity.setting.impl.ModeSetting)s;
                mc.fontRendererObj.drawStringWithShadow(mode.getActiveMode(), controlX, yOff + 2, new Color(130, 100, 255).getRGB());
            }
            yOff += 22;
        }
    }

    private void drawMiniToggle(int x, int y, boolean enabled, String key) {
        float target = enabled ? 1f : 0f;
        float current = toggleAnimations.getOrDefault(key, enabled ? 1f : 0f);
        current += (target - current) * 0.25f;
        toggleAnimations.put(key, current);

        int trackW = 16;
        int trackH = 8;
        Color trackBg = interpolateColor(new Color(50, 50, 55), new Color(100, 80, 200), current);
        RenderUtils.drawRoundedRect(x, y + 1, trackW, trackH, 4, trackBg.getRGB());

        int knobSize = 6;
        int knobX = x + 1 + (int) ((trackW - knobSize - 2) * current);
        RenderUtils.drawRoundedRect(knobX, y + 2, knobSize, knobSize, 3, new Color(240, 240, 240).getRGB());
    }

    private void drawSlider(int x, int y, int sliderWidth, float value, String key, int mouseX, int mouseY) {
        RenderUtils.drawRoundedRect(x, y + 3, sliderWidth, 4, 2, new Color(40, 40, 45).getRGB());
        int filled = (int) (sliderWidth * value);
        if (filled > 0) RenderUtils.drawRoundedRect(x, y + 3, filled, 4, 2, new Color(45, 120, 240).getRGB());

        int knobX = x + filled - 3;
        RenderUtils.drawRoundedRect(knobX, y, 6, 10, 3, new Color(220, 220, 230).getRGB());

        if (draggingSlider != null && draggingSlider.equals(key)) {
            float newVal = Math.max(0f, Math.min(1f, (mouseX - x) / (float) sliderWidth));
            applySliderValue(key, newVal);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton != 0) { super.mouseClicked(mouseX, mouseY, mouseButton); return; }

        if (settingsPopupOpen) {
            handleSettingsPopupClick(mouseX, mouseY);
            return;
        }

        int mx = width / 2 - WINDOW_WIDTH / 2;
        int my = height / 2 - WINDOW_HEIGHT / 2;

        int catY = my + 60;
        for (Module.Category cat : sidebarCategories) {
            if (mouseX >= mx && mouseX <= mx + SIDEBAR_WIDTH && mouseY >= catY && mouseY <= catY + 28) {
                activeCategory = cat;
                scrollOffset = 0; targetScrollOffset = 0;
                mc.thePlayer.playSound("gui.button.press", 1, 1);
                return;
            }
            catY += 28;
        }

        if (mouseX > mx + SIDEBAR_WIDTH && mouseY > my + TOP_NAV_HEIGHT) {
            handleListClick(mx, my, mouseX, mouseY);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void handleListClick(int mx, int my, int mouseX, int mouseY) {
        int listX = mx + SIDEBAR_WIDTH + 10;
        int listY = my + TOP_NAV_HEIGHT;
        int listWidth = WINDOW_WIDTH - SIDEBAR_WIDTH - 20;

        List<Module> modules = (activeCategory == Module.Category.FAVORITES) 
            ? Antigravity.INSTANCE.getModuleManager().getFavoriteModules()
            : Antigravity.INSTANCE.getModuleManager().getModulesByCategory(activeCategory);

        int currentY = listY - (int) scrollOffset;
        for (Module module : modules) {
            if (mouseX >= listX && mouseX <= listX + listWidth && mouseY >= currentY && mouseY <= currentY + LIST_ITEM_HEIGHT) {
                if (mouseX >= listX + listWidth - 15) {
                    settingsPopupOpen = true;
                    settingsPopupTarget = module.getName();
                    draggingSlider = null;
                    return;
                }
                module.toggle();
                mc.thePlayer.playSound("gui.button.press", 1, 1);
                return;
            }
            currentY += LIST_ITEM_HEIGHT + LIST_ITEM_GAP;
        }
    }

    private void handleSettingsPopupClick(int mouseX, int mouseY) {
        if (mouseX < popupX || mouseX > popupX + popupW || mouseY < popupY || mouseY > popupY + popupH) {
            settingsPopupOpen = false;
            settingsPopupTarget = null;
            return;
        }

        Module module = Antigravity.INSTANCE.getModuleManager().getModuleByName(settingsPopupTarget);
        if (module == null) return;

        int yOff = popupY + 40;
        int controlX = popupX + popupW - 50;
        
        for (com.antigravity.setting.Setting s : module.getSettings()) {
            if (s instanceof com.antigravity.setting.impl.BooleanSetting) {
                if (mouseX >= controlX + 20 && mouseX <= controlX + 36 && mouseY >= yOff && mouseY <= yOff + 12) {
                    ((com.antigravity.setting.impl.BooleanSetting)s).setEnabled(!((com.antigravity.setting.impl.BooleanSetting)s).isEnabled());
                    mc.thePlayer.playSound("gui.button.press", 1, 1);
                    return;
                }
            } else if (s instanceof com.antigravity.setting.impl.ModeSetting) {
                if (mouseX >= controlX && mouseX <= controlX + 40 && mouseY >= yOff && mouseY <= yOff + 12) {
                    ((com.antigravity.setting.impl.ModeSetting)s).cycle();
                    mc.thePlayer.playSound("gui.button.press", 1, 1);
                    return;
                }
            } else if (s instanceof com.antigravity.setting.impl.NumberSetting) {
                if (mouseX >= controlX - 25 && mouseX <= controlX + 35 && mouseY >= yOff && mouseY <= yOff + 10) {
                    draggingSlider = s.getName();
                    return;
                }
            }
            yOff += 22;
        }
    }

    private void applySliderValue(String key, float pct) {
        Module module = Antigravity.INSTANCE.getModuleManager().getModuleByName(settingsPopupTarget);
        if (module == null) return;

        for (com.antigravity.setting.Setting s : module.getSettings()) {
            if (s.getName().equals(key) && s instanceof com.antigravity.setting.impl.NumberSetting) {
                com.antigravity.setting.impl.NumberSetting num = (com.antigravity.setting.impl.NumberSetting)s;
                double val = num.getMinimum() + (num.getMaximum() - num.getMinimum()) * pct;
                num.setValue(val);
                return;
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (settingsPopupOpen && draggingSlider != null) {
            int sliderX = popupX + popupW - 50 - 25;
            float newVal = Math.max(0f, Math.min(1f, (mouseX - sliderX) / 60f));
            applySliderValue(draggingSlider, newVal);
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        draggingSlider = null;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int scroll = Mouse.getEventDWheel();
        if (scroll != 0) {
            targetScrollOffset -= (scroll / 4f);
            targetScrollOffset = Math.max(0, Math.min(maxScroll, targetScrollOffset));
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) { // Escape
            if (settingsPopupOpen) {
                settingsPopupOpen = false;
                settingsPopupTarget = null;
            } else {
                mc.displayGuiScreen(parentScreen);
            }
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    private Color interpolateColor(Color c1, Color c2, float factor) {
        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * factor);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * factor);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * factor);
        return new Color(Math.max(0, Math.min(255, r)), Math.max(0, Math.min(255, g)), Math.max(0, Math.min(255, b)));
    }

    private void enableScissor(int x, int y, int w, int h) {
        int scaleFactor = new net.minecraft.client.gui.ScaledResolution(mc).getScaleFactor();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(x * scaleFactor, mc.displayHeight - (y + h) * scaleFactor, w * scaleFactor, h * scaleFactor);
    }

    private void disableScissor() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
