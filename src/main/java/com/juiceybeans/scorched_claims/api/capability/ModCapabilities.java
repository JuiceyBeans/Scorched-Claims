package com.juiceybeans.scorched_claims.api.capability;

import com.juiceybeans.scorched_claims.Main;
import com.juiceybeans.scorched_claims.api.IChunkPower;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModCapabilities {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IChunkPower.class);
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<LevelChunk> event) {
        event.addCapability(ChunkPowerProvider.IDENTIFIER, new ChunkPowerProvider());
        Main.LOGGER.debug("Attached capability to chunk {}", event.getObject().getPos());
    }
}
