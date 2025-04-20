package net.hibiscus.naturespirit.registration.sets;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.blocks.*;
import net.hibiscus.naturespirit.datagen.NSConfiguredFeatures;
import net.hibiscus.naturespirit.registration.NSBoatTypes;
import net.hibiscus.naturespirit.registration.NSParticleTypes;
import net.minecraft.block.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.item.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static net.hibiscus.naturespirit.registration.NSRegistryHelper.*;

public class WoodSet {

  private final ItemLike leavesBefore;
  private final ItemLike saplingBefore;
  private final ItemLike logBefore;
  private final ItemLike signBefore;
  private final ItemLike boatBefore;
  private final ItemLike buttonBefore;
  private final List<Block> registeredBlocksList = new ArrayList<>();
  private final List<Item> registeredItemsList = new ArrayList<>();
  private final ResourceLocation name;
  private final MapColor sideColor;
  private final MapColor topColor;
  private final WoodPreset woodPreset;
  private BlockSetType blockSetType;
  private WoodType woodType;
  private Block log;
  private Block strippedLog;
  private Block bundle;
  private Block strippedBundle;
  private Block wood;
  private Block strippedWood;
  private Block leaves;
  private Block frostyLeaves;
  private Block sapling;
  private Block pottedSapling;
  private Block redLeaves;
  private Block redSapling;
  private Block pottedRedSapling;
  private Block orangeLeaves;
  private Block orangeSapling;
  private Block pottedOrangeSapling;
  private Block yellowLeaves;
  private Block yellowSapling;
  private Block pottedYellowSapling;
  private Block blueLeaves;
  private Block partBlueLeaves;
  private Block blueSapling;
  private Block pottedBlueSapling;
  private Block purpleLeaves;
  private Block partPurpleLeaves;
  private Block purpleSapling;
  private Block pottedPurpleSapling;
  private Block pinkLeaves;
  private Block partPinkLeaves;
  private Block pinkSapling;
  private Block pottedPinkSapling;
  private Block whiteLeaves;
  private Block partWhiteLeaves;
  private Block whiteSapling;
  private Block pottedWhiteSapling;
  private Block vines;
  private Block vinesPlant;
  private Block blueVines;
  private Block purpleVines;
  private Block pinkVines;
  private Block whiteVines;
  private Block blueVinesPlant;
  private Block purpleVinesPlant;
  private Block pinkVinesPlant;
  private Block whiteVinesPlant;
  private Block planks;
  private Block stairs;
  private Block slab;
  private Block mosaic;
  private Block mosaicStairs;
  private Block mosaicSlab;
  private Block fence;
  private Block fenceGate;
  private Block pressurePlate;
  private Block button;
  private Block door;
  private Block trapDoor;
  private Block sign;
  private Block wallSign;
  private Block hangingSign;
  private Block hangingWallSign;
  private Item signItem;
  private Item hangingSignItem;
  private Item boatItem;
  private Item chestBoatItem;
  private TagKey<Item> itemLogsTag;
  private TagKey<Block> blockLogsTag;
  private final Supplier<Boat.Type> boatTypeSupplier;
  private TreeGrower saplingGenerator;
  private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> configuredFeature;
  private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> configuredFeature2;
  private boolean hasLargeTree;
  private final boolean hasMosaic;
  private final List<Block> leavesList = new ArrayList<>();
  private final List<Block> saplingList = new ArrayList<>();

  private void registerWood() {
    blockSetType = createBlockSetType();
    woodType = new WoodTypeBuilder().register(getNameID(), blockSetType);

    log = woodPreset == WoodPreset.JOSHUA ? createJoshuaLog() : createLog();
    strippedLog = woodPreset == WoodPreset.JOSHUA ? createStrippedJoshuaLog() : createStrippedLog();

    if (woodPreset == WoodPreset.JOSHUA) {
      bundle = createBundle();
      strippedBundle = createStrippedBundle();
      StrippableBlockRegistry.register(bundle, strippedBundle);
      FlammableBlockRegistry.getDefaultInstance().add(bundle, 5, 5);
      FlammableBlockRegistry.getDefaultInstance().add(strippedBundle, 5, 5);
    } else {
      StrippableBlockRegistry.register(log, strippedLog);
    }

    if (woodPreset != WoodPreset.BAMBOO && woodPreset != WoodPreset.JOSHUA) {
      wood = createWood();
      strippedWood = createStrippedWood();
      StrippableBlockRegistry.register(wood, strippedWood);
    }

    if (this.hasDefaultLeaves()) {
      leaves = createLeaves();
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(leavesBefore, leaves));

      if (this.hasDefaultSapling()) {
        saplingGenerator = new TreeGrower(NatureSpirit.MOD_ID + "_" + this.getName(), configuredFeature2, configuredFeature, Optional.empty());
        sapling = this.isSandy() ? createSandySapling(saplingGenerator) : createSapling(saplingGenerator);
        pottedSapling = createPottedSapling(sapling);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(saplingBefore, sapling.asItem()));
        SaplingHashMap.put(getName(), new Block[]{sapling, pottedSapling});
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> factories.add(new VillagerTrades.ItemsForEmeralds(sapling, 5, 1, 8, 1)));
      }
    }

    if (woodPreset == WoodPreset.FROSTABLE) {
      frostyLeaves = createLeaves("frosty_");
      leaves = createFrostableLeaves();
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(leavesBefore, leaves, frostyLeaves));
      saplingGenerator = new TreeGrower(NatureSpirit.MOD_ID + "_" + this.getName(), configuredFeature2, configuredFeature, Optional.empty());
      sapling = createSapling(saplingGenerator);
      pottedSapling = createPottedSapling(sapling);
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(saplingBefore, sapling.asItem()));
      SaplingHashMap.put(getName(), new Block[]{sapling, pottedSapling});
      TradeOfferHelper.registerWanderingTraderOffers(1, factories -> factories.add(new VillagerTrades.ItemsForEmeralds(sapling, 5, 1, 8, 1)));

    }

    if (woodPreset == WoodPreset.WILLOW) {
      vines = createVines(this::getVinesPlant);
      vinesPlant = createVinesPlant(vines);

      leaves = createVinesLeavesBlock(vinesPlant, vines);
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(leavesBefore, leaves.asItem()));

      saplingGenerator = new TreeGrower(NatureSpirit.MOD_ID + "_" + this.getName(), configuredFeature2, configuredFeature, Optional.empty());
      sapling = createSapling(saplingGenerator);
      pottedSapling = createPottedSapling(sapling);
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(saplingBefore, sapling.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(Blocks.VINE, vines.asItem()));
      SaplingHashMap.put(getName(), new Block[]{sapling, pottedSapling});
      TradeOfferHelper.registerWanderingTraderOffers(1, factories -> factories.add(new VillagerTrades.ItemsForEmeralds(sapling, 5, 1, 8, 1)));
    }
    if (woodPreset == WoodPreset.WISTERIA) {
      whiteVines = createVines("white_", this::getWhiteVinesPlant);
      blueVines = createVines("blue_", this::getBlueVinesPlant);
      pinkVines = createVines("pink_", this::getPinkVinesPlant);
      purpleVines = createVines("purple_", this::getPurpleVinesPlant);
      whiteVinesPlant = createVinesPlant("white_", whiteVines);
      blueVinesPlant = createVinesPlant("blue_", blueVines);
      pinkVinesPlant = createVinesPlant("pink_", pinkVines);
      purpleVinesPlant = createVinesPlant("purple_", purpleVines);
      whiteLeaves = createVinesLeavesBlock("white_", whiteVinesPlant, whiteVines);
      blueLeaves = createVinesLeavesBlock("blue_", blueVinesPlant, blueVines);
      pinkLeaves = createVinesLeavesBlock("pink_", pinkVinesPlant, pinkVines);
      purpleLeaves = createVinesLeavesBlock("purple_", purpleVinesPlant, purpleVines);
      partWhiteLeaves = createVinesLeavesBlock("part_white_", whiteVinesPlant, whiteVines);
      partBlueLeaves = createVinesLeavesBlock("part_blue_", blueVinesPlant, blueVines);
      partPinkLeaves = createVinesLeavesBlock("part_pink_", pinkVinesPlant, pinkVines);
      partPurpleLeaves = createVinesLeavesBlock("part_purple_", purpleVinesPlant, purpleVines);
      whiteSapling = createSapling("white_",
          new TreeGrower(NatureSpirit.MOD_ID + "_" + this.getName(), Optional.empty(), Optional.of(NSConfiguredFeatures.WHITE_WISTERIA_TREE), Optional.empty()));
      blueSapling = createSapling("blue_",
          new TreeGrower(NatureSpirit.MOD_ID + "_" + this.getName(), Optional.empty(), Optional.of(NSConfiguredFeatures.BLUE_WISTERIA_TREE), Optional.empty()));
      pinkSapling = createSapling("pink_",
          new TreeGrower(NatureSpirit.MOD_ID + "_" + this.getName(), Optional.empty(), Optional.of(NSConfiguredFeatures.PINK_WISTERIA_TREE), Optional.empty()));
      purpleSapling = createSapling("purple_",
          new TreeGrower(NatureSpirit.MOD_ID + "_" + this.getName(), Optional.empty(), Optional.of(NSConfiguredFeatures.PURPLE_WISTERIA_TREE), Optional.empty()));
      pottedWhiteSapling = createPottedSapling("white_", whiteSapling);
      pottedBlueSapling = createPottedSapling("blue_", blueSapling);
      pottedPinkSapling = createPottedSapling("pink_", pinkSapling);
      pottedPurpleSapling = createPottedSapling("purple_", purpleSapling);
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(
          entries -> entries.addAfter(leavesBefore, whiteLeaves.asItem(), partWhiteLeaves, blueLeaves, partBlueLeaves, pinkLeaves, partPinkLeaves, purpleLeaves, partPurpleLeaves));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(Blocks.VINE, whiteVines.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(whiteVines, blueVines.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(blueVines, pinkVines.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(pinkVines, purpleVines.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(saplingBefore, whiteSapling.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(whiteSapling, blueSapling.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(blueSapling, pinkSapling.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(pinkSapling, purpleSapling.asItem()));
      SaplingHashMap.put("white_" + getName(), new Block[]{whiteSapling, pottedWhiteSapling});
      SaplingHashMap.put("blue_" + getName(), new Block[]{blueSapling, pottedBlueSapling});
      SaplingHashMap.put("pink_" + getName(), new Block[]{pinkSapling, pottedPinkSapling});
      SaplingHashMap.put("purple_" + getName(), new Block[]{purpleSapling, pottedPurpleSapling});
      SaplingHashMap.put("part_white_" + getName(), new Block[]{whiteSapling, pottedWhiteSapling});
      SaplingHashMap.put("part_blue_" + getName(), new Block[]{blueSapling, pottedBlueSapling});
      SaplingHashMap.put("part_pink_" + getName(), new Block[]{pinkSapling, pottedPinkSapling});
      SaplingHashMap.put("part_purple_" + getName(), new Block[]{purpleSapling, pottedPurpleSapling});
      TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
        factories.add(new VillagerTrades.ItemsForEmeralds(whiteSapling, 5, 1, 8, 1));
        factories.add(new VillagerTrades.ItemsForEmeralds(blueSapling, 5, 1, 8, 1));
        factories.add(new VillagerTrades.ItemsForEmeralds(purpleSapling, 5, 1, 8, 1));
        factories.add(new VillagerTrades.ItemsForEmeralds(pinkSapling, 5, 1, 8, 1));
      });
    }
    if (woodPreset == WoodPreset.MAPLE) {
      redLeaves = createParticleLeaves("red_", NSParticleTypes.RED_MAPLE_LEAVES_PARTICLE, 100);
      orangeLeaves = createParticleLeaves("orange_", NSParticleTypes.ORANGE_MAPLE_LEAVES_PARTICLE, 100);
      yellowLeaves = createParticleLeaves("yellow_", NSParticleTypes.YELLOW_MAPLE_LEAVES_PARTICLE, 100);
      redSapling = createSapling("red_",
          new TreeGrower(NatureSpirit.MOD_ID + "_" + this.getName(), Optional.empty(), Optional.of(NSConfiguredFeatures.RED_MAPLE_TREE), Optional.empty()));
      orangeSapling = createSapling("orange_",
          new TreeGrower(NatureSpirit.MOD_ID + "_" + this.getName(), Optional.empty(), Optional.of(NSConfiguredFeatures.ORANGE_MAPLE_TREE), Optional.empty()));
      yellowSapling = createSapling("yellow_",
          new TreeGrower(NatureSpirit.MOD_ID + "_" + this.getName(), Optional.empty(), Optional.of(NSConfiguredFeatures.YELLOW_MAPLE_TREE), Optional.empty()));
      pottedRedSapling = createPottedSapling("red_", redSapling);
      pottedOrangeSapling = createPottedSapling("orange_", orangeSapling);
      pottedYellowSapling = createPottedSapling("yellow_", yellowSapling);
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(leavesBefore, redLeaves.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(redLeaves, orangeLeaves.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(orangeLeaves, yellowLeaves.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(saplingBefore, redSapling.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(redSapling, orangeSapling.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(orangeSapling, yellowSapling.asItem()));
      SaplingHashMap.put("red_" + getName(), new Block[]{redSapling, pottedRedSapling});
      SaplingHashMap.put("orange_" + getName(), new Block[]{orangeSapling, pottedOrangeSapling});
      SaplingHashMap.put("yellow_" + getName(), new Block[]{yellowSapling, pottedYellowSapling});
      TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
        factories.add(new VillagerTrades.ItemsForEmeralds(redSapling, 5, 1, 8, 1));
        factories.add(new VillagerTrades.ItemsForEmeralds(orangeSapling, 5, 1, 8, 1));
        factories.add(new VillagerTrades.ItemsForEmeralds(yellowSapling, 5, 1, 8, 1));
      });
    }
    if (woodPreset == WoodPreset.ASPEN) {
      leaves = createLeaves();
      yellowLeaves = createLeaves("yellow_");
      sapling = createSapling(
          new TreeGrower(NatureSpirit.MOD_ID + "_" + this.getName(), .5f, Optional.empty(), Optional.empty(), Optional.of(NSConfiguredFeatures.ASPEN_TREE),
              Optional.of(NSConfiguredFeatures.YELLOW_ASPEN_TREE), Optional.empty(), Optional.empty()));
      pottedSapling = createPottedSapling(sapling);
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(leavesBefore, yellowLeaves.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(leavesBefore, leaves.asItem()));
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(saplingBefore, sapling.asItem()));
      SaplingHashMap.put(getName(), new Block[]{sapling, pottedSapling});
      TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
        factories.add(new VillagerTrades.ItemsForEmeralds(sapling, 5, 1, 8, 1));
      });
    }
    if (this.hasMosaic()) {
      mosaic = createMosaic();
      mosaicStairs = createMosaicStairs();
      mosaicSlab = createMosaicSlab();
      FlammableBlockRegistry.getDefaultInstance().add(mosaic, 5, 20);
      FlammableBlockRegistry.getDefaultInstance().add(mosaicSlab, 5, 20);
      FlammableBlockRegistry.getDefaultInstance().add(mosaicStairs, 5, 20);
      FuelRegistry.INSTANCE.add(mosaic, 300);
      FuelRegistry.INSTANCE.add(mosaicStairs, 300);
      FuelRegistry.INSTANCE.add(mosaicSlab, 150);
    }
    planks = createPlanks();
    stairs = createStairs();
    slab = createSlab();
    fence = createFence();
    fenceGate = createFenceGate();
    pressurePlate = createPressurePlate();
    button = createButton();
    door = createDoor();
    trapDoor = createTrapDoor();
    sign = createSign();
    wallSign = createWallSign();
    hangingSign = createHangingSign();
    hangingWallSign = createWallHangingSign();
    signItem = createSignItem();
    hangingSignItem = createHangingSignItem();

    FabricBlockEntityType signEntity = BlockEntityType.SIGN;
    signEntity.addSupportedBlock(sign);
    signEntity.addSupportedBlock(wallSign);

    FabricBlockEntityType hangingSignEntity = BlockEntityType.HANGING_SIGN;
    hangingSignEntity.addSupportedBlock(hangingSign);
    hangingSignEntity.addSupportedBlock(hangingWallSign);

    Boat.Type boatType = boatTypeSupplier.get();
    String boatTypeName = boatType.getName().replace("natures_spirit_", "");
    boatItem = createItem(boatTypeName + "_boat",
        new BoatItem(false, boatType, new Item.Properties().stacksTo(1))
    );
    chestBoatItem = createItem(boatTypeName + "_chest_boat",
        new BoatItem(true, boatType, new Item.Properties().stacksTo(1))
    );
    NSBoatTypes.addBoatTypeItems(boatType, boatItem, chestBoatItem);
    NSBoatTypes.setBoatTypeBaseItem(boatType, planks);

    RenderLayerHashMap.put(getName() + "_door", door);
    RenderLayerHashMap.put(getName() + "_trapdoor", trapDoor);

    FlammableBlockRegistry.getDefaultInstance().add(strippedLog, 5, 5);
    FlammableBlockRegistry.getDefaultInstance().add(log, 5, 5);
    FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 20);
    FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 20);
    FlammableBlockRegistry.getDefaultInstance().add(planks, 5, 20);
    FlammableBlockRegistry.getDefaultInstance().add(fence, 5, 20);
    FlammableBlockRegistry.getDefaultInstance().add(fenceGate, 5, 20);

    FuelRegistry.INSTANCE.add(fence, 300);
    FuelRegistry.INSTANCE.add(fenceGate, 300);

    blockLogsTag = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(getModID(), getName() + "_logs"));
    itemLogsTag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(getModID(), getName() + "_logs"));
    addToBuildingTab(buttonBefore, logBefore, signBefore, boatBefore, this);
  }

  public WoodSet(
      ResourceLocation name,
      MapColor sideColor,
      MapColor topColor,
      ItemLike leavesBefore,
      ItemLike logBefore,
      ItemLike signBefore,
      ItemLike boatBefore,
      ItemLike buttonBefore,
      ItemLike saplingBefore,
      Supplier<Boat.Type> boatType,
      WoodPreset woodPreset,
      boolean hasMosaic,
      ResourceKey<ConfiguredFeature<?, ?>> configuredFeature
  ) {
    this.woodPreset = woodPreset;
    this.name = name;
    this.sideColor = sideColor;
    this.topColor = topColor;
    this.leavesBefore = leavesBefore;
    this.logBefore = logBefore;
    this.signBefore = signBefore;
    this.boatBefore = boatBefore;
    this.buttonBefore = buttonBefore;
    this.saplingBefore = saplingBefore;
    this.boatTypeSupplier = boatType;
    this.hasMosaic = hasMosaic;
    this.configuredFeature = Optional.of(configuredFeature);
    this.configuredFeature2 = Optional.empty();
    registerWood();
    WoodHashMap.put(this.getName(), this);
  }

  public WoodSet(
      ResourceLocation name,
      MapColor sideColor,
      MapColor topColor,
      ItemLike leavesBefore,
      ItemLike logBefore,
      ItemLike signBefore,
      ItemLike boatBefore,
      ItemLike buttonBefore,
      ItemLike saplingBefore,
      Supplier<Boat.Type> boatType,
      WoodPreset woodPreset,
      boolean hasMosaic,
      Optional<ResourceKey<ConfiguredFeature<?, ?>>> configuredFeature,
      Optional<ResourceKey<ConfiguredFeature<?, ?>>> configuredFeature2
  ) {
    this.woodPreset = woodPreset;
    this.name = name;
    this.sideColor = sideColor;
    this.topColor = topColor;
    this.leavesBefore = leavesBefore;
    this.logBefore = logBefore;
    this.signBefore = signBefore;
    this.boatBefore = boatBefore;
    this.buttonBefore = buttonBefore;
    this.saplingBefore = saplingBefore;
    this.boatTypeSupplier = boatType;
    this.hasMosaic = hasMosaic;
    this.configuredFeature = configuredFeature;
    this.configuredFeature2 = configuredFeature2;
    this.hasLargeTree = true;
    registerWood();
    WoodHashMap.put(this.getName(), this);
  }

  public ResourceLocation getNameID() {
    return name;
  }

  public String getName() {
    return name.getPath();
  }

  public String getModID() {
    return name.getNamespace();
  }

  public BlockSetType getBlockSetType() {
    return this.blockSetType;
  }

  public WoodPreset getWoodPreset() {
    return woodPreset;
  }

  public MapColor getTopColor() {
    return topColor;
  }

  public WoodType getWoodType() {
    return woodType;
  }

  public Block getButton() {
    return button;
  }

  public Block getFence() {
    return fence;
  }

  public Block getPlanks() {
    return planks;
  }

  public Block getSlab() {
    return slab;
  }

  public Block getFenceGate() {
    return fenceGate;
  }

  public Block getStairs() {
    return stairs;
  }

  public Block getDoor() {
    return door;
  }

  public Block getHangingSign() {
    return hangingSign;
  }

  public Block getHangingWallSign() {
    return hangingWallSign;
  }

  public Block getPressurePlate() {
    return pressurePlate;
  }

  public Block getSign() {
    return sign;
  }

  public Block getTrapDoor() {
    return trapDoor;
  }

  public Block getWallSign() {
    return wallSign;
  }

  public Item getHangingSignItem() {
    return hangingSignItem;
  }

  public Item getSignItem() {
    return signItem;
  }

  public Item getBoatItem() {
    return boatItem;
  }

  public Item getChestBoatItem() {
    return chestBoatItem;
  }

  public Block getLog() {
    return log;
  }

  public Block getStrippedLog() {
    return strippedLog;
  }

  public Block getBundle() {
    return bundle;
  }

  public Block getStrippedBundle() {
    return strippedBundle;
  }

  public Block getWood() {
    return wood;
  }

  public Block getStrippedWood() {
    return strippedWood;
  }

  public Block getMosaic() {
    return mosaic;
  }

  public Block getMosaicStairs() {
    return mosaicStairs;
  }

  public Block getMosaicSlab() {
    return mosaicSlab;
  }

  public Block getLeaves() {
    return leaves;
  }

  public Block getFrostyLeaves() {
    return frostyLeaves;
  }

  public Block getSapling() {
    return sapling;
  }

  public Block getPottedSapling() {
    return pottedSapling;
  }

  public Block getVines() {
    return vines;
  }

  public Block getVinesPlant() {
    return vinesPlant;
  }

  public Block getRedLeaves() {
    return redLeaves;
  }

  public Block getOrangeLeaves() {
    return orangeLeaves;
  }

  public Block getYellowLeaves() {
    return yellowLeaves;
  }

  public Block getBlueLeaves() {
    return blueLeaves;
  }

  public Block getPurpleLeaves() {
    return purpleLeaves;
  }

  public Block getPinkLeaves() {
    return pinkLeaves;
  }

  public Block getWhiteLeaves() {
    return whiteLeaves;
  }

  public Block getPottedRedSapling() {
    return pottedRedSapling;
  }

  public Block getPottedOrangeSapling() {
    return pottedOrangeSapling;
  }

  public Block getPottedYellowSapling() {
    return pottedYellowSapling;
  }

  public Block getPottedBlueSapling() {
    return pottedBlueSapling;
  }

  public Block getPottedPurpleSapling() {
    return pottedPurpleSapling;
  }

  public Block getPottedPinkSapling() {
    return pottedPinkSapling;
  }

  public Block getPottedWhiteSapling() {
    return pottedWhiteSapling;
  }

  public Block getRedSapling() {
    return redSapling;
  }

  public Block getOrangeSapling() {
    return orangeSapling;
  }

  public Block getYellowSapling() {
    return yellowSapling;
  }

  public Block getBlueSapling() {
    return blueSapling;
  }

  public Block getPurpleSapling() {
    return purpleSapling;
  }

  public Block getPinkSapling() {
    return pinkSapling;
  }

  public Block getWhiteSapling() {
    return whiteSapling;
  }

  public Block getBlueVines() {
    return blueVines;
  }

  public Block getPurpleVines() {
    return purpleVines;
  }

  public Block getPinkVines() {
    return pinkVines;
  }

  public Block getWhiteVines() {
    return whiteVines;
  }

  public Block getBlueVinesPlant() {
    return blueVinesPlant;
  }

  public Block getPurpleVinesPlant() {
    return purpleVinesPlant;
  }

  public Block getPinkVinesPlant() {
    return pinkVinesPlant;
  }

  public Block getWhiteVinesPlant() {
    return whiteVinesPlant;
  }

  public Block getPartBlueLeaves() {
    return partBlueLeaves;
  }

  public Block getPartPurpleLeaves() {
    return partPurpleLeaves;
  }

  public Block getPartPinkLeaves() {
    return partPinkLeaves;
  }

  public Block getPartWhiteLeaves() {
    return partWhiteLeaves;
  }

  private String getWoodName() {
    String name;
    if (woodPreset == WoodPreset.NETHER) {
      name = getName() + "_hyphae";
    } else {
      name = getName() + "_wood";
    }
    return name;
  }

  private String getLogName() {
    String name;
    if (woodPreset == WoodPreset.BAMBOO) {
      name = getName() + "_block";
    } else if (woodPreset == WoodPreset.NETHER) {
      name = getName() + "_stem";
    } else {
      name = getName() + "_log";
    }
    return name;
  }

  private Block getBase() {
    Block base;
    if (woodPreset == WoodPreset.BAMBOO) {
      base = Blocks.BAMBOO_PLANKS;
    } else if (woodPreset == WoodPreset.FANCY) {
      base = Blocks.CHERRY_PLANKS;
    } else if (woodPreset == WoodPreset.NETHER) {
      base = Blocks.CRIMSON_PLANKS;
    } else {
      base = Blocks.OAK_PLANKS;
    }
    return base;
  }

  private Block getSignBase() {
    Block base;
    if (woodPreset == WoodPreset.BAMBOO) {
      base = Blocks.BAMBOO_SIGN;
    } else if (woodPreset == WoodPreset.FANCY) {
      base = Blocks.CHERRY_SIGN;
    } else if (woodPreset == WoodPreset.NETHER) {
      base = Blocks.CRIMSON_SIGN;
    } else {
      base = Blocks.OAK_SIGN;
    }
    return base;
  }

  private Block getHangingSignBase() {
    Block base;
    if (woodPreset == WoodPreset.BAMBOO) {
      base = Blocks.BAMBOO_HANGING_SIGN;
    } else if (woodPreset == WoodPreset.FANCY) {
      base = Blocks.CHERRY_HANGING_SIGN;
    } else if (woodPreset == WoodPreset.NETHER) {
      base = Blocks.CRIMSON_HANGING_SIGN;
    } else {
      base = Blocks.OAK_HANGING_SIGN;
    }
    return base;
  }


  public Boat.Type getBoatType() {
    return boatTypeSupplier.get();
  }

  public TagKey<Item> getItemLogsTag() {
    return itemLogsTag;
  }

  public TagKey<Block> getBlockLogsTag() {
    return blockLogsTag;
  }

  public List<Block> getRegisteredBlocksList() {
    return ImmutableList.copyOf(registeredBlocksList);
  }

  public List<Item> getRegisteredItemsList() {
    return ImmutableList.copyOf(registeredItemsList);
  }

  public List<Block> getLeavesList() {
    return ImmutableList.copyOf(leavesList);
  }

  public List<Block> getsaplingList() {
    return ImmutableList.copyOf(saplingList);
  }

  private Block createBlockWithItem(String blockID, Block block) {
    Block listBlock = registerBlock(blockID, block);
    registeredBlocksList.add(listBlock);
    return listBlock;
  }

  public Item createItem(String blockID, Item item) {
    Item listItem = registerItem(blockID, item);
    registeredItemsList.add(listItem);
    return listItem;
  }

  private Block createLog() {
    return createBlockWithItem(getLogName(), Blocks.log(topColor, sideColor));
  }

  private Block createStrippedLog() {
    return createBlockWithItem("stripped_" + getLogName(), Blocks.log(topColor, sideColor));
  }

  private Block createBundle() {
    return createBlockWithItem(getName() + "_bundle", Blocks.log(topColor, sideColor));
  }

  private Block createStrippedBundle() {
    return createBlockWithItem("stripped_" + getName() + "_bundle", Blocks.log(topColor, sideColor));
  }

  private Block createJoshuaLog() {
    return createBlockWithItem(getLogName(), new BranchingTrunkBlock(
        BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD)));
  }

  private Block createStrippedJoshuaLog() {
    return createBlockWithItem("stripped_" + getLogName(), new BranchingTrunkBlock(
        BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD)));
  }

  private Block createWood() {
    return createBlockWithItem(getWoodName(), new RotatedPillarBlock(
        Properties.of().mapColor(sideColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
  }

  private Block createStrippedWood() {
    return createBlockWithItem("stripped_" + getWoodName(), new RotatedPillarBlock(Properties.of().mapColor(topColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
  }

  private Block createLeaves() {
    Block block = createBlockWithItem(getName() + "_leaves", new LeavesBlock(
        BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()
            .isValidSpawn(Blocks::ocelotOrParrot).isSuffocating(Blocks::never).isViewBlocking(Blocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(Blocks::never)));
    CompostingChanceRegistry.INSTANCE.add(block, 0.3F);
    FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
    LeavesHashMap.put(getName(), block);
    leavesList.add(block);
    return block;
  }

  private Block createLeaves(String prefix) {
    Block block = createBlockWithItem(prefix + getName() + "_leaves", new LeavesBlock(
        BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()
            .isValidSpawn(Blocks::ocelotOrParrot).isSuffocating(Blocks::never).isViewBlocking(Blocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(Blocks::never)));
    CompostingChanceRegistry.INSTANCE.add(block, 0.3F);
    FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
    LeavesHashMap.put(prefix + getName(), block);
    leavesList.add(block);
    return block;
  }

  private Block createFrostableLeaves() {
    Block block = createBlockWithItem(getName() + "_leaves",
        new ProjectileLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()
            .isValidSpawn(Blocks::ocelotOrParrot).isSuffocating(Blocks::never).isViewBlocking(Blocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(Blocks::never), frostyLeaves));
    CompostingChanceRegistry.INSTANCE.add(block, 0.3F);
    FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
    LeavesHashMap.put(getName(), block);
    leavesList.add(block);
    return block;
  }

  private Block createFrostableLeaves(String prefix) {
    Block block = createBlockWithItem(prefix + getName() + "_leaves",
        new ProjectileLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()
            .isValidSpawn(Blocks::ocelotOrParrot).isSuffocating(Blocks::never).isViewBlocking(Blocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(Blocks::never), frostyLeaves));
    CompostingChanceRegistry.INSTANCE.add(block, 0.3F);
    FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
    LeavesHashMap.put(prefix + getName(), block);
    leavesList.add(block);
    return block;
  }

  private Block createParticleLeaves(ParticleOptions particle, int chance) {
    Block block = createBlockWithItem(getName() + "_leaves", new ParticleLeavesBlock(
        BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()
            .isValidSpawn(Blocks::ocelotOrParrot).isSuffocating(Blocks::never).isViewBlocking(Blocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(Blocks::never), particle, chance));
    CompostingChanceRegistry.INSTANCE.add(block, 0.3F);
    FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
    LeavesHashMap.put(getName(), block);
    leavesList.add(block);
    return block;
  }

  private Block createParticleLeaves(String prefix, ParticleOptions particle, int chance) {
    Block block = createBlockWithItem(prefix + getName() + "_leaves", new ParticleLeavesBlock(
        BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()
            .isValidSpawn(Blocks::ocelotOrParrot).isSuffocating(Blocks::never).isViewBlocking(Blocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(Blocks::never), particle, chance));
    CompostingChanceRegistry.INSTANCE.add(block, 0.3F);
    FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
    LeavesHashMap.put(prefix + getName(), block);
    leavesList.add(block);
    return block;
  }

  private Block createVinesLeavesBlock(Block vinesPlantBlock, Block vinesTipBlock) {
    Block block = createBlockWithItem(getName() + "_leaves",
        new VinesLeavesBlock(BlockBehaviour.Properties.of().strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()
            .isValidSpawn(Blocks::ocelotOrParrot).isSuffocating(Blocks::never).isViewBlocking(Blocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(Blocks::never), vinesPlantBlock, vinesTipBlock));
    CompostingChanceRegistry.INSTANCE.add(block, 0.3F);
    FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
    LeavesHashMap.put(getName(), block);
    leavesList.add(block);
    return block;
  }

  private Block createVinesLeavesBlock(String prefix, Block vinesPlantBlock, Block vinesTipBlock) {
    Block block = createBlockWithItem(prefix + getName() + "_leaves",
        new VinesLeavesBlock(BlockBehaviour.Properties.of().strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()
            .isValidSpawn(Blocks::ocelotOrParrot).isSuffocating(Blocks::never).isViewBlocking(Blocks::never).ignitedByLava()
            .pushReaction(PushReaction.DESTROY).isRedstoneConductor(Blocks::never), vinesPlantBlock, vinesTipBlock));
    CompostingChanceRegistry.INSTANCE.add(block, 0.3F);
    FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
    LeavesHashMap.put(prefix + getName(), block);
    leavesList.add(block);
    return block;
  }

  private Block createVines(Supplier<Block> vinesPlantBlock) {
    Block vinesBlock = createBlockWithItem(getName() + "_vines",
        new DownwardVineBlock(BlockBehaviour.Properties
            .of()
            .pushReaction(PushReaction.DESTROY)
            .randomTicks()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.WEEPING_VINES), vinesPlantBlock));
    RenderLayerHashMap.put(getName() + "_vines", vinesBlock);
    CompostingChanceRegistry.INSTANCE.add(vinesBlock, 0.3F);
    return vinesBlock;
  }

  private Block createVines(String prefix, Supplier<Block> vinesPlantBlock) {
    Block vinesBlock = createBlockWithItem(prefix + getName() + "_vines",
        new DownwardVineBlock(BlockBehaviour.Properties
            .of()
            .pushReaction(PushReaction.DESTROY)
            .randomTicks()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.WEEPING_VINES), vinesPlantBlock));
    RenderLayerHashMap.put(prefix + getName() + "_vines", vinesBlock);
    CompostingChanceRegistry.INSTANCE.add(vinesBlock, 0.3F);
    return vinesBlock;
  }

  private Block createVinesPlant(Block vines) {
    Block vinesPlant = registerBlockWithoutTab(getName() + "_vines_plant", new DownwardsVinePlantBlock(BlockBehaviour.Properties
        .of()
        .pushReaction(PushReaction.DESTROY)
        .noCollission()
        .noOcclusion()
        .instabreak()
        .sound(SoundType.WEEPING_VINES)
        .dropsLike(vines), vines));
    RenderLayerHashMap.put(getName() + "_vines_plant", vinesPlant);
    return vinesPlant;
  }

  private Block createVinesPlant(String prefix, Block vines) {
    Block vinesPlant = registerBlockWithoutTab(prefix + getName() + "_vines_plant", new DownwardsVinePlantBlock(BlockBehaviour.Properties
        .of()
        .pushReaction(PushReaction.DESTROY)
        .noCollission()
        .noOcclusion()
        .instabreak()
        .sound(SoundType.WEEPING_VINES)
        .dropsLike(vines), vines));
    RenderLayerHashMap.put(prefix + getName() + "_vines_plant", vinesPlant);
    return vinesPlant;
  }

  private Block createPlanks() {
    return createBlockWithItem(getName() + "_planks", new Block(BlockBehaviour.Properties.ofFullCopy(getBase()).sound(getBlockSetType().soundType()).mapColor(getTopColor())));
  }

  private Block createStairs() {
    return createBlockWithItem(getName() + "_stairs",
        new StairBlock(getBase().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(getBase()).sound(getBlockSetType().soundType()).mapColor(getTopColor())));
  }

  private Block createSlab() {
    return createBlockWithItem(getName() + "_slab", new SlabBlock(BlockBehaviour.Properties.ofFullCopy(getBase()).sound(getBlockSetType().soundType()).mapColor(getTopColor())));
  }

  private Block createMosaic() {
    return createBlockWithItem(getName() + "_mosaic", new Block(BlockBehaviour.Properties.ofFullCopy(getBase()).sound(getBlockSetType().soundType()).mapColor(getTopColor())));
  }

  private Block createMosaicStairs() {
    return createBlockWithItem(getName() + "_mosaic_stairs",
        new StairBlock(getBase().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(getBase()).sound(getBlockSetType().soundType()).mapColor(getTopColor())));
  }

  private Block createMosaicSlab() {
    return createBlockWithItem(getName() + "_mosaic_slab", new SlabBlock(BlockBehaviour.Properties.ofFullCopy(getBase()).sound(getBlockSetType().soundType()).mapColor(getTopColor())));
  }

  private Block createFence() {
    return createBlockWithItem(getName() + "_fence", new FenceBlock(BlockBehaviour.Properties.ofFullCopy(getBase()).sound(getBlockSetType().soundType()).mapColor(getTopColor())));
  }

  private Block createFenceGate() {
    return createBlockWithItem(getName() + "_fence_gate", new FenceGateBlock(getWoodType(),
        BlockBehaviour.Properties.of().mapColor(getBase().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava()));
  }

  private Block createPressurePlate() {
    return createBlockWithItem(getName() + "_pressure_plate", new PressurePlateBlock(getBlockSetType(),
        BlockBehaviour.Properties.of().mapColor(this.getBase().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava()
            .pushReaction(PushReaction.DESTROY)));
  }

  private Block createButton() {
    return createBlockWithItem(getName() + "_button",
        new ButtonBlock(getBlockSetType(), 30, BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)));
  }

  private Block createDoor() {
    return createBlockWithItem(getName() + "_door",
        new DoorBlock(getBlockSetType(), BlockBehaviour.Properties.ofFullCopy(getBase()).sound(getBlockSetType().soundType()).noOcclusion().mapColor(getTopColor())));
  }

  private Block createTrapDoor() {
    return createBlockWithItem(getName() + "_trapdoor",
        new TrapDoorBlock(getBlockSetType(), BlockBehaviour.Properties.ofFullCopy(getBase()).sound(getBlockSetType().soundType()).noOcclusion().mapColor(getTopColor())));
  }

  private Block createSign() {
    return registerBlockWithoutTab(getName() + "_sign", new StandingSignBlock(woodType, BlockBehaviour.Properties.ofFullCopy(getSignBase()).mapColor(this.getTopColor())));
  }

  private Block createWallSign() {
    return registerBlockWithoutTab(getName() + "_wall_sign", new WallSignBlock(woodType, BlockBehaviour.Properties.ofFullCopy(getSignBase()).mapColor(this.getTopColor()).dropsLike(sign)));
  }

  private Block createHangingSign() {
    return registerBlockWithoutTab(getName() + "_hanging_sign", new CeilingHangingSignBlock(woodType, BlockBehaviour.Properties.ofFullCopy(getHangingSignBase()).mapColor(this.getTopColor())));
  }

  private Block createWallHangingSign() {
    return registerBlockWithoutTab(getName() + "_wall_hanging_sign",
        new WallHangingSignBlock(woodType, BlockBehaviour.Properties.ofFullCopy(getHangingSignBase()).mapColor(this.getTopColor()).dropsLike(hangingSign)));
  }

  public Block createSapling(TreeGrower saplingGenerator) {
    Block block = createBlockWithItem(getName() + "_sapling", new SaplingBlock(saplingGenerator, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    CompostingChanceRegistry.INSTANCE.add(block, 0.3F);
    RenderLayerHashMap.put(getName() + "_sapling", block);
    saplingList.add(block);
    return block;
  }

  public Block createSandySapling(TreeGrower saplingGenerator) {
    Block block = createBlockWithItem(getName() + "_sapling", new SandySaplingBlock(saplingGenerator, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    CompostingChanceRegistry.INSTANCE.add(block, 0.3F);
    RenderLayerHashMap.put(getName() + "_sapling", block);
    saplingList.add(block);
    return block;
  }

  public Block createPottedSapling(Block sapling) {
    return registerTransparentBlockWithoutTab("potted_" + getName() + "_sapling",
        new FlowerPotBlock(sapling, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));
  }

  public Block createSapling(String prefix, TreeGrower saplingGenerator) {
    Block block = createBlockWithItem(prefix + getName() + "_sapling", new SaplingBlock(saplingGenerator, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    CompostingChanceRegistry.INSTANCE.add(block, 0.3F);
    RenderLayerHashMap.put(prefix + getName() + "_sapling", block);
    saplingList.add(block);
    return block;
  }

  public Block createSandySapling(String prefix, TreeGrower saplingGenerator) {
    Block block = createBlockWithItem(prefix + getName() + "_sapling", new SandySaplingBlock(saplingGenerator, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    CompostingChanceRegistry.INSTANCE.add(block, 0.3F);
    RenderLayerHashMap.put(prefix + getName() + "_sapling", block);
    saplingList.add(block);
    return block;
  }

  public Block createPottedSapling(String prefix, Block sapling) {
    return registerTransparentBlockWithoutTab("potted_" + prefix + getName() + "_sapling",
        new FlowerPotBlock(sapling, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));
  }

  private Item createSignItem() {
    return createItem(getName() + "_sign", new SignItem(new Item.Properties().stacksTo(16), sign, wallSign));
  }

  private Item createHangingSignItem() {
    return createItem(getName() + "_hanging_sign", new HangingSignItem(hangingSign, hangingWallSign, new Item.Properties().stacksTo(16)));
  }

  private BlockSetType createBlockSetType() {
    if (this.woodPreset == WoodPreset.BAMBOO) {
      return BlockSetTypeBuilder.copyOf(BlockSetType.BAMBOO).register(getNameID());
    } else if (woodPreset == WoodPreset.FANCY) {
      return BlockSetTypeBuilder.copyOf(BlockSetType.CHERRY).register(getNameID());
    } else if (this.woodPreset == WoodPreset.NETHER) {
      return BlockSetTypeBuilder.copyOf(BlockSetType.CRIMSON).register(getNameID());
    } else {
      return BlockSetTypeBuilder.copyOf(BlockSetType.OAK).register(getNameID());
    }
  }

  public boolean isSandy() {
    return woodPreset == WoodPreset.JOSHUA || woodPreset == WoodPreset.SANDY;
  }

  public boolean hasDefaultLeaves() {
    return woodPreset == WoodPreset.DEFAULT || woodPreset == WoodPreset.WISTERIA || woodPreset == WoodPreset.FANCY || woodPreset == WoodPreset.JOSHUA
        || woodPreset == WoodPreset.NO_SAPLING || woodPreset == WoodPreset.SANDY;
  }

  public boolean hasDefaultSapling() {
    return woodPreset != WoodPreset.NO_SAPLING && woodPreset != WoodPreset.WISTERIA;
  }

  public boolean hasBark() {
    return woodPreset != WoodPreset.JOSHUA && woodPreset != WoodPreset.BAMBOO;
  }

  public boolean hasMosaic() {
    return this.hasMosaic;
  }

  public enum WoodPreset {
    DEFAULT, MAPLE, ASPEN, FROSTABLE, JOSHUA, SANDY, NO_SAPLING, WISTERIA, WILLOW, FANCY, NETHER, BAMBOO
  }

  public static void addToBuildingTab(ItemLike proceedingItem, ItemLike logPlacement, ItemLike signPlacement, ItemLike boatPlacement, WoodSet woodset) {
    ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
      entries.addAfter(proceedingItem, woodset.getLog());
      if (woodset.getWoodPreset() == WoodPreset.JOSHUA) {
        entries.addAfter(woodset.getLog(), woodset.getBundle(), woodset.getStrippedLog(), woodset.getStrippedBundle(), woodset.getPlanks());
      } else if (!woodset.hasBark()) {
        entries.addAfter(woodset.getLog(), woodset.getStrippedLog(), woodset.getPlanks());
      } else {
        entries.addAfter(woodset.getLog(), woodset.getWood());
        entries.addAfter(woodset.getWood(), woodset.getStrippedLog(), woodset.getStrippedWood(), woodset.getPlanks());
      }
      entries.addAfter(woodset.getPlanks(), woodset.getStairs(), woodset.getSlab(),
          woodset.getFence(), woodset.getFenceGate(),
          woodset.getDoor(), woodset.getTrapDoor(),
          woodset.getPressurePlate(), woodset.getButton());

      if (woodset.hasMosaic()) {
        entries.addAfter(woodset.getPlanks(), woodset.getMosaic());
        entries.addAfter(woodset.getStairs(), woodset.getMosaicStairs());
        entries.addAfter(woodset.getSlab(), woodset.getMosaicSlab());
      }
    });
    ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(logPlacement, woodset.getLog().asItem()));
    ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> entries.addAfter(signPlacement, woodset.getSignItem(), woodset.getHangingSignItem()));
    ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> entries.addAfter(boatPlacement, woodset.getBoatItem(), woodset.getChestBoatItem()));
  }

}
