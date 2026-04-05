package com.benbenlaw.strainers.recipe;

import com.benbenlaw.strainers.Strainers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class StrainersRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Strainers.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, Strainers.MOD_ID);

    //Strainer
    public static final Supplier<RecipeSerializer<StrainerRecipe>> STRAINER_SERIALIZER =
            SERIALIZER.register("strainer", () -> StrainerRecipe.SERIALIZER);
    public static final Supplier<RecipeType<StrainerRecipe>> STRAINER_TYPE =
            TYPES.register("strainer", () -> StrainerRecipe.TYPE);

    //Tag Output
    public static final Supplier<RecipeSerializer<TagOutputRecipe>> SHAPED_TAG_OUTPUT =
            SERIALIZER.register("shaped_tag_output", () -> TagOutputRecipe.SERIALIZER);
    public static final Supplier<RecipeType<TagOutputRecipe>> SHAPED_TAG_OUTPUT_TYPE =
            TYPES.register("shaped_tag_output", () -> TagOutputRecipe.TYPE);


}