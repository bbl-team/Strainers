package com.benbenlaw.strainers.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class OreColorConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final Map<String, Integer> COLORS = new HashMap<>();

    private final Path file;

    public OreColorConfig(Path configDir) {
        this.file = configDir.resolve("bbl").resolve("strainers")
                .resolve("ores.json");
    }

    public void init() {
        if (!Files.exists(file)) {
            generateDefaultFile();
        }
        load();
    }

    private void generateDefaultFile() {
        try {
            Map<String, String> defaults = new HashMap<>();

            for (var entry : OreDefaults.COLORS.entrySet()) {
                defaults.put(entry.getKey(), toHex(entry.getValue()));
            }

            Files.createDirectories(file.getParent());
            Files.writeString(file, GSON.toJson(defaults));

        } catch (IOException e) {
            throw new RuntimeException("Failed to create ore color config", e);
        }
    }

    public void load() {
        try {
            if (!Files.exists(file)) {
                generateDefaultFile();
            }

            String json = Files.readString(file);

            @SuppressWarnings("unchecked")
            Map<String, String> raw = GSON.fromJson(json, Map.class);

            if (raw == null) raw = new HashMap<>();

            COLORS.clear();

            for (var entry : raw.entrySet()) {
                COLORS.put(entry.getKey(), parseHex(entry.getValue()));
            }

            boolean changed = false;

            for (var def : OreDefaults.COLORS.entrySet()) {
                if (!COLORS.containsKey(def.getKey())) {
                    COLORS.put(def.getKey(), def.getValue());
                    changed = true;
                }
            }

            if (changed) {
                save();
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load ore color config", e);
        }
    }

    private void save() {
        try {
            Map<String, String> out = new HashMap<>();

            for (var entry : COLORS.entrySet()) {
                out.put(entry.getKey(), toHex(entry.getValue()));
            }

            Files.createDirectories(file.getParent());
            Files.writeString(file, GSON.toJson(out));

        } catch (IOException e) {
            throw new RuntimeException("Failed to save ore color config", e);
        }
    }

    public int getColor(String ore) {
        return COLORS.getOrDefault(ore, OreDefaults.get(ore));
    }

    private static String toHex(int color) {
        return String.format("#%08X", color);
    }

    private static int parseHex(String hex) {
        return (int) Long.parseLong(hex.replace("#", ""), 16);
    }
}