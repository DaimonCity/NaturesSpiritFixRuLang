package net.hibiscus.naturespirit.mixin.entity.boat;

import net.hibiscus.naturespirit.registration.NSBoatTypes;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Boat.class)
public class BoatDropsMixin {

  //CREDIT TO nyuppo/fabric-boat-example ON GITHUB

  @Inject(method = "asItem", at = @At("RETURN"), cancellable = true)
  public void getNaturesSpiritBoats(CallbackInfoReturnable<Item> info) {
    NSBoatTypes.getBoatItem(Boat.class.cast(this).getVariant()).ifPresent(info::setReturnValue);
  }

}
