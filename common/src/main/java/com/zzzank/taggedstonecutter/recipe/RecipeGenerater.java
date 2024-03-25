package com.zzzank.taggedstonecutter.recipe;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import org.jetbrains.annotations.Nullable;

public abstract class RecipeGenerater {

    //TODO: cache?

    static final List<TagAddingRecipe> RECIPES = new ArrayList<>();

    public static List<StonecutterRecipe> generateRecipes(ItemStack stack) {
        TagAddingRecipe matched = tryMatch(stack.getItem());
        List<StonecutterRecipe> dummyRecipes = matched == null
            ? new ArrayList<>()
            : toDummyRecipes(Ingredient.of(matched.to));
        return dummyRecipes;
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
        for (TagAddingRecipe recipe : RECIPES) {
            if (recipe.from.contains(item)) {
                return recipe;
            }
        }
        return null;
    }

    public static TagCollection<Item> getItemTags() {
        return SerializationTags.getInstance().getItems();
    }
}
