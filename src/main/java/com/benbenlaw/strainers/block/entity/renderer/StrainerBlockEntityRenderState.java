package com.benbenlaw.strainers.block.entity.renderer;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

public class StrainerBlockEntityRenderState extends BlockEntityRenderState {
    public FluidStack fluidStack;
    public int tankCapacity;
    public ItemStack mesh;
    public ItemStack processingItem;
    public Level blockEntityLevel;

    final ItemStackRenderState meshStackRenderer = new ItemStackRenderState();
    final ItemStackRenderState processingStackRenderer = new ItemStackRenderState();
    final ItemStackRenderState glassStack = new ItemStackRenderState();

}
