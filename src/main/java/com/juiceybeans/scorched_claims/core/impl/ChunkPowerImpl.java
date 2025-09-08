package com.juiceybeans.scorched_claims.core.impl;

import com.juiceybeans.scorched_claims.api.IChunkPower;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ChunkPowerImpl implements IChunkPower {
    public static final Capability<IChunkPower> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

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
        this.power += amount;
    }

    @Override
    public void decreaseChunkPower(int amount) {
        this.power = Math.max(0, this.power - amount);
    }


}
