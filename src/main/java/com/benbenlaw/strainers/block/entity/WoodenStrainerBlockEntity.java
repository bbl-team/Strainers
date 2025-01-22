package com.benbenlaw.strainers.block.entity;

import com.benbenlaw.core.block.entity.handler.IInventoryHandlingBlockEntity;
import com.benbenlaw.core.block.entity.handler.InputOutputItemHandler;
import com.benbenlaw.core.recipe.NoInventoryRecipe;
import com.benbenlaw.strainers.block.ModBlocks;
import com.benbenlaw.strainers.recipe.MeshUpgradesRecipe;
import com.benbenlaw.strainers.recipe.OutputUpgradesRecipe;
import com.benbenlaw.strainers.recipe.StrainerRecipe;
import com.benbenlaw.strainers.screen.custom.WoodenStrainerMenu;
import com.benbenlaw.strainers.util.ModTags;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WoodenStrainerBlockEntity extends BlockEntity implements MenuProvider, IInventoryHandlingBlockEntity {


    private final ItemStackHandler itemHandler = new ItemStackHandler(25) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            sync();
        }

        @Override
        protected int getStackLimit(int slot, ItemStack stack) {

            if (slot == SPEED_UPGRADE || slot == MESH_UPGRADE || slot == OUTPUT_UPGRADE || slot == MESH_SLOT) {
                return 1;
            }
            return super.getStackLimit(slot, stack);
        }
    };
    private FakePlayer fakePlayer;


    public void sync() {
        if (level instanceof ServerLevel serverLevel) {
            LevelChunk chunk = serverLevel.getChunkAt(getBlockPos());
            if (Objects.requireNonNull(chunk.getLevel()).getChunkSource() instanceof ServerChunkCache chunkCache) {
                chunkCache.chunkMap.getPlayers(chunk.getPos(), false).forEach(this::syncContents);
            }
        }
    }

    public void syncContents(ServerPlayer player) {
        player.connection.send(Objects.requireNonNull(getUpdatePacket()));
    }



    public final ContainerData data;
    public int progress = 0;
    public int maxProgress = 220;
    public static final int INPUT_SLOT = 0;
    public static final int MESH_SLOT = 1;
    public static final int SPEED_UPGRADE = 2; // Upgrade Slot Opolis Utilities speed upgrades
    public static final int MESH_UPGRADE = 3; // Mesh damage upgrades
    public static final int OUTPUT_UPGRADE = 4; // Upgrade Slot Strainers chance increase upgrades

    //UPGRADE VALUES
    public double meshDamageChance = 1.0;
    public double outputChanceIncrease = 0.0;
    public static final int[] OUTPUT_SLOTS = {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};

    private final IItemHandler strainerItemHandler = new InputOutputItemHandler(
            itemHandler,
            this::isInputSlot,
            this::isOutputSlot
    );

    private boolean isInputSlot(int slot, ItemStack itemStack) {

        if (slot == MESH_SLOT) {
            return itemStack.is(ModTags.Items.MESHES);
        }

        else if (slot == INPUT_SLOT) {
            return !itemStack.is(ModTags.Items.MESHES);
        }


        return false;
    }

    private boolean isOutputSlot(int slot) {
        for (int outputSlot : OUTPUT_SLOTS) {
            if (slot == outputSlot) {
                return true;
            }
        }
        return false;
    }

    public IItemHandler getItemHandlerCapability(Direction side) {
        if (side == null)
            return itemHandler;

        return strainerItemHandler;
    }


    public void setHandler(ItemStackHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            this.itemHandler.setStackInSlot(i, handler.getStackInSlot(i));
        }
    }

    public ItemStackHandler getItemStackHandler() {
        return this.itemHandler;
    }


    public WoodenStrainerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.WOODEN_STRAINER_BLOCK_ENTITY.get(), blockPos, blockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> WoodenStrainerBlockEntity.this.progress;
                    case 1 -> WoodenStrainerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> WoodenStrainerBlockEntity.this.progress = value;
                    case 1 -> WoodenStrainerBlockEntity.this.maxProgress = value;
                }
            }

            public int getCount() {
                return 2;
            }
        };

        if (level instanceof ServerLevel serverLevel) {
            this.fakePlayer = createFakePlayer(serverLevel);
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.strainers.wooden_strainer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int container, @NotNull Inventory inventory, @NotNull Player player) {
        return new WoodenStrainerMenu(container, inventory, this.getBlockPos(), data);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.setChanged();
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(compoundTag, provider);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        saveAdditional(compoundTag, provider);
        return compoundTag;
    }

    @Override
    public void onDataPacket(@NotNull Connection connection, @NotNull ClientboundBlockEntityDataPacket clientboundBlockEntityDataPacket,
                             HolderLookup.@NotNull Provider provider) {
        super.onDataPacket(connection, clientboundBlockEntityDataPacket, provider);
    }


    @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.put("inventory", this.itemHandler.serializeNBT(provider));
        compoundTag.putInt("strainer.progress", progress);
        compoundTag.putInt("strainer.maxProgress", maxProgress);
        compoundTag.putDouble("strainer.meshDamageChance", meshDamageChance);
        compoundTag.putDouble("strainer.outputChanceIncrease", outputChanceIncrease);
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {
        this.itemHandler.deserializeNBT(provider, compoundTag.getCompound("inventory"));
        progress = compoundTag.getInt("strainer.progress");
        maxProgress = compoundTag.getInt("strainer.maxProgress");
        meshDamageChance = compoundTag.getDouble("strainer.meshDamageChance");
        outputChanceIncrease = compoundTag.getDouble("strainer.outputChanceIncrease");
        super.loadAdditional(compoundTag, provider);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    public void tick() {

        assert level != null;
        if (!level.isClientSide()) {

            if (this.fakePlayer == null && level instanceof ServerLevel serverLevel) {
                this.fakePlayer = createFakePlayer(serverLevel);
            }

            //Speed Upgrade Check

            if (itemHandler.getStackInSlot(SPEED_UPGRADE).isEmpty()) {
                maxProgress = 220;
            } else {
            //    getMaxProgressFromUpgrade();
                sync();
            }

            //Mesh Upgrade Check

            if (itemHandler.getStackInSlot(MESH_UPGRADE).isEmpty()) {
                meshDamageChance = 1.0;
            } else {
                getMeshDamageChanceUpgrade();
                sync();
            }

            //Output Upgrade Check

            if (itemHandler.getStackInSlot(OUTPUT_UPGRADE).isEmpty()) {
                outputChanceIncrease = 0.0;
            } else {
                getOutputChanceIncrease();
                sync();
            }

            BlockPos pPos = this.worldPosition;
            BlockState pState = level.getBlockState(pPos);
            WoodenStrainerBlockEntity pBlockEntity = this;

            if (hasRecipe(pBlockEntity)) {
                pBlockEntity.progress++;
                sync();

                setChanged(level, pPos, pState);
                if (pBlockEntity.progress > pBlockEntity.maxProgress) {
                    setChanged();
                    craftItem(pBlockEntity);
                }
            } else {
                pBlockEntity.resetProgress();
                sync();
                setChanged();
            }
        }

    }


    /*
    public void getMaxProgressFromUpgrade() {
        for (RecipeHolder<SpeedUpgradesRecipe> match : level.getRecipeManager().getRecipesFor(SpeedUpgradesRecipe.Type.INSTANCE, NoInventoryRecipe.INSTANCE, level)) {
            NonNullList<Ingredient> input = match.value().getIngredients();
            for (Ingredient ingredient : input) {
                if (ingredient.test(itemHandler.getStackInSlot(SPEED_UPGRADE))) {
                    maxProgress = match.value().tickRate();
                    break;
                }
            }
        }
    }

     */

    public void getMeshDamageChanceUpgrade() {
        assert level != null;
        for (RecipeHolder<MeshUpgradesRecipe> match : level.getRecipeManager().getRecipesFor(MeshUpgradesRecipe.Type.INSTANCE, NoInventoryRecipe.INSTANCE, level)) {
            NonNullList<Ingredient> input = match.value().getIngredients();
            for (Ingredient ingredient : input) {
                if (ingredient.test(itemHandler.getStackInSlot(MESH_UPGRADE))) {
                    meshDamageChance = match.value().meshDamageChance();
                    break;
                }
            }
        }
    }
    public void getOutputChanceIncrease() {
        assert level != null;
        for (RecipeHolder<OutputUpgradesRecipe> match : level.getRecipeManager().getRecipesFor(OutputUpgradesRecipe.Type.INSTANCE, NoInventoryRecipe.INSTANCE, level)) {
            NonNullList<Ingredient> input = match.value().getIngredients();
            for (Ingredient ingredient : input) {
                if (ingredient.test(itemHandler.getStackInSlot(OUTPUT_UPGRADE))) {
                    outputChanceIncrease = match.value().outputChanceIncrease();
                    break;
                }
            }
        }
    }

    private boolean hasRecipe(@NotNull WoodenStrainerBlockEntity entity) {
        Level level = entity.level;
        RecipeInput inventory = new RecipeInput() {
            @Override
            public @NotNull ItemStack getItem(int index) {
                return itemHandler.getStackInSlot(index);
            }

            @Override
            public int size() {
                return itemHandler.getSlots();
            }
        };

        assert level != null;
        List<RecipeHolder<StrainerRecipe>> allRecipes = level.getRecipeManager()
                .getAllRecipesFor(StrainerRecipe.Type.INSTANCE);

        BlockPos blockPos = entity.worldPosition.above(1);
        BlockState blockAbove = level.getBlockState(blockPos);
        FluidState fluidAbove = level.getFluidState(blockPos);

        for (RecipeHolder<StrainerRecipe> recipeHolder : allRecipes) {
            StrainerRecipe recipe = recipeHolder.value();
            Fluid fluidInRecipe = recipe.getFluidAbove();
            Block blockInRecipe = recipe.getBlockAbove();

            FluidState fluidInTank = null;
            if (blockAbove.is(ModBlocks.STRAINER_TANK.get())) {
                fluidInTank = ((StrainerTankBlockEntity) Objects.requireNonNull(level.getBlockEntity(blockPos))).getFluidStack().getFluid().defaultFluidState();
            }

            if (fluidInTank == null) {
                fluidInTank = fluidAbove;
            }

            boolean isFluidMatching = fluidInTank.is(fluidInRecipe) || fluidAbove.is(fluidInRecipe);
            boolean isBlockMatching = blockAbove.is(blockInRecipe) && (recipe.getBlockAbove().isEmpty(blockAbove) && !blockAbove.is(ModBlocks.STRAINER_TANK.get())) && fluidAbove.is(Fluids.EMPTY);
            boolean isAirBlockRecipe = blockAbove.is(blockInRecipe);

            if (isFluidMatching || isBlockMatching || isAirBlockRecipe) {
                if (hasMeshItem(entity, recipe)
                        && hasInputItem(entity, recipe)
                        && canStartRecipe(inventory, recipe.output().getItems()[0])) {
                    return true;
                }
            }
        }
        return false;
    }

    private void craftItem(@NotNull WoodenStrainerBlockEntity entity) {
        RecipeInput inventory = new RecipeInput() {
            @Override
            public @NotNull ItemStack getItem(int index) {
                return itemHandler.getStackInSlot(index);
            }

            @Override
            public int size() {
                return itemHandler.getSlots();
            }
        };

        assert level != null;
        List<RecipeHolder<StrainerRecipe>> allRecipes = level.getRecipeManager()
                .getAllRecipesFor(StrainerRecipe.Type.INSTANCE);

        BlockPos blockPos = entity.worldPosition.above(1);
        BlockState blockAbove = level.getBlockState(blockPos);
        FluidState fluidAbove = level.getFluidState(blockPos);

        for (RecipeHolder<StrainerRecipe> recipeHolder : allRecipes) {
            StrainerRecipe recipe = recipeHolder.value();
            Fluid fluidInRecipe = recipe.getFluidAbove();
            Block blockInRecipe = recipe.getBlockAbove();

            FluidState fluidInTank = null;
            if (blockAbove.is(ModBlocks.STRAINER_TANK.get())) {
                fluidInTank = ((StrainerTankBlockEntity) Objects.requireNonNull(level.getBlockEntity(blockPos))).getFluidStack().getFluid().defaultFluidState();
            }

            if (fluidInTank == null) {
                fluidInTank = fluidAbove;
            }

            boolean isFluidMatching = fluidInTank.is(fluidInRecipe) || fluidAbove.is(fluidInRecipe);
            boolean isBlockMatching = blockAbove.is(blockInRecipe) && (recipe.getBlockAbove().isEmpty(blockAbove) && !blockAbove.is(ModBlocks.STRAINER_TANK.get())) && fluidAbove.is(Fluids.EMPTY);
            boolean isAirBlockRecipe = blockAbove.is(blockInRecipe);

            if (isFluidMatching || isBlockMatching || isAirBlockRecipe) {
                if (hasMeshItem(entity, recipe)
                        && hasInputItem(entity, recipe)
                        && canStartRecipe(inventory, recipe.output().getItems()[0])) {

                    // Handle multiple potential outputs based on chance
                    if (!recipe.output().ingredient().hasNoItems() && Math.random() < recipe.getOutputChance() + outputChanceIncrease) {

                        Item outputItem = recipe.output().getItems()[0].getItem();
                        int outputCount = recipe.output().count();

                        ItemStack outputStack = new ItemStack(outputItem, outputCount);
                        boolean inserted = false;

                        // Try to insert into existing stacks first
                        for (int i = 5; i <= 24; i++) {
                            ItemStack existingStack = entity.itemHandler.getStackInSlot(i);
                            if (!existingStack.isEmpty() && ItemStack.isSameItem(existingStack, outputStack)) {
                                int combinedCount = existingStack.getCount() + outputStack.getCount();
                                int maxStackSize = Math.min(existingStack.getMaxStackSize(), entity.itemHandler.getSlotLimit(i));

                                if (combinedCount <= maxStackSize) {
                                    existingStack.grow(outputStack.getCount());
                                    inserted = true;
                                    break;
                                } else {
                                    int remaining = maxStackSize - existingStack.getCount();
                                    existingStack.grow(remaining);
                                    outputStack.shrink(remaining);
                                }
                            }
                        }

                        // If there is still remaining output, insert into empty slots
                        if (!inserted && !outputStack.isEmpty()) {
                            for (int i = 5; i <= 24; i++) {
                                if (entity.itemHandler.getStackInSlot(i).isEmpty()) {
                                    entity.itemHandler.setStackInSlot(i, outputStack);
                                    inserted = true;
                                    break;
                                }
                            }
                        }

                        if (!inserted) {
                            // If no space is available, drop the output item in the world
                            Containers.dropItemStack(level, entity.worldPosition.getX(), entity.worldPosition.getY(), entity.worldPosition.getZ(), outputStack);
                        }
                    }


                }
            }
        }

        // Handle input item consumption and mesh damage
        consumeInputItem(entity);
        damageMeshItem(entity);
        entity.resetProgress();
        // Continue to next recipe to handle multiple outputs
    }


    private void consumeInputItem(WoodenStrainerBlockEntity entity) {
        ItemStack inputItem = entity.itemHandler.getStackInSlot(INPUT_SLOT);
        if (!inputItem.isEmpty()) {
            inputItem.shrink(1);
            if (inputItem.getCount() <= 0) {
                entity.itemHandler.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
            }
        }
    }

    private void damageMeshItem(WoodenStrainerBlockEntity entity) {
        ItemStack meshItem = entity.itemHandler.getStackInSlot(MESH_SLOT);
        if (meshItem.isDamageableItem()) {
            if (Math.random() <= meshDamageChance) {
                meshItem.hurtAndBreak(1, fakePlayer, fakePlayer.getEquipmentSlotForItem(meshItem));
                if (meshItem.getCount() <= 0) {
                    entity.itemHandler.setStackInSlot(MESH_SLOT, ItemStack.EMPTY);
                }
            }
        }
    }

    private void resetProgress() {
        this.progress = 0;
        //this.maxProgress = 220;
    }

    private boolean hasMeshItem(WoodenStrainerBlockEntity entity, StrainerRecipe recipe) {
        ItemStack meshItem = entity.itemHandler.getStackInSlot(MESH_SLOT);
        if (recipe.getMinMeshTier() == 1) {
            return meshItem.is(ModTags.Items.TIER_1_MESHES) || meshItem.is(ModTags.Items.TIER_2_MESHES) || meshItem.is(ModTags.Items.TIER_3_MESHES) || meshItem.is(ModTags.Items.TIER_4_MESHES) || meshItem.is(ModTags.Items.TIER_5_MESHES) || meshItem.is(ModTags.Items.TIER_6_MESHES);
        }
        else if (recipe.getMinMeshTier() == 2) {
            return meshItem.is(ModTags.Items.TIER_2_MESHES) || meshItem.is(ModTags.Items.TIER_3_MESHES) || meshItem.is(ModTags.Items.TIER_4_MESHES) || meshItem.is(ModTags.Items.TIER_5_MESHES) || meshItem.is(ModTags.Items.TIER_6_MESHES);
        }
        else if (recipe.getMinMeshTier() == 3) {
            return meshItem.is(ModTags.Items.TIER_3_MESHES) || meshItem.is(ModTags.Items.TIER_4_MESHES) || meshItem.is(ModTags.Items.TIER_5_MESHES) || meshItem.is(ModTags.Items.TIER_6_MESHES);
        }
        else if (recipe.getMinMeshTier() == 4) {
            return meshItem.is(ModTags.Items.TIER_4_MESHES) || meshItem.is(ModTags.Items.TIER_5_MESHES) || meshItem.is(ModTags.Items.TIER_6_MESHES);
        }
        else if (recipe.getMinMeshTier() == 5) {
            return meshItem.is(ModTags.Items.TIER_5_MESHES) || meshItem.is(ModTags.Items.TIER_6_MESHES);
        }
        else if (recipe.getMinMeshTier() == 6) {
            return meshItem.is(ModTags.Items.TIER_6_MESHES);
        }
        return false;
    }

    public int getMeshTierInStrainer(WoodenStrainerBlockEntity entity, StrainerRecipe recipe) {
        if (hasMeshItem(entity, recipe)) {
            Item meshItem = entity.itemHandler.getStackInSlot(MESH_SLOT).getItem();

            if (meshItem.asItem().getDefaultInstance().is(ModTags.Items.TIER_6_MESHES)) {
                return 6;
            }
            else if (meshItem.asItem().getDefaultInstance().is(ModTags.Items.TIER_5_MESHES)) {
                return 5;
            }
            else if (meshItem.asItem().getDefaultInstance().is(ModTags.Items.TIER_4_MESHES)) {
                return 4;
            }
            else if (meshItem.asItem().getDefaultInstance().is(ModTags.Items.TIER_3_MESHES)) {
                return 3;
            }
            else if (meshItem.asItem().getDefaultInstance().is(ModTags.Items.TIER_2_MESHES)) {
                return 2;
            }
            else if (meshItem.asItem().getDefaultInstance().is(ModTags.Items.TIER_1_MESHES)) {
                return 1;
            }
        }
        return 0;
    }

    private boolean hasInputItem(@NotNull WoodenStrainerBlockEntity entity, @NotNull StrainerRecipe recipe) {

        ItemStack[] items = recipe.input().getItems();
        ItemStack slotItem = entity.itemHandler.getStackInSlot(INPUT_SLOT);

        for (ItemStack item : items) {
            if (ItemStack.isSameItem(item, slotItem)) {
                return true;
            }
        }
        return false;
    }

    private boolean canStartRecipe(RecipeInput inventory, ItemStack stack) {
        int emptySlotCounter = 0;
        int occupiedSlotCounter = 0;

        for (int i = 5; i <= 24; i++) {
            ItemStack itemStack = inventory.getItem(i);

            if (itemStack.isEmpty()) {
                emptySlotCounter++;
            } else if (itemStack.getItem() == stack.getItem()) {
                int availableSpace = stack.getCount(); // Use the entire stack size

                if (availableSpace > 0) {
                    // There's enough space for the entire stack, continue checking other slots
                    continue;
                } else {
                    return false; // Item already present in a slot, recipe should not start
                }
            } else {
                occupiedSlotCounter++;
            }
        }

        // Return false if all slots are occupied or if there are no empty slots
        return occupiedSlotCounter != 24 && emptySlotCounter != 0;
    }


    private FakePlayer createFakePlayer(ServerLevel level) {
        return new FakePlayer(level, new GameProfile(UUID.randomUUID(), "Strainer"));
    }
}