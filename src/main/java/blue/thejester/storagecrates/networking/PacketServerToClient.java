package blue.thejester.storagecrates.networking;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

public class PacketServerToClient extends Packet {

    //This is necessary
    public PacketServerToClient() {}

    public PacketServerToClient(NBTTagCompound data, PacketHandler.Callback handler) {
        super(data, handler);
    }

    public static class Handler implements IMessageHandler<PacketServerToClient, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(final PacketServerToClient message, final MessageContext context) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                if (message.data != null) {
                    PacketHandler.CRATE_CALLBACK.handleData(message.data, context);
                }
            });
            return null;
        }
    }
}