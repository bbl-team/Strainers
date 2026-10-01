package com.benbenlaw.strainers.integration.jei;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.item.StrainersItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BagOutputsCategory implements IRecipeCategory<BagOutputsRecipe> {

    public static final IRecipeType<BagOutputsRecipe> RECIPE_TYPE = IRecipeType.create(Strainers.identifier("bag_outputs"), BagOutputsRecipe.class);

    private static final int COLUMNS = 7;
    private static final int VISIBLE_ROWS = 4;
    private static final int GRID_X = 2;
    private static final int GRID_Y = 26;

    private final IDrawable icon;

    public BagOutputsCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(StrainersItems.SEED_BAG.get()));
    }

    @Override
    public @NotNull IRecipeType<BagOutputsRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("jei.strainers.bag_outputs");
    }

    @Override
    public int getWidth() {
        return GRID_X + COLUMNS * 18 + 18;
    }

    @Override
    public int getHeight() {
        return GRID_Y + VISIBLE_ROWS * 18 - 2;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public @Nullable Identifier getIdentifier(BagOutputsRecipe recipe) {
        return Strainers.identifier(recipe.name());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BagOutputsRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 2, 2)
                .setStandardSlotBackground()
                .add(recipe.bag());

        for (ItemStack output : recipe.outputs()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT)
                    .setStandardSlotBackground()
                    .add(output);
        }
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, BagOutputsRecipe recipe, IFocusGroup focuses) {
        int textWidth = getWidth() - 24;
        builder.addText(recipe.bag().getHoverName(), textWidth, 10)
                .setPosition(22, 3)
                .setTextAlignment(HorizontalAlignment.CENTER)
                .setColor(0xFF404040)
                .setShadow(false);
        builder.addText(Component.literal("#" + recipe.usedOn().location()), textWidth, 10)
                .setPosition(22, 14)
                .setTextAlignment(HorizontalAlignment.CENTER)
                .setColor(0xFF909090)
                .setShadow(false);

        List<IRecipeSlotDrawable> outputSlots = builder.getRecipeSlots().getSlots(RecipeIngredientRole.OUTPUT);
        builder.addScrollGridWidget(outputSlots, COLUMNS, VISIBLE_ROWS)
                .setPosition(GRID_X, GRID_Y);
    }
}
