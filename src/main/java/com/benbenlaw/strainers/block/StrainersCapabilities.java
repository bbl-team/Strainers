package com.benbenlaw.strainers.block;

import com.benbenlaw.strainers.block.entity.StrainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class StrainersCapabilities {

    //Capability Registration (Item Handler)
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

        //Strainer
        event.registerBlockEntity(Capabilities.Item.BLOCK, StrainersBlockEntities.STRAINER_BLOCK_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getItemCapability());       

        event.registerBlockEntity(Capabilities.Fluid.BLOCK, StrainersBlockEntities.STRAINER_BLOCK_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getFluidCapability());





    }

}
