package com.Toon.WiredUp.Containers;

import com.Toon.WiredUp.TileEntities.ExampleTileEntity;
import com.Toon.WiredUp.init.BlockInit;
import com.Toon.WiredUp.init.ModContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import javax.annotation.Nullable;
import java.util.Objects;

public class ExampleTileContainer extends Container {

    public final ExampleTileEntity te;
    private final IWorldPosCallable canInteractWithCallable;

    public ExampleTileContainer(final int id, final PlayerInventory playInv, final ExampleTileEntity te) {
        super(ModContainerTypes.EXAMPLE_TILE_ENTITY.get(), id);
        this.te = te;
        this.canInteractWithCallable = IWorldPosCallable.of(te.getWorld(), te.getPos());

        this.addSlot(new Slot((IInventory) te, 0, 80, 35));

        // Main Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playInv, col + row * 9 + 9, 8 + col * 18, 166 - (4 - row) * 18 - 10));
            }
        }

        // Player Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playInv, col, 8 + col * 18, 142));
        }
    }

    public ExampleTileContainer(final int id, final PlayerInventory playInv, final PacketBuffer data) {
        this(id, playInv, getTileEntity(playInv, data));
    }

    private static ExampleTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(data, "Packet Buffer cannot be null.");
        final TileEntity te = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (te instanceof ExampleTileEntity) {
            return (ExampleTileEntity) te;
        }
        throw new IllegalStateException("Tile Entity Is Not Correct");
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.TILE_EXAMPLE.get());
    }

    public float getEnergyNormalizedStored() {
        return ((float) this.te.energy / this.te.maxEnergy) * 67;
    }

    public float getFuelNormalized() {
        return (this.te.getTimeLeftSmeltingNormalized()) * 14;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();
            if (index < ExampleTileEntity.slots
                    && !this.mergeItemStack(stack1, ExampleTileEntity.slots, this.inventorySlots.size(), true)) {
                return ItemStack.EMPTY;
            }
            if (!this.mergeItemStack(stack1, 0, ExampleTileEntity.slots, false)) {
                return ItemStack.EMPTY;
            }

            if (stack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return stack;
    }
}
