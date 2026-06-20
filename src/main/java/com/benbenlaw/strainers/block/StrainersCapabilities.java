package com.benbenlaw.strainers.block;

import com.benbenlaw.strainers.item.FluidDropItem;
import com.benbenlaw.strainers.item.StrainersItems;
import com.benbenlaw.strainers.item.util.FluidDropResourceHandler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public class StrainersCapabilities {

    //Capability Registration (Item Handler)
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

        //Strainer
        event.registerBlockEntity(Capabilities.Item.BLOCK, StrainersBlockEntities.STRAINER_BLOCK_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getItemHandler());

        event.registerBlockEntity(Capabilities.Fluid.BLOCK, StrainersBlockEntities.STRAINER_BLOCK_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getFluidHandler());

        //Drop
        BuiltInRegistries.ITEM.forEach(item -> {
            if (item instanceof FluidDropItem) {
                event.registerItem(Capabilities.Fluid.ITEM, (stack, access) -> {
                    if (stack.getItem() instanceof FluidDropItem) {
                        return new FluidDropResourceHandler(access);
                    }
                    return null;
                }, item);
            }
        });
    }
}
