package com.benbenlaw.strainers.recipe;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.strainers.block.entity.WoodenStrainerBlockEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
   /*
public record AddToExcistingStrainerRecipe(
        ResourceLocation recipeID,
        NonNullList<ChanceResult> results) implements Recipe<RecipeInput> {



    @Override
    public boolean matches(@NotNull RecipeInput container, @NotNull Level level) {

        return true;
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

    public static class Type implements RecipeType<AddToExcistingStrainerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();}

    public static class Serializer implements RecipeSerializer<AddToExcistingStrainerRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public static final MapCodec<AddToExcistingStrainerRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                        BlockState.CODEC.fieldOf("above_block").forGetter(AddToExcistingStrainerRecipe::aboveBlock),
                        Ingredient.CODEC.fieldOf("input").forGetter(AddToExcistingStrainerRecipe::input),
                        Ingredient.CODEC.fieldOf("mesh").forGetter(AddToExcistingStrainerRecipe::mesh),
                        Codec.INT.fieldOf("duration").forGetter(AddToExcistingStrainerRecipe::duration),
                        Codec.list(ChanceResult.CODEC).fieldOf("results").flatXmap(chanceResults -> {
                            NonNullList<ChanceResult> nonNullList = NonNullList.create();
                            nonNullList.addAll(chanceResults);
                            return DataResult.success(nonNullList);
                        }, DataResult::success).forGetter(AddToExcistingStrainerRecipe::getRollResults)
                ).apply(instance, AddToExcistingStrainerRecipe::new)
        );

        private final StreamCodec<RegistryFriendlyByteBuf, AddToExcistingStrainerRecipe> STREAM_CODEC = StreamCodec.of(
                AddToExcistingStrainerRecipe.Serializer::write, AddToExcistingStrainerRecipe.Serializer::read);

        @Override
        public @NotNull MapCodec<AddToExcistingStrainerRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, AddToExcistingStrainerRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static AddToExcistingStrainerRecipe read(RegistryFriendlyByteBuf buffer) {
            BlockState aboveBlock = Block.stateById(buffer.readInt());
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient mesh = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            int duration = buffer.readInt();
            int size = buffer.readVarInt();
            NonNullList<ChanceResult> outputs = NonNullList.withSize(size, ChanceResult.EMPTY);
            outputs.replaceAll(ignored -> ChanceResult.read(buffer));
            return new AddToExcistingStrainerRecipe(aboveBlock, input, mesh, duration, outputs);
        }

        private static void write(RegistryFriendlyByteBuf buffer, AddToExcistingStrainerRecipe recipe) {
            buffer.writeInt(Block.getId(recipe.aboveBlock));
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.mesh);
            buffer.writeInt(recipe.duration);
            buffer.writeVarInt(recipe.results.size());
            for (ChanceResult output : recipe.results) {
                output.write(buffer);
            }
        }
    }

}

    */