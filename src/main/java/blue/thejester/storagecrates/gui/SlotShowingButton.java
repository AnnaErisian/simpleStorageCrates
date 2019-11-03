package blue.thejester.storagecrates.gui;

import blue.thejester.storagecrates.tileentity.TileEntityInventoryBasic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class SlotShowingButton extends GuiButton {

    private final int slot;
    private final TileEntityInventoryBasic tileEntity;
    private final GuiInventoryBasic inventory;

    public SlotShowingButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int slotNum, TileEntityInventoryBasic tileEntity, GuiInventoryBasic inventory)
    {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.slot = slotNum;
        this.tileEntity = tileEntity;
        this.inventory = inventory;
    }

    public SlotShowingButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {
        this(buttonId, x, y, widthIn, heightIn, buttonText, 0, null, null);
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        super.drawButton(mc, mouseX, mouseY, partialTicks);

        if(tileEntity != null && this.tileEntity.getSizeInventory() > this.slot) {
            final ItemStack itemstack = this.tileEntity.getStackInSlot(this.slot);
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.enableDepth();
            GlStateManager.translate(2.0F, 2.0F, 32.0F);
            this.zLevel = 200.0F;
            mc.getRenderItem().zLevel = 200.0F;
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(itemstack, this.x, this.y);
            if (this.hovered && !itemstack.isEmpty() && mc.player.inventory.getItemStack().isEmpty())
            {
                this.renderToolTip(mc, itemstack, mouseX, mouseY);
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.translate(-2.0F, -2.0F, -32.0F);
            this.zLevel = 0.0F;
            mc.getRenderItem().zLevel = 0.0F;
        }
    }

    protected void renderToolTip(Minecraft mc, ItemStack stack, int x, int y)
    {
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        net.minecraftforge.fml.client.config.GuiUtils.preItemToolTip(stack);
        this.drawHoveringText(mc, this.getItemToolTip(mc, stack), x, y, (font == null ? mc.fontRenderer : font));
        net.minecraftforge.fml.client.config.GuiUtils.postItemToolTip();
    }

    public List<String> getItemToolTip(Minecraft mc, ItemStack p_191927_1_)
    {
        List<String> list = p_191927_1_.getTooltip(mc.player, mc.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);

        for (int i = 0; i < list.size(); ++i)
        {
            if (i == 0)
            {
                list.set(i, p_191927_1_.getRarity().rarityColor + (String)list.get(i));
            }
            else
            {
                list.set(i, TextFormatting.GRAY + (String)list.get(i));
            }
        }

        return list;
    }

    protected void drawHoveringText(Minecraft mc, List<String> textLines, int x, int y, FontRenderer font)
    {
        net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(textLines, x, y, mc.currentScreen.width, mc.currentScreen.height, -1, font);
        if (false && !textLines.isEmpty())
        {
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            int i = 0;

            for (String s : textLines)
            {
                int j = mc.fontRenderer.getStringWidth(s);

                if (j > i)
                {
                    i = j;
                }
            }

            int l1 = x + 12;
            int i2 = y - 12;
            int k = 8;

            if (textLines.size() > 1)
            {
                k += 2 + (textLines.size() - 1) * 10;
            }

            if (l1 + i > mc.currentScreen.width)
            {
                l1 -= 28 + i;
            }

            if (i2 + k + 6 > mc.currentScreen.height)
            {
                i2 = mc.currentScreen.height - k - 6;
            }

            this.zLevel = 300.0F;
            mc.getRenderItem().zLevel = 300.0F;
            int l = -267386864;
            this.drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, -267386864, -267386864);
            this.drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, -267386864, -267386864);
            this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, -267386864, -267386864);
            this.drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, -267386864, -267386864);
            this.drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, -267386864, -267386864);
            int i1 = 1347420415;
            int j1 = 1344798847;
            this.drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, 1347420415, 1344798847);
            this.drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, 1347420415, 1344798847);
            this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, 1347420415, 1347420415);
            this.drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, 1344798847, 1344798847);

            for (int k1 = 0; k1 < textLines.size(); ++k1)
            {
                String s1 = textLines.get(k1);
                mc.fontRenderer.drawStringWithShadow(s1, (float)l1, (float)i2, -1);

                if (k1 == 0)
                {
                    i2 += 2;
                }

                i2 += 10;
            }

            this.zLevel = 0.0F;
            mc.getRenderItem().zLevel = 0.0F;
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableRescaleNormal();
        }
    }
}
