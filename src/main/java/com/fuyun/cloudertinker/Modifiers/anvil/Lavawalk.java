package com.fuyun.cloudertinker.Modifiers.anvil;

import com.fuyun.cloudertinker.extend.event.LivingFluidCollisionEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;

public class Lavawalk extends Modifier {
    @SubscribeEvent
    public static void onFluidCollisionLava(LivingFluidCollisionEvent event) {
        // 1. 检查事件实体是否为玩家
        if (event.getEntity() instanceof Player) {
            LivingEntity stack0 = event.getEntity(); // 获取实体
            if (stack0 instanceof Player) { // 再次确认是玩家（冗余检查，但无害）
                Player player = (Player)stack0; // 将实体转换为玩家对象

                // 2. 检查玩家状态和流体类型
                // wearer.fallDistance < 20.0F: 玩家下落距离小于6格，防止从高处直接掉入熔岩时生效
                // !wearer.isUsingItem(): 玩家没有在使用物品
                // !wearer.isCrouching(): 玩家没有在潜行
                // event.getFluidState().is(FluidTags.LAVA): 当前碰撞的流体是熔岩
                if (!player.isUsingItem() && !player.isCrouching() && event.getFluidState().is(FluidTags.LAVA)) {

                    // 3. 检查玩家是否佩戴了“黑暗莲花”（Dark Nelumbo）饰品
                    // CuriosApi.getCuriosHelper().findFirstCurio(wearer, (Item)ModItems.DARK_NELUMBO.get())
                    // 这行代码通过Curios API查找玩家身上是否佩戴了ModItems.DARK_NELUMBO（也就是当前的DarkNelumbo物品）
                    //↓原版方法
                    //Optional<SlotResult> stack0 = CuriosApi.getCuriosHelper().findFirstCurio(player, (Item)ModItems.DARK_NELUMBO.get());
                    //ack0.isPresent()
                    // 4. 如果佩戴了，则允许玩家在熔岩上行走（阻止碰撞效果，即不会受到熔岩伤害或下沉）
                    if (
                           player.hasEffect(MobEffects.FIRE_RESISTANCE)
                    ) { // 如果饰品满足
                        event.setResult(Event.Result.ALLOW); // 将事件结果设置为允许，这意味着阻止了玩家与熔岩的正常碰撞行为（例如：下沉和受到伤害）
                    }
                }
            }
        }
}
}
