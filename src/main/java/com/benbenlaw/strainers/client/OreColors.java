package com.benbenlaw.strainers.client;

public class OreColors {

    private static OreColorConfig CONFIG;

    public static void init(OreColorConfig config) {
        CONFIG = config;
    }

    public static int get(String ore) {
        if (CONFIG == null) {
            return OreDefaults.get(ore);
        }
        return CONFIG.getColor(ore);
    }
}