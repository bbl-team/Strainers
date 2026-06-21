package com.benbenlaw.strainers.data;

import com.benbenlaw.core.block.SyncableBlock;
import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.StrainersBlocks;
import com.benbenlaw.strainers.client.OreTintSource;
import com.benbenlaw.strainers.fluid.StrainersFluids;
import com.benbenlaw.strainers.item.OrePieceItem;
import com.benbenlaw.strainers.item.StrainersItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.model.item.DynamicFluidContainerModel;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class StrainersModelProvider extends ModelProvider {

    public StrainersModelProvider(PackOutput output) {
        super(output, Strainers.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        //Items
        StrainersItems.ITEMS.getEntries().stream()
                .filter(entry -> !(entry.get() instanceof BlockItem || entry.get().asItem() instanceof OrePieceItem))
                .forEach(entry ->
                        itemModels.generateFlatItem(entry.get(), ModelTemplates.FLAT_ITEM)
                );

        itemModels.createFlatItemModel(StrainersItems.ORE_PIECE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.itemModelOutput.accept(StrainersItems.ORE_PIECE.get().asItem(),
                ItemModelUtils.tintedModel(ModelLocationUtils.getModelLocation(StrainersItems.ORE_PIECE.asItem()), new OreTintSource()));


        //Buckets
        bucketItem(itemModels, StrainersFluids.ERODING_WATER.getBucket(), StrainersFluids.ERODING_WATER.getFluid(), false, true);
        bucketItem(itemModels, StrainersFluids.PURIFYING_WATER.getBucket(), StrainersFluids.PURIFYING_WATER.getFluid(), false, true);
        bucketItem(itemModels, StrainersFluids.SALTY_WATER.getBucket(), StrainersFluids.SALTY_WATER.getFluid(), false, true);

        //Blocks
        blockModels.createTrivialCube(StrainersBlocks.DUST_BLOCK.get());
        blockModels.createTrivialCube(StrainersBlocks.PURIFIED_DUST_BLOCK.get());
        blockModels.createTrivialCube(StrainersBlocks.PURIFIED_SAND.get());
        blockModels.createTrivialCube(StrainersBlocks.PURIFIED_GRAVEL.get());
        blockModels.createTrivialCube(StrainersBlocks.PURIFIED_DIRT.get());
        blockModels.createTrivialCube(StrainersBlocks.PURIFIED_STONE.get());
        blockModels.createTrivialCube(StrainersBlocks.PURIFIED_SOUL_SAND.get());
        blockModels.createTrivialCube(StrainersBlocks.PURIFIED_SOUL_SOIL.get());
        blockModels.createTrivialCube(StrainersBlocks.PURIFIED_NETHERRACK.get());
        blockModels.createTrivialCube(StrainersBlocks.MULCH.get());
        blockModels.createTrivialCube(StrainersBlocks.PURIFIED_DEEPSLATE.get());
        blockModels.createTrivialCube(StrainersBlocks.PURIFIED_END_STONE.get());

        //Fluids
        blockModels.createNonTemplateModelBlock(StrainersFluids.ERODING_WATER.getBlock(), Blocks.WATER);
        blockModels.createNonTemplateModelBlock(StrainersFluids.PURIFYING_WATER.getBlock(), Blocks.WATER);
        blockModels.createNonTemplateModelBlock(StrainersFluids.SALTY_WATER.getBlock(), Blocks.WATER);


    }


    public void createMachineBlock(Block block, Consumer<BlockModelDefinitionGenerator> blockStateOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
        TextureMapping idleTextureMapping = (new TextureMapping()).put(TextureSlot.TOP, new Material(Strainers.identifier("block/casting_top"))).put(TextureSlot.SIDE, new Material(Strainers.identifier("block/casting_side"))).put(TextureSlot.FRONT, TextureMapping.getBlockTexture(block, "_front"));
        TextureMapping workingTextureMapping = (new TextureMapping()).put(TextureSlot.TOP, new Material(Strainers.identifier("block/casting_top"))).put(TextureSlot.SIDE, new Material(Strainers.identifier("block/casting_side"))).put(TextureSlot.FRONT, TextureMapping.getBlockTexture(block, "_front_working"));

        MultiVariant multivariant = plainVariant(ModelTemplates.CUBE_ORIENTABLE.create(block, idleTextureMapping, modelOutput));
        MultiVariant multivariant1 = plainVariant(ModelTemplates.CUBE_ORIENTABLE_VERTICAL.create(block, idleTextureMapping, modelOutput));

        MultiVariant workingVariant = plainVariant(ModelTemplates.CUBE_ORIENTABLE.createWithSuffix(block, "_working", workingTextureMapping, modelOutput));
        MultiVariant workingVariant1 = plainVariant(ModelTemplates.CUBE_ORIENTABLE_VERTICAL.createWithSuffix(block, "_working", workingTextureMapping, modelOutput));

        blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(BlockStateProperties.FACING, SyncableBlock.RUNNING)
                                .select(Direction.DOWN, false, multivariant1.with(X_ROT_180))
                                .select(Direction.UP, false, multivariant1)
                                .select(Direction.NORTH, false, multivariant)
                                .select(Direction.EAST, false, multivariant.with(Y_ROT_90))
                                .select(Direction.SOUTH,false, multivariant.with(Y_ROT_180))
                                .select(Direction.WEST,false, multivariant.with(Y_ROT_270))
                                .select(Direction.DOWN, true, workingVariant1.with(X_ROT_180))
                                .select(Direction.UP, true, workingVariant1)
                                .select(Direction.NORTH, true, workingVariant)
                                .select(Direction.EAST, true, workingVariant.with(Y_ROT_90))
                                .select(Direction.SOUTH,true, workingVariant.with(Y_ROT_180))
                                .select(Direction.WEST,true, workingVariant.with(Y_ROT_270))));

    }

    public void bucketItem(ItemModelGenerators itemModelGenerators, BucketItem item, Fluid fluid, boolean flipGas, boolean applyFluidLuminosity) {
        Material drip = new Material(Identifier.fromNamespaceAndPath(NeoForgeMod.MOD_ID, "item/mask/bucket_fluid_drip"));
        Material bucket = new Material(Identifier.withDefaultNamespace("item/bucket"));
        DynamicFluidContainerModel.Textures textures = new DynamicFluidContainerModel.Textures(Optional.empty(), Optional.of(bucket), Optional.of(drip), Optional.empty());
        itemModelGenerators.itemModelOutput.accept(item, new DynamicFluidContainerModel.Unbaked(textures, fluid, flipGas, false, applyFluidLuminosity));
    }

    @Override
    protected @NotNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return StrainersBlocks.BLOCKS.getEntries().stream().filter(
                x -> !x.is(StrainersBlocks.STRAINER.getId())
        );
    }

    @Override
    protected @NotNull Stream<? extends Holder<Item>> getKnownItems() {
        return StrainersItems.ITEMS.getEntries().stream().filter(
                x -> !x.is(StrainersBlocks.STRAINER.getId())
        );
    }

    @Override
    public @NotNull String getName() {
        return Strainers.MOD_ID + " Models";
    }
}
