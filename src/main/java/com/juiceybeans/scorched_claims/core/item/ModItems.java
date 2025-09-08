package com.juiceybeans.scorched_claims.core.item;

import com.juiceybeans.scorched_claims.Main;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Item> CLAIM_TICKET = ITEMS.register("claim_ticket", () -> new Item(
            new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> DEBUG_CAT = ITEMS.register("debug_cat", () -> new DebugCatItem(
            new Item.Properties().rarity(Rarity.EPIC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
