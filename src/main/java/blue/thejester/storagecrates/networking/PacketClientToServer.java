package blue.thejester.storagecrates.networking;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class PacketClientToServer extends Packet {

    //This is necessary
    public PacketClientToServer() {}

    public PacketClientToServer(NBTTagCompound data, PacketHandler.Callback handler) {
        super(data, handler);
    }

    public static class Handler implements IMessageHandler<PacketClientToServer, IMessage> {

        @Override
        public IMessage onMessage(final PacketClientToServer message, final MessageContext context) {
            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
                if (message.data != null) {
                    PacketHandler.CRATE_CALLBACK.handleData(message.data, context);
                }
            });
            return null;
        }
    }
}