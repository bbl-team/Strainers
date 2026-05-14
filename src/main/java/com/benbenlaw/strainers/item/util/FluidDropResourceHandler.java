package com.benbenlaw.strainers.item.util;

import com.benbenlaw.strainers.item.FluidDropItem;
import com.benbenlaw.strainers.item.StrainersItems;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.transfer.ItemAccessResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.Objects;

public final class FluidDropResourceHandler extends ItemAccessResourceHandler<FluidResource> {
    
    int dropCapacity = 250;
    
    public FluidDropResourceHandler(ItemAccess itemAccess) {
        super(itemAccess, 1);
    }

    @Override
    protected FluidResource getResourceFrom(ItemResource accessResource, int index) {
        if (accessResource.getItem() instanceof FluidDropItem dropItem) {
            return FluidResource.of(dropItem.content);
        } else if (accessResource.is(Items.MILK_BUCKET) && NeoForgeMod.MILK.isBound()) {
            return FluidResource.of(NeoForgeMod.MILK.get());
        } else {
            return FluidResource.EMPTY;
        }
    }

    @Override
    protected int getAmountFrom(ItemResource accessResource, int index) {
        var resource = getResourceFrom(accessResource, index);
        return resource.isEmpty() ? 0 : dropCapacity;
    }

    //TODO - currently in neoforge theres no way to have a empty resource so we have to parse an item in this case a stick to be the "empty" resource, requires a neoforge fix
    @Override
    protected ItemResource update(ItemResource accessResource, int index, FluidResource newResource, int newAmount) {
        if (newAmount == 0) {
            return ItemResource.of(StrainersItems.DEPLETED_DROP.asItem());
        } else if (newAmount != dropCapacity) {
            return ItemResource.EMPTY;
        } else {
            var newStack = newResource.toStack(newAmount);
            return ItemResource.of(newStack.getFluidType().getBucket(newStack));
        }
    }

    @Override
    protected int getCapacity(int index, FluidResource resource) {
        Objects.checkIndex(index, size());
        return dropCapacity;
    }
}