package com.benbenlaw.strainers.data;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.StrainersBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class StrainersBlockTags extends BlockTagsProvider {

    StrainersBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Strainers.MOD_ID);
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        //Pickaxe
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(StrainersBlocks.STRAINER.get())
        ;
    }

    @Override
    public @NotNull String getName() {
        return Strainers.MOD_ID + " Block Tags";
    }
}
