package com.benbenlaw.strainers.integration.jei;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.StrainersBlocks;
import com.benbenlaw.strainers.event.client.ClientRecipeCache;
import com.benbenlaw.strainers.recipe.*;
import com.benbenlaw.strainers.screen.custom.StrainerScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
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
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(StrainerRecipeCategory.RECIPE_TYPE, new ItemStack(StrainersBlocks.STRAINER.get()));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        slotDrawable = registration.getJeiHelpers().getGuiHelper().getSlotDrawable();

        registration.addRecipeCategories(new StrainerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(StrainerRecipeCategory.RECIPE_TYPE, ClientRecipeCache.getCachedStrainerRecipes().stream().toList());
    }

    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(StrainerScreen.class, 24, 2, 24, 16, StrainerRecipeCategory.RECIPE_TYPE);
    }
}
