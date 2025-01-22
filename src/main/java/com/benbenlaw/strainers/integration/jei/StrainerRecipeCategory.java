package com.benbenlaw.strainers.integration.jei;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.ModBlocks;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import com.benbenlaw.strainers.util.ModTags;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.awt.*;

public class StrainerRecipeCategory implements IRecipeCategory<StrainerRecipe> {
    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "strainer");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "textures/gui/jei_strainer.png");

    static final RecipeType<StrainerRecipe> RECIPE_TYPE = RecipeType.create(Strainers.MOD_ID, "strainer",
            StrainerRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public StrainerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 180, 40);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.WOODEN_STRAINER.get()));
    }

    @Override
    public RecipeType<StrainerRecipe> getRecipeType() {
        return JEIStrainersPlugin.STRAINER;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Strainers");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    public @Nullable ResourceLocation getRegistryName(StrainerRecipe recipe) {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(StrainerRecipe.Type.INSTANCE).stream()
                .filter(recipeHolder -> recipeHolder.value().equals(recipe))
                .map(RecipeHolder::id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, StrainerRecipe recipe, IFocusGroup focusGroup) {

        builder.addSlot(RecipeIngredientRole.INPUT, 4, 23).addIngredients(recipe.getIngredients().getFirst());

        if (recipe.getMinMeshTier() == 1) {
            builder.addSlot(RecipeIngredientRole.INPUT, 25, 23).addIngredients(Ingredient.of(ModTags.Items.TIER_1_MESHES));
            builder.addSlot(RecipeIngredientRole.OUTPUT, 161, 23).addIngredients((recipe.output().ingredient()));
        }
        if (recipe.getMinMeshTier() == 2) {
            builder.addSlot(RecipeIngredientRole.INPUT, 25, 23).addIngredients(Ingredient.of(ModTags.Items.TIER_2_MESHES));
            builder.addSlot(RecipeIngredientRole.OUTPUT, 161, 23).addIngredients(recipe.output().ingredient());
        }
        if (recipe.getMinMeshTier() == 3) {
            builder.addSlot(RecipeIngredientRole.INPUT, 25, 23).addIngredients(Ingredient.of(ModTags.Items.TIER_3_MESHES));
            builder.addSlot(RecipeIngredientRole.OUTPUT, 161, 23).addIngredients(recipe.output().ingredient());
        }
        if (recipe.getMinMeshTier() == 4) {
            builder.addSlot(RecipeIngredientRole.INPUT, 25, 23).addIngredients(Ingredient.of(ModTags.Items.TIER_4_MESHES));
            builder.addSlot(RecipeIngredientRole.OUTPUT, 161, 23).addIngredients(recipe.output().ingredient());
        }
        if (recipe.getMinMeshTier() == 5) {
            builder.addSlot(RecipeIngredientRole.INPUT, 25, 23).addIngredients(Ingredient.of(ModTags.Items.TIER_5_MESHES));
            builder.addSlot(RecipeIngredientRole.OUTPUT, 161, 23).addIngredients(recipe.output().ingredient());
        }
        if (recipe.getMinMeshTier() == 6) {
            builder.addSlot(RecipeIngredientRole.INPUT, 25, 23).addIngredients(Ingredient.of(ModTags.Items.TIER_6_MESHES));
            builder.addSlot(RecipeIngredientRole.OUTPUT, 161, 23).addIngredients(recipe.output().ingredient());
        }

        Block blockInRecipe = recipe.getBlockAbove();

        if(!blockInRecipe.asItem().equals(Items.AIR)) {
            builder.addSlot(RecipeIngredientRole.CATALYST, 4, 3).addItemStack(blockInRecipe.asItem().getDefaultInstance());
        }

        Fluid fluid = recipe.getFluidAbove();
        builder.addSlot(RecipeIngredientRole.CATALYST, 4, 3).addFluidStack(fluid
                , 1000).setFluidRenderer(1000, true, 16,16);

    }

    @Override
    public void draw(StrainerRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

        @Nonnull final Minecraft minecraft = Minecraft.getInstance();

        int meshTier = recipe.getMinMeshTier();

        guiGraphics.drawString(minecraft.font.self(), Component.literal("T" + meshTier + "+"), 24, 8, Color.GRAY.getRGB(), false);
        guiGraphics.drawString(minecraft.font.self(), Component.literal("Default Chance: " +  ((int) (recipe.getOutputChance() * 100)) + "%"), 75, 5, Color.GRAY.getRGB(), false);

        int duration = 220;
        String durationText = duration + " ticks";
        int textWidth = minecraft.font.width(durationText);
        int xPosition = 105 - (textWidth / 2);

        guiGraphics.drawString(minecraft.font.self(), Component.literal(durationText), xPosition, 26, Color.GRAY.getRGB(), false);
    }
}
