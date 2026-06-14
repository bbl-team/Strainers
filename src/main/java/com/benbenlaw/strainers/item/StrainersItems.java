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
    public static final DeferredItem<Item> DIRT_PILE = ITEMS.registerSimpleItem("dirt_pile");
    public static final DeferredItem<Item> DEPLETED_DROP = ITEMS.registerSimpleItem("depleted_drop");
    public static final DeferredItem<Item> SCULK_DUST = ITEMS.registerSimpleItem("sculk_dust");
    public static final DeferredItem<Item> WATER_DROP = ITEMS.registerItem("water_drop",
            (properties) -> new FluidDropItem(properties, Fluids.WATER));
    public static final DeferredItem<Item> LAVA_DROP = ITEMS.registerItem("lava_drop",
            (properties) -> new FluidDropItem(properties, Fluids.LAVA));
    public static final DeferredItem<Item> ERODING_DROP = ITEMS.registerItem("eroding_drop",
            (properties) -> new FluidDropItem(properties, StrainersFluids.ERODING_WATER.getFluid()));
    public static final DeferredItem<Item> PURIFYING_DROP = ITEMS.registerItem("purifying_drop",
            (properties) -> new FluidDropItem(properties, StrainersFluids.PURIFYING_WATER.getFluid()));
    public static final DeferredItem<Item> SALT_WATER_DROP = ITEMS.registerItem("salt_water_drop",
            (properties) -> new FluidDropItem(properties, StrainersFluids.SALTY_WATER.getFluid()));
    public static final DeferredItem<Item> ORE_PIECE = ITEMS.registerItem("ore_piece", OrePieceItem::new);

    //Meshes
    public static final DeferredItem<Item> WOODEN_MESH = ITEMS.registerItem("wooden_mesh",
            Item::new, properties -> properties.durability(64).enchantable(15));
    public static final DeferredItem<Item> FLINT_MESH = ITEMS.registerItem("flint_mesh",
            Item::new, properties -> properties.durability(128).enchantable(5));
    public static final DeferredItem<Item> COPPER_MESH = ITEMS.registerItem("copper_mesh",
            Item::new, properties -> properties.durability(256).enchantable(12));
    public static final DeferredItem<Item> IRON_MESH = ITEMS.registerItem("iron_mesh",
            Item::new, properties -> properties.durability(512).enchantable(15));
    public static final DeferredItem<Item> GOLD_MESH = ITEMS.registerItem("gold_mesh",
            Item::new, properties -> properties.durability(256).enchantable(22));
    public static final DeferredItem<Item> DIAMOND_MESH = ITEMS.registerItem("diamond_mesh",
            Item::new, properties -> properties.durability(1024).enchantable(10));
    public static final DeferredItem<Item> EMERALD_MESH = ITEMS.registerItem("emerald_mesh",
            Item::new, properties -> properties.durability(1024).enchantable(22));
    public static final DeferredItem<Item> NETHERITE_MESH = ITEMS.registerItem("netherite_mesh",
            Item::new, properties -> properties.durability(2048).enchantable(15));
    
    //Misc
    public static final DeferredItem<Item> SAPLING_BAG = ITEMS.registerItem("sapling_bag", SaplingBagItem::new);
    public static final DeferredItem<Item> SEED_BAG = ITEMS.registerItem("seed_bag", SeedBagItem::new);

}
