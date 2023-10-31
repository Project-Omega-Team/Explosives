package io.github.projectomega.explosives.item;

import io.github.projectomega.explosives.entity.ThrowableGrenadeEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/*
 * Author: Forked from MrCrayfishGunMod, continued by ProjectOmega developers
 */
public class GrenadeItem extends Item {
  private int maxCookTime;
  private float speed;

  public GrenadeItem(Properties properties, int maxCookTime, float power, float speed) {
    super(properties);
    this.maxCookTime = maxCookTime;
    this.speed = speed;
  }

  @Override
  public UseAnim getUseAnimation(ItemStack stack) {
    return UseAnim.BOW;
  }

  @Override
  public int getUseDuration(ItemStack stack) {
    return this.maxCookTime;
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player,
      InteractionHand interactionHand) {
    ItemStack stack = player.getItemInHand(interactionHand);
    player.startUsingItem(interactionHand);
    return InteractionResultHolder.consume(stack);
  }

  @Override
  public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
    if (this.canCook() && !level.isClientSide()) {
      if (!(entity instanceof Player) || !((Player) entity).isCreative()) {
        stack.shrink(1);
      }
      ThrowableGrenadeEntity grenade = this.createGrenadeEntity(level, entity, 0);
      grenade.onDeath();
    }
    return stack;
  }

  @Override
  public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
    if (!level.isClientSide()) {
      int duration = this.getUseDuration(stack) - timeLeft;
      if (duration >= 10) {
        if (!(entity instanceof Player) || !((Player) entity).isCreative()) {
          stack.shrink(1);
        }
        ThrowableGrenadeEntity grenade =
            this.createGrenadeEntity(level, entity, this.maxCookTime - duration);

        grenade.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F,
            Math.min(1.0F, duration / 20F) * this.speed, 1.5F);

        level.addFreshEntity(grenade);
        this.onThrown(level, grenade);
      }
    }
  }

  public boolean canCook() {
    return true;
  }

  public ThrowableGrenadeEntity createGrenadeEntity(Level level, LivingEntity entity,
      int timeLeft) {
    return null;
  };

  protected void onThrown(Level world, ThrowableGrenadeEntity entity) {}
}
