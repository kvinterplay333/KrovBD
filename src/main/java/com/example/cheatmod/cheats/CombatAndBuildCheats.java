package com.example.cheatmod.cheats;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CombatAndBuildCheats {
    public static boolean isKillAuraEnabled = false;
    public static boolean isFastPlaceEnabled = false;
    public static double killAuraRange = 4.5;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player == Minecraft.getInstance().player) {
            Player player = event.player;

            if (isKillAuraEnabled) {
                runKillAura(player);
            }

            if (isFastPlaceEnabled) {
                Minecraft mc = Minecraft.getInstance();
                try {
                    var field = Minecraft.class.getDeclaredField("rightClickDelay");
                    field.setAccessible(true);
                    field.setInt(mc, 0);
                } catch (Exception ignored) {}
            }
        }
    }

    private static void runKillAura(Player player) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        Entity target = null;
        double closestDist = Double.MAX_VALUE;

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity == player) continue;
            if (entity instanceof LivingEntity living && living.isAlive() && !living.isInvulnerable()) {
                double dist = player.distanceToSqr(entity);
                if (dist < closestDist && dist <= (killAuraRange * killAuraRange)) {
                    closestDist = dist;
                    target = entity;
                }
            }
        }

        if (target != null) {
            faceEntityInstant(player, target);
            mc.gameMode.attack(player, target);
            player.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
        }
    }

    private static void faceEntityInstant(Player player, Entity target) {
        double diffX = target.getX() - player.getX();
        double diffZ = target.getZ() - player.getZ();
        double diffY = (target.getY() + target.getEyeHeight() * 0.5) - (player.getY() + player.getEyeHeight());
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);

        player.setYRot((float) (Math.atan2(diffZ, diffX) * (180 / Math.PI)) - 90.0f);
        player.setXRot((float) (-(Math.atan2(diffY, dist) * (180 / Math.PI))));
    }
}
