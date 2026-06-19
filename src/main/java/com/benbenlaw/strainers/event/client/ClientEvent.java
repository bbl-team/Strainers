package com.benbenlaw.strainers.event.client;

import com.benbenlaw.core.util.TooltipUtil;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.client.OreColorConfig;
import com.benbenlaw.strainers.client.OreColors;
import com.benbenlaw.strainers.item.FluidDropItem;
import com.benbenlaw.strainers.item.StrainersDataComponents;
import com.benbenlaw.strainers.item.StrainersItems;
import com.benbenlaw.strainers.item.util.FluidListComponent;
import com.benbenlaw.strainers.util.StrainersTags;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.fluids.FluidStack;

import java.nio.file.Path;

@EventBusSubscriber(modid = Strainers.MOD_ID)
public class ClientEvent {

    private static OreColorConfig CONFIG;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

        Path configDir = FMLPaths.CONFIGDIR.get();

        CONFIG = new OreColorConfig(configDir);
        CONFIG.init();

        OreColors.init(CONFIG);
    }

    @SubscribeEvent
    public static void onTooltipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        TooltipUtil.addShiftTooltip(stack, event, StrainersItems.SAPLING_BAG.get(), "tooltip.strainers.sapling_bag");
        TooltipUtil.addShiftTooltip(stack, event, StrainersItems.SEED_BAG.get(), "tooltip.strainers.seed_bag");
        //TooltipUtil.addShiftTooltip(stack, event, StrainersItems.DEPLETED_DROP.get(), "tooltip.strainers.depleted_drop");

        if (stack.getItem() instanceof FluidDropItem dropItem) {

            if (Minecraft.getInstance().hasShiftDown()) {


                FluidStack fluidStack = new FluidStack(dropItem.getContent(), 250);
                Component fluidLine = Component.translatable("tooltip.strainers.fluid_drop_item", fluidStack.getHoverName()).withStyle(ChatFormatting.BLUE);

                event.getToolTip().add(fluidLine);
            }
            else {
                event.getToolTip().add(Component.translatable("tooltip.bblcore.shift").withStyle(ChatFormatting.YELLOW));
            }
        }

        if (stack.has(StrainersDataComponents.FLUIDS.get())) {
            FluidListComponent fluidListComponent = stack.get(StrainersDataComponents.FLUIDS.get());

            if (fluidListComponent == null || fluidListComponent.fluids().isEmpty()) return;

            if (Minecraft.getInstance().hasShiftDown()) {
                event.getToolTip().add(Component.translatable("tooltip.strainers.fluids_header")
                        .withStyle(ChatFormatting.BLUE));

                for (FluidStack fluid : fluidListComponent.fluids()) {
                    if (!fluid.isEmpty()) {
                        Component fluidLine = Component.literal(" - ")
                                .append(Component.literal(fluid.getAmount() + "mB "))
                                .append(fluid.getHoverName())
                                .withStyle(ChatFormatting.BLUE);

                        event.getToolTip().add(fluidLine);
                    }
                }
            } else {
                event.getToolTip().add(Component.translatable("tooltip.bblcore.shift").withStyle(ChatFormatting.YELLOW));
            }
        }

        if (stack.is(StrainersTags.Items.TIER_1_MESHES)) {
            TooltipUtil.addShiftTooltip(stack, event, StrainersTags.Items.TIER_1_MESHES, "tooltip.strainers.tier_1_mesh");
        }
        else if (stack.is(StrainersTags.Items.TIER_2_MESHES)) {
            TooltipUtil.addShiftTooltip(stack, event, StrainersTags.Items.TIER_2_MESHES, "tooltip.strainers.tier_2_mesh");
        }
        else if (stack.is(StrainersTags.Items.TIER_3_MESHES)) {
            TooltipUtil.addShiftTooltip(stack, event, StrainersTags.Items.TIER_3_MESHES, "tooltip.strainers.tier_3_mesh");
        }
        else if (stack.is(StrainersTags.Items.TIER_4_MESHES)) {
            TooltipUtil.addShiftTooltip(stack, event, StrainersTags.Items.TIER_4_MESHES, "tooltip.strainers.tier_4_mesh");
        }
        else if (stack.is(StrainersTags.Items.TIER_5_MESHES)) {
            TooltipUtil.addShiftTooltip(stack, event, StrainersTags.Items.TIER_5_MESHES, "tooltip.strainers.tier_5_mesh");
        }
        else if (stack.is(StrainersTags.Items.TIER_6_MESHES)) {
            TooltipUtil.addShiftTooltip(stack, event, StrainersTags.Items.TIER_6_MESHES, "tooltip.strainers.tier_6_mesh");
        }
        else if (stack.is(StrainersTags.Items.TIER_7_MESHES)) {
            TooltipUtil.addShiftTooltip(stack, event, StrainersTags.Items.TIER_7_MESHES, "tooltip.strainers.tier_7_mesh");
        }
        else if (stack.is(StrainersTags.Items.TIER_8_MESHES)) {
            TooltipUtil.addShiftTooltip(stack, event, StrainersTags.Items.TIER_8_MESHES, "tooltip.strainers.tier_8_mesh");
        }
        else if (stack.is(StrainersTags.Items.TIER_9_MESHES)) {
            TooltipUtil.addShiftTooltip(stack, event, StrainersTags.Items.TIER_9_MESHES, "tooltip.strainers.tier_9_mesh");
        }
        else if (stack.is(StrainersTags.Items.TIER_10_MESHES)) {
            TooltipUtil.addShiftTooltip(stack, event, StrainersTags.Items.TIER_10_MESHES, "tooltip.strainers.tier_10_mesh");
        }
        else if (stack.is(StrainersTags.Items.TIER_11_MESHES)) {
            TooltipUtil.addShiftTooltip(stack, event, StrainersTags.Items.TIER_11_MESHES, "tooltip.strainers.tier_11_mesh");
        }
        else if (stack.is(StrainersTags.Items.TIER_12_MESHES)) {
            TooltipUtil.addShiftTooltip(stack, event, StrainersTags.Items.TIER_12_MESHES, "tooltip.strainers.tier_12_mesh");
        }
    }
}
