package net.hibiscus.naturespirit.util;

import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteraction.InteractionMap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static net.minecraft.core.cauldron.CauldronInteraction.*;

public interface NSCauldronBehavior {

  InteractionMap MILK_CAULDRON_BEHAVIOR = newInteractionMap("milk");
  CauldronInteraction FILL_WITH_MILK = (state, world, pos, player, hand, stack) -> emptyBucket(
      world,
      pos,
      player,
      hand,
      stack,
      NSMiscBlocks.MILK_CAULDRON.defaultBlockState(),
      SoundEvents.BUCKET_EMPTY
  );
  InteractionMap CHEESE_CAULDRON_BEHAVIOR = newInteractionMap("cheese");
  CauldronInteraction FILL_WITH_CHEESE = (state, world, pos, player, hand, stack) -> emptyBucket(
      world,
      pos,
      player,
      hand,
      stack,
      NSMiscBlocks.CHEESE_CAULDRON.defaultBlockState(),
      SoundEvents.BUCKET_EMPTY
  );

  static void registerBehavior() {
    MILK_CAULDRON_BEHAVIOR.map().put(
        Items.BUCKET,
        (state, world, pos, player, hand, stack) -> fillBucket(state,
            world,
            pos,
            player,
            hand,
            stack,
            new ItemStack(Items.MILK_BUCKET),
            (statex) -> true,
            SoundEvents.COW_MILK
        )
    );
    addDefaultInteractions(MILK_CAULDRON_BEHAVIOR.map());
    CHEESE_CAULDRON_BEHAVIOR.map().put(
        Items.BUCKET,
        (state, world, pos, player, hand, stack) -> fillBucket(state,
            world,
            pos,
            player,
            hand,
            stack,
            new ItemStack(NSMiscBlocks.CHEESE_BUCKET),
            (statex) -> true,
            SoundEvents.BUCKET_FILL
        )
    );
    addDefaultInteractions(CHEESE_CAULDRON_BEHAVIOR.map());
  }


}
