package com.fuyun.cloudertinker.Effects;

import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

import static com.fuyun.cloudertinker.Effects.Bloodlust.bloodlust;

public class Bloodlust_beat extends NoMilkEffect {
    public Bloodlust_beat() {
        super(MobEffectCategory.BENEFICIAL, 0x6E9AA0,true);
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
        MinecraftForge.EVENT_BUS.addListener(this::MobEffectEvent);
    }
    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        ModDataNBT entitydata = ModDataNBT.readFromNBT(living.getPersistentData());
        living.removeEffect(CloudertinkerEffects.Bloodlust_erode.get());
        living.removeEffect(CloudertinkerEffects.Bloodlust.get());
        entitydata.putInt(bloodlust,0);
    }
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() != null) {
            LivingEntity entity = event.getEntity();
            Entity entity1=event.getSource().getEntity();
            if (entity1 instanceof LivingEntity livingEntity){
                MobEffectInstance instance =livingEntity.getEffect(CloudertinkerEffects.Bloodlust_beat.get());
                if (instance!=null){
                    event.setAmount(event.getAmount()*1.4f);
                }
            }else if(entity1 instanceof Projectile projectile&&projectile.getOwner()!=null){
                LivingEntity livingEntity= (LivingEntity) projectile.getOwner();
                MobEffectInstance instance =livingEntity.getEffect(CloudertinkerEffects.Bloodlust_beat.get());
                if (instance!=null){
                    event.setAmount(event.getAmount()*1.4f);
                }
            }
        }
    }

    public void MobEffectEvent(MobEffectEvent.Applicable event) {
        if (event.getEntity() != null && event.getEntity() instanceof LivingEntity) {
            if (event.getEntity().getEffect(CloudertinkerEffects.Bloodlust_beat.get())!=null){
                    if (event.getEffectInstance().getEffect() == (CloudertinkerEffects.Bloodlust.get()) || event.getEffectInstance().getEffect() == (CloudertinkerEffects.Bloodlust_erode.get()))
                        event.setResult(Event.Result.DENY);
            }
        }
    }

}
