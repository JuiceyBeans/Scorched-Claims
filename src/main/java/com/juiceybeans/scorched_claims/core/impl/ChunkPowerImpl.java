package com.juiceybeans.scorched_claims.core.impl;

import com.juiceybeans.scorched_claims.api.IChunkPower;

import net.minecraft.world.level.chunk.LevelChunk;

public class ChunkPowerImpl implements IChunkPower {

    private final LevelChunk chunk;
    private int power = 1000;

    public ChunkPowerImpl(LevelChunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public int getChunkPower() {
        return this.power;
    }

    @Override
    public void markDirty() {
        chunk.setUnsaved(true);
    }

    @Override
    public void setChunkPower(int power) {
        this.power = power;
        markDirty();
    }

    @Override
    public void increaseChunkPower(int amount) {
        this.power += amount;
        markDirty();
    }

    @Override
    public void decreaseChunkPower(int amount) {
        this.power = Math.max(0, this.power - amount);
        markDirty();
    }
}
