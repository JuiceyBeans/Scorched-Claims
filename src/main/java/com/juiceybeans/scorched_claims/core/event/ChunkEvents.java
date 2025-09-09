package com.juiceybeans.scorched_claims.core.event;

import com.juiceybeans.scorched_claims.core.util.ChunkPowerUtils;

import xaero.pac.common.server.api.OpenPACServerAPI;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ChunkEvents {

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent event) {
        var pos = BlockPos.containing(event.getExplosion().getPosition());
        var level = event.getLevel();

        OpenPACServerAPI opacAPI = Minecraft.getInstance().isLocalServer() ?
                OpenPACServerAPI.get(Minecraft.getInstance().getSingleplayerServer()) :
                OpenPACServerAPI.get(level.getServer());

        var chunkOwner = opacAPI.getServerClaimsManager().get(level.dimension().registry(), pos).getPlayerId();
        if (chunkOwner == null || level.getServer().getPlayerList().getPlayer(chunkOwner) == null) return;

        var chunk = level.getChunkAt(pos);
        int power = ChunkPowerUtils.getChunkPower(chunk);
        var exploder = event.getExplosion().getExploder();
        var reduceBy = exploder instanceof PrimedTnt ? 100 : 50;

        if (power > 0) {
            ChunkPowerUtils.decreaseChunkPower(chunk, reduceBy);
        } else {
            level.playSound(null, pos, SoundEvents.WITHER_DEATH, SoundSource.BLOCKS, 1.0f, 1.0f);
            opacAPI.getServerClaimsManager().unclaim(
                    level.dimension().registry(), chunk.getPos().x, chunk.getPos().z);
        }
    }
}
