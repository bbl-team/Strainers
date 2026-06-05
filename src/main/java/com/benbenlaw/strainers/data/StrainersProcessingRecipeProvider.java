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

        //Mulch -> Stone Pebbles, Dirt Piles
        simpleStrainer(StrainersItems.STONE_PEBBLE.get(), 3, 1.0f, StrainersBlocks.MULCH, 1, 0.5f, "mulch/stone_pebble");
        simpleStrainer(StrainersItems.DIRT_PILE.get(), 3, 1.0f, StrainersBlocks.MULCH, 1, 0.5f, "mulch/dirt_pile");
        simpleStrainer(StrainersItems.WATER_DROP.get(),0.5f, StrainersBlocks.MULCH, 1, 0.1f, "mulch/water_drop");
        simpleStrainer(Items.BONE_MEAL, 0.4f, StrainersBlocks.MULCH, 1, 0.1f, "mulch/bone_meal");
        simpleWaterStrainer(StrainersItems.SAPLING_BAG.get(), 0.2f, StrainersBlocks.MULCH, 2, 0.05f, "mulch/sapling_seed");

        //Leaves -> Gravel Pebble, Stick, Leaf Pile, Sapling Bag
        simpleStrainer(StrainersItems.STONE_PEBBLE.get(), 0.75f, ItemTags.LEAVES, 1, 0.25f, "leaves/stone_pebble");
        simpleStrainer(Items.STICK, 0.75f, ItemTags.LEAVES, 1, 0.25f, "leaves/stick");
        simpleStrainer(StrainersItems.LEAF_PILE.get(), 2, 1f, ItemTags.LEAVES, 1, 1f, "leaves/leaf_pile");
        simpleWaterStrainer(StrainersItems.SAPLING_BAG.get(), 0.1f, ItemTags.LEAVES, 2, 0.05f, "leaves/sapling_seed");

        //Dirt -> Stone Pebble, Gravel Pebble, Stick
        simpleStrainer(StrainersItems.STONE_PEBBLE.get(), 0.75f, ItemTags.DIRT, 1, 0.25f, "dirt/stone_pebble");
        simpleStrainer(StrainersItems.GRAVEL_PEBBLE.get(), 0.75f, ItemTags.DIRT, 1, 0.25f, "dirt/gravel_pebble");
        simpleStrainer(Items.STICK, 0.75f, ItemTags.DIRT, 1, 0.25f, "dirt/stick");

        //Gravel -> Purified Drop, Eroding Drop
        simpleWaterStrainer(StrainersItems.PURIFYING_DROP.get(), 0.1f, Items.GRAVEL, 2, 0.05f, "gravel/purifying_drop");
        simpleWaterStrainer(StrainersItems.ERODING_DROP.get(), 0.1f, Items.GRAVEL, 2, 0.05f, "gravel/eroding_drop");

        //Sand -> Salty Drop
        simpleWaterStrainer(StrainersItems.SALT_WATER_DROP.get(), 0.1f, Items.SAND, 2, 0.05f, "sand/salty_drop");

        //Purified Blocks
        simplePurifyingStrainer(StrainersBlocks.PURIFIED_DIRT.get().asItem(), 1.0f, ItemTags.DIRT, 2, 0.0f, "dirt/purified_dirt");
        simplePurifyingStrainer(StrainersBlocks.PURIFIED_GRAVEL.get().asItem(), 1.0f, Items.GRAVEL, 2, 0.0f, "gravel/purified_gravel");
        simplePurifyingStrainer(StrainersBlocks.PURIFIED_SAND.get().asItem(), 1.0f, Items.SAND, 2, 0.0f, "sand/purified_sand");
        simplePurifyingStrainer(StrainersBlocks.PURIFIED_DUST_BLOCK.get().asItem(), 1.0f, StrainersBlocks.DUST_BLOCK, 3, 0.0f, "dust_block/purified_dust_block");
        simplePurifyingStrainer(StrainersBlocks.PURIFIED_NETHERRACK.get().asItem(), 1.0f, Blocks.NETHERRACK.asItem(), 4, 0.0f, "netherrack/purified_netherrack");
        simplePurifyingStrainer(StrainersBlocks.PURIFIED_SOUL_SAND.get().asItem(), 1.0f, Blocks.SOUL_SAND.asItem(), 4, 0.0f, "soul_sand/purified_soul_sand");
        simplePurifyingStrainer(StrainersBlocks.PURIFIED_SOUL_SOIL.get().asItem(), 1.0f, Blocks.SOUL_SOIL.asItem(), 4, 0.0f, "soul_soil/purified_soul_soil");
        simplePurifyingStrainer(StrainersBlocks.PURIFIED_STONE.get().asItem(), 1.0f, Blocks.STONE, 4, 0.0f, "stone/purified_soul_soil");
        simplePurifyingStrainer(StrainersBlocks.PURIFIED_DEEPSLATE.get().asItem(), 1.0f, Blocks.DEEPSLATE, 7, 0.0f, "stone/purified_deepslate");



        //Purified Dirt -> Grass Block
        simplePurifyingStrainer(Blocks.GRASS_BLOCK.asItem(), 1.0f, StrainersBlocks.PURIFIED_DIRT, 7, 0.0f, "purified_dirt/grass_block");

        //Purified Netherrack -> Nether Blocks
        simpleWaterStrainer(Blocks.BASALT.asItem(), 0.1f, StrainersBlocks.PURIFIED_NETHERRACK, 4, 0.1f, "purified_netherrack/basalt");
        simpleWaterStrainer(Blocks.BLACKSTONE.asItem(), 0.1f, StrainersBlocks.PURIFIED_NETHERRACK, 4, 0.1f, "purified_netherrack/blackstone");

        //Purified Stone -> Granite, Diorite, Andesite, Calcite
        simpleWaterStrainer(Blocks.GRANITE.asItem(), 0.1f, StrainersBlocks.PURIFIED_STONE, 4, 0.1f, "purified_stone/granite");
        simpleWaterStrainer(Blocks.DIORITE.asItem(), 0.1f, StrainersBlocks.PURIFIED_STONE, 4, 0.1f, "purified_stone/diorite");
        simpleWaterStrainer(Blocks.ANDESITE.asItem(), 0.1f, StrainersBlocks.PURIFIED_STONE, 4, 0.1f, "purified_stone/andesite");
        simpleWaterStrainer(Blocks.CALCITE.asItem(), 0.1f, StrainersBlocks.PURIFIED_STONE, 4, 0.1f, "purified_stone/calcite");

        //Purified Soul Sand -> Nether Wart
        simpleWaterStrainer(Items.NETHER_WART, 0.1f, StrainersBlocks.PURIFIED_SOUL_SAND, 4, 0.1f, "purified_soul_sand/nether_wart");

        //Purified Soul Soil -> Crimson/ Warped Fungus
        simpleWaterStrainer(Items.CRIMSON_FUNGUS, 0.1f, StrainersBlocks.PURIFIED_SOUL_SOIL, 4, 0.1f, "purified_soul_soil/crimson_fungus");
        simpleWaterStrainer(Items.WARPED_FUNGUS, 0.1f, StrainersBlocks.PURIFIED_SOUL_SOIL, 4, 0.1f, "purified_soul_soil/warped_fungus");

        //Purified Dirt -> Seeds
        simpleWaterStrainer(Items.WHEAT_SEEDS, 0.1f, StrainersBlocks.PURIFIED_DIRT, 1, 0.1f, "purified_dirt/wheat_seeds");
        simpleWaterStrainer(Items.BEETROOT_SEEDS, 0.1f, StrainersBlocks.PURIFIED_DIRT, 2, 0.1f, "purified_dirt/beetroot_seeds");
        simpleWaterStrainer(Items.PUMPKIN_SEEDS, 0.1f, StrainersBlocks.PURIFIED_DIRT, 4, 0.1f, "purified_dirt/pumpkin_seeds");
        simpleWaterStrainer(Items.MELON_SEEDS, 0.1f, StrainersBlocks.PURIFIED_DIRT, 4, 0.1f, "purified_dirt/melon_seeds");

        //Purified Dirt -> Crops
        simpleWaterStrainer(Items.CARROT, 0.1f, ItemTags.DIRT, 1, 0.1f, "purified_dirt/wheat");
        simpleWaterStrainer(Items.POTATO, 0.1f, ItemTags.DIRT, 4, 0.1f, "purified_dirt/potato");
        simpleWaterStrainer(Items.POISONOUS_POTATO, 0.01f, ItemTags.DIRT, 4, 0.01f, "purified_dirt/poisonous_potato");
        simpleWaterStrainer(Items.SWEET_BERRIES, 0.01f, ItemTags.DIRT, 4, 0.01f, "purified_dirt/sweet_berries");

        //Mud -> Clay Ball
        simpleStrainer(Items.CLAY_BALL, 2, 0.75f, Items.MUD, 1, 0.25f, "mud/clay_ball");

        //Cobblestone -> Gravel Pebble
        simpleWaterStrainer(StrainersItems.GRAVEL_PEBBLE.get(), 2f, Tags.Items.COBBLESTONES, 1, 0.5f, "cobblestone/gravel_pebble");

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

        //Sand -> Soul Sand
        simpleErodingStrainer(Items.SOUL_SAND, 1.0f, Items.SAND, 6, 0.0f, "sand/soul_sand");

        //Dirt -> Soul Soil
        simpleErodingStrainer(Items.SOUL_SOIL, 1.0f, ItemTags.DIRT, 6, 0.0f, "dirt/soul_soil");

        //Stone -> Deepslate
        simpleErodingStrainer(Items.DEEPSLATE, 1.0f, Items.STONE, 7, 0.0f, "stone/deepslate");

        //Sand -> Prismarine
        simpleSaltyWaterStrainer(Items.PRISMARINE_SHARD, 0.5f, StrainersBlocks.PURIFIED_SAND, 4, 0.1f, "sand/prismarine_shard");
        simpleSaltyWaterStrainer(Items.PRISMARINE_CRYSTALS, 0.30f, StrainersBlocks.PURIFIED_SAND, 4, 0.1f, "sand/prismarine_crystal");

        //Purified Deepslate ->
        simpleWaterStrainer(StrainersItems.SCULK_DUST.get(), 0.75f, StrainersBlocks.PURIFIED_DEEPSLATE, 4, 0.05f, "purified_deepslate/sculk_dust");
        simpleWaterStrainer(Items.ECHO_SHARD, 0.5f, StrainersBlocks.PURIFIED_DEEPSLATE, 4, 0.05f, "purified_deepslate/echo_shard");
        simpleWaterStrainer(Items.DISC_FRAGMENT_5, 0.1f, StrainersBlocks.PURIFIED_DEEPSLATE, 4, 0.1f, "purified_deepslate/disc_fragment_5");
        simpleWaterStrainer(Items.GLOW_BERRIES, 0.1f, StrainersBlocks.PURIFIED_DEEPSLATE, 4, 0.1f, "purified_deepslate/glow_berries");

        //Ore Progression T2 -> Copper (Progression)/ Coal/ Tin/ Zinc/ Aluminum
        simpleWaterStrainerOre(StrainersItems.COPPER_ORE_PIECE.get(), 0.75f, StrainersBlocks.PURIFIED_GRAVEL, 2, 0.05f, "copper", "purified_gravel/copper_ore_piece");
        simpleWaterStrainerOre(StrainersItems.COAL_ORE_PIECE.get(), 0.75f, StrainersBlocks.PURIFIED_GRAVEL, 2, 0.05f, "coal", "purified_gravel/coal_ore_piece");

        simpleWaterStrainerOre(StrainersItems.TIN_ORE_PIECE.get(), 0.75f, StrainersBlocks.PURIFIED_SAND, 2, 0.05f, "tin", "purified_sand/tin_ore_piece");
        simpleWaterStrainerOre(StrainersItems.ZINC_ORE_PIECE.get(), 0.75f, StrainersBlocks.PURIFIED_SAND, 2, 0.05f, "zinc", "purified_sand/zinc_ore_piece");
        simpleWaterStrainerOre(StrainersItems.ALUMINUM_ORE_PIECE.get(), 0.75f, StrainersBlocks.PURIFIED_SAND, 2, 0.05f, "aluminum", "purified_sand/aluminum_ore_piece");

        //Ore Progression T3 -> Iron (Progression)/ Lead/ Nickel / Lapis / Redstone / Amethyst
        simpleWaterStrainerOre(StrainersItems.LAPIS_ORE_PIECE.get(), 0.75f, StrainersBlocks.PURIFIED_DUST_BLOCK, 3, 0.05f, "lapis", "purified_dust_block/lapis_ore_piece");
        simpleWaterStrainer(Items.AMETHYST_SHARD, 0.75f, StrainersBlocks.PURIFIED_DUST_BLOCK, 3, 0.05f, "purified_dust_block/amethyst_shard");
        simpleWaterStrainerOre(StrainersItems.REDSTONE_ORE_PIECE.get(), 0.75f, StrainersBlocks.PURIFIED_DUST_BLOCK, 3, 0.05f, "redstone", "purified_dust_block/redstone_ore_piece");

        simpleWaterStrainerOre(StrainersItems.IRON_ORE_PIECE.get(), 0.75f, StrainersBlocks.PURIFIED_GRAVEL, 3, 0.05f, "iron", "purified_gravel/iron_ore_piece");

        simpleWaterStrainerOre(StrainersItems.LEAD_ORE_PIECE.get(), 0.75f, StrainersBlocks.PURIFIED_SAND, 3, 0.05f, "lead", "purified_sand/lead_ore_piece");
        simpleWaterStrainerOre(StrainersItems.NICKEL_ORE_PIECE.get(), 0.75f, StrainersBlocks.PURIFIED_SAND, 3, 0.05f, "nickel", "purified_sand/nickel_ore_piece");

        simpleWaterStrainer(StrainersItems.QUARTZ_ORE_PIECE.get(), 0.75f, StrainersBlocks.PURIFIED_NETHERRACK, 3, 0.05f, "purified_netherrack/quartz_piece");

        //Ore Progression T4  -> Gold (Progression)/ Silver
        simpleWaterStrainerOre(StrainersItems.GOLD_ORE_PIECE.get(), 0.65f, StrainersBlocks.PURIFIED_GRAVEL, 4, 0.05f, "gold", "purified_gravel/gold_ore_piece");
        simpleWaterStrainerOre(StrainersItems.GOLD_ORE_PIECE.get(), 0.65f, StrainersBlocks.PURIFIED_NETHERRACK, 4, 0.05f, "gold", "purified_netherrack/gold_ore_piece");
        simpleWaterStrainerOre(StrainersItems.SILVER_ORE_PIECE.get(), 0.65f, StrainersBlocks.PURIFIED_SAND, 4, 0.05f, "silver", "purified_sand/silver_ore_piece");

        //Ore Progression T5 -> Diamond (Progression) / Osmium
        simpleWaterStrainerOre(StrainersItems.DIAMOND_ORE_PIECE.get(), 0.55f,  StrainersBlocks.PURIFIED_GRAVEL, 5, 0.05f, "diamond", "purified_gravel/diamond_ore_piece");
        simpleWaterStrainerOre(StrainersItems.OSMIUM_ORE_PIECE.get(), 0.55f, StrainersBlocks.PURIFIED_SAND, 5, 0.05f, "osmium", "purified_sand/osmium_ore_piece");

        //Ore Progression T6 -> Emerald (Progression) / Platinum / Uranium
        simpleWaterStrainerOre(StrainersItems.EMERALD_ORE_PIECE.get(), 0.45f, StrainersBlocks.PURIFIED_GRAVEL, 6, 0.05f, "emerald", "purified_gravel/emerald_ore_piece");

        simpleWaterStrainerOre(StrainersItems.PLATINUM_ORE_PIECE.get(), 0.45f, StrainersBlocks.PURIFIED_SAND, 6, 0.05f, "platinum", "purified_sand/platinum_ore_piece");
        simpleWaterStrainerOre(StrainersItems.URANIUM_ORE_PIECE.get(), 0.45f, StrainersBlocks.PURIFIED_SAND, 6, 0.05f, "uranium", "purified_sand/uranium_ore_piece");

        //Ore Progression T7 -> Ancient Debris (Progression)
        simpleWaterStrainer(StrainersItems.DEBRIS_ORE_PIECE.get(), 0.01f, StrainersBlocks.PURIFIED_NETHERRACK, 7, 0.01f, "purified_netherrack/debris_ore_piece");

        //Ore Progression T8 -> ????




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

    public void simplePurifyingStrainer(Item template, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.PURIFYING_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simplePurifyingStrainer(Item template, int count, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), count),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.PURIFYING_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, count), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simplePurifyingStrainer(Item template, float chance, TagKey<Item> tag, int tier, double additionalChancePerTier, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(this.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.PURIFYING_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simplePurifyingStrainer(Item template, int count, float chance, TagKey<Item> tag, int tier, double additionalChancePerTier, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(this.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)), count),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.PURIFYING_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, count), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simplePurifyingStrainer(Item template, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String resource, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.PURIFYING_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output.withConditions(new NotCondition(new TagEmptyCondition<>(CommonTags.getItemTag("ores", resource)))), id);
    }

    public void simpleSaltyWaterStrainer(Item template, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.SALTY_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleSaltyWaterStrainer(Item template, int count, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), count),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.SALTY_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, count), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleSaltyWaterStrainer(Item template, float chance, TagKey<Item> tag, int tier, double additionalChancePerTier, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(this.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.SALTY_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleSaltyWaterStrainer(Item template, int count, float chance, TagKey<Item> tag, int tier, double additionalChancePerTier, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(this.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)), count),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.SALTY_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, count), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output, id);
    }

    public void simpleSaltyWaterStrainer(Item template, float chance, ItemLike ingredient, int tier, double additionalChancePerTier, String resource, String id) {

        StrainerRecipeBuilder.strainerRecipeBuilder(
                        new SizedIngredient(Ingredient.of(ingredient), 1),
                        Optional.of(new SizedFluidIngredient(FluidIngredient.of(StrainersFluids.SALTY_WATER.getFluid()), 1000)),
                        new ChanceResult(new ItemStackTemplate(template, 1), chance),
                        tier,
                        additionalChancePerTier
                )
                .save(output.withConditions(new NotCondition(new TagEmptyCondition<>(CommonTags.getItemTag("ores", resource)))), id);
    }
}