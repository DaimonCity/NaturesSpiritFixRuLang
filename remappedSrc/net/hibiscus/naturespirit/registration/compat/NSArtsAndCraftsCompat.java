package net.hibiscus.naturespirit.registration.compat;

import net.minecraft.block.*;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import static net.hibiscus.naturespirit.registration.NSColoredBlocks.*;
import static net.hibiscus.naturespirit.registration.NSRegistryHelper.registerBlock;

public class NSArtsAndCraftsCompat {

    public static final Block BLEACHED_CHALK = registerBlock(
            "bleached_chalk",
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.5F)),
            PINK_KAOLIN_BRICK_SLAB,
            CreativeModeTabs.COLORED_BLOCKS
    );
    public static final Block BLEACHED_CHALK_STAIRS = registerBlock(
            "bleached_chalk_stairs",
            new StairBlock(BLEACHED_CHALK.defaultBlockState(),
                    BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.5F)),
            PINK_CHALK,
            CreativeModeTabs.COLORED_BLOCKS
    );
    public static final Block BLEACHED_CHALK_SLAB = registerBlock(
            "bleached_chalk_slab",
            new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.5F)),
            PINK_CHALK_STAIRS,
            CreativeModeTabs.COLORED_BLOCKS
    );
    public static void registerBlocks() {}
}
