package org.reuac.commandwhitelist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor{
    private final CommandWhiteList plugin;

    public MainCommand(CommandWhiteList plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfig();
        plugin.loadConfig();
        if (sender instanceof Player) {
            sender.sendMessage("CommandWhitelist configuration reloaded.");
        }
        return true;
    }
}
