package com.juiceybeans.scorched_claims.data.datagen;

import com.juiceybeans.scorched_claims.Main;
import com.juiceybeans.scorched_claims.core.item.ModItems;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public abstract class ModLangProvider extends LanguageProvider {

    public ModLangProvider(PackOutput output, String locale) {
        super(output, Main.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        for (RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
            addItem(item, formatToEnglishLocalization(item.getId().getPath()));
        }

        addLang();
    }

    private static String formatToEnglishLocalization(String input) {
        return Arrays.stream(input.toLowerCase(Locale.ROOT).split("_"))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining(" "));
    }

    private void addLang() {
        add("itemGroup.scorched_claims.scorched_claims_tab", "Scorched Claims");

        add("chat.scorched_claims.debug_cat.switch", "Mode: %s");
        add("chat.scorched_claims.debug_cat.check_power", "%s: Power is %s");
        add("chat.scorched_claims.debug_cat.increase_power", "%s: Increased power, current power is %s");
        add("chat.scorched_claims.debug_cat.decrease_power", "%s: Decreased power, current power is %s");
        add("chat.scorched_claims.explosion_notification", "Explosion of power %s took place in chunk %s");
        add("chat.scorched_claims.claim_destroyed", "Chunk at %s was destroyed!");

        add("config.screen.scorched_claims", "Scorched Claims Config");
        add("config.scorched_claims.option.passiveHeal", "Chunk Passive Healing");
        add("config.scorched_claims.option.chunkPassiveHealTime", "Passive Heal Time");
        add("config.scorched_claims.option.chunkPassiveHealTime.comment.0", "Time taken for a chunk to passively heal power (in seconds)");
        add("config.scorched_claims.option.chunkPassiveHealRate", "Passive Heal Rate");
        add("config.scorched_claims.option.chunkPassiveHealRate.comment.0", "Amount of power healed passively by a chunk. Can be negative to passively damage the chunk");
        add("config.scorched_claims.option.chunkPassiveHealCap", "Passive Heal Cap");
        add("config.scorched_claims.option.chunkPassiveHealCap.comment.0", "Maximum power upto which a chunk can heal passively");
    }
}
