package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import twilightforest.init.TFMobEffects;
import util.method.ModifierLevel;

import java.util.List;
import java.util.function.BiConsumer;

import static com.fuyun.cloudertinker.Modifiers.WarpenModifiers.Frosttought.enabled;

public class Frostsword extends BattleModifier {
    private static final ResourceLocation frostcraft = Cloudertinker.getResource("frostcraft");
    public boolean havenolevel() {
        return true;
    }

    @Override
    public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
        if (enabled &&toolAttackContext.getLivingTarget() != null) {
            int level = iToolStackView.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
            ModDataNBT tooldata = iToolStackView.getPersistentData();
            if (tooldata.getInt(frostcraft) > 0){
                LivingEntity entity = toolAttackContext.getLivingTarget();
                entity.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(),  (20 * (int)(tooldata.getInt(frostcraft)*0.02)),  (int)(tooldata.getInt(frostcraft)*0.02 -1)));
               if (level < 3){
                   return (float) (v1 + (v1 * 0.5 ));
               } else if (level <6) {
                   return (float) (v1 + (v1*1));
               }else if(level<9){
                   return (float) (v1 + (v1*2  ));
                }else {
                   return (float) (v1 + (v1 * 3 ));
               }
            }
        }
        return v1;
    }
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        ModDataNBT tooldata = tool.getPersistentData();
        int level = tool.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
        if (tooldata.getInt(frostcraft)>=level * 30 + 20&&slot==EquipmentSlot.MAINHAND){
            consumer.accept(Attributes.ATTACK_SPEED, new AttributeModifier(ModifierLevel.UUIDFromWeapon(slot,this.getId()), String.valueOf(Attributes.ATTACK_SPEED), (int) 100, AttributeModifier.Operation.ADDITION));

        }
         }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = tool.getPersistentData();
            int level = tool.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
            int damage;
            if (level < 3){
               damage=50;
            } else if (level <6) {
                damage=100;
            }else if(level<9){
                damage=200;
            }else {
                damage=300;
            }
            list.add(Component.translatable("modifier.cloudertinker.frostsword.tooltip1").append((int)(damage)+"%").withStyle(ChatFormatting.AQUA));
        }
    }
}
