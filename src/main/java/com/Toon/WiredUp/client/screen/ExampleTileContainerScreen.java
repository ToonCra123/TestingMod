package com.Toon.WiredUp.client.screen;

import com.Toon.WiredUp.Containers.ExampleTileContainer;
import com.Toon.WiredUp.WiredUp;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExampleTileContainerScreen extends ContainerScreen<ExampleTileContainer> {
    private static final ResourceLocation GUI_LOCATION = new ResourceLocation(WiredUp.MODID, "textures/gui/example_gui.png");
    public ExampleTileContainerScreen(ExampleTileContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 175;
        this.ySize = 201;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), (float) this.playerInventoryTitleX,
                (float) this.playerInventoryTitleY, 4210752);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.textureManager.bindTexture(GUI_LOCATION);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);

        float energy = this.container.getEnergyNormalizedStored();

        this.blit(matrixStack, x + 124, y + 10 + 67 - Math.round(energy), 176, 67 - Math.round(energy), 20,  Math.round(energy));

        if (this.container.te.isBurning()) {
            int i = this.guiLeft;
            int j = this.guiTop;

            int k = Math.round(this.container.getFuelNormalized());
            this.blit(matrixStack, i + 81, j + 32 - k, 176, 81 - k, 14, k);
        }
    }

    private static int findInverseOfSmelting(int x) {
        return 14 - x;
    }

    @Override
    public void tick() {
        super.tick();
    }
}
