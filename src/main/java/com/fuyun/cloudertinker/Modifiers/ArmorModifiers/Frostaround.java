package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fml.ModList;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import twilightforest.init.TFMobEffects;

import java.util.List;
import java.util.Random;

public class Frostaround extends ArmorModifier {
    private static final ResourceLocation frostcraft = Cloudertinker.getResource("frostcraft");
    public boolean havenolevel() {
        return true;
    }
    public static boolean enabled = ModList.get().isLoaded("twilightforest");
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (enabled &&entity instanceof ServerPlayer player ) {
            ModDataNBT tooldata = tool.getPersistentData();
            if (player.tickCount %20 ==0 &&tooldata.getInt(frostcraft)>0  && isCorrectSlot){
                double x = player.getX();
                double y = player.getY();
                double z = player.getZ();
                float a = (float) ((tooldata.getInt(frostcraft) + 10) * 0.01);
                List<Mob> mobList = player.level.getEntitiesOfClass(Mob.class, new AABB(x + 3 * a, y + 3 * a, z + 3* a, x - 3 * a, y - 3 * a, z - 3 * a));
                for (Mob mob : mobList) {
                    if (mob != null) {
                        Random random = new Random();
                        int randomNum = random.nextInt(10) + 1;
                        if (randomNum <= 2) {
                            mob.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(), 10, 4, true, true));
                        }
                    }
                }

            }
            }
        }
    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if (enabled && enemy  != null) {
            ModDataNBT tooldata = armor.getPersistentData();
            if (tooldata.getInt(frostcraft)>0) {
                double x = entity.getX();
                double y = entity.getY();
                double z = entity.getZ();
                float a = (float) ((tooldata.getInt(frostcraft) + 10) * 0.01);
                List<Mob> mobList = entity.level.getEntitiesOfClass(Mob.class, new AABB(x + 3 * a, y + 3 * a, z + 3 * a, x - 3 * a, y - 3 * a, z - 3 * a));
                for (Mob mob : mobList) {
                    if (mob != null) {
                        mob.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(), 50, 5, true, true));
                    }
                }
                tooldata.putInt(frostcraft,tooldata.getInt(frostcraft)-9*level);
                return amount * (1-((tooldata.getInt(frostcraft) + 10) * 0.002f));
            } else { tooldata.putInt(frostcraft,0);}
        }
        return amount;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = tool.getPersistentData();
            int level = tool.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
            list.add(Component.translatable("modifier.cloudertinker.frostaround.tooltip1",(float) ((tooldata.getInt(frostcraft)+10) * 0.03)).withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("modifier.cloudertinker.frostaround.tooltip1",((tooldata.getInt(frostcraft)) * 0.2)).withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("modifier.cloudertinker.frostaround.tooltip1" ,(9*level) ).withStyle(ChatFormatting.AQUA));
        }
    }

}




