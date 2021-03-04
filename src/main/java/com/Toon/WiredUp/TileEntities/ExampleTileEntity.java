package com.Toon.WiredUp.TileEntities;

import com.Toon.WiredUp.Containers.ExampleTileContainer;
import com.Toon.WiredUp.WiredUp;
import com.Toon.WiredUp.init.TileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class ExampleTileEntity extends LockableLootTileEntity implements ITickableTileEntity, IEnergyStorage {

    public final int maxExtract = 250;
    public int energy;
    public final int maxEnergy = 10000;
    public final int fePerTick = 10;

    private int timeLeftSmelting;
    private boolean isBurning;
    private int totalTimeSmelting;

    public static int slots = 1;
    protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

    public ExampleTileEntity() {
        super(TileEntityTypes.EXAMPLE_TILE_ENTITY.get());
    }


    public void dropAllContents(World world, BlockPos blockPos) {
        InventoryHelper.dropItems(world, blockPos, items);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.items = itemsIn;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + WiredUp.MODID + ".example_tile");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new ExampleTileContainer(id, player, this);
    }

    @Override
    public int getSizeInventory() {
        return slots;
    }

    /* ---------------------------------------


                NBT AND CLIENT SYNC


     ----------------------------------------*/

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if(!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.items);
        }
        compound.putInt("energy", this.energy);
        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(1, ItemStack.EMPTY);
        if(!this.checkLootAndRead(nbt)) {
            ItemStackHelper.loadAllItems(nbt, this.items);
        }
        this.energy = nbt.getInt("energy");
    }
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT comp = new CompoundNBT();
        write(comp);
        return new SUpdateTileEntityPacket(this.pos, 1, comp);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(this.getBlockState(), pkt.getNbtCompound());
    }

    public void handleUpdateTag(CompoundNBT tag) {
        this.read(this.getBlockState(), tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    private void syncClient() {
        this.getWorld().markBlockRangeForRenderUpdate(pos, this.getBlockState(), this.getBlockState());
        this.getWorld().notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 3);
        markDirty();
    }

    /* ---------------------------------------


                      Fuel


     ----------------------------------------*/

    public static boolean isFuel(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack) > 0;
    }

    public boolean isBurning() {
        return isBurning;
    }

    public float getTimeLeftSmeltingNormalized() {
        if (this.isBurning()) {

            return ((float) this.timeLeftSmelting / this.totalTimeSmelting);
        } else {
            return 0f;
        }
    }
    /* ---------------------------------------


                      Tick


     ----------------------------------------*/

    @Override
    public void tick() {
        if(this.energy < this.maxEnergy) {
            if(!isBurning) {
                for (ItemStack stack : this.items) {
                    if (isFuel(stack)) {
                        this.isBurning = true;
                        this.totalTimeSmelting = ForgeHooks.getBurnTime(stack);
                        this.timeLeftSmelting = ForgeHooks.getBurnTime(stack) - 1;
                        this.energy += this.fePerTick;
                        stack.setCount(stack.getCount() - 1);
                        syncClient();
                    }
                }
            } else {
                if (this.timeLeftSmelting > 0) {
                    this.energy += this.fePerTick;
                    this.timeLeftSmelting--;
                    syncClient();
                } else {
                    this.isBurning = false;
                    this.totalTimeSmelting = 0;
                    this.timeLeftSmelting = 0;
                }
            }
        }
    }


    /* ---------------------------------------


                      ENERGY


     ----------------------------------------*/


    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return this.energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return this.maxEnergy;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
