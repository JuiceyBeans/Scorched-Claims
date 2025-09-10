package com.juiceybeans.scorched_claims.core.event;

import com.juiceybeans.scorched_claims.core.util.ClaimPowerUtils;

import dev.ftb.mods.ftbchunks.api.ChunkTeamData;
import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import dev.ftb.mods.ftbchunks.api.ClaimedChunkManager;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Set;
import java.util.UUID;

public class ChunkEvents {

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent event) {
        Level level = event.getLevel();

        if (level.isClientSide()) return;

        BlockPos blockPos = BlockPos.containing(event.getExplosion().getPosition());
        ChunkDimPos chunkDimPos = new ChunkDimPos(level, blockPos);
        ClaimedChunkManager manager = FTBChunksAPI.api().getManager();
        ClaimedChunk claim = manager.getChunk(chunkDimPos);

        ChunkTeamData teamData = claim != null ? claim.getTeamData() : null;
        if (teamData == null) return; // return if chunk is unclaimed

        Set<UUID> teamId = teamData.getTeam().getMembers();
        int onlinePlayerCount = 0;
        for (var pId : teamId) {
            if (level.getServer().getPlayerList().getPlayer(pId) != null) {
                onlinePlayerCount++; // increment if players are online
                break;
            }
        }

        // return if nobody from the team is online
        if (onlinePlayerCount == 0) return;

        LevelChunk chunk = level.getChunkAt(blockPos);
        int power = ClaimPowerUtils.getClaimPower(chunk);
        Entity exploder = event.getExplosion().getExploder();
        int reduceBy = exploder instanceof PrimedTnt ? 100 : 50;

        if (power > 0) {
            ClaimPowerUtils.decreaseClaimPower(chunk, reduceBy);
        } else {
            ClaimPowerUtils.destroyClaim(level, claim);
        }
    }
}
