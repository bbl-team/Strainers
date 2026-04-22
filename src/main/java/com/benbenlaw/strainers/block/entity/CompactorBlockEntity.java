package com.benbenlaw.strainers.block.entity;

import com.benbenlaw.core.block.entity.SyncableBlockEntity;
import com.benbenlaw.core.block.entity.handler.IInventoryHandlingBlockEntity;
import com.benbenlaw.core.block.entity.handler.InputOutputItemHandler;
import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.strainers.item.custom.UpgradeItem;
import com.benbenlaw.strainers.recipe.MeshChanceResult;
import com.benbenlaw.strainers.recipe.ModRecipes;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import com.benbenlaw.strainers.recipe.StrainerRecipeInput;
import com.benbenlaw.strainers.screen.custom.CompactorMenu;
import com.benbenlaw.strainers.screen.custom.WoodenStrainerMenu;
import com.benbenlaw.strainers.util.StrainersIngredientDurations;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CompactorBlockEntity extends SyncableBlockEntity implements MenuProvider, IInventoryHandlingBlockEntity {

    private final ItemStackHandler itemHandler = new ItemStackHandler(18) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            sync();
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (stack.isEmpty()) return ItemStack.EMPTY;

            if (isInputSlot(slot, stack)) {
                return insertIntoInputs(stack, simulate);
            }

            if (isOutputSlot(slot)) {
                return insertIntoOutputs(stack, simulate);
            }

            return super.insertItem(slot, stack, simulate);
        }
    };

    public final ContainerData data;
    public static final int[] INPUT_SLOTS = {0,1,2,3,4,5,6,7,8};
    public static final int[] OUTPUT_SLOTS = {9,10,11,12,13,14,15,16,17};

    private final IItemHandler compactorItemHandler =
            new InputOutputItemHandler(itemHandler, this::isInputSlot, this::isOutputSlot);

    // 🔥 CACHE
    private final List<CachedRecipe> cachedRecipes = new ArrayList<>();
    private boolean recipesCached = false;

    public CompactorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COMPACTOR_BLOCK_ENTITY.get(), pos, state);
        this.data = new SimpleContainerData(2);
    }

    private boolean isInputSlot(int slot, ItemStack stack) {
        for (int s : INPUT_SLOTS) if (s == slot) return true;
        return false;
    }

    private boolean isOutputSlot(int slot) {
        for (int s : OUTPUT_SLOTS) if (s == slot) return true;
        return false;
    }

    public IItemHandler getItemHandlerCapability(Direction side) {
        return side == null ? itemHandler : compactorItemHandler;
    }

    public void setHandler(ItemStackHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            this.itemHandler.setStackInSlot(i, handler.getStackInSlot(i));
        }
    }

    public ItemStackHandler getItemStackHandler() {
        return this.itemHandler;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        cacheRecipes();
        this.setChanged();
    }

    private void cacheRecipes() {
        if (recipesCached || level == null) return;

        cachedRecipes.clear();

        for (RecipeHolder<CraftingRecipe> holder :
                level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING)) {

            if (!(holder.value() instanceof ShapedRecipe shaped)) continue;

            int size = shaped.getWidth();
            if (size != shaped.getHeight()) continue;
            if (size != 2 && size != 3) continue;

            Ingredient first = shaped.getIngredients().get(0);

            boolean allSame = true;
            for (Ingredient ing : shaped.getIngredients()) {
                if (ing != first) {
                    allSame = false;
                    break;
                }
            }

            if (!allSame) continue;

            cachedRecipes.add(new CachedRecipe(
                    first,
                    size,
                    shaped.getResultItem(level.registryAccess()).copy()
            ));
        }

        recipesCached = true;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.strainers.compactor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int container, @NotNull Inventory inventory, @NotNull Player player) {
        return new CompactorMenu(container, inventory, this.getBlockPos(), data);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(tag, provider);
        tag.put("inventory", this.itemHandler.serializeNBT(provider));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        this.itemHandler.deserializeNBT(provider, tag.getCompound("inventory"));
        super.loadAdditional(tag, provider);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert level != null;
        Containers.dropContents(level, worldPosition, inv);
    }

    public void tick() {
        if (level == null || level.isClientSide()) return;
        if (level.getGameTime() % 20 != 0) return;

        if (!recipesCached) cacheRecipes();

        for (int slot : INPUT_SLOTS) {
            ItemStack stack = itemHandler.getStackInSlot(slot);
            if (stack.isEmpty() || stack.getCount() < 4) continue;

            for (CachedRecipe recipe : cachedRecipes) {
                int needed = recipe.size * recipe.size;

                if (stack.getCount() < needed) continue;
                if (!recipe.ingredient.test(stack)) continue;

                ItemStack result = recipe.result.copy();

                if (insertResult(result)) {
                    stack.shrink(needed);
                    itemHandler.setStackInSlot(slot, stack);
                    setChanged();
                    sync();
                    return;
                }
            }
        }
    }

    private boolean insertResult(ItemStack result) {
        for (int slot : OUTPUT_SLOTS) {
            ItemStack out = itemHandler.getStackInSlot(slot);

            if (!out.isEmpty() &&
                    ItemStack.isSameItemSameComponents(out, result) &&
                    out.getCount() < out.getMaxStackSize()) {

                int transfer = Math.min(result.getCount(),
                        out.getMaxStackSize() - out.getCount());

                out.grow(transfer);
                result.shrink(transfer);
                itemHandler.setStackInSlot(slot, out);

                if (result.isEmpty()) return true;
            }
        }

        for (int slot : OUTPUT_SLOTS) {
            if (itemHandler.getStackInSlot(slot).isEmpty()) {
                itemHandler.setStackInSlot(slot, result.copy());
                return true;
            }
        }

        return false;
    }

    private ItemStack insertIntoInputs(ItemStack stack, boolean simulate) {
        ItemStack remaining = stack.copy();

        for (int slot : INPUT_SLOTS) {
            ItemStack existing = itemHandler.getStackInSlot(slot);

            if (!existing.isEmpty() &&
                    ItemStack.isSameItemSameComponents(existing, remaining)) {

                int transfer = Math.min(remaining.getCount(),
                        existing.getMaxStackSize() - existing.getCount());

                if (transfer > 0) {
                    if (!simulate) {
                        existing.grow(transfer);
                        itemHandler.setStackInSlot(slot, existing);
                    }
                    remaining.shrink(transfer);
                    if (remaining.isEmpty()) return ItemStack.EMPTY;
                }
            }
        }

        for (int slot : INPUT_SLOTS) {
            if (itemHandler.getStackInSlot(slot).isEmpty()) {
                if (!simulate) itemHandler.setStackInSlot(slot, remaining.copy());
                return ItemStack.EMPTY;
            }
        }

        return remaining;
    }

    private ItemStack insertIntoOutputs(ItemStack stack, boolean simulate) {
        ItemStack remaining = stack.copy();

        for (int slot : OUTPUT_SLOTS) {
            ItemStack existing = itemHandler.getStackInSlot(slot);

            if (!existing.isEmpty() &&
                    ItemStack.isSameItemSameComponents(existing, remaining)) {

                int transfer = Math.min(remaining.getCount(),
                        existing.getMaxStackSize() - existing.getCount());

                if (transfer > 0) {
                    if (!simulate) {
                        existing.grow(transfer);
                        itemHandler.setStackInSlot(slot, existing);
                    }
                    remaining.shrink(transfer);
                    if (remaining.isEmpty()) return ItemStack.EMPTY;
                }
            }
        }

        for (int slot : OUTPUT_SLOTS) {
            if (itemHandler.getStackInSlot(slot).isEmpty()) {
                if (!simulate) itemHandler.setStackInSlot(slot, remaining.copy());
                return ItemStack.EMPTY;
            }
        }

        return remaining;
    }

    private record CachedRecipe(Ingredient ingredient, int size, ItemStack result) {}
}
