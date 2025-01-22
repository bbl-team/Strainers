package com.benbenlaw.strainers.integration.jei;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.item.ModItems;
import com.benbenlaw.strainers.recipe.MeshUpgradesRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class MeshUpgradesRecipeCategory implements IRecipeCategory<MeshUpgradesRecipe> {
    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "mesh_upgrade");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "textures/gui/jei_upgrades.png");

    public static final RecipeType<MeshUpgradesRecipe> RECIPE_TYPE = RecipeType.create(Strainers.MOD_ID, "mesh_upgrade",
            MeshUpgradesRecipe.class);

    private IDrawable background;
    private final IDrawable icon;
    private final IGuiHelper helper;
    private int tabs_used = 0;

    public MeshUpgradesRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
        this.background = helper.createDrawable(TEXTURE, 0, 0, 102, 19);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.NETHERITE_MESH.get()));
    }

    @Override
    public @NotNull RecipeType<MeshUpgradesRecipe> getRecipeType() {
        return JEIStrainersPlugin.MESH_UPGRADES;
    }

    @Override
    public boolean isHandled(MeshUpgradesRecipe recipe) {
        return tabs_used == 0;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.literal("Mesh Upgrades");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    public @Nullable ResourceLocation getRegistryName(MeshUpgradesRecipe recipe) {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(MeshUpgradesRecipe.Type.INSTANCE).stream()
                .filter(recipeHolder -> recipeHolder.value().equals(recipe))
                .map(RecipeHolder::id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MeshUpgradesRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.CATALYST, 4, 2 ).addIngredients(recipe.input());
    }

    @Override
    public void draw(MeshUpgradesRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

        @NotNull final Minecraft minecraft = Minecraft.getInstance();

        double outputAddition = recipe.meshDamageChance();
        double outputAdditionPercentage = outputAddition * 100;

        guiGraphics.drawString(minecraft.font.self(), Component.literal("Mesh Damage"), 24, 2, Color.GRAY.getRGB(), false);
        guiGraphics.drawString(minecraft.font.self(), Component.literal("Chance: " + outputAdditionPercentage + "%"), 24, 12, Color.GRAY.getRGB(), false);

    }
}



