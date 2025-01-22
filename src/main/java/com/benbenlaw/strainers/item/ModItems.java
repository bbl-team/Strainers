package com.benbenlaw.strainers.item;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.item.custom.ErodingSaltMulchItem;
import com.benbenlaw.strainers.item.custom.MeshItem;
import com.benbenlaw.strainers.item.custom.PurifyingSaltMulchItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Strainers.MOD_ID);

    public static final DeferredItem<Item> STONE_PEBBLE = ITEMS.register("stone_pebble",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PURIFYING_SALT_MULCH = ITEMS.register("purifying_salt_mulch",
            () -> new PurifyingSaltMulchItem(new Item.Properties()));

    public static final DeferredItem<Item> ERODING_SALT_MULCH = ITEMS.register("eroding_salt_mulch",
            () -> new ErodingSaltMulchItem(new Item.Properties()));

    //TIER 1 MESHES

    public static final DeferredItem<Item> WOODEN_MESH = ITEMS.register("wooden_mesh",
            () -> new MeshItem(new Item.Properties().durability(186)));
    public static final DeferredItem<Item> LEAFY_MESH = ITEMS.register("leafy_mesh",
            () -> new MeshItem(new Item.Properties().durability(96)));
    public static final DeferredItem<Item> BAMBOO_MESH = ITEMS.register("bamboo_mesh",
            () -> new MeshItem(new Item.Properties().durability(320)));
    public static final DeferredItem<Item> STRING_MESH = ITEMS.register("string_mesh",
            () -> new MeshItem(new Item.Properties().durability(160)));
    public static final DeferredItem<Item> BONE_MESH = ITEMS.register("bone_mesh",
            () -> new MeshItem(new Item.Properties().durability(193)));

    //TIER 2 MESHES
    public static final DeferredItem<Item> FLINT_MESH = ITEMS.register("flint_mesh",
            () -> new MeshItem(new Item.Properties().durability(140)));
    public static final DeferredItem<Item> COPPER_MESH = ITEMS.register("copper_mesh",
            () -> new MeshItem(new Item.Properties().durability(384)));
    public static final DeferredItem<Item> LAPIS_MESH = ITEMS.register("lapis_mesh",
            () -> new MeshItem(new Item.Properties().durability(307)));
    public static final DeferredItem<Item> TIN_MESH = ITEMS.register("tin_mesh",
            () -> new MeshItem(new Item.Properties().durability(307)));


    //TIER 3 MESHES
    public static final DeferredItem<Item> IRON_MESH = ITEMS.register("iron_mesh",
            () -> new MeshItem(new Item.Properties().durability(526)));
    public static final DeferredItem<Item> AMETHYST_MESH = ITEMS.register("amethyst_mesh",
            () -> new MeshItem(new Item.Properties().durability(640)));
    public static final DeferredItem<Item> BRONZE_MESH = ITEMS.register("bronze_mesh",
            () -> new MeshItem(new Item.Properties().durability(640)));
    public static final DeferredItem<Item> REDSTONE_MESH = ITEMS.register("redstone_mesh",
            () -> new MeshItem(new Item.Properties().durability(342)));

    //TIER 4 MESHES

    public static final DeferredItem<Item> GOLD_MESH = ITEMS.register("gold_mesh",
            () -> new MeshItem(new Item.Properties().durability(128)));
    public static final DeferredItem<Item> QUARTZ_MESH = ITEMS.register("quartz_mesh",
            () -> new MeshItem(new Item.Properties().durability(640)));
    public static final DeferredItem<Item> BLAZE_MESH = ITEMS.register("blaze_mesh",
            () -> new MeshItem(new Item.Properties().durability(873)));
    public static final DeferredItem<Item> BREEZE_MESH = ITEMS.register("breeze_mesh",
            () -> new MeshItem(new Item.Properties().durability(1024)));

    //TIER 5 MESHES

    public static final DeferredItem<Item> DIAMOND_MESH = ITEMS.register("diamond_mesh",
            () -> new MeshItem(new Item.Properties().durability(1536)));
    public static final DeferredItem<Item> ECHO_MESH = ITEMS.register("echo_mesh",
            () -> new MeshItem(new Item.Properties().durability(768)));
    public static final DeferredItem<Item> EMERALD_MESH = ITEMS.register("emerald_mesh",
            () -> new MeshItem(new Item.Properties().durability(1536)));
    public static final DeferredItem<Item> PRISMARINE_MESH = ITEMS.register("prismarine_mesh",
            () -> new MeshItem(new Item.Properties().durability(2048)));

    //TIER 6 MESHES
    public static final DeferredItem<Item> NETHERITE_MESH = ITEMS.register("netherite_mesh",
            () -> new MeshItem(new Item.Properties().durability(2048)));
    public static final DeferredItem<Item> OBSIDIAN_MESH = ITEMS.register("obsidian_mesh",
            () -> new MeshItem(new Item.Properties().durability(2535)));
    public static final DeferredItem<Item> END_MESH = ITEMS.register("end_mesh",
            () -> new MeshItem(new Item.Properties().durability(1894)));
    public static final DeferredItem<Item> HEAVY_MESH = ITEMS.register("heavy_mesh",
            () -> new MeshItem(new Item.Properties().durability(2749)));

    //ORE PIECES

    public static final DeferredItem<Item> IRON_ORE_PIECE = ITEMS.register("iron_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COPPER_ORE_PIECE = ITEMS.register("copper_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TIN_ORE_PIECE = ITEMS.register("tin_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SILVER_ORE_PIECE = ITEMS.register("silver_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LEAD_ORE_PIECE = ITEMS.register("lead_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NICKEL_ORE_PIECE = ITEMS.register("nickel_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ZINC_ORE_PIECE = ITEMS.register("zinc_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PLATINUM_ORE_PIECE = ITEMS.register("platinum_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> OSMIUM_ORE_PIECE = ITEMS.register("osmium_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> URANIUM_ORE_PIECE = ITEMS.register("uranium_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ALUMINUM_ORE_PIECE = ITEMS.register("aluminum_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLD_ORE_PIECE = ITEMS.register("gold_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DIAMOND_ORE_PIECE = ITEMS.register("diamond_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> EMERALD_ORE_PIECE = ITEMS.register("emerald_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LAPIS_ORE_PIECE = ITEMS.register("lapis_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> REDSTONE_ORE_PIECE = ITEMS.register("redstone_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COAL_ORE_PIECE = ITEMS.register("coal_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> QUARTZ_ORE_PIECE = ITEMS.register("quartz_ore_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DEBRIS_ORE_PIECE = ITEMS.register("debris_ore_piece", () -> new Item(new Item.Properties()));


    //Strainer Upgrades
    public static final DeferredItem<Item> MESH_UPGRADE_1 = ITEMS.register("mesh_upgrade_1",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MESH_UPGRADE_2 = ITEMS.register("mesh_upgrade_2",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MESH_UPGRADE_3 = ITEMS.register("mesh_upgrade_3",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> OUTPUT_UPGRADE_1 = ITEMS.register("output_upgrade_1",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> OUTPUT_UPGRADE_2 = ITEMS.register("output_upgrade_2",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> OUTPUT_UPGRADE_3 = ITEMS.register("output_upgrade_3",
            () -> new Item(new Item.Properties()));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
