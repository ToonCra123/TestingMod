package com.Toon.WiredUp.init;

import com.Toon.WiredUp.TileEntities.ExampleTileEntity;
import com.Toon.WiredUp.WiredUp;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypes {
    public static void register() {
        TILE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final DeferredRegister<TileEntityType<?>> TILE_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, WiredUp.MODID);

    public static final RegistryObject<TileEntityType<ExampleTileEntity>> EXAMPLE_TILE_ENTITY = TILE_TYPES.register("example_tile_entity",
            () -> TileEntityType.Builder.create(ExampleTileEntity::new, BlockInit.TILE_EXAMPLE.get()).build(null));
}
