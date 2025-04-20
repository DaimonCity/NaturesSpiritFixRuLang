package net.hibiscus.naturespirit.datagen;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.hibiscus.naturespirit.blocks.DesertTurnipStemBlock;
import net.hibiscus.naturespirit.registration.*;
import net.hibiscus.naturespirit.registration.sets.FlowerSet;
import net.hibiscus.naturespirit.registration.sets.StoneSet;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.client.*;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.BlockModelGenerators.TintState;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import static net.hibiscus.naturespirit.registration.NSMiscBlocks.*;
import static net.minecraft.data.models.BlockModelGenerators.*;
import static net.minecraft.data.models.model.TexturedModel.createDefault;

class NSModelGenerator extends FabricModelProvider {

  private static final ModelTemplate TALL_LARGE_CROSS = block("tall_large_cross", TextureSlot.CROSS);
  private static final ModelTemplate TINTED_TALL_LARGE_CROSS = block("tinted_tall_large_cross", TextureSlot.CROSS);
  private static final ModelTemplate LARGE_CROSS = block("large_cross", TextureSlot.CROSS);
  private static final ModelTemplate TINTED_LARGE_CROSS = block("tinted_large_cross", TextureSlot.CROSS);
  private static final ModelTemplate TALL_CROSS = block("tall_cross", TextureSlot.CROSS);
  private static final ModelTemplate FLOWER_POT_TALL_CROSS = block("flower_pot_tall_cross", TextureSlot.PLANT);
  private static final ModelTemplate FLOWER_POT_LARGE_CROSS = block("flower_pot_large_cross", TextureSlot.PLANT);
  private static final ModelTemplate SUCCULENT = block("succulent", TextureSlot.PLANT);
  private static final ModelTemplate POLYPORE = block("polypore", TextureSlot.PLANT);
  private static final ModelTemplate SUCCULENT_WALL = block("succulent_wall", TextureSlot.PLANT);
  private static final ModelTemplate FLOWER_POT_SUCCULENT = block("flower_pot_succulent", TextureSlot.PLANT);
  private static final ModelTemplate FLOWER_POT_TINTED_LARGE_CROSS = block("tinted_flower_pot_large_cross", TextureSlot.PLANT);
  private static final ModelTemplate CROP = block("crop", TextureSlot.CROP);
  private static final ModelTemplate PAPER_LANTERN = block("template_paper_lantern", TextureSlot.TOP, TextureSlot.SIDE);
  private static final ModelTemplate HANGING_PAPER_LANTERN = block("template_hanging_paper_lantern", "_hanging", TextureSlot.TOP, TextureSlot.SIDE);

  private static final ModelTemplate AZOLLA_1 = block("template_azolla_1", "_1", TextureSlot.PLANT);
  private static final ModelTemplate AZOLLA_2 = block("template_azolla_2", "_2", TextureSlot.PLANT);
  private static final ModelTemplate AZOLLA_3 = block("template_azolla_3", "_3", TextureSlot.PLANT);
  private static final ModelTemplate AZOLLA_4 = block("template_azolla_4", "_4", TextureSlot.PLANT);

  public static final ModelTemplate[] OLIVE_BRANCH_STAGES = IntStream.range(0, 4).mapToObj((stage) -> minecraftBlock("cross", "_stage" + stage, TextureSlot.CROSS)).toArray(ModelTemplate[]::new);

  public static TextureMapping paperLantern(Block block) {
    return (new TextureMapping()).put(TextureSlot.SIDE, getId(block)).put(TextureSlot.TOP, getId(block).withSuffix("_top"));
  }


  public static final TexturedModel.Provider TEXTURED_SUCCULENT = createDefault(TextureMapping::plant, SUCCULENT);
  public static final TexturedModel.Provider TEXTURED_POLYPORE = createDefault(TextureMapping::plant, POLYPORE);
  public static final TexturedModel.Provider TEMPLATE_PAPER_LANTERN = createDefault(NSModelGenerator::paperLantern, PAPER_LANTERN);
  public static final TexturedModel.Provider TEMPLATE_HANGING_PAPER_LANTERN = createDefault(NSModelGenerator::paperLantern, HANGING_PAPER_LANTERN);

  public static final TexturedModel.Provider TEXTURED_AZOLLA_1 = createDefault(TextureMapping::plant, AZOLLA_1);
  public static final TexturedModel.Provider TEXTURED_AZOLLA_2 = createDefault(TextureMapping::plant, AZOLLA_2);
  public static final TexturedModel.Provider TEXTURED_AZOLLA_3 = createDefault(TextureMapping::plant, AZOLLA_3);
  public static final TexturedModel.Provider TEXTURED_AZOLLA_4 = createDefault(TextureMapping::plant, AZOLLA_4);



  public NSModelGenerator(FabricDataOutput output) {
    super(output);
  }

  private static ModelTemplate block(String parent, TextureSlot... requiredTextureKeys) {
    return new ModelTemplate(Optional.of(ResourceLocation.fromNamespaceAndPath("natures_spirit", "block/" + parent)), Optional.empty(), requiredTextureKeys);
  }

  private static ModelTemplate block(String parent, String variant, TextureSlot... requiredTextureKeys) {
    return new ModelTemplate(Optional.of(ResourceLocation.fromNamespaceAndPath("natures_spirit", "block/" + parent)), Optional.of(variant), requiredTextureKeys);
  }
  private static ModelTemplate minecraftBlock(String parent, String variant, TextureSlot... requiredTextureKeys) {
    return new ModelTemplate(Optional.of(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + parent)), Optional.of(variant), requiredTextureKeys);
  }

  public static ResourceLocation getId(Block block) {
    ResourceLocation identifier = BuiltInRegistries.BLOCK.getKey(block);
    return identifier.withPrefix("block/");
  }

  private void createSlab(Block block, Block slab, BlockModelGenerators blockStateModelGenerator) {
    ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(block);
    TexturedModel texturedModel = TexturedModel.CUBE.get(block);
    ResourceLocation resourceLocation2 = ModelTemplates.SLAB_BOTTOM.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation3 = ModelTemplates.SLAB_TOP.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(createSlab(slab, resourceLocation2, resourceLocation3, resourceLocation));
  }

  private void createStairs(Block block, Block stairs, BlockModelGenerators blockStateModelGenerator) {
    TexturedModel texturedModel = TexturedModel.CUBE.get(block);
    ResourceLocation resourceLocation = ModelTemplates.STAIRS_INNER.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation2 = ModelTemplates.STAIRS_STRAIGHT.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation3 = ModelTemplates.STAIRS_OUTER.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(createStairs(stairs, resourceLocation, resourceLocation2, resourceLocation3));
    blockStateModelGenerator.delegateItemModel(stairs, resourceLocation2);
  }

  public void createWoodDoor(Block doorBlock, BlockModelGenerators blockStateModelGenerator) {
    TextureMapping textureMapping = TextureMapping.door(doorBlock);
    ResourceLocation resourceLocation = ModelTemplates.DOOR_BOTTOM_LEFT.create(doorBlock, textureMapping, blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation2 = ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(doorBlock, textureMapping, blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation3 = ModelTemplates.DOOR_BOTTOM_RIGHT.create(doorBlock, textureMapping, blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation4 = ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(doorBlock, textureMapping, blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation5 = ModelTemplates.DOOR_TOP_LEFT.create(doorBlock, textureMapping, blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation6 = ModelTemplates.DOOR_TOP_LEFT_OPEN.create(doorBlock, textureMapping, blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation7 = ModelTemplates.DOOR_TOP_RIGHT.create(doorBlock, textureMapping, blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation8 = ModelTemplates.DOOR_TOP_RIGHT_OPEN.create(doorBlock, textureMapping, blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.createSimpleFlatItemModel(doorBlock.asItem());
    blockStateModelGenerator.blockStateOutput.accept(createDoor(doorBlock,
        resourceLocation,
        resourceLocation2,
        resourceLocation3,
        resourceLocation4,
        resourceLocation5,
        resourceLocation6,
        resourceLocation7,
        resourceLocation8
    ));
  }

  public final void createOliveBranch(Block block, BlockModelGenerators blockStateModelGenerator) {
    blockStateModelGenerator.createSimpleFlatItemModel(block.asItem());
    blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block)))
        .with(blockStateModelGenerator.createColumnWithFacing()).with(PropertyDispatch.property(BlockStateProperties.AGE_3).generate((age) ->
            Variant.variant().with(VariantProperties.MODEL, OLIVE_BRANCH_STAGES[age].create(block, TextureMapping.cross(TextureMapping.getBlockTexture(block, "_stage" + age)), blockStateModelGenerator.modelOutput)))));
  }

  public void createWoodTrapdoor(Block orientableTrapdoorBlock, BlockModelGenerators blockStateModelGenerators) {
    TextureMapping textureMapping = TextureMapping.defaultTexture(orientableTrapdoorBlock);
    ResourceLocation resourceLocation = ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(orientableTrapdoorBlock, textureMapping, blockStateModelGenerators.modelOutput);
    ResourceLocation resourceLocation2 = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(orientableTrapdoorBlock, textureMapping, blockStateModelGenerators.modelOutput);
    ResourceLocation resourceLocation3 = ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(orientableTrapdoorBlock, textureMapping, blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(createOrientableTrapdoor(orientableTrapdoorBlock, resourceLocation, resourceLocation2, resourceLocation3));
    blockStateModelGenerators.delegateItemModel(orientableTrapdoorBlock, resourceLocation2);
  }

  public void createWoodFenceGate(Block planks, Block fenceGateBlock, BlockModelGenerators blockStateModelGenerator) {
    TexturedModel texturedModel = TexturedModel.CUBE.get(planks);
    ResourceLocation resourceLocation = ModelTemplates.FENCE_GATE_OPEN.create(fenceGateBlock, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation2 = ModelTemplates.FENCE_GATE_CLOSED.create(fenceGateBlock, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation3 = ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGateBlock, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation4 = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGateBlock, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createFenceGate(fenceGateBlock,
        resourceLocation,
        resourceLocation2,
        resourceLocation3,
        resourceLocation4,
        true
    ));
  }

  public void createWoodFence(Block planks, Block fenceBlock, BlockModelGenerators blockStateModelGenerators) {
    TexturedModel texturedModel = TexturedModel.CUBE.get(planks);
    ResourceLocation resourceLocation = ModelTemplates.FENCE_POST.create(fenceBlock, texturedModel.getMapping(), blockStateModelGenerators.modelOutput);
    ResourceLocation resourceLocation2 = ModelTemplates.FENCE_SIDE.create(fenceBlock, texturedModel.getMapping(), blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(BlockModelGenerators.createFence(fenceBlock, resourceLocation, resourceLocation2));
    ResourceLocation resourceLocation3 = ModelTemplates.FENCE_INVENTORY.create(fenceBlock, texturedModel.getMapping(), blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.delegateItemModel(fenceBlock, resourceLocation3);
  }

  public void createWoodPressurePlate(Block planks, Block pressurePlateBlock, BlockModelGenerators blockStateModelGenerator) {
    TexturedModel texturedModel = TexturedModel.CUBE.get(planks);
    ResourceLocation resourceLocation = ModelTemplates.PRESSURE_PLATE_UP.create(pressurePlateBlock, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    ResourceLocation resourceLocation2 = ModelTemplates.PRESSURE_PLATE_DOWN.create(pressurePlateBlock, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(pressurePlateBlock, resourceLocation, resourceLocation2));
  }

  public void createWoodSign(Block signBlock, Block wallSignBlock, BlockModelGenerators blockStateModelGenerator) {
    TextureMapping textureMapping = TextureMapping.defaultTexture(signBlock);
    ResourceLocation resourceLocation = ModelTemplates.PARTICLE_ONLY.create(signBlock, textureMapping, blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(signBlock, resourceLocation));
    blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(wallSignBlock, resourceLocation));
    blockStateModelGenerator.createSimpleFlatItemModel(signBlock.asItem());
    blockStateModelGenerator.skipAutoItemBlock(wallSignBlock);
  }

  public void createWall(Block block, Block wallBlock, BlockModelGenerators blockStateModelGenerator) {
    TexturedModel texturedModel = TexturedModel.CUBE.get(block);
    ResourceLocation identifier = ModelTemplates.WALL_POST.create(wallBlock, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    ResourceLocation identifier2 = ModelTemplates.WALL_LOW_SIDE.create(wallBlock, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    ResourceLocation identifier3 = ModelTemplates.WALL_TALL_SIDE.create(wallBlock, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createWall(wallBlock, identifier, identifier2, identifier3));
    ResourceLocation identifier4 = ModelTemplates.WALL_INVENTORY.create(wallBlock, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.delegateItemModel(wallBlock, identifier4);
  }

  public void createWoodButton(Block planks, Block buttonBlock, BlockModelGenerators blockStateModelGenerators) {
    TexturedModel texturedModel = TexturedModel.CUBE.get(planks);
    ResourceLocation resourceLocation = ModelTemplates.BUTTON.create(buttonBlock, texturedModel.getMapping(), blockStateModelGenerators.modelOutput);
    ResourceLocation resourceLocation2 = ModelTemplates.BUTTON_PRESSED.create(buttonBlock, texturedModel.getMapping(), blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(BlockModelGenerators.createButton(buttonBlock, resourceLocation, resourceLocation2));
    ResourceLocation resourceLocation3 = ModelTemplates.BUTTON_INVENTORY.create(buttonBlock, texturedModel.getMapping(), blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.delegateItemModel(buttonBlock, resourceLocation3);
  }

  public void createHangingSign(Block strippedLog, Block hangingSign, Block wallHangingSign, BlockModelGenerators blockStateModelGenerator) {
    TextureMapping textureMap = TextureMapping.particle(strippedLog);
    ResourceLocation identifier = ModelTemplates.PARTICLE_ONLY.create(hangingSign, textureMap, blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(hangingSign, identifier));
    blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(wallHangingSign, identifier));
    blockStateModelGenerator.createSimpleFlatItemModel(hangingSign.asItem());
    blockStateModelGenerator.skipAutoItemBlock(wallHangingSign);
  }

  private void generateWoodBlockStateModels(HashMap<String, WoodSet> woods, BlockModelGenerators blockStateModelGenerator) {
    for (WoodSet woodSet : woods.values()) {
      if (woodSet.getWoodPreset() == WoodSet.WoodPreset.BAMBOO) {
        blockStateModelGenerator.woodProvider(woodSet.getLog()).logWithHorizontal(woodSet.getLog());
        blockStateModelGenerator.woodProvider(woodSet.getStrippedLog()).logWithHorizontal(woodSet.getStrippedLog());
      } else if (woodSet.getWoodPreset() == WoodSet.WoodPreset.JOSHUA) {
        blockStateModelGenerator.woodProvider(woodSet.getBundle()).logWithHorizontal(woodSet.getBundle());
        blockStateModelGenerator.woodProvider(woodSet.getStrippedBundle()).logWithHorizontal(woodSet.getStrippedBundle());
      } else if (woodSet.hasBark()) {
        blockStateModelGenerator.woodProvider(woodSet.getLog()).logWithHorizontal(woodSet.getLog()).wood(woodSet.getWood());
        blockStateModelGenerator.woodProvider(woodSet.getStrippedLog()).logWithHorizontal(woodSet.getStrippedLog()).wood(woodSet.getStrippedWood());
      }
      if (woodSet.hasMosaic()) {
        blockStateModelGenerator.createTrivialBlock(woodSet.getMosaic(), TexturedModel.CUBE);
        createSlab(woodSet.getMosaic(), woodSet.getMosaicSlab(), blockStateModelGenerator);
        createStairs(woodSet.getMosaic(), woodSet.getMosaicStairs(), blockStateModelGenerator);
      }
      if (woodSet.getWoodPreset() == WoodSet.WoodPreset.WILLOW) {
        generateVineBlockStateModels(woodSet.getVines(), woodSet.getVinesPlant(), blockStateModelGenerator);
      }
      blockStateModelGenerator.createTrivialBlock(woodSet.getPlanks(), TexturedModel.CUBE);
      createSlab(woodSet.getPlanks(), woodSet.getSlab(), blockStateModelGenerator);
      createStairs(woodSet.getPlanks(), woodSet.getStairs(), blockStateModelGenerator);
      createWoodDoor(woodSet.getDoor(), blockStateModelGenerator);
      createWoodTrapdoor(woodSet.getTrapDoor(), blockStateModelGenerator);
      createWoodFenceGate(woodSet.getPlanks(), woodSet.getFenceGate(), blockStateModelGenerator);
      createWoodFence(woodSet.getPlanks(), woodSet.getFence(), blockStateModelGenerator);
      createWoodButton(woodSet.getPlanks(), woodSet.getButton(), blockStateModelGenerator);
      createWoodPressurePlate(woodSet.getPlanks(), woodSet.getPressurePlate(), blockStateModelGenerator);
      createWoodSign(woodSet.getSign(), woodSet.getWallSign(), blockStateModelGenerator);
      createHangingSign(woodSet.getStrippedLog(), woodSet.getHangingSign(), woodSet.getHangingWallSign(), blockStateModelGenerator);
    }
  }

  private void generateFlowerSetBlockStateModels(HashMap<String, FlowerSet> flowers, BlockModelGenerators blockStateModelGenerator) {
    for (FlowerSet flowerSet : flowers.values()) {
      if (Objects.equals(flowerSet.getName(), "protea")) {
        continue;
      }
      if (flowerSet.getPreset() == FlowerSet.FlowerPreset.SMALL) {
        generateFlowerBlockStateModels(flowerSet.getFlowerBlock(), flowerSet.getPottedFlowerBlock(), blockStateModelGenerator);
      }
      if (flowerSet.getPreset() == FlowerSet.FlowerPreset.MID_SMALL) {
        generatePottedAnemone(flowerSet.getFlowerBlock(), flowerSet.getPottedFlowerBlock(), blockStateModelGenerator);
      }
      if (flowerSet.getPreset() == FlowerSet.FlowerPreset.TALL) {
        blockStateModelGenerator.createDoublePlant(flowerSet.getFlowerBlock(), TintState.NOT_TINTED);
      }
      if (flowerSet.getPreset() == FlowerSet.FlowerPreset.BIG_TALL) {
        generateTallLargeFlower(flowerSet.getFlowerBlock(), blockStateModelGenerator);
      }
      if (flowerSet.getPreset() == FlowerSet.FlowerPreset.BIG_SMALL) {
        generateLargeFlower(flowerSet.getFlowerBlock(), flowerSet.getPottedFlowerBlock(), blockStateModelGenerator);
      }
    }
  }

  private void generateStoneBlockStateModels(HashMap<String, StoneSet> stones, BlockModelGenerators blockStateModelGenerator) {
    for (StoneSet stoneSet : stones.values()) {
      if (stoneSet.hasTiles()) {
        createWall(stoneSet.getTiles(), stoneSet.getTilesWall(), blockStateModelGenerator);
        createSlab(stoneSet.getTiles(), stoneSet.getTilesSlab(), blockStateModelGenerator);
        createStairs(stoneSet.getTiles(), stoneSet.getTilesStairs(), blockStateModelGenerator);
        blockStateModelGenerator.createTrivialCube(stoneSet.getTiles());
        if (stoneSet.hasCracked()) {
          blockStateModelGenerator.createTrivialCube(stoneSet.getCrackedTiles());
        }
      }
      if (stoneSet.hasCobbled()) {
        createWall(stoneSet.getCobbled(), stoneSet.getCobbledWall(), blockStateModelGenerator);
        createSlab(stoneSet.getCobbled(), stoneSet.getCobbledSlab(), blockStateModelGenerator);
        createStairs(stoneSet.getCobbled(), stoneSet.getCobbledStairs(), blockStateModelGenerator);
        blockStateModelGenerator.createTrivialCube(stoneSet.getCobbled());
        if (stoneSet.hasMossy()) {
          createWall(stoneSet.getMossyCobbled(), stoneSet.getMossyCobbledWall(), blockStateModelGenerator);
          createSlab(stoneSet.getMossyCobbled(), stoneSet.getMossyCobbledSlab(), blockStateModelGenerator);
          createStairs(stoneSet.getMossyCobbled(), stoneSet.getMossyCobbledStairs(), blockStateModelGenerator);
          blockStateModelGenerator.createTrivialCube(stoneSet.getMossyCobbled());
        }
      }
      if (stoneSet.hasMossy()) {
        createWall(stoneSet.getMossyBricks(), stoneSet.getMossyBricksWall(), blockStateModelGenerator);
        createSlab(stoneSet.getMossyBricks(), stoneSet.getMossyBricksSlab(), blockStateModelGenerator);
        createStairs(stoneSet.getMossyBricks(), stoneSet.getMossyBricksStairs(), blockStateModelGenerator);
        blockStateModelGenerator.createTrivialCube(stoneSet.getMossyBricks());
      }
      if (stoneSet.hasCracked()) {
        blockStateModelGenerator.createTrivialCube(stoneSet.getCrackedBricks());
      }
      createWall(stoneSet.getBricks(), stoneSet.getBricksWall(), blockStateModelGenerator);
      createSlab(stoneSet.getBricks(), stoneSet.getBricksSlab(), blockStateModelGenerator);
      createStairs(stoneSet.getBricks(), stoneSet.getBricksStairs(), blockStateModelGenerator);
      blockStateModelGenerator.createTrivialCube(stoneSet.getBricks());

      createWall(stoneSet.getPolished(), stoneSet.getPolishedWall(), blockStateModelGenerator);
      createSlab(stoneSet.getPolished(), stoneSet.getPolishedSlab(), blockStateModelGenerator);
      createStairs(stoneSet.getPolished(), stoneSet.getPolishedStairs(), blockStateModelGenerator);
      blockStateModelGenerator.createTrivialCube(stoneSet.getPolished());

      createSlab(stoneSet.getBase(), stoneSet.getBaseSlab(), blockStateModelGenerator);
      createStairs(stoneSet.getBase(), stoneSet.getBaseStairs(), blockStateModelGenerator);
      if (stoneSet.isRotatable()) {
        blockStateModelGenerator.woodProvider(stoneSet.getBase()).logWithHorizontal(stoneSet.getBase());
      } else {
        blockStateModelGenerator.createTrivialCube(stoneSet.getBase());
      }

      blockStateModelGenerator.createTrivialCube(stoneSet.getChiseled());
    }
  }

  private void generateTreeBlockStateModels(HashMap<String, Block[]> saplings, HashMap<String, Block> leaves, BlockModelGenerators blockStateModelGenerator) {
    for (String i : leaves.keySet()) {
      Block[] saplingType = saplings.get(i);
      Block leavesType = leaves.get(i);
      if (!Objects.equals(i, "coconut") && !Objects.equals(i, "joshua")) {
        blockStateModelGenerator.createTrivialBlock(leavesType, TexturedModel.LEAVES);
        if (i.equals("redwood")) {
          generatePottedAnemone(saplingType[0], saplingType[1], blockStateModelGenerator);
        } else if (!Objects.equals(i, "wisteria") && !Objects.equals(i, "yellow_aspen") && !i.startsWith("part") && !i.startsWith("frosty")) {
          blockStateModelGenerator.createPlant(saplingType[0], saplingType[1], TintState.NOT_TINTED);
        }
      } else if (Objects.equals(i, "joshua")) {
        blockStateModelGenerator.createPlant(saplingType[0], saplingType[1], TintState.NOT_TINTED);
      }
    }
  }

  public final void registerTallCrossBlockState(Block block, TextureMapping crossTexture, BlockModelGenerators blockStateModelGenerators) {
    ResourceLocation identifier = TALL_CROSS.create(block, crossTexture, blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(createSimpleBlock(block, identifier));
  }

  public final void registerPaperLantern(Block lantern, BlockModelGenerators blockStateModelGenerator) {
    ResourceLocation identifier = TEMPLATE_PAPER_LANTERN.create(lantern, blockStateModelGenerator.modelOutput);
    ResourceLocation identifier2 = TEMPLATE_HANGING_PAPER_LANTERN.create(lantern, blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.createSimpleFlatItemModel(lantern.asItem());
    blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(lantern).with(createBooleanModelDispatch(BlockStateProperties.HANGING, identifier2, identifier)));
  }

  public final void registerVineBlockState(Block block, TextureMapping crossTexture, BlockModelGenerators blockStateModelGenerators) {
    ResourceLocation identifier = CROP.create(block, crossTexture, blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(createSimpleBlock(block, identifier));
  }

  public final void registerTallLargeBlockState(Block block, TextureMapping crossTexture, BlockModelGenerators blockStateModelGenerators) {
    ResourceLocation identifier = TALL_LARGE_CROSS.create(block, crossTexture, blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(createSimpleBlock(block, identifier));
  }

  public final void registerTintedTallLargeBlockState(Block block, TextureMapping crossTexture, BlockModelGenerators blockStateModelGenerators) {
    ResourceLocation identifier = TINTED_TALL_LARGE_CROSS.create(block, crossTexture, blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(createSimpleBlock(block, identifier));
  }

  public final void registerSpecificFlowerItemModel(Block block, BlockModelGenerators blockStateModelGenerators) {
    Item item = block.asItem();
    ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), blockStateModelGenerators.modelOutput);
  }

  private void generateFlowerBlockStateModels(Block block, Block block2, BlockModelGenerators blockStateModelGenerator) {
    blockStateModelGenerator.createPlant(block, block2, TintState.NOT_TINTED);
  }

  private void generateTintedFlowerBlockStateModels(Block block, Block block2, BlockModelGenerators blockStateModelGenerator) {
    blockStateModelGenerator.createPlant(block, block2, TintState.TINTED);
  }

  private void generatePottedAnemone(Block block, Block flowerPot, BlockModelGenerators blockStateModelGenerators) {
    registerSpecificFlowerItemModel(block, blockStateModelGenerators);
    TextureMapping textureMap1 = TextureMapping.cross(block);
    registerTallCrossBlockState(block, textureMap1, blockStateModelGenerators);
    TextureMapping textureMap = TextureMapping.plant(block);
    ResourceLocation identifier = FLOWER_POT_TALL_CROSS.create(flowerPot, textureMap, blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(createSimpleBlock(flowerPot, identifier));
  }

  public final void generateVineBlockStateModels(Block plant, Block plantStem, BlockModelGenerators blockStateModelGenerators) {
    TextureMapping textureMap1 = TextureMapping.crop(getId(plant));
    this.registerVineBlockState(plant, textureMap1, blockStateModelGenerators);
    TextureMapping textureMap2 = TextureMapping.crop(getId(plantStem));
    this.registerVineBlockState(plantStem, textureMap2, blockStateModelGenerators);
    blockStateModelGenerators.createSimpleFlatItemModel(plant, "_plant");
  }

  public final void generateTallLargeFlower(Block doubleBlock, BlockModelGenerators blockStateModelGenerators) {
    registerSpecificFlowerItemModel(doubleBlock, blockStateModelGenerators);
    ResourceLocation identifier = blockStateModelGenerators.createSuffixedVariant(doubleBlock, "_top", LARGE_CROSS, TextureMapping::cross);
    ResourceLocation identifier2 = blockStateModelGenerators.createSuffixedVariant(doubleBlock, "_bottom", LARGE_CROSS, TextureMapping::cross);
    blockStateModelGenerators.createDoubleBlock(doubleBlock, identifier, identifier2);
  }

  public final void generateTintedTallLargeFlower(Block doubleBlock, BlockModelGenerators blockStateModelGenerators) {
    registerSpecificFlowerItemModel(doubleBlock, blockStateModelGenerators);
    ResourceLocation identifier = blockStateModelGenerators.createSuffixedVariant(doubleBlock, "_top", TINTED_LARGE_CROSS, TextureMapping::cross);
    ResourceLocation identifier2 = blockStateModelGenerators.createSuffixedVariant(doubleBlock, "_bottom", TINTED_LARGE_CROSS, TextureMapping::cross);
    blockStateModelGenerators.createDoubleBlock(doubleBlock, identifier, identifier2);
  }

  public final void generateLargeFlower(Block block, Block flowerPot, BlockModelGenerators blockStateModelGenerators) {
    registerSpecificFlowerItemModel(block, blockStateModelGenerators);
    registerTallLargeBlockState(block, TextureMapping.cross(block), blockStateModelGenerators);
    TextureMapping textureMap = TextureMapping.plant(block);
    ResourceLocation identifier = FLOWER_POT_LARGE_CROSS.create(flowerPot, textureMap, blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(createSimpleBlock(flowerPot, identifier));
  }

  public final void generateSucculent(Block block, Block wall, Block flowerPot, BlockModelGenerators blockStateModelGenerators) {
    TexturedModel texturedModel = TEXTURED_SUCCULENT.get(block);
    TextureMapping textureMap = TextureMapping.plant(block);
    ResourceLocation identifier = texturedModel.create(block, blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, identifier));
    ResourceLocation identifier2 = SUCCULENT_WALL.create(wall, texturedModel.getMapping(), blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(wall, Variant.variant().with(VariantProperties.MODEL, identifier2))
        .with(BlockModelGenerators.createHorizontalFacingDispatch()));
    blockStateModelGenerators.createSimpleFlatItemModel(block);
    ResourceLocation identifier3 = FLOWER_POT_SUCCULENT.create(flowerPot, textureMap, blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(createSimpleBlock(flowerPot, identifier3));
  }

  public final void generatePolypore(Block wall, BlockModelGenerators blockStateModelGenerators) {
    ResourceLocation identifier2 = TEXTURED_POLYPORE.create(wall, blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(wall, Variant.variant().with(VariantProperties.MODEL, identifier2))
        .with(BlockModelGenerators.createHorizontalFacingDispatch()));
    blockStateModelGenerators.createSimpleFlatItemModel(wall);
  }

  public final void generateTintedLargeFlower(Block block, Block flowerPot, BlockModelGenerators blockStateModelGenerators) {
    registerSpecificFlowerItemModel(block, blockStateModelGenerators);
    registerTintedTallLargeBlockState(block, TextureMapping.cross(block), blockStateModelGenerators);
    TextureMapping textureMap = TextureMapping.plant(block);
    ResourceLocation identifier = FLOWER_POT_TINTED_LARGE_CROSS.create(flowerPot, textureMap, blockStateModelGenerators.modelOutput);
    blockStateModelGenerators.blockStateOutput.accept(createSimpleBlock(flowerPot, identifier));
  }

  public final void generateLargeFlower(Block block, BlockModelGenerators blockStateModelGenerators) {
    registerSpecificFlowerItemModel(block, blockStateModelGenerators);
    registerTallLargeBlockState(block, TextureMapping.cross(block), blockStateModelGenerators);
  }

  public final void registerCropWithoutItem(Block crop, Property<Integer> ageProperty, BlockModelGenerators blockStateModelGenerator, int... ageTextureIndices) {
    if (ageProperty.getPossibleValues().size() != ageTextureIndices.length) {
      throw new IllegalArgumentException();
    } else {
      Int2ObjectMap<ResourceLocation> int2ObjectMap = new Int2ObjectOpenHashMap();
      PropertyDispatch blockStateVariantMap = PropertyDispatch.property(ageProperty).generate((integer) -> {
        int i = ageTextureIndices[integer];
        ResourceLocation identifier = int2ObjectMap.computeIfAbsent(i, (j) -> blockStateModelGenerator.createSuffixedVariant(crop, "_stage" + i, ModelTemplates.CROP, TextureMapping::crop));
        return Variant.variant().with(VariantProperties.MODEL, identifier);
      });
      blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(crop).with(blockStateVariantMap));
    }
  }

  public final void registerMushroomBlock(Block mushroomBlock, BlockModelGenerators blockStateModelGenerator) {
    ResourceLocation identifier = ModelTemplates.SINGLE_FACE.create(mushroomBlock, TextureMapping.defaultTexture(mushroomBlock), blockStateModelGenerator.modelOutput);
    ResourceLocation identifier2 = ModelLocationUtils.decorateBlockModelLocation("mushroom_block_inside");
    blockStateModelGenerator.blockStateOutput.accept(MultiPartGenerator
        .multiPart(mushroomBlock)
        .with(Condition.condition().term(BlockStateProperties.NORTH, true), Variant.variant().with(VariantProperties.MODEL, identifier))
        .with(Condition.condition().term(BlockStateProperties.EAST, true), Variant
            .variant()
            .with(VariantProperties.MODEL, identifier)
            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
            .with(VariantProperties.UV_LOCK, true))
        .with(Condition.condition().term(BlockStateProperties.SOUTH, true), Variant
            .variant()
            .with(VariantProperties.MODEL, identifier)
            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
            .with(VariantProperties.UV_LOCK, true))
        .with(Condition.condition().term(BlockStateProperties.WEST, true), Variant
            .variant()
            .with(VariantProperties.MODEL, identifier)
            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
            .with(VariantProperties.UV_LOCK, true))
        .with(Condition.condition().term(BlockStateProperties.UP, true), Variant
            .variant()
            .with(VariantProperties.MODEL, identifier)
            .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
            .with(VariantProperties.UV_LOCK, true))
        .with(Condition.condition().term(BlockStateProperties.DOWN, true), Variant
            .variant()
            .with(VariantProperties.MODEL, identifier)
            .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
            .with(VariantProperties.UV_LOCK, true))
        .with(Condition.condition().term(BlockStateProperties.NORTH, false), Variant.variant().with(VariantProperties.MODEL, identifier2))
        .with(Condition.condition().term(BlockStateProperties.EAST, false), Variant
            .variant()
            .with(VariantProperties.MODEL, identifier2)
            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
            .with(VariantProperties.UV_LOCK, false))
        .with(Condition.condition().term(BlockStateProperties.SOUTH, false), Variant
            .variant()
            .with(VariantProperties.MODEL, identifier2)
            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
            .with(VariantProperties.UV_LOCK, false))
        .with(Condition.condition().term(BlockStateProperties.WEST, false), Variant
            .variant()
            .with(VariantProperties.MODEL, identifier2)
            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
            .with(VariantProperties.UV_LOCK, false))
        .with(Condition.condition().term(BlockStateProperties.UP, false), Variant
            .variant()
            .with(VariantProperties.MODEL, identifier2)
            .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
            .with(VariantProperties.UV_LOCK, false))
        .with(Condition.condition().term(BlockStateProperties.DOWN, false), Variant
            .variant()
            .with(VariantProperties.MODEL, identifier2)
            .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
            .with(VariantProperties.UV_LOCK, false)));
    blockStateModelGenerator.delegateItemModel(mushroomBlock, TexturedModel.CUBE.createWithSuffix(mushroomBlock, "_inventory", blockStateModelGenerator.modelOutput));
  }

  private void registerCheese(BlockModelGenerators blockStateModelGenerator) {
    blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(CHEESE_BLOCK).with(
        PropertyDispatch.property(BlockStateProperties.BITES).select(0, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(CHEESE_BLOCK)))
            .select(1, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(CHEESE_BLOCK, "_slice1")))
            .select(2, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(CHEESE_BLOCK, "_slice2")))
            .select(3, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(CHEESE_BLOCK, "_slice3")))
            .select(4, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(CHEESE_BLOCK, "_slice4")))
            .select(5, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(CHEESE_BLOCK, "_slice5")))
            .select(6, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(CHEESE_BLOCK, "_slice6")))));
    blockStateModelGenerator.skipAutoItemBlock(CHEESE_BLOCK);
    blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(CHEESE_CAULDRON,
        ModelTemplates.CAULDRON_FULL.create(CHEESE_CAULDRON, TextureMapping.cauldron(TextureMapping.getBlockTexture(CHEESE_BLOCK).withSuffix("_top")), blockStateModelGenerator.modelOutput)));
    blockStateModelGenerator.skipAutoItemBlock(CHEESE_CAULDRON);
  }

  public final void registerPaperPanels(Block block, Block paperPanel, BlockModelGenerators blockStateModelGenerator) {
    TextureMapping textureMap = TextureMapping.pane(block, paperPanel);
    ResourceLocation identifier = ModelTemplates.STAINED_GLASS_PANE_POST.create(paperPanel, textureMap, blockStateModelGenerator.modelOutput);
    ResourceLocation identifier2 = ModelTemplates.STAINED_GLASS_PANE_SIDE.create(paperPanel, textureMap, blockStateModelGenerator.modelOutput);
    ResourceLocation identifier3 = ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.create(paperPanel, textureMap, blockStateModelGenerator.modelOutput);
    ResourceLocation identifier4 = ModelTemplates.STAINED_GLASS_PANE_NOSIDE.create(paperPanel, textureMap, blockStateModelGenerator.modelOutput);
    ResourceLocation identifier5 = ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.create(paperPanel, textureMap, blockStateModelGenerator.modelOutput);
    Item item = paperPanel.asItem();
    ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(block), blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(MultiPartGenerator.multiPart(paperPanel).with(Variant.variant().with(VariantProperties.MODEL, identifier))
        .with(Condition.condition().term(BlockStateProperties.NORTH, true), Variant.variant().with(VariantProperties.MODEL, identifier2))
        .with(Condition.condition().term(BlockStateProperties.EAST, true), Variant.variant().with(VariantProperties.MODEL, identifier2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
        .with(Condition.condition().term(BlockStateProperties.SOUTH, true), Variant.variant().with(VariantProperties.MODEL, identifier3))
        .with(Condition.condition().term(BlockStateProperties.WEST, true), Variant.variant().with(VariantProperties.MODEL, identifier3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
        .with(Condition.condition().term(BlockStateProperties.NORTH, false), Variant.variant().with(VariantProperties.MODEL, identifier4))
        .with(Condition.condition().term(BlockStateProperties.EAST, false), Variant.variant().with(VariantProperties.MODEL, identifier5))
        .with(Condition.condition().term(BlockStateProperties.SOUTH, false), Variant.variant().with(VariantProperties.MODEL, identifier5).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
        .with(Condition.condition().term(BlockStateProperties.WEST, false), Variant.variant().with(VariantProperties.MODEL, identifier4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));
  }

  public final void registerNorthDefaultHorizontalFacing(TexturedModel.Provider modelFactory, Block block, BlockModelGenerators blockStateModelGenerator) {
    ResourceLocation identifier = modelFactory.create(block, blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(
        MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, identifier)).with(createHorizontalFacingDispatch()));
  }

  public final void registerAzolla(Item item, Block flowerbed, BlockModelGenerators blockStateModelGenerator) {
    blockStateModelGenerator.createSimpleFlatItemModel(item);
    ResourceLocation identifier = TEXTURED_AZOLLA_1.create(flowerbed, blockStateModelGenerator.modelOutput);
    ResourceLocation identifier2 = TEXTURED_AZOLLA_2.create(flowerbed, blockStateModelGenerator.modelOutput);
    ResourceLocation identifier3 = TEXTURED_AZOLLA_3.create(flowerbed, blockStateModelGenerator.modelOutput);
    ResourceLocation identifier4 = TEXTURED_AZOLLA_4.create(flowerbed, blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(MultiPartGenerator.multiPart(flowerbed)
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 1, new Integer[]{2, 3, 4}).term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
            Variant.variant().with(VariantProperties.MODEL, identifier))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 1, new Integer[]{2, 3, 4}).term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
            Variant.variant().with(VariantProperties.MODEL, identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 1, new Integer[]{2, 3, 4}).term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
            Variant.variant().with(VariantProperties.MODEL, identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 1, new Integer[]{2, 3, 4}).term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
            Variant.variant().with(VariantProperties.MODEL, identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 2, new Integer[]{3, 4}).term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
            Variant.variant().with(VariantProperties.MODEL, identifier2))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 2, new Integer[]{3, 4}).term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
            Variant.variant().with(VariantProperties.MODEL, identifier2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 2, new Integer[]{3, 4}).term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
            Variant.variant().with(VariantProperties.MODEL, identifier2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 2, new Integer[]{3, 4}).term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
            Variant.variant().with(VariantProperties.MODEL, identifier2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 3, new Integer[]{4}).term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
            Variant.variant().with(VariantProperties.MODEL, identifier3))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 3, new Integer[]{4}).term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
            Variant.variant().with(VariantProperties.MODEL, identifier3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 3, new Integer[]{4}).term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
            Variant.variant().with(VariantProperties.MODEL, identifier3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 3, new Integer[]{4}).term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
            Variant.variant().with(VariantProperties.MODEL, identifier3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH), Variant.variant().with(VariantProperties.MODEL, identifier4))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
            Variant.variant().with(VariantProperties.MODEL, identifier4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
            Variant.variant().with(VariantProperties.MODEL, identifier4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
        .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
            Variant.variant().with(VariantProperties.MODEL, identifier4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));
  }

  @Override
  public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    generateWoodBlockStateModels(NSRegistryHelper.WoodHashMap, blockStateModelGenerator);
    generateFlowerSetBlockStateModels(NSRegistryHelper.FlowerHashMap, blockStateModelGenerator);
    generateStoneBlockStateModels(NSRegistryHelper.StoneHashMap, blockStateModelGenerator);
    generateTreeBlockStateModels(NSRegistryHelper.SaplingHashMap, NSRegistryHelper.LeavesHashMap, blockStateModelGenerator);

    createOliveBranch(NSWoods.OLIVE_BRANCH, blockStateModelGenerator);

    blockStateModelGenerator.createAmethystCluster(CALCITE_CLUSTER);
    blockStateModelGenerator.createAmethystCluster(SMALL_CALCITE_BUD);
    blockStateModelGenerator.createAmethystCluster(LARGE_CALCITE_BUD);

    registerCheese(blockStateModelGenerator);
    generateFlowerBlockStateModels(FLAXEN_FERN, POTTED_FLAXEN_FERN, blockStateModelGenerator);
    generateFlowerBlockStateModels(SHIITAKE_MUSHROOM, POTTED_SHIITAKE_MUSHROOM, blockStateModelGenerator);
    registerMushroomBlock(SHIITAKE_MUSHROOM_BLOCK, blockStateModelGenerator);
    generatePolypore(GRAY_POLYPORE, blockStateModelGenerator);
    registerMushroomBlock(GRAY_POLYPORE_BLOCK, blockStateModelGenerator);
    registerCropWithoutItem(NSMiscBlocks.DESERT_TURNIP_STEM, DesertTurnipStemBlock.AGE, blockStateModelGenerator, 0, 1, 2, 3, 4, 5, 6, 7);
    generateTallLargeFlower(NSMiscBlocks.TALL_SCORCHED_GRASS, blockStateModelGenerator);
    generateTallLargeFlower(TALL_BEACH_GRASS, blockStateModelGenerator);
    generateTallLargeFlower(TALL_FRIGID_GRASS, blockStateModelGenerator);
    generateTintedTallLargeFlower(LARGE_LUSH_FERN, blockStateModelGenerator);
    generateTallLargeFlower(TALL_SEDGE_GRASS, blockStateModelGenerator);
    generateTallLargeFlower(TALL_OAT_GRASS, blockStateModelGenerator);
    generateTallLargeFlower(TALL_MELIC_GRASS, blockStateModelGenerator);
    generateLargeFlower(NSMiscBlocks.SCORCHED_GRASS, POTTED_SCORCHED_GRASS, blockStateModelGenerator);
    generateLargeFlower(RED_BEARBERRIES, POTTED_RED_BEARBERRIES, blockStateModelGenerator);
    generateLargeFlower(PURPLE_BEARBERRIES, POTTED_PURPLE_BEARBERRIES, blockStateModelGenerator);
    generateLargeFlower(GREEN_BEARBERRIES, POTTED_GREEN_BEARBERRIES, blockStateModelGenerator);
    generateLargeFlower(GREEN_BITTER_SPROUTS, blockStateModelGenerator);
    generateLargeFlower(RED_BITTER_SPROUTS, blockStateModelGenerator);
    generateLargeFlower(PURPLE_BITTER_SPROUTS, blockStateModelGenerator);
    generateLargeFlower(BEACH_GRASS, POTTED_BEACH_GRASS, blockStateModelGenerator);
    generateLargeFlower(SEDGE_GRASS, POTTED_SEDGE_GRASS, blockStateModelGenerator);
    generateLargeFlower(OAT_GRASS, POTTED_OAT_GRASS, blockStateModelGenerator);
    generateLargeFlower(FRIGID_GRASS, POTTED_FRIGID_GRASS, blockStateModelGenerator);
    generateTintedLargeFlower(LUSH_FERN, POTTED_LUSH_FERN, blockStateModelGenerator);
    generateTintedLargeFlower(MELIC_GRASS, POTTED_MELIC_GRASS, blockStateModelGenerator);
    generateVineBlockStateModels(NSWoods.WISTERIA.getBlueVines(), NSWoods.WISTERIA.getBlueVinesPlant(), blockStateModelGenerator);
    generateVineBlockStateModels(NSWoods.WISTERIA.getWhiteVines(), NSWoods.WISTERIA.getWhiteVinesPlant(), blockStateModelGenerator);
    generateVineBlockStateModels(NSWoods.WISTERIA.getPurpleVines(), NSWoods.WISTERIA.getPurpleVinesPlant(), blockStateModelGenerator);
    generateVineBlockStateModels(NSWoods.WISTERIA.getPinkVines(), NSWoods.WISTERIA.getPinkVinesPlant(), blockStateModelGenerator);
    registerAzolla(AZOLLA_ITEM, AZOLLA, blockStateModelGenerator);

    blockStateModelGenerator.woodProvider(ALLUAUDIA_BUNDLE).logWithHorizontal(ALLUAUDIA_BUNDLE);
    blockStateModelGenerator.woodProvider(STRIPPED_ALLUAUDIA_BUNDLE).logWithHorizontal(STRIPPED_ALLUAUDIA_BUNDLE);

    createWoodDoor(PAPER_DOOR, blockStateModelGenerator);
    createWoodTrapdoor(PAPER_TRAPDOOR, blockStateModelGenerator);
    createWoodDoor(FRAMED_PAPER_DOOR, blockStateModelGenerator);
    createWoodTrapdoor(FRAMED_PAPER_TRAPDOOR, blockStateModelGenerator);
    createWoodDoor(BLOOMING_PAPER_DOOR, blockStateModelGenerator);
    createWoodTrapdoor(BLOOMING_PAPER_TRAPDOOR, blockStateModelGenerator);
    createWoodSign(PAPER_SIGN, PAPER_WALL_SIGN, blockStateModelGenerator);
    createHangingSign(PAPER_BLOCK, PAPER_HANGING_SIGN, PAPER_WALL_HANGING_SIGN, blockStateModelGenerator);
    blockStateModelGenerator.createTrivialBlock(PAPER_BLOCK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(FRAMED_PAPER_BLOCK, TexturedModel.CUBE);
    registerNorthDefaultHorizontalFacing(TexturedModel.GLAZED_TERRACOTTA, BLOOMING_PAPER_BLOCK, blockStateModelGenerator);
    registerPaperPanels(PAPER_BLOCK, PAPER_PANEL, blockStateModelGenerator);
    registerPaperPanels(FRAMED_PAPER_BLOCK, FRAMED_PAPER_PANEL, blockStateModelGenerator);
    registerPaperPanels(BLOOMING_PAPER_BLOCK, BLOOMING_PAPER_PANEL, blockStateModelGenerator);

    registerPaperLantern(NSColoredBlocks.PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.WHITE_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.LIGHT_GRAY_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.GRAY_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.BLACK_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.BROWN_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.RED_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.ORANGE_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.YELLOW_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.LIME_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.GREEN_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.CYAN_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.LIGHT_BLUE_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.BLUE_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.PURPLE_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.MAGENTA_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.PINK_PAPER_LANTERN, blockStateModelGenerator);

    createSlab(NSColoredBlocks.KAOLIN, NSColoredBlocks.KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.WHITE_KAOLIN, NSColoredBlocks.WHITE_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIGHT_GRAY_KAOLIN, NSColoredBlocks.LIGHT_GRAY_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.GRAY_KAOLIN, NSColoredBlocks.GRAY_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BLACK_KAOLIN, NSColoredBlocks.BLACK_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BROWN_KAOLIN, NSColoredBlocks.BROWN_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.RED_KAOLIN, NSColoredBlocks.RED_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.ORANGE_KAOLIN, NSColoredBlocks.ORANGE_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.YELLOW_KAOLIN, NSColoredBlocks.YELLOW_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIME_KAOLIN, NSColoredBlocks.LIME_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.GREEN_KAOLIN, NSColoredBlocks.GREEN_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.CYAN_KAOLIN, NSColoredBlocks.CYAN_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIGHT_BLUE_KAOLIN, NSColoredBlocks.LIGHT_BLUE_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BLUE_KAOLIN, NSColoredBlocks.BLUE_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.PURPLE_KAOLIN, NSColoredBlocks.PURPLE_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.MAGENTA_KAOLIN, NSColoredBlocks.MAGENTA_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.PINK_KAOLIN, NSColoredBlocks.PINK_KAOLIN_SLAB, blockStateModelGenerator);

    createStairs(NSColoredBlocks.KAOLIN, NSColoredBlocks.KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.WHITE_KAOLIN, NSColoredBlocks.WHITE_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIGHT_GRAY_KAOLIN, NSColoredBlocks.LIGHT_GRAY_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.GRAY_KAOLIN, NSColoredBlocks.GRAY_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BLACK_KAOLIN, NSColoredBlocks.BLACK_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BROWN_KAOLIN, NSColoredBlocks.BROWN_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.RED_KAOLIN, NSColoredBlocks.RED_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.ORANGE_KAOLIN, NSColoredBlocks.ORANGE_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.YELLOW_KAOLIN, NSColoredBlocks.YELLOW_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIME_KAOLIN, NSColoredBlocks.LIME_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.GREEN_KAOLIN, NSColoredBlocks.GREEN_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.CYAN_KAOLIN, NSColoredBlocks.CYAN_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIGHT_BLUE_KAOLIN, NSColoredBlocks.LIGHT_BLUE_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BLUE_KAOLIN, NSColoredBlocks.BLUE_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.PURPLE_KAOLIN, NSColoredBlocks.PURPLE_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.MAGENTA_KAOLIN, NSColoredBlocks.MAGENTA_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.PINK_KAOLIN, NSColoredBlocks.PINK_KAOLIN_STAIRS, blockStateModelGenerator);

    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.WHITE_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.LIGHT_GRAY_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.GRAY_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.BLACK_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.BROWN_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.RED_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.ORANGE_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.YELLOW_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.LIME_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.GREEN_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.CYAN_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.LIGHT_BLUE_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.BLUE_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.PURPLE_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.MAGENTA_KAOLIN, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.PINK_KAOLIN, TexturedModel.CUBE);

    createSlab(NSColoredBlocks.KAOLIN_BRICKS, NSColoredBlocks.KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.WHITE_KAOLIN_BRICKS, NSColoredBlocks.WHITE_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICKS, NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.GRAY_KAOLIN_BRICKS, NSColoredBlocks.GRAY_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BLACK_KAOLIN_BRICKS, NSColoredBlocks.BLACK_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BROWN_KAOLIN_BRICKS, NSColoredBlocks.BROWN_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.RED_KAOLIN_BRICKS, NSColoredBlocks.RED_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.ORANGE_KAOLIN_BRICKS, NSColoredBlocks.ORANGE_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.YELLOW_KAOLIN_BRICKS, NSColoredBlocks.YELLOW_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIME_KAOLIN_BRICKS, NSColoredBlocks.LIME_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.GREEN_KAOLIN_BRICKS, NSColoredBlocks.GREEN_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.CYAN_KAOLIN_BRICKS, NSColoredBlocks.CYAN_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICKS, NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BLUE_KAOLIN_BRICKS, NSColoredBlocks.BLUE_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.PURPLE_KAOLIN_BRICKS, NSColoredBlocks.PURPLE_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.MAGENTA_KAOLIN_BRICKS, NSColoredBlocks.MAGENTA_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.PINK_KAOLIN_BRICKS, NSColoredBlocks.PINK_KAOLIN_BRICK_SLAB, blockStateModelGenerator);

    createStairs(NSColoredBlocks.KAOLIN_BRICKS, NSColoredBlocks.KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.WHITE_KAOLIN_BRICKS, NSColoredBlocks.WHITE_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICKS, NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.GRAY_KAOLIN_BRICKS, NSColoredBlocks.GRAY_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BLACK_KAOLIN_BRICKS, NSColoredBlocks.BLACK_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BROWN_KAOLIN_BRICKS, NSColoredBlocks.BROWN_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.RED_KAOLIN_BRICKS, NSColoredBlocks.RED_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.ORANGE_KAOLIN_BRICKS, NSColoredBlocks.ORANGE_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.YELLOW_KAOLIN_BRICKS, NSColoredBlocks.YELLOW_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIME_KAOLIN_BRICKS, NSColoredBlocks.LIME_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.GREEN_KAOLIN_BRICKS, NSColoredBlocks.GREEN_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.CYAN_KAOLIN_BRICKS, NSColoredBlocks.CYAN_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICKS, NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BLUE_KAOLIN_BRICKS, NSColoredBlocks.BLUE_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.PURPLE_KAOLIN_BRICKS, NSColoredBlocks.PURPLE_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.MAGENTA_KAOLIN_BRICKS, NSColoredBlocks.MAGENTA_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.PINK_KAOLIN_BRICKS, NSColoredBlocks.PINK_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);

    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.WHITE_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.GRAY_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.BLACK_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.BROWN_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.RED_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.ORANGE_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.YELLOW_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.LIME_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.GREEN_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.CYAN_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.BLUE_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.PURPLE_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.MAGENTA_KAOLIN_BRICKS, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.PINK_KAOLIN_BRICKS, TexturedModel.CUBE);

    createSlab(NSColoredBlocks.WHITE_CHALK, NSColoredBlocks.WHITE_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIGHT_GRAY_CHALK, NSColoredBlocks.LIGHT_GRAY_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.GRAY_CHALK, NSColoredBlocks.GRAY_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BLACK_CHALK, NSColoredBlocks.BLACK_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BROWN_CHALK, NSColoredBlocks.BROWN_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.RED_CHALK, NSColoredBlocks.RED_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.ORANGE_CHALK, NSColoredBlocks.ORANGE_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.YELLOW_CHALK, NSColoredBlocks.YELLOW_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIME_CHALK, NSColoredBlocks.LIME_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.GREEN_CHALK, NSColoredBlocks.GREEN_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.CYAN_CHALK, NSColoredBlocks.CYAN_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIGHT_BLUE_CHALK, NSColoredBlocks.LIGHT_BLUE_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BLUE_CHALK, NSColoredBlocks.BLUE_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.PURPLE_CHALK, NSColoredBlocks.PURPLE_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.MAGENTA_CHALK, NSColoredBlocks.MAGENTA_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.PINK_CHALK, NSColoredBlocks.PINK_CHALK_SLAB, blockStateModelGenerator);

    createStairs(NSColoredBlocks.WHITE_CHALK, NSColoredBlocks.WHITE_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIGHT_GRAY_CHALK, NSColoredBlocks.LIGHT_GRAY_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.GRAY_CHALK, NSColoredBlocks.GRAY_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BLACK_CHALK, NSColoredBlocks.BLACK_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BROWN_CHALK, NSColoredBlocks.BROWN_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.RED_CHALK, NSColoredBlocks.RED_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.ORANGE_CHALK, NSColoredBlocks.ORANGE_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.YELLOW_CHALK, NSColoredBlocks.YELLOW_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIME_CHALK, NSColoredBlocks.LIME_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.GREEN_CHALK, NSColoredBlocks.GREEN_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.CYAN_CHALK, NSColoredBlocks.CYAN_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIGHT_BLUE_CHALK, NSColoredBlocks.LIGHT_BLUE_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BLUE_CHALK, NSColoredBlocks.BLUE_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.PURPLE_CHALK, NSColoredBlocks.PURPLE_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.MAGENTA_CHALK, NSColoredBlocks.MAGENTA_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.PINK_CHALK, NSColoredBlocks.PINK_CHALK_STAIRS, blockStateModelGenerator);

    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.WHITE_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.LIGHT_GRAY_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.GRAY_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.BLACK_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.BROWN_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.RED_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.ORANGE_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.YELLOW_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.LIME_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.GREEN_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.CYAN_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.LIGHT_BLUE_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.BLUE_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.PURPLE_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.MAGENTA_CHALK, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(NSColoredBlocks.PINK_CHALK, TexturedModel.CUBE);

    blockStateModelGenerator.createRotatedPillarWithHorizontalVariant(NSMiscBlocks.DESERT_TURNIP_ROOT_BLOCK, TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
    blockStateModelGenerator.createTrivialBlock(CHERT_COAL_ORE, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(CHERT_COPPER_ORE, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(CHERT_DIAMOND_ORE, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(CHERT_GOLD_ORE, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(CHERT_EMERALD_ORE, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(CHERT_IRON_ORE, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(CHERT_LAPIS_ORE, TexturedModel.CUBE);
    blockStateModelGenerator.createTrivialBlock(CHERT_REDSTONE_ORE, TexturedModel.CUBE);

    generateSucculent(ORNATE_SUCCULENT, ORNATE_WALL_SUCCULENT, POTTED_ORNATE_SUCCULENT, blockStateModelGenerator);
    generateSucculent(DROWSY_SUCCULENT, DROWSY_WALL_SUCCULENT, POTTED_DROWSY_SUCCULENT, blockStateModelGenerator);
    generateSucculent(AUREATE_SUCCULENT, AUREATE_WALL_SUCCULENT, POTTED_AUREATE_SUCCULENT, blockStateModelGenerator);
    generateSucculent(SAGE_SUCCULENT, SAGE_WALL_SUCCULENT, POTTED_SAGE_SUCCULENT, blockStateModelGenerator);
    generateSucculent(FOAMY_SUCCULENT, FOAMY_WALL_SUCCULENT, POTTED_FOAMY_SUCCULENT, blockStateModelGenerator);
    generateSucculent(IMPERIAL_SUCCULENT, IMPERIAL_WALL_SUCCULENT, POTTED_IMPERIAL_SUCCULENT, blockStateModelGenerator);
    generateSucculent(REGAL_SUCCULENT, REGAL_WALL_SUCCULENT, POTTED_REGAL_SUCCULENT, blockStateModelGenerator);
  }

  @Override
  public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    itemModelGenerator.generateFlatItem(NSMiscBlocks.OLIVES, ModelTemplates.FLAT_ITEM);
    itemModelGenerator.generateFlatItem(NSMiscBlocks.DESERT_TURNIP, ModelTemplates.FLAT_ITEM);
    itemModelGenerator.generateFlatItem(NSWoods.COCONUT_SHELL, ModelTemplates.FLAT_ITEM);
    itemModelGenerator.generateFlatItem(NSWoods.COCONUT_HALF, ModelTemplates.FLAT_ITEM);
    itemModelGenerator.generateFlatItem(CALCITE_SHARD, ModelTemplates.FLAT_ITEM);
    itemModelGenerator.generateFlatItem(CHALK_POWDER, ModelTemplates.FLAT_ITEM);
    itemModelGenerator.generateFlatItem(CHEESE_BUCKET, ModelTemplates.FLAT_ITEM);
    itemModelGenerator.generateFlatItem(CHEESE_ARROW, ModelTemplates.FLAT_ITEM);
    itemModelGenerator.generateFlatItem(HELVOLA_FLOWER_ITEM, ModelTemplates.FLAT_ITEM);
    itemModelGenerator.generateFlatItem(HELVOLA_PAD_ITEM, ModelTemplates.FLAT_ITEM);

    NSBoatTypes.getAllBoatItems().forEach(item -> itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
  }
}
