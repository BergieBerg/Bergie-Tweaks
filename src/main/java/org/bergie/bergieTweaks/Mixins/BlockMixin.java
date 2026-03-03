package org.bergie.bergieTweaks.Mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.bergie.bergieTweaks.BergieTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin( Block.class )
public class BlockMixin {
    @Inject(
            method = "dropStack(Lnet/minecraft/world/World;Ljava/util/function/Supplier;Lnet/minecraft/item/ItemStack;)V",
            at = @At(
                    shift = At.Shift.BEFORE,
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
            )
    )
    private static void SplitBlockDrop(World world, Supplier<ItemEntity> itemEntitySupplier, ItemStack stack, CallbackInfo ci, @Local ItemEntity itementity)
    {
        BergieTweaks.splitItemEntity(world, itementity, world::spawnEntity);
    }
}
