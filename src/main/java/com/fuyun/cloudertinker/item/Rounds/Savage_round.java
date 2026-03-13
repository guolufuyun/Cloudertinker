package com.fuyun.cloudertinker.item.Rounds;



import com.fuyun.cloudertinker.CTKConfig;
import com.fuyun.cloudertinker.item.Tigermark_rounds;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class Savage_round extends Tigermark_rounds {
    public Savage_round(Properties pProperties) {
        super(pProperties);
    this.damageboost= CTKConfig.COMMON.Savage_round_damage.get().floatValue();
        this.thrust= 100;
        this.push_power= CTKConfig.COMMON.Savage_Push_Power.get();
        this.explosion_damage= CTKConfig.COMMON.Savage_Explosion_damage.get().floatValue();
        this.color= ChatFormatting.DARK_RED;
    }


}
