package com.fuyun.cloudertinker.Modifiers.OnlyBowMOdifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.*;
import twilightforest.init.TFDamageSources;
import twilightforest.init.TFMobEffects;

import java.util.List;

import static com.fuyun.cloudertinker.Modifiers.ArmorModifiers.Chill_aura.enabled;

public class Frostbomb extends BattleModifier {
    private static final ResourceLocation frostcraft = Cloudertinker.getResource("frostcraft");
    public boolean havenolevel() {
        return true;
    }
    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (enabled &&target != null) {
            double d0 = (float)arrow.getDeltaMovement().length();
            int damage = Mth.ceil(Mth.clamp(d0 * arrow.getBaseDamage(),0.0D, Float.MAX_VALUE));
            ModDataNBT tooldata = ToolStack.from(attacker.getItemBySlot(EquipmentSlot.MAINHAND)).getPersistentData();
            if (tooldata.getInt(frostcraft) > 0){
                double x = target.getX();
                double y = target.getY();
                double z = target.getZ();
                int ice = ToolStack.from(attacker.getItemBySlot(EquipmentSlot.MAINHAND)).getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
                int a = (int) ((tooldata.getInt(frostcraft)+10) * 0.01);
                List<Mob> mobList = target.level.getEntitiesOfClass(Mob.class, new AABB(x + 2 * a, y + 2 * a, z + 2 * a, x - 2 * a, y - 2 * a, z - 2 * a));
                for (Mob mob : mobList) {
                    if (mob != null) {
                        mob.invulnerableTime= 0;
                        mob.hurt(new DamageSource(TFDamageSources.tfSource("frozen")),(float) ((damage * 2 *a) ));
                        mob.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(),  (20 * (int)(tooldata.getInt(frostcraft)*0.02)),  (int)(tooldata.getInt(frostcraft)*0.02 -1)));
                        mob.invulnerableTime= 0;
                    }}
                tooldata.putInt(frostcraft,tooldata.getInt(frostcraft)-3 *ice-3);
                target.playSound(SoundEvents.GENERIC_EXPLODE,1,1);
                target.setLastHurtByMob(attacker);
                if (target.level instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(ParticleTypes.EXPLOSION,target.getX(),target.getY()+0.5*target.getBbHeight(),target.getZ(),2 * a,0,0,0,0);
                }
            } else {
                tooldata.putInt(frostcraft,0);
            }
        }
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = tool.getPersistentData();
            int level = tool.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
            list.add(Component.translatable("modifier.cloudertinker.frostbomb.tooltip1"  ,(int) ((tooldata.getInt(frostcraft)+10) * 0.02)).withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("modifier.cloudertinker.frostbomb.tooltip2" ,((tooldata.getInt(frostcraft)) * 0.02)).withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("modifier.cloudertinker.frostbomb.tooltip3",(3 * level+3)).withStyle(ChatFormatting.AQUA));
        }
    }

}
