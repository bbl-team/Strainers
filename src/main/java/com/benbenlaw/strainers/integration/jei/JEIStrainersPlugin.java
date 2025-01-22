package com.benbenlaw.strainers.integration.jei;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.ModBlocks;
import com.benbenlaw.strainers.recipe.MeshUpgradesRecipe;
import com.benbenlaw.strainers.recipe.ModRecipes;
import com.benbenlaw.strainers.recipe.OutputUpgradesRecipe;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JEIStrainersPlugin implements IModPlugin {

    public static RecipeType<StrainerRecipe> STRAINER =
            new RecipeType<>(StrainerRecipeCategory.UID, StrainerRecipe.class);

    public static RecipeType<MeshUpgradesRecipe> MESH_UPGRADES =
            new RecipeType<>(MeshUpgradesRecipeCategory.UID, MeshUpgradesRecipe.class);

    public static RecipeType<OutputUpgradesRecipe> OUTPUT_UPGRADES =
            new RecipeType<>(OutputUpgradesRecipeCategory.UID, OutputUpgradesRecipe.class);


    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WOODEN_STRAINER.get()), StrainerRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WOODEN_STRAINER.get()), MeshUpgradesRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WOODEN_STRAINER.get()), OutputUpgradesRecipeCategory.RECIPE_TYPE);
    //    registration.addRecipeCatalyst(new ItemStack(ModBlocks.WOODEN_STRAINER.get()), SpeedUpgradesRecipeCategory.RECIPE_TYPE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {

        registration.addRecipeCategories(new
                StrainerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new
                MeshUpgradesRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new
                OutputUpgradesRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

    }
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        assert Minecraft.getInstance().level != null;
        final var recipeManager = Minecraft.getInstance().level.getRecipeManager();

        registration.addRecipes(StrainerRecipeCategory.RECIPE_TYPE,
                recipeManager.getAllRecipesFor(ModRecipes.STRAINER_TYPE.get()).stream().map(RecipeHolder::value).toList());


        registration.addRecipes(MeshUpgradesRecipeCategory.RECIPE_TYPE,
                recipeManager.getAllRecipesFor(ModRecipes.MESH_UPGRADE_TYPE.get()).stream().map(RecipeHolder::value).toList());

        registration.addRecipes(OutputUpgradesRecipeCategory.RECIPE_TYPE,
                recipeManager.getAllRecipesFor(ModRecipes.OUTPUT_UPGRADE_TYPE.get()).stream().map(RecipeHolder::value).toList());






    }
}
