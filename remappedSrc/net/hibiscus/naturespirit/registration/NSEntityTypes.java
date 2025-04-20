package net.hibiscus.naturespirit.registration;

import net.hibiscus.naturespirit.entity.CheeseArrowEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static net.hibiscus.naturespirit.NatureSpirit.MOD_ID;

public class NSEntityTypes {

  private static final EntityType.Builder<CheeseArrowEntity> CHEESE_ARROW_ENTITY_BUILDER = EntityType.Builder.of(CheeseArrowEntity::new, MobCategory.MISC);
  public static final EntityType<CheeseArrowEntity> CHEESE_ARROW = registerEntityType(
      "cheese_arrow",
      CHEESE_ARROW_ENTITY_BUILDER.sized(0.5F, 0.5F)
          .clientTrackingRange(4)
          .updateInterval(20)
          .build()
  );

  public static void registerEntityTypes() {
  }

   /* public static EntityType<BisonEntity> BISON = registerMobEntityType(
	 "bison",
	 FabricEntityTypeBuilder.createMob()
	 .spawnGroup(SpawnGroup.CREATURE)
	 .defaultAttributes(BisonEntity::createBisonAttributes)
	 .entityFactory(BisonEntity::new)
	 .dimensions(EntityDimensions.fixed(2.0F, 1.75F))
	 .trackRangeChunks(10));
	*/

  public static <T extends EntityType<?>> T registerEntityType(String id, T type) {
    return Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, id), type);
  }

	/*
	public static <T extends Entity> EntityType<T> registerMobEntityType(String id, FabricEntityTypeBuilder<T> type) {
		EntityType<T> entityType = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, id), type.build());
		registerItem(id + "spawn_egg", new SpawnEggItem((EntityType<? extends MobEntity>) entityType, 15771042, 14377823, new FabricItemSettings().maxCount(64)), HibiscusItemGroups.NS_MISC_ITEM_GROUP, Items.COW_SPAWN_EGG,
			ItemGroups.SPAWN_EGGS);
		return entityType;
	}
	 */

}
