package com.juiceybeans.scorched_claims.api.capability;

import com.juiceybeans.scorched_claims.api.IChunkPower;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {
    public static final Capability<IChunkPower> CHUNK_POWER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
}