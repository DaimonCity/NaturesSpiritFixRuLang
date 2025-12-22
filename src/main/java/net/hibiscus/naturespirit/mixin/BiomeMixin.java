package net.hibiscus.naturespirit.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hibiscus.naturespirit.registration.NSTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Biome.class)
public class BiomeMixin {
    @WrapOperation(method = "canSetIce(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Z)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;doesNotSnow(Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean isValid(Biome instance, BlockPos pos, Operation<Boolean> original, WorldView world) {
        if (world.getBiome(pos).isIn(NSTags.Biomes.FORCE_ICE)){
            return false;
        }
        return original.call(instance, pos);
    }
}
