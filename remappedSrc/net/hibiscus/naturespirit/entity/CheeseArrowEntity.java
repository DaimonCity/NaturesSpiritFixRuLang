package net.hibiscus.naturespirit.entity;

import net.hibiscus.naturespirit.registration.NSEntityTypes;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CheeseArrowEntity extends AbstractArrow {

  public CheeseArrowEntity(EntityType<? extends CheeseArrowEntity> entityType, Level world) {
    super(entityType, world);
  }

  public CheeseArrowEntity(Level world, LivingEntity owner, ItemStack stack, @Nullable ItemStack weapon) {
    super(NSEntityTypes.CHEESE_ARROW, owner, world, stack, weapon);
  }

  public CheeseArrowEntity(double x, double y, double z, Level world, ItemStack stack, @Nullable ItemStack weapon) {
    super(NSEntityTypes.CHEESE_ARROW, x, y, z, world, stack, weapon);
  }

  public void tick() {
    super.tick();
    if (this.level().isClientSide && !this.inGround) {
      this.level().addParticle(NSParticleTypes.MILK_PARTICLE, this.getX(), this.getY(), this.getZ(), 0D, 0D, 0D);
    }

  }

  @Override
  protected ItemStack getDefaultPickupItem() {
    return new ItemStack(NSMiscBlocks.CHEESE_ARROW);
  }

  protected void doPostHurtEffects(LivingEntity target) {
    super.doPostHurtEffects(target);
    target.removeAllEffects();
  }

  public void readAdditionalSaveData(CompoundTag nbt) {
    super.readAdditionalSaveData(nbt);
  }

  public void addAdditionalSaveData(CompoundTag nbt) {
    super.addAdditionalSaveData(nbt);
  }
}
