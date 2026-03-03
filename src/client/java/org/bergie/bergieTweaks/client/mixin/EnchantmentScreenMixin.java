package org.bergie.bergieTweaks.client.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.text.StringVisitable;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EnchantmentScreen.class)
abstract public class EnchantmentScreenMixin {
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/EnchantmentScreen;isPointWithinBounds(IIIIDD)Z"
            )
    )
    private boolean noClue(EnchantmentScreen instance, int i, int j, int k, int l, double v, double w)
    {
        return false;
    }

    @Redirect(
            method = "drawBackground",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V",
                    ordinal = 2
            )
    )
    private void noLevelDisabled(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height)
    {}

    @Redirect(
            method = "drawBackground",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V",
                    ordinal = 5
            )
    )
    private void noLevelEnabled(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height)
    {}

    @Redirect(
            method = "drawBackground",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawWrappedText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/StringVisitable;IIIIZ)V"
            )
    )
    private void shiftEnchantingLanguage(DrawContext instance, TextRenderer textRenderer, StringVisitable text, int x, int y, int width, int color, boolean shadow)
    {
        instance.drawWrappedText(textRenderer, text, x - 18, y, width, color, shadow);
    }

    @ModifyVariable(
            method = "drawBackground",
            at = @At("STORE"),
            ordinal = 9
    )
    private int moreEnchantingCharacters(int p)
    {
        return p + 18;
    }
}
