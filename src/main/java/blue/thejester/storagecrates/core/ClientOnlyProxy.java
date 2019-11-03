package blue.thejester.storagecrates.core;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ClientOnlyProxy extends CommonProxy {

    @Override
    public void preInit() {

        super.preInit();

        // This is currently necessary in order to make your block render properly when it is an item (i.e. in the inventory
        //   or in your hand or thrown on the ground).
        // Minecraft knows to look for the item model based on the GameRegistry.registerBlock.  However the registration of
        //  the model for each item is normally done by RenderItem.registerItems(), and this is not currently aware
        //   of any extra items you have created.  Hence you have to do it manually.
        // It must be done on client only, and must be done after the block has been created in Common.preinit().
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("storagecrates:mbe30_inventory_basic", "inventory");
        ModelResourceLocation itemModelResourceLocationM = new ModelResourceLocation("storagecrates:mbe30_inventory_basic_m", "inventory");
        ModelResourceLocation itemModelResourceLocationL = new ModelResourceLocation("storagecrates:mbe30_inventory_basic_l", "inventory");
        final int DEFAULT_ITEM_SUBTYPE = 0;
        ModelLoader.setCustomModelResourceLocation(CommonProxy.itemBlockInventoryBasic, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
        ModelLoader.setCustomModelResourceLocation(CommonProxy.itemBlockInventoryBasicM, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocationM);
        ModelLoader.setCustomModelResourceLocation(CommonProxy.itemBlockInventoryBasicL, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocationL);
    }

    @Override
    public boolean isDedicatedServer() {
        return false;
    }
}
