package com.benbenlaw.strainers.screen.custom;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.network.packet.ChangeDropConfigScrollOffsetPacket;
import com.benbenlaw.strainers.network.packet.ReturnToStrainerPacket;
import com.benbenlaw.strainers.util.MousePositionManagerUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class StrainerDropConfigScreen extends AbstractContainerScreen<StrainerDropConfigMenu> {

    private static final Identifier TEXTURE = Strainers.identifier("textures/gui/strainer_config_gui.png");
    private static final Identifier SCROLL_ICON = Strainers.identifier("scroll");
    private static final Identifier BACK_ICON = Strainers.identifier("back_icon");

    private boolean isDraggingScrollbar = false;

    public StrainerDropConfigScreen(StrainerDropConfigMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        MousePositionManagerUtil.setLastKnownPosition();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        for (int i = 0; i < menu.getVisibleOutputCount(); i++) {
            Slot slot = menu.getDisplaySlot(i);
            int color = menu.isRemoved(i) ? 0x90FF0000 : 0x9000FF00;
            guiGraphics.fill(x + slot.x, y + slot.y, x + slot.x + 16, y + slot.y + 16, color);
        }

        int max = menu.getMaxScroll();
        float scroll = max == 0 ? 0 : ((float) menu.getScrollOffset() / max);
        int barY = y + 17 + (int) (scroll * 37);

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SCROLL_ICON, 12, 15, 0, 0, x + 154, barY, 12, 15);

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BACK_ICON, 10, 10, 0, 0, x + 161, y + 5, 10, 10);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int max = menu.getMaxScroll();
        if (max <= 0) return false;

        int next = menu.getScrollOffset() - (int) Math.signum(scrollY);
        next = Math.max(0, Math.min(max, next));

        menu.setScrollOffset(next);
        ClientPacketDistributor.sendToServer(new ChangeDropConfigScrollOffsetPacket(menu.containerId, next));

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

        int backX = leftPos + 161;
        int backY = topPos + 5;

        if (event.button() == 0 &&
                event.x() >= backX && event.x() < backX + 10 &&
                event.y() >= backY && event.y() < backY + 10) {
            ClientPacketDistributor.sendToServer(new ReturnToStrainerPacket(menu.getBlockPos()));
            return true;
        }

        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public void onClose() {
        MousePositionManagerUtil.clear();
        super.onClose();
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

    private void updateScrollFromMouse(int mouseY) {
        int top = topPos + 17 + 7;
        int bottom = topPos + 17 + 37 + 7;
        float ratio = (float) (mouseY - top) / (bottom - top);
        ratio = Math.max(0, Math.min(1, ratio));

        int max = menu.getMaxScroll();
        int next = Math.round(ratio * max);

        menu.setScrollOffset(next);
        ClientPacketDistributor.sendToServer(new ChangeDropConfigScrollOffsetPacket(menu.containerId, next));
    }
}