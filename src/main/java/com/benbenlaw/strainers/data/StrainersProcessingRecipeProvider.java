package com.benbenlaw.strainers.data;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.StrainersBlocks;
import com.benbenlaw.strainers.data.recipes.StrainerRecipeBuilder;
import com.benbenlaw.strainers.item.StrainersItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
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

        //Mulch
        StrainerRecipeBuilder.create()
                .input(StrainersBlocks.MULCH)
                .output(StrainersItems.STONE_PEBBLE.get(), 3, 1.0f)
                .tier(1, 0.5f).save(output, "mulch/stone_pebble");

        StrainerRecipeBuilder.create()
                .input(StrainersBlocks.MULCH)
                .output(StrainersItems.DIRT_PILE.get(), 3, 1.0f)
                .tier(1, 0.5f).save(output, "mulch/dirt_pile");

        StrainerRecipeBuilder.create()
                .input(StrainersBlocks.MULCH)
                .output(StrainersItems.WATER_DROP.get(), 1, 0.5f)
                .tier(1, 0.1f)
                .save(output, "mulch/water_drop");

        StrainerRecipeBuilder.create()
                .input(StrainersBlocks.MULCH)
                .output(Items.BONE_MEAL, 1, 0.4f)
                .tier(1, 0.1f)
                .save(output, "mulch/bone_meal");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.MULCH)
                .output(StrainersItems.SAPLING_BAG.get(), 1, 0.2f)
                .tier(2, 0.05f)
                .save(output, "mulch/gravel_pebble");

        //Leaves
        StrainerRecipeBuilder.create()
                .input(ItemTags.LEAVES, this.registries)
                .output(StrainersItems.STONE_PEBBLE.get(), 1, 1.0f)
                .tier(1, 0.25f)
                .save(output, "leaves/stone_pebble");

        StrainerRecipeBuilder.create()
                .input(ItemTags.LEAVES, this.registries)
                .output(Items.STICK, 1, 0.75f)
                .tier(1, 0.25f)
                .save(output, "leaves/stick");

        StrainerRecipeBuilder.create()
                .input(ItemTags.LEAVES, this.registries)
                .output(StrainersItems.LEAF_PILE.get(), 2, 1f)
                .tier(1, 1.0f)
                .save(output, "leaves/leaf_pile");

        StrainerRecipeBuilder.create().water()
                .input(ItemTags.LEAVES, this.registries)
                .output(StrainersItems.SAPLING_BAG.get(), 1, 0.1f)
                .tier(1, 0.05f)
                .save(output, "leaves/sapling_seed");

        //Dirt
        StrainerRecipeBuilder.create()
                .input(ItemTags.DIRT, this.registries)
                .output(StrainersItems.STONE_PEBBLE.get(), 1, 0.75f)
                .tier(1, 0.25f)
                .save(output, "dirt/stone_pebble");

        StrainerRecipeBuilder.create()
                .input(ItemTags.DIRT, this.registries)
                .output(Items.STICK, 1, 0.75f)
                .tier(1, 0.25f)
                .save(output, "dirt/stick");

        StrainerRecipeBuilder.create()
                .input(ItemTags.DIRT, this.registries)
                .output(StrainersItems.GRAVEL_PEBBLE.get(), 1, 0.75f)
                .tier(1, 0.25f)
                .save(output, "dirt/gravel_pebble");

        StrainerRecipeBuilder.create().eroding()
                .input(ItemTags.DIRT, this.registries)
                .output(Items.SOUL_SOIL, 1, 1.0f)
                .tier(6, 0.0f)
                .save(output, "dirt/soul_soil");


        //Gravel
        StrainerRecipeBuilder.create().water()
                .input(Items.GRAVEL)
                .output(StrainersItems.PURIFYING_DROP.get(), 1, 0.1f)
                .tier(1, 0.05f)
                .save(output, "gravel/purifying_drop");

        StrainerRecipeBuilder.create().water()
                .input(Items.GRAVEL)
                .output(StrainersItems.ERODING_DROP.get(), 1, 0.1f)
                .tier(1, 0.05f)
                .save(output, "gravel/eroding_drop");

        //Sand
        StrainerRecipeBuilder.create().water()
                .input(Tags.Items.SANDS, registries)
                .output(StrainersItems.SALT_WATER_DROP.get(), 1, 0.1f)
                .tier(1, 0.05f)
                .save(output, "sand/salt_water_drop");

        StrainerRecipeBuilder.create().water()
                .input(Tags.Items.SANDS, registries)
                .output(StrainersItems.SAND_DUST.get(), 2, 1.0f)
                .tier(1, 0.0f)
                .save(output, "sand/sand_dust");

        StrainerRecipeBuilder.create().eroding()
                .input(Tags.Items.SANDS, registries)
                .output(StrainersBlocks.DUST_BLOCK.get().asItem(), 1, 1.0f)
                .tier(1, 0.0f)
                .save(output, "sand/dust_block");

        StrainerRecipeBuilder.create().eroding()
                .input(Tags.Items.SANDS, registries)
                .output(Items.SOUL_SAND, 1, 1.0f)
                .tier(6, 0.0f)
                .save(output, "sand/soul_sand");

        //Sandstone
        StrainerRecipeBuilder.create().purifying()
                .input(Blocks.SANDSTONE)
                .output(Items.END_STONE, 1, 1.0f)
                .tier(2, 0.0f)
                .save(output, "sandstone/end_stone");

        //Blocks to Purified versions
        StrainerRecipeBuilder.create().purifying()
                .input(Blocks.DIRT)
                .output(StrainersBlocks.PURIFIED_DIRT.get().asItem(), 1, 1.0f)
                .tier(2, 0.0f)
                .save(output, "dirt/purified_dirt");

        StrainerRecipeBuilder.create().purifying()
                .input(Blocks.GRAVEL)
                .output(StrainersBlocks.PURIFIED_GRAVEL.get().asItem(), 1, 1.0f)
                .tier(2, 0.0f)
                .save(output, "gravel/purified_gravel");

        StrainerRecipeBuilder.create().purifying()
                .input(Blocks.SAND)
                .output(StrainersBlocks.PURIFIED_SAND.get().asItem(), 1, 1.0f)
                .tier(2, 0.0f)
                .save(output, "sand/purified_sand");

        StrainerRecipeBuilder.create().purifying()
                .input(StrainersBlocks.DUST_BLOCK)
                .output(StrainersBlocks.PURIFIED_DUST_BLOCK.get().asItem(), 1, 1.0f)
                .tier(3, 0.0f)
                .save(output, "dust_block/purified_dust_block");

        StrainerRecipeBuilder.create().purifying()
                .input(Blocks.NETHERRACK)
                .output(StrainersBlocks.PURIFIED_NETHERRACK.get().asItem(), 1, 1.0f)
                .tier(4, 0.0f)
                .save(output, "netherrack/purified_netherrack");

        StrainerRecipeBuilder.create().purifying()
                .input(Blocks.SOUL_SAND)
                .output(StrainersBlocks.PURIFIED_SOUL_SAND.get().asItem(), 1, 1.0f)
                .tier(4, 0.0f)
                .save(output, "soul_sand/purified_soul_sand");

        StrainerRecipeBuilder.create().purifying()
                .input(Blocks.SOUL_SOIL)
                .output(StrainersBlocks.PURIFIED_SOUL_SOIL.get().asItem(), 1, 1.0f)
                .tier(4, 0.0f)
                .save(output, "soul_soil/purified_soul_soil");

        StrainerRecipeBuilder.create().purifying()
                .input(Blocks.STONE)
                .output(StrainersBlocks.PURIFIED_STONE.get().asItem(), 1, 1.0f)
                .tier(4, 0.0f)
                .save(output, "stone/purified_stone");

        StrainerRecipeBuilder.create().purifying()
                .input(Blocks.DEEPSLATE)
                .output(StrainersBlocks.PURIFIED_DEEPSLATE.get().asItem(), 1, 1.0f)
                .tier(4, 0.0f)
                .save(output, "stone/purified_deepslate");

        StrainerRecipeBuilder.create().purifying()
                .input(Blocks.END_STONE)
                .output(StrainersBlocks.PURIFIED_END_STONE.get().asItem(), 1, 1.0f)
                .tier(8, 0.0f)
                .save(output, "end_stone/purified_end_stone");

        //Purified Dirt
        StrainerRecipeBuilder.create().purifying()
                .input(StrainersBlocks.PURIFIED_DIRT)
                .output(Blocks.GRASS_BLOCK.asItem(), 1, 1.0f)
                .tier(7, 0.0f)
                .save(output, "purified_dirt/dirt");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_DIRT)
                .output(StrainersItems.SEED_BAG.asItem(), 1, 0.1f)
                .tier(1, 0.1f)
                .save(output, "purified_dirt/seed_bag");

        //Purified Netherrack
        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_NETHERRACK)
                .output(Blocks.BASALT.asItem(), 1, 0.1f)
                .tier(4, 0.1f)
                .save(output, "purified_netherrack/basalt");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_NETHERRACK)
                .output(Blocks.BLACKSTONE.asItem(), 1, 0.1f)
                .tier(4, 0.1f)
                .save(output, "purified_netherrack/blackstone");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_NETHERRACK)
                .output("quartz", 0.75f)
                .tier(3, 0.05f)
                .save(output, "purified_netherrack/quartz");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_NETHERRACK)
                .output("gold", 0.70f)
                .tier(4, 0.05f)
                .save(output, "purified_netherrack/gold");

        StrainerRecipeBuilder.create().water(25)
                .input(StrainersBlocks.PURIFIED_NETHERRACK)
                .output("netherite_scrap", 0.05f)
                .tier(7, 0.05f)
                .save(output, "purified_netherrack/netherite_scrap");

        //Purified Stone
        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_STONE)
                .output(Blocks.GRANITE.asItem(), 1, 0.1f)
                .tier(4, 0.1f)
                .save(output, "purified_stone/granite");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_STONE)
                .output(Blocks.DIORITE.asItem(), 1, 0.1f)
                .tier(4, 0.1f)
                .save(output, "purified_stone/diorite");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_STONE)
                .output(Blocks.ANDESITE.asItem(), 1, 0.1f)
                .tier(4, 0.1f)
                .save(output, "purified_stone/andesite");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_STONE)
                .output(Blocks.CALCITE.asItem(), 1, 0.1f)
                .tier(4, 0.1f)
                .save(output, "purified_stone/calcite");

        //Purified Soul Sand
        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_SOUL_SAND)
                .output(Items.NETHER_WART, 1, 0.1f)
                .tier(4, 0.1f)
                .save(output, "purified_soul_sand/nether_wart");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_SOUL_SAND)
                .output(Items.GLOWSTONE_DUST, 1, 0.2f)
                .tier(4, 0.1f)
                .save(output, "purified_soul_sand/glowstone");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_SOUL_SAND)
                .output(Items.BLAZE_POWDER, 1, 0.15f)
                .tier(5, 0.05f)
                .save(output, "purified_soul_sand/blaze_powder");

        //Purified Soul Soil
        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_SOUL_SOIL)
                .output(Items.CRIMSON_FUNGUS, 1, 0.1f)
                .tier(4, 0.1f)
                .save(output, "purified_soul_soil/crimson_fungus");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_SOUL_SOIL)
                .output(Items.WARPED_FUNGUS, 1, 0.1f)
                .tier(4, 0.1f)
                .save(output, "purified_soul_soil/warped_fungus");

        //Mud
        StrainerRecipeBuilder.create().water()
                .input(Items.MUD)
                .output(Items.CLAY_BALL, 1, 1f)
                .tier(1, 0.5f)
                .save(output, "mud/clay_ball");

        StrainerRecipeBuilder.create().water()
                .input(Items.MUD)
                .output(Items.CLAY_BALL, 1, 0.5f)
                .tier(1, 0.25f)
                .save(output, "mud/clay_ball_low");

        //Purified End Stone
        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_END_STONE)
                .output(Items.CHORUS_FRUIT, 1, 0.1f)
                .tier(4, 0.1f)
                .save(output, "purified_end_stone/chorus_fruit");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_END_STONE)
                .output(Items.ENDER_PEARL, 1, 0.05f)
                .tier(5, 0.05f)
                .save(output, "purified_end_stone/ender_pearl");

        //Cobblestone
        StrainerRecipeBuilder.create().water()
                .input(Tags.Items.COBBLESTONES, registries)
                .output(StrainersItems.GRAVEL_PEBBLE.get(), 1, 1f)
                .tier(1, 0.5f)
                .save(output, "cobblestone/gravel_pebble");

        StrainerRecipeBuilder.create().water()
                .input(Tags.Items.COBBLESTONES, registries)
                .output(StrainersItems.GRAVEL_PEBBLE.get(), 1, 0.5f)
                .tier(1, 0.25f)
                .save(output, "cobblestone/gravel_pebble_low");

        StrainerRecipeBuilder.create().eroding()
                .input(Tags.Items.COBBLESTONES, registries)
                .output(Items.GRAVEL, 1, 1.0f)
                .tier(1, 0.0f)
                .save(output, "cobblestone/gravel");

        //Gravel
        StrainerRecipeBuilder.create().eroding()
                .input(Tags.Items.GRAVELS, registries)
                .output(Items.SAND, 1, 1.0f)
                .tier(1, 0.0f)
                .save(output, "gravel/sand");

        //Stone
        StrainerRecipeBuilder.create().eroding()
                .input(Tags.Items.STONES, registries)
                .output(Items.NETHERRACK, 4, 1.0f)
                .tier(1, 0.0f)
                .save(output, "stone/netherrack");

        StrainerRecipeBuilder.create().eroding()
                .input(Tags.Items.STONES, registries)
                .output(Items.DEEPSLATE, 1, 1.0f)
                .tier(7, 0.0f)
                .save(output, "stone/deepslate");


        //Netherrack
        StrainerRecipeBuilder.create().eroding()
                .input(Tags.Items.NETHERRACKS, registries)
                .output(StrainersItems.LAVA_DROP.get(), 1, 0.1f)
                .tier(5, 0.1f)
                .save(output, "netherrack/lava_drop");

        //Gravel
        StrainerRecipeBuilder.create().water()
                .input(Tags.Items.GRAVELS, registries)
                .output(StrainersItems.SAND_DUST.get(), 1, 1.0f)
                .tier(1, 0.5f)
                .save(output, "gravel/sand_dust");

        StrainerRecipeBuilder.create().water()
                .input(Items.GRAVEL)
                .output(Items.FLINT, 1, 0.2f)
                .tier(1, 0.1f)
                .save(output, "gravel/flint");

        //Purified Sand
        StrainerRecipeBuilder.create().salty()
                .input(StrainersBlocks.PURIFIED_SAND)
                .output(Items.PRISMARINE_SHARD, 1, 0.5f)
                .tier(4, 0.1f)
                .save(output, "purified_sand/prismarine_shard");

        StrainerRecipeBuilder.create().salty()
                .input(StrainersBlocks.PURIFIED_SAND)
                .output(Items.PRISMARINE_CRYSTALS, 1, 0.3f)
                .tier(4, 0.05f)
                .save(output, "purified_sand/prismarine_crystal");

        StrainerRecipeBuilder.create().salty()
                .input(StrainersBlocks.PURIFIED_SAND)
                .output("salt", 0.3f)
                .tier(3, 0.05f)
                .save(output, "purified_sand/salt");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_SAND)
                .output(Items.SUGAR_CANE, 1, 0.1f)
                .tier(3, 0.1f)
                .save(output, "purified_sand/sugar_cane");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_SAND)
                .output(Items.CACTUS, 1, 0.1f)
                .tier(3, 0.1f)
                .save(output, "purified_sand/cactus");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_SAND)
                .output(Items.CACTUS_FLOWER, 1, 0.1f)
                .tier(3, 0.1f)
                .save(output, "purified_sand/cactus_flower");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_SAND)
                .output(Items.DEAD_BUSH, 1, 0.1f)
                .tier(3, 0.1f)
                .save(output, "purified_sand/dead_bush");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_SAND)
                .output(Items.DRY_TALL_GRASS, 1, 0.1f)
                .tier(3, 0.1f)
                .save(output, "purified_sand/dry_tall_grass");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_SAND)
                .output(Items.DRY_SHORT_GRASS, 1, 0.1f)
                .tier(3, 0.1f)
                .save(output, "purified_sand/dry_short_grass");

        //Purified Deepslate
        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_DEEPSLATE)
                .output(StrainersItems.SCULK_DUST.get(), 1, 0.75f)
                .tier(4, 0.15f)
                .save(output, "purified_deepslate/sculk_dust");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_DEEPSLATE)
                .output(Items.ECHO_SHARD, 1, 0.5f)
                .tier(4, 0.05f)
                .save(output, "purified_deepslate/echo_shard");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_DEEPSLATE)
                .output(Items.DISC_FRAGMENT_5, 1, 0.1f)
                .tier(5, 0.1f)
                .save(output, "purified_deepslate/disc_fragment_5");

        StrainerRecipeBuilder.create().water(2)
                .input(StrainersBlocks.PURIFIED_DEEPSLATE)
                .output(Items.GLOW_BERRIES, 1, 0.1f)
                .tier(4, 0.1f)
                .save(output, "purified_deepslate/glow_berries");

        //Purified Gravel
        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("copper", 0.75f)
                .tier(2, 0.05f)
                .save(output, "purified_gravel/copper");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("coal", 0.75f)
                .tier(2, 0.15f)
                .save(output, "purified_gravel/coal");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("tin", 0.75f)
                .tier(2, 0.15f)
                .save(output, "tin", "purified_gravel/tin");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("zinc", 0.75f)
                .tier(2, 0.15f)
                .save(output, "zinc", "purified_gravel/zinc");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("aluminum", 0.75f)
                .tier(2, 0.15f)
                .save(output, "aluminum", "purified_gravel/aluminum");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("lapis", 0.75f)
                .tier(3, 0.15f)
                .save(output, "purified_gravel/lapis");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("iron", 0.75f)
                .tier(3, 0.15f)
                .save(output, "purified_gravel/iron");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("lead", 0.75f)
                .tier(3, 0.15f)
                .save(output, "lead", "purified_gravel/lead");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("nickel", 0.75f)
                .tier(3, 0.15f)
                .save(output, "nickel", "purified_gravel/nickel");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("gold", 0.70f)
                .tier(4, 0.2f)
                .save(output, "purified_gravel/gold");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("silver", 0.70f)
                .tier(4, 0.2f)
                .save(output, "silver", "purified_gravel/silver");

        StrainerRecipeBuilder.create().water(10)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("diamond", 0.65f)
                .tier(5, 0.25f)
                .save(output, "purified_gravel/diamond");

        StrainerRecipeBuilder.create().water()
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("sulfur", 0.65f)
                .tier(5, 0.25f)
                .save(output, "sulfur", "purified_gravel/sulfur");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("osmium", 0.65f)
                .tier(5, 0.25f)
                .save(output, "osmium", "purified_gravel/osmium");

        StrainerRecipeBuilder.create().water(10)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("emerald", 0.60f)
                .tier(6, 0.3f)
                .save(output, "purified_gravel/emerald");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("topaz", 0.60f)
                .tier(6, 0.3f)
                .save(output, "topaz", "purified_gravel/topaz");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("peridot", 0.60f)
                .tier(6, 0.3f)
                .save(output, "peridot", "purified_gravel/peridot");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("ruby", 0.60f)
                .tier(6, 0.3f)
                .save(output, "ruby", "purified_gravel/ruby");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("sapphire", 0.60f)
                .tier(6, 0.3f)
                .save(output, "sapphire", "purified_gravel/sapphire");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("fluorite", 0.60f)
                .tier(6, 0.3f)
                .save(output, "fluorite", "purified_gravel/fluorite");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("platinum", 0.60f)
                .tier(6, 0.3f)
                .save(output, "platinum", "purified_gravel/platinum");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_GRAVEL)
                .output("uranium", 0.60f)
                .tier(6, 0.3f)
                .save(output, "uranium", "purified_gravel/uranium");


        //Purified Dust
        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_DUST_BLOCK)
                .output("redstone", 0.75f)
                .tier(3, 0.15f)
                .save(output, "purified_dust_block/redstone");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_DUST_BLOCK)
                .output(Items.AMETHYST_SHARD, 1, 0.15f)
                .tier(3, 0.105f)
                .save(output, "purified_dust_block/amethyst_shard");

        StrainerRecipeBuilder.create().water(5)
                .input(StrainersBlocks.PURIFIED_DUST_BLOCK)
                .output("cinnabar", 0.15f)
                .tier(5, 0.15f)
                .save(output, "cinnabar", "purified_dust_block/cinnabar");

        //Sherds
        BuiltInRegistries.ITEM.entrySet().stream()
                .filter(entry -> entry.getKey().identifier().getPath().endsWith("_pottery_sherd"))
                .forEach(entry -> {
                    Item item = entry.getValue();
                    String path = entry.getKey().identifier().getPath();

                    StrainerRecipeBuilder.create().eroding(5)
                            .input(ItemTags.TERRACOTTA, registries)
                            .output(item, 0.01f)
                            .tier(1, 0.01f)
                            .save(output, "sherds/" + path);
                });

        //Mod Support
        addSilentGems();
        addForbiddenArcanus();
        addPowah();
        addTheurgy();
        addAllTheModium();
        addMysticalAgriculture();
        addEvilCraft();
        addDimensionalResources();
    }

    public void addSilentGems() {

        List<String> ores = new ArrayList<>();
        ores.add("opal");
        ores.add("garnet");
        ores.add("tanzanite");
        ores.add("black_diamond");
        ores.add("azure_silver");
        ores.add("white_diamond");
        ores.add("bort");
        ores.add("ammolite");
        ores.add("turquoise");
        ores.add("aquamarine");
        ores.add("alexandrite");
        ores.add("carnelian");
        ores.add("citrin");
        ores.add("iolite");
        ores.add("kyanite");
        ores.add("pearl");

        for (String ore : ores) {
            StrainerRecipeBuilder.create().water(5)
                    .input(StrainersBlocks.PURIFIED_DUST_BLOCK)
                    .output(ore, 0.35f)
                    .tier(6, 0.25f)
                    .save(output, ore, "silent/purified_dust_block/" + ore);
        }

        List<String> netherOres = new ArrayList<>();
        netherOres.add("crimson_iron");
        netherOres.add("rose_quartz");

        for (String ore : netherOres) {
            StrainerRecipeBuilder.create().water(10)
                    .input(StrainersBlocks.PURIFIED_NETHERRACK)
                    .output(ore, 0.35f)
                    .tier(6, 0.25f)
                    .save(output, ore, "silent/purified_netherrack/" + ore);
        }
    }

    public void addForbiddenArcanus() {

        List<String> ores = new ArrayList<>();
        ores.add("runic");
        ores.add("stellarite");
        ores.add("arcane_crystal");

        for (String ore : ores) {
            StrainerRecipeBuilder.create().water()
                    .input(StrainersBlocks.PURIFIED_GRAVEL)
                    .output(ore, 0.35f)
                    .tier(6, 0.25f)
                    .save(output, ore, "forbiddenarcanus/purified_gravel/" + ore);
        }
    }

    public void addPowah() {

        List<String> ores = new ArrayList<>();
        ores.add("uraninite");
        ores.add("uraninite_dense");
        ores.add("uraninite_regular");
        ores.add("uraninite_poor");

        for (String ore : ores) {
            StrainerRecipeBuilder.create().water()
                    .input(StrainersBlocks.PURIFIED_GRAVEL)
                    .output(ore, 0.15f)
                    .tier(7, 0.1f)
                    .save(output, ore, "powah/purified_gravel/" + ore);
        }
    }
    public void addMysticalAgriculture() {

        List<String> ores = new ArrayList<>();
        ores.add("prosperity");
        ores.add("inferium");

        for (String ore : ores) {
            StrainerRecipeBuilder.create().water(15)
                    .input(StrainersBlocks.PURIFIED_DUST_BLOCK)
                    .output(ore, 0.2f)
                    .tier(7, 0.05f)
                    .save(output, ore, "purified_dust_block/" + ore);
        }

        StrainerRecipeBuilder.create().water(15)
                .input(StrainersBlocks.PURIFIED_SOUL_SAND)
                .output("soulium", 0.2f)
                .tier(5, 0.15f)
                .save(output, "soulium", "mysticalagriculture/purified_soul_sand/soulium");
    }

    public void addTheurgy() {

        List<String> ores = new ArrayList<>();
        ores.add("sal_ammoniac");

        for (String ore : ores) {
            StrainerRecipeBuilder.create().water(15)
                    .input(StrainersBlocks.PURIFIED_DUST_BLOCK)
                    .output(ore, 0.15f)
                    .tier(73, 0.05f)
                    .save(output, ore, "theurgy/purified_dust_block/" + ore);
        }
    }

    public void addAllTheModium() {

        List<String> ores = new ArrayList<>();
        ores.add("allthemodium");
        ores.add("vibranium");
        ores.add("unobtainium");

        StrainerRecipeBuilder.create().water(1000)
                .input(StrainersBlocks.PURIFIED_DEEPSLATE)
                .output("allthemodium", 0.01f)
                .tier(8, 0.01f)
                .save(output, "allthemodium", "allthemodium/purified_dust_block/allthemodium");

        StrainerRecipeBuilder.create().water(1000)
                .input(StrainersBlocks.PURIFIED_NETHERRACK)
                .output("vibranium", 0.01f)
                .tier(8, 0.01f)
                .save(output, "vibranium", "allthemodium/purified_netherrack/vibranium");

        StrainerRecipeBuilder.create().water(1000)
                .input(StrainersBlocks.PURIFIED_END_STONE)
                .output("unobtainium", 0.01f)
                .tier(8, 0.01f)
                .save(output, "unobtainium", "allthemodium/purified_end_stone/unobtainium");
    }

    public void addEvilCraft() {

        List<String> ores = new ArrayList<>();
        ores.add("dark_gem");

        for (String ore : ores) {
            StrainerRecipeBuilder.create().water(25)
                    .input(StrainersBlocks.PURIFIED_GRAVEL)
                    .output(ore, 0.15f)
                    .tier(7, 0.05f)
                    .save(output, ore, "evilcraft/purified_gravel/" + ore);
        }
    }

    public void addDimensionalResources() {
        List<String> ores = new ArrayList<>();
        ores.add("dimensional");

        for (String ore : ores) {
            StrainerRecipeBuilder.create().water(25)
                    .input(StrainersBlocks.PURIFIED_GRAVEL)
                    .output(ore, 0.15f)
                    .tier(8, 0.05f)
                    .save(output, ore, "dimensionalresources/purified_gravel/" + ore);
        }
    }
}