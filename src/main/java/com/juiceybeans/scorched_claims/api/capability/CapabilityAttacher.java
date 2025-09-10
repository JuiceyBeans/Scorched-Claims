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
        event.addCapability(ClaimPowerProvider.IDENTIFIER, new ClaimPowerProvider(event.getObject()));
        Main.LOGGER.info("Attached capability to chunk {}", event.getObject().getPos());
    }
}
