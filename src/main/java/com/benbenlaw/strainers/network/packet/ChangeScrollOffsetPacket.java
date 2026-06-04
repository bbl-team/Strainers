package com.benbenlaw.strainers.network.packet;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.screen.custom.StrainerMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record ChangeScrollOffsetPacket(int containerId, int scrollOffset) implements CustomPacketPayload {

    public static final Type<ChangeScrollOffsetPacket> TYPE = new Type<>(Strainers.identifier("change_scroll_offset"));


    public static final IPayloadHandler<ChangeScrollOffsetPacket> HANDLER = (packet, context) -> {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (player.containerMenu instanceof StrainerMenu menu && menu.containerId == packet.containerId()) {
                menu.setScrollOffset(packet.scrollOffset());
            }
        });
    };

    public static final StreamCodec<RegistryFriendlyByteBuf, ChangeScrollOffsetPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ChangeScrollOffsetPacket::containerId,
            ByteBufCodecs.INT, ChangeScrollOffsetPacket::scrollOffset,
            ChangeScrollOffsetPacket::new
    );

    @Override
    public CustomPacketPayload.Type<ChangeScrollOffsetPacket> type() {
        return TYPE;
    }


}