package com.benbenlaw.strainers.item;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.fluid.StrainersFluids;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StrainersItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Strainers.MOD_ID);

    public static final DeferredItem<Item> STONE_PEBBLE = ITEMS.registerSimpleItem("stone_pebble");
    public static final DeferredItem<Item> GRAVEL_PEBBLE = ITEMS.registerSimpleItem("gravel_pebble");
    public static final DeferredItem<Item> SAND_DUST = ITEMS.registerSimpleItem("sand_dust");
    public static final DeferredItem<Item> DUST = ITEMS.registerSimpleItem("dust");
    public static final DeferredItem<Item> LEAF_PILE = ITEMS.registerSimpleItem("leaf_pile");
    public static final DeferredItem<Item> WATER_DROP = ITEMS.registerItem("water_drop",
            (properties) -> new FluidDropItem(properties, Fluids.WATER));
    public static final DeferredItem<Item> LAVA_DROP = ITEMS.registerItem("lava_drop",
            (properties) -> new FluidDropItem(properties, Fluids.LAVA));
    public static final DeferredItem<Item> ERODING_DROP = ITEMS.registerItem("eroding_drop",
            (properties) -> new FluidDropItem(properties, StrainersFluids.ERODING_WATER.getFluid()));
    public static final DeferredItem<Item> PURIFYING_DROP = ITEMS.registerItem("purifying_drop",
            (properties) -> new FluidDropItem(properties, StrainersFluids.PURIFYING_WATER.getFluid()));


    //public static final DeferredItem<Item> EMPTY_DROP = ITEMS.registerSimpleItem("empty_drop");

    //Meshes
    public static final DeferredItem<Item> WOODEN_MESH = ITEMS.registerItem("wooden_mesh",
            Item::new, properties -> properties.durability(64));
    public static final DeferredItem<Item> FLINT_MESH = ITEMS.registerItem("flint_mesh",
            Item::new, properties -> properties.durability(128));
    public static final DeferredItem<Item> COPPER_MESH = ITEMS.registerItem("copper_mesh",
            Item::new, properties -> properties.durability(256));
    public static final DeferredItem<Item> IRON_MESH = ITEMS.registerItem("iron_mesh",
            Item::new, properties -> properties.durability(512));
    public static final DeferredItem<Item> GOLD_MESH = ITEMS.registerItem("gold_mesh",
            Item::new, properties -> properties.durability(256));
    public static final DeferredItem<Item> DIAMOND_MESH = ITEMS.registerItem("diamond_mesh",
            Item::new, properties -> properties.durability(1024));
    public static final DeferredItem<Item> EMERALD_MESH = ITEMS.registerItem("emerald_mesh",
            Item::new, properties -> properties.durability(1024));
    public static final DeferredItem<Item> NETHERITE_MESH = ITEMS.registerItem("netherite_mesh",
            Item::new, properties -> properties.durability(2048));

    //ORE PIECES
    public static final DeferredItem<Item> IRON_ORE_PIECE = ITEMS.registerSimpleItem("iron_ore_piece");
    public static final DeferredItem<Item> COPPER_ORE_PIECE = ITEMS.registerSimpleItem("copper_ore_piece");
    public static final DeferredItem<Item> TIN_ORE_PIECE = ITEMS.registerSimpleItem("tin_ore_piece");
    public static final DeferredItem<Item> SILVER_ORE_PIECE = ITEMS.registerSimpleItem("silver_ore_piece");
    public static final DeferredItem<Item> LEAD_ORE_PIECE = ITEMS.registerSimpleItem("lead_ore_piece");
    public static final DeferredItem<Item> NICKEL_ORE_PIECE = ITEMS.registerSimpleItem("nickel_ore_piece");
    public static final DeferredItem<Item> ZINC_ORE_PIECE = ITEMS.registerSimpleItem("zinc_ore_piece");
    public static final DeferredItem<Item> PLATINUM_ORE_PIECE = ITEMS.registerSimpleItem("platinum_ore_piece");
    public static final DeferredItem<Item> OSMIUM_ORE_PIECE = ITEMS.registerSimpleItem("osmium_ore_piece");
    public static final DeferredItem<Item> URANIUM_ORE_PIECE = ITEMS.registerSimpleItem("uranium_ore_piece");
    public static final DeferredItem<Item> ALUMINUM_ORE_PIECE = ITEMS.registerSimpleItem("aluminum_ore_piece");
    public static final DeferredItem<Item> GOLD_ORE_PIECE = ITEMS.registerSimpleItem("gold_ore_piece");
    public static final DeferredItem<Item> DIAMOND_ORE_PIECE = ITEMS.registerSimpleItem("diamond_ore_piece");
    public static final DeferredItem<Item> EMERALD_ORE_PIECE = ITEMS.registerSimpleItem("emerald_ore_piece");
    public static final DeferredItem<Item> LAPIS_ORE_PIECE = ITEMS.registerSimpleItem("lapis_ore_piece");
    public static final DeferredItem<Item> REDSTONE_ORE_PIECE = ITEMS.registerSimpleItem("redstone_ore_piece");
    public static final DeferredItem<Item> COAL_ORE_PIECE = ITEMS.registerSimpleItem("coal_ore_piece");
    public static final DeferredItem<Item> QUARTZ_ORE_PIECE = ITEMS.registerSimpleItem("quartz_ore_piece");
    public static final DeferredItem<Item> DEBRIS_ORE_PIECE = ITEMS.registerSimpleItem("debris_ore_piece");

    //Misc
    public static final DeferredItem<Item> SAPLING_SEED = ITEMS.registerItem("sapling_seed",
            SaplingSeedItem::new, properties -> properties);

}
