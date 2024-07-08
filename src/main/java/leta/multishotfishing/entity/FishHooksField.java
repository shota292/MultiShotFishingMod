package leta.multishotfishing.entity;

import net.minecraft.entity.projectile.FishingBobberEntity;

import java.util.ArrayList;

public interface FishHooksField {
    // fishHooksという名のFishingBobberEntityを複数持つフィールドを定義
    ArrayList<FishingBobberEntity> fishHooks = new ArrayList<>();
}
