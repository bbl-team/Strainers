package com.benbenlaw.strainers.item;

import com.benbenlaw.strainers.config.StrainersConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.*;

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

        StrainersConfig.ADD_SAPLING_BAG_OUTPUTS.get()
                .forEach(entry -> saplings.addAll(resolveConfigEntry(entry)));
        StrainersConfig.REMOVE_SAPLING_BAG_OUTPUTS.get()
                .forEach(entry -> saplings.removeAll(resolveConfigEntry(entry)));

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

    private List<Block> resolveConfigEntry(String entry) {
        if (entry == null || entry.isBlank()) return Collections.emptyList();

        if (entry.startsWith("#")) {
            Identifier tagId = Identifier.tryParse(entry.substring(1));
            if (tagId == null) return Collections.emptyList();
            TagKey<Block> tag = TagKey.create(Registries.BLOCK, tagId);
            return BuiltInRegistries.BLOCK.get(tag)
                    .map(holders -> holders.stream().map(Holder::value).toList())
                    .orElse(Collections.emptyList());
        }

        if (entry.endsWith(":*")) {
            String modId = entry.substring(0, entry.length() - 2);
            if (modId.isBlank()) return Collections.emptyList();
            return BuiltInRegistries.BLOCK.entrySet().stream()
                    .filter(e -> e.getKey().identifier().getNamespace().equals(modId))
                    .map(Map.Entry::getValue)
                    .toList();
        }

        Identifier id = Identifier.tryParse(entry);
        if (id == null) return Collections.emptyList();
        Block block = BuiltInRegistries.BLOCK.getValue(id);
        if (block == Blocks.AIR && !entry.equals("minecraft:air")) return Collections.emptyList();
        return List.of(block);
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