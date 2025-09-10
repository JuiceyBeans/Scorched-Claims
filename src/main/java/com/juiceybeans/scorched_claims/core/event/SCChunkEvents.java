package com.juiceybeans.scorched_claims.core.event;

import com.juiceybeans.scorched_claims.SCConfig;
import com.juiceybeans.scorched_claims.core.util.ClaimPowerUtils;
import com.juiceybeans.scorched_claims.core.util.FTBChunksUtils;

import dev.ftb.mods.ftbchunks.api.*;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Set;
import java.util.UUID;

public class SCChunkEvents {

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
        int reduceBy = exploder instanceof PrimedTnt ? SCConfig.INSTANCE.tntDamage : 50;

        if (power > 0) {
            ClaimPowerUtils.decreaseClaimPower(chunk, reduceBy);
        } else {
            ClaimPowerUtils.destroyClaim(level, claim);
        }
    }

    @SubscribeEvent
    public static void onChunkEntered(EntityEvent.EnteringSection event) {
        if (!(event.getEntity() instanceof Player player) || player.level().isClientSide() ||
                FTBChunksUtils.isInTeam(player.getUUID())) {
            return;
        }

        var newChunkTeamId = FTBChunksUtils.getChunkInfo(player.level(), event.getNewPos().chunk()).getTeamData()
                .getTeam().getId();
        var playerTeamId = FTBChunksUtils.getTeamByPlayer(player.getUUID());

        if (newChunkTeamId != playerTeamId) {
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 1, false, false));
        }
    }

    public static void onChunkClaimed(CommandSourceStack source, ClaimedChunk chunk) {
        ClaimPowerUtils.setClaimPower(source.getLevel().getChunk(chunk.getPos().getChunkPos().x,
                chunk.getPos().getChunkPos().z), 1000);
    }
}
