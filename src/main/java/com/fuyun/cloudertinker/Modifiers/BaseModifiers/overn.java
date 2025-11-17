package com.fuyun.cloudertinker.Modifiers.BaseModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.checkerframework.checker.nullness.qual.Nullable;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import util.method.ModifierLevel;

import java.util.Objects;

public class overn extends Modifier {
    public overn() {
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
//        这里是添加了受伤事件的事件监听器，监听 活实体受伤 事件，必须监听才可以，不需要接口和注册钩子
    }

    public void LivingHurtEvent(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        Entity attack = event.getSource().getEntity();
//        获取受击者 entity 和攻击者attack，因为攻击者attack可能不是活实体（箭矢之类的）所以不是活实体类型
        if (attack instanceof Player attacker) {
//            这个if else if是判断攻击者attack是不是活实体或投射物，是活实体的话变量名为attacker
            if (ModifierLevel.EquipHasModifierlevel(attacker, this.getId())) {
//                因为默认的监听器都是全局监听所以要加判断条件，复习一下判断持有词条，这部分我就不仔细写了，下方投射物照样可以这样
//                调整好变量名后那个meleedamage里面的方法基本就可以调用了，只是由于这个方法是void型的所以没有返回值
                event.setAmount(1);
//               这一行就是设置伤害为1，代替meleedamage的return，获取的话就是event.getAmount()，获取的()里不需要写东西
            }

        } else if (attack instanceof Projectile projectile && projectile.getOwner() instanceof LivingEntity attacker) {
//            如果是投射物的话就需要同时拥有者也是活实体才能生效，变量名为attaacker

        }


    }


}
