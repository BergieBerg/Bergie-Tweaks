package org.bergie.bergieTweaks.client.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(net.minecraft.client.gui.screens.inventory.EnchantmentScreen.class)
abstract public class EnchantmentScreenMixin {
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/inventory/EnchantmentScreen;isHovering(IIIIDD)Z"
            )
    )
    private boolean noClue(EnchantmentScreen instance, int i, int j, int k, int l, double v, double w)
    {
        return false;
    }

    @Redirect(
            method = "renderBg",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
                    ordinal = 2
            )
    )
    private void noLevelDisabled(GuiGraphics instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height)
    {}

    @Redirect(
            method = "renderBg",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
                    ordinal = 5
            )
    )
    private void noLevelEnabled(GuiGraphics instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height)
    {}

    @Redirect(
            method = "renderBg",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawWordWrap(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/FormattedText;IIIIZ)V"
            )
    )
    private void shiftEnchantingLanguage(GuiGraphics instance, Font font, FormattedText text, int x, int y, int width, int color, boolean shadow)
    {
        instance.drawWordWrap(font, text, x - 18, y, width, color, shadow);
    }

    @ModifyVariable(
            method = "renderBg",
            at = @At("STORE"),
            ordinal = 9
    )
    private int moreEnchantingCharacters(int p)
    {
        return p + 18;
    }
}
