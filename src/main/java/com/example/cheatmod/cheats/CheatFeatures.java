package com.example.cheatmod.cheats;

import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CheatFeatures {
    public static boolean isSpeedEnabled = false;
    public static boolean isBuffsEnabled = false;
    public static boolean isBlockEspEnabled = false;

    public static int speedLevel = 3;
    public static int strengthLevel = 10; // Сила X
    public static int regenLevel = 4;     // Регенерация IV
    public static int hasteLevel = 7;     // Спешка VII

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player == Minecraft.getInstance().player) {
            Player player = event.player;

            if (isBuffsEnabled) {
                if (hasteLevel > 0)
                    player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 220, hasteLevel - 1, false, false, false));
                if (strengthLevel > 0)
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 220, strengthLevel - 1, false, false, false));
                if (regenLevel > 0)
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 220, regenLevel - 1, false, false, false));
            }

            if (isSpeedEnabled && speedLevel > 0) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 220, speedLevel - 1, false, false, false));
            }
        }
    }
}
