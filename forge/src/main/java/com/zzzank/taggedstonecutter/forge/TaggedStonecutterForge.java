package com.zzzank.taggedstonecutter.forge;

import com.zzzank.taggedstonecutter.TaggedStonecutter;
import com.zzzank.taggedstonecutter.recipe.DummyRecipeGenerater;
import com.zzzank.taggedstonecutter.recipe.AllIngredientRecipe;
import com.zzzank.taggedstonecutter.recipe.AllIngredientRecipe.TagAddingRecipeType;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(TaggedStonecutter.MOD_ID)
public class TaggedStonecutterForge {

    public TaggedStonecutterForge() {
        ForgeRegistries.RECIPE_SERIALIZERS.register(AllIngredientRecipe.SERIALIZER);
        RecipeType.register(TagAddingRecipeType.ID.toString());

        MinecraftForge.EVENT_BUS.addListener((FMLServerAboutToStartEvent event) -> {
            DummyRecipeGenerater.setServer(event.getServer());
        });
        MinecraftForge.EVENT_BUS.addListener((FMLServerStoppedEvent event) -> {
            DummyRecipeGenerater.setServer(null);
        });
        MinecraftForge.EVENT_BUS.addListener((AddReloadListenerEvent event) -> {
            DummyRecipeGenerater.clearCache();
        });
    }
}
