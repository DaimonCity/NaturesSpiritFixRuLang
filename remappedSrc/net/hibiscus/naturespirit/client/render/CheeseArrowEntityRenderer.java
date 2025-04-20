package net.hibiscus.naturespirit.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.entity.CheeseArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class CheeseArrowEntityRenderer extends ArrowRenderer<CheeseArrowEntity> {

  public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(NatureSpirit.MOD_ID, "textures/entity/projectiles/cheese_arrow.png");

  public CheeseArrowEntityRenderer(EntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public ResourceLocation getTexture(CheeseArrowEntity spectralArrowEntity) {
    return TEXTURE;
  }
}
