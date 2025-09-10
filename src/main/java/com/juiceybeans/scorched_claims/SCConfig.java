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

    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 0)
    @Configurable.Comment(value = "Damage dealt to a claim when a team member dies in it", localize = true)
    public int playerDeathDamage = 100;

    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 0)
    @Configurable.Comment(value = "Damage dealt to a claim when TNT explodes in it", localize = true)
    public int tntDamage = 100;

    public static class PassiveHealConfigs {

        @Configurable
        @Configurable.Synchronized
        @Configurable.DecimalRange(min = 0)
        @Configurable.Comment(value = "Time taken for a claim to passively heal power (in seconds)", localize = true)
        public int claimPassiveHealTime = 120;

        @Configurable
        @Configurable.Synchronized
        @Configurable.Comment(value = "Amount of power healed passively by a chunk. Can be negative to passively damage the claim",
                              localize = true)
        public int claimPassiveHealRate = 20;

        @Configurable
        @Configurable.Synchronized
        @Configurable.DecimalRange(min = 0)
        @Configurable.Comment(value = "Maximum power upto which a claim can heal passively", localize = true)
        public int claimPassiveHealCap = 1000;
    }
}
