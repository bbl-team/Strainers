package com.benbenlaw.strainers.network;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.network.packet.ChangeDropConfigScrollOffsetPacket;
import com.benbenlaw.strainers.network.packet.ChangeScrollOffsetPacket;
import com.benbenlaw.strainers.network.packet.RequestDropConfigPacket;
import com.benbenlaw.strainers.network.packet.ReturnToStrainerPacket;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class StrainersMessages {

    public static void registerNetworking(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Strainers.MOD_ID);

        //Client -> Server
        registrar.playToServer(ChangeScrollOffsetPacket.TYPE, ChangeScrollOffsetPacket.STREAM_CODEC, ChangeScrollOffsetPacket.HANDLER);
        registrar.playToServer(ChangeDropConfigScrollOffsetPacket.TYPE, ChangeDropConfigScrollOffsetPacket.STREAM_CODEC, ChangeDropConfigScrollOffsetPacket.HANDLER);
        registrar.playToServer(RequestDropConfigPacket.TYPE, RequestDropConfigPacket.STREAM_CODEC, RequestDropConfigPacket.HANDLER);
        registrar.playToServer(ReturnToStrainerPacket.TYPE, ReturnToStrainerPacket.STREAM_CODEC, ReturnToStrainerPacket.HANDLER);
    }
}