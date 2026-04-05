package com.benbenlaw.strainers.block;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.custom.StrainerBlock;
import com.benbenlaw.strainers.item.StrainersItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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



    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        StrainersItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }
}
