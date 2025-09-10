package util.method;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;


public class ModifierLevel {
    public static int getMainhandModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.MAINHAND));
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.MAINHAND), modifierId);
                }
            }
        }
        return 0;
    }

    public static int getOffhandModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.OFFHAND));
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
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.MAINHAND));
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.MAINHAND), modifierId) + ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.OFFHAND), modifierId);
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
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.HEAD));
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
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.CHEST));
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
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.LEGS));
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
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.FEET));
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
