package com.fuyun.cloudertinker.extend.event;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event.HasResult;

@HasResult
public class LivingFluidCollisionEvent extends LivingEvent {
    private final FluidState fluidState;

    public LivingFluidCollisionEvent(LivingEntity entity, FluidState fluidState) {
        super(entity);
        this.fluidState = fluidState;
    }

    public FluidState getFluidState() {
        return this.fluidState;
    }
}