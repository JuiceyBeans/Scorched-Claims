package com.juiceybeans.scorched_claims.core.util;

import com.juiceybeans.scorched_claims.SCConfig;
import com.juiceybeans.scorched_claims.api.IClaimPower;
import com.juiceybeans.scorched_claims.api.capability.ModCapabilities;

import dev.ftb.mods.ftbchunks.api.ClaimedChunk;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.LazyOptional;

public class ClaimPowerUtils {

    /**
     * Returns the capability, NOT the power level. Use getPower for that
     * 
     * @param chunk The chunk
     * @return Claim power capability
     */
    public static LazyOptional<IClaimPower> getClaimPowerCapability(LevelChunk chunk) {
        return chunk.getCapability(ModCapabilities.CLAIM_POWER_CAPABILITY);
    }

    public static int getClaimPower(LevelChunk chunk) {
        return getClaimPowerCapability(chunk).map(IClaimPower::getClaimPower).orElse(0);
    }

    public static void setClaimPower(LevelChunk chunk, int power) {
        getClaimPowerCapability(chunk).ifPresent(cap -> cap.setClaimPower(power));
    }

    public static void increaseClaimPower(LevelChunk chunk, int amount) {
        getClaimPowerCapability(chunk).ifPresent(cap -> cap.increaseClaimPower(amount));
    }

    public static void decreaseClaimPower(LevelChunk chunk, int amount) {
        getClaimPowerCapability(chunk).ifPresent(cap -> cap.decreaseClaimPower(amount));
    }

    /**
     * Every 2 minutes, increases the power of every claimed chunk in a 3x3 around every online player by 20
     * 
     * @param server Server
     */
    public static void healClaimsAroundPlayers(MinecraftServer server) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            var level = player.level();
            ChunkPos playerChunk = new ChunkPos(player.blockPosition());

            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    ChunkPos chunkPos = new ChunkPos(playerChunk.x + x, playerChunk.z + z);
                    if (level.hasChunk(chunkPos.x, chunkPos.z)) {
                        LevelChunk chunk = level.getChunk(chunkPos.x, chunkPos.z);
                        int claimPower = ClaimPowerUtils.getClaimPower(chunk);

                        // return if the claim has been destroyed (0 power) or if its past the healing cap (1000)
                        if (claimPower <= 0 || claimPower >= SCConfig.INSTANCE.passiveHeal.claimPassiveHealCap)
                            continue;

                        ClaimPowerUtils.increaseClaimPower(chunk,
                                Math.min(SCConfig.INSTANCE.passiveHeal.claimPassiveHealRate, 1000 - claimPower));
                    }
                }
            }
        }
    }

    public static void destroyClaim(Level level, ClaimedChunk claim) {
        level.playSound(null, claim.getPos().getChunkPos().getWorldPosition(), SoundEvents.WITHER_DEATH,
                SoundSource.BLOCKS, 1.0f, 1.0f);
        level.getServer().sendSystemMessage(Component.translatable("chat.scorched_claims.claim_destroyed")
                .withStyle(ChatFormatting.RED));

        claim.unclaim(null, true); // todo figure this out. currently NPEs
    }
}
