package com.fuyun.cloudertinker.register;

import com.fuyun.cloudertinker.Modifiers.ArmorModifiers.Immortalguardian;
import com.fuyun.cloudertinker.Modifiers.WarpenModifiers.Fashionbody;
import com.fuyun.cloudertinker.Modifiers.WarpenModifiers.Twilightprotece;
import com.fuyun.cloudertinker.Modifiers.anvil.Seekwind;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.fuyun.cloudertinker.Cloudertinker.MODID;

public class CloudertinkerSummonModifiers {
//也是个词条注册表，不过在主类里面判定了，只有加了暮色森林才会注册这些
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MODID);

    public static final StaticModifier<Immortalguardian> immortalguardian=MODIFIERS.register("immortalguardian", Immortalguardian::new);
    public static final StaticModifier<Twilightprotece> twilightprotece=MODIFIERS.register("twilightprotece", Twilightprotece::new);
    public static final StaticModifier<Fashionbody> fashionbody=MODIFIERS.register("fashionbody", Fashionbody::new);
    public static final StaticModifier<Seekwind> seekwind=MODIFIERS.register("seekwind", Seekwind::new);
}
