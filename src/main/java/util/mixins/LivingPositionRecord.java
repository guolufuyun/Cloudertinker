package util.mixins;

import net.minecraft.world.phys.Vec3;

public interface LivingPositionRecord {
    boolean moveinLastTick(double offset);
    Vec3 getLastPosition();
}
