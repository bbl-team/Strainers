package com.benbenlaw.strainers.block.entity;

import com.benbenlaw.core.block.entity.SyncableBlockEntity;
import com.benbenlaw.core.block.entity.handler.fluid.SyncableFluidHandler;
import com.benbenlaw.core.block.entity.handler.item.SyncableItemHandler;
import com.benbenlaw.core.util.FakePlayerUtil;
import com.benbenlaw.strainers.block.StrainersBlockEntities;
import com.benbenlaw.strainers.block.custom.StrainerBlock;
import com.benbenlaw.strainers.config.StrainersConfig;
import com.benbenlaw.strainers.item.StrainersDataComponents;
import com.benbenlaw.strainers.item.util.FluidListComponent;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import com.benbenlaw.strainers.recipe.StrainerRecipeInput;
import com.benbenlaw.strainers.screen.custom.StrainerMenu;
import com.benbenlaw.strainers.util.StrainersTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class StrainerBlockEntity extends SyncableBlockEntity implements MenuProvider {

    private final ContainerData data;
    private int maxProgress = 200;
    private int progress = 0;
    private int requiredFluidAmount;
    private FakePlayer fakePlayer;

    public static final int INPUT_SLOT = 0;
    public static final int MESH_SLOT = 1;
    public static final int FIRST_OUTPUT_SLOT = 2;
    public static final int LAST_OUTPUT_SLOT = 51;

    private final SyncableItemHandler inventory = new SyncableItemHandler(this, 52,
            (slot, stack) -> {
                boolean isMesh = stack.is(StrainersTags.Items.MESHES);

                if (slot == 0) {
                    return !isMesh;
                }

                if (slot == 1) {
                    return isMesh;
                }

                return false;
            }, i -> i >= 2) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            updateCachedRecipes();
            recheckFluidRequirement();
            super.onContentsChanged(index, previousContents);
        }
    };

    private final SyncableFluidHandler fluidInventory = new SyncableFluidHandler(this, 1, 4000, (i, stack) -> i == 0, i -> true) {

        @Override
        protected void onContentsChanged(int index, FluidStack previousContents) {
            recheckFluidRequirement();
            super.onContentsChanged(index, previousContents);
        }
    };

    private List<RecipeHolder<StrainerRecipe>> cachedRecipes = List.of();
    private int cachedFluidCost = 0;
    private boolean cachedHasEnoughFluid = true;

    public StrainerBlockEntity(BlockPos pos, BlockState state) {
        super(StrainersBlockEntities.STRAINER_BLOCK_ENTITY.get(), pos, state);

        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    case 2 -> requiredFluidAmount;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> progress = value;
                    case 1 -> maxProgress = value;
                    case 2 -> requiredFluidAmount = value;
                }
            }

            public int getCount() {
                return 3;
            }
        };
    }

    public void tick() {
        if (level == null || level.isClientSide()) return;

        if (!level.getBlockState(worldPosition).getValue(StrainerBlock.RUNNING)) {
            progress = 0;
            sync();
            return;
        }

        if (fakePlayer == null) {
            fakePlayer = FakePlayerUtil.createFakePlayer((ServerLevel) level, "StrainerBlockEntityFakePlayer");
        }
        ItemStack input = ItemUtil.getStack(inventory, INPUT_SLOT);
        ItemStack mesh = ItemUtil.getStack(inventory, MESH_SLOT);


        if (input.isEmpty() || mesh.isEmpty()) {
            progress = 0;
            cachedRecipes = List.of();
            sync();
            return;
        }

        int meshTier = getMeshTier(mesh);
        int maxProgress = getMaxProgress(mesh);
        this.maxProgress = maxProgress;

        if (meshTier <= 0) {
            progress = 0;
            cachedRecipes = List.of();
            sync();
            return;
        }

        if (cachedRecipes.isEmpty()) {
            updateCachedRecipes();
        }

        List<RecipeHolder<StrainerRecipe>> validRecipes = cachedRecipes.stream()
                .filter(holder -> meshTier >= holder.value().minMeshTier())
                .toList();

        if (validRecipes.isEmpty()) {
            progress = 0;
            sync();
            return;
        }

        if (!cachedHasEnoughFluid) {
            sync();
            return;
        }

        if (progress >= maxProgress) {

            List<ItemStack> outputs = rollOutputs(meshTier, mesh, validRecipes);

            if (!canInsertOutputs(outputs)) {
                return;
            }

            craftItem(meshTier, validRecipes, outputs, cachedFluidCost);

        } else {
            progress++;
        }
    }

    private List<ItemStack> rollOutputs(int meshTier, ItemStack mesh, List<RecipeHolder<StrainerRecipe>> recipes) {
        assert level != null;
        RandomSource random = level.getRandom();
        List<ItemStack> outputs = new ArrayList<>();
        int fortune = getFortuneLevel(mesh);

        for (RecipeHolder<StrainerRecipe> holder : recipes) {
            ItemStack rolled = holder.value().rollWithTier(random, meshTier, fortune);
            if (!rolled.isEmpty() && !isRemovedRecipeOutput(rolled)) {
                outputs.add(rolled);
            }
        }

        return outputs;
    }

    private int getTotalFluidAmount(List<RecipeHolder<StrainerRecipe>> recipes) {
        int total = 0;
        for (RecipeHolder<StrainerRecipe> holder : recipes) {

            if (holder.value().fluid().isEmpty()) continue;
            SizedFluidIngredient fluidIngredient = holder.value().fluid().get();
            total += fluidIngredient.amount();
        }
        requiredFluidAmount = total;
        return total;
    }

    private void recheckFluidRequirement() {
        cachedFluidCost = getTotalFluidAmount(cachedRecipes);
        requiredFluidAmount = cachedFluidCost;
        cachedHasEnoughFluid = cachedFluidCost <= 0 || fluidInventory.getAmountAsInt(0) >= cachedFluidCost;
        sync();
    }

    private int getFortuneLevel(ItemStack stack) {
        assert level != null;
        return EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(Enchantments.FORTUNE), stack);
    }

    private int getEfficiencyLevel(ItemStack stack) {
        assert level != null;
        return EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(Enchantments.EFFICIENCY), stack);
    }

    private int getMaxProgress(ItemStack mesh) {
        int base = 200;
        int efficiency = getEfficiencyLevel(mesh);

        int reduced = base - (efficiency * 20);

        return Math.max(5, reduced);
    }

    private void updateCachedRecipes() {
        if (level == null || level.getServer() == null) return;

        var input = new StrainerRecipeInput(inventory, fluidInventory);

        cachedRecipes = level.getServer().getRecipeManager()
                .recipeMap().getRecipesFor(StrainerRecipe.TYPE, input, level)
                .filter(holder -> holder.value().matches(input, level))
                .toList();
    }

    private void craftItem(int meshTier, List<RecipeHolder<StrainerRecipe>> validRecipes, List<ItemStack> outputs, int fluidCost) {

        if (level == null || validRecipes.isEmpty()) {
            return;
        }

        inventory.runInternal(() -> {

            try (Transaction tx = Transaction.open(null)) {

                for (ItemStack stack : outputs) {
                    int remaining = stack.getCount();

                    for (int slot = FIRST_OUTPUT_SLOT;
                         slot <= LAST_OUTPUT_SLOT && remaining > 0;
                         slot++) {

                        remaining -= inventory.insert(slot, ItemResource.of(stack), remaining, tx);
                    }

                    if (remaining > 0) {
                        return;
                    }
                }

                int cost = validRecipes.getFirst().value().input().count();
                inventory.extract(INPUT_SLOT, inventory.getResource(INPUT_SLOT), cost, tx);
                ItemStack mesh = inventory.getResource(MESH_SLOT).toStack();

                if (mesh.isDamageableItem()) {

                    boolean ejectAfterHurt = mesh.isEnchanted() && (mesh.getMaxDamage() - mesh.getDamageValue()) == 2;

                    mesh.hurtAndConvertOnBreak(1, Items.AIR, fakePlayer, fakePlayer.getEquipmentSlotForItem(mesh));

                    if (ejectAfterHurt && !mesh.isEmpty()) {

                        inventory.set(MESH_SLOT, ItemResource.EMPTY, 0);

                        int remaining = mesh.getCount();
                        for (int slot = FIRST_OUTPUT_SLOT;
                             slot <= LAST_OUTPUT_SLOT && remaining > 0;
                             slot++) {

                            remaining -= inventory.insert(slot, ItemResource.of(mesh), remaining, tx);
                        }

                        if (remaining > 0) {
                            inventory.set(MESH_SLOT, ItemResource.of(mesh), remaining);
                        }

                    } else {
                        inventory.set(MESH_SLOT, ItemResource.of(mesh), mesh.getCount());
                        if (mesh.isEmpty()) {
                            level.playSound(null, worldPosition, SoundEvents.ITEM_BREAK.value(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }
                }

                tx.commit();
            }
        });

        if (StrainersConfig.STRAINERS_CONSUME_FLUID.get()) {
            fluidInventory.runInternal(() -> {
                try (Transaction tx = Transaction.open(null)) {
                    if (fluidCost > 0) {
                        fluidInventory.extract(0, fluidInventory.getResource(0), fluidCost, tx);
                    }
                    tx.commit();
                }
            });
        }

        progress = 0;
        sync();
    }


    private boolean canInsertOutputs(List<ItemStack> outputs) {
        return inventory.runInternal(() -> {
            try (Transaction tx = Transaction.open(null)) {

                for (ItemStack stack : outputs) {
                    int remaining = stack.getCount();

                    for (int slot = FIRST_OUTPUT_SLOT;
                         slot <= LAST_OUTPUT_SLOT && remaining > 0;
                         slot++) {

                        remaining -= inventory.insert(slot,ItemResource.of(stack), remaining, tx);
                    }

                    if (remaining > 0) {
                        return false;
                    }
                }

                return true;
            }
        });
    }


    private int getMeshTier(ItemStack mesh) {
        if (mesh.isEmpty()) return 0;

        if (mesh.is(StrainersTags.Items.TIER_1_MESHES)) return 1;
        if (mesh.is(StrainersTags.Items.TIER_2_MESHES)) return 2;
        if (mesh.is(StrainersTags.Items.TIER_3_MESHES)) return 3;
        if (mesh.is(StrainersTags.Items.TIER_4_MESHES)) return 4;
        if (mesh.is(StrainersTags.Items.TIER_5_MESHES)) return 5;
        if (mesh.is(StrainersTags.Items.TIER_6_MESHES)) return 6;
        if (mesh.is(StrainersTags.Items.TIER_7_MESHES)) return 7;
        if (mesh.is(StrainersTags.Items.TIER_8_MESHES)) return 8;
        if (mesh.is(StrainersTags.Items.TIER_9_MESHES)) return 9;
        if (mesh.is(StrainersTags.Items.TIER_10_MESHES)) return 10;
        if (mesh.is(StrainersTags.Items.TIER_11_MESHES)) return 11;
        if (mesh.is(StrainersTags.Items.TIER_12_MESHES)) return 12;

        return 0;
    }

    public boolean isRemovedRecipeOutput(ItemStack output) {
        return getRemovedRecipeOutputs().stream()
                .anyMatch(stack -> ItemStack.isSameItemSameComponents(stack, output));
    }

    public void setRemovedRecipeOutput(ItemStack output, boolean removed) {
        ItemStack mesh = inventory.getResource(MESH_SLOT).toStack();
        if (mesh.isEmpty()) return;

        List<ItemStack> updated = new ArrayList<>(getRemovedRecipeOutputs());
        updated.removeIf(stack -> ItemStack.isSameItemSameComponents(stack, output));
        if (removed) {
            updated.add(output.copyWithCount(1));
        }

        if (updated.isEmpty()) {
            mesh.remove(StrainersDataComponents.REMOVED_DROPS.get());
        } else {
            mesh.set(StrainersDataComponents.REMOVED_DROPS.get(), updated);
        }

        inventory.set(MESH_SLOT, ItemResource.of(mesh), mesh.getCount());
    }

    public List<ItemStack> getRemovedRecipeOutputs() {
        ItemStack mesh = inventory.getResource(MESH_SLOT).toStack();
        return mesh.getOrDefault(StrainersDataComponents.REMOVED_DROPS.get(), List.of());
    }

    public List<ItemStack> getPossibleRecipeOutputs() {
        List<ItemStack> outputs = new ArrayList<>();

        for (RecipeHolder<StrainerRecipe> holder : cachedRecipes) {
            ItemStack template = holder.value().result().template().create();
            if (!template.isEmpty() && outputs.stream().noneMatch(s -> ItemStack.isSameItemSameComponents(s, template))) {
                outputs.add(template);
            }
        }
        return outputs;
    }

    public boolean onPlayerUse(Player player, InteractionHand hand) {
        return FluidUtil.interactWithFluidHandler(player, hand, this.worldPosition, fluidInventory);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        inventory.serialize(output.child("inventory"));
        fluidInventory.serialize(output.child("fluidInventory"));
        output.putInt("progress", progress);
        output.putInt("maxProgress", maxProgress);
        output.putInt("requiredFluidAmount", requiredFluidAmount);

        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        inventory.deserialize(input.childOrEmpty("inventory"));
        fluidInventory.deserialize(input.childOrEmpty("fluidInventory"));
        progress = input.getIntOr("progress", 0);
        maxProgress = input.getIntOr("maxProgress", 200);
        requiredFluidAmount = input.getIntOr("requiredFluidAmount", 0);

        updateCachedRecipes();
        recheckFluidRequirement();

        super.loadAdditional(input);

    }

    @Override
    public void onLoad() {
        super.onLoad();
        updateCachedRecipes();
        recheckFluidRequirement();
    }

    public ItemStacksResourceHandler getItemHandler() {
        return inventory;
    }

    public FluidStacksResourceHandler getFluidHandler() {
        return fluidInventory;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int container, Inventory inventory, Player player) {
        return new StrainerMenu(container, inventory, this.worldPosition, data);
    }

    @Override
    public @NonNull Component getDisplayName() {
        return Component.translatable("block.strainers.strainer");
    }

    @Override
    public void preRemoveSideEffects(@NonNull BlockPos pos, @NonNull BlockState state) {
        dropInventoryContents(inventory);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(StrainersDataComponents.FLUIDS.get(),
                FluidListComponent.fromHandlers(fluidInventory));
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);
        FluidListComponent component = components.get(StrainersDataComponents.FLUIDS.get());
        if (component != null) {
            component.applyToHandlers(fluidInventory);
        }
    }
}