package com.benbenlaw.strainers.integration.jei;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import com.benbenlaw.strainers.util.ModTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.List;

public class CombinedStrainerJEIRecipe implements Comparable<CombinedStrainerJEIRecipe> {
    private final Ingredient input;
    private final Ingredient mesh;
    private final BlockState blockAbove;
    private final List<ChanceResult> combinedResults;

    public CombinedStrainerJEIRecipe(Ingredient input, Ingredient mesh, BlockState blockAbove, List<ChanceResult> combinedResults) {
        this.input = input;
        this.mesh = mesh;
        this.blockAbove = blockAbove;
        this.combinedResults = combinedResults;
    }

    public Ingredient getInput() {
        return input;
    }

    public Ingredient getMesh() {
        return mesh;
    }

    public BlockState getBlockAbove() {
        return blockAbove;
    }

    public List<ChanceResult> getRollResults() {
        return combinedResults;
    }

    @Override
    public int compareTo(CombinedStrainerJEIRecipe other) {
        int tierCompare = Integer.compare(getMeshTier(this.mesh), getMeshTier(other.mesh));
        if (tierCompare != 0) return tierCompare;

        // Then compare by input (so all sand recipes are grouped together within each tier)
        return this.input.toString().compareTo(other.input.toString());
    }


    private static int getMeshTier(Ingredient mesh) {
        if (meshMatchesTag(mesh, ModTags.Items.TIER_1_MESHES)) return 1;
        if (meshMatchesTag(mesh, ModTags.Items.TIER_2_MESHES)) return 2;
        if (meshMatchesTag(mesh, ModTags.Items.TIER_3_MESHES)) return 3;
        if (meshMatchesTag(mesh, ModTags.Items.TIER_4_MESHES)) return 4;
        if (meshMatchesTag(mesh, ModTags.Items.TIER_5_MESHES)) return 5;
        if (meshMatchesTag(mesh, ModTags.Items.TIER_6_MESHES)) return 6;
        if (meshMatchesTag(mesh, ModTags.Items.TIER_7_MESHES)) return 7;
        if (meshMatchesTag(mesh, ModTags.Items.TIER_8_MESHES)) return 8;
        if (meshMatchesTag(mesh, ModTags.Items.TIER_9_MESHES)) return 9;
        if (meshMatchesTag(mesh, ModTags.Items.TIER_10_MESHES)) return 10;

        return Integer.MAX_VALUE; // fallback
    }

    private static boolean meshMatchesTag(Ingredient mesh, TagKey<Item> tag) {
        for (ItemStack stack : mesh.getItems()) {
            if (stack.is(tag)) return true; // any item in the ingredient matches the tag
        }
        return false;
    }

}