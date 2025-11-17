package com.fuyun.cloudertinker.tool;

import com.fuyun.cloudertinker.Cloudertinker;
import com.fuyun.cloudertinker.register.CloudertinkerItem;
import com.fuyun.cloudertinker.register.CloudertinkerOther;
import com.fuyun.cloudertinker.register.CloudertinkerTools;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class toolDefinitions {
//    这个主要是锁定同名路径下的同名数据包用
    public toolDefinitions() {
    }
    public static final FloatToolStat SLASH_COLOR = ToolStats.register(new FloatToolStat(name("slash_color"), -3135232, 0.0F, 0.0F, 11));

    private static ToolStatId name(String name) {
        return new ToolStatId(Cloudertinker.MODID, name);
    }
    public static final ToolDefinition GIANTSWORD = ToolDefinition.create(CloudertinkerTools.giantsword);
    public static final ToolDefinition HARD_SHIELD = ToolDefinition.create(CloudertinkerTools.hard_shield);

}
