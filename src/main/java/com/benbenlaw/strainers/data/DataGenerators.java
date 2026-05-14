package com.benbenlaw.strainers.data;


import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.recipe.StrainersRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Strainers.MOD_ID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new StrainersBlockTags(packOutput, lookupProvider));
        generator.addProvider(true, new StrainersItemTags(packOutput, lookupProvider));
        generator.addProvider(true, new StrainersLangProvider(packOutput));
        generator.addProvider(true, new StrainersDataMapsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(StrainersLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
        generator.addProvider(true, new StrainersModelProvider(packOutput));
        generator.addProvider(true, new StrainersLootModifierProvider(packOutput, lookupProvider));

        //Recipes
        generator.addProvider(true, new StrainersRecipeProvider.Runner(packOutput, lookupProvider));
        generator.addProvider(true, new StrainersProcessingRecipeProvider.Runner(packOutput, lookupProvider));


    }
}
