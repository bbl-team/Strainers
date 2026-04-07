package com.benbenlaw.strainers.block;

import com.benbenlaw.strainers.item.FluidDropItem;
import com.benbenlaw.strainers.item.StrainersItems;
import com.benbenlaw.strainers.item.util.FluidDropResourceHandler;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public class StrainersCapabilities {

    //Capability Registration (Item Handler)
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

        //Strainer
        event.registerBlockEntity(Capabilities.Item.BLOCK, StrainersBlockEntities.STRAINER_BLOCK_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getItemCapability());       

        event.registerBlockEntity(Capabilities.Fluid.BLOCK, StrainersBlockEntities.STRAINER_BLOCK_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getFluidCapability());


        //Drop
        StrainersItems.ITEMS.getEntries().forEach(item -> {
            if (item.get() instanceof FluidDropItem) {
                event.registerItem(Capabilities.Fluid.ITEM, (stack, access) -> {
                    if (stack.getItem() instanceof FluidDropItem) {
                        return new FluidDropResourceHandler(access);
                    }
                    return null;
                }, item.get());
            }
        });
    }
}
