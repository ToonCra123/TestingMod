package com.Toon.WiredUp.Utils;

import com.Toon.WiredUp.Containers.ExampleTileContainer;
import com.Toon.WiredUp.WiredUp;
import com.Toon.WiredUp.client.screen.ExampleTileContainerScreen;
import com.Toon.WiredUp.init.ModContainerTypes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = WiredUp.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ModContainerTypes.EXAMPLE_TILE_ENTITY.get(), ExampleTileContainerScreen::new);
    }
}