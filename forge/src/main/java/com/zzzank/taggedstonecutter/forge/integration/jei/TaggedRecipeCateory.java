package com.zzzank.taggedstonecutter.forge.integration.jei;

import com.zzzank.taggedstonecutter.TaggedStonecutter;
import com.zzzank.taggedstonecutter.recipe.TagAddingRecipe;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TaggedRecipeCateory implements IRecipeCategory<TagAddingRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(TaggedStonecutter.MOD_ID, "recipe");
    // TODO translate
    public static final BaseComponent TITLE = new TranslatableComponent("Tagged Stonecutter");
    private final IDrawable icon;
    private final IDrawable background;

    public TaggedRecipeCateory(IJeiHelpers helpers) {
        this.icon =
            helpers
                .getGuiHelper()
                .createDrawableIngredient(
                    Registry.ITEM.get(new ResourceLocation("minecraft", "stonecutter")).getDefaultInstance()
                );
        this.background =
            helpers
                .getGuiHelper()
                .createDrawable(
                    new ResourceLocation(TaggedStonecutter.MOD_ID, "textures/screen/jei_background.png"),
                    0,
                    0,
                    174,
                    72
                );
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<TagAddingRecipe> getRecipeClass() {
        return TagAddingRecipe.class;
    }

    @Override
    public String getTitle() {
        return TITLE.getString();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(@Nonnull TagAddingRecipe recipe, @Nonnull IIngredients ingredients) {
        ingredients.setInputIngredients(Arrays.asList(Ingredient.of(recipe.getFrom())));

        List<ItemStack> outputs = recipe
            .getTo()
            .getValues()
            .stream()
            .map(Item::getDefaultInstance)
            .collect(Collectors.toList());
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void setRecipe(
        @Nonnull IRecipeLayout recipeLayout,
        @Nonnull TagAddingRecipe recipe,
        @Nonnull IIngredients ingredients
    ) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 0, 27);
        guiItemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        for (int i = 0; i < outputs.size(); i++) {
            int x = 48 + 18 * (i % 7);
            int y = 18 * (i / 7);
            guiItemStacks.init(i + 1, false, x, y);
            guiItemStacks.set(i + 1, outputs.get(i));
        }
    }
}
