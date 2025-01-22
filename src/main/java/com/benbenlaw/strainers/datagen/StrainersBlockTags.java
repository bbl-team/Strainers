package com.benbenlaw.strainers.datagen;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.ModBlocks;
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

    }

    @Override
    public @NotNull String getName() {
        return Strainers.MOD_ID + " Block Tags";
    }
}
