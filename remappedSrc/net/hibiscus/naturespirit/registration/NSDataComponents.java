package net.hibiscus.naturespirit.registration;

import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.blocks.block_entities.PizzaToppingVariant;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import java.util.List;
import java.util.function.UnaryOperator;

public class NSDataComponents {

  public static final DataComponentType<List<PizzaToppingVariant>> TOPPINGS = register("toppings", (builder) -> {
    return builder.persistent(PizzaToppingVariant.CODEC.listOf()).cacheEncoding();
  });

  private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
    return (DataComponentType) Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, id),
        ((DataComponentType.Builder) builderOperator.apply(DataComponentType.builder())).build());
  }

  public static void registerDataComponents() {
  }

}
