package com.benbenlaw.strainers.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.List;

public record StrainerRecipeDisplay(SlotDisplay input, SlotDisplay result) implements RecipeDisplay {

    public static final MapCodec<StrainerRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                            SlotDisplay.CODEC.fieldOf("input").forGetter(StrainerRecipeDisplay::input),
                            SlotDisplay.CODEC.fieldOf("result").forGetter(StrainerRecipeDisplay::result))
                    .apply(builder, StrainerRecipeDisplay::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, StrainerRecipeDisplay> STREAM_CODEC = StreamCodec
            .composite(
                    SlotDisplay.STREAM_CODEC, StrainerRecipeDisplay::input,
                    SlotDisplay.STREAM_CODEC, StrainerRecipeDisplay::result,
                    StrainerRecipeDisplay::new);

    public static final RecipeDisplay.Type<StrainerRecipeDisplay> TYPE = new RecipeDisplay.Type<>(MAP_CODEC,
            STREAM_CODEC);

    @Override
    public SlotDisplay craftingStation() {
        return SlotDisplay.Empty.INSTANCE;
    }

    @Override
    public Type<StrainerRecipeDisplay> type() {
        return TYPE;
    }
}
