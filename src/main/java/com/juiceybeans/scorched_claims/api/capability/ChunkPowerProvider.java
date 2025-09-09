package com.juiceybeans.scorched_claims.api.capability;

import com.juiceybeans.scorched_claims.Main;
import com.juiceybeans.scorched_claims.api.IChunkPower;
import com.juiceybeans.scorched_claims.core.impl.ChunkPowerImpl;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChunkPowerProvider implements ICapabilitySerializable<CompoundTag> {
    public static final ResourceLocation IDENTIFIER = Main.id("chunk_power");
    public static final String NBT_KEY_CHUNK_POWER = "chunk_power";

    private final IChunkPower backend;
    private final LazyOptional<IChunkPower> optionalData;

    public ChunkPowerProvider(LevelChunk chunk) {
        this.backend = new ChunkPowerImpl(chunk);
        this.optionalData = LazyOptional.of(() -> this.backend);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction direction) {
        return ModCapabilities.CHUNK_POWER_CAPABILITY.orEmpty(cap, optionalData);
    }

    void invalidate() {
        this.optionalData.invalidate();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(NBT_KEY_CHUNK_POWER, backend.getChunkPower());
        Main.LOGGER.debug("Serializing chunk power {}", backend.getChunkPower());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains(NBT_KEY_CHUNK_POWER)) {
            int power = nbt.getInt(NBT_KEY_CHUNK_POWER);
            backend.setChunkPower(power);
            Main.LOGGER.debug("Deserializing chunk power {}", power);
        } else {
            Main.LOGGER.debug("No chunk power key found");
        }
    }
}