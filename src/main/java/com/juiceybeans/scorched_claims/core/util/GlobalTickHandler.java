package com.juiceybeans.scorched_claims.core.util;

import com.juiceybeans.scorched_claims.SCConfig;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GlobalTickHandler {

    private static long lastUpdateTime = 0;
    private static final long CHUNK_HEAL_INTERVAL = SCConfig.INSTANCE.passiveHeal.chunkPassiveHealTime * 20L;

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        long currentTime = event.getServer().getTickCount();
        if (currentTime - lastUpdateTime >= CHUNK_HEAL_INTERVAL) {
            lastUpdateTime = currentTime;
            ChunkPowerUtils.healChunksAroundPlayers(event.getServer());
        }
    }
}
