package com.benbenlaw.strainers.network;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.network.packet.ChangeScrollOffsetPacket;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class StrainersMessages {

    public static void registerNetworking(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Strainers.MOD_ID);

        //Client -> Server
        registrar.playToServer(ChangeScrollOffsetPacket.TYPE, ChangeScrollOffsetPacket.STREAM_CODEC, ChangeScrollOffsetPacket.HANDLER);
    }
}
