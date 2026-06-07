package com.benbenlaw.strainers.data;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.StrainersBlocks;
import com.benbenlaw.strainers.client.OreDefaults;
import com.benbenlaw.strainers.data.recipes.TagOutputRecipeProvider;
import com.benbenlaw.strainers.item.OrePieceItem;
import com.benbenlaw.strainers.item.StrainersItems;
import com.geckolib.loading.definition.animation.DoubleOrString;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


public class StrainersRecipeProvider extends RecipeProvider {

    public StrainersRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
            super(packOutput, provider);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider provider, @NotNull RecipeOutput recipeOutput) {
            return new StrainersRecipeProvider(provider, recipeOutput);
        }

        @Override
        public @NotNull String getName() {
            return Strainers.MOD_ID + " Recipes";
        }
    }

    @Override
    protected void buildRecipes() {

        //Reset
        shapeless(RecipeCategory.MISC, StrainersBlocks.STRAINER).requires(StrainersBlocks.STRAINER);

        //Sculk Sensor
        shaped(RecipeCategory.MISC, Blocks.SCULK_SENSOR)
                .pattern("ABA")
                .pattern("CCC")
                .define('A', StrainersItems.SCULK_DUST)
                .define('B', Items.COMPARATOR)
                .define('C', Blocks.SCULK)
                .unlockedBy("has_sculk", has(Blocks.SCULK))
                .save(output, Strainers.identifier("sculk").toString());

        //Sculk Catalyst
        shaped(RecipeCategory.MISC, Blocks.SCULK_CATALYST)
                .pattern("AAA")
                .pattern("BBB")
                .define('A', Blocks.SCULK)
                .define('B', Blocks.CALCITE)
                .unlockedBy("has_mesh", has(Blocks.SCULK))
                .save(output, Strainers.identifier("sculk_catalyst").toString());

        //Sculk Shrieker
        shaped(RecipeCategory.MISC, Blocks.SCULK_SHRIEKER)
                .pattern("AAA")
                .pattern("BBB")
                .pattern("CCC")
                .define('A', Blocks.SCULK_SENSOR)
                .define('B', Blocks.SCULK)
                .define('C', Blocks.CALCITE)
                .unlockedBy("has_mesh", has(Blocks.SCULK))
                .save(output, Strainers.identifier("sculk_shrieker").toString());

        //Strainer
        shaped(RecipeCategory.MISC, StrainersBlocks.STRAINER.get())
                .pattern("ABA")
                .pattern("A A")
                .pattern("A A")
                .define('A', ItemTags.LOGS)
                .define('B', StrainersItems.WOODEN_MESH.get())
                .unlockedBy("has_mesh", has(StrainersItems.WOODEN_MESH.get()))
                .save(output);

        //Meshes
        meshBuilder(StrainersItems.WOODEN_MESH.get(), Tags.Items.RODS_WOODEN, Items.STICK);
        meshBuilder(StrainersItems.FLINT_MESH.get(), Items.FLINT, StrainersItems.WOODEN_MESH.get());
        meshBuilder(StrainersItems.COPPER_MESH.get(), Tags.Items.INGOTS_COPPER, StrainersItems.FLINT_MESH.get());
        meshBuilder(StrainersItems.IRON_MESH.get(), Tags.Items.INGOTS_IRON, StrainersItems.COPPER_MESH.get());
        meshBuilder(StrainersItems.GOLD_MESH.get(), Tags.Items.INGOTS_GOLD, StrainersItems.IRON_MESH.get());
        meshBuilder(StrainersItems.DIAMOND_MESH.get(), Tags.Items.GEMS_DIAMOND, StrainersItems.GOLD_MESH.get());
        meshBuilder(StrainersItems.EMERALD_MESH.get(), Tags.Items.GEMS_EMERALD, StrainersItems.DIAMOND_MESH.get());
        meshBuilder(StrainersItems.NETHERITE_MESH.get(), Tags.Items.INGOTS_NETHERITE, StrainersItems.EMERALD_MESH.get());

        twoByTwoPacker(RecipeCategory.MISC, Items.COBBLESTONE, StrainersItems.STONE_PEBBLE, "cobblestone_from_pebbles");
        twoByTwoPacker(RecipeCategory.MISC, Items.GRAVEL, StrainersItems.GRAVEL_PEBBLE, "gravel_from_pebbles");
        twoByTwoPacker(RecipeCategory.MISC, Items.SAND, StrainersItems.SAND_DUST, "sand_from_dust");
        twoByTwoPacker(RecipeCategory.MISC, StrainersBlocks.DUST_BLOCK, StrainersItems.DUST, "dust_block_from_dust");
        twoByTwoPacker(RecipeCategory.MISC, Blocks.ANCIENT_DEBRIS, StrainersItems.DEBRIS_ORE_PIECE.get(), "debris_from_pieces");
        twoByTwoPacker(RecipeCategory.MISC, StrainersBlocks.MULCH, StrainersItems.LEAF_PILE.get(), "mulch_from_leaf_pile");
        twoByTwoPacker(RecipeCategory.MISC, Blocks.DIRT, StrainersItems.DIRT_PILE.get(), "dirt_from_dirt_pile");
        twoByTwoPacker(RecipeCategory.MISC, Blocks.SCULK, StrainersItems.SCULK_DUST.get(), "sculk_from_sculk_dust");

        shaped(RecipeCategory.MISC, Items.ANCIENT_DEBRIS)
                .pattern("AA")
                .pattern("AA")
                .define('A', DataComponentIngredient.of(false, OrePieceItem.createOrePiece("netherite_scrap")))
                .unlockedBy("has_mesh", has(StrainersItems.WOODEN_MESH.get()))
                .save(output);


        for (String ore : OreDefaults.COLORS.keySet()) {
            generateOrePieceRecipe(ore);
        }

    }

    public void meshBuilder(ItemLike mesh, TagKey<Item> input, ItemLike previousMesh) {

        shaped(RecipeCategory.MISC, mesh)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', input)
                .define('B', previousMesh)
                .unlockedBy("has_input", has(input))
                .save(output);
    }

    public void meshBuilder(ItemLike mesh, ItemLike input, ItemLike previousMesh) {

        shaped(RecipeCategory.MISC, mesh)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', input)
                .define('B', previousMesh)
                .unlockedBy("has_input", has(input))
                .save(output);
    }

    protected void twoByTwoPacker(RecipeCategory category, ItemLike result, ItemLike ingredient, String id) {
        this.shaped(category, result, 1).define('#', ingredient).pattern("##").pattern("##").unlockedBy(getHasName(ingredient), this.has(ingredient)).save(output, Strainers.identifier(id).toString());
    }

    private void generateOrePieceRecipe(String ore) {

        String id = "ore_piece/" + ore;

        TagKey<Item> outputTag = TagKey.create(
                Registries.ITEM,
                Identifier.parse("c:ores/" + ore)
        );

        var pattern = ShapedRecipePattern.of(Map.of('#', DataComponentIngredient.of(false, OrePieceItem.createOrePiece(ore))),"##", "##");

        TagOutputRecipeProvider.tagOutputRecipe(new Recipe.CommonInfo(false), new CraftingRecipe.CraftingBookInfo(CraftingBookCategory.MISC, "ore_pieces"),
                        pattern, outputTag, 1).save(output.withConditions(new NotCondition(new TagEmptyCondition<>(outputTag))), id);
    }

}

