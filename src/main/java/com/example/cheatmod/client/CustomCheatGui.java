package com.example.cheatmod.client;

import com.example.cheatmod.cheats.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class CustomCheatGui extends Screen {

    public CustomCheatGui() {
        super(Component.literal("Cheat Menu"));
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fillGradient(0, 0, this.width, this.height, 0xC0101010, 0xD0000000);

        int panelW = 320;
        int panelH = 220;
        int startX = (this.width - panelW) / 2;
        int startY = (this.height - panelH) / 2;

        guiGraphics.fill(startX, startY, startX + panelW, startY + panelH, 0xFF181818);
        guiGraphics.renderOutline(startX, startY, panelW, panelH, 0xFF55FF55);

        guiGraphics.drawCenteredString(this.font, "§l[ ULTIMATE MULTIPLAYER CHEAT MENU ]", this.width / 2, startY + 12, 0x55FF55);

        drawToggle(guiGraphics, startX + 20, startY + 40, "KillAura", CombatAndBuildCheats.isKillAuraEnabled, mouseX, mouseY);
        drawToggle(guiGraphics, startX + 170, startY + 40, "FastPlace", CombatAndBuildCheats.isFastPlaceEnabled, mouseX, mouseY);

        drawToggle(guiGraphics, startX + 20, startY + 75, "Fullbright", FullbrightManager.isFullbrightEnabled, mouseX, mouseY);
        drawToggle(guiGraphics, startX + 170, startY + 75, "Block ESP", CheatFeatures.isBlockEspEnabled, mouseX, mouseY);

        drawToggle(guiGraphics, startX + 20, startY + 110, "Speed Boost", CheatFeatures.isSpeedEnabled, mouseX, mouseY);
        drawToggle(guiGraphics, startX + 170, startY + 110, "God / Buffs", CheatFeatures.isBuffsEnabled, mouseX, mouseY);

        drawBtn(guiGraphics, startX + 20, startY + 150, 135, 25, "Сохранить структуру", 0xFF3333FF, mouseX, mouseY);
        drawBtn(guiGraphics, startX + 165, startY + 150, 135, 25, "Телепорт к структуре", 0xFFFF5533, mouseX, mouseY);

        guiGraphics.drawCenteredString(this.font, "§7Напиши в чат §e/Y§7 для сброса структуры", this.width / 2, startY + 195, 0xAAAAAA);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void drawToggle(GuiGraphics gfx, int x, int y, String name, boolean state, int mx, int my) {
        int w = 130, h = 25;
        boolean hovered = mx >= x && mx <= x + w && my >= y && my <= y + h;
        gfx.fill(x, y, x + w, y + h, hovered ? 0xFF353535 : 0xFF222222);
        gfx.renderOutline(x, y, w, h, state ? 0xFF00AA00 : 0xFF555555);
        gfx.drawString(this.font, name, x + 8, y + 8, 0xFFFFFFFF, false);
        gfx.drawString(this.font, state ? "ON" : "OFF", x + w - 30, y + 8, state ? 0xFF55FF55 : 0xFFFF5555, true);
    }

    private void drawBtn(GuiGraphics gfx, int x, int y, int w, int h, String text, int color, int mx, int my) {
        boolean hovered = mx >= x && mx <= x + w && my >= y && my <= y + h;
        gfx.fill(x, y, x + w, y + h, hovered ? 0xFF444444 : 0xFF2A2A2A);
        gfx.renderOutline(x, y, w, h, color);
        gfx.drawCenteredString(this.font, text, x + w / 2, y + 8, 0xFFFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int panelW = 320, panelH = 220;
        int startX = (this.width - panelW) / 2;
        int startY = (this.height - panelH) / 2;

        if (isInside(mouseX, mouseY, startX + 20, startY + 40, 130, 25)) CombatAndBuildCheats.isKillAuraEnabled = !CombatAndBuildCheats.isKillAuraEnabled;
        else if (isInside(mouseX, mouseY, startX + 170, startY + 40, 130, 25)) CombatAndBuildCheats.isFastPlaceEnabled = !CombatAndBuildCheats.isFastPlaceEnabled;
        else if (isInside(mouseX, mouseY, startX + 20, startY + 75, 130, 25)) FullbrightManager.toggleFullbright();
        else if (isInside(mouseX, mouseY, startX + 170, startY + 75, 130, 25)) CheatFeatures.isBlockEspEnabled = !CheatFeatures.isBlockEspEnabled;
        else if (isInside(mouseX, mouseY, startX + 20, startY + 110, 130, 25)) CheatFeatures.isSpeedEnabled = !CheatFeatures.isSpeedEnabled;
        else if (isInside(mouseX, mouseY, startX + 170, startY + 110, 130, 25)) CheatFeatures.isBuffsEnabled = !CheatFeatures.isBuffsEnabled;
        else if (isInside(mouseX, mouseY, startX + 20, startY + 150, 135, 25)) {
            if (Minecraft.getInstance().player != null) {
                StructureTeleportManager.setStructure(Minecraft.getInstance().player.blockPosition());
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("§a[CheatMod] Структура сохранена!"));
            }
        } else if (isInside(mouseX, mouseY, startX + 165, startY + 150, 135, 25)) {
            StructureTeleportManager.teleportToStructure();
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isInside(double mx, double my, int x, int y, int w, int h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
