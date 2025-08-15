package com.benbenlaw.strainers.recipe;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.strainers.block.entity.WoodenStrainerBlockEntity;
import com.benbenlaw.strainers.util.ModTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record StrainerRecipe(
        BlockState aboveBlock,
        Ingredient input,
        Ingredient mesh,
        NonNullList<ChanceResult> results) implements Recipe<RecipeInput> {

    @Override
    public boolean matches(@NotNull RecipeInput container, @NotNull Level level) {
        if (level.isClientSide()) {
            return false;
        }

        boolean hasInput = input.test(container.getItem(WoodenStrainerBlockEntity.INPUT_SLOT));
        boolean hasMesh = mesh.test(container.getItem(WoodenStrainerBlockEntity.MESH_SLOT));

        if (container instanceof StrainerRecipeInput strainerRecipeInput) {
            BlockState aboveBlockState = level.getBlockState(strainerRecipeInput.getPos().above());
            return  hasMesh && hasInput && aboveBlockState.equals(aboveBlock);
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
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return results.getFirst().stack();
    }

    public List<ItemStack> getResults() {
        return getRollResults().stream()
                .map(ChanceResult::stack)
                .collect(Collectors.toList());
    }

    public NonNullList<ChanceResult> getRollResults() {
        return this.results;
    }

    public List<ItemStack> rollResults(RandomSource rand) {
        List<ItemStack> results = new ArrayList<>();
        List<ChanceResult> rollResults = getRollResults();
        for (ChanceResult output : rollResults) {
            ItemStack stack = output.rollOutput(rand);
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }
    public BlockState getBlockAbove() {
        return aboveBlock;
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
                        BlockState.CODEC.fieldOf("above_block").forGetter(StrainerRecipe::aboveBlock),
                        Ingredient.CODEC.fieldOf("input").forGetter(StrainerRecipe::input),
                        Ingredient.CODEC.fieldOf("mesh").forGetter(StrainerRecipe::mesh),
                        Codec.list(ChanceResult.CODEC).fieldOf("results").flatXmap(chanceResults -> {
                            NonNullList<ChanceResult> nonNullList = NonNullList.create();
                            nonNullList.addAll(chanceResults);
                            return DataResult.success(nonNullList);
                        }, DataResult::success).forGetter(StrainerRecipe::getRollResults)
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
            BlockState aboveBlock = Block.stateById(buffer.readInt());
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient mesh = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            int size = buffer.readVarInt();
            NonNullList<ChanceResult> outputs = NonNullList.withSize(size, ChanceResult.EMPTY);
            outputs.replaceAll(ignored -> ChanceResult.read(buffer));
            return new StrainerRecipe(aboveBlock, input, mesh, outputs);
        }

        private static void write(RegistryFriendlyByteBuf buffer, StrainerRecipe recipe) {
            buffer.writeInt(Block.getId(recipe.aboveBlock));
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.mesh);
            buffer.writeVarInt(recipe.results.size());
            for (ChanceResult output : recipe.results) {
                output.write(buffer);
            }
        }
    }
}