package com.benbenlaw.strainers.integration.jei;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.ModBlocks;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StrainerRecipeCategory implements IRecipeCategory<StrainerRecipeDisplay> {
    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "strainer");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "textures/gui/jei_strainer.png");

    public static final RecipeType<StrainerRecipeDisplay> RECIPE_TYPE = RecipeType.create(Strainers.MOD_ID, "strainer", StrainerRecipeDisplay.class);


    private final IDrawable background;
    private final IDrawable icon;

    public StrainerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 140, 56);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.WOODEN_STRAINER.get()));
    }

    @Override
    public RecipeType<StrainerRecipeDisplay> getRecipeType() {
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

    public @Nullable ResourceLocation getRegistryName(StrainerRecipeDisplay recipe) {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(StrainerRecipe.Type.INSTANCE).stream()
                .filter(recipeHolder -> recipeHolder.value().equals(recipe))
                .map(RecipeHolder::id)
                .findFirst()
                .orElse(null);
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, StrainerRecipeDisplay recipe, IFocusGroup focusGroup) {

        Fluid fluidState = recipe.aboveBlock().getFluidState().getType();

        if (fluidState == null) {
            builder.addSlot(RecipeIngredientRole.INPUT, 2, 2).addItemStack(new ItemStack(Blocks.SAND))
                    .setCustomRenderer(VanillaTypes.ITEM_STACK, new IIngredientRenderer<>() {
                        @Override
                        public void render(GuiGraphics guiGraphics, ItemStack stack) {
                            JEIBlockRenderHelper.renderBlock(guiGraphics, recipe.aboveBlock(), 1,12 , 0.60f);
                        }

                        @Override
                        public List<Component> getTooltip(ItemStack ingredient, TooltipFlag tooltipFlag) {
                            List<Component> tooltip = new ArrayList<>();

                            tooltip.add(recipe.aboveBlock().getBlock().getName());

                            BlockState targetState = recipe.aboveBlock();
                            BlockState defaultState = targetState.getBlock().defaultBlockState();

                            for (Map.Entry<Property<?>, Comparable<?>> entry : targetState.getValues().entrySet()) {
                                Property<?> property = entry.getKey();
                                Comparable<?> recipeValue = entry.getValue();
                                Comparable<?> defaultValue = defaultState.getValue(property);

                                // Only add to tooltip if it differs from the default
                                if (!recipeValue.equals(defaultValue)) {
                                    String key = property.getName();
                                    String value = recipeValue.toString();
                                    tooltip.add(Component.literal(key + ": " + value));
                                }
                            }
                            return tooltip;
                        }
                    });
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, 2, 2).addFluidStack(fluidState)
                    .setFluidRenderer(1000, true, 16, 16);
        }


        builder.addSlot(RecipeIngredientRole.INPUT, 2, 20).addIngredients(recipe.input());
        builder.addSlot(RecipeIngredientRole.CATALYST, 2, 38).addIngredients(recipe.mesh());

        List<ChanceResult> modifiedOutputs = recipe.getChanceResults();
        int size = modifiedOutputs.size();

        int outputsPerRow = 5;
        int maxRows = 3;
        int slotSize = 18;

        int startX = 51;
        int startY = 2;

        for (int i = 0; i < size && i < outputsPerRow * maxRows; i++) {
            int xOffset = startX + (i % outputsPerRow) * slotSize;
            int yOffset = startY + (i / outputsPerRow) * slotSize;

            int finalIndex = i;
            builder.addSlot(RecipeIngredientRole.OUTPUT, xOffset, yOffset)
                    .addItemStack(modifiedOutputs.get(i).stack())
                    .addRichTooltipCallback((slotView, tooltip) -> {
                        ChanceResult output = modifiedOutputs.get(finalIndex);
                        double baseChance = output.chance();
                        int asPercent = Math.round((float) (baseChance * 100));

                        tooltip.add(Component.translatable("block.strainer.jei.chance")
                                .append(String.valueOf(asPercent))
                                .append("%").withStyle(ChatFormatting.GOLD));
                    });
        }
    }

    @Override
    public void draw(StrainerRecipeDisplay recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

    }

}
