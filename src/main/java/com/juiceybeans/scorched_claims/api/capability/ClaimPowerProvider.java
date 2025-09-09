package com.juiceybeans.scorched_claims.api.capability;

import com.juiceybeans.scorched_claims.Main;
import com.juiceybeans.scorched_claims.api.IClaimPower;
import com.juiceybeans.scorched_claims.core.impl.ClaimPowerImpl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ClaimPowerProvider implements ICapabilitySerializable<CompoundTag> {

    public static final ResourceLocation IDENTIFIER = Main.id("chunk_power");
    public static final String NBT_KEY_CHUNK_POWER = "chunk_power";

    private final IClaimPower backend;
    private final LazyOptional<IClaimPower> optionalData;

    public ClaimPowerProvider(LevelChunk chunk) {
        this.backend = new ClaimPowerImpl(chunk);
        this.optionalData = LazyOptional.of(() -> this.backend);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction direction) {
        return ModCapabilities.CLAIM_POWER_CAPABILITY.orEmpty(cap, optionalData);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(NBT_KEY_CHUNK_POWER, backend.getClaimPower());
        Main.LOGGER.info("Serializing chunk power {}", backend.getClaimPower());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains(NBT_KEY_CHUNK_POWER)) {
            int power = nbt.getInt(NBT_KEY_CHUNK_POWER);
            backend.setClaimPower(power);
            Main.LOGGER.info("Deserializing chunk power {}", power);
        } else {
            Main.LOGGER.info("No chunk power key found");
        }
    }
}
