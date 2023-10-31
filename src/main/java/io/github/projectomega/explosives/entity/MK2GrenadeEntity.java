package io.github.projectomega.explosives.entity;

import io.github.projectomega.explosives.init.ModEntities;
import io.github.projectomega.explosives.init.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MK2GrenadeEntity extends ThrowableGrenadeEntity {

  public MK2GrenadeEntity(EntityType<? extends ThrowableItemEntity> entityType, Level level) {
    super(entityType, level);
  }

  public MK2GrenadeEntity(EntityType<? extends ThrowableItemEntity> entityType, Level level,
      LivingEntity entity) {
    super(entityType, level, entity);
    this.setShouldBounce(true);
    this.setGravityVelocity(0.05F);
    this.setItem(new ItemStack(ModItems.MK2_GRENADE.get()));
  }

  public MK2GrenadeEntity(Level level, LivingEntity entity, int timeLeft, float power) {
    super(ModEntities.THROWABLE_GRENADE.get(), level, entity);
    this.power = power;
    this.setMaxLife(timeLeft);
    this.setShouldBounce(true);
    this.setGravityVelocity(0.07F);
    this.setItem(new ItemStack(ModItems.MK2_GRENADE.get()));
  }

}
