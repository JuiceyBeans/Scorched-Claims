package com.juiceybeans.scorched_claims.core.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.levelgen.WorldgenRandom;

// Dummy mixin so that MDG doesn't throw a fit
// Todo remove if we ever have another mixin
@Mixin(value = WorldgenRandom.class, remap = false)
public class DummyMixin {}
