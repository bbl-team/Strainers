package com.benbenlaw.strainers.client;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.item.StrainersDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record OreTintSource() implements ItemTintSource {

    public static final MapCodec<OreTintSource> CODEC =
            MapCodec.unit(new OreTintSource());

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {

        String ore = stack.get(StrainersDataComponents.ORE_TYPE.get());

        if (ore == null) return 0xFFFFFFFF;

        return OreColors.get(ore);
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return CODEC;
    }
}