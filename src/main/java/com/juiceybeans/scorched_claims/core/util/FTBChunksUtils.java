package com.juiceybeans.scorched_claims.core.util;

import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.UUID;

public class FTBChunksUtils {

    public static Team getTeamByChunk(Level level, LevelChunk playerChunk) {
        var chunk = FTBChunksAPI.api().getManager().getChunk(new ChunkDimPos(level.dimension(), playerChunk.getPos()));
        return chunk != null ? chunk.getTeamData().getTeam() : null;
    }

    public static boolean isInTeam(UUID player, UUID teamToCheck) {
        var belongsTo = FTBTeamsAPI.api().getManager().getPlayerTeamForPlayerID(player).map(Team::getTeamId)
                .orElse(null);
        return belongsTo == teamToCheck;
    }
}
