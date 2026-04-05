package com.benbenlaw.strainers.recipe;

import com.benbenlaw.core.recipe.ChanceResult;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public record StrainerRecipe(SizedIngredient input, SizedFluidIngredient fluid, ChanceResult result, int minMeshTier, double additionalChancePerTier) implements Recipe<RecipeInput> {

    public static final MapCodec<StrainerRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    SizedIngredient.NESTED_CODEC.fieldOf("input").forGetter(StrainerRecipe::input),
                    SizedFluidIngredient.CODEC.fieldOf("fluid").forGetter(StrainerRecipe::fluid),
                    ChanceResult.CODEC.fieldOf("result").forGetter(StrainerRecipe::result),
                    Codec.INT.fieldOf("min_mesh_tier").forGetter(StrainerRecipe::minMeshTier),
                    Codec.DOUBLE.fieldOf("additional_chance_per_tier").forGetter(StrainerRecipe::additionalChancePerTier)
            ).apply(instance, StrainerRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, StrainerRecipe> STREAM_CODEC = StreamCodec.of(
            StrainerRecipe::write, StrainerRecipe::read);

    public static final RecipeType<StrainerRecipe> TYPE = new RecipeType<>() {};

    public static final RecipeSerializer<StrainerRecipe> SERIALIZER =
            new RecipeSerializer<>(CODEC, STREAM_CODEC);

    private static StrainerRecipe read(RegistryFriendlyByteBuf buffer) {
        SizedIngredient input = SizedIngredient.STREAM_CODEC.decode(buffer);
        SizedFluidIngredient fluid = SizedFluidIngredient.STREAM_CODEC.decode(buffer);
        ChanceResult result = ChanceResult.read(buffer);
        int minMeshTier = buffer.readInt();
        double additionalChancePerTier = buffer.readDouble();
        return new StrainerRecipe(input, fluid, result, minMeshTier , additionalChancePerTier);
    }

    private static void write(RegistryFriendlyByteBuf buffer, StrainerRecipe recipe) {
        SizedIngredient.STREAM_CODEC.encode(buffer, recipe.input);
        SizedFluidIngredient.STREAM_CODEC.encode(buffer, recipe.fluid);
        recipe.result.write(buffer);
        buffer.writeInt(recipe.minMeshTier);
        buffer.writeDouble(recipe.additionalChancePerTier);
    }

    public ItemStack rollWithTier(RandomSource random, int meshTier) {
        ItemStack base = result.template().create();
        if (base.isEmpty()) return ItemStack.EMPTY;
        double baseChance = result.chance();
        int bonusLevels = Math.max(0, meshTier - minMeshTier);
        double totalChance = baseChance + bonusLevels * additionalChancePerTier;
        int guaranteed = (int) Math.floor(totalChance);
        double fractional = totalChance - guaranteed;
        if (random.nextDouble() < fractional) guaranteed++;
        if (guaranteed <= 0) return ItemStack.EMPTY;
        ItemStack out = base.copy();
        out.setCount(Math.min(out.getMaxStackSize(), guaranteed));
        return out;
    }

    @Override
    public boolean matches(@NotNull RecipeInput input, @NotNull Level level) {
        if (level.isClientSide()) return false;

        if (!(input instanceof StrainerRecipeInput strainerInput)) return false;

        return this.input.test(strainerInput.getInputStack())
                && this.fluid.test(strainerInput.getFluid());
    }

    //Boiler Plate
    @Override
    public @NonNull ItemStack assemble(RecipeInput recipeInput) {
        return result.template().create();
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<RecipeInput>> getType() {
        return TYPE;
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public @NonNull String group() {
        return "";
    }
}
