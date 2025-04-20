package net.hibiscus.naturespirit.blocks;

import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import java.util.Optional;

public class CheeseBlock extends CakeBlock implements BucketPickup {

  public CheeseBlock(Properties settings) {
    super(settings);
  }

  @Override
  public ItemStack pickupBlock(Player player, LevelAccessor world, BlockPos pos, BlockState state) {
    if (world.getBlockState(pos).getValue(BITES) == 0) {
      world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
      if (!world.isClientSide()) {
        world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
      }

      return new ItemStack(NSMiscBlocks.CHEESE_BUCKET);
    }
    return new ItemStack(Items.BUCKET);
  }
  protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    return stack.is(Items.BUCKET) && state.getValue(BITES) == 0 ? ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION : super.useItemOn(stack, state, world, pos, player, hand, hit);
  }
  @Override
  public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
    if (world.isClientSide) {
      if (eat(world, pos, state, player).consumesAction()) {
        return InteractionResult.SUCCESS;
      }

      if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
        return InteractionResult.CONSUME;
      }
    }

    return eat(world, pos, state, player);
  }

  protected static InteractionResult eat(LevelAccessor world, BlockPos pos, BlockState state, Player player) {
    if (!player.canEat(false)) {
      return InteractionResult.PASS;
    } else {
      player.awardStat(NatureSpirit.EAT_CHEESE);
      player.getFoodData().eat(2, 0.1F);
      int i = state.getValue(BITES);
      world.gameEvent(player, GameEvent.EAT, pos);
      if (i < 6) {
        world.setBlock(pos, state.setValue(BITES, i + 1), Block.UPDATE_ALL);
      } else {
        world.removeBlock(pos, false);
        world.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
      }

      return InteractionResult.SUCCESS;
    }
  }

  @Override
  public Optional<SoundEvent> getPickupSound() {
    return Optional.of(SoundEvents.BUCKET_FILL);
  }
}
