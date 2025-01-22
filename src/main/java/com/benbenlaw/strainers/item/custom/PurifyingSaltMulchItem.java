package com.benbenlaw.strainers.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class PurifyingSaltMulchItem extends Item {
    public PurifyingSaltMulchItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> components, TooltipFlag tooltipFlag) {

        if(Screen.hasShiftDown()) {
            components.add(Component.translatable("jei.strainers.purified_water"));
        } else {
            components.add(Component.translatable("tooltips.strainers.upgrade"));
        }

        super.appendHoverText(stack, tooltipContext, components, tooltipFlag);
    }

}
