package com.fuyun.cloudertinker.Modifiers.OnlyBowMOdifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
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

import java.util.List;

public class Readyboom extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }
    private static final ResourceLocation resentment = Cloudertinker.getResource("resentment");
    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        ModDataNBT entitydata = ModDataNBT.readFromNBT(attacker.getPersistentData());
        ModDataNBT tooldata = ToolStack.from(attacker.getItemBySlot(EquipmentSlot.MAINHAND)).getPersistentData();
        double d0 = (float)arrow.getDeltaMovement().length();
        int damage = Mth.ceil(Mth.clamp(d0 * arrow.getBaseDamage(),0.0D, Float.MAX_VALUE));
        if (target != null &&tooldata.getInt(resentment)>=100) {
            double x = target.getX();
            double y = target.getY();
            double z = target.getZ();
            List<Mob> mobList = target.level.getEntitiesOfClass(Mob.class, new AABB(x + 4, y + 4, z + 4, x - 4, y - 4, z - 4));
            for (Mob mob : mobList) {
                if (mob != null) {
                    MobEffectInstance instance =mob.getEffect((CloudertinkerEffects.ResentmentBrand.get()));
                    if (instance!=null){
                    mob.invulnerableTime= 0;
                        List<Mob> mobList1 = target.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(x + 4, y + 4, z + 4, x - 4, y - 4, z - 4));
                        for (Mob mob1 : mobList1) {
                            if (mob1 != null) {
                                mob1.invulnerableTime= 0;
                                mob1.hurt(DamageSource.explosion(attacker),(float) (damage));
                                mob1.setRemainingFireTicks((200));
                                mob1.invulnerableTime= 0;
                            }
                        }
                        mob.playSound(SoundEvents.GENERIC_EXPLODE,5,1);
                        if (mob.getCommandSenderWorld() instanceof ServerLevel serverLevel){
                            serverLevel.sendParticles(ParticleTypes.EXPLOSION,mob.getX(),mob.getY()+0.5*mob.getBbHeight(),mob.getZ(),5,0,0,0,0);
                        }
                        mob.invulnerableTime= 0;
                    mob.removeEffect(CloudertinkerEffects.ResentmentBrand.get());
                }
                    entitydata.putInt(resentment, 0);
                }
            }
            target.setLastHurtByMob(attacker);
        }
    }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = tool.getPersistentData();
            if (tooldata.getInt(resentment) >= 100) {
                list.add(Component.translatable("modifier.cloudertinker.readyboom.tooltip1").withStyle(ChatFormatting.DARK_RED));
            }else {
                list.add(Component.translatable("modifier.cloudertinker.readyboom.tooltip2").withStyle(ChatFormatting.DARK_RED));
            }
        }
    }
}
