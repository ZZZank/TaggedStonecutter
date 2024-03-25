package com.zzzank.taggedstonecutter.recipe;

import com.zzzank.taggedstonecutter.TaggedStonecutter;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.StonecutterRecipe;

public class DummyStonecutterRecipe extends StonecutterRecipe {

    public DummyStonecutterRecipe(int id, Ingredient ingr, ItemStack stack) {
        super(toDummyId(id), "dummy", ingr, stack);
    }

    /**
     * given that our recipes are injected after slot is changed, and item matching is
     * done by recipe generator, we should be able to use an empty ingredient
     */
    public DummyStonecutterRecipe(int id, ItemStack stack) {
        super(toDummyId(id), "dummy", null, stack);
    }

    private static ResourceLocation toDummyId(int idSuffix){
        return new ResourceLocation(TaggedStonecutter.MOD_ID, String.valueOf(idSuffix));
    }

    /**
     * always true to hide this dummy recipe in RecipeBook
     */
    @Override
    public boolean isSpecial(){
        return true;
    }
}