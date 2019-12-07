package blue.thejester.storagecrates.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

public class InventoryCapability implements IItemHandler {

    private final TileEntityInventoryBasic tile;

    InventoryCapability(TileEntityInventoryBasic tile) {
        this.tile = tile;
    }

    @Override
    public int getSlots() {
        return tile.getSizeInventory();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int i) {
        return tile.getStackInSlot(i);
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        validateSlotIndex(slot);

        ItemStack existing = this.tile.itemStacks[slot];

        int limit = getStackLimit(slot, stack);

        if (!existing.isEmpty())
        {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate)
        {
            if (existing.isEmpty())
            {
                this.tile.itemStacks[slot] = reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack;
            }
            else
            {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            onContentsChanged(slot);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount()- limit) : ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if (amount == 0)
            return ItemStack.EMPTY;

        validateSlotIndex(slot);

        ItemStack existing = this.tile.itemStacks[slot];

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract)
        {
            if (!simulate)
            {
                this.tile.itemStacks[slot] = ItemStack.EMPTY;
                onContentsChanged(slot);
            }
            return existing;
        }
        else
        {
            if (!simulate)
            {
                this.tile.itemStacks[slot] = ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract);
                onContentsChanged(slot);
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    private void onContentsChanged(int slot) {
        tile.markDirty();
    }

    protected int getStackLimit(int slot, @Nonnull ItemStack stack)
    {
        return Math.min(getSlotLimit(slot), stack.getMaxStackSize());
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return 64;
    }

    protected void validateSlotIndex(int slot)
    {
        if (slot < 0 || slot >= tile.getSizeInventory())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + tile.getSizeInventory() + ")");
    }
}
