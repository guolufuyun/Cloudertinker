package util.compound;

import net.minecraft.ChatFormatting;

public class IceFantasy {
    private static final ChatFormatting[] color = {ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE,
            ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE,
            ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_BLUE,
            ChatFormatting.BLUE, ChatFormatting.AQUA};
    public static int bc;

    public static String formatting(String input, ChatFormatting[] colours, double delay) {
        StringBuilder sb = new StringBuilder(input.length() * 1000);
        if (delay <= 0.2D)
            delay = 0.9999D;
        int offset = (int) Math.floor((System.currentTimeMillis() & 0x3FFFL) / delay) % colours.length;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            sb.append(colours[(colours.length + i - offset) % colours.length].toString());
            sb.append(c);
            bc = i;
        }
        return sb.toString();
    }

    public static String GetColor(String input) {
        return formatting(input, color, 50.0D);
        //                  ^      ^     ^
        //               输入文本  颜色  延迟
    }
}

