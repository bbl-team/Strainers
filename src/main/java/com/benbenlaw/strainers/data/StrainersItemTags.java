package com.benbenlaw.strainers.data;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.item.StrainersItems;
import com.benbenlaw.strainers.util.StrainersTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class StrainersItemTags extends ItemTagsProvider {

    StrainersItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Strainers.MOD_ID);
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        //Meshes
        tag(StrainersTags.Items.TIER_1_MESHES).add(StrainersItems.WOODEN_MESH.get());
        tag(StrainersTags.Items.TIER_2_MESHES).add(StrainersItems.FLINT_MESH.get());
        tag(StrainersTags.Items.TIER_3_MESHES).add(StrainersItems.COPPER_MESH.get());
        tag(StrainersTags.Items.TIER_4_MESHES).add(StrainersItems.IRON_MESH.get());
        tag(StrainersTags.Items.TIER_5_MESHES).add(StrainersItems.GOLD_MESH.get());
        tag(StrainersTags.Items.TIER_6_MESHES).add(StrainersItems.DIAMOND_MESH.get());
        tag(StrainersTags.Items.TIER_7_MESHES).add(StrainersItems.EMERALD_MESH.get());
        tag(StrainersTags.Items.TIER_8_MESHES).add(StrainersItems.NETHERITE_MESH.get());


    }

    @Override
    public @NotNull String getName() {
        return Strainers.MOD_ID + " Item Tags";
    }
}
