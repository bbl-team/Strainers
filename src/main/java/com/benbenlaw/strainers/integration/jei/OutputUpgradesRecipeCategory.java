package com.benbenlaw.strainers.integration.jei;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.recipe.OutputUpgradesRecipe;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class OutputUpgradesRecipeCategory implements IRecipeCategory<OutputUpgradesRecipe> {
    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "output_upgrade");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "textures/gui/jei_upgrades.png");

    public static final RecipeType<OutputUpgradesRecipe> RECIPE_TYPE = RecipeType.create(Strainers.MOD_ID, "output_upgrade",
            OutputUpgradesRecipe.class);

    private IDrawable background;
    private final IDrawable icon;

    public @Nullable ResourceLocation getRegistryName(OutputUpgradesRecipe recipe) {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(OutputUpgradesRecipe.Type.INSTANCE).stream()
                .filter(recipeHolder -> recipeHolder.value().equals(recipe))
                .map(RecipeHolder::id)
                .findFirst()
                .orElse(null);
    }
    private final IGuiHelper helper;
    private int tabs_used = 0;

    public OutputUpgradesRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
        this.background = helper.createDrawable(TEXTURE, 0, 0, 102, 19);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.DIAMOND));
    }

    @Override
    public @NotNull RecipeType<OutputUpgradesRecipe> getRecipeType() {
        return JEIStrainersPlugin.OUTPUT_UPGRADES;
    }

    @Override
    public boolean isHandled(OutputUpgradesRecipe recipe) {
        return tabs_used == 0;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.literal("Output Upgrades");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, OutputUpgradesRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.CATALYST, 4, 2 ).addIngredients(recipe.input());
    }

    @Override
    public void draw(OutputUpgradesRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

        @NotNull final Minecraft minecraft = Minecraft.getInstance();

        double outputAddition = recipe.outputChanceIncrease();
        double outputAdditionPercentage = outputAddition * 100;

        guiGraphics.drawString(minecraft.font.self(), Component.literal(outputAdditionPercentage + "% Added to"), 24, 2, Color.GRAY.getRGB(), false);
        guiGraphics.drawString(minecraft.font.self(), Component.literal("Default Chance"), 24, 12, Color.GRAY.getRGB(), false);

    }
}



