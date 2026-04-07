package com.benbenlaw.strainers.screen.custom;

import com.benbenlaw.core.screen.SimpleAbstractContainerMenu;
import com.benbenlaw.core.screen.util.slot.CoreSlot;
import com.benbenlaw.core.screen.util.slot.InputSlot;
import com.benbenlaw.core.screen.util.slot.ResultSlot;
import com.benbenlaw.core.screen.util.CoreSlotTextures;
import com.benbenlaw.strainers.block.StrainersBlocks;
import com.benbenlaw.strainers.block.entity.StrainerBlockEntity;
import com.benbenlaw.strainers.screen.StrainersMenuTypes;
import com.benbenlaw.strainers.util.StrainersTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;


public class StrainerMenu extends SimpleAbstractContainerMenu {
    protected StrainerBlockEntity blockEntity;
    protected Level level;
    protected ContainerData data;
    protected Player player;
    protected BlockPos blockPos;

    public StrainerMenu(int containerID, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerID, inventory, extraData.readBlockPos(), new SimpleContainerData(2));
    }

    public StrainerMenu(int containerID, Inventory inventory, BlockPos blockPos, ContainerData data) {
        super(StrainersMenuTypes.WOODEN_STRAINER_MENU.get(), containerID, inventory, blockPos, 20);
        this.player = inventory.player;
        this.blockPos = blockPos;
        this.level = inventory.player.level();
        this.blockEntity = (StrainerBlockEntity) this.level.getBlockEntity(blockPos);
        this.data = data;

        assert blockEntity != null;
        this.addSlot(new InputSlot(blockEntity.getInputHandler(), blockEntity.getInputHandler()::set, 0, 8, 35)); //input
        this.addSlot(new InputSlot(blockEntity.getInputHandler(), blockEntity.getInputHandler()::set, 1, 8, 53)); //mesh


        for (int i = 0; i < 18; i++) {
            int row = i / 6;
            int col = i % 6;

            int slotX = 62 + (col * 18);
            int slotY = 17 + (row * 18);

            this.addSlot(new ResultSlot(blockEntity.getOutputHandler(), blockEntity.getOutputHandler()::set, i, slotX, slotY));
        }

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0 ;
    }

    public int getScaledProgress() {

        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 24; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
}
