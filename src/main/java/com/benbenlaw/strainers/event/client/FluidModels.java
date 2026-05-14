package com.benbenlaw.strainers.event.client;

import com.benbenlaw.core.Core;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.fluid.StrainersFluids;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;

@EventBusSubscriber(modid = Strainers.MOD_ID)
public class FluidModels {

    @SubscribeEvent
    private static void registerFluidModels(RegisterFluidModelsEvent event) {

        var still = new Material(Core.identifier("block/thin_still"));
        var flowing = new Material(Core.identifier("block/thin_flow"));

        FluidModel.Unbaked erodingWater = new FluidModel.Unbaked(still, flowing, null, state -> 0xBFCCCCFF, null);
        FluidModel.Unbaked purifyingWater = new FluidModel.Unbaked(still, flowing, null, state -> 0xBFff13d6, null);
        FluidModel.Unbaked saltyWater = new FluidModel.Unbaked(still, flowing, null, state -> 0xBF7E92E8, null);

        event.register(erodingWater, StrainersFluids.ERODING_WATER.getStillFluid(), StrainersFluids.ERODING_WATER.getFlowingFluid());
        event.register(purifyingWater, StrainersFluids.PURIFYING_WATER.getStillFluid(), StrainersFluids.PURIFYING_WATER.getFlowingFluid());
        event.register(saltyWater, StrainersFluids.SALTY_WATER.getStillFluid(), StrainersFluids.SALTY_WATER.getFlowingFluid());

    }
}
