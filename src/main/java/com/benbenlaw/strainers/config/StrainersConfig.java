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

    static {

        // Strainers Configs
        BUILDER.comment("Strainers Startup Config")
                .push("Strainers");

        ADD_SEED_BAG_OUTPUTS = BUILDER
                .comment("Add crops to the seed bag, eg minecraft:wheat, by default all crops in the tag are allowed")
                .defineListAllowEmpty(
                        "add_to_seed_bag",
                        List.of(""),
                        () -> "",
                        o -> {
                            if (!(o instanceof String s)) return false;
                            return Identifier.tryParse(s) != null;
                        });

        REMOVE_SEED_BAG_OUTPUTS = BUILDER
                .comment("Remove crops from the seed bag, eg minecraft:wheat, by default all crops in the tag are allowed")
                .defineListAllowEmpty(
                        "remove_from_seed_bag",
                        List.of("minecraft:pitcher_crop", "minecraft:torchflower_crop"),
                        () -> "",
                        o -> {
                            if (!(o instanceof String s)) return false;
                            return Identifier.tryParse(s) != null;
                        });

        ADD_SAPLING_BAG_OUTPUTS = BUILDER
                .comment("Add saplings to the sapling bag, eg minecraft:oak_sapling, by default all saplings in the tag are allowed")
                .defineListAllowEmpty(
                        "add_to_sapling_bag",
                        List.of(""),
                        () -> "",
                        o -> {
                            if (!(o instanceof String s)) return false;
                            return Identifier.tryParse(s) != null;
                        });

        REMOVE_SAPLING_BAG_OUTPUTS = BUILDER
                .comment("Remove saplings from the sapling bag, eg minecraft:oak_sapling, by default all saplings in the tag are allowed")
                .defineListAllowEmpty(
                        "remove_from_sapling_bag",
                        List.of(""),
                        () -> "",
                        o -> {
                            if (!(o instanceof String s)) return false;
                            return Identifier.tryParse(s) != null;
                        });


        BUILDER.pop();
        SPEC = BUILDER.build();

    }
}
