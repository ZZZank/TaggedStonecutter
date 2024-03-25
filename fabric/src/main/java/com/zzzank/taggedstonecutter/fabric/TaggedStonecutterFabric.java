package com.zzzank.taggedstonecutter.fabric;

import com.zzzank.taggedstonecutter.recipe.TagAddingRecipe;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;

public class TaggedStonecutterFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Registry.register(Registry.RECIPE_SERIALIZER, TagAddingRecipe.TYPE_ID, TagAddingRecipe.SERIALIZER);
        Registry.register(Registry.RECIPE_TYPE, TagAddingRecipe.TYPE_ID, TagAddingRecipe.TYPE);
    }
}
