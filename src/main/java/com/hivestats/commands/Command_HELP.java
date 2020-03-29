package com.hivestats.commands;

import com.hivestats.commandhandlers.CommandHandler;
import com.hivestats.commandhandlers.Commands;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import org.apache.commons.lang3.StringUtils;

public class Command_HELP extends CommandBase {

    @Override
    public String getCommandName() {
        return "hivestats";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/hivestats";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        Thread thread = new Thread(){
            public void run(){
                Minecraft mc = Minecraft.getMinecraft();
                mc.thePlayer.addChatComponentMessage(new ChatComponentText(CommandHandler.commandOutput("Commands: " + StringUtils.join(Commands.values(), ", "))));
            }
        };
        thread.start();
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }
}
