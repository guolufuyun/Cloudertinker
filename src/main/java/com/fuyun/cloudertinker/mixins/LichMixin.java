package com.fuyun.cloudertinker.mixins;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.tools.TinkerModifiers;
import twilightforest.entity.boss.Lich;

@Mixin(Lich.class)
public abstract class LichMixin {

    @Inject(at = @At("HEAD"),method = "teleportToSightOfEntity", cancellable = true,remap = false)
    public void teleportToSightOfEntity(Entity entity, CallbackInfo ci){
        Lich lich = (Lich) (Object) this;
        MobEffectInstance instance =lich.getEffect(TinkerModifiers.enderferenceEffect.get());
        if (instance!=null){
            ci.cancel();
        }

    }
    @Inject(method = "teleportToNoChecks", at = @At("HEAD"), cancellable = true,remap = false)
    private void teleportToNoChecks(double destX, double destY, double destZ, CallbackInfo ci) {
        Lich lich = (Lich) (Object) this;
        MobEffectInstance instance =lich.getEffect(TinkerModifiers.enderferenceEffect.get());
        if (instance!=null){
            ci.cancel();
        }
    }
//    @Inject(method = "findVecInLOSOf", at = @At("HEAD"), cancellable = true, remap = false)
//    private void findVecInLOSOf(Entity targetEntity, CallbackInfoReturnable<Vec3> cir) {
//        Lich lich = (Lich) (Object) this;
//        MobEffectInstance instance =lich.getEffect(TinkerEffects.enderference.get());
//        if (instance!=null){
//            cir.setReturnValue(null);
//        }
//    }
}
