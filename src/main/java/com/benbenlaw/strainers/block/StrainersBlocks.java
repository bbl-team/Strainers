package com.benbenlaw.strainers.block;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.custom.StrainerBlock;
import com.benbenlaw.strainers.item.StrainersItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class StrainersBlocks {

    public static final DeferredRegister.Blocks BLOCKS =DeferredRegister.createBlocks(Strainers.MOD_ID);

    //New Blocks
    public static final DeferredBlock<Block> STRAINER = registerBlock("strainer",
            properties -> new StrainerBlock(properties
                    .strength(1.0F)
                    .noOcclusion()));

    public static final DeferredBlock<Block> DUST_BLOCK = registerBlock("dust_block",
            properties -> new Block(properties
                    .strength(1.0F)
                    .sound(SoundType.SAND)));


    public static final DeferredBlock<Block> MULCH = registerBlock("mulch",
            properties -> new Block(properties
                    .strength(1.0F)
                    .sound(SoundType.MUD)));

    public static final DeferredBlock<Block> PURIFIED_DUST_BLOCK = registerBlock("purified_dust_block",
            properties -> new Block(properties
                    .strength(1.0F)
                    .sound(SoundType.SAND)));

    public static final DeferredBlock<Block> PURIFIED_SAND = registerBlock("purified_sand",
            properties -> new Block(properties
                    .strength(1.0F)
                    .sound(SoundType.SAND)));

    public static final DeferredBlock<Block> PURIFIED_GRAVEL = registerBlock("purified_gravel",
            properties -> new Block(properties
                    .strength(1.0F)
                    .sound(SoundType.GRAVEL)));

    public static final DeferredBlock<Block> PURIFIED_DIRT = registerBlock("purified_dirt",
            properties -> new Block(properties
                    .strength(1.0F)
                    .sound(SoundType.GRAVEL)));

    public static final DeferredBlock<Block> PURIFIED_STONE = registerBlock("purified_stone",
            properties -> new Block(properties
                    .strength(1.0F)
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> PURIFIED_SOUL_SAND = registerBlock("purified_soul_sand",
            properties -> new Block(properties
                    .strength(1.0F)
                    .sound(SoundType.SOUL_SAND)));

    public static final DeferredBlock<Block> PURIFIED_SOUL_SOIL = registerBlock("purified_soul_soil",
            properties -> new Block(properties
                    .strength(1.0F)
                    .sound(SoundType.SOUL_SOIL)));

    public static final DeferredBlock<Block> PURIFIED_NETHERRACK = registerBlock("purified_netherrack",
            properties -> new Block(properties
                    .strength(1.0F)
                    .sound(SoundType.NETHERRACK)));

    public static final DeferredBlock<Block> PURIFIED_DEEPSLATE = registerBlock("purified_deepslate",
            properties -> new Block(properties
                    .strength(3.0F)
                    .sound(SoundType.DEEPSLATE)));






    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        StrainersItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }
}
