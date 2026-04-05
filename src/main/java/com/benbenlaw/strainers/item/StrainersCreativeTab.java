package com.benbenlaw.strainers.item;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.StrainersBlocks;
import com.benbenlaw.strainers.fluid.StrainersFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class StrainersCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Strainers.MOD_ID);

    public static final Supplier<CreativeModeTab> STRAINERS_TAB = CREATIVE_MODE_TABS.register("strainers", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> StrainersBlocks.STRAINER.asItem().getDefaultInstance())
            .title(Component.translatable("itemGroup.strainers"))
            .displayItems((featureFlagSet, output) -> {

                StrainersItems.ITEMS.getEntries().forEach((entry) -> output.accept(entry.get()));

                StrainersFluids.FLUIDS.getBucketEntries().forEach(bucket -> output.accept(bucket.get()));
            }).build());
    
}
