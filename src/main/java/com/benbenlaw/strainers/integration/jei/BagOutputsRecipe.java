package com.benbenlaw.strainers.integration.jei;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;

public record BagOutputsRecipe(String name, ItemStack bag, TagKey<Block> usedOn, List<ItemStack> outputs) {
}
