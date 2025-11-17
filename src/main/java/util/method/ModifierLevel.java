package util.method;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class ModifierLevel {
    public static int getMainhandModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {

                ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.MAINHAND);
                if (!(itemStack.getItem() instanceof IModifiable))return 0;
                ToolStack toolStack = ToolStack.from(itemStack);
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.MAINHAND), modifierId);
                }
            }
        }
        return 0;
    }
    public static UUID UUIDFromWeapon(EquipmentSlot solt, ModifierId modifierId){
        return UUID.nameUUIDFromBytes((solt.getName()+modifierId.toString()).getBytes(StandardCharsets.UTF_8));
    }

    public static int getOffhandModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.OFFHAND);
                if (!(itemStack.getItem() instanceof IModifiable))return 0;
                ToolStack toolStack = ToolStack.from(itemStack);
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.OFFHAND), modifierId);
                }
            }
        }
        return 0;
    }

    public static int getEachHandsTotalModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.MAINHAND);
                if (!(itemStack.getItem() instanceof IModifiable))return 0;
                ToolStack toolStack = ToolStack.from(itemStack);
                ItemStack itemStack1 = entity.getItemBySlot(EquipmentSlot.OFFHAND);
                if (!(itemStack1.getItem() instanceof IModifiable))return 0;
                ToolStack toolStack1 = ToolStack.from(itemStack1);
                if (!(toolStack.isBroken()||toolStack1.isBroken())) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.MAINHAND), modifierId) + ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.OFFHAND), modifierId);
                }else if (!toolStack.isBroken()){
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.MAINHAND), modifierId);
                }else {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.OFFHAND), modifierId);
                }
            }
        }
        return 0;
    }

    public static boolean EachHandsHaveModifierlevel(LivingEntity entity, ModifierId modifierId) {
        return ModifierLevel.getMainhandModifierlevel(entity, modifierId) > 0 && ModifierLevel.getOffhandModifierlevel(entity, modifierId) > 0;
    }

    public static boolean HandsHaveModifierlevel(LivingEntity entity, ModifierId modifierId) {
        return ModifierLevel.getEachHandsTotalModifierlevel(entity, modifierId) > 0;
    }

    public static int getHeadModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {

                ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.HEAD);
                if (!(itemStack.getItem() instanceof IModifiable))return 0;
                ToolStack toolStack = ToolStack.from(itemStack);
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.HEAD), modifierId);
                }
            }
        }
        return 0;
    }

    public static int getChestModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.CHEST);
                if (!(itemStack.getItem() instanceof IModifiable))return 0;
                ToolStack toolStack = ToolStack.from(itemStack);
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.CHEST), modifierId);
                }
            }
        }
        return 0;
    }

    public static int getLegsModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.LEGS);
                if (!(itemStack.getItem() instanceof IModifiable))return 0;
                ToolStack toolStack = ToolStack.from(itemStack);
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.LEGS), modifierId);
                }
            }
        }
        return 0;
    }

    public static int getFeetsModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.FEET);
                if (!(itemStack.getItem() instanceof IModifiable))return 0;
                ToolStack toolStack = ToolStack.from(itemStack);
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.FEET), modifierId);
                }
            }
        }
        return 0;
    }

    public static int getTotalArmorModifierlevel(LivingEntity entity, ModifierId modifierId) {
        return ModifierLevel.getHeadModifierlevel(entity, modifierId) + ModifierLevel.getChestModifierlevel(entity, modifierId) + ModifierLevel.getLegsModifierlevel(entity, modifierId) + ModifierLevel.getFeetsModifierlevel(entity, modifierId);
    }

    public static boolean EachaArmorHasModifierlevel(LivingEntity entity, ModifierId modifierId) {
        return ModifierLevel.getHeadModifierlevel(entity, modifierId) > 0 && ModifierLevel.getChestModifierlevel(entity, modifierId) > 0 && ModifierLevel.getLegsModifierlevel(entity, modifierId) > 0 && ModifierLevel.getFeetsModifierlevel(entity, modifierId) > 0;
    }

    public static boolean EquipHasModifierlevel(LivingEntity entity, ModifierId modifierId) {
        return ModifierLevel.getTotalArmorModifierlevel(entity, modifierId) > 0 || ModifierLevel.HandsHaveModifierlevel(entity, modifierId);
    }
}
