package net.hibiscus.naturespirit.registration;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.hibiscus.naturespirit.NatureSpirit;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class NSParticleTypes {

  public static final SimpleParticleType RED_MAPLE_LEAVES_PARTICLE = FabricParticleTypes.simple(false);
  public static final SimpleParticleType ORANGE_MAPLE_LEAVES_PARTICLE = FabricParticleTypes.simple(false);
  public static final SimpleParticleType YELLOW_MAPLE_LEAVES_PARTICLE = FabricParticleTypes.simple(false);
  public static final SimpleParticleType MILK_PARTICLE = FabricParticleTypes.simple(false);
  public static final SimpleParticleType CALCITE_BUBBLE_PARTICLE = FabricParticleTypes.simple(false);

  public static void registerParticleTypes() {
    Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, "red_maple_leaves"), RED_MAPLE_LEAVES_PARTICLE);
    Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, "orange_maple_leaves"), ORANGE_MAPLE_LEAVES_PARTICLE);
    Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, "yellow_maple_leaves"), YELLOW_MAPLE_LEAVES_PARTICLE);
    Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, "milk"), MILK_PARTICLE);
    Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, "calcite_bubble"), CALCITE_BUBBLE_PARTICLE);
  }
}
