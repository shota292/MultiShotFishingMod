package leta.multishotfishing.mixin;

import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
    @Inject(method = "Lnet/minecraft/entity/projectile/FishingBobberEntity;setPlayerFishHook(Lnet/minecraft/entity/projectile/FishingBobberEntity;)V", at = @At("HEAD"), cancellable = true)
    private void injected(CallbackInfo ci) {
        ci.cancel();
    }
}
