package com.fuyun.cloudertinker.Modifiers.BaseModifiers;

import com.fuyun.cloudertinker.register.CloudertinkerModifiers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.capacity.OverslimeModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.slotless.OverslimeModifier;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.function.BiConsumer;

import static com.fuyun.cloudertinker.Modifiers.BaseModifiers.Frostcraft.frostcraft;

public class Overfreeze extends Modifier implements InventoryTickModifierHook, AttributesModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK,ModifierHooks.ATTRIBUTES);
    }

    @Override
    public void onInventoryTick(IToolStackView iToolStackView, ModifierEntry modifierEntry, Level level, LivingEntity livingEntity, int i, boolean b, boolean b1, ItemStack itemStack) {
        if (livingEntity instanceof ServerPlayer player) {
            OverslimeModule overslimeModule = OverslimeModule.INSTANCE; // 使用模块实例
            int current = overslimeModule.getAmount(iToolStackView); // 使用新API
            ModDataNBT tooldata = iToolStackView.getPersistentData();
            int ice = iToolStackView.getModifierLevel(CloudertinkerModifiers.frostcraft.getId());
            int num=1;
            if (ice > 0 && current < overslimeModule.getCapacity(iToolStackView, modifierEntry)&&(tooldata.getInt(frostcraft)/12)>0) {
                if(20/(tooldata.getInt(frostcraft)/12)==0)num=20;
                else if(tooldata.getInt(frostcraft)/12>0)num=tooldata.getInt(frostcraft)/12;
                if (player.tickCount%(20/num)==0){
                   overslimeModule.addAmount(iToolStackView, 1);
                }
            }
        }

    }
    public UUID UUIDFromWeapon(EquipmentSlot solt, ModifierId modifierId){
        return UUID.nameUUIDFromBytes((solt.getName()+modifierId.toString()).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {


        if (tool.hasTag(TinkerTags.Items.ARMOR)){
            OverslimeModule overslimeModule = OverslimeModule.INSTANCE; // 使用模块实例
            int current = overslimeModule.getAmount(tool); // 使用新API
            int armor=current/(100/modifier.getLevel());
            if (armor>=20)armor=20;
            consumer.accept(Attributes.ARMOR, new AttributeModifier(UUIDFromWeapon(slot,this.getId()), String.valueOf(Attributes.ARMOR),armor , AttributeModifier.Operation.ADDITION));
            consumer.accept(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUIDFromWeapon(slot,this.getId()), String.valueOf(Attributes.ARMOR_TOUGHNESS), (double) armor /2 , AttributeModifier.Operation.ADDITION));
        }
    }
}