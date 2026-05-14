package com.benbenlaw.strainers.data;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.StrainersBlocks;
import com.benbenlaw.strainers.item.StrainersItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class StrainersDataMapsProvider extends DataMapProvider {


    public StrainersDataMapsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> providerCompletableFuture) {
        super(output, providerCompletableFuture);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        var burnable = this.builder(NeoForgeDataMaps.COMPOSTABLES);

        burnable.add(StrainersItems.LEAF_PILE, new Compostable(0.25f), false);
        burnable.add(StrainersBlocks.MULCH.getId(), new Compostable(1f), false);
        //burnable.add(StrainersBlocks.MULCH.get().asItem().builtInRegistryHolder(), new Compostable(0.75f), false);


    }

    @Override
    public String getName() {
        return Strainers.MOD_ID + " Data Maps";
    }
}
