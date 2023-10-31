package io.github.projectomega.explosives.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

/*
 * Author: Forked from MrCrayfishGunMod, continued by ProjectOmega developers
 */
public abstract class ThrowableItemEntity extends ThrowableProjectile
    implements IEntityAdditionalSpawnData {
  private ItemStack item = ItemStack.EMPTY;
  private boolean shouldBounce;
  private float gravityVelocity = 0.03F;
  private int maxLife = 20 * 10;

  public ThrowableItemEntity(EntityType<? extends ThrowableItemEntity> entityType, Level level) {
    super(entityType, level);
  }

  public ThrowableItemEntity(EntityType<? extends ThrowableItemEntity> entityType, Level level,
      LivingEntity entity) {
    super(entityType, entity, level);
  }

  public ThrowableItemEntity(EntityType<? extends ThrowableItemEntity> entityType, Level level,
      double x, double y, double z) {
    super(entityType, x, y, z, level);
  }

  public void setItem(ItemStack item) {
    this.item = item;
  }

  public ItemStack getItem() {
    return this.item;
  }

  protected void setShouldBounce(boolean shouldBounce) {
    this.shouldBounce = shouldBounce;
  }

  protected void setGravityVelocity(float gravity) {
    this.gravityVelocity = gravity;
  }

  @Override
  protected float getGravity() {
    return this.gravityVelocity;
  }

  public void setMaxLife(int maxLife) {
    this.maxLife = maxLife;
  }

  @Override
  public void tick() {
    super.tick();
    if (this.shouldBounce && this.tickCount >= this.maxLife) {
      this.remove(RemovalReason.KILLED);
      this.onDeath();
    }
  }

  public void onDeath() {}

  @Override
  protected void onHit(HitResult result) {
    switch (result.getType()) {
      case BLOCK:
        BlockHitResult blockHitResult = (BlockHitResult) result;
        if (this.shouldBounce) {
          BlockPos blockPos = blockHitResult.getBlockPos();
          BlockState blockState = this.level().getBlockState(blockPos);
          SoundEvent soundEvent = blockState.getBlock()
              .getSoundType(blockState, this.level(), blockPos, this).getStepSound();

          double speed = this.getDeltaMovement().length();
          if (speed > 0.1) {
            this.level().playSound(null, result.getLocation().x, result.getLocation().y,
                result.getLocation().z, soundEvent, SoundSource.AMBIENT, 1.0F, 1.0F);
          }
          this.bounce(blockHitResult.getDirection());
        } else {
          this.remove(RemovalReason.KILLED);
          this.onDeath();
        }
        break;
      case ENTITY:
        EntityHitResult entityResult = (EntityHitResult) result;
        Entity entity = entityResult.getEntity();
        if (this.shouldBounce) {
          double s = this.getDeltaMovement().length();
          if (s > 0.1) {
            entity.hurt(entity.damageSources().thrown(this, this.getOwner()), 1.0F);
          }
          this.bounce(Direction.getNearest(this.getDeltaMovement().x(), this.getDeltaMovement().y(),
              this.getDeltaMovement().z()).getOpposite());
          this.setDeltaMovement(this.getDeltaMovement().multiply(0.15, 1.0, 0.15));
        } else {
          this.remove(RemovalReason.KILLED);
          this.onDeath();
        }
        break;
      default:
        break;
    }
  }

  private void bounce(Direction direction) {
    switch (direction.getAxis()) {
      case X:
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.3, 0.6, 0.6));
        break;
      case Y:
        this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, -0.15, 0.6));
        if (this.getDeltaMovement().y() < this.getGravity()) {
          this.setDeltaMovement(this.getDeltaMovement().multiply(1, 0, 1));
        }
        break;
      case Z:
        this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 0.6, -0.3));
        break;
    }
  }

  @Override
  public boolean isNoGravity() {
    return false;
  }

  @Override
  public void writeSpawnData(FriendlyByteBuf buffer) {
    buffer.writeBoolean(this.shouldBounce);
    buffer.writeFloat(this.gravityVelocity);
    buffer.writeItem(this.item);
  }

  @Override
  public void readSpawnData(FriendlyByteBuf buffer) {
    this.shouldBounce = buffer.readBoolean();
    this.gravityVelocity = buffer.readFloat();
    this.item = buffer.readItem();
  }

  @Override
  public Packet<ClientGamePacketListener> getAddEntityPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
  }
}
