package com.benbenlaw.strainers.data.recipes;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class StrainerRecipeBuilder implements RecipeBuilder {

    protected String group;
    SizedIngredient input;
    Optional<SizedFluidIngredient> fluid;
    ChanceResult result;
    int minMeshTier;
    double additionalChancePerTier;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public StrainerRecipeBuilder(SizedIngredient input, Optional<SizedFluidIngredient> fluid, ChanceResult result, int minMeshTier, double additionalChancePerTier) {
        this.input = input;
        this.fluid = fluid;
        this.result = result;
        this.minMeshTier = minMeshTier;
        this.additionalChancePerTier = additionalChancePerTier;
    }

    public static StrainerRecipeBuilder strainerRecipeBuilder(SizedIngredient input, Optional<SizedFluidIngredient> fluid, ChanceResult result, int minMeshTier, double additionalChancePerTier) {
        return new StrainerRecipeBuilder(input, fluid, result, minMeshTier, additionalChancePerTier);
    }

    public static StrainerRecipeBuilder strainerRecipeBuilder(SizedIngredient input, ChanceResult result, int minMeshTier, double additionalChancePerTier) {
        return new StrainerRecipeBuilder(input, Optional.empty(), result, minMeshTier, additionalChancePerTier);
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(String groupName) {
        this.group = groupName;
        return this;
    }


    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        ItemStackTemplate stack = result.template();
        return ResourceKey.create(
                Registries.RECIPE,
                Strainers.identifier("straining/" + stack.item().value().builtInRegistryHolder().key().identifier().getPath())
        );
    }

    @Override
    public void save(@NotNull RecipeOutput recipeOutput, @NotNull String id) {
        save(recipeOutput, ResourceKey.create(Registries.RECIPE, Strainers.identifier("straining/" + id)));
    }



    @Override
    public void save(@NotNull RecipeOutput recipeOutput, @NotNull ResourceKey<Recipe<?>> resourceKey) {
        Advancement.Builder builder = Advancement.Builder.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceKey))
                .rewards(AdvancementRewards.Builder.recipe(resourceKey))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        StrainerRecipe strainerRecipe = new StrainerRecipe(input, fluid, result, minMeshTier, additionalChancePerTier);
        recipeOutput.accept(resourceKey, strainerRecipe, builder.build(resourceKey.identifier().withPrefix("recipe/strainer/")));
    }



}