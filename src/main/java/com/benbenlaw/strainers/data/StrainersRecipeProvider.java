package com.benbenlaw.strainers.data;

import com.benbenlaw.core.tag.CommonTags;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.StrainersBlocks;
import com.benbenlaw.strainers.data.recipes.TagOutputRecipeProvider;
import com.benbenlaw.strainers.item.StrainersItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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
        meshBuilder(StrainersItems.WOODEN_MESH.get(), Tags.Items.RODS_WOODEN);
        meshBuilder(StrainersItems.FLINT_MESH.get(), Items.FLINT);
        meshBuilder(StrainersItems.COPPER_MESH.get(), Tags.Items.INGOTS_COPPER);
        meshBuilder(StrainersItems.IRON_MESH.get(), Tags.Items.INGOTS_IRON);
        meshBuilder(StrainersItems.GOLD_MESH.get(), Tags.Items.INGOTS_GOLD);
        meshBuilder(StrainersItems.DIAMOND_MESH.get(), Tags.Items.GEMS_DIAMOND);
        meshBuilder(StrainersItems.EMERALD_MESH.get(), Tags.Items.GEMS_EMERALD);
        meshBuilder(StrainersItems.NETHERITE_MESH.get(), Tags.Items.INGOTS_NETHERITE);

        twoByTwoPacker(RecipeCategory.MISC, Items.COBBLESTONE, StrainersItems.STONE_PEBBLE);
        twoByTwoPacker(RecipeCategory.MISC, Items.GRAVEL, StrainersItems.GRAVEL_PEBBLE);


        /* Currently adding manually because getting "Cannot encode unpacked recipe" error not sure how to fix, when completed will probably migrate to core
        TagOutputRecipeProvider.tagOutputRecipe(new Recipe.CommonInfo(false), new CraftingRecipe.CraftingBookInfo(CraftingBookCategory.MISC, Strainers.MOD_ID),
                new ShapedRecipePattern(2,2, List.of(Optional.of(Ingredient.of(StrainersItems.COAL_ORE_PIECE.asItem())), Optional.of(Ingredient.of(StrainersItems.COAL_ORE_PIECE.asItem())), Optional.of(Ingredient.of(StrainersItems.COAL_ORE_PIECE.asItem())), Optional.of(Ingredient.of(StrainersItems.COAL_ORE_PIECE.asItem())), Optional.of(Ingredient.of(StrainersItems.COAL_ORE_PIECE.asItem()))),
                        Optional.empty()), CommonTags.getItemTag("ore", "coal"), 1).save(output);

         */

    }

    public void meshBuilder(ItemLike mesh, TagKey<Item> input) {

        shaped(RecipeCategory.MISC, mesh)
                .pattern("ABA")
                .pattern("B B")
                .pattern("ABA")
                .define('A', Tags.Items.RODS_WOODEN)
                .define('B', input)
                .unlockedBy("has_input", has(input))
                .save(output);
    }

    public void meshBuilder(ItemLike mesh, ItemLike input) {

        shaped(RecipeCategory.MISC, mesh)
                .pattern("ABA")
                .pattern("B B")
                .pattern("ABA")
                .define('A', Tags.Items.RODS_WOODEN)
                .define('B', input)
                .unlockedBy("has_input", has(input))
                .save(output);
    }

}
