package com.benbenlaw.strainers.screen.custom;

import com.benbenlaw.core.screen.SimpleAbstractContainerMenu;
import com.benbenlaw.core.screen.util.slot.InputSlot;
import com.benbenlaw.core.screen.util.slot.ResultSlot;
import com.benbenlaw.strainers.block.entity.StrainerBlockEntity;
import com.benbenlaw.strainers.screen.StrainersMenuTypes;
import com.benbenlaw.strainers.util.StrainersTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.transfer.IndexModifier;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class StrainerMenu extends SimpleAbstractContainerMenu {

    protected StrainerBlockEntity blockEntity;
    protected Level level;
    protected ContainerData data;
    protected Player player;
    protected BlockPos blockPos;

    static final int COLUMNS = 5;
    public static final int VISIBLE_ROWS = 3;
    public static final int VISIBLE_SLOTS = COLUMNS * VISIBLE_ROWS;

    private int scrollOffset = 0;

    public StrainerMenu(int containerID, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerID, inventory, extraData.readBlockPos(), new SimpleContainerData(2));
    }

    public StrainerMenu(int containerID, Inventory inventory, BlockPos blockPos, ContainerData data) {
        super(StrainersMenuTypes.WOODEN_STRAINER_MENU.get(), containerID, inventory, blockPos, 17);

        this.player = inventory.player;
        this.blockPos = blockPos;
        this.level = inventory.player.level();
        this.blockEntity = (StrainerBlockEntity) this.level.getBlockEntity(blockPos);
        this.data = data;

        assert blockEntity != null;


        this.addSlot(new InputSlot(blockEntity.getItemHandler(), blockEntity.getItemHandler()::set, 0, 8, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return !stack.is(StrainersTags.Items.MESHES);
            }
        });

        this.addSlot(new InputSlot(blockEntity.getItemHandler(), blockEntity.getItemHandler()::set, 1, 8, 53) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(StrainersTags.Items.MESHES);
            }
        });

        for (int i = 0; i < VISIBLE_SLOTS; i++) {
            int col = i % COLUMNS;
            int row = i / COLUMNS;

            this.addSlot(new ScrollableResultSlot(this, i, 62 + col * 18, 17 + row * 18));
        }

        addDataSlots(data);
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setScrollOffset(int value) {
        this.scrollOffset = Math.clamp(value, 0, getMaxScroll());
    }

    public int getMaxScroll() {
        int total = Math.max(0, blockEntity.getItemHandler().size() - 2);
        int rows = (total + COLUMNS - 1) / COLUMNS;
        return Math.max(0, rows - VISIBLE_ROWS);
    }

    public int getRealSlot(int visibleIndex) {
        return 2 + visibleIndex + (scrollOffset * COLUMNS);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = data.get(0);
        int max = data.get(1);
        return max == 0 ? 0 : (progress * 24 / max);
    }

}