package com.benbenlaw.strainers.network.packet;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.screen.custom.StrainerDropConfigMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record ChangeDropConfigScrollOffsetPacket(int containerId, int scrollOffset) implements CustomPacketPayload {

    public static final Type<ChangeDropConfigScrollOffsetPacket> TYPE = new Type<>(Strainers.identifier("change_drop_config_scroll_offset"));

    public static final IPayloadHandler<ChangeDropConfigScrollOffsetPacket> HANDLER = (packet, context) -> {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (player.containerMenu instanceof StrainerDropConfigMenu menu && menu.containerId == packet.containerId()) {
                menu.setScrollOffset(packet.scrollOffset());
            }
        });
    };

    public static final StreamCodec<RegistryFriendlyByteBuf, ChangeDropConfigScrollOffsetPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ChangeDropConfigScrollOffsetPacket::containerId,
            ByteBufCodecs.INT, ChangeDropConfigScrollOffsetPacket::scrollOffset,
            ChangeDropConfigScrollOffsetPacket::new
    );

    @Override
    public CustomPacketPayload.Type<ChangeDropConfigScrollOffsetPacket> type() {
        return TYPE;
    }
}