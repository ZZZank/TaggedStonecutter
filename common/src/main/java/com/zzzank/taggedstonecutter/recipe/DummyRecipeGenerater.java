package com.zzzank.taggedstonecutter.recipe;

import java.util.ArrayList;
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

    protected static List<TagAddingRecipe> recipes = null;
    private static MinecraftServer server = null;

    public static void setServer(MinecraftServer server) {
        DummyRecipeGenerater.server = server;
    }

    public static List<TagAddingRecipe> getAllTagAddingRecipes() {
        if (recipes == null) {
            if (server == null) {
                return new ArrayList<>(0);
            }
            RecipeManager recipeManager = server.getRecipeManager();
            if (recipeManager == null) {
                return new ArrayList<>(0);
            }
            recipes = recipeManager.getAllRecipesFor(TagAddingRecipe.TYPE);
        }
        return recipes;
    }

    public static List<StonecutterRecipe> generateRecipes(ItemStack stack) {
        TagAddingRecipe matched = tryMatch(stack.getItem());
        if (matched == null) {
            return new ArrayList<>(0);
        }
        return toDummyRecipes(Ingredient.of(matched.to));
    }

    private static List<StonecutterRecipe> toDummyRecipes(Ingredient ingr) {
        if (ingr == null) {
            return new ArrayList<>(0);
        }
        final ItemStack[] items = ingr.getItems();
        final List<StonecutterRecipe> recipes = new ArrayList<>(items.length);
        for (int i = 0; i < items.length; i++) {
            recipes.add(new DummyStonecutterRecipe(i, ingr, items[i]));
        }
        return recipes;
    }

    @Nullable
    public static TagAddingRecipe tryMatch(Item item) {
        for (TagAddingRecipe recipe : getAllTagAddingRecipes()) {
            if (recipe.from.contains(item)) {
                return recipe;
            }
        }
        return null;
    }

    public static TagCollection<Item> getItemTags() {
        return SerializationTags.getInstance().getItems();
    }

    public static void clearCache() {
        recipes = null;
    }
}
