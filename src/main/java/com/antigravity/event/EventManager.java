package com.antigravity.event;

import com.antigravity.Antigravity;

import me.hobbyshop.lunar.ui.MainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class EventManager {

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        // Replace vanilla main menu with our custom Lunar menu
        if (event.gui instanceof GuiMainMenu) {
            event.gui = new MainMenu();
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            int key = Keyboard.getEventKey();

            // Handle Module Toggles
            Antigravity.INSTANCE.getModuleManager().handleKeyInput(key);

            // Handle Mod Menu (Right Shift by default)
            if (key == Keyboard.KEY_RSHIFT) {
                Minecraft.getMinecraft().displayGuiScreen(new com.antigravity.gui.voidui.VoidModMenuScreen());
            }
        }
    }
}
