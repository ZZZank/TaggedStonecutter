package com.zzzank.taggedstonecutter.forge.integration.jei;

import com.zzzank.taggedstonecutter.TaggedStonecutter;
import com.zzzank.taggedstonecutter.recipe.TagAddingRecipe;
import java.util.Arrays;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public class TaggedRecipeCateory implements IRecipeCategory<TagAddingRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(TaggedStonecutter.MOD_ID, "recipe");
    // TODO translate
    public static final TranslatableComponent TITLE = new TranslatableComponent("Tagged Stonecutter");
    private final IDrawable icon;
    private final IDrawable background;

    private static final int slotCountX = 7;
    private static final int slotCountY = 4;

    public TaggedRecipeCateory(IJeiHelpers helpers) {
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
        //input
        guiItemStacks.init(0, true, 0, 27);
        guiItemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        //output preprocessing to prevent overflow
        final List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        int size = outputs.size();
        final int slotCountTotal = slotCountX * slotCountY;
        if (size > slotCountTotal) {
            for (int i = slotCountTotal; i < size; i++) {
                outputs.get(i % slotCountTotal).addAll(outputs.get(i));
            }
            size = slotCountTotal;
        }
        //output
        for (int i = 0; i < size; i++) {
            int x = 48 + 18 * (i % slotCountX);
            int y = 18 * (i / slotCountX);
            guiItemStacks.init(i + 1, false, x, y);
            guiItemStacks.set(i + 1, outputs.get(i));
        }
    }
}
