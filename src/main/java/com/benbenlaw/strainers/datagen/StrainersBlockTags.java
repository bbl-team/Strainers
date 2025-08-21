package com.benbenlaw.strainers.datagen;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.ModBlocks;
import com.benbenlaw.strainers.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class StrainersBlockTags extends BlockTagsProvider {

    StrainersBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Strainers.MOD_ID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        //Axe
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.STRAINER_TANK.get());

        //Pickaxe
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.STRAINER_TANK.get())
        ;

        //Shovel
        tag(BlockTags.MINEABLE_WITH_SHOVEL)

                .add(ModBlocks.PURIFIED_SAND.get())
                .add(ModBlocks.PURIFIED_GRAVEL.get())
                .add(ModBlocks.PURIFIED_SOUL_SAND.get())
                .add(ModBlocks.PURIFIED_DIRT.get())

                .add(ModBlocks.IRON_ORE_BLOCK.get())
                .add(ModBlocks.GOLD_ORE_BLOCK.get())
                .add(ModBlocks.COPPER_ORE_BLOCK.get())
                .add(ModBlocks.SILVER_ORE_BLOCK.get())
                .add(ModBlocks.TIN_ORE_BLOCK.get())
                .add(ModBlocks.LAPIS_ORE_BLOCK.get())
                .add(ModBlocks.REDSTONE_ORE_BLOCK.get())
                .add(ModBlocks.DIAMOND_ORE_BLOCK.get())
                .add(ModBlocks.EMERALD_ORE_BLOCK.get())
                .add(ModBlocks.LEAD_ORE_BLOCK.get())
                .add(ModBlocks.QUARTZ_ORE_BLOCK.get())
                .add(ModBlocks.NICKEL_ORE_BLOCK.get())
                .add(ModBlocks.ZINC_ORE_BLOCK.get())
                .add(ModBlocks.PLATINUM_ORE_BLOCK.get())
                .add(ModBlocks.OSMIUM_ORE_BLOCK.get())
                .add(ModBlocks.URANIUM_ORE_BLOCK.get())
                .add(ModBlocks.ALUMINUM_ORE_BLOCK.get())
                .add(ModBlocks.COAL_ORE_BLOCK.get())
                .add(ModBlocks.DEBRIS_ORE_BLOCK.get())
                ;


                
                

    }

    @Override
    public @NotNull String getName() {
        return Strainers.MOD_ID + " Block Tags";
    }
}
