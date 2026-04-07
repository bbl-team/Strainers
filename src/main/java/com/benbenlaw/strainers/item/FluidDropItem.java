package com.benbenlaw.strainers.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class FluidDropItem extends BucketItem {


    public FluidDropItem(Properties properties, Fluid fluid) {
        super(fluid, properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, this.content == Fluids.EMPTY ? net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY : net.minecraft.world.level.ClipContext.Fluid.NONE);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResult.PASS;
        } else if (hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResult.PASS;
        } else {
            BlockPos pos = hitResult.getBlockPos();
            Direction direction = hitResult.getDirection();
            BlockPos directionOffsetPos = pos.relative(direction);
            if (level.mayInteract(player, pos) && player.mayUseItemAt(directionOffsetPos, direction, itemStack)) {
                if (this.content == Fluids.EMPTY) {
                    return InteractionResult.FAIL;
                } else if (player.getItemInHand(hand).count() >= 4) {
                    BlockState clicked = level.getBlockState(pos);
                    BlockPos placePos = this.canBlockContainFluid(player, level, pos, clicked) && this.content == Fluids.WATER ? pos : directionOffsetPos;
                    if (this.emptyContents(player, level, placePos, hitResult, itemStack)) {
                        this.checkExtraContent(player, level, itemStack, placePos);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, placePos, itemStack);
                        }

                        player.awardStat(Stats.ITEM_USED.get(this));
                        ItemStack reducedStack = player.getItemInHand(hand);
                        if (!player.hasInfiniteMaterials()) {
                            reducedStack.setCount(itemStack.getCount() - 4);
                        }
                        return InteractionResult.SUCCESS.heldItemTransformedTo(reducedStack);
                    } else {
                        return InteractionResult.FAIL;
                    }
                }
            }
        }
        return InteractionResult.FAIL;
    }
}
