package util.method;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModifierEffect {
    public static void direaddMobEffect(LivingEntity entity, MobEffect effect, int duration, int amplifier){
        var pinstance=new MobEffectInstance(effect,duration,amplifier);
        MobEffectInstance instance=entity.getActiveEffectsMap().get(pinstance.getEffect());
        if (instance==null){
            entity.getActiveEffectsMap().put(pinstance.getEffect(),pinstance);
            entity.onEffectAdded(pinstance,null);
        } else if (instance.update(pinstance)) {
            entity.onEffectUpdated(instance,false,null);
        }
    }
    public static boolean hasbeneficialEffect(LivingEntity entity){
        Collection<MobEffectInstance> effects = entity.getActiveEffects();
        List<MobEffectInstance> effectInstances = new ArrayList<>(effects);
        for (MobEffectInstance effectlist : effectInstances) {
            if (effectlist.getEffect().isBeneficial()) {
                return true;
            }
        }
        return false;
    }
}
