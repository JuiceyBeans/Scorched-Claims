package com.juiceybeans.scorched_claims;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;

@dev.toma.configuration.config.Config(id = Main.MOD_ID)
public final class SCConfig {
    public static SCConfig INSTANCE;

    private static final Object LOCK = new Object();

    public static void init() {
        synchronized (LOCK) {
            if (INSTANCE == null) {
                INSTANCE = Configuration.registerConfig(SCConfig.class, ConfigFormats.YAML).getConfigInstance();
            }
        }
    }


    @Configurable
    public PassiveHealConfigs passiveHeal = new PassiveHealConfigs();

    public static class PassiveHealConfigs {
        @Configurable
        @Configurable.Synchronized
        @Configurable.DecimalRange(min = 0)
        @Configurable.Comment(value = "Time taken for a chunk to passively heal power (in seconds)", localize = true)
        public int chunkPassiveHealTime = 120;

        @Configurable
        @Configurable.Synchronized
        @Configurable.Comment(value = "Amount of power healed passively by a chunk. Can be negative to passively damage the chunk", localize = true)
        public int chunkPassiveHealRate = 20;

        @Configurable
        @Configurable.Synchronized
        @Configurable.DecimalRange(min = 0)
        @Configurable.Comment(value = "Maximum power upto which a chunk can heal passively", localize = true)
        public int chunkPassiveHealCap = 1000;
    }
}
