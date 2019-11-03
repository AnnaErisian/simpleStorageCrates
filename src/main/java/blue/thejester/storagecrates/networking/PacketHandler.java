package blue.thejester.storagecrates.networking;

import blue.thejester.storagecrates.tileentity.TileEntityInventoryBasic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandler {

    public static final Callback CRATE_CALLBACK = (compound, context) -> {
        World world = DimensionManager.getWorld(compound.getInteger("WorldID"));
        TileEntity probablyInventory = world.getTileEntity(new BlockPos(compound.getInteger("X"), compound.getInteger("Y"), compound.getInteger("Z")));

        if (probablyInventory instanceof TileEntityInventoryBasic) {
            TileEntityInventoryBasic inventory = (TileEntityInventoryBasic) probablyInventory;
            Entity entity = world.getEntityByID(compound.getInteger("PlayerID"));
            if (entity instanceof EntityPlayer) {
                inventory.onTabChange(compound.getInteger("ButtonID"), (EntityPlayer) entity);
            }
        }
    };

    interface Callback {
        void handleData(NBTTagCompound compoint, MessageContext context);
    }

}
