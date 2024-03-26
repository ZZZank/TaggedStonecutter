package com.zzzank.taggedstonecutter.fabric;

import com.zzzank.taggedstonecutter.recipe.DummyRecipeGenerater;
import com.zzzank.taggedstonecutter.recipe.TagAddingRecipe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.Registry;

public class TaggedStonecutterFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Registry.register(Registry.RECIPE_SERIALIZER, TagAddingRecipe.TYPE_ID, TagAddingRecipe.SERIALIZER);
        Registry.register(Registry.RECIPE_TYPE, TagAddingRecipe.TYPE_ID, TagAddingRecipe.TYPE);
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            DummyRecipeGenerater.setServer(server);
        });
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, res) -> {
            DummyRecipeGenerater.clearCache();
        });
    }
}
