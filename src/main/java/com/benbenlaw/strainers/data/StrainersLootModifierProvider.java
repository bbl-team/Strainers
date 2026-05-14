package com.benbenlaw.strainers.data;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.item.StrainersItems;
import com.benbenlaw.strainers.loot.AddItemModifier;
import com.benbenlaw.strainers.loot.IsLeavesCondition;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class StrainersLootModifierProvider extends GlobalLootModifierProvider {

    public StrainersLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, Strainers.MOD_ID);
    }

    @Override
    protected void start() {

        add("leaf_pile_from_leaves", new AddItemModifier(
                new LootItemCondition[] {
                        IsLeavesCondition.INSTANCE,
                        MatchTool.toolMatches(
                                ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), Items.STICK)).build()
                }, 1001, StrainersItems.LEAF_PILE.get()));

    }
}

