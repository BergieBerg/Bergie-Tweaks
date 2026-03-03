package org.bergie.bergieTweaks.Mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @WrapOperation(
            method = "tick",
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/entity/ItemEntity;tryMerge()V"
            )
    )
    void neverMerge(ItemEntity instance, Operation<Void> original)
    {
    }
}
