package org.reuac.commandwhitelist;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandWhiteList extends JavaPlugin {

    static CommandWhiteList main;

    @Override
    public void onEnable() {
        Bukkit.getPluginCommand("commandwhitelist").setExecutor(new MainCommand());
        Bukkit.getPluginManager().registerEvents(new MainListener(),this);

        saveDefaultConfig();
        loadConfig();

        main = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void loadConfig() {
        MainListener.messages = getConfig().getStringList("CommandNotAllow_Tip");
        MainListener.commandWhitelist = getConfig().getStringList("Command_WhiteList");
        MainListener.loadMessages();
    }
}
