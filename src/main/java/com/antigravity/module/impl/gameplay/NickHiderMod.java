package com.antigravity.module.impl.gameplay;

import com.antigravity.module.Module;
import org.lwjgl.input.Keyboard;

public class NickHiderMod extends Module {

    public NickHiderMod() {
        super("Nick Hider", "Hides your name in the client.", Category.UTILITY, Keyboard.KEY_NONE);
    }
}
