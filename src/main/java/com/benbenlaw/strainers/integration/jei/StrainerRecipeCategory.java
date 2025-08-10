package com.benbenlaw.strainers.integration.jei;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.ModBlocks;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import com.benbenlaw.strainers.util.ModTags;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StrainerRecipeCategory implements IRecipeCategory<StrainerJEIRecipe> {
    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "strainer");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Strainers.MOD_ID, "textures/gui/jei_strainer.png");

    public static final RecipeType<StrainerJEIRecipe> RECIPE_TYPE = RecipeType.create(Strainers.MOD_ID, "strainer", StrainerJEIRecipe.class);


    private final IDrawable background;
    private final IDrawable icon;

    public StrainerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 140, 56);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.WOODEN_STRAINER.get()));
    }

    @Override
    public RecipeType<StrainerJEIRecipe> getRecipeType() {
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

    public @Nullable ResourceLocation getRegistryName(StrainerJEIRecipe recipe) {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(StrainerRecipe.Type.INSTANCE).stream()
                .filter(recipeHolder -> recipeHolder.value().equals(recipe))
                .map(RecipeHolder::id)
                .findFirst()
                .orElse(null);
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, StrainerJEIRecipe recipe, IFocusGroup focusGroup) {

        Fluid fluidState = recipe.getBaseRecipe().getBlockAbove().getFluidState().getType();

        if (fluidState == null) {
            builder.addSlot(RecipeIngredientRole.INPUT, 2, 2).addItemStack(new ItemStack(Blocks.SAND))
                    .setCustomRenderer(VanillaTypes.ITEM_STACK, new IIngredientRenderer<>() {
                        @Override
                        public void render(GuiGraphics guiGraphics, ItemStack stack) {
                            JEIBlockRenderHelper.renderBlock(guiGraphics, recipe.getBaseRecipe().getBlockAbove(), 1,12 , 0.60f);
                        }

                        @Override
                        public List<Component> getTooltip(ItemStack ingredient, TooltipFlag tooltipFlag) {
                            List<Component> tooltip = new ArrayList<>();

                            tooltip.add(recipe.getBaseRecipe().getBlockAbove().getBlock().getName());

                            BlockState targetState = recipe.getBaseRecipe().getBlockAbove();
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


        builder.addSlot(RecipeIngredientRole.INPUT, 2, 20).addIngredients(recipe.getBaseRecipe().input());

        int meshTier = recipe.getMeshTier();
        TagKey<Item> meshTag = switch (meshTier) {
            case 1 -> ModTags.Items.TIER_1_MESHES;
            case 2 -> ModTags.Items.TIER_2_MESHES;
            case 3 -> ModTags.Items.TIER_3_MESHES;
            case 4 -> ModTags.Items.TIER_4_MESHES;
            case 5 -> ModTags.Items.TIER_5_MESHES;
            case 6 -> ModTags.Items.TIER_6_MESHES;
            case 7 -> ModTags.Items.TIER_7_MESHES;
            case 8 -> ModTags.Items.TIER_8_MESHES;
            case 9 -> ModTags.Items.TIER_9_MESHES;
            case 10 -> ModTags.Items.TIER_10_MESHES;
            default -> throw new IllegalArgumentException("Invalid mesh tier: " + meshTier);
        };

        builder.addSlot(RecipeIngredientRole.CATALYST, 2, 38).addIngredients(Ingredient.of(meshTag));

        int tier = recipe.getMeshTier();
        int minTier = recipe.getBaseRecipe().minMeshTier();
        double chancePerTier = recipe.getBaseRecipe().chancePerTier();

        List<ChanceResult> modifiedOutputs = new ArrayList<>(recipe.getBaseRecipe().getRollResults());
        int size = modifiedOutputs.size();
        int centerX = size > 0 ? 1 : 10;
        int centerY = size > 5 ? 2 : 11;
        int xOffset = 0;
        int yOffset = 0;
        int index = 0;

        for (int i = 0; i < size; i++) {
            xOffset = centerX + (i % 5) * 18;
            yOffset = centerY + ((i / 5) * 18);
            index = i;

            int finalIndex = index;
            builder.addSlot(RecipeIngredientRole.OUTPUT, 50 + xOffset, yOffset)
                    .addItemStack(modifiedOutputs.get(i).stack()).addRichTooltipCallback((slotView, tooltip) -> {
                        ChanceResult output = modifiedOutputs.get(finalIndex);
                        double baseChance = output.chance();
                        double totalChance = baseChance + (tier - minTier) * chancePerTier;
                        if (totalChance > 1.0) {
                            totalChance = 1.0;
                        }

                        tooltip.add(Component.translatable("block.strainer.jei.chance")
                                .append(String.valueOf((int) (totalChance * 100)))
                                .append("%").withStyle(ChatFormatting.GOLD));
                    });
        }

    }

    @Override
    public void draw(StrainerJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

    }

}
