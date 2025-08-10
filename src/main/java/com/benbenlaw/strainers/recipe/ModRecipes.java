package com.benbenlaw.strainers.recipe;

import com.benbenlaw.strainers.Strainers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Strainers.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, Strainers.MOD_ID);

    public static final Supplier<RecipeSerializer<StrainerRecipe>> STRAINER_SERIALIZER =
            SERIALIZER.register("strainer", () -> StrainerRecipe.Serializer.INSTANCE);
    public static final Supplier<RecipeType<StrainerRecipe>> STRAINER_TYPE =
            TYPES.register("strainer", () -> StrainerRecipe.Type.INSTANCE);

    public static final Supplier<RecipeSerializer<MeshUpgradesRecipe>> MESH_UPGRADE_SERIALIZER =
            SERIALIZER.register("mesh_upgrade", () -> MeshUpgradesRecipe.Serializer.INSTANCE);
    public static final Supplier<RecipeType<MeshUpgradesRecipe>> MESH_UPGRADE_TYPE =
            TYPES.register("mesh_upgrade", () -> MeshUpgradesRecipe.Type.INSTANCE);

    public static final Supplier<RecipeSerializer<OutputUpgradesRecipe>> OUTPUT_UPGRADE_SERIALIZER =
            SERIALIZER.register("output_upgrade", () -> OutputUpgradesRecipe.Serializer.INSTANCE);
    public static final Supplier<RecipeType<OutputUpgradesRecipe>> OUTPUT_UPGRADE_TYPE =
            TYPES.register("output_upgrade", () -> OutputUpgradesRecipe.Type.INSTANCE);



    public static void register(IEventBus eventBus) {
        SERIALIZER.register(eventBus);
        TYPES.register(eventBus);
    }

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String p_44099_, S p_44100_) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, p_44099_, p_44100_);
    }


}