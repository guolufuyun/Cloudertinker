package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.List;

public class Relieveresentment extends BattleModifier{
    public boolean havenolevel() {
        return true;
    }
    private static final ResourceLocation resentment = Cloudertinker.getResource("resentment");
    @Override
    public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        LivingEntity entity = toolAttackContext.getLivingTarget();
        ModDataNBT tooldata = iToolStackView.getPersistentData();
        if ( entity != null) {
            MobEffectInstance instance =entity.getEffect(CloudertinkerEffects.ResentmentBrand.get());
            if (instance!=null) {
                int timeleft = entity.getEffect(CloudertinkerEffects.ResentmentBrand.get()).getDuration();
                int EffectLevel = entity.getEffect(CloudertinkerEffects.ResentmentBrand.get()).getAmplifier();
                double x = entity.getX();
                double y = entity.getY();
                double z = entity.getZ();
                List<Mob> mobList = entity.level.getEntitiesOfClass(Mob.class, new AABB(x + 4, y + 4, z + 4, x - 4, y - 4, z - 4));
                for (Mob mob : mobList) {
                    if (mob != null) {
                        mob.addEffect(new MobEffectInstance(CloudertinkerEffects.ResentmentBrand.get(), timeleft, EffectLevel));
                    }
                }
                return v1 + (v1 * (float) (tooldata.getInt(resentment)* 0.01));
            }
            return v1 + (v1 * (float) (tooldata.getInt(resentment)* 0.01));
                }
        return v1;
    }


    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = tool.getPersistentData();
            list.add(Component.translatable("modifier.cloudertinker.relieveresentment.tooltip",tooldata.getInt(resentment)).withStyle(ChatFormatting.DARK_RED));
        }
    }
}
