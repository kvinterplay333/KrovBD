package com.example.cheatmod.cheats;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class StructureTeleportManager {
    public static BlockPos targetStructurePos = null;
    public static boolean hasStructureBox = false;

    public static void setStructure(BlockPos pos) {
        targetStructurePos = pos;
        hasStructureBox = true;
    }

    public static void teleportToStructure() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && targetStructurePos != null) {
            mc.player.teleportTo(targetStructurePos.getX() + 0.5, targetStructurePos.getY() + 1.0, targetStructurePos.getZ() + 0.5);
        }
    }

    @SubscribeEvent
    public static void onChatCommand(CommandEvent event) {
        String command = event.getParseResult().getReader().getString();
        if (command.equalsIgnoreCase("/y") || command.equalsIgnoreCase("y")) {
            targetStructurePos = null;
            hasStructureBox = false;
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§c[CheatMod] Структура и квадрат сброшены!"));
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS && hasStructureBox && targetStructurePos != null) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null) return;

            PoseStack poseStack = event.getPoseStack();
            Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();

            poseStack.pushPose();
            poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

            VertexConsumer builder = mc.renderBuffers().bufferSource().getBuffer(RenderType.lines());
            AABB box = new AABB(targetStructurePos.offset(-2, 0, -2), targetStructurePos.offset(3, 4, 3));

            net.minecraft.client.renderer.LevelRenderer.renderLineBox(poseStack, builder, box, 0.0f, 1.0f, 1.0f, 1.0f);

            poseStack.popPose();
        }
    }
}
