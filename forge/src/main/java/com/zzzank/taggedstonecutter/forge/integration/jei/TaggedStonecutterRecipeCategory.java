package com.zzzank.taggedstonecutter.forge.integration.jei;

import com.zzzank.taggedstonecutter.TaggedStonecutter;
import com.zzzank.taggedstonecutter.recipe.AllIngredientRecipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class TaggedStonecutterRecipeCategory implements IRecipeCategory<AllIngredientRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(TaggedStonecutter.MOD_ID, "recipe");
    // TODO translate
    public static final TranslatableComponent TITLE = new TranslatableComponent("Tagged Stonecutter");
    private final IDrawable icon;
    private final IDrawable background;

    private static final int slotCountX = 7;
    private static final int slotCountY = 4;

    public TaggedStonecutterRecipeCategory(IJeiHelpers helpers) {
        this.icon = helpers.getGuiHelper().createDrawableIngredient(new ItemStack(Blocks.STONECUTTER));
        this.background =
            helpers
                .getGuiHelper()
                .createDrawable(
                    new ResourceLocation(TaggedStonecutter.MOD_ID, "textures/screen/jei_background.png"),
                    0,
                    0,
                    48 + 18 * slotCountX,
                    18 * slotCountY
                );
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<AllIngredientRecipe> getRecipeClass() {
        return AllIngredientRecipe.class;
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
    public void setIngredients(@Nonnull AllIngredientRecipe recipe, @Nonnull IIngredients ingredients) {
        ingredients.setInputIngredients(Collections.singletonList(recipe.getFrom()));

        ItemStack[] outputsRaw = recipe.getTo().getItems();
        final int slotCountTotal = slotCountX * slotCountY;
        if (outputsRaw.length < slotCountTotal) {
            ingredients.setOutputs(VanillaTypes.ITEM, Arrays.asList(outputsRaw));
            return;
        }
        //manually prevent output overflow
        final List<List<ItemStack>> compacted = new ArrayList<>(slotCountTotal);
        final int repeatCount = (outputsRaw.length / slotCountTotal) + 1;
        for (int i = 0; i < slotCountTotal; i++) {
            compacted.add(new ArrayList<>(repeatCount));
        }
        for (int i = 0; i < outputsRaw.length; i++) {
            compacted.get(i % slotCountTotal).add(outputsRaw[i]);
        }
        ingredients.setOutputLists(VanillaTypes.ITEM, compacted);
    }

    @Override
    public void setRecipe(
        @Nonnull IRecipeLayout recipeLayout,
        @Nonnull AllIngredientRecipe recipe,
        @Nonnull IIngredients ingredients
    ) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        //input
        guiItemStacks.init(0, true, 0, 27);
        guiItemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        //output
        final List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        final int size = outputs.size();
        //output
        for (int i = 0; i < size; i++) {
            int x = 48 + 18 * (i % slotCountX);
            int y = 18 * (i / slotCountX);
            guiItemStacks.init(i + 1, false, x, y);
            guiItemStacks.set(i + 1, outputs.get(i));
        }
    }
}
