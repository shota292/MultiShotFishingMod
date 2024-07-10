package leta.multishotfishing.mixin;

import leta.multishotfishing.entity.FishHooksField;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements FishHooksField {
    @Unique
    private boolean hooked = false;

    @Override
    public boolean multiShotFishingMod$getHooked() {
        return hooked;
    }
    @Override
    public void multiShotFishingMod$setHooked(boolean hooked) {
        this.hooked = hooked;
    }
}
