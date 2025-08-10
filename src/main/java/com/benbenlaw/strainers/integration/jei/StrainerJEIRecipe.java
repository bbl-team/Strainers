package com.benbenlaw.strainers.integration.jei;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import com.benbenlaw.strainers.util.ModTags;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class StrainerJEIRecipe {
    private final StrainerRecipe baseRecipe;
    private final int meshTier;

    public StrainerJEIRecipe(StrainerRecipe baseRecipe, int meshTier) {
        this.baseRecipe = baseRecipe;
        this.meshTier = meshTier;
    }

    public StrainerRecipe getBaseRecipe() {
        return baseRecipe;
    }

    public int getMeshTier() {
        return meshTier;
    }

    public Ingredient getMeshIngredient() {
        return switch (meshTier) {
            case 1 -> Ingredient.of(ModTags.Items.TIER_1_MESHES);
            case 2 -> Ingredient.of(ModTags.Items.TIER_2_MESHES);
            case 3 -> Ingredient.of(ModTags.Items.TIER_3_MESHES);
            case 4 -> Ingredient.of(ModTags.Items.TIER_4_MESHES);
            case 5 -> Ingredient.of(ModTags.Items.TIER_5_MESHES);
            case 6 -> Ingredient.of(ModTags.Items.TIER_6_MESHES);
            case 7 -> Ingredient.of(ModTags.Items.TIER_7_MESHES);
            case 8 -> Ingredient.of(ModTags.Items.TIER_8_MESHES);
            case 9 -> Ingredient.of(ModTags.Items.TIER_9_MESHES);
            case 10 -> Ingredient.of(ModTags.Items.TIER_10_MESHES);
            default -> Ingredient.EMPTY;
        };
    }

    public Ingredient getInput() {
        return baseRecipe.input();
    }

    public List<ChanceResult> getOutputs() {
        return baseRecipe.getRollResults();
    }

}
