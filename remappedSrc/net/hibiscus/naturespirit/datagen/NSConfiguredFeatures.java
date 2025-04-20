package net.hibiscus.naturespirit.datagen;

import com.google.common.collect.ImmutableList;
import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.blocks.DesertTurnipStemBlock;
import net.hibiscus.naturespirit.blocks.DownwardVineBlock;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSTags;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.hibiscus.naturespirit.world.feature.NSSimpleBlockStateProvider;
import net.hibiscus.naturespirit.world.feature.TurnipRootFeatureConfig;
import net.hibiscus.naturespirit.world.foliage_placer.*;
import net.hibiscus.naturespirit.world.tree_decorator.*;
import net.hibiscus.naturespirit.world.trunk.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.registry.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.Fluids;
import java.util.List;
import java.util.OptionalInt;

import static net.hibiscus.naturespirit.registration.NSWorldGen.*;

public class NSConfiguredFeatures {

  public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MOSS_PATCH_BONEMEAL = registerKey("red_moss_patch_bonemeal");
  public static final ResourceKey<ConfiguredFeature<?, ?>> WISTERIA_DELTA = registerKey("water_delta");
  public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_DELTA = registerKey("swamp_delta");
  public static final ResourceKey<ConfiguredFeature<?, ?>> MARSH_DELTA = registerKey("marsh_delta");
  public static final ResourceKey<ConfiguredFeature<?, ?>> RIVER_DELTA = registerKey("river_delta");

  public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_REDWOOD_TREE = registerKey("large_redwood_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD_TREE = registerKey("redwood_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_REDWOOD_TREE_SPAWN = registerKey("large_redwood_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD_TREE_SPAWN = registerKey("redwood_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_FROSTY_REDWOOD_TREE = registerKey("large_frosty_redwood_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTY_REDWOOD_TREE = registerKey("frosty_redwood_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_FROSTY_REDWOOD_TREE_SPAWN = registerKey("large_frosty_redwood_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTY_REDWOOD_TREE_SPAWN = registerKey("frosty_redwood_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> WILLOW_TREE = registerKey("willow_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> WILLOW_TREE_SPAWN = registerKey("willow_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_WISTERIA_TREE = registerKey("white_wisteria_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_WISTERIA_TREE = registerKey("blue_wisteria_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_WISTERIA_TREE = registerKey("purple_wisteria_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_WISTERIA_TREE = registerKey("pink_wisteria_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_TREE = registerKey("aspen_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_TREE_BEES = registerKey("aspen_tree_bees");
  public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_TREE_SPAWN = registerKey("aspen_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_ASPEN_TREE = registerKey("yellow_aspen_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_ASPEN_TREE_BEES = registerKey("yellow_aspen_tree_bees");
  public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_ASPEN_TREE_SPAWN = registerKey("yellow_aspen_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_TREE = registerKey("red_maple_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE_TREE = registerKey("orange_maple_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_MAPLE_TREE = registerKey("yellow_maple_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_SPAWN = registerKey("maple_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> FIR_TREE = registerKey("fir_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> FIR_TREE_SPAWN = registerKey("fir_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> LARCH_TREE = registerKey("larch_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> LARCH_TREE_SPAWN = registerKey("larch_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> WISTERIA_SPAWN = registerKey("wisteria_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> SUGI_TREE = registerKey("sugi_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_SUGI_TREE = registerKey("large_sugi_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> SUGI_SPAWN = registerKey("sugi_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_SUGI_SPAWN = registerKey("large_sugi_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_TREE = registerKey("cypress_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_TREE_SPAWN = registerKey("cypress_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> OLIVE_TREE = registerKey("olive_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> OLIVE_TREE_SPAWN = registerKey("olive_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> GHAF_TREE = registerKey("ghaf_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> GHAF_TREE_SPAWN = registerKey("ghaf_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> PALO_VERDE_TREE = registerKey("palo_verde_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> PALO_VERDE_TREE_SPAWN = registerKey("palo_verde_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> MAHOGANY_TREE = registerKey("mahogany_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> MAHOGANY_TREE_SPAWN = registerKey("mahogany_tree_spawn");
  //   public static final RegistryKey <ConfiguredFeature <?, ?>> BANYAN_TREE = registerKey("banyan_tree");
//   public static final RegistryKey <ConfiguredFeature <?, ?>> BANYAN_TREE_SPAWN = registerKey("banyan_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> SAXAUL_TREE = registerKey("saxaul_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> SAXAUL_TREE_SPAWN = registerKey("saxaul_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> JOSHUA_TREE = registerKey("joshua_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> JOSHUA_TREE_SPAWN = registerKey("joshua_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> ALLUAUDIA = registerKey("alluaudia");
  public static final ResourceKey<ConfiguredFeature<?, ?>> ALLUAUDIA_SPAWN = registerKey("alluaudia_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> COCONUT_TREE = registerKey("coconut_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> COCONUT_TREE_SPAWN = registerKey("coconut_tree_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CEDAR_TREE = registerKey("cedar_tree");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CEDAR_TREE_SPAWN = registerKey("cedar_tree_spawn");

  public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_BUSH = registerKey("oak_bush");
  public static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE_BUSH = registerKey("spruce_bush");
  public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_BUSH_SPAWN = registerKey("oak_bush_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE_BUSH_SPAWN = registerKey("spruce_bush_spawn");
  public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK_TREE_SPAWN = registerKey("custom_fancy_oak_tree_spawn");


  public static final ResourceKey<ConfiguredFeature<?, ?>> PUMPKIN_PATCH_FEATURE = registerKey("pumpkin_patch_feature");
  public static final ResourceKey<ConfiguredFeature<?, ?>> PUMPKIN_FEATURE = registerKey("pumpkin_feature");

  public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_WISTERIA_FOREST = registerKey("flower_wisteria_forest");
  public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_SUGI_FOREST = registerKey("flower_sugi_forest");
  public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_ERODED_RIVER = registerKey("flower_eroded_river");
  public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_GOLDEN_WILDS = registerKey("flower_golden_wilds");
  public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_SHIITAKE_MUSHROOM = registerKey("huge_shiitake_mushroom");
  public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_CYPRESS_FIELDS = registerKey("flower_cypress_fields");
  public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SCORCHED_GRASS = registerKey("patch_scorched_grass");
  public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_SCORCHED_GRASS = registerKey("patch_tall_scorched_grass");
  public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_STRATIFIED_DESERT = registerKey("flower_stratified_desert");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CATTAILS = registerKey("cattails");
  public static final ResourceKey<ConfiguredFeature<?, ?>> LOTUS_PLANT = registerKey("lotus_plant");
  public static final ResourceKey<ConfiguredFeature<?, ?>> ROOTED_DESERT_TURNIP = registerKey("rooted_desert_turnip");
  public static final ResourceKey<ConfiguredFeature<?, ?>> GRAY_POLYPORE = registerKey("gray_polypore");


  public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    var placedFeatureRegistryEntryLookup = context.lookup(Registries.PLACED_FEATURE);
    var configuredFeatureRegistryEntryLookup = context.lookup(Registries.CONFIGURED_FEATURE);
    HolderGetter<Block> holderGetter = context.lookup(Registries.BLOCK);

    register(context, REDWOOD_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.REDWOOD.getLog()),
        new StraightTrunkPlacer(20, 1, 10),
        BlockStateProvider.simple(NSWoods.REDWOOD.getLeaves()),
        new RedwoodFoliagePlacer(UniformInt.of(1, 3), UniformInt.of(1, 2), UniformInt.of(22, 32)),
        new TwoLayersFeatureSize(2, 0, 2)
    ).ignoreVines().decorators(
        ImmutableList.of(new RedwoodBranchTreeDecorator(.1f, BlockStateProvider.simple(NSWoods.REDWOOD.getLeaves())), new PolyporeTreeDecorator(.3f, .2f, .05f, BlockStateProvider.simple(
            NSMiscBlocks.GRAY_POLYPORE_BLOCK), BlockStateProvider.simple(NSMiscBlocks.GRAY_POLYPORE)))).build());

    register(
        context,
        REDWOOD_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.REDWOOD_CHECKED), 0.5f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.REDWOOD_CHECKED)
        )
    );

    register(context, LARGE_REDWOOD_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.REDWOOD.getLog()),
        new GiantTrunkPlacer(25, 1, 6),
        BlockStateProvider.simple(NSWoods.REDWOOD.getLeaves()),
        new RedwoodFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(27, 31)),
        new TwoLayersFeatureSize(2, 0, 2)
    ).decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)),
        new RedwoodBranchTreeDecorator(.05f, BlockStateProvider.simple(NSWoods.REDWOOD.getLeaves())), new PolyporeTreeDecorator(.2f, .1f, .05f, BlockStateProvider.simple(
            NSMiscBlocks.GRAY_POLYPORE_BLOCK), BlockStateProvider.simple(NSMiscBlocks.GRAY_POLYPORE)))).build());

    register(
        context,
        LARGE_REDWOOD_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARGE_REDWOOD_CHECKED), 0.5f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARGE_REDWOOD_CHECKED)
        )
    );

    register(context, FROSTY_REDWOOD_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.REDWOOD.getLog()),
        new StraightTrunkPlacer(20, 1, 10),
        BlockStateProvider.simple(NSWoods.REDWOOD.getFrostyLeaves()),
        new RedwoodFoliagePlacer(UniformInt.of(1, 3), UniformInt.of(1, 2), UniformInt.of(22, 32)),
        new TwoLayersFeatureSize(2, 0, 2)
    ).ignoreVines().decorators(ImmutableList.of(new RedwoodBranchTreeDecorator(.1f, BlockStateProvider.simple(NSWoods.REDWOOD.getFrostyLeaves())))).build());

    register(
        context,
        FROSTY_REDWOOD_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.FROSTY_REDWOOD_CHECKED), 0.5f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.FROSTY_REDWOOD_CHECKED)
        )
    );

    register(context, LARGE_FROSTY_REDWOOD_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.REDWOOD.getLog()),
        new GiantTrunkPlacer(25, 1, 6),
        BlockStateProvider.simple(NSWoods.REDWOOD.getFrostyLeaves()),
        new RedwoodFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(27, 31)),
        new TwoLayersFeatureSize(2, 0, 2)
    ).decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)),
        new RedwoodBranchTreeDecorator(.05f, BlockStateProvider.simple(NSWoods.REDWOOD.getFrostyLeaves())))).build());

    register(
        context,
        LARGE_FROSTY_REDWOOD_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARGE_FROSTY_REDWOOD_CHECKED), 0.5f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARGE_FROSTY_REDWOOD_CHECKED)
        )
    );

    register(context, ASPEN_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.ASPEN.getLog()),
        new StraightTrunkPlacer(14, 2, 5),
        BlockStateProvider.simple(NSWoods.ASPEN.getLeaves()),
        new AspenFoliagePlacer(UniformInt.of(2, 2), UniformInt.of(2, 3), UniformInt.of(4, 18)),
        new TwoLayersFeatureSize(1, 0, 1)
    ).ignoreVines().build());
    register(context, ASPEN_TREE_BEES, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.ASPEN.getLog()),
        new StraightTrunkPlacer(14, 2, 5),
        BlockStateProvider.simple(NSWoods.ASPEN.getLeaves()),
        new AspenFoliagePlacer(UniformInt.of(2, 2), UniformInt.of(2, 3), UniformInt.of(4, 18)),
        new TwoLayersFeatureSize(1, 0, 1)
    ).ignoreVines().decorators(List.of(new BeehiveDecorator(1.0F))).build());

    register(
        context,
        ASPEN_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.ASPEN_BEES_CHECKED), 0.001f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.ASPEN_CHECKED)
        )
    );

    register(context, YELLOW_ASPEN_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.ASPEN.getLog()),
        new StraightTrunkPlacer(14, 2, 5),
        BlockStateProvider.simple(NSWoods.ASPEN.getYellowLeaves()),
        new AspenFoliagePlacer(UniformInt.of(2, 2), UniformInt.of(2, 3), UniformInt.of(4, 18)),
        new TwoLayersFeatureSize(1, 0, 1)
    ).ignoreVines().build());
    register(context, YELLOW_ASPEN_TREE_BEES, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.ASPEN.getLog()),
        new StraightTrunkPlacer(14, 2, 5),
        BlockStateProvider.simple(NSWoods.ASPEN.getYellowLeaves()),
        new AspenFoliagePlacer(UniformInt.of(2, 2), UniformInt.of(2, 3), UniformInt.of(4, 18)),
        new TwoLayersFeatureSize(1, 0, 1)
    ).ignoreVines().decorators(List.of(new BeehiveDecorator(1.0F))).build());

    register(
        context,
        YELLOW_ASPEN_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.YELLOW_ASPEN_BEES_CHECKED), 0.001f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.YELLOW_ASPEN_CHECKED)
        )
    );

    register(context, RED_MAPLE_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.MAPLE.getLog()),
        new MapleTrunkPlacer(9, 2, 0, new WeightedListInt(SimpleWeightedRandomList
            .<IntProvider>builder()
            .add(ConstantInt.of(5), 1)
            .add(ConstantInt.of(2), 1)
            .add(ConstantInt.of(3), 1)
            .add(ConstantInt.of(4), 1)
            .build()), UniformInt.of(1, 3), UniformInt.of(-5, -4), UniformInt.of(-4, -1)),
        BlockStateProvider.simple(NSWoods.MAPLE.getRedLeaves()),
        new MapleFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(5), 0.26666667F, 0.53333334F),
        new TwoLayersFeatureSize(1, 0, 2)
    ).ignoreVines().decorators(ImmutableList.of(new MapleGroundTreeDecorator(SimpleStateProvider.simple(Blocks.PODZOL), SimpleStateProvider.simple(Blocks.COARSE_DIRT))))
        .build());
    register(context, ORANGE_MAPLE_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.MAPLE.getLog()),
        new MapleTrunkPlacer(
            9,
            2,
            0,
            new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(5), 1).add(
                ConstantInt.of(2),
                1
            ).add(ConstantInt.of(3), 1).add(ConstantInt.of(4), 1).build()),
            UniformInt.of(1, 3),
            UniformInt.of(-5, -4),
            UniformInt.of(-4, -1)
        ),
        BlockStateProvider.simple(NSWoods.MAPLE.getOrangeLeaves()),
        new MapleFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(5), 0.26666667F, 0.53333334F),
        new TwoLayersFeatureSize(1, 0, 2)
    ).ignoreVines().decorators(ImmutableList.of(new MapleGroundTreeDecorator(SimpleStateProvider.simple(Blocks.PODZOL), SimpleStateProvider.simple(Blocks.COARSE_DIRT))))
        .build());
    register(context, YELLOW_MAPLE_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.MAPLE.getLog()),
        new MapleTrunkPlacer(
            9,
            2,
            0,
            new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(5), 1).add(
                ConstantInt.of(2),
                1
            ).add(ConstantInt.of(3), 1).add(ConstantInt.of(4), 1).build()),
            UniformInt.of(1, 3),
            UniformInt.of(-5, -4),
            UniformInt.of(-4, -1)
        ),
        BlockStateProvider.simple(NSWoods.MAPLE.getYellowLeaves()),
        new MapleFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(5), 0.26666667F, 0.53333334F),
        new TwoLayersFeatureSize(1, 0, 2)
    ).ignoreVines().decorators(ImmutableList.of(new MapleGroundTreeDecorator(SimpleStateProvider.simple(Blocks.PODZOL), SimpleStateProvider.simple(Blocks.COARSE_DIRT))))
        .build());

    register(context,
        MAPLE_SPAWN,
        Feature.SIMPLE_RANDOM_SELECTOR,
        new SimpleRandomFeatureConfiguration(HolderSet.direct(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.RED_MAPLE_CHECKED),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.ORANGE_MAPLE_CHECKED),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.YELLOW_MAPLE_CHECKED),
            placedFeatureRegistryEntryLookup.getOrThrow(TreePlacements.FANCY_OAK_CHECKED)
        ))
    );

    register(context, CYPRESS_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.CYPRESS.getLog()),
        new StraightTrunkPlacer(11, 1, 2),
        BlockStateProvider.simple(NSWoods.CYPRESS.getLeaves()),
        new CypressFoliagePlacer(UniformInt.of(2, 2), UniformInt.of(3, 3), UniformInt.of(11, 13)),
        new TwoLayersFeatureSize(1, 0, 1)
    ).ignoreVines().build());

    register(context, FIR_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.FIR.getLog()),
        new StraightTrunkPlacer(10, 1, 2),
        BlockStateProvider.simple(NSWoods.FIR.getLeaves()),
        new FirFoliagePlacer(UniformInt.of(2, 2), UniformInt.of(2, 3), UniformInt.of(3, 12)),
        new TwoLayersFeatureSize(1, 0, 1)
    ).ignoreVines().build());

    register(context,
        FIR_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.FIR_CHECKED), 0.5f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.FIR_CHECKED)
        )
    );

    register(context, MAHOGANY_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.MAHOGANY.getLog()),
        new MahoganyTrunkPlacer(18, 0, 5),
        BlockStateProvider.simple(NSWoods.MAHOGANY.getLeaves()),
        new AcaciaFoliagePlacer(UniformInt.of(2, 2), UniformInt.of(2, 2)),
        new TwoLayersFeatureSize(1, 0, 1)
    ).ignoreVines().decorators(ImmutableList.of(new LeaveVineDecorator(.4f))).build());

    register(context,
        MAHOGANY_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.MAHOGANY_CHECKED), 0.5f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.MAHOGANY_CHECKED)
        )
    );

    register(context, LARCH_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.LARCH.getLog()),
        new StraightTrunkPlacer(12, 0, 4),
        BlockStateProvider.simple(NSWoods.LARCH.getLeaves()),
        new LarchFoliagePlacer(UniformInt.of(2, 2), UniformInt.of(2, 2), UniformInt.of(13, 15)),
        new TwoLayersFeatureSize(1, 0, 1)
    ).ignoreVines().build());

    register(context,
        LARCH_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARCH_CHECKED), 0.5f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARCH_CHECKED)
        )
    );

    register(context,
        WISTERIA_DELTA,
        HIBISCUS_DELTA_FEATURE,
        new DeltaFeatureConfiguration(Blocks.WATER.defaultBlockState(), Blocks.COARSE_DIRT.defaultBlockState(), UniformInt.of(5, 8), UniformInt.of(0, 4))
    );
    register(context,
        SWAMP_DELTA,
        HIBISCUS_DELTA_FEATURE,
        new DeltaFeatureConfiguration(Blocks.WATER.defaultBlockState(), Blocks.MUD.defaultBlockState(), UniformInt.of(2, 12), UniformInt.of(1, 3))
    );
    register(context,
        MARSH_DELTA,
        HIBISCUS_DELTA_FEATURE,
        new DeltaFeatureConfiguration(Blocks.WATER.defaultBlockState(), Blocks.MUD.defaultBlockState(), UniformInt.of(3, 14), UniformInt.of(1, 3))
    );
    register(context,
        RIVER_DELTA,
        HIBISCUS_DELTA_FEATURE,
        new DeltaFeatureConfiguration(Blocks.WATER.defaultBlockState(), Blocks.COARSE_DIRT.defaultBlockState(), UniformInt.of(2, 6), UniformInt.of(1, 3))
    );

    register(context, WILLOW_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.WILLOW.getLog()),
        new FancyTrunkPlacer(10, 3, 5),
        BlockStateProvider.simple(NSWoods.WILLOW.getLeaves()),
        new WisteriaFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0)),
        new TwoLayersFeatureSize(3, 0, 2, OptionalInt.of(5))
    ).decorators(List.of(new WisteriaVinesTreeDecorator(0.65F,
        new NSSimpleBlockStateProvider(NSWoods.WILLOW.getVinesPlant().defaultBlockState()),
        new RandomizedIntStateProvider(BlockStateProvider.simple(NSWoods.WILLOW.getVines().defaultBlockState()), DownwardVineBlock.AGE, UniformInt.of(23, 25)),
        new NSSimpleBlockStateProvider(NSWoods.WILLOW.getLeaves().defaultBlockState()),
        new NSSimpleBlockStateProvider(NSWoods.WILLOW.getLeaves().defaultBlockState()),
        5
    ))).ignoreVines().build());
    register(context,
        WILLOW_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.WILLOW_CHECKED), 0.5f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.WILLOW_CHECKED)
        )
    );

    register(context, WHITE_WISTERIA_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.WISTERIA.getLog()),
        new WisteriaTrunkPlacer(7, 3, 4, UniformInt.of(1, 6), 0.80F, UniformInt.of(7, 10),
            holderGetter.getOrThrow(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
        BlockStateProvider.simple(NSWoods.WISTERIA.getLeaves()),
        new WisteriaFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0)),
        new TwoLayersFeatureSize(2, 0, 2)
    ).decorators(List.of(new WisteriaVinesTreeDecorator(0.45F,
        new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getWhiteVinesPlant().defaultBlockState()),
        new RandomizedIntStateProvider(BlockStateProvider.simple(NSWoods.WISTERIA.getWhiteVines().defaultBlockState()), DownwardVineBlock.AGE, UniformInt.of(22, 25)),
        new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPartWhiteLeaves().defaultBlockState()),
        new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getWhiteLeaves().defaultBlockState()),
        2
    ))).ignoreVines().build());

    register(context, PINK_WISTERIA_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.WISTERIA.getLog()),
        new WisteriaTrunkPlacer(7,
            3,
            4,
            UniformInt.of(1, 6),
            0.80F,
            UniformInt.of(7, 10),
            BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
        ),
        BlockStateProvider.simple(NSWoods.WISTERIA.getLeaves()),
        new WisteriaFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0)),
        new TwoLayersFeatureSize(2, 0, 2)
    ).decorators(List.of(new WisteriaVinesTreeDecorator(0.45F,
        new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPinkVinesPlant().defaultBlockState()),
        new RandomizedIntStateProvider(BlockStateProvider.simple(NSWoods.WISTERIA.getPinkVines().defaultBlockState()), DownwardVineBlock.AGE, UniformInt.of(22, 25)),
        new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPartPinkLeaves().defaultBlockState()),
        new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPinkLeaves().defaultBlockState()),
        2
    ))).ignoreVines().build());

    register(context, BLUE_WISTERIA_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.WISTERIA.getLog()),
        new WisteriaTrunkPlacer(7,
            3,
            4,
            UniformInt.of(1, 6),
            0.80F,
            UniformInt.of(7, 10),
            BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
        ),
        BlockStateProvider.simple(NSWoods.WISTERIA.getLeaves()),
        new WisteriaFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0)),
        new TwoLayersFeatureSize(2, 0, 2)
    ).decorators(List.of(new WisteriaVinesTreeDecorator(0.45F,
        new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getBlueVinesPlant().defaultBlockState()),
        new RandomizedIntStateProvider(BlockStateProvider.simple(NSWoods.WISTERIA.getBlueVines().defaultBlockState()), DownwardVineBlock.AGE, UniformInt.of(22, 25)),
        new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPartBlueLeaves().defaultBlockState()),
        new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getBlueLeaves().defaultBlockState()),
        2
    ))).ignoreVines().build());

    register(context, PURPLE_WISTERIA_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.WISTERIA.getLog()),
        new WisteriaTrunkPlacer(7,
            3,
            4,
            UniformInt.of(1, 6),
            0.80F,
            UniformInt.of(7, 10),
            BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
        ),
        BlockStateProvider.simple(NSWoods.WISTERIA.getLeaves()),
        new WisteriaFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0)),
        new TwoLayersFeatureSize(2, 0, 2)
    ).decorators(List.of(new WisteriaVinesTreeDecorator(0.45F,
        new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPurpleVinesPlant().defaultBlockState()),
        new RandomizedIntStateProvider(BlockStateProvider.simple(NSWoods.WISTERIA.getPurpleVines().defaultBlockState()), DownwardVineBlock.AGE, UniformInt.of(22, 25)),
        new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPartPurpleLeaves().defaultBlockState()),
        new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPurpleLeaves().defaultBlockState()),
        2
    ))).ignoreVines().build());

    register(
        context,
        WISTERIA_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.WHITE_WISTERIA_CHECKED), 0.10f),
            new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.BLUE_WISTERIA_CHECKED), 0.325f),
            new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.PURPLE_WISTERIA_CHECKED), 0.325f),
            new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.PINK_WISTERIA_CHECKED), 0.25f)
        ), placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.WHITE_WISTERIA_CHECKED))
    );

    register(context, SUGI_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.SUGI.getLog()),
        new SugiTrunkPlacer(12, 1, 12, UniformInt.of(4, 6), .85F, UniformInt.of(4, 5),
            BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
        BlockStateProvider.simple(NSWoods.SUGI.getLeaves()),
        new SugiFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0)),
        new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(5))
    ).ignoreVines().build());

    register(context,
        SUGI_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.SUGI_CHECKED), 0.325f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.SUGI_CHECKED)
        )
    );

    register(context, LARGE_SUGI_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.SUGI.getLog()),
        new MegaSugiTrunkPlacer(18, 0, 5),
        BlockStateProvider.simple(NSWoods.SUGI.getLeaves()),
        new SugiFoliagePlacer(UniformInt.of(1, 1), UniformInt.of(0, 0)),
        new TwoLayersFeatureSize(1, 0, 1)
    ).ignoreVines().build());
    register(context,
        LARGE_SUGI_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARGE_SUGI_CHECKED), 0.325f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARGE_SUGI_CHECKED)
        )
    );

    register(context, OLIVE_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.OLIVE.getLog()),
        new OliveTrunkPlacer(3, 0, 2, UniformInt.of(3, 4), .95F, UniformInt.of(2, 3),
            BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
        BlockStateProvider.simple(NSWoods.OLIVE.getLeaves()),
        new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(3), 55),
        new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(5))
    ).ignoreVines().decorators(List.of(new OliveBranchTreeDecorator(.45F))).build());

    register(context,
        OLIVE_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.OLIVE_CHECKED), 0.325f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.OLIVE_CHECKED)
        )
    );

    register(context, PALO_VERDE_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.PALO_VERDE.getLog()),
        new PaloVerdeTrunkPlacer(6, 1, 3, UniformInt.of(4, 6), .95F, UniformInt.of(3, 6),
            BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
        BlockStateProvider.simple(NSWoods.PALO_VERDE.getLeaves()),
        new RandomSpreadFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(2), 70),
        new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(6))
    ).ignoreVines().build());

    register(context,
        PALO_VERDE_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.PALO_VERDE_CHECKED), 0.325f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.PALO_VERDE_CHECKED)
        )
    );

//      register(context, BANYAN_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.GHAF.getLog()),
//              new BanyanTrunkPlacer(7, 0, 2, UniformIntProvider.create(4, 6), .95F, UniformIntProvider.create(3, 6), Registries.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
//              BlockStateProvider.of(NSWoods.LARCH.getLeaves()),
//              new SugiFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)),
//              new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(4))
//      ).ignoreVines().build());

//      register(context,
//              BANYAN_TREE_SPAWN,
//              Feature.RANDOM_SELECTOR,
//              new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.BANYAN_CHECKED), 0.325f)),
//                      placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.BANYAN_CHECKED)
//              )
//      );

    register(context, SAXAUL_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.SAXAUL.getLog()),
        new SaxaulTrunkPlacer(3, 0, 2, UniformInt.of(4, 6), .65F, UniformInt.of(3, 6),
            BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
        BlockStateProvider.simple(NSWoods.SAXAUL.getLeaves()),
        new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), ConstantInt.of(2), 20),
        new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(1))
    ).ignoreVines().build());

    register(context,
        SAXAUL_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.SAXAUL_CHECKED), 0.325f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.SAXAUL_CHECKED)
        )
    );

    register(context, CEDAR_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.CEDAR.getLog()),
        new PaloVerdeTrunkPlacer(6, 1, 3, UniformInt.of(3, 5), .85F, UniformInt.of(2, 5),
            BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
        BlockStateProvider.simple(NSWoods.CEDAR.getLeaves()),
        new SugiFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0)),
        new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(6))
    ).ignoreVines().build());

    register(context,
        CEDAR_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.CEDAR_CHECKED), 0.325f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.CEDAR_CHECKED)
        )
    );

    register(context, COCONUT_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.COCONUT.getLog()),
        new CoconutTrunkPlacer(6, 2, 3, UniformInt.of(1, 3), .35F, BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
        BlockStateProvider.simple(NSWoods.COCONUT.getLeaves()),
        new CoconutFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
        new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(6))
    ).ignoreVines().decorators(List.of(new CoconutTreeDecorator(1.0F))).build());

    register(context,
        COCONUT_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.COCONUT_CHECKED), 0.325f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.COCONUT_CHECKED)
        )
    );

    register(context, GHAF_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(NSWoods.GHAF.getLog()),
        new GhafTrunkPlacer(4, 1, 2, UniformInt.of(4, 6), .95F, UniformInt.of(4, 6),
            BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
        BlockStateProvider.simple(NSWoods.GHAF.getLeaves()),
        new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 60),
        new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(3))
    ).ignoreVines().build());

    register(context,
        GHAF_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.GHAF_CHECKED), 0.325f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.GHAF_CHECKED)
        )
    );

    register(context, JOSHUA_TREE, JOSHUA_TREE_FEATURE, FeatureConfiguration.NONE);
    register(context, GRAY_POLYPORE, POLYPORE_FEATURE, FeatureConfiguration.NONE);
    register(context, ALLUAUDIA, ALLUAUDIA_FEATURE, FeatureConfiguration.NONE);

    register(
        context,
        JOSHUA_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.JOSHUA_CHECKED), 0.325f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.JOSHUA_CHECKED)
        )
    );

    register(
        context,
        ALLUAUDIA_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.ALLUAUDIA_CHECKED), 0.325f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.ALLUAUDIA_CHECKED)
        )
    );

    register(
        context,
        CYPRESS_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.CYPRESS_CHECKED), 0.325f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.CYPRESS_CHECKED)
        )
    );

    register(context,
        FANCY_OAK_TREE_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(TreePlacements.FANCY_OAK_BEES), 0.03F)),
            placedFeatureRegistryEntryLookup.getOrThrow(TreePlacements.FANCY_OAK_CHECKED)
        )
    );

    register(context, OAK_BUSH, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG),
        new BushTrunkPlacer(1, 0, 0),
        BlockStateProvider.simple(Blocks.OAK_LEAVES),
        new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
        new TwoLayersFeatureSize(0, 0, 0)
    ).build());
    register(context,
        OAK_BUSH_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.OAK_BUSH_CHECKED), 0.5f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.OAK_BUSH_CHECKED)
        )
    );

    register(context, SPRUCE_BUSH, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG),
        new StraightTrunkPlacer(1, 0, 0),
        BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
        new GroundedBushFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 75),
        new TwoLayersFeatureSize(1, 0, 1)
    ).build());
    register(
        context,
        SPRUCE_BUSH_SPAWN,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.SPRUCE_BUSH_CHECKED), 0.5f)),
            placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.SPRUCE_BUSH_CHECKED)
        )
    );

    register(context,
        FLOWER_WISTERIA_FOREST,
        Feature.FLOWER,
        new RandomPatchConfiguration(96,
            6,
            2,
            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new NoiseProvider(2445L,
                    new NormalNoise.NoiseParameters(0, 1.0D),
                    0.030833334F,
                    List.of(Blocks.ALLIUM.defaultBlockState(),
                        NSMiscBlocks.BLUEBELL.getFlowerBlock().defaultBlockState(),
                        NSMiscBlocks.ANEMONE.getFlowerBlock().defaultBlockState(),
                        Blocks.OXEYE_DAISY.defaultBlockState(),
                        Blocks.PINK_TULIP.defaultBlockState(),
                        NSMiscBlocks.SNAPDRAGON.getFlowerBlock().defaultBlockState(),
                        NSMiscBlocks.GARDENIA.getFlowerBlock().defaultBlockState(),
                        NSMiscBlocks.LAVENDER.getFlowerBlock().defaultBlockState(),
                        NSMiscBlocks.HIBISCUS.getFlowerBlock().defaultBlockState(),
                        Blocks.CORNFLOWER.defaultBlockState()
                    )
                ))
            )
        )
    );

    register(context,
        FLOWER_SUGI_FOREST,
        Feature.FLOWER,
        new RandomPatchConfiguration(45,
            8,
            2,
            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new NoiseProvider(2445L,
                    new NormalNoise.NoiseParameters(0, 1.0D),
                    0.030833334F,
                    List.of(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
                        Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
                        NSMiscBlocks.GARDENIA.getFlowerBlock().defaultBlockState(),
                        Blocks.AZURE_BLUET.defaultBlockState()
                    )
                ))
            )
        )
    );

    register(context,
        FLOWER_ERODED_RIVER,
        Feature.FLOWER,
        new RandomPatchConfiguration(60,
            6,
            2,
            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new NoiseProvider(2445L,
                    new NormalNoise.NoiseParameters(0, 1.0D),
                    0.030833334F,
                    List.of(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
                        NSMiscBlocks.BLEEDING_HEART.getFlowerBlock().defaultBlockState(),
                        NSMiscBlocks.ANEMONE.getFlowerBlock().defaultBlockState(),
                        NSMiscBlocks.HIBISCUS.getFlowerBlock().defaultBlockState()
                    )
                ))
            )
        )
    );

    register(context,
        FLOWER_GOLDEN_WILDS,
        Feature.FLOWER,
        new RandomPatchConfiguration(36,
            6,
            2,
            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new NoiseProvider(2445L,
                    new NormalNoise.NoiseParameters(0, 1.0D),
                    0.030833334F,
                    List.of(
                        NSMiscBlocks.TIGER_LILY.getFlowerBlock().defaultBlockState(),
                        Blocks.ORANGE_TULIP.defaultBlockState(),
                        NSMiscBlocks.FLAXEN_FERN.defaultBlockState(),
                        Blocks.DANDELION.defaultBlockState(),
                        NSMiscBlocks.MARIGOLD.getFlowerBlock().defaultBlockState(),
                        NSMiscBlocks.LARGE_FLAXEN_FERN.defaultBlockState(),
                        NSMiscBlocks.CARNATION.getFlowerBlock().defaultBlockState()
                    )
                ))
            )
        )
    );

    register(context,
        HUGE_SHIITAKE_MUSHROOM,
        HUGE_SHIITAKE_MUSHROOM_FEATURE,
        new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(NSMiscBlocks.SHIITAKE_MUSHROOM_BLOCK.defaultBlockState().setValue(HugeMushroomBlock.UP, true).setValue(HugeMushroomBlock.DOWN, false)),
            BlockStateProvider.simple(Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false)),
            2
        )
    );

    register(context,
        FLOWER_CYPRESS_FIELDS,
        Feature.FLOWER,
        new RandomPatchConfiguration(120,
            6,
            2,
            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new NoiseProvider(2445L,
                    new NormalNoise.NoiseParameters(0, 1.0D),
                    0.030833334F,
                    List.of(Blocks.SHORT_GRASS.defaultBlockState(),
                        Blocks.TALL_GRASS.defaultBlockState(),
                        Blocks.POPPY.defaultBlockState(),
                        NSMiscBlocks.CARNATION.getFlowerBlock().defaultBlockState(),
                        Blocks.POPPY.defaultBlockState(),
                        Blocks.SHORT_GRASS.defaultBlockState(),
                        Blocks.POPPY.defaultBlockState(),
                        NSMiscBlocks.CARNATION.getFlowerBlock().defaultBlockState(),
                        Blocks.POPPY.defaultBlockState(),
                        Blocks.TALL_GRASS.defaultBlockState(),
                        Blocks.SHORT_GRASS.defaultBlockState()
                    )
                ))
            )
        )
    );

    register(context,
        PATCH_SCORCHED_GRASS,
        Feature.RANDOM_PATCH,
        new RandomPatchConfiguration(32, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(NSMiscBlocks.SCORCHED_GRASS))))
    );
    register(context,
        PATCH_TALL_SCORCHED_GRASS,
        Feature.RANDOM_PATCH,
        new RandomPatchConfiguration(32, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(NSMiscBlocks.SCORCHED_GRASS))))
    );

    register(context,
        FLOWER_STRATIFIED_DESERT,
        Feature.FLOWER,
        new RandomPatchConfiguration(10,
            6,
            2,
            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new NoiseProvider(2445L,
                    new NormalNoise.NoiseParameters(0, 1.0D),
                    0.030833334F,
                    List.of(
                        NSMiscBlocks.SCORCHED_GRASS.defaultBlockState(),
                        NSMiscBlocks.YELLOW_WILDFLOWER.getFlowerBlock().defaultBlockState(),
                        NSMiscBlocks.TALL_SCORCHED_GRASS.defaultBlockState(),
                        NSMiscBlocks.PURPLE_WILDFLOWER.getFlowerBlock().defaultBlockState(),
                        NSMiscBlocks.SCORCHED_GRASS.defaultBlockState()
                    )
                ))
            )
        )
    );

    register(context,
        CATTAILS,
        Feature.RANDOM_PATCH,
        new RandomPatchConfiguration(90,
            6,
            2,
            PlacementUtils.filtered(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(NSMiscBlocks.CATTAIL)),
                BlockPredicate.allOf(BlockPredicate.wouldSurvive(NSMiscBlocks.CATTAIL.defaultBlockState(), BlockPos.ZERO), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, BlockPredicate.anyOf(
                    BlockPredicate.matchesFluids(new BlockPos(1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER),
                    BlockPredicate.matchesFluids(new BlockPos(-1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER),
                    BlockPredicate.matchesFluids(new BlockPos(0, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER),
                    BlockPredicate.matchesFluids(new BlockPos(0, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER),
                    BlockPredicate.matchesFluids(new BlockPos(0, 0, 0), Fluids.WATER, Fluids.FLOWING_WATER)
                ))
            )
        )
    );

    register(context, LOTUS_PLANT, Feature.RANDOM_PATCH, new RandomPatchConfiguration(20, 6, 2, PlacementUtils.onlyWhenEmpty(LOTUS_PLANT_FEATURE, FeatureConfiguration.NONE)));

    register(context,
        ROOTED_DESERT_TURNIP,
        HIBISCUS_TURNIP_ROOT_FEATURE,
        new TurnipRootFeatureConfig(PlacementUtils.inlinePlaced(Feature.NO_BONEMEAL_FLOWER, new RandomPatchConfiguration(30, 3, 2, PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK,
            new SimpleBlockConfiguration(BlockStateProvider.simple(NSMiscBlocks.DESERT_TURNIP_STEM.defaultBlockState().setValue(DesertTurnipStemBlock.AGE, 7))),
            PlacementUtils.HEIGHTMAP
        )), new PlacementModifier[0]),
            3,
            3,
            NSTags.Blocks.TURNIP_ROOT_REPLACEABLE,
            BlockStateProvider.simple(NSMiscBlocks.DESERT_TURNIP_ROOT_BLOCK),
            BlockStateProvider.simple(NSMiscBlocks.DESERT_TURNIP_BLOCK),
            20,
            20,
            3,
            2,
            BlockStateProvider.simple(Blocks.HANGING_ROOTS),
            20,
            35,
            2,
            BlockPredicate.allOf(
                BlockPredicate.anyOf(BlockPredicate.matchesBlocks(List.of(Blocks.AIR, Blocks.CAVE_AIR, Blocks.VOID_AIR)),
                    BlockPredicate.matchesTag(BlockTags.REPLACEABLE)),
                BlockPredicate.matchesTag(Direction.DOWN.getNormal(), NSTags.Blocks.TURNIP_STEM_GROWS_ON)
            )
        )
    );

    register(context,
        PUMPKIN_PATCH_FEATURE,
        HIBISCUS_PUMPKIN_PATCH_FEATURE,
        new OreConfiguration(new BlockMatchTest(Blocks.AIR), Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true), 43, 0)
    );
    register(context, PUMPKIN_FEATURE, HIBISCUS_LARGE_PUMPKIN_FEATURE, new BlockPileConfiguration(new NSSimpleBlockStateProvider(Blocks.PUMPKIN.defaultBlockState())));

  }

  private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature,
      FC configuration) {
    context.register(key, new ConfiguredFeature<>(feature, configuration));
  }

  public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
    return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, name));
  }
}


