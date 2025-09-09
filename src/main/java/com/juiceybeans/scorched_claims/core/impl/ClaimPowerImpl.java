package com.juiceybeans.scorched_claims.core.impl;

import com.juiceybeans.scorched_claims.api.IClaimPower;

import net.minecraft.world.level.chunk.LevelChunk;

public class ClaimPowerImpl implements IClaimPower {

    private final LevelChunk chunk;
    private int power = 1000;

    public ClaimPowerImpl(LevelChunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public int getClaimPower() {
        return this.power;
    }

    @Override
    public void markDirty() {
        chunk.setUnsaved(true);
    }

    @Override
    public void setClaimPower(int power) {
        this.power = power;
        markDirty();
    }

    @Override
    public void increaseClaimPower(int amount) {
        this.power += amount;
        markDirty();
    }

    @Override
    public void decreaseClaimPower(int amount) {
        this.power = Math.max(0, this.power - amount);
        markDirty();
    }
}
