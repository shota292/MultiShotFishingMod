package leta.multishotfishing.mixin;

import leta.multishotfishing.entity.FishHooksField;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {

    @Redirect(method = "use(Lnet/minecraft/item/ItemStack;)I", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/projectile/FishingBobberEntity;hookCountdown:I"))
    private int getHookCountdown(FishingBobberEntity fishingBobberEntity) {
        return ((FishHooksField) Objects.requireNonNull(fishingBobberEntity.getOwner())).multiShotFishingMod$getHooked() ? 1 : 0;
    }

    @Inject(method = "setPlayerFishHook(Lnet/minecraft/entity/projectile/FishingBobberEntity;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;fishHook:Lnet/minecraft/entity/projectile/FishingBobberEntity;", shift = At.Shift.BEFORE), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void setPlayerFishHook(FishingBobberEntity fishHook, CallbackInfo ci, PlayerEntity playerEntity) {
        if (!((Entity)(Object)this).getWorld().isClient) {
            if (fishHook != null) {
                ((FishHooksField) playerEntity).fishHooks.add(fishHook);
            }else {
                ((FishHooksField) playerEntity).fishHooks.remove((FishingBobberEntity) (Object) this);
            }
        }
        ci.cancel();
    }
}
