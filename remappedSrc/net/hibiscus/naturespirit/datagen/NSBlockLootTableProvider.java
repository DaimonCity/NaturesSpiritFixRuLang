package net.hibiscus.naturespirit.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.hibiscus.naturespirit.blocks.AzollaBlock;
import net.hibiscus.naturespirit.registration.NSColoredBlocks;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSRegistryHelper;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.hibiscus.naturespirit.registration.sets.FlowerSet;
import net.hibiscus.naturespirit.registration.sets.StoneSet;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static net.hibiscus.naturespirit.registration.NSMiscBlocks.*;

class NSBlockLootTableProvider extends FabricBlockLootTableProvider {

  private final float[] SAPLING_DROP_CHANCE_2 = new float[]{0.4F, 0.4533333333F, 0.625F, 0.758F};

  private final static float[] LEAVES_STICK_DROP_CHANCE = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};

  protected NSBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(dataOutput, registryLookup);
  }

  private void addWoodTable(HashMap<String, WoodSet> woods) {
    for (WoodSet woodSet : woods.values()) {

      if (woodSet.hasBark()) {
        dropSelf(woodSet.getWood());
        dropSelf(woodSet.getStrippedWood());
      }
      if (woodSet.hasMosaic()) {
        dropSelf(woodSet.getMosaic());
        dropSelf(woodSet.getMosaicStairs());
        this.add(woodSet.getMosaicSlab(), this::createSlabItemTable);
      }
      if (woodSet.getWoodPreset() == WoodSet.WoodPreset.JOSHUA) {
        dropSelf(woodSet.getBundle());
        dropSelf(woodSet.getStrippedBundle());
      }
      dropSelf(woodSet.getLog());
      dropSelf(woodSet.getStrippedLog());
      dropSelf(woodSet.getPlanks());
      dropSelf(woodSet.getButton());
      this.add(woodSet.getDoor(), this::createDoorTable);
      dropSelf(woodSet.getStairs());
      this.add(woodSet.getSlab(), this::createSlabItemTable);
      dropSelf(woodSet.getFence());
      dropSelf(woodSet.getTrapDoor());
      dropSelf(woodSet.getSign());
      dropSelf(woodSet.getHangingSign());
      dropSelf(woodSet.getPressurePlate());
      dropSelf(woodSet.getFenceGate());
    }
  }

  private void addStoneTable(HashMap<String, StoneSet> stones) {
    for (StoneSet stoneSet : stones.values()) {

      if (stoneSet.hasTiles()) {
        dropSelf(stoneSet.getTiles());
        dropSelf(stoneSet.getTilesStairs());
        this.add(stoneSet.getTilesSlab(), this::createSlabItemTable);
        dropSelf(stoneSet.getTilesWall());
        if (stoneSet.hasCracked()) {
          dropSelf(stoneSet.getCrackedTiles());
        }
      }
      if (stoneSet.hasMossy()) {
        dropSelf(stoneSet.getMossyBricks());
        dropSelf(stoneSet.getMossyBricksStairs());
        this.add(stoneSet.getMossyBricksSlab(), this::createSlabItemTable);
        dropSelf(stoneSet.getMossyBricksWall());
      }
      if (stoneSet.hasCracked()) {
        dropSelf(stoneSet.getCrackedBricks());
      }
      if (stoneSet.hasCobbled()) {
        dropSelf(stoneSet.getCobbled());
        dropSelf(stoneSet.getCobbledStairs());
        this.add(stoneSet.getCobbledSlab(), this::createSlabItemTable);
        dropSelf(stoneSet.getCobbledWall());
        this.add(stoneSet.getBase(), (block) -> this.createSingleItemTableWithSilkTouch(block, stoneSet.getCobbled()));
        if (stoneSet.hasMossy()) {
          dropSelf(stoneSet.getMossyCobbled());
          dropSelf(stoneSet.getMossyCobbledStairs());
          this.add(stoneSet.getMossyCobbledSlab(), this::createSlabItemTable);
          dropSelf(stoneSet.getMossyCobbledWall());
        }
      } else {
        dropSelf(stoneSet.getBase());
      }
      dropSelf(stoneSet.getBaseStairs());
      this.add(stoneSet.getBaseSlab(), this::createSlabItemTable);

      dropSelf(stoneSet.getBricks());
      dropSelf(stoneSet.getBricksStairs());
      this.add(stoneSet.getBricksSlab(), this::createSlabItemTable);
      dropSelf(stoneSet.getBricksWall());
      dropSelf(stoneSet.getPolished());
      dropSelf(stoneSet.getPolishedStairs());
      this.add(stoneSet.getPolishedSlab(), this::createSlabItemTable);
      dropSelf(stoneSet.getPolishedWall());
    }
  }

  private void addFlowerTable(HashMap<String, FlowerSet> flowers) {
    for (FlowerSet flowerSet : flowers.values()) {
      if (flowerSet.isTall()) {
        this.add(flowerSet.getFlowerBlock(), (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
      } else {
        this.dropSelf(flowerSet.getFlowerBlock());
        dropPottedContents(flowerSet.getPottedFlowerBlock());
      }
    }
  }

  public final LootTable.Builder dropsWithSilkTouchOrShears2(ItemLike drop) {
    return LootTable.lootTable()
        .withPool(LootPool.lootPool().when(this.hasShearsOrSilkTouch()).setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(drop)));
  }

  public LootTable.Builder noSaplingLeavesDrop(Block leaves) {
    HolderLookup.RegistryLookup<Enchantment> impl = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
    return this.dropsWithSilkTouchOrShears2(leaves)
        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).when(this.doesNotHaveShearsOrSilkTouch()).add(
            ((LootPoolSingletonContainer.Builder) this.applyExplosionDecay(leaves,
                LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F))))).when(
                BonusLevelTableCondition.bonusLevelFlatChance(impl.getOrThrow(Enchantments.FORTUNE), LEAVES_STICK_DROP_CHANCE))));
  }

  public LootTable.Builder flowerbedDropsWithShears(Block flowerbed) {
    return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(this.applyExplosionDecay(flowerbed, LootItem.lootTableItem(flowerbed).apply(
        IntStream.rangeClosed(1, 4).boxed().toList(), (flowerAmount) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) flowerAmount)).when(
                LootItemBlockStatePropertyCondition
                    .hasBlockStateProperties(flowerbed).setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(AzollaBlock.AMOUNT, flowerAmount)))))).when(this.hasShearsOrSilkTouch()));
  }

  private void addTreeTable(HashMap<String, Block[]> saplings, HashMap<String, Block> leaves) {
    for (String i : leaves.keySet()) {
      Block leavesType = leaves.get(i);
      if (saplings.get(i) != null) {
        Block[] saplingType = saplings.get(i);
        dropSelf(saplingType[0]);
        dropPottedContents(saplingType[1]);
        if (i.equals("joshua")) {
          this.add(leavesType, (block) -> this.createLeavesDrops(block, saplingType[0], SAPLING_DROP_CHANCE_2));
        } else {
          this.add(leavesType, (block) -> this.createLeavesDrops(block, saplingType[0], NORMAL_LEAVES_SAPLING_CHANCES));
        }
      } else if (i.equals("yellow_aspen")) {
        this.add(leavesType, (block) -> this.createLeavesDrops(block, NSWoods.ASPEN.getSapling(), NORMAL_LEAVES_SAPLING_CHANCES));
      } else {
        this.add(leavesType, this::noSaplingLeavesDrop);
      }
    }
  }

  @Override
  public void generate() {
    addFlowerTable(NSRegistryHelper.FlowerHashMap);
    addStoneTable(NSRegistryHelper.StoneHashMap);
    addWoodTable(NSRegistryHelper.WoodHashMap);
    addTreeTable(NSRegistryHelper.SaplingHashMap, NSRegistryHelper.LeavesHashMap);
    this.add(NSWoods.OLIVE_BRANCH, BlockLootSubProvider::createShearsOnlyDrop);

    HolderLookup.RegistryLookup<Enchantment> impl = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
    this.add(CALCITE_CLUSTER, (block) -> createSilkTouchDispatchTable(block,
        LootItem.lootTableItem(CALCITE_SHARD)
            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4F)))
            .apply(ApplyBonusCount.addOreBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))
            .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES)))
            .otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(CALCITE_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2F)))))
    ));
    this.dropWhenSilkTouch(SMALL_CALCITE_BUD);
    this.dropWhenSilkTouch(LARGE_CALCITE_BUD);

    this.add(CATTAIL, (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));

    addNetherVinesDropTable(NSWoods.WISTERIA.getWhiteVines(), NSWoods.WISTERIA.getWhiteVinesPlant());
    addNetherVinesDropTable(NSWoods.WISTERIA.getBlueVines(), NSWoods.WISTERIA.getBlueVinesPlant());
    addNetherVinesDropTable(NSWoods.WISTERIA.getPurpleVines(), NSWoods.WISTERIA.getPurpleVinesPlant());
    addNetherVinesDropTable(NSWoods.WISTERIA.getPinkVines(), NSWoods.WISTERIA.getPinkVinesPlant());
    addNetherVinesDropTable(NSWoods.WILLOW.getVines(), NSWoods.WILLOW.getVinesPlant());
    this.add(NSWoods.FIR.getFrostyLeaves(), createLeavesDrops(NSWoods.FIR.getFrostyLeaves(), NSWoods.FIR.getSapling(), NORMAL_LEAVES_SAPLING_CHANCES));
    this.add(NSWoods.REDWOOD.getFrostyLeaves(), createLeavesDrops(NSWoods.REDWOOD.getFrostyLeaves(), NSWoods.REDWOOD.getSapling(), NORMAL_LEAVES_SAPLING_CHANCES));
    this.dropSelf(ALLUAUDIA);
    this.dropSelf(ALLUAUDIA_BUNDLE);
    this.dropSelf(STRIPPED_ALLUAUDIA);
    this.dropSelf(STRIPPED_ALLUAUDIA_BUNDLE);

    this.add(AZOLLA, this::flowerbedDropsWithShears);

    this.add(CHERT_COAL_ORE, (Block block) -> this.createOreDrop(block, Items.COAL));
    this.add(CHERT_EMERALD_ORE, (Block block) -> this.createOreDrop(block, Items.EMERALD));
    this.add(CHERT_DIAMOND_ORE, (Block block) -> this.createOreDrop(block, Items.DIAMOND));
    this.add(CHERT_COPPER_ORE, this::createCopperOreDrops);
    this.add(CHERT_REDSTONE_ORE, this::createRedstoneOreDrops);
    this.add(CHERT_IRON_ORE, (Block block) -> this.createOreDrop(block, Items.RAW_IRON));
    this.add(CHERT_GOLD_ORE, (Block block) -> this.createOreDrop(block, Items.RAW_GOLD));
    this.add(CHERT_LAPIS_ORE, this::createLapisOreDrops);

    this.dropSelf(SHIITAKE_MUSHROOM);
    this.add(SHIITAKE_MUSHROOM_BLOCK, this.createMushroomBlockDrop(SHIITAKE_MUSHROOM_BLOCK, SHIITAKE_MUSHROOM));
    this.dropSelf(GRAY_POLYPORE);
    this.add(GRAY_POLYPORE_BLOCK, this.createMushroomBlockDrop(GRAY_POLYPORE_BLOCK, GRAY_POLYPORE));

    addNetherVinesDropTable(LOTUS_STEM, LOTUS_STEM);
    this.dropOther(LOTUS_FLOWER, LOTUS_FLOWER_ITEM);

    this.dropSelf(SHIITAKE_MUSHROOM);
    this.createMushroomBlockDrop(SHIITAKE_MUSHROOM_BLOCK, SHIITAKE_MUSHROOM);

    this.dropSelf(NSWoods.COCONUT_THATCH);
    this.dropSelf(NSWoods.COCONUT_THATCH_CARPET);
    this.dropSelf(NSWoods.COCONUT_THATCH_STAIRS);
    this.add(NSWoods.COCONUT_THATCH_SLAB, this::createSlabItemTable);

    this.dropSelf(NSWoods.EVERGREEN_THATCH);
    this.dropSelf(NSWoods.EVERGREEN_THATCH_CARPET);
    this.dropSelf(NSWoods.EVERGREEN_THATCH_STAIRS);
    this.add(NSWoods.EVERGREEN_THATCH_SLAB, this::createSlabItemTable);

    this.dropSelf(NSWoods.XERIC_THATCH);
    this.dropSelf(NSWoods.XERIC_THATCH_CARPET);
    this.dropSelf(NSWoods.XERIC_THATCH_STAIRS);
    this.add(NSWoods.XERIC_THATCH_SLAB, this::createSlabItemTable);

    this.add(PAPER_DOOR, this::createDoorTable);
    this.dropSelf(PAPER_TRAPDOOR);
    this.add(FRAMED_PAPER_DOOR, this::createDoorTable);
    this.dropSelf(FRAMED_PAPER_TRAPDOOR);
    this.add(BLOOMING_PAPER_DOOR, this::createDoorTable);
    this.dropSelf(BLOOMING_PAPER_TRAPDOOR);
    this.dropSelf(PAPER_BLOCK);
    this.dropSelf(BLOOMING_PAPER_BLOCK);
    this.dropSelf(FRAMED_PAPER_BLOCK);
    this.dropSelf(PAPER_SIGN);
    this.dropSelf(PAPER_HANGING_SIGN);
    this.dropSelf(PAPER_PANEL);
    this.dropSelf(BLOOMING_PAPER_PANEL);
    this.dropSelf(FRAMED_PAPER_PANEL);

    this.dropSelf(RED_MOSS_BLOCK);
    this.dropSelf(RED_MOSS_CARPET);
    this.dropSelf(SANDY_SOIL);

    this.dropSelf(ORNATE_SUCCULENT);
    this.dropSelf(DROWSY_SUCCULENT);
    this.dropSelf(AUREATE_SUCCULENT);
    this.dropSelf(SAGE_SUCCULENT);
    this.dropSelf(FOAMY_SUCCULENT);
    this.dropSelf(IMPERIAL_SUCCULENT);
    this.dropSelf(REGAL_SUCCULENT);

    dropPottedContents(POTTED_ORNATE_SUCCULENT);
    dropPottedContents(POTTED_DROWSY_SUCCULENT);
    dropPottedContents(POTTED_AUREATE_SUCCULENT);
    dropPottedContents(POTTED_SAGE_SUCCULENT);
    dropPottedContents(POTTED_FOAMY_SUCCULENT);
    dropPottedContents(POTTED_IMPERIAL_SUCCULENT);
    dropPottedContents(POTTED_REGAL_SUCCULENT);

    this.dropSelf(PINK_SAND);
    this.dropSelf(PINK_SANDSTONE);
    this.add(PINK_SANDSTONE_SLAB, this::createSlabItemTable);
    this.dropSelf(PINK_SANDSTONE_STAIRS);
    this.dropSelf(PINK_SANDSTONE_WALL);
    this.dropSelf(SMOOTH_PINK_SANDSTONE);
    this.add(SMOOTH_PINK_SANDSTONE_SLAB, this::createSlabItemTable);
    this.dropSelf(SMOOTH_PINK_SANDSTONE_STAIRS);
    this.dropSelf(CUT_PINK_SANDSTONE);
    this.add(CUT_PINK_SANDSTONE_SLAB, this::createSlabItemTable);
    this.dropSelf(CHISELED_PINK_SANDSTONE);

    this.dropSelf(NSColoredBlocks.KAOLIN);
    this.dropSelf(NSColoredBlocks.WHITE_KAOLIN);
    this.dropSelf(NSColoredBlocks.LIGHT_GRAY_KAOLIN);
    this.dropSelf(NSColoredBlocks.GRAY_KAOLIN);
    this.dropSelf(NSColoredBlocks.BLACK_KAOLIN);
    this.dropSelf(NSColoredBlocks.BROWN_KAOLIN);
    this.dropSelf(NSColoredBlocks.RED_KAOLIN);
    this.dropSelf(NSColoredBlocks.ORANGE_KAOLIN);
    this.dropSelf(NSColoredBlocks.YELLOW_KAOLIN);
    this.dropSelf(NSColoredBlocks.LIME_KAOLIN);
    this.dropSelf(NSColoredBlocks.GREEN_KAOLIN);
    this.dropSelf(NSColoredBlocks.CYAN_KAOLIN);
    this.dropSelf(NSColoredBlocks.LIGHT_BLUE_KAOLIN);
    this.dropSelf(NSColoredBlocks.BLUE_KAOLIN);
    this.dropSelf(NSColoredBlocks.PURPLE_KAOLIN);
    this.dropSelf(NSColoredBlocks.MAGENTA_KAOLIN);
    this.dropSelf(NSColoredBlocks.PINK_KAOLIN);
    this.add(NSColoredBlocks.KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.WHITE_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.LIGHT_GRAY_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.GRAY_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.BLACK_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.BROWN_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.RED_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.ORANGE_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.YELLOW_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.LIME_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.GREEN_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.CYAN_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.LIGHT_BLUE_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.BLUE_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.PURPLE_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.MAGENTA_KAOLIN_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.PINK_KAOLIN_SLAB, this::createSlabItemTable);
    this.dropSelf(NSColoredBlocks.KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.WHITE_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.LIGHT_GRAY_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.GRAY_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.BLACK_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.BROWN_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.RED_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.ORANGE_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.YELLOW_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.LIME_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.GREEN_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.CYAN_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.LIGHT_BLUE_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.BLUE_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.PURPLE_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.MAGENTA_KAOLIN_STAIRS);
    this.dropSelf(NSColoredBlocks.PINK_KAOLIN_STAIRS);

    this.dropSelf(NSColoredBlocks.KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.WHITE_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.GRAY_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.BLACK_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.BROWN_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.RED_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.ORANGE_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.YELLOW_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.LIME_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.GREEN_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.CYAN_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.BLUE_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.PURPLE_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.MAGENTA_KAOLIN_BRICKS);
    this.dropSelf(NSColoredBlocks.PINK_KAOLIN_BRICKS);
    this.add(NSColoredBlocks.KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.WHITE_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.GRAY_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.BLACK_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.BROWN_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.RED_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.ORANGE_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.YELLOW_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.LIME_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.GREEN_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.CYAN_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.BLUE_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.PURPLE_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.MAGENTA_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.PINK_KAOLIN_BRICK_SLAB, this::createSlabItemTable);
    this.dropSelf(NSColoredBlocks.KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.WHITE_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.GRAY_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.BLACK_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.BROWN_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.RED_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.ORANGE_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.YELLOW_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.LIME_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.GREEN_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.CYAN_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.BLUE_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.PURPLE_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.MAGENTA_KAOLIN_BRICK_STAIRS);
    this.dropSelf(NSColoredBlocks.PINK_KAOLIN_BRICK_STAIRS);

    this.dropSelf(NSColoredBlocks.PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.WHITE_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.LIGHT_GRAY_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.GRAY_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.BLACK_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.BROWN_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.RED_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.ORANGE_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.YELLOW_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.LIME_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.GREEN_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.CYAN_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.LIGHT_BLUE_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.BLUE_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.PURPLE_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.MAGENTA_PAPER_LANTERN);
    this.dropSelf(NSColoredBlocks.PINK_PAPER_LANTERN);

    this.dropSelf(NSColoredBlocks.WHITE_CHALK);
    this.dropSelf(NSColoredBlocks.LIGHT_GRAY_CHALK);
    this.dropSelf(NSColoredBlocks.GRAY_CHALK);
    this.dropSelf(NSColoredBlocks.BLACK_CHALK);
    this.dropSelf(NSColoredBlocks.BROWN_CHALK);
    this.dropSelf(NSColoredBlocks.RED_CHALK);
    this.dropSelf(NSColoredBlocks.ORANGE_CHALK);
    this.dropSelf(NSColoredBlocks.YELLOW_CHALK);
    this.dropSelf(NSColoredBlocks.LIME_CHALK);
    this.dropSelf(NSColoredBlocks.GREEN_CHALK);
    this.dropSelf(NSColoredBlocks.CYAN_CHALK);
    this.dropSelf(NSColoredBlocks.LIGHT_BLUE_CHALK);
    this.dropSelf(NSColoredBlocks.BLUE_CHALK);
    this.dropSelf(NSColoredBlocks.PURPLE_CHALK);
    this.dropSelf(NSColoredBlocks.MAGENTA_CHALK);
    this.dropSelf(NSColoredBlocks.PINK_CHALK);
    this.add(NSColoredBlocks.WHITE_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.LIGHT_GRAY_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.GRAY_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.BLACK_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.BROWN_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.RED_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.ORANGE_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.YELLOW_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.LIME_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.GREEN_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.CYAN_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.LIGHT_BLUE_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.BLUE_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.PURPLE_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.MAGENTA_CHALK_SLAB, this::createSlabItemTable);
    this.add(NSColoredBlocks.PINK_CHALK_SLAB, this::createSlabItemTable);
    this.dropSelf(NSColoredBlocks.WHITE_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.LIGHT_GRAY_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.GRAY_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.BLACK_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.BROWN_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.RED_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.ORANGE_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.YELLOW_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.LIME_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.GREEN_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.CYAN_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.LIGHT_BLUE_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.BLUE_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.PURPLE_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.MAGENTA_CHALK_STAIRS);
    this.dropSelf(NSColoredBlocks.PINK_CHALK_STAIRS);

    this.dropSelf(NSMiscBlocks.DESERT_TURNIP_ROOT_BLOCK);

    this.dropSelf(NSWoods.COCONUT_SPROUT);
    this.dropSelf(NSWoods.COCONUT_BLOCK);

    this.add(FRIGID_GRASS, this::createGrassDrops);
    this.add(SCORCHED_GRASS, this::createGrassDrops);
    this.add(BEACH_GRASS, this::createGrassDrops);
    this.add(SEDGE_GRASS, this::createGrassDrops);
    this.add(FLAXEN_FERN, this::createGrassDrops);
    this.add(OAT_GRASS, this::createGrassDrops);
    this.add(LUSH_FERN, this::createGrassDrops);
    this.add(MELIC_GRASS, this::createGrassDrops);
    this.add(RED_BEARBERRIES, this::createGrassDrops);
    this.add(RED_BITTER_SPROUTS, this::createGrassDrops);
    this.add(GREEN_BEARBERRIES, this::createGrassDrops);
    this.add(GREEN_BITTER_SPROUTS, this::createGrassDrops);
    this.add(PURPLE_BEARBERRIES, this::createGrassDrops);
    this.add(PURPLE_BITTER_SPROUTS, this::createGrassDrops);

    this.add(TALL_FRIGID_GRASS, createDoublePlantWithSeedDrops(TALL_FRIGID_GRASS, FRIGID_GRASS));
    this.add(TALL_SCORCHED_GRASS, createDoublePlantWithSeedDrops(TALL_SCORCHED_GRASS, SCORCHED_GRASS));
    this.add(TALL_BEACH_GRASS, createDoublePlantWithSeedDrops(TALL_BEACH_GRASS, BEACH_GRASS));
    this.add(TALL_SEDGE_GRASS, createDoublePlantWithSeedDrops(TALL_SEDGE_GRASS, SEDGE_GRASS));
    this.add(LARGE_FLAXEN_FERN, createDoublePlantWithSeedDrops(LARGE_FLAXEN_FERN, FLAXEN_FERN));
    this.add(TALL_OAT_GRASS, createDoublePlantWithSeedDrops(TALL_OAT_GRASS, OAT_GRASS));
    this.add(LARGE_LUSH_FERN, createDoublePlantWithSeedDrops(LARGE_LUSH_FERN, LUSH_FERN));
    this.add(TALL_MELIC_GRASS, createDoublePlantWithSeedDrops(TALL_MELIC_GRASS, MELIC_GRASS));

    dropPottedContents(POTTED_PURPLE_BEARBERRIES);
    dropPottedContents(POTTED_GREEN_BEARBERRIES);
    dropPottedContents(POTTED_RED_BEARBERRIES);
    dropPottedContents(POTTED_MELIC_GRASS);
    dropPottedContents(POTTED_OAT_GRASS);
    dropPottedContents(POTTED_LUSH_FERN);
    dropPottedContents(POTTED_FLAXEN_FERN);
    dropPottedContents(POTTED_SEDGE_GRASS);
    dropPottedContents(POTTED_SCORCHED_GRASS);
    dropPottedContents(POTTED_FRIGID_GRASS);
    dropPottedContents(POTTED_BEACH_GRASS);

  }
}
