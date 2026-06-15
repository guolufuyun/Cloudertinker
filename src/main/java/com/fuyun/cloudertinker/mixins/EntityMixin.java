package com.fuyun.cloudertinker.mixins;


import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import util.method.ModifierLevel;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "teleportTo(DDD)V", at = @At("HEAD"))
    private void onTeleportTo(double x, double y, double z, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;

        if (entity instanceof LivingEntity livingEntity &&
                ModifierLevel.EquipHasModifierlevel(livingEntity, CloudertinkerModifiers.lichprotect.getId())) {
            applySilhouetteDanceEffect(livingEntity);
        }
    }

    private void applySilhouetteDanceEffect(LivingEntity entity) {
        // TODO: 添加buff效果（后续实现）

    }
}

