package net.hibiscus.naturespirit.registration.compat;

import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.ItemGroups;

import static net.hibiscus.naturespirit.registration.NSColoredBlocks.*;
import static net.hibiscus.naturespirit.registration.NSRegistryHelper.registerBlock;

public class NSArtsAndCraftsCompat {

    public static final Block BLEACHED_CHALK = registerBlock(
            "bleached_chalk",
            new Block(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.5F)),
            PINK_KAOLIN_BRICK_SLAB,
            ItemGroups.COLORED_BLOCKS
    );
    public static final Block BLEACHED_CHALK_STAIRS = registerBlock(
            "bleached_chalk_stairs",
            new StairsBlock(BLEACHED_CHALK.getDefaultState(),
                    AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.5F)),
            PINK_CHALK,
            ItemGroups.COLORED_BLOCKS
    );
    public static final Block BLEACHED_CHALK_SLAB = registerBlock(
            "bleached_chalk_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.5F)),
            PINK_CHALK_STAIRS,
            ItemGroups.COLORED_BLOCKS
    );
    public static void registerBlocks() {}
}
