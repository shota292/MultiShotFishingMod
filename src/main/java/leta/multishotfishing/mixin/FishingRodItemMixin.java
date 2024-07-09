package leta.multishotfishing.mixin;

import leta.multishotfishing.entity.FishHooksField;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;


@Mixin(FishingRodItem.class)
public class FishingRodItemMixin {
    @Redirect(method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;fishHook:Lnet/minecraft/entity/projectile/FishingBobberEntity;", opcode = Opcodes.GETFIELD, ordinal = 0))
    private FishingBobberEntity getFishHook(PlayerEntity playerEntity) {
        return !((FishHooksField) playerEntity).fishHooks.isEmpty() ? ((FishHooksField)playerEntity).fishHooks.getFirst():null;
    }

    @Redirect(method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/FishingBobberEntity;use(Lnet/minecraft/item/ItemStack;)I", ordinal = 0))
    private int useFishHook(FishingBobberEntity fishingBobberEntity, ItemStack itemStack) {
//        return (fishingBobberEntity != null) ? fishingBobberEntity.use(itemStack) : 0;
        return 0;
    }
    @Inject(method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/FishingBobberEntity;use(Lnet/minecraft/item/ItemStack;)I", ordinal = 0, shift = At.Shift.AFTER))
    private void useFishHooks(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
//        i = ((FishHooksField)user).fishHooks.stream().mapToInt(fishHook -> fishHook.use(user.getStackInHand(hand))).sum();
//        LOGGER.info("useFishHooks: "+((FishHooksField)user).fishHooks);
        ArrayList<FishingBobberEntity> FishiHooks = ((FishHooksField) user).fishHooks;
        int size = FishiHooks.size();
        // logに出力
//        LOGGER.info("useFishHooks: "+size);
        for (int j = 0; j < size; j++) {
            FishiHooks.getFirst().use(user.getStackInHand(hand));
//            LOGGER.info("useFishHooks: "+size);
        }
    }
    @Inject(method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z", shift = At.Shift.AFTER),locals = LocalCapture.CAPTURE_FAILHARD)
    private void spawnFishHooks(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, ItemStack itemStack, ServerWorld serverWorld, int j, int k) {
        world.spawnEntity(new FishingBobberEntity(user, world, k, j));
    }
}
