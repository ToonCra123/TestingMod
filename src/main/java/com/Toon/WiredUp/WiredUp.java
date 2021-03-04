package com.Toon.WiredUp;

import com.Toon.WiredUp.init.BlockInit;
import com.Toon.WiredUp.init.ModContainerTypes;
import com.Toon.WiredUp.init.TileEntityTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WiredUp.MODID)
public class WiredUp
{
    public static final String MODID = "wiredup";

    public WiredUp() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        BlockInit.Register();
        TileEntityTypes.register();
        ModContainerTypes.register();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }
}
