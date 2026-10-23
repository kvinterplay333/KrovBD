package com.example.cheatmod.cheats;

import net.minecraft.client.Minecraft;

public class FullbrightManager {
    public static boolean isFullbrightEnabled = false;
    private static double oldGamma = 1.0;

    public static void toggleFullbright() {
        Minecraft mc = Minecraft.getInstance();
        isFullbrightEnabled = !isFullbrightEnabled;
        
        if (isFullbrightEnabled) {
            oldGamma = mc.options.gamma().get();
            mc.options.gamma().set(12.0D);
        } else {
            mc.options.gamma().set(oldGamma);
        }
    }
}
