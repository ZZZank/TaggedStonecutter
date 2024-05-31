package com.zzzank.taggedstonecutter.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zzzank.taggedstonecutter.TaggedStonecutter;
import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * recipe for stone cutter, but
 */
public class AllIngredientRecipe implements Recipe<Container> {

    public static final RecipeSerializer<AllIngredientRecipe> SERIALIZER = new TagAddingRecipeSerializer();
    public static final RecipeType<AllIngredientRecipe> TYPE = new TagAddingRecipeType();

    private final ResourceLocation id;
    private final Ingredient from;
    private final Ingredient to;

    public AllIngredientRecipe(Ingredient fromAndTo, ResourceLocation id) {
        this(fromAndTo, fromAndTo, id);
    }

    public AllIngredientRecipe(Ingredient from, Ingredient to, ResourceLocation id) {
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public Ingredient getFrom() {
        return from;
    }

    public Ingredient getTo() {
        return to;
    }

    @Deprecated
    @Override
    public ItemStack assemble(Container container) {
        return ItemStack.EMPTY;
    }

    @Deprecated
    @Override
    public boolean canCraftInDimensions(int arg0, int arg1) {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Deprecated
    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AllIngredientRecipe.SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return AllIngredientRecipe.TYPE;
    }

    @Deprecated
    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    public static final class TagAddingRecipeSerializer implements RecipeSerializer<AllIngredientRecipe> {

        private TagAddingRecipeSerializer() {}

        public static ResourceLocation registryName = TagAddingRecipeType.ID;

        @Override
        public AllIngredientRecipe fromJson(ResourceLocation id, JsonObject o) {
            Ingredient from = Ingredient.fromJson(o.get("from"));
            JsonElement to = o.get("to");
            if (to == null) {
                return new AllIngredientRecipe(from, id);
            }
            return new AllIngredientRecipe(from, Ingredient.fromJson(to), id);
        }

        @Override
        public AllIngredientRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            return new AllIngredientRecipe(Ingredient.fromNetwork(buffer), Ingredient.fromNetwork(buffer), id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AllIngredientRecipe recipe) {
            recipe.from.toNetwork(buffer);
            recipe.to.toNetwork(buffer);
        }

        /**forge only */
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            TagAddingRecipeSerializer.registryName = name;
            return this;
        }

        /**forge only */
        @Nullable
        public ResourceLocation getRegistryName() {
            return TagAddingRecipeSerializer.registryName;
        }

        /**forge only */
        public Class<?> getRegistryType() {
            return AllIngredientRecipe.class;
        }
    }

    public static final class TagAddingRecipeType implements RecipeType<AllIngredientRecipe> {

        public static final ResourceLocation ID = new ResourceLocation(TaggedStonecutter.MOD_ID, "recipe");

        private TagAddingRecipeType() {}

        @Override
        @Deprecated
        public <C extends Container> Optional<AllIngredientRecipe> tryMatch(
            Recipe<C> recipe,
            Level level,
            C container
        ) {
            return Optional.empty();
        }
    }
}
