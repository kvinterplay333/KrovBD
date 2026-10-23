package com.example.cheatmod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyInit {
    public static final String CATEGORY = "key.categories.cheatmod";
    public static final String OPEN_GUI = "key.cheatmod.open_gui";

    public static final KeyMapping OPEN_GUI_KEY = new KeyMapping(
            OPEN_GUI,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_F8, // Открытие на F8
            CATEGORY
    );

    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_GUI_KEY);
    }
}
