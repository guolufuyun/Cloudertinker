package com.fuyun.cloudertinker.mixins;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import util.mixins.LivingPositionRecord;


    @Mixin(LivingEntity.class)
    public abstract class LivingEntityMixin extends Entity implements LivingPositionRecord{


        public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
            super(p_19870_, p_19871_);

        }
        @Unique
        private  Vec3 lastPos=Vec3.ZERO;
        @Unique
        public Vec3 getLastPosition() {
            return this.lastPos;
        }
        @Unique
        public boolean moveinLastTick(double offset) {
            var nowpos=this.position();
            var lastpos=lastPos;
            return Math.abs(nowpos.x-lastpos.x)>offset||Math.abs(nowpos.y-lastpos.y)>offset||Math.abs(nowpos.z-lastpos.z)>offset;
        }
        @Inject(at =@At("TAIL"),method = "tick")
        private void setLastPos(CallbackInfo ci){
            this.lastPos=this.position();
        }

    }

