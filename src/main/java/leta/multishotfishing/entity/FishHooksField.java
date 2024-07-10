package leta.multishotfishing.entity;

import net.minecraft.entity.projectile.FishingBobberEntity;

import java.util.ArrayList;

public interface FishHooksField {
    ArrayList<FishingBobberEntity> fishHooks = new ArrayList<>();

    boolean multiShotFishingMod$getHooked();
    void multiShotFishingMod$setHooked(boolean hooked);
}
