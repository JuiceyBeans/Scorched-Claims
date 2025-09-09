package com.juiceybeans.scorched_claims.core.util;

import com.juiceybeans.scorched_claims.SCConfig;
import com.juiceybeans.scorched_claims.api.IChunkPower;
import com.juiceybeans.scorched_claims.api.capability.ModCapabilities;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.LazyOptional;

public class ChunkPowerUtils {

    /**
     * Returns the capability, NOT the power level. Use getPower for that
     * 
     * @param chunk The chunk
     * @return Chunk power capability
     */
    public static LazyOptional<IChunkPower> getChunkPowerCapability(LevelChunk chunk) {
        return chunk.getCapability(ModCapabilities.CHUNK_POWER_CAPABILITY);
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

    /**
     * Every 2 minutes, increases the power of every claimed chunk in a 3x3 around every online player by 20
     * @param server Server
     */
    public static void healChunksAroundPlayers(MinecraftServer server) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            var level = player.level();
            ChunkPos playerChunk = new ChunkPos(player.blockPosition());

            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    ChunkPos chunkPos = new ChunkPos(playerChunk.x + x, playerChunk.z + z);
                    if (level.hasChunk(chunkPos.x, chunkPos.z)) {
                        LevelChunk chunk = level.getChunk(chunkPos.x, chunkPos.z);
                        int chunkPower = ChunkPowerUtils.getChunkPower(chunk);

                        // return if the claim has been destroyed (0 power) or if its past the healing cap (1000)
                        if (chunkPower <= 0 || chunkPower >= SCConfig.INSTANCE.passiveHeal.chunkPassiveHealCap) return;

                        ChunkPowerUtils.increaseChunkPower(chunk,
                                Math.min(SCConfig.INSTANCE.passiveHeal.chunkPassiveHealRate, 1000 - chunkPower));
                    }
                }
            }
        }
    }
}
