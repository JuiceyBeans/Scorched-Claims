package com.juiceybeans.scorched_claims.core.capability;

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
    public static final Capability<IChunkPower> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IChunkPower.class);
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<LevelChunk> event) {
        event.addCapability(ChunkPowerAttacher.ChunkPowerProvider.IDENTIFIER, new ChunkPowerAttacher.ChunkPowerProvider());
        Main.LOGGER.debug("Attached capability to chunk {}", event.getObject());
    }
}
