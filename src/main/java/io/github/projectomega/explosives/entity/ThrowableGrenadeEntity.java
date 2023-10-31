package io.github.projectomega.explosives.entity;

import io.github.projectomega.explosives.init.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;

/*
 * Author: Forked from MrCrayfishGunMod, continued by ProjectOmega developers
 */
public class ThrowableGrenadeEntity extends ThrowableItemEntity {

  public float rotation;
  public float previousRotation;
  public float power;

  public ThrowableGrenadeEntity(EntityType<? extends ThrowableItemEntity> entityType, Level level) {
    super(entityType, level);
  }

  public ThrowableGrenadeEntity(EntityType<? extends ThrowableItemEntity> entityType, Level level,
      LivingEntity entity) {
    super(entityType, level, entity);
  }

  public ThrowableGrenadeEntity(Level level, LivingEntity entity, int timeLeft, float power) {
    super(ModEntities.THROWABLE_GRENADE.get(), level, entity);
    this.power = power;
    this.setMaxLife(timeLeft);
  }

  @Override
  public void tick() {
    super.tick();
    this.previousRotation = this.rotation;
    double speed = this.getDeltaMovement().length();
    if (speed > 0.1) {
      this.rotation += speed * 50;
    }
  }

  @Override
  public void onDeath() {
    this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.power, false,
        ExplosionInteraction.BLOCK);
  }

  @Override
  protected void defineSynchedData() {}
}
