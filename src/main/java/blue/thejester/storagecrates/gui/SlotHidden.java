package blue.thejester.storagecrates.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlotHidden extends Slot {

    public SlotHidden(IInventory inventory, int index, int x, int y) {

        super(inventory, index, x, y);
    }

    @SideOnly(Side.CLIENT)
    public boolean isEnabled() {

        return false;
    }

    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return false;
    }

}
