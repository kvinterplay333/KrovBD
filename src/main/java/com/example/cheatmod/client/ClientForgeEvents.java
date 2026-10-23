package com.example.cheatmod.client;

import com.example.cheatmod.KrovBD;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KrovBD.MOD_ID)
public class ClientForgeEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyInit.OPEN_GUI_KEY.consumeClick()) {
            Minecraft.getInstance().setScreen(new CustomCheatGui());
        }
    }
}
