package com.benbenlaw.strainers.event.client;

import com.benbenlaw.strainers.recipe.StrainerRecipe;
import net.minecraft.resources.Identifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClientRecipeCache {

    //Melting Recipes
    public static Map<Identifier, StrainerRecipe> cachedStrainerRecipes = new HashMap<>();

    public static void setCachedStrainerRecipes(Map<Identifier, StrainerRecipe> cachedStrainerRecipes) {
        ClientRecipeCache.cachedStrainerRecipes = cachedStrainerRecipes;
    }

    public static Collection<StrainerRecipe> getCachedStrainerRecipes() {
        return cachedStrainerRecipes.values();
    }


}
