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
        add("block.strainers.eroding_water", "Eroding Water");
        add("block.strainers.purifying_water", "Purifying Water");
        add("block.strainers.salty_water", "Salty Water");
        add("block.strainers.mulch", "Mulch");
        add("block.strainers.purified_dust_block", "Purified Dust Block");
        add("block.strainers.purified_sand", "Purified Sand");
        add("block.strainers.purified_gravel", "Purified Gravel");
        add("block.strainers.purified_dirt", "Purified Dirt");
        add("block.strainers.purified_stone", "Purified Stone");
        add("block.strainers.purified_netherrack", "Purified Netherrack");
        add("block.strainers.purified_soul_sand", "Purified Soul Sand");
        add("block.strainers.purified_soul_soil", "Purified Soul Soil");
        add("block.strainers.purified_deepslate", "Purified Deepslate");
        add("block.strainers.purified_end_stone", "Purified End Stone");

        //Items
        add("item.strainers.wooden_mesh", "Wooden Mesh");
        add("item.strainers.flint_mesh", "Flint Mesh");
        add("item.strainers.copper_mesh", "Copper Mesh");
        add("item.strainers.iron_mesh", "Iron Mesh");
        add("item.strainers.gold_mesh", "Gold Mesh");
        add("item.strainers.diamond_mesh", "Diamond Mesh");
        add("item.strainers.emerald_mesh", "Emerald Mesh");
        add("item.strainers.netherite_mesh", "Netherite Mesh");

        add("item.strainers.iron_ore_piece", "Iron Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.gold_ore_piece", "Gold Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.copper_ore_piece", "Copper Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.coal_ore_piece", "Coal Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.diamond_ore_piece", "Diamond Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.emerald_ore_piece", "Emerald Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.tin_ore_piece", "Tin Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.silver_ore_piece", "Silver Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.lead_ore_piece", "Lead Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.nickel_ore_piece", "Nickel Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.zinc_ore_piece", "Zinc Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.platinum_ore_piece", "Platinum Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.osmium_ore_piece", "Osmium Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.uranium_ore_piece", "Uranium Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.aluminum_ore_piece", "Aluminum Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.quartz_ore_piece", "Quartz Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.debris_ore_piece", "Debris Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.lapis_ore_piece", "Lapis Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");
        add("item.strainers.redstone_ore_piece", "Redstone Ore Piece (THIS ITEM WILL BE REMOVED IN FUTURE MOD UPDATE)");

        add("item.strainers.ore_piece", "%s Ore Piece");

        add("item.strainers.stone_pebble", "Stone Pebble");
        add("item.strainers.gravel_pebble", "Gravel Pebble");
        add("item.strainers.sand_dust", "Sand Dust");
        add("item.strainers.dust", "Dust");
        add("item.strainers.sculk_dust", "Sculk Dust");

        add("item.strainers.eroding_water_bucket", "Eroding Water Bucket");
        add("item.strainers.purifying_water_bucket", "Purifying Water Bucket");
        add("item.strainers.salty_water_bucket", "Salty Water Bucket");

        add("item.strainers.sapling_bag", "Sapling Bag (What's inside!)");
        add("item.strainers.seed_bag", "Seed Bag (What's inside!)");
        add("item.strainers.leaf_pile", "Leaf Pile");
        add("item.strainers.water_drop", "Water Drop");
        add("item.strainers.lava_drop", "Lava Drop");
        add("item.strainers.eroding_drop", "Eroding Drop");
        add("item.strainers.purifying_drop", "Purifying Drop");
        add("item.strainers.salt_water_drop", "Salt Water Drop");
        add("item.strainers.depleted_drop", "Depleted Drop");
        add("item.strainers.dirt_pile", "Dirt Pile");

        //Tooltip
        add("tooltip.strainers.fluids_header", "Fluids:");
        add("tooltip.strainers.fluid_drop_item", "Holding 4 and right clicking will create a source block of %s. Can also be used to fill tanks like a bucket would!");

        add("tooltip.strainers.tier_1_mesh", "Tier 1 Mesh");
        add("tooltip.strainers.tier_2_mesh", "Tier 2 Mesh");
        add("tooltip.strainers.tier_3_mesh", "Tier 3 Mesh");
        add("tooltip.strainers.tier_4_mesh", "Tier 4 Mesh");
        add("tooltip.strainers.tier_5_mesh", "Tier 5 Mesh");
        add("tooltip.strainers.tier_6_mesh", "Tier 6 Mesh");
        add("tooltip.strainers.tier_7_mesh", "Tier 7 Mesh");
        add("tooltip.strainers.tier_8_mesh", "Tier 8 Mesh");
        add("tooltip.strainers.tier_9_mesh", "Tier 9 Mesh");
        add("tooltip.strainers.tier_10_mesh", "Tier 10 Mesh");
        add("tooltip.strainers.tier_11_mesh", "Tier 11 Mesh");
        add("tooltip.strainers.tier_12_mesh", "Tier 12 Mesh");

        add("tooltip.strainers.sapling_bag", "Right Click to place a random sapling, on Dirt or Grass");
        add("tooltip.strainers.seed_bag", "Right Click to place a random seed, on Farmland");
        add("tooltip.strainers.depleted_drop", "Yes its useless just throw me away !");

        //JEI
        add("jei.strainers.strainer", "Strainer");
        add("jei.strainers.chance", "Chance: %s%%");
        add("jei.strainers.additional", "Additional %s%% per level above minimum mesh tier");
        add("jei.strainers.leaf_pile", "Leaf Piles can be obtained when breaking leaves with a Stick");

    }

    @Override
    public @NotNull String getName() {
        return Strainers.MOD_ID + " Language Provider";
    }
}
