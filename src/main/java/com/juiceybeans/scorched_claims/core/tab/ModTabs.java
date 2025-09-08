package com.juiceybeans.scorched_claims.core.tab;

import com.juiceybeans.scorched_claims.Main;
import com.juiceybeans.scorched_claims.core.ModBlocks;
import com.juiceybeans.scorched_claims.core.item.ModItems;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            Main.MOD_ID);

    public static final Supplier<CreativeModeTab> SCORCHED_CLAIMS_TAB = TABS.register("scorched_claims_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + Main.MOD_ID + ".scorched_claims_tab"))
                    .icon(() -> new ItemStack(ModItems.CLAIM_TICKET.get()))
                    .displayItems((params, output) -> {
                        addItems(output);
                        addBlocks(output);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }

    private static void addItems(CreativeModeTab.Output tabOutput) {
        for (RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
            tabOutput.accept(item.get());
        }
    }

    private static void addBlocks(CreativeModeTab.Output tabOutput) {
        for (RegistryObject<Block> block : ModBlocks.BLOCKS.getEntries()) {
            tabOutput.accept(block.get());
        }
    }
}
