package com.benbenlaw.strainers.item.util;

import com.benbenlaw.strainers.config.StrainersConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BagOutputs {

    public static List<Block> seeds() {
        return resolve(BlockTags.CROPS, StrainersConfig.ADD_SEED_BAG_OUTPUTS.get(), StrainersConfig.REMOVE_SEED_BAG_OUTPUTS.get());
    }

    public static List<Block> saplings() {
        return resolve(BlockTags.SAPLINGS, StrainersConfig.ADD_SAPLING_BAG_OUTPUTS.get(), StrainersConfig.REMOVE_SAPLING_BAG_OUTPUTS.get());
    }

    public static List<Block> flowers() {
        return resolve(BlockTags.FLOWERS, StrainersConfig.ADD_FLOWER_BAG_OUTPUTS.get(), StrainersConfig.REMOVE_FLOWER_BAG_OUTPUTS.get());
    }

    private static List<Block> resolve(TagKey<Block> base, List<? extends String> add, List<? extends String> remove) {
        List<Block> blocks = new ArrayList<>(resolveTag(base));
        add.forEach(entry -> blocks.addAll(resolveConfigEntry(entry)));
        remove.forEach(entry -> blocks.removeAll(resolveConfigEntry(entry)));
        return blocks;
    }

    private static List<Block> resolveTag(TagKey<Block> tag) {
        return BuiltInRegistries.BLOCK.get(tag)
                .map(holders -> holders.stream().map(Holder::value).toList())
                .orElse(Collections.emptyList());
    }

    private static List<Block> resolveConfigEntry(String entry) {
        if (entry == null || entry.isBlank()) return Collections.emptyList();

        if (entry.startsWith("#")) {
            Identifier tagId = Identifier.tryParse(entry.substring(1));
            if (tagId == null) return Collections.emptyList();
            return resolveTag(TagKey.create(Registries.BLOCK, tagId));
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
}
