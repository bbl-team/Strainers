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
        tag(StrainersTags.Items.MESHES)
            .addTag(StrainersTags.Items.TIER_1_MESHES)
            .addTag(StrainersTags.Items.TIER_2_MESHES)
            .addTag(StrainersTags.Items.TIER_3_MESHES)
            .addTag(StrainersTags.Items.TIER_4_MESHES)
            .addTag(StrainersTags.Items.TIER_5_MESHES)
            .addTag(StrainersTags.Items.TIER_6_MESHES)
            .addTag(StrainersTags.Items.TIER_7_MESHES)
            .addTag(StrainersTags.Items.TIER_8_MESHES)
        ;

        tag(StrainersTags.Items.TIER_1_MESHES).add(StrainersItems.WOODEN_MESH.get());
        tag(StrainersTags.Items.TIER_2_MESHES).add(StrainersItems.FLINT_MESH.get());
        tag(StrainersTags.Items.TIER_3_MESHES).add(StrainersItems.COPPER_MESH.get());
        tag(StrainersTags.Items.TIER_4_MESHES).add(StrainersItems.IRON_MESH.get());
        tag(StrainersTags.Items.TIER_5_MESHES).add(StrainersItems.GOLD_MESH.get());
        tag(StrainersTags.Items.TIER_6_MESHES).add(StrainersItems.DIAMOND_MESH.get());
        tag(StrainersTags.Items.TIER_7_MESHES).add(StrainersItems.EMERALD_MESH.get());
        tag(StrainersTags.Items.TIER_8_MESHES).add(StrainersItems.NETHERITE_MESH.get());

        //Ore Pieces
        tag(StrainersTags.Items.ORE_PIECES)
            .add(StrainersItems.IRON_ORE_PIECE.get())
            .add(StrainersItems.GOLD_ORE_PIECE.get())
            .add(StrainersItems.COPPER_ORE_PIECE.get())
            .add(StrainersItems.DIAMOND_ORE_PIECE.get())
            .add(StrainersItems.EMERALD_ORE_PIECE.get())
            .add(StrainersItems.DEBRIS_ORE_PIECE.get())

            .add(StrainersItems.SILVER_ORE_PIECE.get())
            .add(StrainersItems.LEAD_ORE_PIECE.get())
            .add(StrainersItems.NICKEL_ORE_PIECE.get())
            .add(StrainersItems.URANIUM_ORE_PIECE.get())
            .add(StrainersItems.ZINC_ORE_PIECE.get())
            .add(StrainersItems.PLATINUM_ORE_PIECE.get())
            .add(StrainersItems.TIN_ORE_PIECE.get())
            .add(StrainersItems.NICKEL_ORE_PIECE.get())
            .add(StrainersItems.OSMIUM_ORE_PIECE.get())
            .add(StrainersItems.ALUMINUM_ORE_PIECE.get())
            .add(StrainersItems.LAPIS_ORE_PIECE.get())
            .add(StrainersItems.REDSTONE_ORE_PIECE.get())
            .add(StrainersItems.COAL_ORE_PIECE.get())
            .add(StrainersItems.QUARTZ_ORE_PIECE.get())

        ;

    }

    @Override
    public @NotNull String getName() {
        return Strainers.MOD_ID + " Item Tags";
    }
}
