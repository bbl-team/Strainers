package com.benbenlaw.strainers.recipe;

import com.benbenlaw.core.recipe.NoInventoryRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record OutputUpgradesRecipe(Ingredient input, double outputChanceIncrease) implements Recipe<NoInventoryRecipe> {

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(1);
        ingredients.add(input);
        return ingredients;
    }

    @Override
    public boolean matches(@NotNull NoInventoryRecipe inv, @NotNull Level pLevel) {
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull NoInventoryRecipe inv, HolderLookup.@NotNull Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }


    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return ItemStack.EMPTY;
    }


    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return OutputUpgradesRecipe.Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return OutputUpgradesRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<OutputUpgradesRecipe> {
        private Type() {
        }

        public static final OutputUpgradesRecipe.Type INSTANCE = new OutputUpgradesRecipe.Type();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<OutputUpgradesRecipe> {
        public static final OutputUpgradesRecipe.Serializer INSTANCE = new Serializer();

        public final MapCodec<OutputUpgradesRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(OutputUpgradesRecipe::input),
                                Codec.DOUBLE.fieldOf("outputChanceIncrease").forGetter(OutputUpgradesRecipe::outputChanceIncrease))
                        .apply(instance, OutputUpgradesRecipe::new)
        );

        private final StreamCodec<RegistryFriendlyByteBuf, OutputUpgradesRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);

        @Override
        public @NotNull MapCodec<OutputUpgradesRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, OutputUpgradesRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static OutputUpgradesRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            double outputChanceIncrease = buffer.readDouble();

            return new OutputUpgradesRecipe(input, outputChanceIncrease);
        }

        private static void write(RegistryFriendlyByteBuf buffer, OutputUpgradesRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
            buffer.writeDouble(recipe.outputChanceIncrease);
        }
    }
}


