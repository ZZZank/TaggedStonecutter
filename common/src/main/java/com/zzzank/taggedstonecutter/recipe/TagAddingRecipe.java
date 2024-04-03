package com.zzzank.taggedstonecutter.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zzzank.taggedstonecutter.TaggedStonecutter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class TagAddingRecipe implements Recipe<Container> {

    public static final RecipeSerializer<TagAddingRecipe> SERIALIZER = new TagAddingRecipeSerializer();
    public static final RecipeType<TagAddingRecipe> TYPE = new TagAddingRecipeType();

    final ResourceLocation id;
    final Tag<Item> from;
    final Tag<Item> to;
    final ResourceLocation fromName;
    final ResourceLocation toName;

    public TagAddingRecipe(ResourceLocation from, ResourceLocation id) {
        this(from, from, id);
    }

    public TagAddingRecipe(ResourceLocation from, ResourceLocation to, ResourceLocation id) {
        this.id = id;
        this.fromName = from;
        this.toName = to;
        TagCollection<Item> itemTags = DummyRecipeGenerater.getItemTags();
        this.from = itemTags.getTag(this.fromName);
        this.to = itemTags.getTag(this.toName);
    }

    public Tag<Item> getFrom() {
        return from;
    }

    public Tag<Item> getTo() {
        return to;
    }

    @Deprecated
    @Override
    public ItemStack assemble(Container container) {
        return ItemStack.EMPTY;
    }

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
        return TagAddingRecipe.SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return TagAddingRecipe.TYPE;
    }

    @Deprecated
    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    public static final class TagAddingRecipeSerializer implements RecipeSerializer<TagAddingRecipe> {

        private TagAddingRecipeSerializer() {}

        public static ResourceLocation registryName = TagAddingRecipeType.ID;

        @Override
        public TagAddingRecipe fromJson(ResourceLocation id, JsonObject o) {
            String from = o.get("from").getAsString();
            JsonElement to = o.get("to");
            if (to == null) {
                return new TagAddingRecipe(new ResourceLocation(from), id);
            }
            return new TagAddingRecipe(
                new ResourceLocation(from),
                new ResourceLocation(to.getAsString()),
                id
            );
        }

        @Override
        public TagAddingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            ResourceLocation fromName = buffer.readResourceLocation();
            ResourceLocation toName = buffer.readResourceLocation();
            return new TagAddingRecipe(fromName, toName, id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TagAddingRecipe recipe) {
            buffer.writeResourceLocation(recipe.fromName);
            buffer.writeResourceLocation(recipe.toName);
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
            return TagAddingRecipe.class;
        }
    }

    public static final class TagAddingRecipeType implements RecipeType<TagAddingRecipe> {

        public static final ResourceLocation ID = new ResourceLocation(TaggedStonecutter.MOD_ID, "add");

        private TagAddingRecipeType() {}
    }
}
