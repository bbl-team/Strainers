package com.benbenlaw.strainers.datagen;

import com.benbenlaw.core.item.CoreItems;
import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.ModBlocks;
import com.benbenlaw.strainers.datagen.recipes.MeshUpgradesRecipeBuilder;
import com.benbenlaw.strainers.datagen.recipes.OutputUpgradesRecipeBuilder;
import com.benbenlaw.strainers.datagen.recipes.StrainerRecipeBuilder;
import com.benbenlaw.strainers.fluid.StrainersFluids;
import com.benbenlaw.strainers.item.ModItems;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import com.benbenlaw.strainers.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static com.benbenlaw.strainers.datagen.recipes.ResultLists.LEAVES_RESULTS;
import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;

public class StrainersRecipes extends RecipeProvider {

    public StrainersRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {

        //Mesh Upgrade 1
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MESH_UPGRADE_1)
                .pattern(" M ")
                .pattern("MUM")
                .pattern(" M ")
                .define('M', ModTags.Items.TIER_2_MESHES)
                .define('U', CoreItems.UPGRADE_BASE)
                .group("strainers")
                .unlockedBy("has_item", has(ModTags.Items.TIER_2_MESHES))
                .save(consumer);

        MeshUpgradesRecipeBuilder.MeshUpgradesRecipeBuilder(Ingredient.of(ModItems.MESH_UPGRADE_1), 0.75)
                .unlockedBy("has_item", hasItems(ModItems.MESH_UPGRADE_1)).save(consumer);

        //Mesh Upgrade 2
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MESH_UPGRADE_2)
                .pattern(" M ")
                .pattern("MUM")
                .pattern(" M ")
                .define('M', ModTags.Items.TIER_4_MESHES)
                .define('U', ModItems.MESH_UPGRADE_1)
                .group("strainers")
                .unlockedBy("has_item", has(ModTags.Items.TIER_4_MESHES))
                .save(consumer);

        MeshUpgradesRecipeBuilder.MeshUpgradesRecipeBuilder(Ingredient.of(ModItems.MESH_UPGRADE_2), 0.5)
                .unlockedBy("has_item", hasItems(ModItems.MESH_UPGRADE_2)).save(consumer);

        //Mesh Upgrade 3
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MESH_UPGRADE_3)
                .pattern(" M ")
                .pattern("MUM")
                .pattern(" M ")
                .define('M', ModTags.Items.TIER_6_MESHES)
                .define('U', ModItems.MESH_UPGRADE_2)
                .group("strainers")
                .unlockedBy("has_item", has(ModTags.Items.TIER_6_MESHES))
                .save(consumer);

        MeshUpgradesRecipeBuilder.MeshUpgradesRecipeBuilder(Ingredient.of(ModItems.MESH_UPGRADE_3), 0.25)
                .unlockedBy("has_item", hasItems(ModItems.MESH_UPGRADE_3)).save(consumer);

        //Output Upgrade 1
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.OUTPUT_UPGRADE_1)
                .pattern(" M ")
                .pattern("MUM")
                .pattern(" M ")
                .define('M', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('U', CoreItems.UPGRADE_BASE)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.STORAGE_BLOCKS_IRON))
                .save(consumer);

        OutputUpgradesRecipeBuilder.OutputUpgradesRecipeBuilder(Ingredient.of(ModItems.OUTPUT_UPGRADE_1), 0.2)
                .unlockedBy("has_item", hasItems(ModItems.OUTPUT_UPGRADE_1)).save(consumer);

        //Output Upgrade 2
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.OUTPUT_UPGRADE_2)
                .pattern(" M ")
                .pattern("MUM")
                .pattern(" M ")
                .define('M', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .define('U', ModItems.OUTPUT_UPGRADE_1)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.STORAGE_BLOCKS_DIAMOND))
                .save(consumer);

        OutputUpgradesRecipeBuilder.OutputUpgradesRecipeBuilder(Ingredient.of(ModItems.OUTPUT_UPGRADE_2), 0.4)
                .unlockedBy("has_item", hasItems(ModItems.OUTPUT_UPGRADE_2)).save(consumer);

        //Output Upgrade 3
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.OUTPUT_UPGRADE_3)
                .pattern(" M ")
                .pattern("MUM")
                .pattern(" M ")
                .define('M', Tags.Items.STORAGE_BLOCKS_NETHERITE)
                .define('U', ModItems.OUTPUT_UPGRADE_2)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.STORAGE_BLOCKS_NETHERITE))
                .save(consumer);

        OutputUpgradesRecipeBuilder.OutputUpgradesRecipeBuilder(Ingredient.of(ModItems.OUTPUT_UPGRADE_3), 0.6)
                .unlockedBy("has_item", hasItems(ModItems.OUTPUT_UPGRADE_3)).save(consumer);

        // Tags Checks, this is part of ore move to core version for 1.22, thanks ben //

        TagKey<Item> aluminumIngotTag = ItemTags.create(
                Objects.requireNonNull(ResourceLocation.tryParse(String.valueOf(ResourceLocation.fromNamespaceAndPath("c", "ingots/aluminum")))));
        TagKey<Item> zincIngotTag = ItemTags.create(
                Objects.requireNonNull(ResourceLocation.tryParse(String.valueOf(ResourceLocation.fromNamespaceAndPath("c", "ingots/zinc")))));
        TagKey<Item> silverIngotTag = ItemTags.create(
                Objects.requireNonNull(ResourceLocation.tryParse(String.valueOf(ResourceLocation.fromNamespaceAndPath("c", "ingots/silver")))));
        TagKey<Item> osmiumIngotTag = ItemTags.create(
                Objects.requireNonNull(ResourceLocation.tryParse(String.valueOf(ResourceLocation.fromNamespaceAndPath("c", "ingots/osmium")))));
        TagKey<Item> uraniumIngotTag = ItemTags.create(
                Objects.requireNonNull(ResourceLocation.tryParse(String.valueOf(ResourceLocation.fromNamespaceAndPath("c", "ingots/uranium")))));
        TagKey<Item> platinumIngotTag = ItemTags.create(
                Objects.requireNonNull(ResourceLocation.tryParse(String.valueOf(ResourceLocation.fromNamespaceAndPath("c", "ingots/platinum")))));
        TagKey<Item> bronzeIngotTag = ItemTags.create(
                Objects.requireNonNull(ResourceLocation.tryParse(String.valueOf(ResourceLocation.fromNamespaceAndPath("c", "ingots/bronze")))));
        TagKey<Item> tinIngotTag = ItemTags.create(
                Objects.requireNonNull(ResourceLocation.tryParse(String.valueOf(ResourceLocation.fromNamespaceAndPath("c", "ingots/tin")))));


        // ********** Vanilla Recipes ********** //

        //Eroding Salt Mulch
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ERODING_SALT_MULCH)
                .pattern("SSS")
                .pattern("SMS")
                .pattern("SSS")
                .define('S', Items.CHARCOAL)
                .define('M', ModBlocks.MULCH)
                .group("strainers")
                .unlockedBy("has_item", has(Items.CHARCOAL))
                .save(consumer);

        //Purifying Salt Mulch
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PURIFYING_SALT_MULCH)
                .pattern("SSS")
                .pattern("SMS")
                .pattern("SSS")
                .define('S', ItemTags.SAND)
                .define('M', ModBlocks.MULCH)
                .group("strainers")
                .unlockedBy("has_item", has(ItemTags.SAND))
                .save(consumer);

        //Tank
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.STRAINER_TANK)
                .pattern("GGG")
                .pattern("GSG")
                .pattern("GGG")
                .define('G', Tags.Items.GLASS_BLOCKS)
                .define('S', ModBlocks.WOODEN_STRAINER)
                .group("strainers")
                .unlockedBy("has_item", has(Blocks.GLASS))
                .save(consumer);

        //Mulch
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MULCH)
                .pattern(" L ")
                .pattern("LSL")
                .pattern(" L ")
                .define('L', ItemTags.LEAVES)
                .define('S', ItemTags.SAPLINGS)
                .group("strainers")
                .unlockedBy("has_item", has(ItemTags.LEAVES))
                .save(consumer);

        //Dirt
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.DIRT)
                .pattern(" L ")
                .pattern("LML")
                .pattern(" L ")
                .define('L', ItemTags.LEAVES)
                .define('M', ModBlocks.MULCH)
                .group("strainers")
                .unlockedBy("has_item", has(ModItems.STONE_PEBBLE))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "dirt_from_mulch"));

        ////Leafy Mesh
        //ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LEAFY_MESH)
        //        .pattern("SLS")
        //        .pattern("LLL")
        //        .pattern("SLS")
        //        .define('L', com.benbenlaw.opolisutilities.item.ModItems.LEAFY_STRING)
        //        .define('S', Tags.Items.RODS_WOODEN)
        //        .group("strainers")
        //        .unlockedBy("has_item", has(com.benbenlaw.opolisutilities.item.ModItems.LEAFY_STRING))
        //        .save(consumer);

        //Bamboo Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BAMBOO_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Items.BAMBOO)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", hasItems(Items.BAMBOO))
                .save(consumer);

        //Netherite Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NETHERITE_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Items.NETHERITE_INGOT)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", hasItems(Items.NETHERITE_INGOT))
                .save(consumer);

        //Obsidian Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.OBSIDIAN_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Blocks.OBSIDIAN)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", hasItems(Blocks.OBSIDIAN))
                .save(consumer);

        //Prismarine Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PRISMARINE_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Tags.Items.GEMS_PRISMARINE)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.GEMS_PRISMARINE))
                .save(consumer);

        //End Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.END_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Tags.Items.END_STONES)
                .define('S', Items.END_ROD)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.END_STONES))
                .save(consumer);

        //Heavy Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HEAVY_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Items.HEAVY_CORE)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(Items.HEAVY_CORE))
                .save(consumer);

        //Wooden Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOODEN_MESH)
                .pattern("SSS")
                .pattern("S S")
                .pattern("SSS")
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.RODS_WOODEN))
                .save(consumer);

        //Bone mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Items.BONE)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", hasItems(Items.BONE))
                .save(consumer);

        //Blaze Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLAZE_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Items.BLAZE_ROD)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", hasItems(Items.BLAZE_ROD))
                .save(consumer);

        //Breeze Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BREEZE_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Items.BREEZE_ROD)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", hasItems(Items.BREEZE_ROD))
                .save(consumer);

        //Bronze Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', bronzeIngotTag)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(bronzeIngotTag))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(bronzeIngotTag))));

        //Copper Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.INGOTS_COPPER))
                .save(consumer);

        //Diamond Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Items.DIAMOND)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", hasItems(Items.DIAMOND))
                .save(consumer);

        //Echo Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ECHO_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Items.ECHO_SHARD)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(Items.ECHO_SHARD))
                .save(consumer);

        //Emerald Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMERALD_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Items.EMERALD)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", hasItems(Items.EMERALD))
                .save(consumer);

        //Flint Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Items.FLINT)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", hasItems(Items.FLINT))
                .save(consumer);

        //Gold Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLD_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Tags.Items.INGOTS_GOLD)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.INGOTS_GOLD))
                .save(consumer);

        //Iron Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Tags.Items.INGOTS_IRON)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.INGOTS_IRON))
                .save(consumer);

        //lapis Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LAPIS_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Tags.Items.GEMS_LAPIS)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.GEMS_LAPIS))
                .save(consumer);

        //Redstone Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.REDSTONE_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.DUSTS_REDSTONE))
                .save(consumer);

        //Tin Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TIN_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', tinIngotTag)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(tinIngotTag))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(bronzeIngotTag))));

        //Amethyst Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMETHYST_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Tags.Items.GEMS_AMETHYST)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.GEMS_AMETHYST))
                .save(consumer);

        //Quartz Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.QUARTZ_MESH)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Tags.Items.GEMS_QUARTZ)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.GEMS_QUARTZ))
                .save(consumer);

        //String Mesh
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING)
                .pattern("SBS")
                .pattern("BBB")
                .pattern("SBS")
                .define('B', Items.STRING)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(Items.STRING))
                .save(consumer);

        //Strainer
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.WOODEN_STRAINER)
                .pattern("SMS")
                .pattern("SMS")
                .pattern("LLL")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('L', ItemTags.LOGS)
                .define('M', ModTags.Items.MESHES)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.RODS_WOODEN))
                .save(consumer);

        //STONE
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.STONE)
                .pattern("SS")
                .pattern("SS")
                .define('S', ModItems.STONE_PEBBLE)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.RODS_WOODEN))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "stone_from_pebble"));

        //Ore Mulch
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ORE_MULCH)
                .pattern("SGS")
                .pattern("GMG")
                .pattern("SGS")
                .define('S', ItemTags.SAND)
                .define('G', Blocks.GRAVEL)
                .define('M', ModBlocks.MULCH)
                .group("strainers")
                .unlockedBy("has_item", has(Blocks.GRAVEL))
                .save(consumer);

        //

        //

        // ********** Mesh Upgrades ********** //



        // ********** Output Upgrades ********** //

        OutputUpgradesRecipeBuilder.OutputUpgradesRecipeBuilder(Ingredient.of(Items.EMERALD), 0.1)
                .unlockedBy("has_item", hasItems(Items.DIAMOND))
                .save(consumer);

        // ********** Strainer Recipes ********** //

        // Leaves //

        StrainerRecipeBuilder.strainerRecipe(Blocks.WATER.defaultBlockState(),
                        Ingredient.of(ItemTags.LEAVES),
                        1, 3, 0.2, LEAVES_RESULTS)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "strainer/water_and_leaves"));

        /*
        // MUD (WATER) //
        StrainerRecipeBuilder.strainerRecipe(Blocks.WATER.defaultBlockState(),
                        Ingredient.of(Blocks.MUD),
                        Ingredient.of(ModTags.Items.TIER_1_MESHES), 100, 0.1,
                        NonNullList.of(new ChanceResult(new ItemStack(Items.CLAY_BALL), 0.7f)))
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "strainer/water_and_mud"));

        // MYCELIUM  (LAVA) //
        StrainerRecipeBuilder.strainerRecipe(Blocks.LAVA.defaultBlockState(),
                        Ingredient.of(Blocks.GRASS_BLOCK),
                        Ingredient.of(ModTags.Items.TIER_4_MESHES), 100, 0.1,
                        NonNullList.of(
                                new ChanceResult(new ItemStack(Items.CRIMSON_NYLIUM), 0.25f),
                                new ChanceResult(new ItemStack(Items.WARPED_NYLIUM), 0.7f)))
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "strainer/lava_and_grass_block"));

        // MYCELIUM  (EROD) //
        StrainerRecipeBuilder.strainerRecipe(StrainersFluids.ERODING_WATER.getBlock().defaultBlockState(),
                        Ingredient.of(Blocks.GRASS_BLOCK),
                        Ingredient.of(ModTags.Items.TIER_3_MESHES), 100, 0.1,
                        NonNullList.of(
                                new ChanceResult(new ItemStack(Items.BROWN_MUSHROOM), 0.2f),
                                new ChanceResult(new ItemStack(Items.RED_MUSHROOM), 0.2f)))
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "strainer/eroding_water_and_grass_block"));

        // GRASS BLOCK (EROD) //

         */

        /*

        StrainerRecipeBuilder.strainerRecipe(StrainersFluids.ERODING_WATER.getBlock().defaultBlockState(),
                        Ingredient.of(Blocks.GRASS_BLOCK),
                        Ingredient.of(ModTags.Items.TIER_1_MESHES), 100,
                        NonNullList.of(
                                new ChanceResult(new ItemStack(Items.MYCELIUM), 0.8f)))
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "strainer/eroding_water_and_grass_block"));



        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.GRASS_BLOCK), "strainers:eroding_water", SizedIngredient.of(Blocks.MYCELIUM,1), 1, 0.8)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // DIRT (PURE)

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.DIRT), "strainers:purifying_water", SizedIngredient.of(Items.GRASS_BLOCK,1), 4, 0.8)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // DIRT (WATER) //

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.DIRT), "minecraft:water", SizedIngredient.of(ModItems.STONE_PEBBLE.get(),1), 1, 0.2)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // DIRT (LAVA)

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.DIRT), "minecraft:lava", SizedIngredient.of(Blocks.SOUL_SOIL,1), 4, 0.8)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        //STONE (PURE) //
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.STONE), "strainers:purifying_water", SizedIngredient.of(Blocks.GRANITE,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.STONE), "strainers:purifying_water", SizedIngredient.of(Blocks.ANDESITE,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.STONE), "strainers:purifying_water", SizedIngredient.of(Blocks.DIORITE,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.STONE), "strainers:purifying_water", SizedIngredient.of(Blocks.CALCITE,1), 2, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.STONE), "strainers:purifying_water", SizedIngredient.of(Blocks.DRIPSTONE_BLOCK,1), 2, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.STONE), "strainers:purifying_water", SizedIngredient.of(Blocks.TUFF,1), 2, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.STONE), "strainers:purifying_water", SizedIngredient.of(Blocks.DEEPSLATE,1), 3, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // DEEPSLATE (WATER) //
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.STONE), "minecraft:water", SizedIngredient.of(Items.ECHO_SHARD,1), 4, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // NETHERRACK (LAVA) //
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.STONE), "minecraft:lava", SizedIngredient.of(Blocks.BASALT, 1), 3, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.STONE), "minecraft:lava", SizedIngredient.of(Blocks.BLACKSTONE,1), 4, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.STONE), "minecraft:lava", SizedIngredient.of(Blocks.GILDED_BLACKSTONE,1), 5, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // STONE (EROD) //
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.STONE), "strainers:eroding_water", SizedIngredient.of(Blocks.COBBLESTONE,1), 1, 0.8)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // STONE (LAVA) //
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.STONE), "minecraft:lava", SizedIngredient.of(Blocks.NETHERRACK,1), 4, 0.8)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // COBBLESTONE //

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.COBBLESTONE), "strainers:eroding_water", SizedIngredient.of(Blocks.GRAVEL,1), 1, 0.8)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // GRAVEL //

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.GRAVEL), "strainers:eroding_water", SizedIngredient.of(Blocks.SAND,1), 1, 0.8)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // Mulch (WATER) //

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.MULCH), "minecraft:water", SizedIngredient.of(Items.WHEAT_SEEDS,1), 1, 0.20)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.MULCH), "minecraft:water", SizedIngredient.of(Items.BEETROOT_SEEDS,1), 2, 0.20)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.MULCH), "minecraft:water", SizedIngredient.of(Items.MELON_SEEDS,1), 3, 0.20)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.MULCH), "minecraft:water", SizedIngredient.of(Items.PUMPKIN_SEEDS,1), 3, 0.20)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // Mulch (PURE) //

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.MULCH), "strainers:purifying_water", SizedIngredient.of(Items.CARROT,1), 3, 0.20)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.MULCH), "strainers:purifying_water", SizedIngredient.of(Items.POTION,1), 3, 0.20)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.MULCH), "strainers:purifying_water", SizedIngredient.of(Items.COCOA_BEANS,1), 4, 0.20)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.MULCH), "strainers:purifying_water", SizedIngredient.of(Items.TORCHFLOWER_SEEDS,1), 6, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.MULCH), "strainers:purifying_water", SizedIngredient.of(Items.PITCHER_POD,1), 6, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // GRASS (PURE) //
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SHORT_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.POPPY,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SHORT_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.DANDELION,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SHORT_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.BLUE_ORCHID,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SHORT_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.ALLIUM,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SHORT_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.AZURE_BLUET,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SHORT_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.ORANGE_TULIP,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SHORT_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.PINK_TULIP,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SHORT_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.RED_TULIP,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SHORT_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.WHITE_TULIP,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SHORT_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.CORNFLOWER,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SHORT_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.LILY_OF_THE_VALLEY,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SHORT_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.TORCHFLOWER,1), 6, 0.05)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SHORT_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.WITHER_ROSE,1), 6, 0.01)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // TALL GRASS (PURE) //

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.TALL_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.SUNFLOWER,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.TALL_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.LILAC,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.TALL_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.ROSE_BUSH,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.TALL_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.PEONY,1), 1, 0.10)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.TALL_GRASS), "strainers:purifying_water", SizedIngredient.of(Items.PITCHER_PLANT,1), 6, 0.05)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        //SOUL SAND (WATER) //

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SOUL_SAND), "minecraft:lava", SizedIngredient.of(Blocks.NETHER_WART,1), 3, 0.3)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SOUL_SAND), "minecraft:lava", SizedIngredient.of(Blocks.WARPED_FUNGUS,1), 4, 0.2)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SOUL_SAND), "minecraft:lava", SizedIngredient.of(Blocks.CRIMSON_FUNGUS,1), 4, 0.2)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // SAND (LAVA) //

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "minecraft:lava", SizedIngredient.of(Blocks.SOUL_SAND,1), 4, 0.8)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);


        // SAND (WATER) //

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "minecraft:water", SizedIngredient.of(Items.SUGAR_CANE,1), 2, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "minecraft:water", SizedIngredient.of(Items.CACTUS,1), 2, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "minecraft:water", SizedIngredient.of(Items.LILY_PAD,1), 3, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "minecraft:water", SizedIngredient.of(Items.KELP,1), 3, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // SAND (PURE) //
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "strainers:purifying_water", SizedIngredient.of(Items.TUBE_CORAL_FAN,1), 3, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "strainers:purifying_water", SizedIngredient.of(Items.TUBE_CORAL,1), 3, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "strainers:purifying_water", SizedIngredient.of(Items.HORN_CORAL_FAN,1), 3, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "strainers:purifying_water", SizedIngredient.of(Items.HORN_CORAL,1), 3, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "strainers:purifying_water", SizedIngredient.of(Items.FIRE_CORAL_FAN,1), 3, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "strainers:purifying_water", SizedIngredient.of(Items.FIRE_CORAL,1), 3, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "strainers:purifying_water", SizedIngredient.of(Items.BUBBLE_CORAL_FAN,1), 3, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "strainers:purifying_water", SizedIngredient.of(Items.BUBBLE_CORAL,1), 3, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "strainers:purifying_water", SizedIngredient.of(Items.BRAIN_CORAL_FAN,1), 3, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Blocks.SAND), "strainers:purifying_water", SizedIngredient.of(Items.BRAIN_CORAL,1), 3, 0.15)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // CORAL BLOCKS (PURE) //

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Items.BRAIN_CORAL_FAN), "strainers:purifying_water", SizedIngredient.of(Items.BRAIN_CORAL_BLOCK,1), 4, 0.8)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Items.BUBBLE_CORAL_FAN), "strainers:purifying_water", SizedIngredient.of(Items.BUBBLE_CORAL_BLOCK,1), 4, 0.8)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Items.FIRE_CORAL_FAN), "strainers:purifying_water", SizedIngredient.of(Items.FIRE_CORAL_BLOCK,1), 4, 0.8)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Items.HORN_CORAL_FAN), "strainers:purifying_water", SizedIngredient.of(Items.HORN_CORAL_BLOCK,1), 4, 0.8)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(Items.TUBE_CORAL_FAN), "strainers:purifying_water", SizedIngredient.of(Items.TUBE_CORAL_BLOCK,1), 4, 0.8)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // ORE MULCH T1//
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.COAL_ORE_PIECE.get(),1), 1, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // ORE MULCH T2
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.COPPER_ORE_PIECE.get(),1), 2, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.TIN_ORE_PIECE.get(),1), 2, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.ALUMINUM_ORE_PIECE.get(),1), 2, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(aluminumIngotTag))));
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.ZINC_ORE_PIECE.get(),1), 2, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(zincIngotTag))));


        // ORE MULCH T3
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.IRON_ORE_PIECE.get(),1), 3, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.LAPIS_ORE_PIECE.get(),1), 3, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // ORE MULCH T4
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.GOLD_ORE_PIECE.get(),1), 4, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.SILVER_ORE_PIECE.get(),1), 4, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(silverIngotTag))));
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.REDSTONE_ORE_PIECE.get(),1), 4, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.OSMIUM_ORE_PIECE.get(),1), 4, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(osmiumIngotTag))));
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:lava", SizedIngredient.of(ModItems.QUARTZ_ORE_PIECE.get(),1), 4, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);

        // ORE MULCH T5
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.DIAMOND_ORE_PIECE.get(),1), 5, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.EMERALD_ORE_PIECE.get(),1), 5, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.URANIUM_ORE_PIECE.get(),1), 5, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(uraniumIngotTag))));

        // ORE MULCH T6
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:lava", SizedIngredient.of(ModItems.DEBRIS_ORE_PIECE.get(),1), 6, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer);
        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModBlocks.ORE_MULCH), "minecraft:water", SizedIngredient.of(ModItems.PLATINUM_ORE_PIECE.get(),1), 6, 0.35)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(platinumIngotTag))));

        // ORE PIECES TO NUGGETS //

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.TIN_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(tinNuggetTag,1), 1, 1)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(tinNuggetTag))), "strainers:strainer/pieces_to_resources/tin");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.COPPER_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(copperNuggetTag,1), 1, 1)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(copperNuggetTag))), "strainers:strainer/pieces_to_resources/copper");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.ALUMINUM_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(aluminumNuggetTag,1), 1, 1)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(aluminumNuggetTag))), "strainers:strainer/pieces_to_resources/aluminum");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.ZINC_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(zincNuggetTag,1), 1, 1)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(zincNuggetTag))), "strainers:strainer/pieces_to_resources/zinc");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.OSMIUM_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(osmiumNuggetTag,1), 1, 1)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(osmiumNuggetTag))), "strainers:strainer/pieces_to_resources/osmium");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.SILVER_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(silverNuggetTag,1), 1, 1)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(silverNuggetTag))), "strainers:strainer/pieces_to_resources/silver");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.URANIUM_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(uraniumNuggetTag,1), 1, 1)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(uraniumNuggetTag))), "strainers:strainer/pieces_to_resources/uranium");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.PLATINUM_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(platinumNuggetTag,1), 1, 1)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(platinumNuggetTag))), "strainers:strainer/pieces_to_resources/platinum");

        //StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.COAL_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(com.benbenlaw.opolisutilities.item.ModItems.MINI_COAL,1), 1, 1)
        //        .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
        //        .save(consumer, "strainers:strainer/pieces_to_resources/coal");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.IRON_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(ironNuggetTag,1), 1, 1)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(ironNuggetTag))), "strainers:strainer/pieces_to_resources/iron");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.LAPIS_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(Items.LAPIS_LAZULI,1), 1, 0.2)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer, "strainers:strainer/pieces_to_resources/lapis");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.GOLD_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(goldNuggetTag,1), 1, 1)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(goldNuggetTag))), "strainers:strainer/pieces_to_resources/gold");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.LEAD_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(leadNuggetTag,1), 1, 1)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(leadNuggetTag))), "strainers:strainer/pieces_to_resources/lead");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.NICKEL_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(nickelNuggetTag,1), 1, 1)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(nickelNuggetTag))), "strainers:strainer/pieces_to_resources/nickel");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.DIAMOND_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(Items.DIAMOND,1), 1, 0.2)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer, "strainers:strainer/pieces_to_resources/diamond");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.EMERALD_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(Items.EMERALD,1), 1, 0.2)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer, "strainers:strainer/pieces_to_resources/emerald");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.QUARTZ_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(Items.QUARTZ,1), 1, 0.2)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer, "strainers:strainer/pieces_to_resources/quartz");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.REDSTONE_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(Items.REDSTONE,1), 1, 0.2)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer, "strainers:strainer/pieces_to_resources/redstone");

        StrainerRecipeBuilder.strainerRecipe(Ingredient.of(ModItems.DEBRIS_ORE_PIECE), "strainers:purifying_water", SizedIngredient.of(Items.ANCIENT_DEBRIS,1), 1, 0.1)
                .unlockedBy("has_item", has(ModBlocks.WOODEN_STRAINER))
                .save(consumer, "strainers:strainer/pieces_to_resources/debris");


         */





    }

}
