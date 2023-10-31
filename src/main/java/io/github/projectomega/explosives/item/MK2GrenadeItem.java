package io.github.projectomega.explosives.item;

import io.github.projectomega.explosives.entity.MK2GrenadeEntity;
import io.github.projectomega.explosives.entity.ThrowableGrenadeEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class MK2GrenadeItem extends GrenadeItem {
  private float power;

  public MK2GrenadeItem(Properties properties, int maxCookTime, float power, float speed) {
    super(properties, maxCookTime, power, speed);
    this.power = power;
  }

  @Override
  public ThrowableGrenadeEntity createGrenadeEntity(Level level, LivingEntity entity,
      int timeLeft) {
    return new MK2GrenadeEntity(level, entity, timeLeft, this.power);
  }

  @Override
  public boolean canCook() {
    return true;
  }

}
