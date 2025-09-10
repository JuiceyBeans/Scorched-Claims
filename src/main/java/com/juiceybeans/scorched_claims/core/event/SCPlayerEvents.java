package com.juiceybeans.scorched_claims.core.event;

import com.juiceybeans.scorched_claims.SCConfig;
import com.juiceybeans.scorched_claims.core.util.ClaimPowerUtils;
import com.juiceybeans.scorched_claims.core.util.FTBChunksUtils;

import dev.ftb.mods.ftbteams.api.Team;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SCPlayerEvents {

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        Level level = player.level();
        LevelChunk playerChunk = level.getChunkAt(player.blockPosition());
        Team claim = FTBChunksUtils.getTeamByChunk(level, playerChunk);

        if (claim == null) return; // return if not a claimed chunk

        if (FTBChunksUtils.isInTeam(player.getUUID(), claim.getTeamId())) {
            ClaimPowerUtils.decreaseClaimPower(playerChunk, SCConfig.INSTANCE.playerDeathDamage);
        }
    }
}
