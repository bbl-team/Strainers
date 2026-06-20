package com.benbenlaw.strainers.data.recipes;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.core.tag.CommonTags;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.fluid.StrainersFluids;
import com.benbenlaw.strainers.item.OrePieceItem;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class StrainerRecipeBuilder implements RecipeBuilder {

    protected String group;

    private SizedIngredient input;
    private Optional<SizedFluidIngredient> fluid = Optional.empty();
    private ChanceResult result;
    private int minMeshTier;
    private double additionalChancePerTier;

    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private StrainerRecipeBuilder() {
    }

    public static StrainerRecipeBuilder create() {
        return new StrainerRecipeBuilder();
    }

    public StrainerRecipeBuilder input(ItemLike item) {
        this.input = new SizedIngredient(Ingredient.of(item), 1);
        return this;
    }

    public StrainerRecipeBuilder input(ItemLike item, int count) {
        this.input = new SizedIngredient(Ingredient.of(item), count);
        return this;
    }

    public StrainerRecipeBuilder input(Ingredient ingredient) {
        this.input = new SizedIngredient(ingredient, 1);
        return this;
    }

    public StrainerRecipeBuilder input(Ingredient ingredient, int count) {
        this.input = new SizedIngredient(ingredient, count);
        return this;
    }

    public StrainerRecipeBuilder input(SizedIngredient ingredient) {
        this.input = ingredient;
        return this;
    }

    public StrainerRecipeBuilder input(TagKey<Item> tag, HolderLookup.Provider registries) {
        this.input = new SizedIngredient(Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)),1);
        return this;
    }

    public StrainerRecipeBuilder input(TagKey<Item> tag, int count, HolderLookup.Provider registries) {
        this.input = new SizedIngredient(Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)), count);
        return this;
    }

    public StrainerRecipeBuilder output(Item item, float chance) {
        return output(item, 1, chance);
    }

    public StrainerRecipeBuilder output(Item item, int count, float chance) {
        this.result = new ChanceResult(
                new ItemStackTemplate(item, count),
                chance
        );
        return this;
    }

    public StrainerRecipeBuilder output(String oreType, float chance) {
        this.result = new ChanceResult(OrePieceItem.createOrePiece(oreType), chance);
        return this;
    }

    public StrainerRecipeBuilder fluid(SizedFluidIngredient fluid) {
        this.fluid = Optional.of(fluid);
        return this;
    }

    public StrainerRecipeBuilder water() {
        return fluid(new SizedFluidIngredient(FluidIngredient.of(Fluids.WATER),1));
    }

    public StrainerRecipeBuilder water(int amount) {
        return fluid(new SizedFluidIngredient(FluidIngredient.of(Fluids.WATER),amount));
    }

    public StrainerRecipeBuilder eroding() {
        return fluid(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.ERODING_WATER.getFluid()),1));
    }

    public StrainerRecipeBuilder eroding(int amount) {
        return fluid(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.ERODING_WATER.getFluid()),amount));
    }

    public StrainerRecipeBuilder purifying() {
        return fluid(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.PURIFYING_WATER.getFluid()),1));
    }

    public StrainerRecipeBuilder purifying(int amount) {
        return fluid(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.PURIFYING_WATER.getFluid()),amount));
    }

    public StrainerRecipeBuilder salty() {
        return fluid(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.SALTY_WATER.getFluid()),1));
    }

    public StrainerRecipeBuilder salty(int amount) {
        return fluid(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.SALTY_WATER.getFluid()),amount));
    }

    public StrainerRecipeBuilder tier(int minMeshTier, double additionalChancePerTier) {
        this.minMeshTier = minMeshTier;
        this.additionalChancePerTier = additionalChancePerTier;
        return this;
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
                Strainers.identifier(
                        "straining/" +
                                stack.item()
                                        .value()
                                        .builtInRegistryHolder()
                                        .key()
                                        .identifier()
                                        .getPath()
                )
        );
    }

    @Override
    public void save(@NotNull RecipeOutput output, @NotNull String id) {
        save(output,ResourceKey.create(Registries.RECIPE, Strainers.identifier("straining/" + id)));
    }

    public void save(@NotNull RecipeOutput output, @NotNull String oreTag, @NotNull String id) {
        save(output.withConditions(new NotCondition(new TagEmptyCondition<>(CommonTags.getItemTag("ores", oreTag))))
                ,ResourceKey.create(Registries.RECIPE, Strainers.identifier("straining/" + id)));
    }

    @Override
    public void save(@NotNull RecipeOutput recipeOutput, @NotNull ResourceKey<Recipe<?>> resourceKey) {

        Advancement.Builder builder = Advancement.Builder.advancement()
                .addCriterion("has_the_recipe",RecipeUnlockedTrigger.unlocked(resourceKey))
                .rewards(AdvancementRewards.Builder.recipe(resourceKey))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(builder::addCriterion);

        StrainerRecipe recipe = new StrainerRecipe(input, fluid, result, minMeshTier, additionalChancePerTier);

        recipeOutput.accept(
                resourceKey,
                recipe,
                builder.build(
                        resourceKey.identifier()
                                .withPrefix("recipe/strainer/")
                )
        );
    }
}