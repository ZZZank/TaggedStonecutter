package com.zzzank.taggedstonecutter.forge;

import com.zzzank.taggedstonecutter.TaggedStonecutter;
import com.zzzank.taggedstonecutter.recipe.TagAddingRecipe;
import me.shedaniel.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(TaggedStonecutter.MOD_ID)
public class TaggedStonecutterForge {

    public TaggedStonecutterForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(
            TaggedStonecutter.MOD_ID,
            FMLJavaModLoadingContext.get().getModEventBus()
        );
        ForgeRegistries.RECIPE_SERIALIZERS.register(TagAddingRecipe.SERIALIZER);
        // RecipeType
    }
}
