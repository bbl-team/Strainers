package com.benbenlaw.strainers.data;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.item.StrainersItems;
import com.benbenlaw.strainers.util.StrainersTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
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

        //Enchantments
        tag(ItemTags.MINING_LOOT_ENCHANTABLE).addTag(StrainersTags.Items.MESHES);
        tag(ItemTags.MINING_ENCHANTABLE).addTag(StrainersTags.Items.MESHES);
        tag(ItemTags.DURABILITY_ENCHANTABLE).addTag(StrainersTags.Items.MESHES);
        tag(Tags.Items.ENCHANTABLES).addTag(StrainersTags.Items.MESHES);

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
                .add(StrainersItems.ORE_PIECE.get())
        ;

        tag(Tags.Items.DUSTS)
                .add(StrainersItems.DUST.get())
                .add(StrainersItems.SAND_DUST.get())
                .add(StrainersItems.SCULK_DUST.get())
        ;

    }

    @Override
    public @NotNull String getName() {
        return Strainers.MOD_ID + " Item Tags";
    }
}
