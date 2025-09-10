package com.fuyun.cloudertinker.Modifiers.WarpenModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import twilightforest.init.TFMobEffects;

import java.util.List;

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
                tooldata.putInt(frostcraft,tooldata.getInt(frostcraft)-3 *level-3);
                LivingEntity entity = toolAttackContext.getLivingTarget();
                entity.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(),  (20 * (int)(tooldata.getInt(frostcraft)*0.02)),  (int)(tooldata.getInt(frostcraft)*0.02 -1)));
                return (float) (v1 + (v1 * ( (tooldata.getInt(frostcraft)+10) * 0.015) ));
            } else {
                tooldata.putInt(frostcraft,0);
            }
        }
        return v1;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = tool.getPersistentData();
            int level = tool.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
            int sword = 0;
             if (tool.getModifierLevel(CloudertinkerModifiers.frostsword.getId())>0) {
                 list.add(Component.translatable("modifier.cloudertinker.frostsword.tooltip1", tooldata.getInt(frostcraft) * 1.5).withStyle(ChatFormatting.AQUA));
                 sword = 1;
            }
            list.add(Component.translatable("modifier.cloudertinker.frostsword.tooltip1",(3 * level+3) * sword ).withStyle(ChatFormatting.AQUA));
        }
    }
}
