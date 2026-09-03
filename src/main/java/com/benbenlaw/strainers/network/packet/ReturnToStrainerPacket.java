package com.benbenlaw.strainers.network.packet;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.block.entity.StrainerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record ReturnToStrainerPacket(BlockPos blockPos) implements CustomPacketPayload {

    public static final Type<ReturnToStrainerPacket> TYPE = new Type<>(Strainers.identifier("return_to_strainer"));

    public static final IPayloadHandler<ReturnToStrainerPacket> HANDLER = (packet, context) -> {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (player.level().getBlockEntity(packet.blockPos()) instanceof StrainerBlockEntity blockEntity) {
                player.openMenu(blockEntity, buf -> buf.writeBlockPos(packet.blockPos()));
            }
        });
    };

    public static final StreamCodec<RegistryFriendlyByteBuf, ReturnToStrainerPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, ReturnToStrainerPacket::blockPos,
            ReturnToStrainerPacket::new
    );

    @Override
    public CustomPacketPayload.Type<ReturnToStrainerPacket> type() {
        return TYPE;
    }
}