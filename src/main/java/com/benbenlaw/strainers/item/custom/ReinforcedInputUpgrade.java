package com.benbenlaw.strainers.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReinforcedInputUpgrade extends Item {
    public ReinforcedInputUpgrade(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {

        if(Screen.hasShiftDown()) {
            components.add(Component.translatable("tooltips.strainers.reinforced_input_upgrade.shift"));
        } else {
            components.add(Component.translatable("tooltips.strainers.upgrade"));
        }

        super.appendHoverText(stack, level, components, tooltipFlag);
    }
}
