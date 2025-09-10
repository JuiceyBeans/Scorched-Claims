package com.juiceybeans.scorched_claims.api.capability;

import com.juiceybeans.scorched_claims.api.IClaimPower;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {

    public static final Capability<IClaimPower> CLAIM_POWER_CAPABILITY = CapabilityManager
            .get(new CapabilityToken<>() {});
}
