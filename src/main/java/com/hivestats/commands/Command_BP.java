package com.hivestats.commands;

import com.hivestats.commandhandlers.CommandHandler;
import com.hivestats.games.BP;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class Command_BP extends CommandBase {
    private static String[] cmd_args;
    private static String commandName;

    @Override
    public String getCommandName() {
        return "bp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/bp <player> <period>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        cmd_args = args;
        commandName = getCommandName();
        Thread thread = new Thread(){
            public void run(){
                Minecraft mc = Minecraft.getMinecraft();
                String stats ="";
                if (cmd_args.length == 0)
                    stats = String.format("Error: Use /%s <username>", commandName);
                else if (cmd_args.length !=2)
                    stats = new BP(cmd_args[0], "all").stats();
                else if (cmd_args.length >= 2){
                    String period = cmd_args[1].toLowerCase();
                    if (!period.equals(new String("all")) && !period.equals(new String("daily"))
                            && !period.equals(new String("weekly")) && !period.equals(new String("monthly")))
                        stats = "Error: Use /bp <player> [all/daily/weekly/monthly]";
                    else
                        stats = new BP(cmd_args[0], period).stats();
                }
                else
                    stats = "Error: Use /bp <player> [all/daily/weekly/monthly]";
                mc.thePlayer.addChatComponentMessage(new ChatComponentText(CommandHandler.commandOutput(stats)));
            }
        };
        thread.start();
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }
}
