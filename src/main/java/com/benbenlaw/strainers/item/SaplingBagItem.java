package com.benbenlaw.strainers.item;

import com.benbenlaw.strainers.config.StrainersConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaplingBagItem extends Item {
    public SaplingBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        Direction direction = context.getClickedFace();
        BlockPos blockPos = context.getClickedPos();
        BlockPos abovePos = blockPos.above();
        BlockState above = context.getLevel().getBlockState(abovePos);

        if (level.isClientSide()) return InteractionResult.FAIL;

        List<Block> saplings = new ArrayList<>(BuiltInRegistries.BLOCK.get(BlockTags.SAPLINGS)
                .map(holders -> holders.stream()
                        .map(Holder::value)
                        .toList())
                .orElse(Collections.emptyList()));

        StrainersConfig.ADD_SAPLING_BAG_OUTPUTS.get().forEach(sapling -> saplings.add(BuiltInRegistries.BLOCK.getValue(Identifier.tryParse(sapling))));
        StrainersConfig.REMOVE_SAPLING_BAG_OUTPUTS.get().forEach(sapling -> saplings.remove(BuiltInRegistries.BLOCK.getValue(Identifier.tryParse(sapling))));

        if (state.is(BlockTags.SUPPORTS_VEGETATION) && direction == Direction.UP && above.canBeReplaced()) {

            if (!saplings.isEmpty()) {
                Block randomSapling = saplings.get(level.getRandom().nextInt(saplings.size()));
                level.setBlockAndUpdate(abovePos, randomSapling.defaultBlockState());
                context.getItemInHand().shrink(1);
                spawnParticles(level, abovePos, DyeColor.GREEN);
                playSound(level, abovePos);
                return InteractionResult.SUCCESS_SERVER;
            }

        }
        return InteractionResult.FAIL;
    }


    private void spawnParticles(Level level, BlockPos pos, DyeColor color) {
        int colorInt = color.getTextureDiffuseColor();
        DustParticleOptions dust = new DustParticleOptions(colorInt, 1.0F);
        ((ServerLevel) level).sendParticles(dust,
                pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                12,
                0.3, 0.3, 0.3,
                0.05
        );
    }

    private void playSound(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.SLIME_ATTACK, SoundSource.BLOCKS, 0.5F, 1.0F);
    }

}
