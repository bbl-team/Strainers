package com.benbenlaw.strainers.screen.custom;

import com.benbenlaw.core.util.MouseUtil;
import com.benbenlaw.strainers.Strainers;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class WoodenStrainerScreen extends AbstractContainerScreen<WoodenStrainerMenu> {

    Level level;
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "textures/gui/wooden_strainer_gui.png");

    public WoodenStrainerScreen(WoodenStrainerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.level = pMenu.level;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

        if (menu.isCrafting()) {
            int l = this.menu.getScaledProgress()   ;
            guiGraphics.blit(TEXTURE, x + 40, y + 26, 176, 14, menu.getScaledProgress() + 1, 16);
        }


    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
        renderUpgradeSlotInformation(guiGraphics, mouseX, mouseY, x, y);

    }

    private void renderUpgradeSlotInformation (GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {

        if (MouseUtil.isMouseAboveArea(mouseX, mouseY, x, y, 8, 17, 16, 16)) {
            if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && !this.hoveredSlot.hasItem()) {
                guiGraphics.renderTooltip(this.font, Component.translatable("block.gui.mesh"), mouseX, mouseY);
            }
        }

        if (MouseUtil.isMouseAboveArea(mouseX, mouseY, x, y, 8, 35, 16, 16)) {
            if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && !this.hoveredSlot.hasItem()) {
                guiGraphics.renderTooltip(this.font, Component.translatable("block.gui.input_item"), mouseX, mouseY);
            }
        }

        if (MouseUtil.isMouseAboveArea(mouseX, mouseY, x, y, 17, 55, 16, 16)) {
            if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && !this.hoveredSlot.hasItem()) {
                guiGraphics.renderTooltip(this.font, Component.translatable("block.gui.speed_upgrade"), mouseX, mouseY);
            }
            String ticks = String.valueOf(this.menu.blockEntity.maxProgress);
            guiGraphics.renderTooltip(this.font,  Component.literal(ticks+ " ticks"), this.leftPos,
                    this.topPos);
        }

        else if (MouseUtil.isMouseAboveArea(mouseX, mouseY, x, y, 35, 55, 16, 16)) {
            if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && !this.hoveredSlot.hasItem()) {
                guiGraphics.renderTooltip(this.font, Component.translatable("block.gui.mesh_upgrade"), mouseX, mouseY);
            }
            String meshDamage = String.valueOf(this.menu.blockEntity.meshDamageChance * 100);
            guiGraphics.renderTooltip(this.font,  Component.literal("Mesh damage chance: " + meshDamage + " %"), this.leftPos,
                    this.topPos);
        }

        else if (MouseUtil.isMouseAboveArea(mouseX, mouseY, x, y, 53, 55, 16, 16)) {
            if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && !this.hoveredSlot.hasItem()) {
                guiGraphics.renderTooltip(this.font, Component.translatable("block.gui.output_upgrade"), mouseX, mouseY);
            }
            double outputChance = this.menu.blockEntity.outputChanceIncrease;
            guiGraphics.renderTooltip(this.font,  Component.literal(outputChance + " added to recipe output chance"), this.leftPos,
                    this.topPos);
        }
    }


}
