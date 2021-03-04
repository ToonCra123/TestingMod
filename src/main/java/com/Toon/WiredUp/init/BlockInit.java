package com.Toon.WiredUp.init;

import com.Toon.WiredUp.Blocks.ExampleTile;
import com.Toon.WiredUp.WiredUp;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static void Register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WiredUp.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WiredUp.MODID);

    // Blocks
    public static final RegistryObject<Block> TILE_EXAMPLE = BLOCKS.register("example_tile", ExampleTile::new);

    // Block Items
    public static final RegistryObject<Item> TILE_EXAMPLE_ITEM = ITEMS.register("example_tile", () -> new BlockItem(TILE_EXAMPLE.get(), new Item.Properties().group(ItemGroup.MISC)));
}
