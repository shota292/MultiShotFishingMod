package leta.multishotfishing.mixin;

import leta.multishotfishing.entity.FishHooksField;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
    @Shadow @Final private static Logger LOGGER;


    @Inject(method = "setPlayerFishHook(Lnet/minecraft/entity/projectile/FishingBobberEntity;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;fishHook:Lnet/minecraft/entity/projectile/FishingBobberEntity;", shift = At.Shift.BEFORE), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void setPlayerFishHook(FishingBobberEntity fishHook, CallbackInfo ci, PlayerEntity playerEntity) {
        if (!((Entity)(Object)this).getWorld().isClient) {
            if (fishHook != null) {
                ((FishHooksField) playerEntity).fishHooks.add(fishHook);
                // fishHooksの中身をログに出す
                LOGGER.info("setPlayerFishHook: "+((FishHooksField) playerEntity).fishHooks);
            }else {
                ((FishHooksField) playerEntity).fishHooks.remove((FishingBobberEntity) (Object) this);
                // fishHooksの中身をログに出す
                LOGGER.info("setPlayerFishHook: "+((FishHooksField) playerEntity).fishHooks);
            }
        }
        ci.cancel();
//        LOGGER.info("setPlayerFishHook: "+(fishHook!=null?fishHook.getUuidAsString():"null"));
    }
}
