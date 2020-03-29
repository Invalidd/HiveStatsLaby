package com.hivestats.commands;

import com.hivestats.APIs.HiveAPI;
import com.hivestats.commandhandlers.CommandHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class Command_HIVE extends CommandBase {
    private static String[] cmd_args;
    private static String commandName;

    @Override
    public String getCommandName() {
        return "hive";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/hive <player>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        cmd_args = args;
        commandName = getCommandName();
        Thread thread = new Thread(){
            public void run(){
                Minecraft mc = Minecraft.getMinecraft();
                String hive = "";
                if (cmd_args.length == 0)
                    hive = String.format("Error: Use /%s <username>", commandName);
                else
                    hive = new HiveAPI(cmd_args[0]).hive();
                mc.thePlayer.addChatComponentMessage(new ChatComponentText(CommandHandler.commandOutput(hive)));
            }
        };
        thread.start();
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }
}
