package com.zzzank.taggedstonecutter.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import org.jetbrains.annotations.Nullable;

public abstract class DummyRecipeGenerater {

    //TODO: cache?

    private static List<AllIngredientRecipe> recipes = null;

    @Nullable
    private static MinecraftServer server = null;

    public static void setServer(@Nullable MinecraftServer server) {
        DummyRecipeGenerater.server = server;
    }

    public static List<AllIngredientRecipe> getAllTagAddingRecipes() {
        if (recipes == null) {
            if (server == null) {
                return Collections.emptyList();
            }
            RecipeManager recipeManager = server.getRecipeManager();
            if (recipeManager == null) {
                return Collections.emptyList();
            }
            recipes = recipeManager.getAllRecipesFor(AllIngredientRecipe.TYPE);
        }
        return recipes;
    }

    public static List<StonecutterRecipe> generateRecipes(ItemStack stack) {
        if (stack.isEmpty()) {
            return Collections.emptyList();
        }
        AllIngredientRecipe matched = tryMatch(stack);
        if (matched == null) {
            return Collections.emptyList();
        }
        return toDummyRecipes(matched.getTo());
    }

    private static List<StonecutterRecipe> toDummyRecipes(@Nullable Ingredient ingr) {
        if (ingr == null) {
            return Collections.emptyList();
        }
        final ItemStack[] items = ingr.getItems();
        final List<StonecutterRecipe> recipes = new ArrayList<>(items.length);
        for (int i = 0; i < items.length; i++) {
            recipes.add(new DummyRecipe(i, ingr, items[i]));
        }
        return recipes;
    }

    @Nullable
    public static AllIngredientRecipe tryMatch(ItemStack item) {
        for (AllIngredientRecipe recipe : getAllTagAddingRecipes()) {
            if (recipe.getFrom().test(item)) {
                return recipe;
            }
        }
        return null;
    }

    public static TagCollection<Item> getItemTags() {
        return SerializationTags.getInstance().getItems();
    }

    public static void clearCache() {
        DummyRecipeGenerater.recipes = null;
    }
}
