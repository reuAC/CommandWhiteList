package org.reuac.commandwhitelist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        CommandWhiteList.main.reloadConfig();
        CommandWhiteList.main.loadConfig();

        if (commandSender instanceof Player) {
            commandSender.sendMessage("[CommandWhiteList] Reload Complete.");
        }else {
            System.out.println("[CommandWhiteList] Reload Complete.");
        }

        return false;
    }
}
