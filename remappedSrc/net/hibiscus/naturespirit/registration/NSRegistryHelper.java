package net.hibiscus.naturespirit.registration;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.config.NSConfig;
import net.hibiscus.naturespirit.registration.sets.FlowerSet;
import net.hibiscus.naturespirit.registration.sets.StoneSet;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.item.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import java.util.HashMap;

public class NSRegistryHelper {

  public static HashMap<String, FlowerSet> FlowerHashMap = new HashMap<>();
  public static HashMap<String, WoodSet> WoodHashMap = new HashMap<>();
  public static HashMap<String, StoneSet> StoneHashMap = new HashMap<>();
  public static HashMap<String, Block[]> SaplingHashMap = new HashMap<>();
  public static HashMap<String, Block> LeavesHashMap = new HashMap<>();
  public static HashMap<String, Block> RenderLayerHashMap = new HashMap<>();
  public static HashMap<String, Item> NatureSpiritItemHashMap = new HashMap<>();


  public static Boolean never(BlockState state, BlockGetter world, BlockPos pos, EntityType<?> type) {
    return false;
  }

  public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType.Builder<T> factory) {
    return Registry.register(
        BuiltInRegistries.BLOCK_ENTITY_TYPE,
        ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, name),
        factory.build()
    );
  }


  public static Block registerBlockWithoutTab(String name, Block block) {
    return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, name), block);
  }

  public static Block registerBlock(String name, Block block) {
    registerItem(name, new BlockItem(block, new Item.Properties()));
    return registerBlockWithoutTab(name, block);
  }

  public static Block registerBlock(String name, Block block, ItemLike blockBefore) {
    Block block1 = registerBlockWithoutTab(name, block);
    registerItemWithoutTab(name, new BlockItem(block, new Item.Properties()));
    if (NatureSpirit.CONFIG.creative_tab) {
      ItemGroupEvents.modifyEntriesEvent(NSItemGroups.NS_ITEM_GROUP).register(entries -> entries.addAfter(blockBefore, block1.asItem()));
    }
    return block1;
  }

  public static Block registerBlock(String name, Block block, ItemLike blockBefore, ResourceKey<CreativeModeTab> secondaryTab) {
    Block block1 = registerBlock(name, block);
    ItemGroupEvents.modifyEntriesEvent(secondaryTab).register(entries -> entries.addAfter(blockBefore, block1.asItem()));
    return block1;
  }

  public static Block registerBlock(String name, Block block, ItemLike blockBefore, ResourceKey<CreativeModeTab> secondaryTab, ItemLike blockBefore2,
      ResourceKey<CreativeModeTab> thirdTab) {
    Block block1 = registerBlock(name, block, blockBefore, secondaryTab);
    ItemGroupEvents.modifyEntriesEvent(thirdTab).register(entries -> entries.addAfter(blockBefore2, block1.asItem()));
    return block1;
  }


  public static Block registerTransparentBlockWithoutTab(String name, Block block) {
    Block block1 = registerBlockWithoutTab(name, block);
    RenderLayerHashMap.put(name, block1);
    return block1;
  }

  public static Block registerTransparentBlock(String name, Block block) {
    Block block1 = registerBlock(name, block);
    RenderLayerHashMap.put(name, block1);
    return block1;
  }

  public static Block registerTransparentBlock(String name, Block block, ItemLike itemBefore, ResourceKey<CreativeModeTab> secondaryTab) {
    Block block1 = registerBlock(name, block, itemBefore, secondaryTab);
    RenderLayerHashMap.put(name, block1);
    return block1;
  }

  public static Block registerTransparentBlock(String name, Block block, ItemLike itemBefore, ResourceKey<CreativeModeTab> secondaryTab, ItemLike blockBefore2,
      ResourceKey<CreativeModeTab> thirdTab) {
    Block block1 = registerBlock(name, block, itemBefore, secondaryTab, blockBefore2, thirdTab);
    RenderLayerHashMap.put(name, block1);
    return block1;
  }


  public static Block registerPlantBlock(String name, Block block, ItemLike itemBefore, float compost) {
    Block plant = registerTransparentBlock(name, block, itemBefore, CreativeModeTabs.NATURAL_BLOCKS);
    CompostingChanceRegistry.INSTANCE.add(block, compost);
    return plant;
  }


  public static Item registerItemWithoutTab(String name, Item item) {
    Item item1 = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, name), item);
    NatureSpiritItemHashMap.put(name, item1);
    return item1;
  }

  public static Item registerItem(String name, Item item) {
    Item item1 = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, name), item);
    if (NatureSpirit.CONFIG.creative_tab) {
      ItemGroupEvents.modifyEntriesEvent(NSItemGroups.NS_ITEM_GROUP).register(entries -> entries.accept(item1));
    }
    NatureSpiritItemHashMap.put(name, item1);
    return item1;
  }

  public static Item registerItem(String name, Item item, ItemLike itemBefore, ResourceKey<CreativeModeTab> secondaryTab) {
    ItemGroupEvents.modifyEntriesEvent(secondaryTab).register(entries -> entries.addAfter(itemBefore, item));
    return registerItem(name, item);
  }


  public static Item registerPlantItem(String name, Item item, ItemLike itemBefore, ResourceKey<CreativeModeTab> secondaryTab, float compost) {
    CompostingChanceRegistry.INSTANCE.add(item, compost);
    return registerItem(name, item, itemBefore, secondaryTab);
  }

  public static Block registerTallPlantBlock(String name, Block block, ItemLike itemBefore, float compost) {
    Block plant = registerBlockWithoutTab(name, block);
    registerItem(name, new DoubleHighBlockItem(block, new Item.Properties()));
    ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(itemBefore, plant.asItem()));
    RenderLayerHashMap.put(name, plant);
    CompostingChanceRegistry.INSTANCE.add(block, compost);
    return plant;
  }
}
