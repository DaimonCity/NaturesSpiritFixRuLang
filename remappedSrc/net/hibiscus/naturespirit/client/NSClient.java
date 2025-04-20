package net.hibiscus.naturespirit.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.hibiscus.naturespirit.client.render.CheeseArrowEntityRenderer;
import net.hibiscus.naturespirit.client.render.NSEntityModelLayers;
import net.hibiscus.naturespirit.client.render.PizzaBlockEntityRenderer;
import net.hibiscus.naturespirit.client.render.PizzaToppingModel;
import net.hibiscus.naturespirit.registration.*;
import net.minecraft.client.particle.SuspendedTownParticle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

@Environment(EnvType.CLIENT)
public class NSClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    BlockEntityRenderers.register(NSMiscBlocks.PIZZA_BLOCK_ENTITY_TYPE, PizzaBlockEntityRenderer::new);

    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter,
            blockState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? blockPos.below() : blockPos
        ) : -1, NSMiscBlocks.CATTAIL);
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageFoliageColor(blockAndTintGetter,
            blockPos
        ) : -1, NSWoods.SUGI.getLeaves());
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageFoliageColor(blockAndTintGetter,
            blockPos
        ) : -1, NSWoods.MAHOGANY.getLeaves());
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageFoliageColor(blockAndTintGetter,
            blockPos
        ) : -1, NSWoods.LARCH.getLeaves());
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageFoliageColor(blockAndTintGetter,
            blockPos
        ) : -1, NSWoods.ASPEN.getLeaves());
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter,
            blockPos
        ) : -1, NSMiscBlocks.LOTUS_STEM);
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter,
            blockPos
        ) : -1, NSMiscBlocks.LARGE_LUSH_FERN);
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter,
            blockPos
        ) : -1, NSMiscBlocks.LUSH_FERN);
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter,
            blockPos
        ) : -1, NSMiscBlocks.POTTED_LUSH_FERN);
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter,
            blockPos
        ) : -1, NSMiscBlocks.LOTUS_STEM);
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter,
            new BlockPos(blockPos.getX(), -64, blockPos.getZ())
        ) : -1, NSMiscBlocks.LOTUS_FLOWER);

    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColor.getDefaultColor(), NSWoods.SUGI.getLeaves());
    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColor.getDefaultColor(), NSWoods.LARCH.getLeaves());
    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColor.getDefaultColor(), NSWoods.MAHOGANY.getLeaves());
    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColor.getDefaultColor(), NSWoods.ASPEN.getLeaves());
    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColor.getDefaultColor(), NSMiscBlocks.LUSH_FERN);
    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColor.getDefaultColor(), NSMiscBlocks.LARGE_LUSH_FERN);

    NSEntityModelLayers.registerEntityModelLayers();
    EntityModelLayerRegistry.registerModelLayer(NSEntityModelLayers.PIZZA_TOPPING, PizzaToppingModel::getTexturedModelData);

    BlockRenderLayerMap.INSTANCE.putBlock(NSMiscBlocks.PIZZA_BLOCK, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSMiscBlocks.LARGE_CALCITE_BUD, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSMiscBlocks.SMALL_CALCITE_BUD, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSMiscBlocks.CALCITE_CLUSTER, RenderType.cutout());

    BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), NSRegistryHelper.RenderLayerHashMap.values().toArray(new Block[0]));
    BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutoutMipped(), NSRegistryHelper.LeavesHashMap.values().toArray(new Block[0]));

    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.COCONUT_THATCH_CARPET, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.COCONUT_THATCH_SLAB, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.COCONUT_THATCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.COCONUT_THATCH_STAIRS, RenderType.cutout());

    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.EVERGREEN_THATCH_CARPET, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.EVERGREEN_THATCH_SLAB, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.EVERGREEN_THATCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.EVERGREEN_THATCH_STAIRS, RenderType.cutout());

    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.XERIC_THATCH_CARPET, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.XERIC_THATCH_SLAB, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.XERIC_THATCH, RenderType.cutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.XERIC_THATCH_STAIRS, RenderType.cutout());

    EntityRendererRegistry.register(NSEntityTypes.CHEESE_ARROW, CheeseArrowEntityRenderer::new);

    ParticleFactoryRegistry.getInstance().register(NSParticleTypes.RED_MAPLE_LEAVES_PARTICLE,
        ((spriteProvider) -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> new MapleLeavesParticle(world, x, y, z, spriteProvider))
    );
    ParticleFactoryRegistry.getInstance().register(NSParticleTypes.ORANGE_MAPLE_LEAVES_PARTICLE,
        ((spriteProvider) -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> new MapleLeavesParticle(world, x, y, z, spriteProvider))
    );
    ParticleFactoryRegistry.getInstance().register(NSParticleTypes.YELLOW_MAPLE_LEAVES_PARTICLE,
        ((spriteProvider) -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> new MapleLeavesParticle(world, x, y, z, spriteProvider))
    );
    ParticleFactoryRegistry.getInstance().register(NSParticleTypes.MILK_PARTICLE, SuspendedTownParticle.ComposterFillProvider::new);
//    ParticleFactoryRegistry.getInstance().register(CALCITE_BUBBLE_PARTICLE, CalciteBubbleParticle.BubbleFactory::new);
  }
}

