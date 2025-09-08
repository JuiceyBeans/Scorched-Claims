package com.juiceybeans.scorched_claims.core.capability;

import com.juiceybeans.scorched_claims.Main;
import com.juiceybeans.scorched_claims.api.ChunkPowerImpl;
import com.juiceybeans.scorched_claims.api.IChunkPower;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChunkPowerAttacher {
    public static class ChunkPowerProvider implements ICapabilitySerializable<CompoundTag> {
        public static final ResourceLocation IDENTIFIER = Main.id("chunk_power");
        public static final String NBT_KEY_CHUNK_POWER = "chunk_power";

        private final IChunkPower backend = new ChunkPowerImpl();;
        private final LazyOptional<IChunkPower> optionalData = LazyOptional.of(() -> backend);

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction direction) {
            return ModCapabilities.INSTANCE.orEmpty(cap, optionalData);
        }

        void invalidate() {
            this.optionalData.invalidate();
        }

        @Override
        public CompoundTag serializeNBT() {
            final CompoundTag tag = new CompoundTag();
            tag.putDouble(NBT_KEY_CHUNK_POWER, backend.getChunkPower());
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            backend.setChunkPower(nbt.getInt(NBT_KEY_CHUNK_POWER));
        }
    }

    private ChunkPowerAttacher() {
    }
}
