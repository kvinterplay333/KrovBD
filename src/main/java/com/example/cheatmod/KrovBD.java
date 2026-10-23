package com.example.cheatmod;

import com.example.cheatmod.client.KeyInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CheatMod.MOD_ID)
public class CheatMod {
    public static final String MOD_ID = "cheatmod";

    public CheatMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        // Регистрация клавиш
        modEventBus.addListener(KeyInit::registerBindings);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
