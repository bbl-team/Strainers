package com.benbenlaw.strainers.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public final class StrainersConfigFile {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<Boolean> showMeshesInWorld;
    public static final ModConfigSpec.ConfigValue<Boolean> showItemBeingStrainerInWorld;
    public static final ModConfigSpec.ConfigValue<Integer> strainerMaxProgress;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> blockDurations;


    static {
        BUILDER.push("Strainers Config File");

        showMeshesInWorld = BUILDER.comment("Show the strainer meshes in the world, default = true")
                .define("Show Meshes", true);

        showItemBeingStrainerInWorld = BUILDER.comment("Show items in / on the strainer in the world, default = true")
                .define("Show Items ", true);

        strainerMaxProgress = BUILDER.comment("The maximum progress of the strainer, default = 220,")
                .defineInRange("Strainer Max Progress", 220, 1, Integer.MAX_VALUE);

        blockDurations = BUILDER.comment("The durations of the blocks in the world, default = [], example \"minecraft:cherry_leaves=1000\"")
                .defineList("Block Durations", List.of(), o -> o instanceof String);


        BUILDER.pop();
        SPEC = BUILDER.build();

    }
}
