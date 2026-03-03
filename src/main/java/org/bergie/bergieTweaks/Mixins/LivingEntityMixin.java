package org.bergie.bergieTweaks.Mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.bergie.bergieTweaks.BergieTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyArg(
            index = 2,
            method = "generateLoot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootWorldContext;JLjava/util/function/Consumer;)V"
            )
    )
    private Consumer<ItemStack> modifyDropConsumer(Consumer<ItemStack> consumer)
    {
        return itemStack -> BergieTweaks.splitStack(itemStack, consumer);
    }
}
