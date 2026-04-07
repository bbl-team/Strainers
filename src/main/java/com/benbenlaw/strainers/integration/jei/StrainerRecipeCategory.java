package com.benbenlaw.strainers.integration.jei;


import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.core.util.MouseUtil;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.StrainersBlockEntities;
import com.benbenlaw.strainers.block.StrainersBlocks;
import com.benbenlaw.strainers.block.entity.StrainerBlockEntity;
import com.benbenlaw.strainers.event.client.ClientRecipeCache;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import com.benbenlaw.strainers.util.StrainersTags;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class StrainerRecipeCategory implements IRecipeCategory<StrainerRecipe> {

    public final static Identifier TEXTURE = Strainers.identifier("textures/gui/strainer_jei.png");

    public static final IRecipeType<StrainerRecipe> RECIPE_TYPE = IRecipeType.create(Strainers.identifier("strainer"), StrainerRecipe.class);

    private final int width = 101;
    private final int height = 20;
    private final IDrawable icon;

    @Override
    public @Nullable Identifier getIdentifier(StrainerRecipe recipe) {
        return ClientRecipeCache.getCachedStrainerRecipes().stream()
                .filter(r -> r.equals(recipe))
                .findFirst()
                .map(r -> {
                    // Find the corresponding ID in the cache map
                    for (Map.Entry<Identifier, StrainerRecipe> entry : ClientRecipeCache.cachedStrainerRecipes.entrySet()) {
                        if (entry.getValue().equals(recipe)) {
                            return entry.getKey();
                        }
                    }
                    return null;
                })
                .orElse(null);
    }

    public StrainerRecipeCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(StrainersBlocks.STRAINER.get()));
    }

    @Override
    public @NotNull IRecipeType<StrainerRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("jei.strainers.strainer");
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, StrainerRecipe recipe, IFocusGroup focusGroup) {

        if (recipe.fluid().isPresent()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 2, 2).add(recipe.fluid().get().ingredient().display());
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, 2, 2).add(new ItemStack(Items.AIR));
        }

        builder.addSlot(RecipeIngredientRole.INPUT, 20, 2).add(recipe.input().ingredient().display());
        builder.addSlot(RecipeIngredientRole.INPUT, 38, 2).add(getItem(getMeshTier(recipe.minMeshTier())));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 83, 2).add(recipe.result().template().create()).addRichTooltipCallback((slotView, tooltip) -> {
            ChanceResult output = recipe.result();
            float chance = output.chance();
            int displayChance = (int) (chance * 100);
            float additionalChance = (float) recipe.additionalChancePerTier();
            int displayAdditionalChance = (int) (additionalChance * 100);
            tooltip.add(Component.translatable("jei.strainers.chance", displayChance).withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.translatable("jei.strainers.additional", displayAdditionalChance).withStyle(ChatFormatting.GOLD));
        });
    }

    @Override
    public void draw(StrainerRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, 0, 0, 0, 0, width, height, width, height);

    }

    public void createRecipeExtras(IRecipeExtrasBuilder builder, StrainerRecipe recipe, IFocusGroup focuses) {
        builder.addAnimatedRecipeArrow(200).setPosition(57, 2);
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, StrainerRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (MouseUtil.isMouseAboveArea((int) mouseX, (int) mouseY, 54, 1, 0, 0, 28, 18)) {
            tooltip.add(Component.translatable("tooltip.core.ticks", 200));
        }
    }

    private TagKey<Item> getMeshTier(int mesh) {
        if (mesh == 0) {
            return StrainersTags.Items.TIER_1_MESHES;
        } else if (mesh == 1) {
            return StrainersTags.Items.TIER_2_MESHES;
        } else if (mesh == 2) {
            return StrainersTags.Items.TIER_3_MESHES;
        } else if (mesh == 3) {
            return StrainersTags.Items.TIER_4_MESHES;
        } else if (mesh == 4) {
            return StrainersTags.Items.TIER_5_MESHES;
        } else if (mesh == 5) {
            return StrainersTags.Items.TIER_6_MESHES;
        } else if (mesh == 6) {
            return StrainersTags.Items.TIER_7_MESHES;
        } else if (mesh == 7) {
            return StrainersTags.Items.TIER_8_MESHES;
        } return  StrainersTags.Items.TIER_1_MESHES;
    }

    public Item getItem(TagKey<Item> tag) {
        Optional<Item> item = BuiltInRegistries.ITEM.get(tag)
                .flatMap(tag1 -> tag1.stream().findFirst())
                .map(Holder::value);

        return item.map(value -> new ItemStack(value, 1)).get().getItem();
    }
}