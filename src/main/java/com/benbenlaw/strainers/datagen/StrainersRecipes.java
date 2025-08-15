package com.benbenlaw.strainers.datagen;

import com.benbenlaw.core.item.CoreItems;
import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.core.tag.CommonTags;
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
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
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
                .define('L', ItemTags.DIRT)
                .define('S', ItemTags.SAPLINGS)
                .group("strainers")
                .unlockedBy("has_item", has(ItemTags.LEAVES))
                .save(consumer);

        //Dirt
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.DIRT)
                .pattern(" L ")
                .pattern("LSL")
                .pattern(" L ")
                .define('L', ItemTags.LEAVES)
                .define('S', ItemTags.SAPLINGS)
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
                .define('B', Items.BLAZE_POWDER)
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
                .define('B', CommonTags.getTag("bronze", CommonTags.ResourceType.INGOTS))
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(CommonTags.getTag("bronze", CommonTags.ResourceType.INGOTS)))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(CommonTags.getTag("bronze", CommonTags.ResourceType.INGOTS)))));

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
                .define('B', CommonTags.getTag("tin", CommonTags.ResourceType.INGOTS))
                .define('S', Tags.Items.RODS_WOODEN)
                .group("strainers")
                .unlockedBy("has_item", has(CommonTags.getTag("tin", CommonTags.ResourceType.INGOTS)))
                .save(consumer.withConditions(new NotCondition(new TagEmptyCondition(CommonTags.getTag("tin", CommonTags.ResourceType.INGOTS)))));

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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.COBBLESTONE)
                .pattern("SS")
                .pattern("SS")
                .define('S', ModItems.STONE_PEBBLE)
                .group("strainers")
                .unlockedBy("has_item", has(Tags.Items.RODS_WOODEN))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "stone_from_pebble"));

        // ********** Strainer Recipes ********** //

        // Dirt -> Resources
        for (int tier = 1; tier <= 10; tier++) {
            float chance = 0.05f * tier;

            NonNullList<ChanceResult> results = NonNullList.create();

            results.add(new ChanceResult(new ItemStack(ModItems.STONE_PEBBLE.get(), 3), 1.0f));
            results.add(new ChanceResult(new ItemStack(ModItems.STONE_PEBBLE.get()), chance));

            StrainerRecipeBuilder.strainerRecipe(
                            Blocks.WATER.defaultBlockState(),
                            Ingredient.of(ItemTags.DIRT),
                            ModTags.getMeshIngredient(tier),
                            results)
                    .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(
                            Strainers.MOD_ID,
                            "strainer/dirt/water/tier_" + tier));
        }

        //Gravel -> Sand and Flint
        for (int tier = 1; tier <= 10; tier++) {
            float chance = 0.02f * tier;

            NonNullList<ChanceResult> results = NonNullList.create();

            results.add(new ChanceResult(new ItemStack(Items.SAND), 1.0f));
            results.add(new ChanceResult(new ItemStack(Items.FLINT), chance));

            StrainerRecipeBuilder.strainerRecipe(
                            StrainersFluids.ERODING_WATER.getBlock().defaultBlockState(),
                            Ingredient.of(Items.GRAVEL),
                            ModTags.getMeshIngredient(tier),
                            results)
                    .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(
                            Strainers.MOD_ID,
                            "strainer/gravel/eroding/tier_" + tier));
        }

        //Purified Dirt -> Resources
        for (int tier = 1; tier <= 10; tier++) {
            NonNullList<ChanceResult> results = NonNullList.create();

            results.add(new ChanceResult(new ItemStack(Items.WHEAT_SEEDS), 0.35f + ((0.05f * tier) - 0.05f)));

            if (tier >= 3) {
                results.add(new ChanceResult(new ItemStack(Items.BONE_MEAL), 0.35f + ((0.05f * tier) - 0.05f)));
            }

            StrainerRecipeBuilder.strainerRecipe(
                            Blocks.WATER.defaultBlockState(),
                            Ingredient.of(ModBlocks.PURIFIED_DIRT),
                            ModTags.getMeshIngredient(tier),
                            results)
                    .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(
                            Strainers.MOD_ID,
                            "strainer/purified_dirt/water/tier_" + tier));
        }

        //Purified Sand -> Resources
        for (int tier = 1; tier <= 10; tier++) {
            NonNullList<ChanceResult> results = NonNullList.create();


            if (tier >= 2) {
                results.add(new ChanceResult(new ItemStack(ModItems.ALUMINUM_ORE_PIECE.get()), 0.4f + ((0.05f * tier) - 0.05f)));
            }

            if (tier >= 3) {
                results.add(new ChanceResult(new ItemStack(ModItems.TIN_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));

            }

            if (tier >= 4) {
                results.add(new ChanceResult(new ItemStack(ModItems.REDSTONE_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));
                results.add(new ChanceResult(new ItemStack(ModItems.SILVER_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));

            }

            if (tier >= 5) {
                results.add(new ChanceResult(new ItemStack(ModItems.NICKEL_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));
            }

            if (tier >= 6) {
                results.add(new ChanceResult(new ItemStack(Items.AMETHYST_SHARD), 0.2f + ((0.1f * tier) - 0.1f)));
            }
            if (tier >= 8) {
                results.add(new ChanceResult(new ItemStack(ModItems.URANIUM_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));

                results.add(new ChanceResult(new ItemStack(Items.PRISMARINE_CRYSTALS), 0.6f + ((0.1f * tier) - 0.6f)));
                results.add(new ChanceResult(new ItemStack(Items.PRISMARINE_SHARD), 0.6f + ((0.1f * tier) - 0.6f)));
            }

            StrainerRecipeBuilder.strainerRecipe(
                            Blocks.WATER.defaultBlockState(),
                            Ingredient.of(ModBlocks.PURIFIED_SAND),
                            ModTags.getMeshIngredient(tier),
                            results)
                    .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(
                            Strainers.MOD_ID,
                            "strainer/purified_sand/water/tier_" + tier));
        }

        //Purified Gravel -> Resources
        for (int tier = 1; tier <= 10; tier++) {
            NonNullList<ChanceResult> results = NonNullList.create();

            if (tier >= 2) {
                results.add(new ChanceResult(new ItemStack(ModItems.COAL_ORE_PIECE.get()), 0.4f + ((0.05f * tier) - 0.05f)));
            }

            if (tier >= 3) {
                results.add(new ChanceResult(new ItemStack(ModItems.COPPER_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));
                results.add(new ChanceResult(new ItemStack(ModItems.IRON_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));
            }

            if (tier >= 4) {
                results.add(new ChanceResult(new ItemStack(ModItems.ZINC_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));
                results.add(new ChanceResult(new ItemStack(ModItems.GOLD_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));
            }
            if (tier >= 5) {
                results.add(new ChanceResult(new ItemStack(ModItems.LEAD_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));
                results.add(new ChanceResult(new ItemStack(ModItems.LAPIS_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));
            }
            if (tier >= 6) {
                results.add(new ChanceResult(new ItemStack(ModItems.OSMIUM_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));
            }
            if (tier >= 7) {
                results.add(new ChanceResult(new ItemStack(ModItems.PLATINUM_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));
            }
            if (tier >= 8) {
                results.add(new ChanceResult(new ItemStack(ModItems.DIAMOND_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));
                results.add(new ChanceResult(new ItemStack(ModItems.EMERALD_ORE_PIECE.get()), 0.35f + ((0.05f * tier) - 0.05f)));
            }

            StrainerRecipeBuilder.strainerRecipe(
                            Blocks.WATER.defaultBlockState(),
                            Ingredient.of(ModBlocks.PURIFIED_GRAVEL),
                            ModTags.getMeshIngredient(tier),
                            results)
                    .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(
                            Strainers.MOD_ID,
                            "strainer/purified_gravel/water/tier_" + tier));
        }

        //Purified Soul Sand -> Resources
        for (int tier = 5; tier <= 10; tier++) {
            NonNullList<ChanceResult> results = NonNullList.create();

            if (tier >= 7) {
                results.add(new ChanceResult(new ItemStack(Items.BLAZE_POWDER), 0.4f + ((0.05f * tier) - 0.05f)));
            }

            StrainerRecipeBuilder.strainerRecipe(
                            Blocks.WATER.defaultBlockState(),
                            Ingredient.of(ModBlocks.PURIFIED_SOUL_SAND),
                            ModTags.getMeshIngredient(tier),
                            results)
                    .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(
                            Strainers.MOD_ID,
                            "strainer/purified_soul_sand/water/tier_" + tier));
        }

        //Gravel -> Purified Gravel
        createSimpleStrainerRecipe(consumer, ModBlocks.PURIFIED_GRAVEL.asItem(), StrainersFluids.PURIFYING_WATER.getFluid().defaultFluidState(),
                Ingredient.of(Items.GRAVEL), "gravel/purifying");

        //Dirt -> Purified Dirt
        createSimpleStrainerRecipe(consumer, ModBlocks.PURIFIED_DIRT.asItem(), StrainersFluids.PURIFYING_WATER.getFluid().defaultFluidState(),
                Ingredient.of(Items.DIRT), "dirt/purifying");

        //Sand -> Purified Sand
        createSimpleStrainerRecipe(consumer, ModBlocks.PURIFIED_SAND.asItem(), StrainersFluids.PURIFYING_WATER.getFluid().defaultFluidState(),
                Ingredient.of(ItemTags.SAND), "sand/purifying");

        //Cobblestone -> Gravel
        createSimpleStrainerRecipe(consumer, Items.GRAVEL, StrainersFluids.ERODING_WATER.getFluid().defaultFluidState(),
                Ingredient.of(Tags.Items.COBBLESTONES), "cobblestone/eroding");


        //Purifying Sand -> Soul Sand
        createSimpleStrainerRecipe(consumer, Items.SOUL_SAND, StrainersFluids.ERODING_WATER.getFluid().defaultFluidState(),
                Ingredient.of(ModBlocks.PURIFIED_SAND), "purified_sand/eroding");

        //Purifying Dirt -> Soul Soil
        createSimpleStrainerRecipe(consumer, Items.SOUL_SOIL, StrainersFluids.ERODING_WATER.getFluid().defaultFluidState(),
                Ingredient.of(ModBlocks.PURIFIED_DIRT), "purified_dirt/eroding" );


    }

    public void createSimpleStrainerRecipe(RecipeOutput consumer, Item output, FluidState fluidState, Ingredient input, String id) {
        NonNullList<ChanceResult> result = NonNullList.create();
        result.add(new ChanceResult(output.getDefaultInstance(), 1.0f));
        StrainerRecipeBuilder.strainerRecipe(
                        fluidState.createLegacyBlock(),
                        input,
                        Ingredient.of(ModTags.Items.MESHES),
                        result)
                .unlockedBy("has_item", hasItems(ModBlocks.WOODEN_STRAINER))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(
                        Strainers.MOD_ID,
                        "strainer/" + id + "/all_tiers"));
    }

}
