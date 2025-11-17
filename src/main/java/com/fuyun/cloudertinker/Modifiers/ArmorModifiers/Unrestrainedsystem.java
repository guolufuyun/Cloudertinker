package com.fuyun.cloudertinker.Modifiers.ArmorModifiers;

import com.fuyun.cloudertinker.extend.superclass.ArmorModifier;
import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;
import util.method.ModifierLevel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Unrestrainedsystem extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    public void MobEffectEvent(MobEffectEvent.Applicable event) {
        if (event.getEntity() != null && event.getEntity() instanceof LivingEntity) {
            if (ModifierLevel.EquipHasModifierlevel(event.getEntity(), CloudertinkerModifiers.unrestrainedsystem.getId())) {
                if (event.getEffectInstance().isCurativeItem(new ItemStack(Items.MILK_BUCKET))) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (isSelected || isCorrectSlot) {
            Collection<MobEffectInstance> effects = entity.getActiveEffects();
            List<MobEffectInstance> effectInstances = new ArrayList<>(effects);
            for (MobEffectInstance effectlist : effectInstances) {
                if (effectlist.isCurativeItem(new ItemStack(Items.MILK_BUCKET))&&!effectlist.getEffect().isBeneficial()) {
                    entity.removeEffect(effectlist.getEffect());
                }
            }
        }
    }
}
