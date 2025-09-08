package com.juiceybeans.scorched_claims.api.capability;

import com.juiceybeans.scorched_claims.Main;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class CapabilityAttacher {
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<LevelChunk> event) {
        event.addCapability(ChunkPowerProvider.IDENTIFIER, new ChunkPowerProvider());
        Main.LOGGER.debug("Attached capability to chunk {}", event.getObject().getPos());
    }
}
