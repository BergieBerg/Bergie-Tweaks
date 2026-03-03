package org.bergie.bergieTweaks;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class BergieTweaks implements ModInitializer {

    @Override
    public void onInitialize() {
    }

    //Credit to Nostalgic Tweaks:
    //https://github.com/Nostalgica-Reverie/Nostalgic-Tweaks/blob/1.21.4/common/src/main/java/mod/adrenix/nostalgic/helper/candy/ItemHelper.java
    public static void splitStack(ItemStack itemStack, Consumer<ItemStack> consumer)
    {
        int count = itemStack.getCount();
        ItemStack instance = itemStack.copy();
        instance.setCount(1);
        for(int i = 0; i < count; i++)
            consumer.accept(instance);
    }

    //Credit to Nostalgic Tweaks:
    //https://github.com/Nostalgica-Reverie/Nostalgic-Tweaks/blob/1.21.4/common/src/main/java/mod/adrenix/nostalgic/helper/candy/ItemHelper.java
    public static void splitItemEntity(World world, ItemEntity itementity, Consumer<ItemEntity> consumer)
    {
        int count = itementity.getStack().getCount();
        itementity.getStack().setCount(1);
        itementity.setToDefaultPickupDelay();

        for (int i = 0; i < Math.max(0, count - 1); i++)
        {
            double x = (double)((float) itementity.getX() + 0.01F) + MathHelper.nextDouble(world.random, -0.04, 0.04);
            double y = (double)((float) itementity.getY() + 0.01F) + MathHelper.nextDouble(world.random, -0.04, 0.04) - (double) EntityType.ITEM.getHeight() / 2.0F;
            double z = (double)((float) itementity.getZ() + 0.01F) + MathHelper.nextDouble(world.random, -0.04, 0.04);

            ItemEntity newitementity = new ItemEntity(world, x, y, z, itementity.getStack());
            newitementity.setToDefaultPickupDelay();
            consumer.accept(newitementity);
        }
    }
}
