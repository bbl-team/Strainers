package com.benbenlaw.strainers.loot;

import com.benbenlaw.strainers.data.StrainersBlockTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class IsLeavesCondition implements LootItemCondition {

    public static final IsLeavesCondition INSTANCE = new IsLeavesCondition();

    public static final MapCodec<IsLeavesCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.STRING.fieldOf("type").forGetter(condition -> "is_leaves")
            ).apply(instance, (type) -> new IsLeavesCondition()
            ));

    @Override
    public MapCodec<? extends LootItemCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(LootContext lootContext) {

        BlockState state = lootContext.getOptionalParameter(LootContextParams.BLOCK_STATE);

        if (state != null) {
            return state.is(BlockTags.LEAVES);
        } else  {
            return false;
        }
    }
}
