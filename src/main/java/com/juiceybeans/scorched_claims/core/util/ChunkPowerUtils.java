package com.juiceybeans.scorched_claims.core.util;

import com.juiceybeans.scorched_claims.api.IChunkPower;
import com.juiceybeans.scorched_claims.core.capability.ModCapabilities;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.LazyOptional;

public class ChunkPowerUtils {
    /**
     * Returns the capability, NOT the power level. Use getPower for that
     * @param chunk The chunk
     * @return Chunk power capability
     */
    public static LazyOptional<IChunkPower> getChunkPowerCapability(LevelChunk chunk) {
        return chunk.getCapability(ModCapabilities.INSTANCE);
    }

    public static int getChunkPower(LevelChunk chunk) {
        return getChunkPowerCapability(chunk).map(IChunkPower::getChunkPower).orElse(0);
    }

    public static void setChunkPower(LevelChunk chunk, int power) {
        getChunkPowerCapability(chunk).ifPresent(cap -> cap.setChunkPower(power));
    }

    public static void increaseChunkPower(LevelChunk chunk, int amount) {
        getChunkPowerCapability(chunk).ifPresent(cap -> cap.increaseChunkPower(amount));
    }

    public static void decreaseChunkPower(LevelChunk chunk, int amount) {
        getChunkPowerCapability(chunk).ifPresent(cap -> cap.decreaseChunkPower(amount));
    }
}
