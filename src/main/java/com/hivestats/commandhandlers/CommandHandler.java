package com.hivestats.commandhandlers;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class CommandHandler {
    public static String rawText(IChatComponent e){
        String text = e.getUnformattedText().replaceAll("§(.)", "§$1§l").trim();
        return  text.replace(text.split(" ")[0], "").trim();
    }

    public static String commandOutput(String text){
        return EnumChatFormatting.AQUA + "[HiveStats] " + EnumChatFormatting.GOLD + text;
    }

}
