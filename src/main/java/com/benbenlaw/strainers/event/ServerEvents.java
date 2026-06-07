package com.benbenlaw.strainers.event;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.event.client.ClientRecipeCache;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import com.benbenlaw.strainers.recipe.StrainersRecipes;
import net.minecraft.DetectedVersion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

import java.util.*;

@EventBusSubscriber(modid = Strainers.MOD_ID)
public class ServerEvents {

    @SubscribeEvent
    public static void onDataPackSync(OnDatapackSyncEvent event) {
        event.sendRecipes(StrainersRecipes.STRAINER_TYPE.get());

    }

    @SubscribeEvent
    public static void onRecipeReceived(RecipesReceivedEvent event) {
        RecipeMap recipeMap = event.getRecipeMap();

        //Strainers
        Collection<RecipeHolder<StrainerRecipe>> strainerRecipes = recipeMap.byType(StrainersRecipes.STRAINER_TYPE.get());
        Map<Identifier, StrainerRecipe> strainerRecipeMap = new HashMap<>();

        for (RecipeHolder<StrainerRecipe> holder : strainerRecipes) {
            strainerRecipeMap.put(holder.id().identifier(), holder.value());
        }
        ClientRecipeCache.setCachedStrainerRecipes(strainerRecipeMap);
    }

    public static final Set<String> AVAILABLE_ORES = new HashSet<>();

    @SubscribeEvent
    public static void onTagsUpdated(TagsUpdatedEvent event) {
        AVAILABLE_ORES.clear();

        var blocks = event.getLookupProvider().lookupOrThrow(Registries.BLOCK);

        blocks.listTagIds().forEach(tag -> {
            Identifier id = tag.location();

            if (id.getNamespace().equals("c")
                    && id.getPath().startsWith("ores/")) {

                String material = id.getPath().substring("ores/".length());

                AVAILABLE_ORES.add(material);
            }
        });

        System.out.println("Available Ores: " + AVAILABLE_ORES);

    }
}
