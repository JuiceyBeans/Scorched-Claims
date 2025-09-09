package com.juiceybeans.scorched_claims;

import com.juiceybeans.scorched_claims.core.block.ModBlocks;
import com.juiceybeans.scorched_claims.core.event.ChunkEvents;
import com.juiceybeans.scorched_claims.core.item.ModItems;
import com.juiceybeans.scorched_claims.core.tab.ModTabs;
import com.juiceybeans.scorched_claims.core.util.GlobalTickHandler;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("removal")
@Mod(Main.MOD_ID)
public class Main {

    public static final String MOD_ID = "scorched_claims";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(bus);
        ModBlocks.register(bus);
        ModTabs.register(bus);

        bus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        SCConfig.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(GlobalTickHandler.class);
        MinecraftForge.EVENT_BUS.register(ChunkEvents.class);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
