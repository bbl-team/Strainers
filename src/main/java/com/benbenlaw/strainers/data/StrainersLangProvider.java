package com.benbenlaw.strainers.data;

import com.benbenlaw.strainers.Strainers;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

public class StrainersLangProvider extends LanguageProvider {

    public StrainersLangProvider(PackOutput output) {
        super(output, Strainers.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.strainers", "Strainers");

        //Blocks
        add("block.strainers.strainer", "Strainer");
        add("block.strainers.dust_block", "Dust Block");

        //Items
        add("item.strainers.wooden_mesh", "Wooden Mesh");
        add("item.strainers.flint_mesh", "Flint Mesh");
        add("item.strainers.copper_mesh", "Copper Mesh");
        add("item.strainers.iron_mesh", "Iron Mesh");
        add("item.strainers.gold_mesh", "Gold Mesh");
        add("item.strainers.diamond_mesh", "Diamond Mesh");
        add("item.strainers.emerald_mesh", "Emerald Mesh");
        add("item.strainers.netherite_mesh", "Netherite Mesh");

        add("item.strainers.iron_ore_piece", "Iron Ore Piece");
        add("item.strainers.gold_ore_piece", "Gold Ore Piece");
        add("item.strainers.copper_ore_piece", "Copper Ore Piece");
        add("item.strainers.coal_ore_piece", "Coal Ore Piece");
        add("item.strainers.diamond_ore_piece", "Diamond Ore Piece");
        add("item.strainers.emerald_ore_piece", "Emerald Ore Piece");
        add("item.strainers.tin_ore_piece", "Tin Ore Piece");
        add("item.strainers.silver_ore_piece", "Silver Ore Piece");
        add("item.strainers.lead_ore_piece", "Lead Ore Piece");
        add("item.strainers.nickel_ore_piece", "Nickel Ore Piece");
        add("item.strainers.zinc_ore_piece", "Zinc Ore Piece");
        add("item.strainers.platinum_ore_piece", "Platinum Ore Piece");
        add("item.strainers.osmium_ore_piece", "Osmium Ore Piece");
        add("item.strainers.uranium_ore_piece", "Uranium Ore Piece");
        add("item.strainers.aluminum_ore_piece", "Aluminum Ore Piece");
        add("item.strainers.quartz_ore_piece", "Quartz Ore Piece");
        add("item.strainers.debris_ore_piece", "Debris Ore Piece");

        add("item.strainers.stone_pebble", "Stone Pebble");
        add("item.strainers.gravel_pebble", "Gravel Pebble");
        add("item.strainers.sand_dust", "Sand Dust");
        add("item.strainers.dust", "Dust");

        add("item.strainers.eroding_water_bucket", "Eroding Water Bucket");
        add("item.strainers.purifying_water_bucket", "Purifying Water Bucket");

        add("item.strainers.sapling_seed", "Sapling Seeds (What's inside!)");
        add("item.strainers.leaf_pile", "Leaf Pile");








        //JEI
        add("jei.strainers.strainer", "Strainer");
        add("jei.strainers.chance", "Chance: %s%%");
        add("jei.strainers.additional", "Additional %s%% per level above minimum mesh tier");

    }

    @Override
    public @NotNull String getName() {
        return Strainers.MOD_ID + " Language Provider";
    }
}
