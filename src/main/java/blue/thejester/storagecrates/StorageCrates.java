package blue.thejester.storagecrates;

import blue.thejester.storagecrates.core.CommonProxy;
import blue.thejester.storagecrates.networking.PacketClientToServer;
import blue.thejester.storagecrates.networking.PacketServerToClient;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod(modid = StorageCrates.MODID, name = StorageCrates.NAME, version = StorageCrates.VERSION)
public class StorageCrates
{
    public static final String MODID = "storagecrates";
    public static final String NAME = "Jester's Storage Crates";
    public static final String VERSION = "1.0";

    public static Logger logger;

    public static SimpleNetworkWrapper networkInstance;

    // The instance of your mod that Forge uses.  Optional.
    @Mod.Instance(StorageCrates.MODID)
    public static StorageCrates instance;

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide="blue.thejester.storagecrates.core.ClientOnlyProxy", serverSide="blue.thejester.storagecrates.core.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        networkInstance = NetworkRegistry.INSTANCE.newSimpleChannel(StorageCrates.MODID);
        networkInstance.registerMessage(PacketServerToClient.Handler.class, PacketServerToClient.class, 0, Side.CLIENT);
        networkInstance.registerMessage(PacketClientToServer.Handler.class, PacketClientToServer.class, 1, Side.SERVER);

        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}
