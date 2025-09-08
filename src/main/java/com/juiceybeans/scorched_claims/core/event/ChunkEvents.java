package com.juiceybeans.scorched_claims.core.event;

import net.minecraftforge.event.level.ChunkDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ChunkEvents {
    @SubscribeEvent
    public static void onChunkLoad(ChunkDataEvent.Load event) {
    }

    @SubscribeEvent
    public static void onChunkSave(ChunkDataEvent.Save event) {
    }
}
