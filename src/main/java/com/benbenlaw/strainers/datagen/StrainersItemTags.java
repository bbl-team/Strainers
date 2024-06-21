package com.benbenlaw.strainers.datagen;

import com.benbenlaw.opolisutilities.OpolisUtilities;
import com.benbenlaw.opolisutilities.util.ModTags;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.ModBlocks;
import com.benbenlaw.strainers.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class StrainersItemTags extends ItemTagsProvider {

    StrainersItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags.contentsGetter(), Strainers.MOD_ID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        //Enchantments

        //Banned In Block Placer
        tag(ModTags.Items.BANNED_IN_BLOCK_PLACER)
                .add(ModBlocks.WOODEN_STRAINER.asItem())
                .add(ModBlocks.STRAINER_TANK.asItem())
                ;

        //Mesh Tags
        tag(com.benbenlaw.strainers.util.ModTags.Items.TIER_1_MESHES)
                .add(ModItems.WOODEN_MESH.get())
                .add(ModItems.LEAFY_MESH.get())
                .add(ModItems.BAMBOO_MESH.get())
                .add(ModItems.STRING_MESH.get())
                .add(ModItems.BONE_MESH.get())
                ;

        tag(com.benbenlaw.strainers.util.ModTags.Items.TIER_2_MESHES)
                .add(ModItems.FLINT_MESH.get())
                .add(ModItems.COPPER_MESH.get())
                .add(ModItems.LAPIS_MESH.get())
                .add(ModItems.TIN_MESH.get())
                ;

        tag(com.benbenlaw.strainers.util.ModTags.Items.TIER_3_MESHES)
                .add(ModItems.IRON_MESH.get())
                .add(ModItems.AMETHYST_MESH.get())
                .add(ModItems.REDSTONE_MESH.get())
                .add(ModItems.BRONZE_MESH.get())
                ;

        tag(com.benbenlaw.strainers.util.ModTags.Items.TIER_4_MESHES)
                .add(ModItems.GOLD_MESH.get())
                .add(ModItems.QUARTZ_MESH.get())
                .add(ModItems.BLAZE_MESH.get())
                .add(ModItems.BREEZE_MESH.get())
                ;

        tag(com.benbenlaw.strainers.util.ModTags.Items.TIER_5_MESHES)
                .add(ModItems.DIAMOND_MESH.get())
                .add(ModItems.EMERALD_MESH.get())
                .add(ModItems.ECHO_MESH.get())
                .add(ModItems.PRISMARINE_MESH.get())
                ;

        tag(com.benbenlaw.strainers.util.ModTags.Items.TIER_6_MESHES)
                .add(ModItems.OBSIDIAN_MESH.get())
                .add(ModItems.END_MESH.get())
                .add(ModItems.HEAVY_MESH.get())
                .add(ModItems.NETHERITE_MESH.get())
                ;

        tag(com.benbenlaw.strainers.util.ModTags.Items.MESHES)
                .addTag(com.benbenlaw.strainers.util.ModTags.Items.TIER_1_MESHES)
                .addTag(com.benbenlaw.strainers.util.ModTags.Items.TIER_2_MESHES)
                .addTag(com.benbenlaw.strainers.util.ModTags.Items.TIER_3_MESHES)
                .addTag(com.benbenlaw.strainers.util.ModTags.Items.TIER_4_MESHES)
                .addTag(com.benbenlaw.strainers.util.ModTags.Items.TIER_5_MESHES)
                .addTag(com.benbenlaw.strainers.util.ModTags.Items.TIER_6_MESHES)
                ;




    }

    @Override
    public @NotNull String getName() {
        return Strainers.MOD_ID + " Item Tags";
    }
}
