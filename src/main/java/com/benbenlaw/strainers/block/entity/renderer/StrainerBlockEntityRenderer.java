package com.benbenlaw.strainers.block.entity.renderer;

import com.benbenlaw.core.util.FluidRendererUtil;
import com.benbenlaw.strainers.block.entity.StrainerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidUtil;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import org.joml.Quaternionf;

public class StrainerBlockEntityRenderer implements BlockEntityRenderer<StrainerBlockEntity, StrainerBlockEntityRenderState> {

    private final ItemModelResolver itemModelResolver;
    public final ItemStackRenderState glassRenderer = new ItemStackRenderState();

    public StrainerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public StrainerBlockEntityRenderState createRenderState() {
        return new StrainerBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(StrainerBlockEntity blockEntity, StrainerBlockEntityRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        renderState.fluidStack = FluidUtil.getStack(blockEntity.getFluidHandler(), 0);

        renderState.tankCapacity = blockEntity.getFluidHandler().getCapacityAsInt(
                0,
                FluidResource.of(renderState.fluidStack)
        );

        renderState.mesh = ItemUtil.getStack(blockEntity.getItemHandler(), 1);
        renderState.blockEntityLevel = blockEntity.getLevel();
        renderState.processingItem = ItemUtil.getStack(blockEntity.getItemHandler(), 0);

        itemModelResolver.updateForTopItem(renderState.meshStackRenderer, renderState.mesh, ItemDisplayContext.FIXED, blockEntity.getLevel(), null,0);
        itemModelResolver.updateForTopItem(renderState.processingStackRenderer, renderState.processingItem, ItemDisplayContext.FIXED, blockEntity.getLevel(), null,0);
        itemModelResolver.updateForTopItem(renderState.glassStack, Blocks.GLASS.asItem().getDefaultInstance(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);

    }

    @Override
    public void submit(StrainerBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {

        if (!renderState.blockEntityLevel.getBlockState(renderState.blockPos.above()).is(Blocks.AIR)) return;

        //Fluids
        poseStack.pushPose();
        poseStack.translate(0, 0.9, 0);
        float fillRatio = renderState.tankCapacity == 0
                ? 0
                : renderState.fluidStack.getAmount() / (float) renderState.tankCapacity;

        FluidRendererUtil.submitFluid(poseStack, Sheets.translucentBlockItemSheet(), submitNodeCollector, renderState.fluidStack, fillRatio, renderState.lightCoords);
        poseStack.popPose();

        //Mesh
        poseStack.pushPose();
        poseStack.translate(0.5, 1.025, 0.5);
        poseStack.mulPose(new Quaternionf().rotationX((float) Math.toRadians(90)));
        renderState.meshStackRenderer.submit(poseStack, submitNodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();

        //Processing Item
        if (!renderState.processingItem.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5, 1.4, 0.5);
            poseStack.scale(1.5f, 1.5f, 1.5f);
            poseStack.mulPose(new Quaternionf().rotationX((float) Math.toRadians(90)));
            itemModelResolver.updateForTopItem(renderState.processingStackRenderer, renderState.processingItem, ItemDisplayContext.FIXED, renderState.blockEntityLevel, null, 0);
            renderState.processingStackRenderer.submit(poseStack, submitNodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }

        //Glass Casing
        if (!renderState.fluidStack.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5, 1.4, 0.5);
            poseStack.scale(1.8f, 1.8f, 1.8f);
            itemModelResolver.updateForTopItem(renderState.glassStack, Blocks.GLASS.asItem().getDefaultInstance(), ItemDisplayContext.FIXED, renderState.blockEntityLevel, null, 0);
            renderState.glassStack.submit(poseStack, submitNodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }
}