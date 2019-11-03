package blue.thejester.storagecrates.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class Packet implements IMessage {

    protected NBTTagCompound data;

    public Packet() {

    }

    public Packet(NBTTagCompound data, PacketHandler.Callback handler) {
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        try {
            this.data = buffer.readCompoundTag();
        } catch (Exception e) {
            //TODO: Log somethign here
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);

        buffer.writeCompoundTag(this.data);
    }
}
