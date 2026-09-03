package com.benbenlaw.strainers.screen.custom;

import com.benbenlaw.core.Core;
import com.benbenlaw.core.config.StartupConfig;
import com.benbenlaw.core.screen.util.DurationTooltip;
import com.benbenlaw.core.screen.util.FluidRenderingUtils;
import com.benbenlaw.core.util.MouseUtil;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.config.StrainersConfig;
import com.benbenlaw.strainers.network.packet.ChangeScrollOffsetPacket;
import com.benbenlaw.strainers.network.packet.RequestDropConfigPacket;
import com.benbenlaw.strainers.util.MousePositionManagerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class StrainerScreen extends AbstractContainerScreen<StrainerMenu> {

    private static final Identifier TEXTURE = Strainers.identifier("textures/gui/strainer_gui.png");
    private static final Identifier PROGRESS_ARROW = Core.identifier("progress_arrow");
    private static final Identifier BUCKET_ICON = Strainers.identifier("bucket_icon");
    private static final Identifier CONFIG_ICON = Strainers.identifier("config_icon");
    private static final Identifier SCROLL_ICON = Strainers.identifier("scroll");

    private boolean isDraggingScrollbar = false;

    public StrainerScreen(StrainerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        if (MousePositionManagerUtil.lastMouseX != -1) {
            MousePositionManagerUtil.setLastKnownPosition();
        }
    }


    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        if (menu.isCrafting()) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESS_ARROW, 24, 16, 0, 0,x + 31, y + 35, menu.getScaledProgress() + 1, 16);
        }

        float max = menu.getMaxScroll();
        float scroll = max == 0 ? 0 : ((float) menu.getScrollOffset() / max);

        int barY = y + 17 + (int)(scroll * 37);

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SCROLL_ICON, 12, 15, 0, 0, x + 154, barY, 12, 15);

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CONFIG_ICON, 10, 10, 0, 0, x + 139, y + 5, 10, 10);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        DurationTooltip.renderDurationTooltip(guiGraphics, mouseX, mouseY, x, y, 161, 5, menu.data.get(0), menu.data.get(1));
        renderTotalFluidAmountTooltip(guiGraphics, mouseX, mouseY, x, y, 150, 5, menu.data.get(2));

        FluidRenderingUtils.renderFluid(guiGraphics, menu.blockEntity.getFluidHandler(), 0, x, y, 8, 17, 16, 16,
                mouseX, mouseY, Component.translatable("tooltip.strainers.empty")
        );

        if (MouseUtil.isMouseAboveArea(mouseX, mouseY, x, y, 139, 5, 10, 10)) {
            guiGraphics.setTooltipForNextFrame(Minecraft.getInstance().font,
                    Component.translatable("tooltip.strainers.configure_drops"), mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int max = menu.getMaxScroll();
        if (max <= 0) return false;

        int next = menu.getScrollOffset() - (int) Math.signum(scrollY);
        next = Math.max(0, Math.min(max, next));

        menu.setScrollOffset(next);

        ClientPacketDistributor.sendToServer(new ChangeScrollOffsetPacket(menu.containerId, next));

        return true;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {

        MousePositionManagerUtil.getLastKnownPosition();

        int x = leftPos + 154;
        int y = topPos + 17;

        if (event.button() == 0 &&
                event.x() >= x && event.x() < x + 12 &&
                event.y() >= y && event.y() < y + 52) {
            isDraggingScrollbar = true;
            updateScrollFromMouse((int) event.y());
            return true;
        }

        int configX = leftPos + 139;
        int configY = topPos + 5;

        if (event.button() == 0 &&
                event.x() >= configX && event.x() < configX + 10 &&
                event.y() >= configY && event.y() < configY + 10) {
            ClientPacketDistributor.sendToServer(new RequestDropConfigPacket(menu.blockPos));
            return true;
        }

        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (event.button() == 0) isDraggingScrollbar = false;
        return super.mouseReleased(event);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        if (isDraggingScrollbar) {
            updateScrollFromMouse((int) event.y());
            return true;
        }
        return super.mouseDragged(event, dx, dy);
    }

    @Override
    public void onClose() {
        MousePositionManagerUtil.clear();
        super.onClose();
    }

    private void updateScrollFromMouse(int mouseY) {
        int top = topPos + 17 + 7;
        int bottom = topPos + 17 + 37 + 7;
        float ratio = (float)(mouseY - top) / (bottom - top);
        ratio = Math.max(0, Math.min(1, ratio));

        int max = menu.getMaxScroll();
        int next = Math.round(ratio * max);

        menu.setScrollOffset(next);
        ClientPacketDistributor.sendToServer(new ChangeScrollOffsetPacket(menu.containerId, next));
    }

    public static void renderTotalFluidAmountTooltip(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, int x, int y, int xOffset, int yOffset, int fluidAmount) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BUCKET_ICON, 10, 10, 0, 0, x + xOffset, y + yOffset, 10, 10);
        if (MouseUtil.isMouseAboveArea(mouseX, mouseY, x, y, xOffset, yOffset, 10, 10)) {
            Component fluidInfo = StrainersConfig.STRAINERS_CONSUME_FLUID.get()
                    ? Component.translatable("jei.strainers.fluid_amount_consume", fluidAmount)
                    : Component.translatable("jei.strainers.fluid_amount_no_consume", fluidAmount);
            guiGraphics.setTooltipForNextFrame(Minecraft.getInstance().font, fluidInfo, mouseX, mouseY);
        }
    }
}