package net.hibiscus.naturespirit.util;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.hibiscus.naturespirit.blocks.BranchingTrunkBlock;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class NSEvents {

  public static void registerEvents() {
    UseBlockCallback.EVENT.register(((player, world, hand, hitResult) -> {
      BlockPos blockPos = hitResult.getBlockPos();
      BlockState blockState = world.getBlockState(blockPos);
      if ((blockState.is(NSWoods.JOSHUA.getLog()) || blockState.is(NSMiscBlocks.ALLUAUDIA)) && player.getItemInHand(hand).is(ItemTags.AXES)) {
        Block block = blockState.is(NSWoods.JOSHUA.getLog()) ? NSWoods.JOSHUA.getStrippedLog() : NSMiscBlocks.STRIPPED_ALLUAUDIA;
        BlockState blockState2 = block.defaultBlockState().setValue(BranchingTrunkBlock.DOWN, blockState.getValue(BranchingTrunkBlock.DOWN)).setValue(
            BranchingTrunkBlock.UP,
            blockState.getValue(BranchingTrunkBlock.UP)
        ).setValue(BranchingTrunkBlock.NORTH, blockState.getValue(BranchingTrunkBlock.NORTH)).setValue(
            BranchingTrunkBlock.SOUTH,
            blockState.getValue(BranchingTrunkBlock.SOUTH)
        ).setValue(BranchingTrunkBlock.EAST, blockState.getValue(BranchingTrunkBlock.EAST)).setValue(BranchingTrunkBlock.WEST, blockState.getValue(BranchingTrunkBlock.WEST)).setValue(
            BranchingTrunkBlock.WATERLOGGED,
            blockState.getValue(BranchingTrunkBlock.WATERLOGGED)
        ).setValue(BranchingTrunkBlock.SHEARED, blockState.getValue(BranchingTrunkBlock.SHEARED));
        world.playSound(player, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

        if (player instanceof ServerPlayer) {
          CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockPos, player.getItemInHand(hand));
        }

        world.setBlock(blockPos, blockState2, 11);
        world.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, blockState2));
        if (player != null) {
          player.getItemInHand(hand).hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
        }

        return InteractionResult.sidedSuccess(world.isClientSide);
      }
      if (blockState.is(BlockTags.CAULDRONS) && player.getItemInHand(hand).is(Items.MILK_BUCKET) && !blockState.is(NSMiscBlocks.MILK_CAULDRON)) {
        world.playSound(player, blockPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (player instanceof ServerPlayer) {
          CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockPos, player.getItemInHand(hand));
        }

        world.setBlock(blockPos, NSMiscBlocks.MILK_CAULDRON.defaultBlockState(), 11);
        world.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, NSMiscBlocks.MILK_CAULDRON.defaultBlockState()));
        if (!player.isCreative() && !player.isSpectator()) {
          player.setItemInHand(hand, new ItemStack(Items.BUCKET));
        }

        return InteractionResult.sidedSuccess(world.isClientSide);
      }
      if (blockState.is(BlockTags.CAULDRONS) && player.getItemInHand(hand).is(NSMiscBlocks.CHEESE_BUCKET) && !blockState.is(NSMiscBlocks.CHEESE_CAULDRON)) {
        world.playSound(player, blockPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (player instanceof ServerPlayer) {
          CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockPos, player.getItemInHand(hand));
        }

        world.setBlock(blockPos, NSMiscBlocks.CHEESE_CAULDRON.defaultBlockState(), 11);
        world.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, NSMiscBlocks.CHEESE_CAULDRON.defaultBlockState()));
        if (!player.isCreative() && !player.isSpectator()) {
          player.setItemInHand(hand, new ItemStack(Items.BUCKET));
        }

        return InteractionResult.sidedSuccess(world.isClientSide);
      }
      return InteractionResult.PASS;
    }));

  }
}
