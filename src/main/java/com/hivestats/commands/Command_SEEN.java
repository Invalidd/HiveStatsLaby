package com.hivestats.commands;

import com.hivestats.APIs.HiveAPI;
import com.hivestats.commandhandlers.CommandHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class Command_SEEN extends CommandBase {
    private static String[] cmd_args;
    private static String commandName;

    @Override
    public String getCommandName() {
        return "seen";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/seen <player>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        cmd_args = args;
        commandName = getCommandName();
        Thread thread = new Thread(){
            public void run(){
                Minecraft mc = Minecraft.getMinecraft();
                String seen = "";
                if (cmd_args.length == 0)
                    seen = String.format("Error: Use /%s <username>", commandName);
                else
                    seen = new HiveAPI(cmd_args[0]).seen();
                mc.thePlayer.addChatComponentMessage(new ChatComponentText(CommandHandler.commandOutput(seen)));
            }
        };
        thread.start();
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }
}
