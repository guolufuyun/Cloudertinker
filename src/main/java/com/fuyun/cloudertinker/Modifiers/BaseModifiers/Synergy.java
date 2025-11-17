package com.fuyun.cloudertinker.Modifiers.BaseModifiers;

import com.fuyun.cloudertinker.extend.superclass.BattleModifier;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.util.TFItemStackUtils;
import twilightforest.util.TwilightItemTier;

public class Synergy extends BattleModifier {
//    同调
public boolean havenolevel() {
    return true;
}
    private static final float REPAIR_DAMPENER = 1f / 256f;
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide() && entity instanceof ServerPlayer player && !(entity instanceof FakePlayer)) {
            if (!isCorrectSlot) return;
            if (!needsRepair(tool)) return;
            if (player.isUsingItem()||player.isBlocking()||player.gameMode.isDestroyingBlock)return;
            int healPower = 0;
//          定义一个整数
            NonNullList<ItemStack> playerInv = player.getInventory().items;
//          这里是获取player背包物品列表，你写的话就是：右边那个
            for (int i = 0; i < 9; i++) {
//          略过，看私信给你发那个就行
                    ItemStack invSlot = playerInv.get(i);
//                   =左边就是你该写在：左边的内容，invslot是自定义变量名，不多赘述但是这种的最好小写开头
                    if (invSlot.is(TFItems.STEELEAF_INGOT.get())) {
                        healPower += invSlot.getCount();
                    } else if (invSlot.is(TFBlocks.STEELEAF_BLOCK.get().asItem())) {
                        healPower += invSlot.getCount() * 9;
                    } else if (TFItemStackUtils.hasToolMaterial(invSlot, TwilightItemTier.STEELEAF)) {
                        healPower += 1;
                    }
//                    这仨判断其实效果都差不多，第一个是那个整数+钢叶数量，if里面是钢叶；第二个是钢叶块，所以*9，第三个是暮色森林的钢叶工具，不太重要忽略

            }
            ToolDamageUtil.repair(tool, averageInt(healPower * REPAIR_DAMPENER));
//            修复工具那个healpower*REPAIR_DAMPENER点耐久，后面那个其实就是除以256也是之前定义的数字变量
        }
    }

    private static boolean needsRepair(IToolStackView tool) {
        return tool.getDamage() > 0 && !tool.isBroken();
    }

    private static int averageInt(float value) {
        double floor = Math.floor(value);
        double rem = value - floor;
        return (int) floor + (Math.random() < rem ? 1 : 0);
    }
}
