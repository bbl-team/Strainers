package com.benbenlaw.strainers.fluid.eroding;

import com.benbenlaw.strainers.fluid.ModFluids;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ErodingWaterFluidBlock extends LiquidBlock {

    public ErodingWaterFluidBlock() {
        super(ModFluids.ERODING_WATER_FLUID_SOURCE.get(), Properties.of()
                .mapColor(MapColor.COLOR_GRAY)
                .replaceable()
                .noCollission()
                .strength(100.0F)
                .pushReaction(PushReaction.DESTROY)
                .noLootTable()
                .liquid()
                .sound(SoundType.EMPTY)
        );
    }
}
