package org.bergie.bergieTweaks.Mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentScreenHandler.class)
abstract public class EnchantmentScreenHandlerMixin extends ScreenHandler {
    @Shadow
    @Final
    public int[] enchantmentPower;

    protected EnchantmentScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Redirect(
            method = "onButtonClick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;isEmpty()Z",
                    ordinal = 0
            )
    )
    private boolean ignoreLapis(ItemStack instance) {
        return false;
    }
    @Redirect(
            method = "onButtonClick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getCount()I"
            )
    )
    private int enoughLapis(ItemStack instance)
    {
        return 3;
    }

    //redirect
    @Redirect(
            method = "method_17410",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/entity/player/PlayerEntity.applyEnchantmentCosts (Lnet/minecraft/item/ItemStack;I)V"
            )
    )
    private void costRequiredLevels(PlayerEntity instance, ItemStack enchantedItem, int experienceLevels, @Local(ordinal = 0, argsOnly = true) int i)
    {
        instance.applyEnchantmentCosts(enchantedItem, enchantmentPower[i]);
    }

    @Redirect(
            method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/EnchantmentScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;",
                    ordinal = 1
            )
    )
    private Slot removeLapisSlot(EnchantmentScreenHandler instance, Slot slot)
    {
        return null;
    }

    @ModifyArg(
            method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/EnchantmentScreenHandler$2;<init>(Lnet/minecraft/screen/EnchantmentScreenHandler;Lnet/minecraft/inventory/Inventory;III)V"
            ),
            index = 3
    )
    private int shiftSlot(int par3)
    {
        return par3 + 10;
    }

    @Inject(
            method = "getLapisCount",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void alwaysEnoughLapis(CallbackInfoReturnable<Integer> cir)
    {
        cir.setReturnValue(3);
    }
}
