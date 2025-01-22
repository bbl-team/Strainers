package com.benbenlaw.strainers.datagen.recipes;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class StrainerRecipeBuilder implements RecipeBuilder {

    protected String group;
    protected Ingredient input;
    protected String aboveBlock;
    protected SizedIngredient output;
    protected int minMeshTier;
    protected double chance;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public StrainerRecipeBuilder(Ingredient input, String aboveBlock, SizedIngredient output, int minMeshTier, double chance) {
        this.input = input;
        this.aboveBlock = aboveBlock;
        this.output = output;
        this.minMeshTier = minMeshTier;
        this.chance = chance;
    }

    public static StrainerRecipeBuilder strainerRecipe(Ingredient input, String aboveBlock, SizedIngredient output, int minMeshTier, double chance) {
        return new StrainerRecipeBuilder(input, aboveBlock, output, minMeshTier, chance);
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
    public @NotNull Item getResult() {
        return ItemStack.EMPTY.getItem();
    }

    public void save(@NotNull RecipeOutput recipeOutput) {

        if (this.input.isEmpty() || this.input.hasNoItems() || this.input.getItems()[0].is(Blocks.BARRIER.asItem())) {
            this.save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "strainer/" +
                    BuiltInRegistries.ITEM.getKey(this.output.getItems()[0].getItem()).getPath() + "_from_a_tag"));
        } else {
            this.save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "strainer/" +
                    BuiltInRegistries.ITEM.getKey(this.output.getItems()[0].getItem()).getPath() + "_from_" +
                    BuiltInRegistries.ITEM.getKey(this.input.getItems()[0].getItem()).getPath()));
        }
    }

    @Override
    public void save(@NotNull RecipeOutput recipeOutput, @NotNull ResourceLocation id) {
        Advancement.Builder builder = Advancement.Builder.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        StrainerRecipe strainerRecipe = new StrainerRecipe(this.input, this.aboveBlock, this.output, this.minMeshTier, this.chance);
        recipeOutput.accept(id, strainerRecipe, builder.build(id.withPrefix("recipe/strainer/")));
    }


}
