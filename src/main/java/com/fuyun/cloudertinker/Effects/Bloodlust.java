package com.fuyun.cloudertinker.Effects;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

public class Bloodlust extends NoMilkEffect {

    public static final ResourceLocation bloodlust = Cloudertinker.getResource("bloodlust");
    public Bloodlust() {
        super(MobEffectCategory.HARMFUL, 0x620591,true);
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
        MinecraftForge.EVENT_BUS.addListener(this::LivingHealEvent);
    }
    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
       living.addEffect(new MobEffectInstance(CloudertinkerEffects.Bloodlust_erode.get(),2400,0));
    }
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() != null) {
            LivingEntity entity = event.getEntity();
            Entity entity1=event.getSource().getEntity();
            if (entity1 instanceof LivingEntity livingEntity){
                ModDataNBT entitydata = ModDataNBT.readFromNBT(livingEntity.getPersistentData());
                MobEffectInstance instance =livingEntity.getEffect(CloudertinkerEffects.Bloodlust.get());
                if (instance!=null){
                    if (entitydata.getInt(bloodlust)>=480)livingEntity.addEffect(new MobEffectInstance(CloudertinkerEffects.Bloodlust_beat.get(),1200,0));
                    if (livingEntity instanceof Player)entitydata.putInt(bloodlust,entitydata.getInt(bloodlust)+3);
                    else entitydata.putInt(bloodlust,entitydata.getInt(bloodlust)+120);
                }
            }else if(entity1 instanceof Projectile projectile&&projectile.getOwner()!=null){
                LivingEntity livingEntity= (LivingEntity) projectile.getOwner();
                ModDataNBT entitydata = ModDataNBT.readFromNBT(livingEntity.getPersistentData());
                MobEffectInstance instance =livingEntity.getEffect(CloudertinkerEffects.Bloodlust.get());
                if (instance!=null){
                    if (entitydata.getInt(bloodlust)>=480)livingEntity.addEffect(new MobEffectInstance(CloudertinkerEffects.Bloodlust_beat.get(),1200,0));
                    if (livingEntity instanceof Player)entitydata.putInt(bloodlust,entitydata.getInt(bloodlust)+3);
                    else entitydata.putInt(bloodlust,entitydata.getInt(bloodlust)+120);
                }
            }
        }
    }
    public void LivingHealEvent(LivingHealEvent event) {
        if (event.getEntity() != null) {
            LivingEntity entity = event.getEntity();
            if (entity.getEffect(CloudertinkerEffects.Bloodlust.get())!=null){
                event.setAmount(event.getAmount()*0.5f);
            }
        }
    }
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration == 1;
    }
}
