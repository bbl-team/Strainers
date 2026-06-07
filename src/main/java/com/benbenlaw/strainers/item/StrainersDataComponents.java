package com.benbenlaw.strainers.item;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.item.util.FluidListComponent;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StrainersDataComponents {

    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE,  Strainers.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FluidListComponent>> FLUIDS =
            COMPONENTS.register("fluids", () ->
                    DataComponentType.<FluidListComponent>builder()
                            .persistent(FluidListComponent.CODEC)
                            .networkSynchronized(FluidListComponent.STREAM_CODEC)
                            .cacheEncoding()
                            .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ORE_TYPE =
            COMPONENTS.register("ore_type", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .cacheEncoding()
                            .build());

}

