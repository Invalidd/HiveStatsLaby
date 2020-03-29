package com.hivestats.main;

import com.hivestats.commands.Command_BP;
import com.hivestats.commands.Command_HELP;
import com.hivestats.commands.Command_HIVE;
import com.hivestats.commands.Command_SEEN;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;


import java.util.List;

public class HiveStats extends LabyModAddon {
    @Override
    public void onEnable() {
        System.out.println("HiveStats addon enabled!");
        ClientCommandHandler.instance.registerCommand(new Command_BP());
        ClientCommandHandler.instance.registerCommand(new Command_HIVE());
        ClientCommandHandler.instance.registerCommand(new Command_SEEN());
        ClientCommandHandler.instance.registerCommand(new Command_HELP());

    }

    @Override
    public void loadConfig() {
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
    }
}


