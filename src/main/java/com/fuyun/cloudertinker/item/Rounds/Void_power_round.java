package com.fuyun.cloudertinker.item.Rounds;

import com.fuyun.cloudertinker.CTKConfig;
import com.fuyun.cloudertinker.item.Tigermark_rounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nullable;
import java.util.List;

public class Void_power_round extends Tigermark_rounds {
    public Void_power_round(Properties pProperties) {
        super(pProperties);
        this.damageboost= CTKConfig.COMMON.Void_Power_Damage.get().floatValue();
        this.thrust= CTKConfig.COMMON.Void_Power_Thrust.get();
        this.push_power= CTKConfig.COMMON.Void_Power_Push_Power.get();
        this.explosion_damage= CTKConfig.COMMON.Void_Power_Explosion_damage.get().floatValue();
        this.color= ChatFormatting.DARK_PURPLE;
    }
    @Override
    public void onStoppedUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int thrust, List<Mob> mobList) {
        if (entity instanceof Player player) {
            if (mobList == null || mobList.isEmpty()) {
                return;
            }
//            player.sendSystemMessage(Component.translatable("列表非空"));
            Mob mob = null;
            for (Mob mob1 : mobList) {
//                player.sendSystemMessage(Component.translatable("尝试寻找mob"));
                if (mob1 != null && mob1.isAlive()) {
                    mob = mob1;
                    break;
                }
            }
//            player.sendSystemMessage(Component.translatable("判断mob是否有效"));
            if (mob == null || !entity.isAlive()) return;
//            player.sendSystemMessage(Component.translatable("开始计算坐标"));
            float yawRadians = mob.getYRot() * ((float) Math.PI / 180.0F);
            double behindX = mob.getX() + Math.sin(yawRadians);
            double behindY = mob.getY();
            double behindZ = mob.getZ() - Math.cos(yawRadians);
//            player.sendSystemMessage(Component.translatable("计算完成"));
            entity.teleportTo(behindX, behindY, behindZ);
        }
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, list, flag);
        if (Screen.hasShiftDown()) {
            list.add(Component.translatable("cloudertinker.item.tooltip.vpround.value").withStyle(this.color));
        }
    }
}
