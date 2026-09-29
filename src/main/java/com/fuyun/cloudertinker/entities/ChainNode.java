package com.fuyun.cloudertinker.entities;

import net.minecraft.util.Mth;

/**
 * 链节锚点（纯数据，<b>不继承 PartEntity</b>、不进入 getParts()/实体同步）。
 *
 * <p>暮色原版把 5 个链节做成 {@code twilightforest.entity.Chain extends PartEntity}，
 * 只有渲染时才用到位置。本工程改为纯数据对象：
 * <ul>
 *     <li>服务端不需要知道链节；链节位置在 {@link ChainBlock#tick()} 的客户端分支里计算，</li>
 *     <li>{@code com.fuyun.cloudertinker.rander.ChainBlockRenderer} 读取本对象的 “当前 / 上一 tick” 两组坐标做插值。</li>
 * </ul>
 *
 * <p>使用约定（必须遵守，否则渲染会抖动）：每 tick <b>先</b> {@link #updateLastPos()} 把上一 tick 的
 * 坐标保存下来，<b>再</b> {@link #setPos(double, double, double)} 写入本 tick 的新坐标。
 * 这与暮色里 “先 {@code Chain.tick()}（PartEntity 的 baseTick 会把 xo/yo/zo 同步为旧值）再 setPos()” 等价。
 */
public class ChainNode {

    private double x;
    private double y;
    private double z;

    /** 上一 tick 的坐标（渲染插值用，public 字段，渲染器直接读） */
    public double xOld;
    public double yOld;
    public double zOld;

    public ChainNode() {
        this(0.0D, 0.0D, 0.0D);
    }

    public ChainNode(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xOld = x;
        this.yOld = y;
        this.zOld = z;
    }

    /** 把当前位置写进 old 位置；每 tick 在读新位置之前调用一次。 */
    public void updateLastPos() {
        this.xOld = this.x;
        this.yOld = this.y;
        this.zOld = this.z;
    }

    public ChainNode setPos(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double z() {
        return this.z;
    }

    /** 插值后的 X（partialTick ∈ [0,1]） */
    public double getX(float partialTick) {
        return Mth.lerp(partialTick, this.xOld, this.x);
    }

    /** 插值后的 Y（partialTick ∈ [0,1]） */
    public double getY(float partialTick) {
        return Mth.lerp(partialTick, this.yOld, this.y);
    }

    /** 插值后的 Z（partialTick ∈ [0,1]） */
    public double getZ(float partialTick) {
        return Mth.lerp(partialTick, this.zOld, this.z);
    }
}
