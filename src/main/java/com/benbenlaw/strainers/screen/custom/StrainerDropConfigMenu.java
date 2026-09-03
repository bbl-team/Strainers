package com.benbenlaw.strainers.screen.custom;

import com.benbenlaw.core.screen.SimpleAbstractContainerMenu;
import com.benbenlaw.strainers.block.entity.StrainerBlockEntity;
import com.benbenlaw.strainers.screen.StrainersMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StrainerDropConfigMenu extends SimpleAbstractContainerMenu {

    private static final int COLUMNS = 8;
    public static final int VISIBLE_ROWS = 3;
    public static final int VISIBLE_SLOTS = COLUMNS * VISIBLE_ROWS;
    private static final int DISPLAY_SLOT_START = 36;

    private static final StreamCodec<RegistryFriendlyByteBuf, List<ItemStack>> OUTPUTS_CODEC =
            ByteBufCodecs.collection(ArrayList::new, ItemStack.STREAM_CODEC);

    private final StrainerBlockEntity blockEntity;
    private final BlockPos blockPos;
    private final List<ItemStack> possibleOutputs;
    private int scrollOffset = 0;

    public StrainerDropConfigMenu(int containerID, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerID, inventory, extraData.readBlockPos(),
                OUTPUTS_CODEC.decode((RegistryFriendlyByteBuf) extraData));
    }

    public StrainerDropConfigMenu(int containerID, Inventory inventory, BlockPos blockPos, List<ItemStack> allOutputs) {
        super(StrainersMenuTypes.STRAINER_DROP_CONFIG_MENU.get(), containerID, inventory, blockPos, VISIBLE_SLOTS);
        this.blockPos = blockPos;
        this.blockEntity = (StrainerBlockEntity) inventory.player.level().getBlockEntity(blockPos);
        assert blockEntity != null;

        this.possibleOutputs = allOutputs;

        ScrollingOutputContainer scrollingContainer = new ScrollingOutputContainer();

        for (int i = 0; i < VISIBLE_SLOTS; i++) {
            int col = i % COLUMNS;
            int row = i / COLUMNS;
            this.addSlot(new DisplaySlot(scrollingContainer, i, 8 + col * 18, 17 + row * 18));
        }
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setScrollOffset(int value) {
        this.scrollOffset = Math.clamp(value, 0, getMaxScroll());
    }

    public int getMaxScroll() {
        int rows = (possibleOutputs.size() + COLUMNS - 1) / COLUMNS;
        return Math.max(0, rows - VISIBLE_ROWS);
    }

    private int getRealIndex(int visibleIndex) {
        return scrollOffset * COLUMNS + visibleIndex;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    @Override
    public void clicked(int slotIndex, int buttonNum, ContainerInput containerInput, Player player) {
        int localIndex = slotIndex - DISPLAY_SLOT_START;
        if (localIndex >= 0 && localIndex < VISIBLE_SLOTS) {
            int realIndex = getRealIndex(localIndex);
            if (!player.level().isClientSide() && realIndex < possibleOutputs.size()) {
                ItemStack stack = possibleOutputs.get(realIndex);
                boolean nowRemoved = !blockEntity.isRemovedRecipeOutput(stack);
                blockEntity.setRemovedRecipeOutput(stack, nowRemoved);
            }
            return;
        }
        super.clicked(slotIndex, buttonNum, containerInput, player);
    }

    public boolean isRemoved(int visibleIndex) {
        int realIndex = getRealIndex(visibleIndex);
        if (realIndex >= possibleOutputs.size()) return false;
        return blockEntity.isRemovedRecipeOutput(possibleOutputs.get(realIndex));
    }

    public int getVisibleOutputCount() {
        return Math.max(0, Math.min(VISIBLE_SLOTS, possibleOutputs.size() - scrollOffset * COLUMNS));
    }

    public Slot getDisplaySlot(int index) {
        return getSlot(DISPLAY_SLOT_START + index);
    }

    private class ScrollingOutputContainer implements Container {
        @Override
        public int getContainerSize() {
            return VISIBLE_SLOTS;
        }

        @Override
        public boolean isEmpty() {
            return possibleOutputs.isEmpty();
        }

        @Override
        public ItemStack getItem(int slot) {
            int real = getRealIndex(slot);
            return real < possibleOutputs.size() ? possibleOutputs.get(real) : ItemStack.EMPTY;
        }

        @Override
        public ItemStack removeItem(int slot, int amount) {
            return ItemStack.EMPTY;
        }

        @Override
        public ItemStack removeItemNoUpdate(int slot) {
            return ItemStack.EMPTY;
        }

        @Override
        public void setItem(int slot, ItemStack stack) {
        }

        @Override
        public void setChanged() {
        }

        @Override
        public boolean stillValid(Player player) {
            return true;
        }

        @Override
        public void clearContent() {
        }
    }

    private static class DisplaySlot extends Slot {
        public DisplaySlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

        @Override
        public boolean mayPickup(Player player) {
            return false;
        }
    }
}