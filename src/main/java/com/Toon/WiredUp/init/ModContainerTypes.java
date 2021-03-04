package com.Toon.WiredUp.init;

import com.Toon.WiredUp.Containers.ExampleTileContainer;
import com.Toon.WiredUp.TileEntities.ExampleTileEntity;
import com.Toon.WiredUp.WiredUp;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {
    public static void register() {
        CONTAINER_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, WiredUp.MODID);

    public static final RegistryObject<ContainerType<ExampleTileContainer>> EXAMPLE_TILE_ENTITY = CONTAINER_TYPES.register("example_container_entity",
            () -> IForgeContainerType.create(ExampleTileContainer::new));

}
