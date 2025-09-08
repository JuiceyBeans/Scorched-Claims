package com.juiceybeans.scorched_claims.api;

public class ChunkPowerImpl implements IChunkPower {
    private int power = 1000;

    @Override
    public int getChunkPower() {
        return power;
    }

    @Override
    public void setChunkPower(int power) {
        this.power = power;
    }

    @Override
    public void increaseChunkPower(int amount) {
        this.power += power;
    }

    @Override
    public void decreaseChunkPower(int amount) {
        this.power = Math.max(0, this.power - amount);
    }


}
