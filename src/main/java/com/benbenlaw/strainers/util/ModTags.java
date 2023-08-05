package com.benbenlaw.strainers.util;

import com.benbenlaw.strainers.Strainers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks {

        public static final TagKey<Block> EMPTY = tag("empty");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Strainers.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }

    }


    public static class Items {

        public static final TagKey<Item> EMPTY = tag("empty");
        public static final TagKey<Item> TIER_1_MESHES = tag("tier_1_meshes");
        public static final TagKey<Item> TIER_2_MESHES = tag("tier_2_meshes");
        public static final TagKey<Item> TIER_3_MESHES = tag("tier_3_meshes");
        public static final TagKey<Item> TIER_4_MESHES = tag("tier_4_meshes");
        public static final TagKey<Item> TIER_5_MESHES = tag("tier_5_meshes");
        public static final TagKey<Item> TIER_6_MESHES = tag("tier_6_meshes");

        public static final TagKey<Item> MESHES = tag("meshes");
        public static final TagKey<Item> UPGRADES = tag("upgrades");


        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Strainers.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }

    }
}
