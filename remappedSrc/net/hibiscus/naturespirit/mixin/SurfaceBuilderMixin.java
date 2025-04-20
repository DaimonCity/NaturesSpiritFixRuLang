package net.hibiscus.naturespirit.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.config.NSConfig;
import net.hibiscus.naturespirit.registration.NSBiomes;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BlockColumn;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceSystem;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SurfaceSystem.class)
public class SurfaceBuilderMixin {

  @Final
  @Shadow
  private BlockState defaultState;
  @Unique
  private NormalNoise sugiPillarNoise;
  @Unique
  private NormalNoise sugiPillarRoofNoise;
  @Unique
  private NormalNoise sugiSurfaceNoise;
  @Unique
  private NormalNoise stratifiedDesertPillarNoise;
  @Unique
  private NormalNoise stratifiedDesertPillarRoofNoise;
  @Unique
  private NormalNoise stratifiedDesertSurfaceNoise;

  @Inject(method = "<init>", at = @At(value = "TAIL"))
  private void injectNoise(RandomState noiseConfig, BlockState defaultState, int seaLevel, PositionalRandomFactory randomDeriver, CallbackInfo ci) {
    sugiPillarNoise = noiseConfig.getOrCreateNoise(NSWorldGen.SUGI_PILLAR);
    sugiPillarRoofNoise = noiseConfig.getOrCreateNoise(NSWorldGen.SUGI_PILLAR_ROOF);
    sugiSurfaceNoise = noiseConfig.getOrCreateNoise(NSWorldGen.SUGI_SURFACE);
    stratifiedDesertPillarNoise = noiseConfig.getOrCreateNoise(NSWorldGen.STRATIFIED_DESERT_PILLAR);
    stratifiedDesertPillarRoofNoise = noiseConfig.getOrCreateNoise(NSWorldGen.STRATIFIED_DESERT_PILLAR_ROOF);
    stratifiedDesertSurfaceNoise = noiseConfig.getOrCreateNoise(NSWorldGen.STRATIFIED_DESERT_SURFACE);
  }

  @Inject(method = "buildSurface", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/entry/RegistryEntry;matchesKey(Lnet/minecraft/registry/RegistryKey;)Z", ordinal = 0))
  private void injectPillars(RandomState noiseConfig, BiomeManager biomeAccess, Registry<Biome> biomeRegistry, boolean useLegacyRandom, WorldGenerationContext heightContext,
      ChunkAccess chunk, NoiseChunk chunkNoiseSampler, SurfaceRules.RuleSource materialRule,
      CallbackInfo ci,
      @Local Holder<Biome> registryEntry, @Local(ordinal = 2) int k, @Local(ordinal = 3) int l, @Local(ordinal = 4) int m, @Local(ordinal = 5) int n,
      @Local BlockColumn blockColumn) {
    if (NatureSpirit.CONFIG.sugi_and_stratified_pillars) {
      int o = chunk.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, k, l) + 1;
      if (registryEntry.is(NSBiomes.SUGI_FOREST) || registryEntry.is(NSBiomes.BLOOMING_SUGI_FOREST)) {
        this.placeSugiPillar(blockColumn, m, n, o, chunk);
      }
      if (registryEntry.is(NSBiomes.STRATIFIED_DESERT) || registryEntry.is(NSBiomes.LIVELY_DUNES) || registryEntry.is(NSBiomes.BLOOMING_DUNES)) {
        this.placeStratifiedDesertPillar(blockColumn, m, n, o, chunk);
      }
    }
  }

  @Unique
  private void placeSugiPillar(BlockColumn column, int x, int z, int surfaceY, LevelHeightAccessor chunk) {
    double e = Math.min(Math.abs(sugiSurfaceNoise.getValue(x, 0.0, z) * 8.5), sugiPillarRoofNoise.getValue((double) x * 0.2, 0.0, (double) z * 0.2) * 12.0);
    if (e > -10.0) {
      double h = Math.abs(sugiPillarNoise.getValue((double) x * 0.9, 0.0, (double) z * 0.8) * 2.05);
      double i = 32.0 + Math.min(e * e * 6.75, Math.ceil(h * 30.0) + 48.0);
      int j = Mth.floor(i);
      if (surfaceY <= j) {
        int k;
        for (k = j; k >= chunk.getMinBuildHeight(); --k) {
          BlockState blockState = column.getBlock(k);
          if (blockState.is(this.defaultState.getBlock())) {
            break;
          }
        }

        for (k = j; k >= chunk.getMinBuildHeight() && (column.getBlock(k).isAir() || column.getBlock(k).is(Blocks.WATER)); --k) {
          column.setBlock(k, this.defaultState);
        }


      }
    }
  }

  @Unique
  private void placeStratifiedDesertPillar(BlockColumn column, int x, int z, int surfaceY, LevelHeightAccessor chunk) {
    double e = Math.min(Math.abs(stratifiedDesertSurfaceNoise.getValue(x, 0.0, z) * 8.5), stratifiedDesertPillarNoise.getValue((double) x * 0.2, 0.0, (double) z * 0.2) * 14.0);
    if (!(e <= 0.0)) {
      double h = Math.abs(stratifiedDesertPillarRoofNoise.getValue((double) x * 0.75, 0.0, (double) z * 0.75) * 2.25);
      double i = 54.0 + Math.min(e * e * 3.5, Math.ceil(h * 30.0) + 38.0);
      int j = Mth.floor(i);
      if (surfaceY <= j) {
        int k;
        for (k = j; k >= chunk.getMinBuildHeight(); --k) {
          BlockState blockState = column.getBlock(k);
          if (blockState.is(this.defaultState.getBlock())) {
            break;
          }
        }

        for (k = j; k >= chunk.getMinBuildHeight() && (column.getBlock(k).isAir() || column.getBlock(k).is(Blocks.WATER)); --k) {
          column.setBlock(k, this.defaultState);
        }

      }
    }
  }

}
