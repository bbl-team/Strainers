package com.benbenlaw.strainers.network.packet;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.entity.StrainerBlockEntity;
import com.benbenlaw.strainers.screen.custom.StrainerDropConfigMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

import java.util.ArrayList;
import java.util.List;

public record RequestDropConfigPacket(BlockPos blockPos) implements CustomPacketPayload {

    public static final Type<RequestDropConfigPacket> TYPE = new Type<>(Strainers.identifier("request_drop_config"));

    private static final StreamCodec<RegistryFriendlyByteBuf, List<ItemStack>> OUTPUTS_CODEC =
            ByteBufCodecs.collection(ArrayList::new, ItemStack.STREAM_CODEC);

    public static final IPayloadHandler<RequestDropConfigPacket> HANDLER = (packet, context) -> {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (player.level().getBlockEntity(packet.blockPos()) instanceof StrainerBlockEntity blockEntity) {
                List<ItemStack> outputs = blockEntity.getPossibleRecipeOutputs();

                player.openMenu(new SimpleMenuProvider(
                        (containerId, inv, p) -> new StrainerDropConfigMenu(containerId, inv, packet.blockPos(), outputs),
                        Component.translatable("screen.strainers.configure_drops")
                ), buf -> {
                    buf.writeBlockPos(packet.blockPos());
                    OUTPUTS_CODEC.encode(buf, outputs);
                });
            }
        });
    };

    public static final StreamCodec<RegistryFriendlyByteBuf, RequestDropConfigPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, RequestDropConfigPacket::blockPos,
            RequestDropConfigPacket::new
    );

    @Override
    public CustomPacketPayload.Type<RequestDropConfigPacket> type() {
        return TYPE;
    }
}