package com.zzzank.taggedstonecutter.forge.integration.jei;

import com.zzzank.taggedstonecutter.TaggedStonecutter;
import com.zzzank.taggedstonecutter.recipe.DummyRecipeGenerater;
import javax.annotation.Nonnull;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JPlugin implements IModPlugin {

    public JPlugin() {}

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(TaggedStonecutter.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(@Nonnull IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new TaggedRecipeCateory(registration.getJeiHelpers()));
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        registration.addRecipes(DummyRecipeGenerater.getAllTagAddingRecipes(), TaggedRecipeCateory.UID);
    }

    /**
     * Register recipe transfer handlers (move ingredients from the inventory into crafting GUIs).
     */
    @Override
    public void registerRecipeTransferHandlers(@Nonnull IRecipeTransferRegistration registration) {}

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
            Registry.ITEM.get(new ResourceLocation("minecraft", "stonecutter")).getDefaultInstance(),
            TaggedRecipeCateory.UID
        );
    }
}
