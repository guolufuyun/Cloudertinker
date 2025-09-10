package com.fuyun.cloudertinker.Effects;

import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

import static com.fuyun.cloudertinker.Effects.Bloodlust.bloodlust;

public class Bloodlust_erode extends NoMilkEffect {
    public Bloodlust_erode() {
        super(MobEffectCategory.HARMFUL, 0xA7636D,true);
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
        MinecraftForge.EVENT_BUS.addListener(this::MobEffectEvent);
        MinecraftForge.EVENT_BUS.addListener(this::LivingHealEvent);
    }
    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        ModDataNBT entitydata = ModDataNBT.readFromNBT(living.getPersistentData());
        living.removeEffect(CloudertinkerEffects.Bloodlust.get());
        living.removeEffect(CloudertinkerEffects.Bloodlust_beat.get());
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
        if (entity.getEffect(CloudertinkerEffects.Bloodlust_erode.get())!=null){
            event.setAmount(event.getAmount()*1.5f);
        }
        }
    }
    public void LivingHealEvent(LivingHealEvent event) {
        if (event.getEntity() != null) {
            LivingEntity entity = event.getEntity();
            if (entity.getEffect(CloudertinkerEffects.Bloodlust_erode.get())!=null){
                event.setAmount(0);

            }
        }
    }
    public void MobEffectEvent(MobEffectEvent.Applicable event) {
        if (event.getEntity() != null && event.getEntity() instanceof LivingEntity) {
            if (event.getEntity().getEffect(CloudertinkerEffects.Bloodlust_erode.get())!=null){
                if (event.getEffectInstance().getEffect() == (CloudertinkerEffects.Bloodlust.get()) || event.getEffectInstance().getEffect() == (CloudertinkerEffects.Bloodlust_beat.get()))
                    event.setResult(Event.Result.DENY);
            }
        }
    }
}
