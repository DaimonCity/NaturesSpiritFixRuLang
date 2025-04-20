package net.hibiscus.naturespirit.client.render;

import net.hibiscus.naturespirit.NatureSpirit;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class NSEntityModelLayers {

  public static void registerEntityModelLayers() {
  }

  public static final ModelLayerLocation PIZZA_TOPPING = register("pizza", "toppings");

  private static ModelLayerLocation registerMain(String id) {
    return register(id, "main");
  }

  private static ModelLayerLocation register(String id, String layer) {
    return create(id, layer);
  }

  private static ModelLayerLocation create(String id, String layer) {
    return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, id), layer);
  }
}
