package com.benbenlaw.strainers.block;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.entity.StrainerBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class StrainersBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Strainers.MOD_ID);

    public static final Supplier<BlockEntityType<StrainerBlockEntity>> STRAINER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("strainer_block_entity", () ->
                    new BlockEntityType<>(StrainerBlockEntity::new, StrainersBlocks.STRAINER.get()));

}
