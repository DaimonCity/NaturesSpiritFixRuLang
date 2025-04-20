package net.hibiscus.naturespirit.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.hibiscus.naturespirit.registration.NSRegistryHelper;
import net.hibiscus.naturespirit.registration.NSTags;
import net.hibiscus.naturespirit.registration.NSTags.Blocks;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.hibiscus.naturespirit.registration.sets.FlowerSet;
import net.hibiscus.naturespirit.registration.sets.StoneSet;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.hibiscus.naturespirit.registration.sets.WoodSet.WoodPreset;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static net.hibiscus.naturespirit.registration.NSMiscBlocks.*;

public class NSBlockTagGenerator extends FabricTagProvider.BlockTagProvider {

  public NSBlockTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  private void addWoodTags(HashMap<String, WoodSet> woods) {
    for (WoodSet woodSet : woods.values()) {

      tag(BlockTags.PLANKS).add(new Block[]{woodSet.getPlanks()});
      tag(BlockTags.WOODEN_BUTTONS).add(new Block[]{woodSet.getButton()});
      tag(BlockTags.WOODEN_DOORS).add(new Block[]{woodSet.getDoor()});
      tag(BlockTags.WOODEN_STAIRS).add(new Block[]{woodSet.getStairs()});
      tag(BlockTags.WOODEN_SLABS).add(new Block[]{woodSet.getSlab()});
      tag(BlockTags.WOODEN_FENCES).add(new Block[]{woodSet.getFence()});
      tag(woodSet.getBlockLogsTag()).add(woodSet.getStrippedLog(), woodSet.getLog());
      tag(NSTags.Blocks.STRIPPED_LOGS).add(woodSet.getStrippedLog());
      tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(new Block[]{woodSet.getLog()});
      tag(BlockTags.LOGS_THAT_BURN).addTag(woodSet.getBlockLogsTag());
      if (woodSet.getWoodPreset() == WoodPreset.JOSHUA) {
        tag(woodSet.getBlockLogsTag()).add(woodSet.getStrippedBundle(), woodSet.getBundle());
        tag(NSTags.Blocks.STRIPPED_LOGS).add(woodSet.getStrippedBundle());
      }
      if (woodSet.hasBark()) {
        tag(woodSet.getBlockLogsTag()).add(woodSet.getStrippedWood(), woodSet.getWood());
        tag(NSTags.Blocks.STRIPPED_LOGS).add(woodSet.getStrippedWood());
      }
      if (woodSet.hasMosaic()) {
        tag(BlockTags.SLABS).add(woodSet.getMosaicSlab());
        tag(BlockTags.STAIRS).add(woodSet.getMosaicStairs());
        tag(BlockTags.MINEABLE_WITH_AXE).add(woodSet.getMosaic(), woodSet.getMosaicSlab(), woodSet.getMosaicStairs());
      }
      if (woodSet.getWoodPreset() == WoodSet.WoodPreset.WILLOW) {
        tag(BlockTags.CLIMBABLE).add(
            woodSet.getVinesPlant(),
            woodSet.getVines()
        );
      }
      tag(BlockTags.WOODEN_TRAPDOORS).add(new Block[]{woodSet.getTrapDoor()});
      tag(BlockTags.STANDING_SIGNS).add(new Block[]{woodSet.getSign()});
      tag(BlockTags.WALL_SIGNS).add(new Block[]{woodSet.getWallSign()});
      tag(BlockTags.WOODEN_PRESSURE_PLATES).add(new Block[]{woodSet.getPressurePlate()});
      tag(BlockTags.FENCE_GATES).add(new Block[]{woodSet.getFenceGate()});
      tag(BlockTags.CEILING_HANGING_SIGNS).add(woodSet.getHangingSign());
      tag(BlockTags.WALL_HANGING_SIGNS).add(woodSet.getHangingWallSign());

    }
  }

  private void addStoneTags(HashMap<String, StoneSet> stones) {
    for (StoneSet stoneSet : stones.values()) {

      tag(BlockTags.MINEABLE_WITH_PICKAXE).add(stoneSet.getRegisteredBlocksList().toArray(new Block[]{}));
      if (stoneSet.hasTiles()) {
        tag(BlockTags.SLABS).add(stoneSet.getTilesSlab());
        tag(BlockTags.STAIRS).add(stoneSet.getTilesStairs());
        tag(BlockTags.WALLS).add(stoneSet.getTilesWall());
      }
      if (stoneSet.hasCobbled()) {
        tag(BlockTags.SLABS).add(stoneSet.getCobbledSlab());
        tag(BlockTags.STAIRS).add(stoneSet.getCobbledStairs());
        tag(BlockTags.WALLS).add(stoneSet.getCobbledWall());
        if (stoneSet.hasMossy()) {
          tag(BlockTags.SLABS).add(stoneSet.getMossyCobbledSlab());
          tag(BlockTags.STAIRS).add(stoneSet.getMossyCobbledStairs());
          tag(BlockTags.WALLS).add(stoneSet.getMossyCobbledWall());
        }
      }
      if (stoneSet.hasMossy()) {
        tag(BlockTags.SLABS).add(stoneSet.getMossyBricksSlab());
        tag(BlockTags.STAIRS).add(stoneSet.getMossyBricksStairs());
        tag(BlockTags.WALLS).add(stoneSet.getMossyBricksWall());
      }
      tag(BlockTags.SLABS).add(stoneSet.getPolishedSlab());
      tag(BlockTags.STAIRS).add(stoneSet.getPolishedStairs());
      tag(BlockTags.WALLS).add(stoneSet.getPolishedWall());
      tag(BlockTags.SLABS).add(stoneSet.getBricksSlab());
      tag(BlockTags.STAIRS).add(stoneSet.getBricksStairs());
      tag(BlockTags.WALLS).add(stoneSet.getBricksWall());
      tag(BlockTags.SLABS).add(stoneSet.getBaseSlab());
      tag(BlockTags.STAIRS).add(stoneSet.getBaseStairs());

    }
  }

  private void addTreeTags(HashMap<String, Block[]> saplings, HashMap<String, Block> leaves) {
    for (String i : leaves.keySet()) {
      Block leavesType = leaves.get(i);
      tag(BlockTags.MINEABLE_WITH_HOE).add(leavesType);
      tag(BlockTags.LEAVES).add(leavesType);
      if (!Objects.equals(i, "wisteria") && !Objects.equals(i, "coconut") && !i.startsWith("part") && !i.startsWith("frosty") && !Objects.equals(i, "yellow_aspen")) {
        Block[] saplingType = saplings.get(i);
        tag(BlockTags.SAPLINGS).add(new Block[]{saplingType[0]});
        tag(BlockTags.FLOWER_POTS).add(new Block[]{saplingType[1]});
      }
    }
  }

  @Override
  protected void addTags(HolderLookup.Provider arg) {
    addWoodTags(NSRegistryHelper.WoodHashMap);
    addStoneTags(NSRegistryHelper.StoneHashMap);
    addTreeTags(NSRegistryHelper.SaplingHashMap, NSRegistryHelper.LeavesHashMap);
    for (FlowerSet flowerSet : NSRegistryHelper.FlowerHashMap.values()) {
      if (flowerSet.isTall()) {
        tag(BlockTags.TALL_FLOWERS).add(flowerSet.getFlowerBlock());
      } else {
        tag(BlockTags.FLOWER_POTS).add(flowerSet.getPottedFlowerBlock());
        tag(BlockTags.SMALL_FLOWERS).add(flowerSet.getFlowerBlock());
      }
      tag(BlockTags.SWORD_EFFICIENT).add(flowerSet.getFlowerBlock());
      tag(BlockTags.REPLACEABLE_BY_TREES).add(flowerSet.getFlowerBlock());
    }
    tag(BlockTags.REPLACEABLE).add(AZOLLA);
    tag(BlockTags.TALL_FLOWERS).add(CATTAIL);
    tag(BlockTags.FLOWERS).add(LOTUS_FLOWER,
        NSWoods.WISTERIA.getBlueVines(),
        NSWoods.WISTERIA.getBlueVinesPlant(),
        NSWoods.WISTERIA.getWhiteVines(),
        NSWoods.WISTERIA.getWhiteVinesPlant(),
        NSWoods.WISTERIA.getPinkVines(),
        NSWoods.WISTERIA.getPinkVinesPlant(),
        NSWoods.WISTERIA.getPurpleVines(),
        NSWoods.WISTERIA.getPurpleVinesPlant(),
        NSWoods.WISTERIA.getBlueLeaves(),
        NSWoods.WISTERIA.getWhiteLeaves(),
        NSWoods.WISTERIA.getPinkLeaves(),
        NSWoods.WISTERIA.getPurpleLeaves()
    );
    tag(Blocks.ALLUAUDIA_BLOCKS).add(STRIPPED_ALLUAUDIA_BUNDLE, ALLUAUDIA_BUNDLE, ALLUAUDIA, STRIPPED_ALLUAUDIA);
    tag(BlockTags.MINEABLE_WITH_AXE).addTag(Blocks.ALLUAUDIA_BLOCKS);
    tag(BlockTags.WOODEN_DOORS).add(PAPER_DOOR, FRAMED_PAPER_DOOR, BLOOMING_PAPER_DOOR);
    tag(BlockTags.WOODEN_TRAPDOORS).add(PAPER_TRAPDOOR, FRAMED_PAPER_TRAPDOOR, BLOOMING_PAPER_TRAPDOOR);
    tag(BlockTags.CLIMBABLE).add(
        NSWoods.WISTERIA.getBlueVines(),
        NSWoods.WISTERIA.getBlueVinesPlant(),
        NSWoods.WISTERIA.getWhiteVines(),
        NSWoods.WISTERIA.getWhiteVinesPlant(),
        NSWoods.WISTERIA.getPinkVines(),
        NSWoods.WISTERIA.getPinkVinesPlant(),
        NSWoods.WISTERIA.getPurpleVines(),
        NSWoods.WISTERIA.getPurpleVinesPlant(),
        NSWoods.WILLOW.getVinesPlant(),
        NSWoods.WILLOW.getVines()
    );
    tag(BlockTags.BEE_GROWABLES).add(
        NSWoods.WISTERIA.getBlueVines(),
        NSWoods.WISTERIA.getBlueVinesPlant(),
        NSWoods.WISTERIA.getWhiteVines(),
        NSWoods.WISTERIA.getWhiteVinesPlant(),
        NSWoods.WISTERIA.getPinkVines(),
        NSWoods.WISTERIA.getPinkVinesPlant(),
        NSWoods.WISTERIA.getPurpleVines(),
        NSWoods.WISTERIA.getPurpleVinesPlant(),
        LOTUS_FLOWER
    );
    tag(BlockTags.DEAD_BUSH_MAY_PLACE_ON).forceAddTag(Blocks.KAOLIN);
    tag(BlockTags.DIRT).add(SANDY_SOIL, CHERT.getBase(), RED_MOSS_BLOCK);
    tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(PINK_SANDSTONE, RED_MOSS_BLOCK).forceAddTag(Blocks.KAOLIN).forceAddTag(Blocks.KAOLIN);
    tag(BlockTags.CROPS).add(DESERT_TURNIP_STEM);
    tag(BlockTags.MAINTAINS_FARMLAND).add(DESERT_TURNIP_STEM);
    tag(BlockTags.MINEABLE_WITH_HOE).add(
        SCORCHED_GRASS,
        TALL_SCORCHED_GRASS,
        BEACH_GRASS,
        TALL_BEACH_GRASS,
        SEDGE_GRASS,
        TALL_SEDGE_GRASS,
        LARGE_FLAXEN_FERN,
        FLAXEN_FERN,
        LARGE_LUSH_FERN,
        LUSH_FERN,
        TALL_MELIC_GRASS,
        MELIC_GRASS,
        FRIGID_GRASS,
        TALL_FRIGID_GRASS,
        OAT_GRASS,
        TALL_OAT_GRASS,
        RED_BEARBERRIES,
        GREEN_BEARBERRIES,
        PURPLE_BEARBERRIES,
        RED_BITTER_SPROUTS,
        PURPLE_BITTER_SPROUTS,
        GREEN_BITTER_SPROUTS,
        NSWoods.COCONUT_THATCH,
        NSWoods.COCONUT_THATCH_STAIRS,
        NSWoods.COCONUT_THATCH_CARPET,
        NSWoods.COCONUT_THATCH_SLAB,
        NSWoods.EVERGREEN_THATCH,
        NSWoods.EVERGREEN_THATCH_STAIRS,
        NSWoods.EVERGREEN_THATCH_CARPET,
        NSWoods.EVERGREEN_THATCH_SLAB,
        NSWoods.XERIC_THATCH,
        NSWoods.XERIC_THATCH_STAIRS,
        NSWoods.XERIC_THATCH_CARPET,
        NSWoods.XERIC_THATCH_SLAB,
        RED_MOSS_BLOCK,
        RED_MOSS_CARPET, ORNATE_SUCCULENT, AUREATE_SUCCULENT, SAGE_SUCCULENT, FOAMY_SUCCULENT, IMPERIAL_SUCCULENT, REGAL_SUCCULENT
    );
    tag(BlockTags.SWORD_EFFICIENT).add(
        SCORCHED_GRASS,
        TALL_SCORCHED_GRASS,
        BEACH_GRASS,
        TALL_BEACH_GRASS,
        SEDGE_GRASS,
        TALL_SEDGE_GRASS,
        LARGE_FLAXEN_FERN,
        FLAXEN_FERN,
        SHIITAKE_MUSHROOM,
        GRAY_POLYPORE,
        FRIGID_GRASS,
        TALL_FRIGID_GRASS,
        OAT_GRASS,
        TALL_OAT_GRASS,
        LARGE_LUSH_FERN,
        LUSH_FERN,
        TALL_MELIC_GRASS,
        MELIC_GRASS,
        RED_BEARBERRIES,
        GREEN_BEARBERRIES,
        PURPLE_BEARBERRIES,
        RED_BITTER_SPROUTS,
        PURPLE_BITTER_SPROUTS,
        GREEN_BITTER_SPROUTS
    );
    tag(BlockTags.REPLACEABLE_BY_TREES).add(
        SCORCHED_GRASS,
        TALL_SCORCHED_GRASS,
        BEACH_GRASS,
        TALL_BEACH_GRASS,
        SEDGE_GRASS,
        TALL_SEDGE_GRASS,
        LARGE_FLAXEN_FERN,
        FLAXEN_FERN,
        FRIGID_GRASS,
        TALL_FRIGID_GRASS,
        OAT_GRASS,
        TALL_OAT_GRASS,
        LARGE_LUSH_FERN,
        LUSH_FERN,
        TALL_MELIC_GRASS,
        MELIC_GRASS, AUREATE_SUCCULENT, SAGE_SUCCULENT, FOAMY_SUCCULENT, IMPERIAL_SUCCULENT, REGAL_SUCCULENT
    );
    tag(BlockTags.SAND).add(PINK_SAND, SANDY_SOIL);
    tag(BlockTags.SMELTS_TO_GLASS).add(PINK_SAND);
    tag(BlockTags.MINEABLE_WITH_SHOVEL).add(PINK_SAND, SANDY_SOIL);
    tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .forceAddTag(NSTags.Blocks.KAOLIN)
        .forceAddTag(NSTags.Blocks.KAOLIN_STAIRS)
        .forceAddTag(NSTags.Blocks.KAOLIN_SLABS)
        .forceAddTag(NSTags.Blocks.KAOLIN_BRICKS)
        .forceAddTag(NSTags.Blocks.KAOLIN_BRICK_STAIRS)
        .forceAddTag(NSTags.Blocks.KAOLIN_BRICK_SLABS)
        .forceAddTag(NSTags.Blocks.CHALK)
        .forceAddTag(NSTags.Blocks.CHALK_STAIRS)
        .forceAddTag(NSTags.Blocks.CHALK_SLABS)
        .add(CHERT_COAL_ORE, CHERT_COPPER_ORE, CHERT_DIAMOND_ORE, CHERT_GOLD_ORE, CHERT_EMERALD_ORE, CHERT_IRON_ORE, CHERT_LAPIS_ORE, CHERT_REDSTONE_ORE, PINK_SANDSTONE,
            SMOOTH_PINK_SANDSTONE, CUT_PINK_SANDSTONE, PINK_SANDSTONE_STAIRS, SMOOTH_PINK_SANDSTONE_STAIRS, PINK_SANDSTONE_SLAB, SMOOTH_PINK_SANDSTONE_SLAB,
            CUT_PINK_SANDSTONE_SLAB, PINK_SANDSTONE_WALL, CHISELED_PINK_SANDSTONE);
    tag(BlockTags.STAIRS).add(PINK_SANDSTONE_STAIRS, SMOOTH_PINK_SANDSTONE_STAIRS, NSWoods.EVERGREEN_THATCH_STAIRS, NSWoods.COCONUT_THATCH_STAIRS);
    tag(BlockTags.SLABS).add(PINK_SANDSTONE_SLAB, SMOOTH_PINK_SANDSTONE_SLAB, CUT_PINK_SANDSTONE_SLAB, NSWoods.EVERGREEN_THATCH_SLAB,
        NSWoods.COCONUT_THATCH_SLAB);
    tag(BlockTags.WALLS).add(PINK_SANDSTONE_WALL);
    tag(BlockTags.CAULDRONS).add(CHEESE_CAULDRON, MILK_CAULDRON);
    tag(BlockTags.FLOWER_POTS).add(
        POTTED_MELIC_GRASS,
        POTTED_FLAXEN_FERN,
        POTTED_FRIGID_GRASS,
        POTTED_SHIITAKE_MUSHROOM,
        POTTED_BEACH_GRASS,
        POTTED_SEDGE_GRASS,
        POTTED_SCORCHED_GRASS,
        POTTED_OAT_GRASS,
        POTTED_LUSH_FERN,
        POTTED_ORNATE_SUCCULENT,
        POTTED_DROWSY_SUCCULENT,
        POTTED_AUREATE_SUCCULENT,
        POTTED_SAGE_SUCCULENT,
        POTTED_FOAMY_SUCCULENT,
        POTTED_IMPERIAL_SUCCULENT);
    tag(BlockTags.ENDERMAN_HOLDABLE).add(SHIITAKE_MUSHROOM);
    tag(BlockTags.MINEABLE_WITH_AXE).add(SHIITAKE_MUSHROOM, SHIITAKE_MUSHROOM_BLOCK, GRAY_POLYPORE, GRAY_POLYPORE_BLOCK, DESERT_TURNIP_BLOCK, DESERT_TURNIP_ROOT_BLOCK,
        DESERT_TURNIP_STEM, PAPER_BLOCK, PAPER_PANEL, PAPER_DOOR, PAPER_SIGN, PAPER_WALL_SIGN, PAPER_HANGING_SIGN, PAPER_WALL_HANGING_SIGN, FRAMED_PAPER_BLOCK, FRAMED_PAPER_PANEL,
        FRAMED_PAPER_DOOR, FRAMED_PAPER_TRAPDOOR, BLOOMING_PAPER_BLOCK, BLOOMING_PAPER_DOOR, BLOOMING_PAPER_TRAPDOOR, BLOOMING_PAPER_PANEL);
    tag(BlockTags.CEILING_HANGING_SIGNS).add(PAPER_HANGING_SIGN);
    tag(BlockTags.WALL_HANGING_SIGNS).add(PAPER_WALL_HANGING_SIGN);
    tag(BlockTags.STANDING_SIGNS).add(new Block[]{PAPER_SIGN});
    tag(BlockTags.WALL_SIGNS).add(new Block[]{PAPER_WALL_SIGN});
    tag(BlockTags.COAL_ORES).add(CHERT_COAL_ORE);
    tag(BlockTags.COPPER_ORES).add(CHERT_COPPER_ORE);
    tag(BlockTags.DIAMOND_ORES).add(CHERT_DIAMOND_ORE);
    tag(BlockTags.GOLD_ORES).add(CHERT_GOLD_ORE);
    tag(BlockTags.EMERALD_ORES).add(CHERT_EMERALD_ORE);
    tag(BlockTags.IRON_ORES).add(CHERT_IRON_ORE);
    tag(BlockTags.LAPIS_ORES).add(CHERT_LAPIS_ORE);
    tag(BlockTags.REDSTONE_ORES).add(CHERT_REDSTONE_ORE);
    tag(BlockTags.NEEDS_IRON_TOOL).add(CHERT_EMERALD_ORE, CHERT_DIAMOND_ORE, CHERT_GOLD_ORE, CHERT_REDSTONE_ORE);
    tag(BlockTags.NEEDS_STONE_TOOL).add(CHERT_COPPER_ORE, CHERT_IRON_ORE, CHERT_LAPIS_ORE);
    tag(BlockTags.STONE_ORE_REPLACEABLES).add(TRAVERTINE.getBase());
    tag(BlockTags.BASE_STONE_OVERWORLD).add(TRAVERTINE.getBase());
    tag(BlockTags.BASE_STONE_OVERWORLD).add(CHERT.getBase());
    tag(BlockTags.STAIRS).forceAddTag(NSTags.Blocks.CHALK_STAIRS).forceAddTag(NSTags.Blocks.KAOLIN_STAIRS).forceAddTag(NSTags.Blocks.KAOLIN_BRICK_STAIRS)
        .add(PINK_SANDSTONE_STAIRS, SMOOTH_PINK_SANDSTONE_STAIRS);
    tag(BlockTags.SLABS).forceAddTag(NSTags.Blocks.CHALK_SLABS).forceAddTag(NSTags.Blocks.KAOLIN_SLABS).forceAddTag(NSTags.Blocks.KAOLIN_SLABS)
        .add(PINK_SANDSTONE_SLAB, SMOOTH_PINK_SANDSTONE_SLAB, CUT_PINK_SANDSTONE_SLAB);
    tag(BlockTags.WALLS).add(PINK_SANDSTONE_WALL);
  }
}
