package com.example.cheatmod.client;

import com.example.cheatmod.cheats.CheatFeatures;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockEspRenderer {

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS && CheatFeatures.isBlockEspEnabled) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null || mc.player == null) return;

            PoseStack poseStack = event.getPoseStack();
            Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();

            poseStack.pushPose();
            poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

            VertexConsumer builder = mc.renderBuffers().bufferSource().getBuffer(RenderType.lines());

            int radius = 20;
            BlockPos playerPos = mc.player.blockPosition();

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos pos = playerPos.offset(x, y, z);
                        BlockState state = mc.level.getBlockState(pos);
                        String name = state.getBlock().getDescriptionId().toLowerCase();

                        if (name.contains("ore") || name.contains("ancient_debris") || name.contains("lava")) {
                            AABB box = new AABB(pos);
                            net.minecraft.client.renderer.LevelRenderer.renderLineBox(
                                    poseStack, builder, box, 1.0f, 0.0f, 0.0f, 1.0f
                            );
                        }
                    }
                }
            }
            poseStack.popPose();
        }
    }
}
