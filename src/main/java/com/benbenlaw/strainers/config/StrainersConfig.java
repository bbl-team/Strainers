package com.benbenlaw.strainers.config;

import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class StrainersConfig {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ADD_SEED_BAG_OUTPUTS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> REMOVE_SEED_BAG_OUTPUTS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ADD_SAPLING_BAG_OUTPUTS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> REMOVE_SAPLING_BAG_OUTPUTS;
    public static final ModConfigSpec.ConfigValue<Boolean> STRAINERS_CONSUME_FLUID;

    static {

        BUILDER.comment("Strainers Startup Config")
                .push("Strainers");

        STRAINERS_CONSUME_FLUID = BUILDER
                .comment("Whether Strainers consume fluid per recipe")
                .define("strainers_consume_fluid", false);

        ADD_SEED_BAG_OUTPUTS = BUILDER
                .comment("Add crops to the seed bag. Supports: 'minecraft:wheat' (block), '#minecraft:crops' (tag), 'minecraft:*' (all blocks from a mod)")
                .defineListAllowEmpty(
                        "add_to_seed_bag",
                        List.of(""),
                        () -> "",
                        StrainersConfig::isValidEntry);

        REMOVE_SEED_BAG_OUTPUTS = BUILDER
                .comment("Remove crops from the seed bag. Supports: 'minecraft:wheat' (block), '#minecraft:crops' (tag), 'minecraft:*' (all blocks from a mod)")
                .defineListAllowEmpty(
                        "remove_from_seed_bag",
                        List.of("minecraft:pitcher_crop", "minecraft:torchflower_crop", "croptopia:*", "mysticalagriculture:*", "productivefarming:*"),
                        () -> "",
                        StrainersConfig::isValidEntry);

        ADD_SAPLING_BAG_OUTPUTS = BUILDER
                .comment("Add saplings to the sapling bag. Supports: 'minecraft:oak_sapling' (block), '#minecraft:saplings' (tag), 'minecraft:*' (all blocks from a mod)")
                .defineListAllowEmpty(
                        "add_to_sapling_bag",
                        List.of(""),
                        () -> "",
                        StrainersConfig::isValidEntry);

        REMOVE_SAPLING_BAG_OUTPUTS = BUILDER
                .comment("Remove saplings from the sapling bag. Supports: 'minecraft:oak_sapling' (block), '#minecraft:saplings' (tag), 'minecraft:*' (all blocks from a mod)")
                .defineListAllowEmpty(
                        "remove_from_sapling_bag",
                        List.of("productivetrees:*", "allthemodium:*"),
                        () -> "",
                        StrainersConfig::isValidEntry);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    private static boolean isValidEntry(Object o) {
        if (!(o instanceof String s)) return false;
        if (s.isBlank()) return true;
        if (s.startsWith("#")) return Identifier.tryParse(s.substring(1)) != null;
        if (s.endsWith(":*")) return s.length() > 2;
        return Identifier.tryParse(s) != null;
    }
}