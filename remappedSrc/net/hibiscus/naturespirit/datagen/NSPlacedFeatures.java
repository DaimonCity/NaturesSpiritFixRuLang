package net.hibiscus.naturespirit.datagen;


import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.registration.NSTags;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import java.util.List;

public class NSPlacedFeatures {


  public static final ResourceKey<PlacedFeature> LARGE_REDWOOD_CHECKED = registerKey("large_redwood_checked");
  public static final ResourceKey<PlacedFeature> REDWOOD_CHECKED = registerKey("redwood_checked");
  public static final ResourceKey<PlacedFeature> LARGE_FROSTY_REDWOOD_CHECKED = registerKey("large_frosty_redwood_checked");
  public static final ResourceKey<PlacedFeature> FROSTY_REDWOOD_CHECKED = registerKey("frosty_redwood_checked");
  public static final ResourceKey<PlacedFeature> ASPEN_CHECKED = registerKey("aspen_checked");
  public static final ResourceKey<PlacedFeature> YELLOW_ASPEN_CHECKED = registerKey("yellow_aspen_checked");
  public static final ResourceKey<PlacedFeature> ASPEN_BEES_CHECKED = registerKey("aspen_bees_checked");
  public static final ResourceKey<PlacedFeature> YELLOW_ASPEN_BEES_CHECKED = registerKey("yellow_aspen_bees_checked");
  public static final ResourceKey<PlacedFeature> RED_MAPLE_CHECKED = registerKey("red_maple_checked");
  public static final ResourceKey<PlacedFeature> ORANGE_MAPLE_CHECKED = registerKey("orange_maple_checked");
  public static final ResourceKey<PlacedFeature> YELLOW_MAPLE_CHECKED = registerKey("yellow_maple_checked");
  public static final ResourceKey<PlacedFeature> CYPRESS_CHECKED = registerKey("cypress_checked");
  public static final ResourceKey<PlacedFeature> FIR_CHECKED = registerKey("fir_checked");
  public static final ResourceKey<PlacedFeature> LARCH_CHECKED = registerKey("larch_checked");
  public static final ResourceKey<PlacedFeature> WILLOW_CHECKED = registerKey("willow_checked");
  public static final ResourceKey<PlacedFeature> WILLOW_PLACED = registerKey("willow_placed");
  public static final ResourceKey<PlacedFeature> WHITE_WISTERIA_CHECKED = registerKey("white_wisteria_checked");
  public static final ResourceKey<PlacedFeature> BLUE_WISTERIA_CHECKED = registerKey("blue_wisteria_checked");
  public static final ResourceKey<PlacedFeature> PINK_WISTERIA_CHECKED = registerKey("pink_wisteria_checked");
  public static final ResourceKey<PlacedFeature> PURPLE_WISTERIA_CHECKED = registerKey("purple_wisteria_checked");
  public static final ResourceKey<PlacedFeature> SUGI_CHECKED = registerKey("sugi_checked");
  public static final ResourceKey<PlacedFeature> LARGE_SUGI_CHECKED = registerKey("large_sugi_checked");
  public static final ResourceKey<PlacedFeature> OLIVE_CHECKED = registerKey("olive_checked");
  public static final ResourceKey<PlacedFeature> GHAF_CHECKED = registerKey("ghaf_checked");
  public static final ResourceKey<PlacedFeature> PALO_VERDE_CHECKED = registerKey("palo_verde_checked");
  public static final ResourceKey<PlacedFeature> MAHOGANY_CHECKED = registerKey("mahogany_checked");
  public static final ResourceKey<PlacedFeature> SAXAUL_CHECKED = registerKey("saxaul_checked");
  //   public static final RegistryKey <PlacedFeature> BANYAN_CHECKED = registerKey("banyan_checked");
  public static final ResourceKey<PlacedFeature> COCONUT_CHECKED = registerKey("coconut_checked");
  public static final ResourceKey<PlacedFeature> CEDAR_CHECKED = registerKey("cedar_checked");
  public static final ResourceKey<PlacedFeature> JOSHUA_CHECKED = registerKey("joshua_checked");
  public static final ResourceKey<PlacedFeature> ALLUAUDIA_CHECKED = registerKey("alluaudia_checked");

  public static final ResourceKey<PlacedFeature> OAK_BUSH_CHECKED = registerKey("oak_bush_checked");
  public static final ResourceKey<PlacedFeature> SPRUCE_BUSH_CHECKED = registerKey("spruce_bush_checked");

  public static final ResourceKey<PlacedFeature> FLOWER_WISTERIA_PLACED = registerKey("flower_wisteria_placed");
  public static final ResourceKey<PlacedFeature> FLOWER_SUGI_PLACED = registerKey("flower_sugi_placed");
  public static final ResourceKey<PlacedFeature> FLOWER_RIVER_PLACED = registerKey("flower_river_placed");
  public static final ResourceKey<PlacedFeature> FLOWER_GOLDEN_PLACED = registerKey("flower_golden_placed");
  public static final ResourceKey<PlacedFeature> FLOWER_GOLDEN2_PLACED = registerKey("flower_golden2_placed");
  public static final ResourceKey<PlacedFeature> FLOWER_CYPRESS_PLACED = registerKey("flower_cypress_placed");
  public static final ResourceKey<PlacedFeature> FLOWER_CARNATION_PLACED = registerKey("flower_carnation_placed");
  public static final ResourceKey<PlacedFeature> PATCH_SCORCHED_GRASS_PLACED = registerKey("patch_scorched_grass_placed");
  public static final ResourceKey<PlacedFeature> PATCH_TALL_SCORCHED_GRASS_PLACED = registerKey("patch_tall_scorched_grass_placed");
  public static final ResourceKey<PlacedFeature> FLOWER_STRATIFIED_DESERT_PLACED = registerKey("flower_stratified_desert_placed");

  public static final ResourceKey<PlacedFeature> WISTERIA_WATER = registerKey("wisteria_water_placed");
  public static final ResourceKey<PlacedFeature> LAVENDER_WATER = registerKey("lavender_water_placed");
  public static final ResourceKey<PlacedFeature> SWAMP_WATER = registerKey("swamp_water_placed");
  public static final ResourceKey<PlacedFeature> MARSH_WATER = registerKey("marsh_water_placed");
  public static final ResourceKey<PlacedFeature> RIVER_WATER = registerKey("river_water_placed");

  public static final ResourceKey<PlacedFeature> LARGE_REDWOOD_PLACED = registerKey("large_redwood_placed");
  public static final ResourceKey<PlacedFeature> REDWOOD_PLACED = registerKey("redwood_placed");
  public static final ResourceKey<PlacedFeature> LARGE_FROSTY_REDWOOD_PLACED = registerKey("large_frosty_redwood_placed");
  public static final ResourceKey<PlacedFeature> FROSTY_REDWOOD_PLACED = registerKey("frosty_redwood_placed");
  public static final ResourceKey<PlacedFeature> ASPEN_PLACED = registerKey("aspen_placed");
  public static final ResourceKey<PlacedFeature> YELLOW_ASPEN_PLACED = registerKey("yellow_aspen_placed");
  public static final ResourceKey<PlacedFeature> MAPLE_PLACED = registerKey("maple_placed");
  public static final ResourceKey<PlacedFeature> MAPLE2_PLACED = registerKey("maple2_placed");
  public static final ResourceKey<PlacedFeature> FEW_ASPEN_PLACED = registerKey("few_aspen_placed");
  public static final ResourceKey<PlacedFeature> DENSE_CYPRESS_PLACED = registerKey("dense_cypress_placed");
  public static final ResourceKey<PlacedFeature> CYPRESS_PLACED = registerKey("cypress_placed");
  public static final ResourceKey<PlacedFeature> FIR_PLACED = registerKey("fir_placed");
  public static final ResourceKey<PlacedFeature> DENSE_FIR_PLACED = registerKey("dense_fir_placed");
  public static final ResourceKey<PlacedFeature> SPRUCE_BUSH_PLACED = registerKey("spruce_bush_placed");
  public static final ResourceKey<PlacedFeature> WISTERIA_PLACED = registerKey("wisteria_placed");
  public static final ResourceKey<PlacedFeature> SUGI_PLACED = registerKey("sugi_placed");
  public static final ResourceKey<PlacedFeature> OLIVE_PLACED = registerKey("olive_placed");
  public static final ResourceKey<PlacedFeature> GHAF_PLACED = registerKey("ghaf_placed");
  public static final ResourceKey<PlacedFeature> PALO_VERDE_PLACED = registerKey("palo_verde_placed");
  public static final ResourceKey<PlacedFeature> SAXAUL_PLACED = registerKey("saxaul_placed");
  public static final ResourceKey<PlacedFeature> SPARSE_SAXAUL_PLACED = registerKey("sparse_saxaul_placed");
  public static final ResourceKey<PlacedFeature> JOSHUA_PLACED = registerKey("joshua_placed");
  public static final ResourceKey<PlacedFeature> ALLUAUDIA_PLACED = registerKey("alluaudia_placed");
  public static final ResourceKey<PlacedFeature> COCONUT_PLACED = registerKey("coconut_placed");
  public static final ResourceKey<PlacedFeature> CEDAR_PLACED = registerKey("cedar_placed");
  public static final ResourceKey<PlacedFeature> DENSE_CEDAR_PLACED = registerKey("dense_cedar_placed");
  public static final ResourceKey<PlacedFeature> MAHOGANY_PLACED = registerKey("mahogany_placed");
  public static final ResourceKey<PlacedFeature> SPARSE_OLIVE_PLACED = registerKey("sparse_olive_placed");

  public static final ResourceKey<PlacedFeature> OAK_BUSH_PLACED = registerKey("oak_bush_placed");
  public static final ResourceKey<PlacedFeature> CUSTOM_FANCY_OAK_TREE_PLACED = registerKey("custom_fancy_oak_tree_placed");
  public static final ResourceKey<PlacedFeature> CUSTOM_FANCY_OAK_TREE2_PLACED = registerKey("custom_fancy_oak_tree2_placed");
  public static final ResourceKey<PlacedFeature> CATTAILS = registerKey("cattails_placed");
  public static final ResourceKey<PlacedFeature> LOTUS_PLANT_PLACED = registerKey("lotus_plant_placed");
  public static final ResourceKey<PlacedFeature> ROOTED_DESERT_TURNIP = registerKey("rooted_desert_turnip_placed");

  private static final PlacementModifier TREE_THRESHOLD = SurfaceWaterDepthFilter.forMaxDepth(0);

  public static void bootstrap(BootstrapContext<PlacedFeature> context) {
    var configuredFeatureRegistryEntryLookup = context.lookup(Registries.CONFIGURED_FEATURE);

    registerKey(context,
        LARGE_REDWOOD_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LARGE_REDWOOD_TREE),
        List.of(PlacementUtils.filteredByBlockSurvival(NSWoods.REDWOOD.getSapling()))
    );
    registerKey(context,
        LARGE_FROSTY_REDWOOD_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LARGE_FROSTY_REDWOOD_TREE),
        List.of(PlacementUtils.filteredByBlockSurvival(NSWoods.REDWOOD.getSapling()))
    );
    registerKey(context, REDWOOD_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.REDWOOD_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.REDWOOD.getSapling()));
    registerKey(context, FROSTY_REDWOOD_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FROSTY_REDWOOD_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.REDWOOD.getSapling()));
    registerKey(context, ASPEN_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ASPEN_TREE), PlacementUtils.filteredByBlockSurvival(NSWoods.ASPEN.getSapling()));
    registerKey(context,
        ASPEN_BEES_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ASPEN_TREE_BEES),
        PlacementUtils.filteredByBlockSurvival(NSWoods.ASPEN.getSapling())
    );
    registerKey(context, YELLOW_ASPEN_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.YELLOW_ASPEN_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.ASPEN.getSapling()));
    registerKey(context,
        YELLOW_ASPEN_BEES_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.YELLOW_ASPEN_TREE_BEES),
        PlacementUtils.filteredByBlockSurvival(NSWoods.ASPEN.getSapling())
    );
    registerKey(context,
        RED_MAPLE_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.RED_MAPLE_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.MAPLE.getRedSapling())
    );
    registerKey(context,
        ORANGE_MAPLE_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ORANGE_MAPLE_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.MAPLE.getOrangeSapling())
    );
    registerKey(context,
        YELLOW_MAPLE_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.YELLOW_MAPLE_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.MAPLE.getYellowSapling())
    );
    registerKey(context, CYPRESS_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CYPRESS_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.CYPRESS.getSapling()));
    registerKey(context, JOSHUA_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.JOSHUA_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.JOSHUA.getSapling()));
    registerKey(context, ALLUAUDIA_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ALLUAUDIA),
        PlacementUtils.filteredByBlockSurvival(NSWoods.JOSHUA.getSapling()));
    registerKey(context, GHAF_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.GHAF_TREE), PlacementUtils.filteredByBlockSurvival(NSWoods.GHAF.getSapling()));
    registerKey(context, PALO_VERDE_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.PALO_VERDE_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.PALO_VERDE.getSapling()));
    registerKey(context, CEDAR_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CEDAR_TREE), PlacementUtils.filteredByBlockSurvival(NSWoods.CEDAR.getSapling()));
    registerKey(context, COCONUT_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.COCONUT_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.JOSHUA.getSapling()));
    registerKey(context, FIR_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FIR_TREE), PlacementUtils.filteredByBlockSurvival(NSWoods.FIR.getSapling()));
    registerKey(context, LARCH_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LARCH_TREE), PlacementUtils.filteredByBlockSurvival(NSWoods.LARCH.getSapling()));
    registerKey(context, WILLOW_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.WILLOW_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.WILLOW.getSapling()));
    registerKey(context, MAHOGANY_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.MAHOGANY_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.MAHOGANY.getSapling()));
    registerKey(context, LARGE_SUGI_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LARGE_SUGI_SPAWN),
        PlacementUtils.filteredByBlockSurvival(NSWoods.SUGI.getSapling()));
    registerKey(context, SAXAUL_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SAXAUL_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.SAXAUL.getSapling()));
    registerKey(context,
        WHITE_WISTERIA_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.WHITE_WISTERIA_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.WISTERIA.getWhiteSapling())
    );
    registerKey(context,
        BLUE_WISTERIA_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.BLUE_WISTERIA_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.WISTERIA.getBlueSapling())
    );
    registerKey(context,
        PINK_WISTERIA_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.PINK_WISTERIA_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.WISTERIA.getPinkSapling())
    );
    registerKey(context,
        PURPLE_WISTERIA_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.PURPLE_WISTERIA_TREE),
        PlacementUtils.filteredByBlockSurvival(NSWoods.WISTERIA.getPurpleSapling())
    );
    registerKey(context, SUGI_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SUGI_TREE), PlacementUtils.filteredByBlockSurvival(NSWoods.SUGI.getSapling()));
//      registerKey(context, BANYAN_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.BANYAN_TREE), PlacedFeatures.wouldSurvive(NSWoods.BANYAN.getSapling()));
    registerKey(context, OLIVE_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.OLIVE_TREE), PlacementUtils.filteredByBlockSurvival(NSWoods.OLIVE.getSapling()));

    registerKey(context, OAK_BUSH_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.OAK_BUSH), BlockPredicateFilter.forPredicate(BlockPredicate.anyOf(
            BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO),
            BlockPredicate.matchesTag(BlockPos.ZERO.below(), NSTags.Blocks.KAOLIN),
            BlockPredicate.matchesTag(BlockPos.ZERO.below(), BlockTags.BASE_STONE_OVERWORLD),
            BlockPredicate.matchesTag(BlockPos.ZERO.east(), BlockTags.BASE_STONE_OVERWORLD),
            BlockPredicate.matchesTag(BlockPos.ZERO.west(), BlockTags.BASE_STONE_OVERWORLD),
            BlockPredicate.matchesTag(BlockPos.ZERO.south(), BlockTags.BASE_STONE_OVERWORLD),
            BlockPredicate.matchesTag(BlockPos.ZERO.north(), BlockTags.BASE_STONE_OVERWORLD)
        )
    ));
    registerKey(context, SPRUCE_BUSH_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SPRUCE_BUSH),
        PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));

    registerKey(context,
        FLOWER_WISTERIA_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_WISTERIA_FOREST),
        CountPlacement.of(3),
        RarityFilter.onAverageOnceEvery(2),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome()
    );
    registerKey(context,
        FLOWER_SUGI_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_SUGI_FOREST),
        CountPlacement.of(4),
        RarityFilter.onAverageOnceEvery(8),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome()
    );
    registerKey(context,
        FLOWER_RIVER_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_ERODED_RIVER),
        CountPlacement.of(5),
        RarityFilter.onAverageOnceEvery(2),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome()
    );
    registerKey(context,
        FLOWER_GOLDEN_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_GOLDEN_WILDS),
        CountPlacement.of(3),
        RarityFilter.onAverageOnceEvery(5),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome()
    );
    registerKey(context,
        FLOWER_GOLDEN2_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_GOLDEN_WILDS),
        CountPlacement.of(5),
        RarityFilter.onAverageOnceEvery(1),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome()
    );
    registerKey(context,
        FLOWER_CYPRESS_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_CYPRESS_FIELDS),
        CountPlacement.of(3),
        RarityFilter.onAverageOnceEvery(15),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome()
    );
    registerKey(context,
        FLOWER_CARNATION_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_CYPRESS_FIELDS),
        CountPlacement.of(5),
        RarityFilter.onAverageOnceEvery(2),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome()
    );
    registerKey(context,
        PATCH_SCORCHED_GRASS_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.PATCH_SCORCHED_GRASS),
        CountPlacement.of(4),
        RarityFilter.onAverageOnceEvery(2),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome()
    );
    registerKey(context,
        PATCH_TALL_SCORCHED_GRASS_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.PATCH_TALL_SCORCHED_GRASS),
        CountPlacement.of(3),
        RarityFilter.onAverageOnceEvery(2),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome()
    );

    registerKey(context,
        FLOWER_STRATIFIED_DESERT_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_STRATIFIED_DESERT),
        CountPlacement.of(3),
        RarityFilter.onAverageOnceEvery(15),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome()
    );

    registerKey(context,
        WISTERIA_WATER,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.WISTERIA_DELTA),
        CountOnEveryLayerPlacement.of(20),
        BiomeFilter.biome()
    );
    registerKey(context,
        LAVENDER_WATER,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.WISTERIA_DELTA),
        CountOnEveryLayerPlacement.of(12),
        BiomeFilter.biome()
    );
    registerKey(context, SWAMP_WATER, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SWAMP_DELTA), CountOnEveryLayerPlacement.of(10),
        BiomeFilter.biome());
    registerKey(context, MARSH_WATER, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.MARSH_DELTA), CountOnEveryLayerPlacement.of(20),
        BiomeFilter.biome());
    registerKey(context, RIVER_WATER, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.RIVER_DELTA), CountOnEveryLayerPlacement.of(20),
        BiomeFilter.biome());

    registerKey(context,
        LARGE_REDWOOD_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LARGE_REDWOOD_TREE_SPAWN),
        CountPlacement.of(3),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        REDWOOD_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.REDWOOD_TREE_SPAWN),
        CountPlacement.of(7),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        LARGE_FROSTY_REDWOOD_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LARGE_FROSTY_REDWOOD_TREE_SPAWN),
        CountPlacement.of(3),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        FROSTY_REDWOOD_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FROSTY_REDWOOD_TREE_SPAWN),
        CountPlacement.of(7),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        ASPEN_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ASPEN_TREE_SPAWN),
        NoiseThresholdCountPlacement.of(.5D, 0, 5),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        YELLOW_ASPEN_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.YELLOW_ASPEN_TREE_SPAWN),
        NoiseThresholdCountPlacement.of(.5D, 5, 0),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        DENSE_CYPRESS_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CYPRESS_TREE_SPAWN),
        CountPlacement.of(20),
        RarityFilter.onAverageOnceEvery(80),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        CEDAR_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CEDAR_TREE_SPAWN),
        NoiseBasedCountPlacement.of(1, 60, -.25F),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        DENSE_CEDAR_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CEDAR_TREE_SPAWN),
        CountPlacement.of(30),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        SPARSE_OLIVE_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.OLIVE_TREE_SPAWN),
        CountPlacement.of(3),
        RarityFilter.onAverageOnceEvery(50),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        CYPRESS_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CYPRESS_TREE_SPAWN),
        CountPlacement.of(20),
        RarityFilter.onAverageOnceEvery(110),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        FEW_ASPEN_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ASPEN_TREE_SPAWN),
        CountPlacement.of(20),
        RarityFilter.onAverageOnceEvery(80),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        MAPLE2_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.MAPLE_SPAWN),
        CountPlacement.of(7),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        MAPLE_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.MAPLE_SPAWN),
        NoiseBasedCountPlacement.of(3, 100, 0),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        FIR_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FIR_TREE_SPAWN),
        NoiseBasedCountPlacement.of(3, 200, 0),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        DENSE_FIR_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FIR_TREE_SPAWN),
        CountPlacement.of(6),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        WILLOW_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.WILLOW_TREE_SPAWN),
        CountPlacement.of(2),
        InSquarePlacement.spread(),
        SurfaceWaterDepthFilter.forMaxDepth(2),
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        SPRUCE_BUSH_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SPRUCE_BUSH_SPAWN),
        CountPlacement.of(4),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        WISTERIA_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.WISTERIA_SPAWN),
        CountPlacement.of(15),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        SUGI_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SUGI_SPAWN),
        CountPlacement.of(5),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        MAHOGANY_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.MAHOGANY_TREE_SPAWN),
        CountPlacement.of(2),
        InSquarePlacement.spread(),
        SurfaceWaterDepthFilter.forMaxDepth(7),
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        OLIVE_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.OLIVE_TREE_SPAWN),
        CountPlacement.of(20),
        RarityFilter.onAverageOnceEvery(80),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        GHAF_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.GHAF_TREE_SPAWN),
        CountPlacement.of(10),
        RarityFilter.onAverageOnceEvery(100),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        PALO_VERDE_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.PALO_VERDE_TREE_SPAWN),
        CountPlacement.of(11),
        RarityFilter.onAverageOnceEvery(85),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        SAXAUL_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SAXAUL_TREE_SPAWN),
        NoiseBasedCountPlacement.of(6, 130, 0),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        SPARSE_SAXAUL_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SAXAUL_TREE_SPAWN),
        CountPlacement.of(2),
        RarityFilter.onAverageOnceEvery(20),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        JOSHUA_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.JOSHUA_TREE_SPAWN),
        CountPlacement.of(20),
        RarityFilter.onAverageOnceEvery(80),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        ALLUAUDIA_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ALLUAUDIA_SPAWN),
        CountPlacement.of(10),
        RarityFilter.onAverageOnceEvery(80),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        COCONUT_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.COCONUT_TREE_SPAWN),
        CountPlacement.of(3),
        RarityFilter.onAverageOnceEvery(3),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );

    registerKey(context,
        OAK_BUSH_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.OAK_BUSH_SPAWN),
        CountPlacement.of(1),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );

    registerKey(context,
        CUSTOM_FANCY_OAK_TREE_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FANCY_OAK_TREE_SPAWN),
        CountPlacement.of(1),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        CUSTOM_FANCY_OAK_TREE2_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FANCY_OAK_TREE_SPAWN),
        RarityFilter.onAverageOnceEvery(35),
        CountPlacement.of(1),
        InSquarePlacement.spread(),
        TREE_THRESHOLD,
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
    registerKey(context,
        CATTAILS,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CATTAILS),
        RarityFilter.onAverageOnceEvery(1),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome()
    );
    registerKey(context,
        LOTUS_PLANT_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LOTUS_PLANT),
        RarityFilter.onAverageOnceEvery(15),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome()
    );

    registerKey(context,
        ROOTED_DESERT_TURNIP,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ROOTED_DESERT_TURNIP),
        CountPlacement.of(UniformInt.of(1, 2)),
        InSquarePlacement.spread(),
        RarityFilter.onAverageOnceEvery(30),
        HeightRangePlacement.uniform(VerticalAnchor.absolute(30), VerticalAnchor.absolute(226)),
        EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), 12),
        RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
        BiomeFilter.biome()
    );
  }

  public static ResourceKey<PlacedFeature> registerKey(String name) {
    return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, name));
  }

  private static void registerKey(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
      List<PlacementModifier> modifiers) {
    context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
  }

  private static <FC extends FeatureConfiguration, F extends Feature<FC>> void registerKey(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
      Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
    registerKey(context, key, configuration, List.of(modifiers));
  }

}
