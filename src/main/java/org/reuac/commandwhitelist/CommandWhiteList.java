package org.reuac.commandwhitelist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.stream.Collectors;

public final class CommandWhiteList extends JavaPlugin {

    static CommandWhiteList main;

    @Override
    public void onEnable() {
        Bukkit.getPluginCommand("commandwhitelist").setExecutor(new MainCommand());
        Bukkit.getPluginManager().registerEvents(new MainListener(),this);

        try {
            Class.forName("org.bukkit.event.player.PlayerCommandSendEvent");
            Bukkit.getPluginManager().registerEvents(new PlayerCommandSendListener(), this);
            getLogger().info("PlayerCommandSendListener 已成功注册");
        } catch (ClassNotFoundException e) {
            getLogger().info("当前版本不支持 PlayerCommandSendEvent，未注册 PlayerCommandSendListener");
        }


        saveDefaultConfig();
        loadConfig();

        main = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadWorldCommandSettings() {
        FileConfiguration config = getConfig();

        // 遍历配置文件中的所有世界并缓存指令设置
        for (String worldName : config.getConfigurationSection("Worlds").getKeys(false)) {
            List<String> whitelist = config.getStringList("Worlds." + worldName + ".Command_WhiteList");
            boolean enabledMessage = config.getBoolean("Worlds." + worldName + ".enabledMessage");
            List<String> notAllowTip = config.getStringList("Worlds." + worldName + ".CommandNotAllow_Tip").stream()
                    .map(message -> ChatColor.translateAlternateColorCodes('&', message))
                    .collect(Collectors.toList());

            // 将世界的指令白名单和提示信息缓存
            MainListener.worldCommandSettingsMap.put(worldName, new WorldCommandSettings(whitelist, enabledMessage, notAllowTip));
        }
    }



    public void loadConfig() {
        MainListener.DefaultConfig = getConfig().getString("Default.name");
        MainListener.DefaultEnable = getConfig().getBoolean("Default.enable");
        loadWorldCommandSettings();
    }
}
