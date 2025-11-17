package com.fuyun.cloudertinker.entities;

import com.fuyun.cloudertinker.register.CloudertinkerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HeaveRock extends AbstractArrow {
    public HeaveRock(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public HeaveRock(Level pLevel, LivingEntity pShooter) {
        this(CloudertinkerEntity.heave_rock.get(), pLevel);
        this.setPos(pShooter.getX(), pShooter.getEyeY() - 0.1, pShooter.getZ());
        this.setYRot(pShooter.getYRot());
        this.setXRot(pShooter.getXRot());
        this.shootFromRotation(pShooter, pShooter.getXRot(), pShooter.getYRot(), 0, 3, 1);
        setOwner(pShooter);
        Vec3 move = this.getDeltaMovement();
        this.setDeltaMovement(move.scale(0.25f));
        this.pickup = Pickup.CREATIVE_ONLY;
    }
    @Override
    protected ItemStack getPickupItem() {
        return null;
    }
    @Override
    public void tick() {
        super.tick();
        Vec3 vec3 = this.getDeltaMovement();
        double d5 = vec3.x;
        double d6 = vec3.y;
        double d1 = vec3.z;
        this.setDeltaMovement(this.getDeltaMovement().scale(1.01f));

        if (this.tickCount > 400){
            this.discard();
        }
        for(int i = 0; i < 4; ++i) {
            this.level().addParticle(ParticleTypes.CRIT, this.getX() + d5 * (double)i / 4.0D, this.getY() + d6 * (double)i / 4.0D, this.getZ() + d1 * (double)i / 4.0D, -d5, -d6 + 0.2D, -d1);
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        List<Mob> mobList = null;
        if (this.level().isClientSide()) return;
        if (pResult.getType()== HitResult.Type.ENTITY){
            Entity target = pResult.getType()== HitResult.Type.ENTITY ? ((EntityHitResult)pResult).getEntity() : null;
            if (target != null) {
                double x = target.getX();
                double y = target.getY();
                double z = target.getZ();
                mobList = target.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(x + 1, y + 1, z + 1, x - 1, y - 1, z - 1));

            }

        } else if (pResult.getType()== HitResult.Type.BLOCK) {
            BlockPos pos=((BlockHitResult)pResult).getBlockPos();
            double x = pos.getX();
            double y = pos.getY();
            double z = pos.getZ();
            mobList = this.level().getEntitiesOfClass(Mob.class, new AABB(x + 1, y + 1, z + 1, x - 1, y - 1, z - 1));

        }

        float damage = (float) Mth.clamp(this.getDeltaMovement().length() * this.getBaseDamage(), 0.0D, Float.MAX_VALUE);

        if (mobList != null) {
            for (Mob mob : mobList) {
                if (mob != null) {
                    mob.invulnerableTime= 0;
                    mob.hurt(this.damageSources().arrow(this, this.getOwner()), damage /5);
                    mob.invulnerableTime= 0;
                }}
            this.discard();
        }
    }
    public BlockState getBlockState() {
        return Blocks.OBSIDIAN.defaultBlockState();
    }
}
