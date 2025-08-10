package com.benbenlaw.strainers.integration.jei;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.ModBlocks;
import com.benbenlaw.strainers.recipe.MeshUpgradesRecipe;
import com.benbenlaw.strainers.recipe.ModRecipes;
import com.benbenlaw.strainers.recipe.OutputUpgradesRecipe;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIStrainersPlugin implements IModPlugin {
    public static IDrawableStatic slotDrawable;

    public static RecipeType<StrainerJEIRecipe> STRAINER =
            new RecipeType<>(StrainerRecipeCategory.UID, StrainerJEIRecipe.class);

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

        slotDrawable = registration.getJeiHelpers().getGuiHelper().getSlotDrawable();

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        assert Minecraft.getInstance().level != null;
        var recipeManager = Minecraft.getInstance().level.getRecipeManager();

        // Get all StrainerRecipes from the manager
        List<StrainerRecipe> allStrainerRecipes = recipeManager.getAllRecipesFor(StrainerRecipe.Type.INSTANCE)
                .stream()
                .map(RecipeHolder::value)
                .toList();

        List<StrainerJEIRecipe> jeiRecipes = new ArrayList<>();

        for (StrainerRecipe recipe : allStrainerRecipes) {
            for (int tier = recipe.minMeshTier(); tier <= recipe.maxMeshTier(); tier++) {
                jeiRecipes.add(new StrainerJEIRecipe(recipe, tier));
            }
        }

        registration.addRecipes(StrainerRecipeCategory.RECIPE_TYPE, jeiRecipes);

        registration.addRecipes(MeshUpgradesRecipeCategory.RECIPE_TYPE,
                recipeManager.getAllRecipesFor(ModRecipes.MESH_UPGRADE_TYPE.get()).stream().map(RecipeHolder::value).toList());

        registration.addRecipes(OutputUpgradesRecipeCategory.RECIPE_TYPE,
                recipeManager.getAllRecipesFor(ModRecipes.OUTPUT_UPGRADE_TYPE.get()).stream().map(RecipeHolder::value).toList());
    }
}
