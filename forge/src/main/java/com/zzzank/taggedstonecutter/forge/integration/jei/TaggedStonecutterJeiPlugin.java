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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

@JeiPlugin
public class TaggedStonecutterJeiPlugin implements IModPlugin {

    private static final ResourceLocation uid = new ResourceLocation(TaggedStonecutter.MOD_ID, "jei_plugin");

    public TaggedStonecutterJeiPlugin() {}

    @Override
    public ResourceLocation getPluginUid() {
        return uid;
    }

    @Override
    public void registerCategories(@Nonnull IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new TaggedStonecutterRecipeCategory(registration.getJeiHelpers()));
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        registration.addRecipes(DummyRecipeGenerater.getAllTagAddingRecipes(), TaggedStonecutterRecipeCategory.UID);
    }

    /**
     * Register recipe transfer handlers (move ingredients from the inventory into crafting GUIs).
     */
    @Override
    public void registerRecipeTransferHandlers(@Nonnull IRecipeTransferRegistration registration) {}

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Blocks.STONECUTTER), TaggedStonecutterRecipeCategory.UID);
    }
}
