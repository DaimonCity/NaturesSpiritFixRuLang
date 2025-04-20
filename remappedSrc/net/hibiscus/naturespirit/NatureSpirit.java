package net.hibiscus.naturespirit;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.hibiscus.naturespirit.blocks.block_entities.PizzaToppingVariant;
import net.hibiscus.naturespirit.config.NSConfig;
import net.hibiscus.naturespirit.mixin.StatsTypeAccessor;
import net.hibiscus.naturespirit.registration.*;
import net.hibiscus.naturespirit.registration.compat.NSArtsAndCraftsCompat;
import net.hibiscus.naturespirit.util.NSCauldronBehavior;
import net.hibiscus.naturespirit.util.NSEvents;
import net.hibiscus.naturespirit.util.NSVillagers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.world.entity.animal.CatVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class NatureSpirit implements ModInitializer {

  public static final String MOD_ID = "natures_spirit";
  public static final Logger LOGGER = LoggerFactory.getLogger("Nature's Spirit");
  public static final ResourceLocation EAT_PIZZA_SLICE = StatsTypeAccessor.registerNew("eat_pizza_slice", StatFormatter.DEFAULT);
  public static final ResourceLocation EAT_CHEESE = StatsTypeAccessor.registerNew("eat_cheese", StatFormatter.DEFAULT);
  public static final ResourceKey<Registry<PizzaToppingVariant>> PIZZA_TOPPING_VARIANT = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MOD_ID, "pizza_topping_variant"));
  public static final NSConfig CONFIG = new NSConfig(FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + ".toml"));

  @Override
  public void onInitialize() {
    Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(MOD_ID);

    if (modContainer.isPresent()) {
      ResourceManagerHelper.registerBuiltinResourcePack(
          ResourceLocation.fromNamespaceAndPath(MOD_ID, "modified_vanilla_trees"), modContainer.get(),
          Component.translatable("pack.natures_spirit.modified_vanilla_trees"),
          CONFIG.vanilla_trees_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
      );
      ResourceManagerHelper.registerBuiltinResourcePack(
          ResourceLocation.fromNamespaceAndPath(MOD_ID, "modified_flower_forest"), modContainer.get(),
          Component.translatable("pack.natures_spirit.modified_flower_forest"),
          CONFIG.flower_forest_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
      );
      ResourceManagerHelper.registerBuiltinResourcePack(
          ResourceLocation.fromNamespaceAndPath(MOD_ID, "modified_birch_forest"), modContainer.get(),
          Component.translatable("pack.natures_spirit.modified_birch_forest"),
          CONFIG.birch_forest_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
      );
      ResourceManagerHelper.registerBuiltinResourcePack(
          ResourceLocation.fromNamespaceAndPath(MOD_ID, "modified_jungle"), modContainer.get(),
          Component.translatable("pack.natures_spirit.modified_jungle"),
          CONFIG.jungle_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
      );
      ResourceManagerHelper.registerBuiltinResourcePack(
          ResourceLocation.fromNamespaceAndPath(MOD_ID, "modified_swamp"), modContainer.get(),
          Component.translatable("pack.natures_spirit.modified_swamp"),
          CONFIG.swamp_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
      );
      ResourceManagerHelper.registerBuiltinResourcePack(
          ResourceLocation.fromNamespaceAndPath(MOD_ID, "modified_desert"), modContainer.get(),
          Component.translatable("pack.natures_spirit.modified_desert"),
          CONFIG.desert_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
      );
      ResourceManagerHelper.registerBuiltinResourcePack(
          ResourceLocation.fromNamespaceAndPath(MOD_ID, "modified_badlands"), modContainer.get(),
          Component.translatable("pack.natures_spirit.modified_badlands"),
          CONFIG.badlands_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
      );
      ResourceManagerHelper.registerBuiltinResourcePack(
          ResourceLocation.fromNamespaceAndPath(MOD_ID, "modified_mountain_biomes"), modContainer.get(),
          Component.translatable("pack.natures_spirit.modified_mountain_biomes"),
          CONFIG.mountain_biomes_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
      );
      ResourceManagerHelper.registerBuiltinResourcePack(
          ResourceLocation.fromNamespaceAndPath(MOD_ID, "modified_savannas"), modContainer.get(),
          Component.translatable("pack.natures_spirit.modified_savannas"),
          CONFIG.savanna_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
      );
      ResourceManagerHelper.registerBuiltinResourcePack(
          ResourceLocation.fromNamespaceAndPath(MOD_ID, "modified_dark_forest"), modContainer.get(),
          Component.translatable("pack.natures_spirit.modified_dark_forest"),
          CONFIG.dark_forest_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
      );
      ResourceManagerHelper.registerBuiltinResourcePack(
          ResourceLocation.fromNamespaceAndPath(MOD_ID, "modified_windswept_hills"), modContainer.get(),
          Component.translatable("pack.natures_spirit.modified_windswept_hills"),
          CONFIG.windswept_hills_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
      );


      if (FabricLoader.getInstance().getModContainer("arts_and_crafts").isPresent()) {
        ResourceManagerHelper.registerBuiltinResourcePack(
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "arts_and_crafts_res"), modContainer.get(),
                ResourcePackActivationType.ALWAYS_ENABLED
        );
        ResourceManagerHelper.registerBuiltinResourcePack(
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "arts_and_crafts_dat"), modContainer.get(),
                ResourcePackActivationType.ALWAYS_ENABLED
        );
      }

      ResourceManagerHelper.registerBuiltinResourcePack(
          ResourceLocation.fromNamespaceAndPath(MOD_ID, "plank_consistency"), modContainer.get(),
          Component.translatable("pack.natures_spirit.plank_consistency"),
          ResourcePackActivationType.NORMAL
      );

      ResourceManagerHelper.registerBuiltinResourcePack(
          ResourceLocation.fromNamespaceAndPath(MOD_ID, "emissive_ores_compatibility"), modContainer.get(),
          Component.translatable("pack.natures_spirit.emissive_ores_compatibility"),
          ResourcePackActivationType.NORMAL
      );
    }

    NSSounds.registerSounds();
    NSDataComponents.registerDataComponents();
    NSEntityTypes.registerEntityTypes();
    NSVillagers.registerVillagers();
    NSParticleTypes.registerParticleTypes();
    NSBiomes.registerBiomes();
    NSWoods.registerWoods();
    NSColoredBlocks.registerColoredBlocks();
    NSMiscBlocks.registerMiscBlocks();
    if (FabricLoader.getInstance().getModContainer("arts_and_crafts").isPresent()) {
      NSArtsAndCraftsCompat.registerBlocks();
    }
    NSEvents.registerEvents();
    NSWorldGen.registerWorldGen();
    NSItemGroups.registerItemGroup();
    NSCriteria.registerCriteria();
    NSCauldronBehavior.registerBehavior();
    DynamicRegistries.registerSynced(PIZZA_TOPPING_VARIANT, PizzaToppingVariant.CODEC);

    Registry.register(BuiltInRegistries.CAT_VARIANT, "trans", new CatVariant(ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/cat/trans" + ".png")));

  }
}
