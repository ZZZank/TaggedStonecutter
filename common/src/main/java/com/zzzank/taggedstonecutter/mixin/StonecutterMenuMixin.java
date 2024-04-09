package com.zzzank.taggedstonecutter.mixin;

import com.zzzank.taggedstonecutter.recipe.DummyRecipeGenerater;
import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StonecutterMenu.class)
public abstract class StonecutterMenuMixin extends AbstractContainerMenu {

    private StonecutterMenuMixin(MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

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
        final List<StonecutterRecipe> dummyRecipes = DummyRecipeGenerater.generateRecipes(stack);
        if (dummyRecipes.isEmpty()) {
            return;
        }
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        this.recipes = dummyRecipes;
        ci.cancel();
    }

    /**
     * @see net.minecraft.world.inventory.StonecutterMenu#quickMoveStack(Player, int)
     */
    @Inject(
        method = "quickMoveStack",
        at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z"),
        cancellable = true
    )
    public void onQuickMoveStack(Player player, int index, CallbackInfoReturnable<ItemStack> ci) {
        if (index == 0 || index == 1) {
            return;
        }
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasItem()) {
            return;
        }
        if (
            DummyRecipeGenerater.tryMatch(slot.getItem().getItem()) != null &&
            !this.moveItemStackTo(slot.getItem(), 0, 1, false)
        ) {
            ci.setReturnValue(ItemStack.EMPTY);
        }
    }
}
