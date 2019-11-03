package blue.thejester.storagecrates.gui;

import blue.thejester.storagecrates.StorageCrates;
import blue.thejester.storagecrates.tileentity.TileEntityInventoryBasic;
import blue.thejester.storagecrates.networking.PacketClientToServer;
import blue.thejester.storagecrates.networking.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.IOException;

/**
 * User: brandon3055
 * Date: 06/01/2015
 *
 * GuiInventoryBasic is a simple gui that does nothing but draw a background image and a line of text on the screen
 * everything else is handled by the vanilla container code
 */
@SideOnly(Side.CLIENT)
public class GuiInventoryBasic extends GuiContainer {

	// This is the resource location for the background image for the GUI
	private static final ResourceLocation texture = new ResourceLocation("storagecrates", "textures/gui/crate_inventory_bg.png");
	private TileEntityInventoryBasic tileEntityInventoryBasic;
	private final int page;

	public GuiInventoryBasic(InventoryPlayer invPlayer, TileEntityInventoryBasic tile, int page) {
		super(new ContainerBasic(invPlayer, tile, page));
		this.page = page;
		tileEntityInventoryBasic = tile;
		// Set the width and height of the gui.  Should match the size of the texture!
		xSize = 247;
		ySize = 256;
	}

	@Override
	public void initGui() {
		super.initGui();

		//intentional fallthrough usage because I am oh so clever for using a basic feature of the language
		switch(tileEntityInventoryBasic.pages) {
			case 4:
				this.buttonList.add(new SlotShowingButton(4, this.guiLeft + 215, this.guiTop + 200, 20, 20,
						"", ContainerBasic.TE_INVENTORY_SLOT_COUNT * 3, this.tileEntityInventoryBasic, this));
			case 3:
				this.buttonList.add(new SlotShowingButton(3, this.guiLeft + 215, this.guiTop + 176, 20, 20,
						"", ContainerBasic.TE_INVENTORY_SLOT_COUNT * 2, this.tileEntityInventoryBasic, this));
			case 2:
				this.buttonList.add(new SlotShowingButton(2, this.guiLeft + 13, this.guiTop + 200, 20, 20,
						"", ContainerBasic.TE_INVENTORY_SLOT_COUNT, this.tileEntityInventoryBasic, this));
				this.buttonList.add(new SlotShowingButton(1, this.guiLeft + 13, this.guiTop + 176, 20, 20,
						"", 0, this.tileEntityInventoryBasic, this));
		}
	}


	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id >= 1 && button.id <= 4) {
			//Code snagged from Ellpeck's Actually Additions
			NBTTagCompound compound = new NBTTagCompound();
			BlockPos pos = this.tileEntityInventoryBasic.getPos();
			compound.setInteger("X", pos.getX());
			compound.setInteger("Y", pos.getY());
			compound.setInteger("Z", pos.getZ());
			compound.setInteger("WorldID", this.tileEntityInventoryBasic.getWorld().provider.getDimension());
			compound.setInteger("PlayerID", Minecraft.getMinecraft().player.getEntityId());
			compound.setInteger("ButtonID", button.id);
			StorageCrates.networkInstance.sendToServer(new PacketClientToServer(compound, PacketHandler.CRATE_CALLBACK));
		}
	}

	// draw the background for the GUI - rendered first
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {
		// Bind the image texture of our custom container
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		// Draw the image
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	// draw the foreground for the GUI - rendered after the slots, but before the dragged items and tooltips
	// renders relative to the top left corner of the background
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		final int LABEL_XPOS = 5;
		final int LABEL_YPOS = 5;
		fontRenderer.drawString(tileEntityInventoryBasic.getDisplayName().getUnformattedText(), LABEL_XPOS, LABEL_YPOS, Color.darkGray.getRGB());
	}

	/**
	 * From Actually Additions by Ellpeck
	 *
	 * I can see why this is the sole function of a superclass "GuiWtfMojang"
	 * Wtf, Mojang
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}
