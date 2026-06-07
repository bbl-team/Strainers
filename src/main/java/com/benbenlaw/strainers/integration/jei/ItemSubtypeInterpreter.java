package com.benbenlaw.strainers.integration.jei;

import com.benbenlaw.strainers.item.StrainersDataComponents;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ItemSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
    @Override
    public @Nullable Object getSubtypeData(ItemStack stack, UidContext uidContext) {
        return stack.getOrDefault(StrainersDataComponents.ORE_TYPE, "");
    }

}