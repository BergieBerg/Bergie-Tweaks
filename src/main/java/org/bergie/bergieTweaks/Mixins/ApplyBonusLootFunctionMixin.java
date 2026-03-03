package org.bergie.bergieTweaks.Mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "net.minecraft.loot.function.ApplyBonusLootFunction$BinomialWithBonusCount")
abstract public class ApplyBonusLootFunctionMixin{
    @ModifyVariable(method = "getValue", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    int setInitialValueToZero(int initialValue)
    {
        return 0;
    }
}
