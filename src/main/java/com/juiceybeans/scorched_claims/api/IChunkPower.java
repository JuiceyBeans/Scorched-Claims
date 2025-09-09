package com.juiceybeans.scorched_claims.api;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IChunkPower {

    int getChunkPower();

    void markDirty();

    void setChunkPower(int power);

    void increaseChunkPower(int amount);

    void decreaseChunkPower(int amount);
}
