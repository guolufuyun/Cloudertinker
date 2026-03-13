package com.fuyun.cloudertinker;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;


@Mod.EventBusSubscriber(modid = Cloudertinker.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTKConfig {

    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }
    public static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;

    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }
    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CTKConfig.commonSpec);
    }
    public static class Common{

        public final ForgeConfigSpec.IntValue Cuttinggirl_damage;
        public final ForgeConfigSpec.IntValue Sacrificeblood_MAX;
        public final ForgeConfigSpec.DoubleValue Minotaurcharge_damage;
        public final ForgeConfigSpec.DoubleValue Breakphantom_damage;
        public final ForgeConfigSpec.DoubleValue Overlost_MAX;
        public final ForgeConfigSpec.IntValue Fragile_damage;
        public final ForgeConfigSpec.IntValue Harmonize_MAX;
//
public final ForgeConfigSpec.DoubleValue Push_Power;
        public final ForgeConfigSpec.DoubleValue Savage_Push_Power;
        public final ForgeConfigSpec.DoubleValue Charge_range;
        public final ForgeConfigSpec.DoubleValue Expanded_Charge_range;
        public final ForgeConfigSpec.DoubleValue Savage_round_damage;
        public final ForgeConfigSpec.DoubleValue Explosion_damage;
        public final ForgeConfigSpec.DoubleValue Savage_Explosion_damage;
        public Common(ForgeConfigSpec.Builder builder){
            builder.comment("Modifiers behaviour").comment("词条行为").push("modifiers_behaviour");
            this.Cuttinggirl_damage = builder.comment("Cuttinggirl damage, 3*level by default.").comment("断鸡少女伤害，默认每级3。")
                    .defineInRange("Cuttinggirl_damage",3,1, Integer.MAX_VALUE);
            this.Sacrificeblood_MAX = builder.comment("Sacrificeblood damage boost max level,10 by default.").comment("祭血的力量等级上限，默认10。")
                    .defineInRange("Sacrificeblood_MAX",10,1, Integer.MAX_VALUE);
            this.Minotaurcharge_damage = builder.comment("Minotaurcharge damage, 170% by default.").comment("哞菇冲撞倍率,默认170%。")
                    //                    需要改语言
                    .defineInRange("Minotaurcharge_damage",1.7,1.0, Double.MAX_VALUE);
            this.Breakphantom_damage = builder.comment("Breakphantom damage, true by default.").comment("破影倍率。")
                    //                    需要改语言
                    .defineInRange("Breakphantom_damage",2.5,1.0, Double.MAX_VALUE);
            this.Overlost_MAX = builder.comment("Overlost damage max, 200% by default.").comment("迷失伤害上限。")
                    .defineInRange("Overlost_MAX",2.0,1, Double.MAX_VALUE);
            this.Fragile_damage = builder.comment("Fragile tool damage, 100 by default.").comment("易碎消耗耐久，默认100。")
//                    需要改语言
                    .defineInRange("Fragile_damage",100,1, Integer.MAX_VALUE);
            this.Harmonize_MAX = builder.comment("Harmonize max damage, 64 by default.").comment("协调伤害上限。")
                    .defineInRange("Harmonize_MAX",64,1, Integer.MAX_VALUE);
            builder.pop();

            builder.comment("Tiantuistar blade").comment("天退星刀").push("tiantuistar_blade");
            this.Charge_range = builder.comment("Charge attack range, 0.5 by default.").comment("冲刺攻击范围，默认0.5。")
                    .defineInRange("Charge attack range",0.5,0.0, Double.MAX_VALUE);
            this.Expanded_Charge_range = builder.comment("Expanded charge attack range, 0.5*level by default.").comment("延展冲刺攻击范围，默认每级0.5。")
                    .defineInRange("Expanded charge attack range",0.5,0.0, Double.MAX_VALUE);
            this.Savage_round_damage = builder.comment("Savage tigermark round damage, 150% by default.").comment("猛虎标弹伤害，默认150%。")
                    .defineInRange("Savage tigermark round damage",1.5,0.0, Double.MAX_VALUE);

            builder.pop();


            builder.comment("Tigermark rounds").comment("虎标弹").push("tigermark_round");
            this.Push_Power = builder.comment("Tigermark round push power, 0.2 by default.").comment("虎标弹推进力度，默认0.2。")
                    .defineInRange("Tigermark round push power",0.2,0.0, Double.MAX_VALUE);
            this.Explosion_damage = builder.comment("Tigermark round Explosion damage, 25% by default.").comment("虎标弹爆炸伤害，默认25%")
                    .defineInRange("Tigermark round Explosion damage",0.25,0.0, Double.MAX_VALUE);

            this.Savage_Push_Power = builder.comment("Savage tigermark round push power, 0.5 by default.").comment("猛虎标弹推进力度，默认0.5。")
                    .defineInRange("Savage tigermark round push power",0.5,0.0, Double.MAX_VALUE);
            this.Savage_Explosion_damage = builder.comment("Savage tigermark round Explosion damage, 50% by default.").comment("猛虎标弹爆炸伤害，默认50%")
                    .defineInRange("Savage tigermark round Explosion damage",0.5,0.0, Double.MAX_VALUE);
            builder.pop();
        }
    }
    public static class Client{
        public Client(ForgeConfigSpec.Builder builder){

        }
    }
}




