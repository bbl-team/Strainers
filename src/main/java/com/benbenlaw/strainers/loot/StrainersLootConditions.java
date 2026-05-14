package com.benbenlaw.strainers.loot;

import com.benbenlaw.strainers.Strainers;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class StrainersLootConditions {

    public static final DeferredRegister<MapCodec<? extends LootItemCondition>> LOOT_CONDITION_SERIALIZERS =
            DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, Strainers.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends LootItemCondition>, MapCodec<IsLeavesCondition>>  IS_LEAVES_CONDITION =
            LOOT_CONDITION_SERIALIZERS.register("is_leaves", () -> IsLeavesCondition.CODEC);
}
