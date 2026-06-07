package com.benbenlaw.strainers.client;

import java.util.Map;

public final class OreDefaults {

    private OreDefaults() {}

    public static final Map<String, Integer> COLORS = Map.<String, Integer>ofEntries(

            Map.entry("iron", 0xFFd5d5d5),
            Map.entry("gold", 0xFFfaf25e),
            Map.entry("copper", 0xFFbf5935),
            Map.entry("netherite", 0xFF4c484c),
            Map.entry("redstone", 0xFFe04040),
            Map.entry("netherite_scrap", 0xFF5D342C),
            Map.entry("diamond", 0xFF9ff8e5),
            Map.entry("emerald", 0xFF40f082),
            Map.entry("lapis", 0xFF1c52a6),
            Map.entry("quartz", 0xFFdad1c4),
            Map.entry("coal", 0xFF252525),

            //Common / All The Ores
            Map.entry("tin", 0xFFdff1f8),
            Map.entry("lead", 0xFF8b9cd0),
            Map.entry("osmium", 0xFFafc6cc),
            Map.entry("uranium", 0xFFe5eac0),
            Map.entry("silver", 0xFFd1dadf),
            Map.entry("nickel", 0xFFc5beac),
            Map.entry("zinc", 0xFFc7dddb),
            Map.entry("platinum", 0xFFa8e4fc),
            Map.entry("aluminum", 0xFFd9e1e3),
            Map.entry("iridium", 0xFFb7b0ac),
            Map.entry("titanium", 0xFFEAFFF9),
            Map.entry("tungsten", 0xFF3D334F),
            Map.entry("ruby", 0xFFf57fa8),
            Map.entry("sapphire", 0xFF439ef9),
            Map.entry("peridot", 0xFFe5ef43),
            Map.entry("topaz", 0xFFE0A048),
            Map.entry("monazite", 0xFF9F3A98),
            Map.entry("cinnabar", 0xFFB4263C),
            Map.entry("sulfur", 0xFFEEC62E),
            Map.entry("fluorite", 0xFFF0A6DE),
            Map.entry("salt", 0xFFEBECEA),

            //All The Modium
            Map.entry("allthemodium", 0xFFFFFF4A),
            Map.entry("vibranium", 0xFF73FFB9),
            Map.entry("unobtainium", 0xFFA82CE3),

            //Silent Gems
            Map.entry("opal", 0xFFCAEBD1),
            Map.entry("garnet", 0xFFD1211F),
            Map.entry("tanzanite", 0xFF3D00B9),
            Map.entry("kyanite", 0xFFC7BEAD),
            Map.entry("chaos", 0xFFEBD8D1),
            Map.entry("heliodor", 0xFFDDCF17),
            Map.entry("iesnium", 0xFF64949D),
            Map.entry("rose_quartz", 0xFFFF6BBD),
            Map.entry("iolite", 0xFF0F110E),
            Map.entry("citrin", 0xFFC78B03),
            Map.entry("carnelian", 0xFFB33825),
            Map.entry("alexandrite", 0xFF9016CC),
            Map.entry("crimson_iron", 0xFFFF6189),
            Map.entry("turquoise", 0xFFA3FFFA),
            Map.entry("ammolite", 0xFF5E1490),
            Map.entry("bort", 0xFF89A0C8),
            Map.entry("white_diamond", 0xFFAB9BA7),
            Map.entry("azure_silver", 0xFFFDC0FF),
            Map.entry("black_diamond", 0xFF5F524C),
            Map.entry("moldavite", 0xFF6F9A1E),
            Map.entry("aquamarine", 0xFF6EE9F4),
            Map.entry("pearl", 0xFFC7BEAD),

            //Forbidden Arcanus
            Map.entry("runic", 0xFFCBCFE2),
            Map.entry("stellarite", 0xFF8A917E),
            Map.entry("arcane_crystal", 0xFFA7BBFA),

            //Powah
            Map.entry("uraninite", 0xFFCBCFE2),
            Map.entry("uraninite_regular", 0xFFCBCFE2),
            Map.entry("uraninite_dense", 0xFFCBCFE2),
            Map.entry("uraninite_poor", 0xFFCBCFE2),

            //Theurgy
            Map.entry("sal_ammoniac", 0xFFE3C6E5),

            //Mystical Agriculture
            Map.entry("soulium", 0xFF8C563C),
            Map.entry("prosperity", 0xFF84A2A2),
            Map.entry("inferium", 0xFF8FAF00)






    );

    public static int get(String ore) {
        return COLORS.getOrDefault(ore, 0xFFAAAAAA);
    }
}