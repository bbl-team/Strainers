package com.benbenlaw.strainers.block;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.custom.MulchBlock;
import com.benbenlaw.strainers.block.custom.StrainerTankBlock;
import com.benbenlaw.strainers.block.custom.WoodenStrainerBlock;
import com.benbenlaw.strainers.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Strainers.MOD_ID);

    //New Blocks

    public static final DeferredBlock<Block> WOODEN_STRAINER = registerBlock("wooden_strainer",
            () -> new WoodenStrainerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE).strength(0.5f).sound(SoundType.WOOD)
                    .noOcclusion()));

    public static final DeferredBlock<Block> STRAINER_TANK = registerBlock("strainer_tank",
            () -> new StrainerTankBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).sound(SoundType.GLASS)
                    .noOcclusion()));

    public static final DeferredBlock<Block> MULCH = registerBlock("mulch",
            () -> new MulchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).strength(0.5f).sound(SoundType.PACKED_MUD)));

    public static final DeferredBlock<Block> PURIFIED_GRAVEL = registerBlock("purified_gravel",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).strength(0.5f).sound(SoundType.PACKED_MUD)));

    public static final DeferredBlock<Block> PURIFIED_SAND = registerBlock("purified_sand",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).strength(0.5f).sound(SoundType.PACKED_MUD)));

    public static final DeferredBlock<Block> PURIFIED_DIRT = registerBlock("purified_dirt",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).strength(0.5f).sound(SoundType.PACKED_MUD)));

    public static final DeferredBlock<Block> PURIFIED_SOUL_SAND = registerBlock("purified_soul_sand",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).strength(0.5f).sound(SoundType.PACKED_MUD)));


    //ORE Blocks
    public static final DeferredBlock<Block> IRON_ORE_BLOCK = registerBlock("iron_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> COPPER_ORE_BLOCK = registerBlock("copper_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> TIN_ORE_BLOCK = registerBlock("tin_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SILVER_ORE_BLOCK = registerBlock("silver_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> LEAD_ORE_BLOCK = registerBlock("lead_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> NICKEL_ORE_BLOCK = registerBlock("nickel_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> ZINC_ORE_BLOCK = registerBlock("zinc_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> PLATINUM_ORE_BLOCK = registerBlock("platinum_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> OSMIUM_ORE_BLOCK = registerBlock("osmium_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> URANIUM_ORE_BLOCK = registerBlock("uranium_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> ALUMINUM_ORE_BLOCK = registerBlock("aluminum_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> GOLD_ORE_BLOCK = registerBlock("gold_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> DIAMOND_ORE_BLOCK = registerBlock("diamond_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> EMERALD_ORE_BLOCK = registerBlock("emerald_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> LAPIS_ORE_BLOCK = registerBlock("lapis_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> REDSTONE_ORE_BLOCK = registerBlock("redstone_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> COAL_ORE_BLOCK = registerBlock("coal_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> QUARTZ_ORE_BLOCK = registerBlock("quartz_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> DEBRIS_ORE_BLOCK = registerBlock("debris_ore_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(0.5f).sound(SoundType.STONE)));








    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = (DeferredBlock<T>) BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));

    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
