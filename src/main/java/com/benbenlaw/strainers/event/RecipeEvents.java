package com.benbenlaw.strainers.event;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.event.client.ClientRecipeCache;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import com.benbenlaw.strainers.recipe.StrainersRecipes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = Strainers.MOD_ID)
public class RecipeEvents {

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
}
