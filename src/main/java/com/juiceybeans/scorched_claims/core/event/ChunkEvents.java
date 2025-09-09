package com.juiceybeans.scorched_claims.core.event;

import com.juiceybeans.scorched_claims.core.util.ClaimPowerUtils;
import com.juiceybeans.scorched_claims.core.util.OPACUtil;

import xaero.pac.common.server.api.OpenPACServerAPI;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class ChunkEvents {

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent event) {
        Level level = event.getLevel();

        if (level.isClientSide()) return;

        BlockPos pos = BlockPos.containing(event.getExplosion().getPosition());
        OpenPACServerAPI opacAPI = OPACUtil.getOpacApi(level);

        if (opacAPI == null) return;

        UUID chunkOwner = OPACUtil.getChunkOwner(level, pos);

        // return if chunk does not have an owner, or if owner is not online
        if (chunkOwner == null || level.getServer().getPlayerList().getPlayer(chunkOwner) == null) return;

        LevelChunk chunk = level.getChunkAt(pos);
        int power = ClaimPowerUtils.getClaimPower(chunk);
        Entity exploder = event.getExplosion().getExploder();
        int reduceBy = exploder instanceof PrimedTnt ? 100 : 50;

        if (power > 0) {
            ClaimPowerUtils.decreaseClaimPower(chunk, reduceBy);
        } else {
            level.playSound(null, pos, SoundEvents.WITHER_DEATH, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.getServer().sendSystemMessage(Component.translatable("chat.scorched_claims.claim_destroyed")
                    .withStyle(ChatFormatting.RED));

            opacAPI.getServerClaimsManager().unclaim(
                    level.dimension().registry(), chunk.getPos().x, chunk.getPos().z);
        }
    }
}
