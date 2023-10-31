// package io.github.projectomega.explosives.entity;

// import io.github.projectomega.explosives.init.ModEntities;
// import net.minecraft.core.BlockPos;
// import net.minecraft.core.Direction;
// import net.minecraft.network.FriendlyByteBuf;
// import net.minecraft.network.protocol.Packet;
// import net.minecraft.network.protocol.game.ClientGamePacketListener;
// import net.minecraft.sounds.SoundEvent;
// import net.minecraft.sounds.SoundSource;
// import net.minecraft.world.entity.Entity;
// import net.minecraft.world.entity.EntityType;
// import net.minecraft.world.entity.LivingEntity;
// import net.minecraft.world.entity.player.Player;
// import net.minecraft.world.entity.projectile.ThrowableProjectile;
// import net.minecraft.world.item.ItemStack;
// import net.minecraft.world.level.Level;
// import net.minecraft.world.level.Level.ExplosionInteraction;
// import net.minecraft.world.level.block.state.BlockState;
// import net.minecraft.world.phys.BlockHitResult;
// import net.minecraft.world.phys.EntityHitResult;
// import net.minecraft.world.phys.HitResult;
// import net.minecraftforge.entity.IEntityAdditionalSpawnData;
// import net.minecraftforge.network.NetworkHooks;

// public class ThrowableGrenadeEntity extends ThrowableProjectile
// implements IEntityAdditionalSpawnData {
// private ItemStack item = ItemStack.EMPTY;
// private boolean shouldBounce;
// private float gravityVelocity = 0.08f;
// private int maxLife;
// public float rotation;
// public float prevRotation;


// public ThrowableGrenadeEntity(EntityType<? extends ThrowableProjectile> entityType, Level level)
// {
// super(entityType, level);
// }

// public ThrowableGrenadeEntity(EntityType<? extends ThrowableProjectile> entityType, double x,
// double y, double z, Level level) {
// super(entityType, x, y, z, level);
// }

// public ThrowableGrenadeEntity(EntityType<? extends ThrowableProjectile> entityType,
// LivingEntity player, Level level) {
// super(entityType, player, level);
// }

// public ThrowableGrenadeEntity(Level level, Player player, boolean shouldBounce, ItemStack item,
// int maxLife) {
// super(ModEntities.GRENADE_PROJECTILE.get(), player, level);
// this.shouldBounce = shouldBounce;
// this.item = item;
// this.maxLife = maxLife;
// }

// @Override
// protected void defineSynchedData() {}

// protected void setShouldBounce(boolean shouldBounce) {
// this.shouldBounce = shouldBounce;
// }

// protected void setGravityVelocity(float gravity) {
// this.gravityVelocity = gravity;
// }

// @Override
// protected float getGravity() {
// return this.gravityVelocity;
// }

// @Override
// public void tick() {
// super.tick();

// if (this.shouldBounce && this.tickCount >= this.maxLife) {
// this.remove(RemovalReason.KILLED);
// this.onDeath();
// }
// this.prevRotation = this.rotation;
// double speed = this.getDeltaMovement().length();

// if (speed > 0.1) {
// this.rotation += speed * 50;
// }
// // if (this.level().isClientSide()) {
// // this.level().addParticle(ParticleTypes.SMOKE, true, this.getX(), this.getY(), this.getZ(), 0,
// // 0, 0);
// // }
// }

// public void onDeath() {
// if (!this.level().isClientSide()) {
// return;
// }
// // this.level().broadcastEntityEvent(this, (byte) 3);
// this.level().explode(this, this.getX(), this.getY(), this.getZ(), 5f, false,
// ExplosionInteraction.BLOCK);

// }

// @Override
// protected void onHit(HitResult result) {
// switch (result.getType()) {
// case BLOCK:
// BlockHitResult blockHitResult = (BlockHitResult) result;
// if (this.shouldBounce) {
// BlockPos blockPos = blockHitResult.getBlockPos();
// BlockState blockState = this.level().getBlockState(blockPos);
// SoundEvent soundEvent = blockState.getBlock()
// .getSoundType(blockState, this.level(), blockPos, this).getStepSound();
// double speed = this.getDeltaMovement().length();
// if (speed > 0.1) {
// this.level().playSound(null, result.getLocation().x, result.getLocation().y,
// result.getLocation().z, soundEvent, SoundSource.AMBIENT, 1.0F, 1.0F);
// }
// this.bounce(blockHitResult.getDirection());
// } else {
// this.remove(RemovalReason.KILLED);
// this.onDeath();
// }
// break;
// case ENTITY:
// EntityHitResult entityResult = (EntityHitResult) result;
// Entity entity = entityResult.getEntity();
// if (this.shouldBounce) {
// double s = this.getDeltaMovement().length();
// if (s > 0.1) {
// entity.hurt(entity.damageSources().thrown(this, this.getOwner()), 1.0F);
// }
// this.bounce(Direction.getNearest(this.getDeltaMovement().x(), this.getDeltaMovement().y(),
// this.getDeltaMovement().z()).getOpposite());
// this.setDeltaMovement(this.getDeltaMovement().multiply(0.15, 1.0, 0.15));
// } else {
// this.remove(RemovalReason.KILLED);
// this.onDeath();
// }
// break;
// default:
// break;
// }
// }

// private void bounce(Direction direction) {
// switch (direction.getAxis()) {
// case X:
// this.setDeltaMovement(this.getDeltaMovement().multiply(-0.3, 0.6, 0.6));
// break;
// case Y:
// this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, -0.15, 0.6));
// if (this.getDeltaMovement().y() < this.getGravity()) {
// this.setDeltaMovement(this.getDeltaMovement().multiply(1, 0, 1));
// }
// break;
// case Z:
// this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 0.6, -0.3));
// break;
// }
// }

// @Override
// public boolean isNoGravity() {
// return false;
// }

// public void setItem(ItemStack item) {
// this.item = item;
// }

// public ItemStack getItem() {
// return this.item;
// }

// @Override
// public void writeSpawnData(FriendlyByteBuf buffer) {
// buffer.writeBoolean(this.shouldBounce);
// buffer.writeFloat(this.gravityVelocity);
// buffer.writeItem(this.item);
// buffer.writeInt(this.maxLife);
// }

// @Override
// public void readSpawnData(FriendlyByteBuf buffer) {
// this.shouldBounce = buffer.readBoolean();
// this.gravityVelocity = buffer.readFloat();
// this.item = buffer.readItem();
// this.maxLife = buffer.readInt();
// }

// @Override
// public Packet<ClientGamePacketListener> getAddEntityPacket() {
// return NetworkHooks.getEntitySpawningPacket(this);
// }

// }
