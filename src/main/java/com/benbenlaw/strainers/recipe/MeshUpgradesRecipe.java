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

public record MeshUpgradesRecipe(Ingredient input, double meshDamageChance) implements Recipe<NoInventoryRecipe> {

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
        return MeshUpgradesRecipe.Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return MeshUpgradesRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<MeshUpgradesRecipe> {
        private Type() {
        }

        public static final MeshUpgradesRecipe.Type INSTANCE = new MeshUpgradesRecipe.Type();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<MeshUpgradesRecipe> {
        public static final MeshUpgradesRecipe.Serializer INSTANCE = new Serializer();

        public final MapCodec<MeshUpgradesRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(MeshUpgradesRecipe::input),
                                Codec.DOUBLE.fieldOf("meshDamageChance").forGetter(MeshUpgradesRecipe::meshDamageChance))
                        .apply(instance, MeshUpgradesRecipe::new)
        );

        private final StreamCodec<RegistryFriendlyByteBuf, MeshUpgradesRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);

        @Override
        public @NotNull MapCodec<MeshUpgradesRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, MeshUpgradesRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static MeshUpgradesRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            double meshDamageChance = buffer.readDouble();

            return new MeshUpgradesRecipe(input, meshDamageChance);
        }

        private static void write(RegistryFriendlyByteBuf buffer, MeshUpgradesRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
            buffer.writeDouble(recipe.meshDamageChance);
        }
    }
}


