package com.benbenlaw.strainers.integration.jei;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.StrainersBlocks;
import com.benbenlaw.strainers.event.client.ClientRecipeCache;
import com.benbenlaw.strainers.item.StrainersItems;
import com.benbenlaw.strainers.item.util.BagOutputs;
import com.benbenlaw.strainers.recipe.*;
import com.benbenlaw.strainers.screen.custom.StrainerScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.crafting.RecipeCache;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@JeiPlugin
public class JEIStrainersPlugin implements IModPlugin {
    public static IDrawableStatic slotDrawable;

    @Override
    public @NotNull Identifier getPluginUid() {
        return Strainers.identifier("jei_plugin");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(StrainersItems.ORE_PIECE.asItem(), new ItemSubtypeInterpreter());

    }


    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(StrainerRecipeCategory.RECIPE_TYPE, new ItemStack(StrainersBlocks.STRAINER.get()));
        registration.addCraftingStation(BagOutputsCategory.RECIPE_TYPE,
                new ItemStack(StrainersItems.SEED_BAG.get()),
                new ItemStack(StrainersItems.SAPLING_BAG.get()),
                new ItemStack(StrainersItems.FLOWER_BAG.get()));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        slotDrawable = registration.getJeiHelpers().getGuiHelper().getSlotDrawable();

        registration.addRecipeCategories(new StrainerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new BagOutputsCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(StrainerRecipeCategory.RECIPE_TYPE, ClientRecipeCache.getCachedStrainerRecipes().stream().toList());

        registration.addRecipes(BagOutputsCategory.RECIPE_TYPE, List.of(
                bagRecipe("seed_bag", StrainersItems.SEED_BAG.get(), BlockTags.SUPPORTS_CROPS, BagOutputs.seeds()),
                bagRecipe("sapling_bag", StrainersItems.SAPLING_BAG.get(), BlockTags.SUPPORTS_VEGETATION, BagOutputs.saplings()),
                bagRecipe("flower_bag", StrainersItems.FLOWER_BAG.get(), BlockTags.GRASS_BLOCKS, BagOutputs.flowers())));

        registration.addIngredientInfo(StrainersItems.LEAF_PILE.get(), Component.translatable("jei.strainers.leaf_pile"));
    }

    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(StrainerScreen.class, 32, 33, 24, 16, StrainerRecipeCategory.RECIPE_TYPE);
    }

    private static BagOutputsRecipe bagRecipe(String name, Item bag, TagKey<Block> usedOn, List<Block> blocks) {
        List<ItemStack> outputs = blocks.stream()
                .distinct()
                .map(JEIStrainersPlugin::displayStack)
                .filter(stack -> !stack.isEmpty())
                .toList();
        return new BagOutputsRecipe(name, new ItemStack(bag), usedOn, outputs);
    }

    private static ItemStack displayStack(Block block) {
        ItemStack stack = new ItemStack(block.asItem());
        if (!stack.isEmpty()) return stack;
        Level level = Minecraft.getInstance().level;
        if (level == null) return ItemStack.EMPTY;
        return block.getCloneItemStack(level, BlockPos.ZERO, block.defaultBlockState(), false, null);
    }
}
