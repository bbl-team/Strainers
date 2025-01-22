package com.benbenlaw.strainers.recipe;

import com.benbenlaw.strainers.block.entity.WoodenStrainerBlockEntity;
import com.benbenlaw.strainers.util.ModTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.NotNull;

public record StrainerRecipe(Ingredient input, String aboveBlock, SizedIngredient output, int minMeshTier, double chance) implements Recipe<RecipeInput> {

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(1);
        ingredients.add(input);
        return ingredients;
    }

    @Override
    public boolean matches(@NotNull RecipeInput pContainer, @NotNull Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        if(input.test(pContainer.getItem(WoodenStrainerBlockEntity.INPUT_SLOT))) {
            ItemStack meshItem = pContainer.getItem(WoodenStrainerBlockEntity.MESH_SLOT);
            return switch (minMeshTier) {
                case 1 -> meshItem.is(ModTags.Items.TIER_1_MESHES) || meshItem.is(ModTags.Items.TIER_2_MESHES) || meshItem.is(ModTags.Items.TIER_3_MESHES) || meshItem.is(ModTags.Items.TIER_4_MESHES) || meshItem.is(ModTags.Items.TIER_5_MESHES) || meshItem.is(ModTags.Items.TIER_6_MESHES);
                case 2 -> meshItem.is(ModTags.Items.TIER_2_MESHES) || meshItem.is(ModTags.Items.TIER_3_MESHES) || meshItem.is(ModTags.Items.TIER_4_MESHES) || meshItem.is(ModTags.Items.TIER_5_MESHES) || meshItem.is(ModTags.Items.TIER_6_MESHES);
                case 3 -> meshItem.is(ModTags.Items.TIER_3_MESHES) || meshItem.is(ModTags.Items.TIER_4_MESHES) || meshItem.is(ModTags.Items.TIER_5_MESHES) || meshItem.is(ModTags.Items.TIER_6_MESHES);
                case 4 -> meshItem.is(ModTags.Items.TIER_4_MESHES) || meshItem.is(ModTags.Items.TIER_5_MESHES) || meshItem.is(ModTags.Items.TIER_6_MESHES);
                case 5 -> meshItem.is(ModTags.Items.TIER_5_MESHES) || meshItem.is(ModTags.Items.TIER_6_MESHES);
                case 6 -> meshItem.is(ModTags.Items.TIER_6_MESHES);
                default -> false;
            };
        }
        return false;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeInput container, HolderLookup.@NotNull Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }
    public double getOutputChance() {
        return chance;
    }

    public SizedIngredient getOutput() {
        return output;
    }

    public int getMinMeshTier() {
        return minMeshTier;
    }

    public Block getBlockAbove() {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.parse(aboveBlock));
    }
    public Fluid getFluidAbove() {
        return BuiltInRegistries.FLUID.get(ResourceLocation.parse(aboveBlock));

    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Type implements RecipeType<StrainerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();}

    public static class Serializer implements RecipeSerializer<StrainerRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public static final MapCodec<StrainerRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(StrainerRecipe::input),
                        Codec.STRING.fieldOf("aboveBlock").forGetter(StrainerRecipe::aboveBlock),
                        SizedIngredient.FLAT_CODEC.fieldOf("output").forGetter(StrainerRecipe::output),
                        Codec.INT.fieldOf("minMeshTier").forGetter(StrainerRecipe::minMeshTier),
                        Codec.DOUBLE.fieldOf("chance").forGetter(StrainerRecipe::chance)
                ).apply(instance, StrainerRecipe::new)
        );

        private final StreamCodec<RegistryFriendlyByteBuf, StrainerRecipe> STREAM_CODEC = StreamCodec.of(
                StrainerRecipe.Serializer::write, StrainerRecipe.Serializer::read);

        @Override
        public @NotNull MapCodec<StrainerRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, StrainerRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static StrainerRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            String aboveBlock = buffer.readUtf(Short.MAX_VALUE);
            SizedIngredient output = SizedIngredient.STREAM_CODEC.decode(buffer);
            int minMeshTier = buffer.readInt();
            double chance = buffer.readDouble();
            return new StrainerRecipe(input, aboveBlock, output, minMeshTier, chance);
        }

        private static void write(RegistryFriendlyByteBuf buffer, StrainerRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
            buffer.writeUtf(recipe.aboveBlock, Short.MAX_VALUE);
            SizedIngredient.STREAM_CODEC.encode(buffer, recipe.output);
            buffer.writeInt(recipe.minMeshTier);
            buffer.writeDouble(recipe.chance);
        }
    }
}