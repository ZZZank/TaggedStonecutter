package com.zzzank.taggedstonecutter.fabric;

import com.zzzank.taggedstonecutter.recipe.DummyRecipeGenerater;
import com.zzzank.taggedstonecutter.recipe.AllIngredientRecipe;
import com.zzzank.taggedstonecutter.recipe.AllIngredientRecipe.TagAddingRecipeType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.Registry;

public class TaggedStonecutterFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Registry.register(Registry.RECIPE_SERIALIZER, TagAddingRecipeType.ID, AllIngredientRecipe.SERIALIZER);
        Registry.register(Registry.RECIPE_TYPE, TagAddingRecipeType.ID, AllIngredientRecipe.TYPE);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            DummyRecipeGenerater.setServer(server);
        });
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            DummyRecipeGenerater.setServer(null);
        });
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, res) -> {
            DummyRecipeGenerater.clearCache();
        });
    }
}
