package com.juiceybeans.scorched_claims.core.util;

import xaero.pac.common.claims.player.api.IPlayerChunkClaimAPI;
import xaero.pac.common.server.api.OpenPACServerAPI;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.UUID;

public class OPACUtil {

    public static OpenPACServerAPI getOpacApi(Level level) {
        return level.isClientSide() || level.getServer() == null ? null : OpenPACServerAPI.get(level.getServer());
    }

    public static UUID getChunkOwner(Level level, ChunkPos pos) {
        OpenPACServerAPI opacAPI = getOpacApi(level);
        if (opacAPI == null) return null;

        IPlayerChunkClaimAPI claim = opacAPI.getServerClaimsManager().get(level.dimension().registry(), pos);
        return claim == null ? null : claim.getPlayerId();
    }

    public static UUID getChunkOwner(Level level, BlockPos pos) {
        return getChunkOwner(level, level.getChunkAt(pos).getPos());
    }

    public static UUID getChunkOwner(Level level, LevelChunk chunk) {
        return getChunkOwner(level, chunk.getPos());
    }

    public static boolean isChunkClaimed(Level level, ChunkPos pos) {
        var opacAPI = getOpacApi(level);
        if (opacAPI == null) return false;

        var claim = opacAPI.getServerClaimsManager().get(level.dimension().registry(), pos);
        return claim != null;
    }

    public static boolean isChunkClaimed(Level level, LevelChunk chunk) {
        return isChunkClaimed(level, chunk.getPos());
    }
}
