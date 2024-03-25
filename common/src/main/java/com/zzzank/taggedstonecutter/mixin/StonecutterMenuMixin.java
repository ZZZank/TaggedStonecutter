package com.zzzank.taggedstonecutter.mixin;

import com.zzzank.taggedstonecutter.recipe.RecipeGenerater;
import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StonecutterMenu.class)
public abstract class StonecutterMenuMixin {

    @Shadow
    private List<StonecutterRecipe> recipes;

    @Shadow
    @Final
    private DataSlot selectedRecipeIndex;

    @Shadow
    @Final
    Slot resultSlot;

    @Inject(method = "setupRecipeList", at = @At("HEAD"), cancellable = true)
    public void onSetupRecipeList(Container container, ItemStack stack, CallbackInfo ci) {
        if (stack.isEmpty()) {
            return;
        }
        final List<StonecutterRecipe> dummyRecipes = RecipeGenerater.generateRecipes(stack);
        if (dummyRecipes.isEmpty()) {
            return;
        }
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        this.recipes = dummyRecipes;
        ci.cancel();
    }
}
