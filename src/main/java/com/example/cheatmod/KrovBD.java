package com.example.cheatmod;

import com.example.cheatmod.client.KeyInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KrovBD.MOD_ID)
public class KrovBD {
    public static final String MOD_ID = "krovbd";

    public KrovBD() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(KeyInit::registerBindings);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
