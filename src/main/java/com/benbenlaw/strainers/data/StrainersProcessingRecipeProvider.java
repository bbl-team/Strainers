package com.benbenlaw.strainers.data;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.core.tag.CommonTags;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.StrainersBlocks;
import com.benbenlaw.strainers.data.recipes.StrainerRecipeBuilder;
import com.benbenlaw.strainers.fluid.StrainersFluids;
import com.benbenlaw.strainers.item.StrainersItems;
import com.benbenlaw.strainers.util.StrainersTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class StrainersProcessingRecipeProvider extends RecipeProvider {


    public StrainersProcessingRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }
    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
            super(packOutput, provider);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider provider, @NotNull RecipeOutput recipeOutput) {
            return new StrainersProcessingRecipeProvider(provider, recipeOutput);
        }

        @Override
        public @NotNull String getName() {
            return Strainers.MOD_ID + " Processing Recipes";
        }
    }

    @Override
    protected void buildRecipes() {

        //Leaves -> Stone Pebble/ Gravel/ Stick/ Leaf Pile/ Water Drop
        simpleStrainer(StrainersItems.STONE_PEBBLE.get(), 0.75f, ItemTags.LEAVES, 1, 0.25f, "leaves/stone_pebble");
        simpleStrainer(StrainersItems.GRAVEL_PEBBLE.get(), 0.75f, ItemTags.LEAVES, 1, 0.25f, "leaves/gravel_pebble");
        simpleStrainer(Items.STICK, 0.75f, ItemTags.LEAVES, 1, 0.25f, "leaves/stick");
        simpleStrainer(StrainersItems.LEAF_PILE.get(), 2, 1f, ItemTags.LEAVES, 1, 1f, "leaves/leaf_pile");
        simpleStrainer(StrainersItems.WATER_DROP.get(),0.5f, ItemTags.LEAVES, 1, 0.1f, "leaves/water_drop");

        //Dirt -> Stone Pebble/Gravel/Stick
        simpleStrainer(StrainersItems.STONE_PEBBLE.get(), 0.75f, ItemTags.DIRT, 1, 0.25f, "dirt/stone_pebble");
        simpleStrainer(StrainersItems.GRAVEL_PEBBLE.get(), 0.75f, ItemTags.DIRT, 1, 0.25f, "dirt/gravel_pebble");
        simpleStrainer(Items.STICK, 0.75f, ItemTags.DIRT, 1, 0.25f, "dirt/stick");

        //Dirt -> Seeds
        simpleWaterStrainer(Items.WHEAT_SEEDS, 0.1f, ItemTags.DIRT, 1, 0.1f, "dirt/wheat_seeds");
        simpleWaterStrainer(Items.BEETROOT_SEEDS, 0.1f, ItemTags.DIRT, 2, 0.1f, "dirt/beetroot_seeds");
        simpleWaterStrainer(StrainersItems.SAPLING_BAG.get(), 0.1f, ItemTags.DIRT, 2, 0.1f, "dirt/sapling_seed");
        simpleWaterStrainer(Items.PUMPKIN_SEEDS, 0.1f, ItemTags.DIRT, 4, 0.1f, "dirt/pumpkin_seeds");
        simpleWaterStrainer(Items.MELON_SEEDS, 0.1f, ItemTags.DIRT, 4, 0.1f, "dirt/melon_seeds");

        //Dirt -> Crops
        simpleWaterStrainer(Items.CARROT, 0.1f, ItemTags.DIRT, 1, 0.1f, "dirt/wheat");
        simpleWaterStrainer(Items.POTATO, 0.1f, ItemTags.DIRT, 4, 0.1f, "dirt/potato");
        simpleWaterStrainer(Items.POISONOUS_POTATO, 0.01f, ItemTags.DIRT, 4, 0.01f, "dirt/poisonous_potato");
        simpleWaterStrainer(Items.SWEET_BERRIES, 0.01f, ItemTags.DIRT, 4, 0.01f, "dirt/sweet_berries");

        //Mud -> Clay Ball
        simpleStrainer(Items.CLAY_BALL, 2, 0.75f, Items.MUD, 1, 0.25f, "mud/clay_ball");

        //Cobblestone -> Gravel Pebble
        simpleWaterStrainer(StrainersItems.GRAVEL_PEBBLE.get(), 2f, Tags.Items.COBBLESTONES, 1, 0.5f, "cobblestone/gravel_pebble");

        //Cobblestone -> Eroding Drop
        simpleWaterStrainer(StrainersItems.ERODING_DROP.get(), 0.1f, Tags.Items.COBBLESTONES, 1, 0.1f, "cobblestone/eroding_drop");

        //Cobblestone -> Gravel -> Sand -> Dust (Progression)
        simpleErodingStrainer(Items.GRAVEL, 1.0f, Tags.Items.COBBLESTONES, 1, 0.0f, "cobblestone/gravel");
        simpleErodingStrainer(Items.SAND, 1.0f, Tags.Items.GRAVELS, 1, 0.0f, "gravel/sand");
        simpleErodingStrainer(StrainersBlocks.DUST_BLOCK.get().asItem(), 1.0f, Tags.Items.SANDS, 1, 0.0f, "sand/dust_block");

        //Stone -> Netherrack
        simpleErodingStrainer(Blocks.NETHERRACK.asItem(), 0.75f, Tags.Items.STONES, 5, 0.15f, "stone/netherrack");

        //Netherrack -> Lava Drop
        simpleErodingStrainer(StrainersItems.LAVA_DROP.get(), 0.1f, Blocks.NETHERRACK.asItem(), 6, 0.1f, "netherrack/lava_drop");

        //Gravel -> Sand Dust
        simpleWaterStrainer(StrainersItems.SAND_DUST.get(), 2, 1f, Items.GRAVEL, 1, 0.5f, "gravel/sand_dust");

        //Sand -> Dust
        simpleWaterStrainer(StrainersItems.DUST.get().asItem(), 2,  1f, Items.SAND, 1, 0.5f, "sand/dust");

        //Gravel -> Flint (Progression)
        simpleWaterStrainer(Items.FLINT, 0.2f, Items.GRAVEL, 1, 0.1f, "gravel/flint");

        //Ore Pieces -> Pure Drop
        simpleWaterStrainer(StrainersItems.PURIFYING_DROP.get(), 0.01f, StrainersTags.Items.ORE_PIECES, 1, 0.1f, "ore_pieces/pure_drop");

        //Gravel -> Copper (Progression)/ Coal/ Tin/ Zinc/ Aluminum
        simpleWaterStrainerOre(StrainersItems.COPPER_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 2, 0.15f, "copper", "gravel/copper_ore_piece");
        simpleWaterStrainerOre(StrainersItems.COAL_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 2, 0.15f, "coal", "gravel/coal_ore_piece");
        simpleWaterStrainerOre(StrainersItems.TIN_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 2, 0.15f, "tin", "gravel/tin_ore_piece");
        simpleWaterStrainerOre(StrainersItems.ZINC_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 2, 0.15f, "zinc", "gravel/zinc_ore_piece");
        simpleWaterStrainerOre(StrainersItems.ALUMINUM_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 2, 0.15f, "aluminum", "gravel/aluminum_ore_piece");

        //Gravel -> Iron (Progression)/ Lead/ Nickel / Lapis / Redstone / Amethyst
        simpleWaterStrainerOre(StrainersItems.IRON_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 3, 0.15f, "iron", "gravel/iron_ore_piece");
        simpleWaterStrainerOre(StrainersItems.LEAD_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 3, 0.15f, "lead", "gravel/lead_ore_piece");
        simpleWaterStrainerOre(StrainersItems.NICKEL_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 3, 0.15f, "nickel", "gravel/nickel_ore_piece");
        simpleWaterStrainerOre(StrainersItems.LAPIS_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 3, 0.15f, "lapis", "gravel/lapis_ore_piece");
        simpleWaterStrainer(Items.AMETHYST_SHARD, 0.75f, Items.GRAVEL, 3, 0.15f, "gravel/amethyst_shard");

        //Dust -> Redstone
        simpleWaterStrainerOre(StrainersItems.REDSTONE_ORE_PIECE.get(), 0.75f, StrainersBlocks.DUST_BLOCK, 3, 0.15f, "redstone", "dust/redstone_ore_piece");

        //Gravel -> Gold (Progression)/ Silver
        simpleWaterStrainerOre(StrainersItems.GOLD_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 4, 0.15f, "gold", "gravel/gold_ore_piece");
        simpleWaterStrainerOre(StrainersItems.SILVER_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 4, 0.15f, "silver", "gravel/silver_ore_piece");

        //Gravel -> Diamond (Progression) / Osmium
        simpleWaterStrainerOre(StrainersItems.DIAMOND_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 5, 0.15f, "diamond", "gravel/diamond_ore_piece");
        simpleWaterStrainerOre(StrainersItems.OSMIUM_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 5, 0.15f, "osmium", "gravel/osmium_ore_piece");

        //Gravel -> Emerald (Progression) / Platinum / Uranium
        simpleWaterStrainerOre(StrainersItems.EMERALD_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 6, 0.15f, "emerald", "gravel/emerald_ore_piece");
        simpleWaterStrainerOre(StrainersItems.PLATINUM_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 6, 0.15f, "platinum", "gravel/platinum_ore_piece");
        simpleWaterStrainerOre(StrainersItems.URANIUM_ORE_PIECE.get(), 0.75f, Items.GRAVEL, 6, 0.15f, "uranium", "gravel/uranium_ore_piece");

        //Netherrack -> Quartz
        simpleWaterStrainer(StrainersItems.QUARTZ_ORE_PIECE.get(), 0.75f, Blocks.NETHERRACK.asItem(), 3, 0.15f, "netherrack/quartz_piece");

        //Netherrack -> Ancient Debris (Progression)
        simpleWaterStrainer(StrainersItems.DEBRIS_ORE_PIECE.get(), 0.75f, Blocks.NETHERRACK.asItem(), 7, 0.15f, "netherrack/debris_ore_piece");

        //Sand -> Prismarine
        simpleWaterStrainer(Items.PRISMARINE_SHARD, 0.75f, Items.SAND, 4, 0.15f, "sand/prismarine_shard");
        simpleWaterStrainer(Items.PRISMARINE_CRYSTALS, 0.75f, Items.SAND, 4, 0.15f, "sand/prismarine_crystal");

    }

    public void simpleStrainer(Item template, float chance, TagKey<Item> tag, int tier, double additionalChancePerTier, String id) {
        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(this.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)), 1),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleStrainer(Item template, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String id) {
        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), 1),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleStrainer(Item template, int count, float chance, TagKey<Item> tag, int tier, double additionalChancePerTier, String id) {
        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(this.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)), count),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleStrainer(Item template, int count, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), 1),
                        new ChanceResult(new ItemStackTemplate(template, count), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleWaterStrainer(Item template, float chance, TagKey<Item> tag, int tier, double additionalChancePerTier, String id) {
        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(this.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(Fluids.WATER), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleWaterStrainer(Item template, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(Fluids.WATER), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleWaterStrainerOre(Item template, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String resource, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(Fluids.WATER), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output.withConditions(new NotCondition(new TagEmptyCondition<>(CommonTags.getItemTag("ores", resource)))), id);
    }

    public void simpleWaterStrainer(Item template, int count, float chance, TagKey<Item> tag, int tier, double additionalChancePerTier, String id) {
        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(this.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(Fluids.WATER), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, count), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleWaterStrainer(Item template, int count, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(Fluids.WATER), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, count), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleErodingStrainer(Item template, float chance, TagKey<Item> tag, int tier, double additionalChancePerTier, String id) {
        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(this.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.ERODING_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleErodingStrainer(Item template, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.ERODING_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleErodingStrainer(Item template, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String resource, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.ERODING_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output.withConditions(new NotCondition(new TagEmptyCondition<>(CommonTags.getItemTag("ores", resource)))), id);
    }

    public void simpleErodingStrainer(Item template, int count, float chance, TagKey<Item> tag, int tier, double additionalChancePerTier, String id) {
        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(this.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.ERODING_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, count), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleErodingStrainer(Item template, int count, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), count),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.ERODING_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }
}