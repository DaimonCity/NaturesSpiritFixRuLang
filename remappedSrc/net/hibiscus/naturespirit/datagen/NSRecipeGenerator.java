package net.hibiscus.naturespirit.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.hibiscus.naturespirit.registration.*;
import net.hibiscus.naturespirit.registration.sets.FlowerSet;
import net.hibiscus.naturespirit.registration.sets.StoneSet;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.server.recipe.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import static net.hibiscus.naturespirit.NatureSpirit.MOD_ID;
import static net.hibiscus.naturespirit.registration.NSMiscBlocks.*;
import static net.minecraft.data.BlockFamilies.familyBuilder;

public class NSRecipeGenerator extends FabricRecipeProvider {

  public NSRecipeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  public static final BlockFamily PINK_SANDSTONE_FAMILY = familyBuilder(NSMiscBlocks.PINK_SANDSTONE).wall(PINK_SANDSTONE_WALL).stairs(PINK_SANDSTONE_STAIRS).slab(PINK_SANDSTONE_SLAB)
      .chiseled(CHISELED_PINK_SANDSTONE).cut(CUT_PINK_SANDSTONE).dontGenerateModel().dontGenerateRecipe().getFamily();
  public static final BlockFamily CUT_PINK_SANDSTONE_FAMILY = familyBuilder(NSMiscBlocks.CUT_PINK_SANDSTONE).slab(CUT_PINK_SANDSTONE_SLAB).dontGenerateModel().getFamily();
  public static final BlockFamily SMOOTH_PINK_SANDSTONE_FAMILY = familyBuilder(NSMiscBlocks.SMOOTH_PINK_SANDSTONE).slab(SMOOTH_PINK_SANDSTONE_SLAB).stairs(SMOOTH_PINK_SANDSTONE_STAIRS)
      .dontGenerateModel().getFamily();


  public static void oneToOneConversionRecipe(RecipeOutput exporter, ItemLike output, ItemLike input, String group, int outputCount) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, outputCount).requires(input).group(group)
        .unlockedBy(RecipeProvider.getHasName(input), RecipeProvider.has(input))
        .save(exporter, ResourceLocation.fromNamespaceAndPath(MOD_ID, RecipeProvider.getConversionRecipeName(output, input)));
  }

  public static void stonecutterResultFromBase(RecipeOutput exporter, RecipeCategory category, ItemLike output, ItemLike input) {
    stonecutterResultFromBase(exporter, category, output, input, 1);
  }

  public static void stonecutterResultFromBase(RecipeOutput exporter, RecipeCategory category, ItemLike output, ItemLike input, int count) {
    SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), category, output, count)
        .unlockedBy(RecipeProvider.getHasName(input), RecipeProvider.has(input))
        .save(exporter, ResourceLocation.fromNamespaceAndPath(MOD_ID, RecipeProvider.getConversionRecipeName(output, input) + "_stonecutting"));
  }

  private void generateWoodRecipes(HashMap<String, WoodSet> woods, RecipeOutput consumer) {
    for (WoodSet woodSet : woods.values()) {
      planksFromLogs(consumer, woodSet.getPlanks(), woodSet.getItemLogsTag(), 4);
      if (woodSet.hasBark()) {
        woodFromLogs(consumer, woodSet.getWood(), woodSet.getLog());
        woodFromLogs(consumer, woodSet.getStrippedWood(), woodSet.getStrippedLog());
      }
      if (woodSet.getWoodPreset() == WoodSet.WoodPreset.JOSHUA) {
        twoByTwoPacker(consumer, RecipeCategory.BUILDING_BLOCKS, woodSet.getBundle(), woodSet.getLog());
        twoByTwoPacker(consumer, RecipeCategory.BUILDING_BLOCKS, woodSet.getStrippedBundle(), woodSet.getStrippedLog());
        oneToOneConversionRecipe(consumer, woodSet.getPlanks(), woodSet.getBundle(), "planks", 4);
        oneToOneConversionRecipe(consumer, woodSet.getPlanks(), woodSet.getStrippedBundle(), "planks", 4);
      }
      if (woodSet.hasMosaic()) {
        mosaicBuilder(consumer, RecipeCategory.BUILDING_BLOCKS, woodSet.getMosaic(), woodSet.getSlab());
        BlockFamily mosaicFamily = familyBuilder(woodSet.getMosaic())
            .stairs(woodSet.getMosaicStairs())
            .slab(woodSet.getMosaicSlab())
            .getFamily();
        generateRecipes(consumer, mosaicFamily, FeatureFlagSet.of(FeatureFlags.VANILLA));
      }
      hangingSign(consumer, woodSet.getHangingSign(), woodSet.getStrippedLog());
      woodenBoat(consumer, woodSet.getBoatItem(), woodSet.getPlanks());
      chestBoat(consumer, woodSet.getChestBoatItem(), woodSet.getBoatItem());
      BlockFamily family = familyBuilder(woodSet.getPlanks())
          .button(woodSet.getButton())
          .fence(woodSet.getFence())
          .fenceGate(woodSet.getFenceGate())
          .pressurePlate(woodSet.getPressurePlate())
          .sign(woodSet.getSign(), woodSet.getWallSign())
          .slab(woodSet.getSlab())
          .stairs(woodSet.getStairs())
          .door(woodSet.getDoor())
          .trapdoor(woodSet.getTrapDoor())
          .recipeGroupPrefix("wooden")
          .recipeUnlockedBy("has_planks")
          .getFamily();
      generateRecipes(consumer, family, FeatureFlagSet.of(FeatureFlags.VANILLA));
    }
  }

  private void generateFlowerRecipes(HashMap<String, FlowerSet> flowers, RecipeOutput consumer) {
    for (FlowerSet flowerSet : flowers.values()) {
      if (flowerSet.getDyeColor() != null) {
        oneToOneConversionRecipe(consumer, flowerSet.getDyeColor(), flowerSet.getFlowerBlock(), flowerSet.getDyeColor().toString(), flowerSet.getDyeNumber());
      }
    }
  }

  private void generateStoneRecipes(HashMap<String, StoneSet> stoones, RecipeOutput exporter) {
    for (StoneSet stoneSet : stoones.values()) {
      generateRecipes(exporter, stoneSet.getBaseFamily(), FeatureFlagSet.of(FeatureFlags.VANILLA));
      generateRecipes(exporter, stoneSet.getBrickFamily(), FeatureFlagSet.of(FeatureFlags.VANILLA));
      generateRecipes(exporter, stoneSet.getPolishedFamily(), FeatureFlagSet.of(FeatureFlags.VANILLA));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricks(), 4)
          .define('S', stoneSet.getPolished()).pattern("SS")
          .pattern("SS")
          .unlockedBy("has_polished_" + stoneSet.getName(), has(stoneSet.getPolished()))
          .save(exporter);

      if (stoneSet.hasTiles()) {
        generateRecipes(exporter, stoneSet.getTileFamily(), FeatureFlagSet.of(FeatureFlags.VANILLA));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stoneSet.getTiles(), 4)
            .define('S', stoneSet.getBricks())
            .pattern("SS").pattern("SS")
            .unlockedBy("has_" + stoneSet.getName() + "_bricks", has(stoneSet.getBricks()))
            .save(exporter);
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTiles(), stoneSet.getPolished());
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesSlab(), stoneSet.getPolished(), 2);
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesStairs(), stoneSet.getPolished());
        stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, stoneSet.getTilesWall(), stoneSet.getPolished());
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTiles(), stoneSet.getBricks());
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesSlab(), stoneSet.getBricks(), 2);
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesStairs(), stoneSet.getBricks());
        stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, stoneSet.getTilesWall(), stoneSet.getBricks());
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesSlab(), stoneSet.getTiles(), 2);
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesStairs(), stoneSet.getTiles());
        stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, stoneSet.getTilesWall(), stoneSet.getTiles());
      }
      if (stoneSet.hasCobbled()) {
        generateRecipes(exporter, stoneSet.getCobbledFamily(), FeatureFlagSet.of(FeatureFlags.VANILLA));
        if (stoneSet.hasMossy()) {
          generateRecipes(exporter, stoneSet.getMossyCobbledFamily(), FeatureFlagSet.of(FeatureFlags.VANILLA));
          ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyCobbled())
              .requires(stoneSet.getCobbled()).requires(Blocks.MOSS_BLOCK)
              .group("mossy_cobblestone")
              .unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
              .save(exporter, ResourceLocation.fromNamespaceAndPath(MOD_ID, getConversionRecipeName(stoneSet.getMossyCobbled(), Blocks.MOSS_BLOCK)));
          ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyCobbled())
              .requires(stoneSet.getCobbled())
              .requires(Blocks.VINE)
              .group("mossy_cobblestone")
              .unlockedBy("has_vine", has(Blocks.VINE))
              .save(exporter, ResourceLocation.fromNamespaceAndPath(MOD_ID, getConversionRecipeName(stoneSet.getMossyCobbled(), Blocks.VINE)));
          stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyCobbledSlab(), stoneSet.getMossyCobbled(), 2);
          stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyCobbledStairs(), stoneSet.getMossyCobbled());
          stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, stoneSet.getMossyCobbledWall(), stoneSet.getMossyCobbled());
        }
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(stoneSet.getCobbled()), RecipeCategory.BUILDING_BLOCKS, stoneSet.getBase(), 0.1F, 200)
            .unlockedBy("has_cobbled_" + stoneSet.getName(), has(stoneSet.getCobbled()))
            .save(exporter);
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getCobbledSlab(), stoneSet.getCobbled(), 2);
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getCobbledStairs(), stoneSet.getCobbled());
        stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, stoneSet.getCobbledWall(), stoneSet.getCobbled());
      }
      if (stoneSet.hasMossy()) {
        generateRecipes(exporter, stoneSet.getMossyBrickFamily(), FeatureFlagSet.of(FeatureFlags.VANILLA));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyBricks())
            .requires(stoneSet.getBricks())
            .requires(Blocks.VINE)
            .group("mossy_stone_bricks")
            .unlockedBy("has_vine", has(Blocks.VINE))
            .save(exporter, ResourceLocation.fromNamespaceAndPath(MOD_ID, getConversionRecipeName(stoneSet.getMossyBricks(), Blocks.VINE)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyBricks())
            .requires(stoneSet.getBricks())
            .requires(Blocks.MOSS_BLOCK)
            .group("mossy_stone_bricks")
            .unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
            .save(exporter, ResourceLocation.fromNamespaceAndPath(MOD_ID, getConversionRecipeName(stoneSet.getMossyBricks(), Blocks.MOSS_BLOCK)));
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyBricksSlab(), stoneSet.getMossyBricks(), 2);
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyBricksStairs(), stoneSet.getMossyBricks());
        stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, stoneSet.getMossyBricksWall(), stoneSet.getMossyBricks());
      }
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBaseSlab(), stoneSet.getBase(), 2);
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBaseStairs(), stoneSet.getBase());
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getChiseled(), stoneSet.getBase());
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getPolished(), stoneSet.getBase());
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getPolishedSlab(), stoneSet.getBase(), 2);
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getPolishedStairs(), stoneSet.getBase());
      stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, stoneSet.getPolishedWall(), stoneSet.getBase());
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricks(), stoneSet.getBase());
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksSlab(), stoneSet.getBase(), 2);
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksStairs(), stoneSet.getBase());
      stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, stoneSet.getBricksWall(), stoneSet.getBase());
      if (stoneSet.hasTiles()) {
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTiles(), stoneSet.getBase());
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesSlab(), stoneSet.getBase(), 2);
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesStairs(), stoneSet.getBase());
        stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, stoneSet.getTilesWall(), stoneSet.getBase());
      }
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getPolishedSlab(), stoneSet.getPolished(), 2);
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getPolishedStairs(), stoneSet.getPolished());
      stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, stoneSet.getPolishedWall(), stoneSet.getPolished());
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricks(), stoneSet.getPolished());
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksSlab(), stoneSet.getPolished(), 2);
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksStairs(), stoneSet.getPolished());
      stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, stoneSet.getBricksWall(), stoneSet.getPolished());
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksSlab(), stoneSet.getBricks(), 2);
      stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksStairs(), stoneSet.getBricks());
      stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, stoneSet.getBricksWall(), stoneSet.getBricks());
    }
  }

  public static void offer2x2CompactingTagRecipe(RecipeOutput exporter, RecipeCategory category, ItemLike output, TagKey<Item> input) {
    ShapedRecipeBuilder.shaped(category, output, 1).define('#', input).pattern("##").pattern("##").unlockedBy("has_evergreen_leaves", has(input)).save(exporter);
  }

  @Override
  protected ResourceLocation getRecipeIdentifier(ResourceLocation identifier) {
    return ResourceLocation.fromNamespaceAndPath(MOD_ID, identifier.getPath());
  }

  @Override
  public void buildRecipes(RecipeOutput exporter) {
    twoByTwoPacker(exporter, RecipeCategory.BUILDING_BLOCKS, ALLUAUDIA_BUNDLE, ALLUAUDIA);
    twoByTwoPacker(exporter, RecipeCategory.BUILDING_BLOCKS, STRIPPED_ALLUAUDIA_BUNDLE, STRIPPED_ALLUAUDIA);
    chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, CHISELED_PINK_SANDSTONE, Ingredient.of(PINK_SANDSTONE_SLAB))
        .unlockedBy("has_pink_sandstone", has(NSMiscBlocks.PINK_SANDSTONE))
        .unlockedBy("has_chiseled_pink_sandstone", has(CHISELED_PINK_SANDSTONE))
        .unlockedBy("has_cut_pink_sandstone", has(NSMiscBlocks.CUT_PINK_SANDSTONE))
        .save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.PINK_SANDSTONE)
        .define('#', PINK_SAND).pattern("##")
        .pattern("##").unlockedBy("has_sand", has(NSMiscBlocks.PINK_SAND))
        .save(exporter);
    slabBuilder(RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.PINK_SANDSTONE_SLAB, Ingredient.of(
        NSMiscBlocks.PINK_SANDSTONE,
        NSMiscBlocks.CHISELED_PINK_SANDSTONE
    ))
        .unlockedBy("has_pink_sandstone", has(NSMiscBlocks.PINK_SANDSTONE)).unlockedBy("has_chiseled_pink_sandstone", has(
            NSMiscBlocks.CHISELED_PINK_SANDSTONE))
        .save(exporter);
    stairBuilder(NSMiscBlocks.PINK_SANDSTONE_STAIRS, Ingredient.of(
        NSMiscBlocks.PINK_SANDSTONE,
        NSMiscBlocks.CHISELED_PINK_SANDSTONE,
        NSMiscBlocks.CUT_PINK_SANDSTONE
    ))
        .unlockedBy("has_pink_sandstone", has(NSMiscBlocks.PINK_SANDSTONE))
        .unlockedBy("has_chiseled_pink_sandstone", has(NSMiscBlocks.CHISELED_PINK_SANDSTONE))
        .unlockedBy("has_cut_pink_sandstone", has(NSMiscBlocks.CUT_PINK_SANDSTONE))
        .save(exporter);
    cut(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.CUT_PINK_SANDSTONE, NSMiscBlocks.PINK_SANDSTONE);
    wall(exporter, RecipeCategory.DECORATIONS, NSMiscBlocks.PINK_SANDSTONE_WALL, NSMiscBlocks.PINK_SANDSTONE);
    SimpleCookingRecipeBuilder.smelting(Ingredient.of(NSMiscBlocks.PINK_SANDSTONE), RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.SMOOTH_PINK_SANDSTONE.asItem(), 0.1F, 200)
        .unlockedBy("has_pink_sandstone", has(NSMiscBlocks.PINK_SANDSTONE))
        .save(exporter);
    stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.CUT_PINK_SANDSTONE, NSMiscBlocks.PINK_SANDSTONE);
    stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.PINK_SANDSTONE_SLAB, NSMiscBlocks.PINK_SANDSTONE, 2);
    stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.CUT_PINK_SANDSTONE_SLAB, NSMiscBlocks.PINK_SANDSTONE, 2);
    stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.CUT_PINK_SANDSTONE_SLAB, NSMiscBlocks.CUT_PINK_SANDSTONE, 2);
    stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.PINK_SANDSTONE_STAIRS, NSMiscBlocks.PINK_SANDSTONE);
    stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, NSMiscBlocks.PINK_SANDSTONE_WALL, NSMiscBlocks.PINK_SANDSTONE);
    stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.CHISELED_PINK_SANDSTONE, NSMiscBlocks.PINK_SANDSTONE);
    stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.SMOOTH_PINK_SANDSTONE_SLAB, NSMiscBlocks.SMOOTH_PINK_SANDSTONE, 2);
    stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.SMOOTH_PINK_SANDSTONE_STAIRS, NSMiscBlocks.SMOOTH_PINK_SANDSTONE);

    carpet(exporter, NSWoods.COCONUT_THATCH_CARPET, NSWoods.COCONUT_THATCH);
    slab(exporter, RecipeCategory.BUILDING_BLOCKS, NSWoods.COCONUT_THATCH_SLAB, NSWoods.COCONUT_THATCH);
    stairBuilder(NSWoods.COCONUT_THATCH_STAIRS, Ingredient.of(NSWoods.COCONUT_THATCH)).unlockedBy(getHasName(NSWoods.COCONUT_THATCH),
        has(NSWoods.COCONUT_THATCH)).save(exporter);
    twoByTwoPacker(exporter, RecipeCategory.BUILDING_BLOCKS, NSWoods.COCONUT_THATCH, NSWoods.COCONUT.getLeaves());

    carpet(exporter, NSWoods.EVERGREEN_THATCH_CARPET, NSWoods.EVERGREEN_THATCH);
    slab(exporter, RecipeCategory.BUILDING_BLOCKS, NSWoods.EVERGREEN_THATCH_SLAB, NSWoods.EVERGREEN_THATCH);
    stairBuilder(NSWoods.EVERGREEN_THATCH_STAIRS, Ingredient.of(NSWoods.EVERGREEN_THATCH)).unlockedBy(getHasName(NSWoods.EVERGREEN_THATCH),
        has(NSWoods.EVERGREEN_THATCH)).save(exporter);
    offer2x2CompactingTagRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSWoods.EVERGREEN_THATCH, NSTags.Items.EVERGREEN_LEAVES);

    carpet(exporter, NSWoods.XERIC_THATCH_CARPET, NSWoods.XERIC_THATCH);
    slab(exporter, RecipeCategory.BUILDING_BLOCKS, NSWoods.XERIC_THATCH_SLAB, NSWoods.XERIC_THATCH);
    stairBuilder(NSWoods.XERIC_THATCH_STAIRS, Ingredient.of(NSWoods.XERIC_THATCH)).unlockedBy(getHasName(NSWoods.XERIC_THATCH), has(NSWoods.XERIC_THATCH))
        .save(exporter);
    offer2x2CompactingTagRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSWoods.XERIC_THATCH, NSTags.Items.XERIC_LEAVES);

    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.KAOLIN).pattern("SS")
        .pattern("SS").unlockedBy("has_kaolin", has(NSColoredBlocks.KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.WHITE_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.WHITE_KAOLIN).pattern("SS")
        .pattern("SS").unlockedBy("has_white_kaolin", has(NSColoredBlocks.WHITE_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.LIGHT_GRAY_KAOLIN)
        .pattern("SS").pattern("SS").unlockedBy("has_light_gray_kaolin", has(NSColoredBlocks.LIGHT_GRAY_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.GRAY_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.GRAY_KAOLIN).pattern("SS")
        .pattern("SS").unlockedBy("has_gray_kaolin", has(NSColoredBlocks.GRAY_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.BLACK_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.BLACK_KAOLIN).pattern("SS")
        .pattern("SS").unlockedBy("has_black_kaolin", has(NSColoredBlocks.BLACK_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.BROWN_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.BROWN_KAOLIN).pattern("SS")
        .pattern("SS").unlockedBy("has_brown_kaolin", has(NSColoredBlocks.BROWN_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.RED_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.RED_KAOLIN).pattern("SS")
        .pattern("SS").unlockedBy("has_red_kaolin", has(NSColoredBlocks.RED_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.ORANGE_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.ORANGE_KAOLIN)
        .pattern("SS").pattern("SS").unlockedBy("has_orange_kaolin", has(NSColoredBlocks.ORANGE_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.YELLOW_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.YELLOW_KAOLIN)
        .pattern("SS").pattern("SS").unlockedBy("has_yellow_kaolin", has(NSColoredBlocks.YELLOW_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.LIME_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.LIME_KAOLIN).pattern("SS")
        .pattern("SS").unlockedBy("has_lime_kaolin", has(NSColoredBlocks.LIME_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.GREEN_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.GREEN_KAOLIN).pattern("SS")
        .pattern("SS").unlockedBy("has_green_kaolin", has(NSColoredBlocks.GREEN_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.CYAN_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.CYAN_KAOLIN).pattern("SS")
        .pattern("SS").unlockedBy("has_cyan_kaolin", has(NSColoredBlocks.CYAN_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.LIGHT_BLUE_KAOLIN)
        .pattern("SS").pattern("SS").unlockedBy("has_light_blue_kaolin", has(NSColoredBlocks.LIGHT_BLUE_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.BLUE_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.BLUE_KAOLIN).pattern("SS")
        .pattern("SS").unlockedBy("has_blue_kaolin", has(NSColoredBlocks.BLUE_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.PURPLE_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.PURPLE_KAOLIN)
        .pattern("SS").pattern("SS").unlockedBy("has_purple_kaolin", has(NSColoredBlocks.PURPLE_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.MAGENTA_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.MAGENTA_KAOLIN)
        .pattern("SS").pattern("SS").unlockedBy("has_magenta_kaolin", has(NSColoredBlocks.MAGENTA_KAOLIN)).save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.PINK_KAOLIN_BRICKS, 4).define(Character.valueOf('S'), NSColoredBlocks.PINK_KAOLIN).pattern("SS")
        .pattern("SS").unlockedBy("has_pink_kaolin", has(NSColoredBlocks.PINK_KAOLIN)).save(exporter);

    carpet(exporter, RED_MOSS_CARPET, RED_MOSS_BLOCK);

    generateFlowerRecipes(NSRegistryHelper.FlowerHashMap, exporter);
    generateWoodRecipes(NSRegistryHelper.WoodHashMap, exporter);
    generateStoneRecipes(NSRegistryHelper.StoneHashMap, exporter);
    oneToOneConversionRecipe(exporter, Items.BROWN_DYE, NSMiscBlocks.CATTAIL, "brown_dye", 2);
    oneToOneConversionRecipe(exporter, Items.PINK_DYE, LOTUS_FLOWER, "pink_dye", 1);
    oneToOneConversionRecipe(exporter, Items.WHITE_DYE, HELVOLA_FLOWER_ITEM, "white_dye", 1);
    oneToOneConversionRecipe(exporter, Items.RED_DYE, ORNATE_SUCCULENT, "red_dye", 1);
    oneToOneConversionRecipe(exporter, Items.LIME_DYE, DROWSY_SUCCULENT, "lime_dye", 1);
    oneToOneConversionRecipe(exporter, Items.YELLOW_DYE, AUREATE_SUCCULENT, "yellow_dye", 1);
    oneToOneConversionRecipe(exporter, Items.GREEN_DYE, SAGE_SUCCULENT, "green_dye", 1);
    oneToOneConversionRecipe(exporter, Items.LIGHT_BLUE_DYE, FOAMY_SUCCULENT, "light_blue_dye", 1);
    oneToOneConversionRecipe(exporter, Items.PURPLE_DYE, IMPERIAL_SUCCULENT, "purple_dye", 1);
    oneToOneConversionRecipe(exporter, Items.PINK_DYE, REGAL_SUCCULENT, "pink_dye", 1);
    threeByThreePacker(exporter, RecipeCategory.FOOD, NSMiscBlocks.DESERT_TURNIP_BLOCK, NSMiscBlocks.DESERT_TURNIP, "desert_turnip");
    twoByTwoPacker(exporter, RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.WHITE_CHALK, NSMiscBlocks.CHALK_POWDER);

    oneToOneConversionRecipe(exporter, NSWoods.COCONUT_HALF, NSWoods.COCONUT_BLOCK, "coconut_half", 2);
    oneToOneConversionRecipe(exporter, Items.BOWL, NSWoods.COCONUT_SHELL, "bowl", 1);
    SimpleCookingRecipeBuilder.smelting(Ingredient.of(NSTags.Items.COCONUT_ITEMS), RecipeCategory.MISC, Items.CHARCOAL, 0.15F, 125)
        .unlockedBy("has_coconut", has(NSTags.Items.COCONUT_ITEMS)).save(exporter, ResourceLocation.fromNamespaceAndPath(MOD_ID, "charcoal_from_coconuts"));

    generateRecipes(exporter, CUT_PINK_SANDSTONE_FAMILY, FeatureFlagSet.of(FeatureFlags.VANILLA));
    generateRecipes(exporter, SMOOTH_PINK_SANDSTONE_FAMILY, FeatureFlagSet.of(FeatureFlags.VANILLA));

  }
}
