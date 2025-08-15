package com.benbenlaw.strainers.datagen.recipes;

import com.benbenlaw.core.recipe.ChanceResult;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ResultLists {

    public static final NonNullList<ChanceResult> LEAVES_RESULTS;

    static {
        LEAVES_RESULTS = NonNullList.create();
        LEAVES_RESULTS.add(new ChanceResult(new ItemStack(Items.OAK_SAPLING), 0.1f));
        LEAVES_RESULTS.add(new ChanceResult(new ItemStack(Items.SPRUCE_SAPLING), 0.1f));
        LEAVES_RESULTS.add(new ChanceResult(new ItemStack(Items.BIRCH_SAPLING), 0.1f));
        LEAVES_RESULTS.add(new ChanceResult(new ItemStack(Items.JUNGLE_SAPLING), 0.1f));
        LEAVES_RESULTS.add(new ChanceResult(new ItemStack(Items.ACACIA_SAPLING), 0.1f));
        LEAVES_RESULTS.add(new ChanceResult(new ItemStack(Items.DARK_OAK_SAPLING), 0.1f));
        LEAVES_RESULTS.add(new ChanceResult(new ItemStack(Items.CHERRY_SAPLING), 0.1f));
        LEAVES_RESULTS.add(new ChanceResult(new ItemStack(Items.MANGROVE_PROPAGULE), 0.1f));
        LEAVES_RESULTS.add(new ChanceResult(new ItemStack(Items.BAMBOO), 0.1f));
    }
}
