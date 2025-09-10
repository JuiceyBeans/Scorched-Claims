package com.juiceybeans.scorched_claims.api;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IClaimPower {

    int getClaimPower();

    void markDirty();

    void setClaimPower(int power);

    void increaseClaimPower(int amount);

    void decreaseClaimPower(int amount);
}
