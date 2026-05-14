package com.benbenlaw.strainers.data;

import com.benbenlaw.strainers.block.StrainersBlocks;
import com.benbenlaw.strainers.item.StrainersDataComponents;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class StrainersLootTableProvider extends VanillaBlockLoot {

    public StrainersLootTableProvider(HolderLookup.Provider p_344962_) {
        super(p_344962_);
    }
    @Override
    protected void generate() {

        this.dropWithFluidComponent(StrainersBlocks.STRAINER.get());
        this.dropSelf(StrainersBlocks.DUST_BLOCK.get());
        this.dropSelf(StrainersBlocks.PURIFIED_DUST_BLOCK.get());
        this.dropSelf(StrainersBlocks.PURIFIED_SAND.get());
        this.dropSelf(StrainersBlocks.PURIFIED_GRAVEL.get());
        this.dropSelf(StrainersBlocks.PURIFIED_DIRT.get());
        this.dropSelf(StrainersBlocks.PURIFIED_STONE.get());
        this.dropSelf(StrainersBlocks.PURIFIED_SOUL_SAND.get());
        this.dropSelf(StrainersBlocks.PURIFIED_SOUL_SOIL.get());
        this.dropSelf(StrainersBlocks.PURIFIED_NETHERRACK.get());
        this.dropSelf(StrainersBlocks.MULCH.get());



    }

    private void dropWithFluidComponent(Block block) {
        this.add(block, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block)
                                .apply(CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                                        .include(StrainersDataComponents.FLUIDS.get())))));
    }

    @Override
    protected void add(@NotNull Block block, @NotNull LootTable.Builder table) {
        //Overwrite the core register method to add to our list of known blocks
        super.add(block, table);
        knownBlocks.add(block);
    }
    private final Set<Block> knownBlocks = new ReferenceOpenHashSet<>();

    @NotNull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
