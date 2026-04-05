package com.benbenlaw.strainers.event.client;

import com.benbenlaw.core.util.TooltipUtil;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.item.StrainersItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = Strainers.MOD_ID)
public class TooltipEvent {

    @SubscribeEvent
    public static void onTooltipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        TooltipUtil.addShiftTooltip(stack, event, StrainersItems.SAPLING_SEED.get(), "tooltip.strainers.sapling_seed");

    }
}
