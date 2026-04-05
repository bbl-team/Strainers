package com.benbenlaw.strainers.recipe;

import com.benbenlaw.core.block.entity.handler.fluid.InputFluidHandler;
import com.benbenlaw.core.block.entity.handler.item.InputItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.fluid.FluidUtil;
import net.neoforged.neoforge.transfer.item.ItemUtil;

public class StrainerRecipeInput implements RecipeInput {

    private final InputItemHandler itemHandler;
    private final InputFluidHandler fluidHandler;

    public StrainerRecipeInput(InputItemHandler itemHandler, InputFluidHandler fluidHandler) {
        this.itemHandler = itemHandler;
        this.fluidHandler = fluidHandler;
    }

    public ItemStack getInputStack() {
        return itemHandler.getResource(0).toStack();
    }

    public ItemStack getMeshStack() {
        return itemHandler.getResource(1).toStack();
    }

    public FluidStack getFluid() {
        return FluidUtil.getStack(fluidHandler, 0);
    }

    @Override
    public ItemStack getItem(int i) {
        return ItemUtil.getStack(itemHandler, i);
    }

    @Override
    public int size() {
        return itemHandler.size();
    }
}