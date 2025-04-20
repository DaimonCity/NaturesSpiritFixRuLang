package net.hibiscus.naturespirit.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrushItem.class)
public class BrushItemMixin {

  @Unique
  public long nextDustTime = 0;

  @Inject(method = "usageTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockEntity(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/entity/BlockEntity;"))
  private void injectCalciteClusterBrushing(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks, CallbackInfo ci, @Local BlockState blockState, @Local BlockPos blockPos, @Local BlockHitResult blockHitResult, @Local
      Player playerEntity) {
    if (blockState.is(NSMiscBlocks.SMALL_CALCITE_BUD) || blockState.is(NSMiscBlocks.LARGE_CALCITE_BUD) || blockState.is(NSMiscBlocks.CALCITE_CLUSTER)) {

      if (world.getGameTime() > nextDustTime) {
        nextDustTime = world.getGameTime() + 20L;
        ItemEntity itemEntity = getChalkPowder(world, blockPos);
        itemEntity.setDeltaMovement(Vec3.ZERO);
        world.addFreshEntity(itemEntity);
        if (world.getRandom().nextFloat() < .3) {
          if (blockState.is(NSMiscBlocks.SMALL_CALCITE_BUD)) world.setBlockAndUpdate(blockPos, blockState.getFluidState().createLegacyBlock());
          if (blockState.is(NSMiscBlocks.LARGE_CALCITE_BUD)) world.setBlockAndUpdate(blockPos, NSMiscBlocks.SMALL_CALCITE_BUD.withPropertiesOf(blockState));
          if (blockState.is(NSMiscBlocks.CALCITE_CLUSTER)) world.setBlockAndUpdate(blockPos, NSMiscBlocks.LARGE_CALCITE_BUD.withPropertiesOf(blockState));
          EquipmentSlot equipmentSlot = stack.equals(playerEntity.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
          stack.hurtAndBreak(1, user, equipmentSlot);
        }
      }
    }
  }

  @Unique
  private ItemEntity getChalkPowder(Level world, BlockPos blockPos) {
    double d = EntityType.ITEM.getWidth();
    double e = 1.0 - d;
    double f = d / 2.0;
    double g = (double) blockPos.getX() + 0.5 * e + f;
    double h = (double) blockPos.getY() + 0.25 + (double)(EntityType.ITEM.getHeight() / 2.0F);
    double i = (double) blockPos.getZ() + 0.5 * e + f;
    return new ItemEntity(world, g, h, i, new ItemStack(NSMiscBlocks.CHALK_POWDER, world.getRandom().nextIntBetweenInclusive(1, 3)));
  }

  @Inject(method = "useOnBlock", at = @At("HEAD"))
  private void useOnBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
    nextDustTime = context.getLevel().getGameTime() + 10L;
  }

}
