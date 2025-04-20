package net.hibiscus.naturespirit.registration;

import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.advancements.CoconutHitCriterion;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class NSCriteria {

  public static final CoconutHitCriterion COCONUT_HIT_CRITERION = register("coconut_hit", new CoconutHitCriterion());

  public static void registerCriteria() {
  }

  public static <T extends CriterionTrigger<?>> T register(String id, T criterion) {
    return Registry.register(BuiltInRegistries.TRIGGER_TYPES, ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, id), criterion);
  }
}
