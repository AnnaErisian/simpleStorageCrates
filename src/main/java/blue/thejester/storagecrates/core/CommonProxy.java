package blue.thejester.storagecrates.core;

import blue.thejester.storagecrates.*;
import blue.thejester.storagecrates.block.BlockInventoryBasic;
import blue.thejester.storagecrates.gui.GuiHandlerMBE30;
import blue.thejester.storagecrates.tileentity.TileEntityInventoryBasic;
import blue.thejester.storagecrates.tileentity.TileEntityInventoryBasicLarge;
import blue.thejester.storagecrates.tileentity.TileEntityInventoryBasicMed;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class CommonProxy {

    public static Block blockInventoryBasic;  // this holds the unique instance of your block
    public static ItemBlock itemBlockInventoryBasic; // and the corresponding item form that block
    public static Block blockInventoryBasicM;  // this holds the unique instance of your block
    public static ItemBlock itemBlockInventoryBasicM; // and the corresponding item form that block
    public static Block blockInventoryBasicL;  // this holds the unique instance of your block
    public static ItemBlock itemBlockInventoryBasicL; // and the corresponding item form that block

    /**
     * Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry
     */
    public void preInit() {
        //read config first

        // each instance of your block should have a name that is unique within your mod.  use lower case.
        /* it is a good practise to use a consistent registry name and obtain the unlocalised name from the registry name,
         *  this will avoid breaking old worlds if something changes. This would look like
         *  		blockInventoryBasic.getRegistryName().toString();
         *  and would require changing the lang file as the block's name would be now
         *          tile.storagecrates:mbe_30_inventory_basic.name
         */
        blockInventoryBasic = (BlockInventoryBasic) (new BlockInventoryBasic(1).setRegistryName(StorageCrates.MODID, "mbe30_inventory_basic"));
        blockInventoryBasic.setUnlocalizedName(blockInventoryBasic.getRegistryName().toString());
        ForgeRegistries.BLOCKS.register(blockInventoryBasic);
        blockInventoryBasicM = (BlockInventoryBasic) (new BlockInventoryBasic(2).setRegistryName(StorageCrates.MODID, "mbe30_inventory_basic_m"));
        blockInventoryBasicM.setUnlocalizedName(blockInventoryBasicM.getRegistryName().toString());
        ForgeRegistries.BLOCKS.register(blockInventoryBasicM);
        blockInventoryBasicL = (BlockInventoryBasic) (new BlockInventoryBasic(3).setRegistryName(StorageCrates.MODID, "mbe30_inventory_basic_l"));
        blockInventoryBasicL.setUnlocalizedName(blockInventoryBasicL.getRegistryName().toString());
        ForgeRegistries.BLOCKS.register(blockInventoryBasicL);

        // same but for the associated item
        itemBlockInventoryBasic = new ItemBlock(blockInventoryBasic);
        itemBlockInventoryBasic.setRegistryName(blockInventoryBasic.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlockInventoryBasic);
        itemBlockInventoryBasicM = new ItemBlock(blockInventoryBasicM);
        itemBlockInventoryBasicM.setRegistryName(blockInventoryBasicM.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlockInventoryBasicM);
        itemBlockInventoryBasicL = new ItemBlock(blockInventoryBasicL);
        itemBlockInventoryBasicL.setRegistryName(blockInventoryBasicL.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlockInventoryBasicL);

        // register the tile entity associated with the inventory block
        GameRegistry.registerTileEntity(TileEntityInventoryBasic.class, new ResourceLocation(StorageCrates.MODID, "mbe30_tile_inventory_basic"));
        GameRegistry.registerTileEntity(TileEntityInventoryBasicMed.class, new ResourceLocation(StorageCrates.MODID, "mbe30_tile_inventory_basic_m"));
        GameRegistry.registerTileEntity(TileEntityInventoryBasicLarge.class, new ResourceLocation(StorageCrates.MODID, "mbe30_tile_inventory_basic_l"));

        // You need to register a GUIHandler for the container.  However there can be only one handler per mod, so for the purposes
        //   of this project, we create a single GuiHandlerRegistry for all examples.
        // We register this GuiHandlerRegistry with the NetworkRegistry, and then tell the GuiHandlerRegistry about
        //   each example's GuiHandler, in this case GuiHandlerMBE30, so that when it gets a request from NetworkRegistry,
        //   it passes the request on to the correct example's GuiHandler.
        NetworkRegistry.INSTANCE.registerGuiHandler(StorageCrates.instance, GuiHandlerRegistry.getInstance());
        GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerMBE30(), GuiHandlerMBE30.getGuiID()+1);
        GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerMBE30(), GuiHandlerMBE30.getGuiID()+2);
        GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerMBE30(), GuiHandlerMBE30.getGuiID()+3);
        GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerMBE30(), GuiHandlerMBE30.getGuiID()+4);
    }

    /**
     * Do your mod setup. Build whatever data structures you care about. Register recipes,
     * send FMLInterModComms messages to other mods.
     */
    public void init() {

        ResourceLocation crateRecipeGroup = new ResourceLocation("storagecrates");

        // Small Crate
        GameRegistry.addShapedRecipe(new ResourceLocation("storagecrates:jsc_small_crate_recipe"), crateRecipeGroup, new ItemStack(itemBlockInventoryBasic), new Object[]{
                "CRC",
                "RCR",
                "CRC",
                'C', "chestWood",
                'R', "logWood"
        });

        // Medium Crate
        GameRegistry.addShapedRecipe(new ResourceLocation("storagecrates:jsc_medium_crate_recipe"), crateRecipeGroup, new ItemStack(itemBlockInventoryBasicM), new Object[]{
                "CRC",
                "RXR",
                "CRC",
                'C', "chestWood",
                'R', Items.IRON_INGOT,
                'X', itemBlockInventoryBasic
        });

        // Medium Crate
        GameRegistry.addShapedRecipe(new ResourceLocation("storagecrates:jsc_large_crate_recipe"), crateRecipeGroup, new ItemStack(itemBlockInventoryBasicL), new Object[]{
                "CRC",
                "RXR",
                "CRC",
                'C', "chestWood",
                'R', Items.DIAMOND,
                'X', itemBlockInventoryBasicM
        });

    }

    /**
     * Handle interaction with other mods, complete your setup based on this.
     */
    public void postInit() {
    }

    /**
     * is this a dedicated server?
     *
     * @return true if this is a dedicated server, false otherwise
     */
    public boolean isDedicatedServer() {
        return true;
    }
}
