package org.reuac.commandwhitelist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class CommandWhiteList extends JavaPlugin {
    private FileConfiguration config;
    private Map<String, WorldConfig> worldConfigs = new HashMap<>();
    private String defaultConfig_name;
    private boolean defaultEnabled;
    private Map<String, GroupConfig> groupConfigs = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();

        MainCommand reloadCommand = new MainCommand(this);
        getCommand("commandwhitelist").setExecutor(reloadCommand);
        getServer().getPluginManager().registerEvents(new MainListener(this), this);

        try {
            Class.forName("org.bukkit.event.player.PlayerCommandSendEvent");
            Bukkit.getPluginManager().registerEvents(new PlayerCommandSendListener(this), this);
            getLogger().info("PlayerCommandSendListener Successfully registered");
        } catch (ClassNotFoundException e) {
            getLogger().info("The current version does not support PlayerCommandSendEvent，Not registered PlayerCommandSendListener");
        }

        getLogger().info("CommandWhiteList enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("CommandWhiteList disabled.");
    }

    public void loadConfig() {
        config = getConfig();
        worldConfigs.clear();
        ConfigurationSection worldsSection = config.getConfigurationSection("Worlds");

        if (worldsSection != null) {
            for (String worldName : worldsSection.getKeys(false)) {
                ConfigurationSection worldSection = worldsSection.getConfigurationSection(worldName);
                if (worldSection != null) {
                    List<String> whitelist = worldSection.getStringList("Command_WhiteList");
                    boolean enabledMessage = worldSection.getBoolean("enabledMessage");
                    List<String> notAllowTip = worldSection.getStringList("CommandNotAllow_Tip").stream()
                            .map(message_temp -> ChatColor.translateAlternateColorCodes('&', message_temp))
                            .collect(Collectors.toList());
                    worldConfigs.put(worldName, new WorldConfig(whitelist, enabledMessage, notAllowTip));
                }
            }
        }

        ConfigurationSection defaultGlobalSection = config.getConfigurationSection("Default");
        if (defaultGlobalSection != null) {
            defaultEnabled = defaultGlobalSection.getBoolean("enable");
            defaultConfig_name = defaultGlobalSection.getString("name");
        }

        ConfigurationSection groupsSection = config.getConfigurationSection("CommandWhiteListGroup");
        if (groupsSection != null) {
            for (String groupName : groupsSection.getKeys(false)) {
                ConfigurationSection groupSection = groupsSection.getConfigurationSection(groupName);
                if (groupSection != null) {
                    List<String> whitelist = groupSection.getStringList("whitelist");
                    boolean enabledMessage = groupSection.getBoolean("enabledMessage");
                    List<String> message = groupSection.getStringList("message").stream()
                            .map(message_temp -> ChatColor.translateAlternateColorCodes('&', message_temp))
                            .collect(Collectors.toList());
                    groupConfigs.put(groupName, new GroupConfig(whitelist, enabledMessage, message));
                }
            }
        }

        getLogger().info("Config loaded. Number of worlds configured: " + worldConfigs.size());
        getLogger().info("Config loaded. Number of groups configured: " + groupConfigs.size());
    }

    public Map<String, WorldConfig> getWorldConfigs() {
        return worldConfigs;
    }

    public String getDefaultConfig() {
        return defaultConfig_name;
    }

    public boolean isDefaultEnabled() {
        return defaultEnabled;
    }

    public GroupConfig getGroupConfig(Player player) {
        for (Map.Entry<String, GroupConfig> entry : groupConfigs.entrySet()) {
            if (player.hasPermission("commandwhitelist.group." + entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static class WorldConfig {
        private final List<String> commandWhitelist;
        private final boolean enabledMessage;
        private final List<String> commandNotAllowTip;

        public WorldConfig(List<String> commandWhitelist, boolean enabledMessage, List<String> commandNotAllowTip) {
            this.commandWhitelist = commandWhitelist;
            this.enabledMessage = enabledMessage;
            this.commandNotAllowTip = commandNotAllowTip;
        }

        public List<String> getCommandWhitelist() {
            return commandWhitelist;
        }

        public boolean isEnabledMessage() {
            return enabledMessage;
        }

        public List<String> getCommandNotAllowTip() {
            return commandNotAllowTip;
        }
    }

    public static class GroupConfig {
        private final List<String> whitelist;
        private final boolean enabledMessage;
        private final List<String> message;

        public GroupConfig(List<String> whitelist, boolean enabledMessage, List<String> message) {
            this.whitelist = whitelist;
            this.enabledMessage = enabledMessage;
            this.message = message;
        }

        public List<String> getWhitelist() {
            return whitelist;
        }

        public boolean isEnabledMessage() {
            return enabledMessage;
        }

        public List<String> getMessage() {
            return message;
        }
    }

    public boolean isCommandAllowed(String command, List<String> whitelist) {
        for (String allowedCommand : whitelist) {
            if (matches(command, allowedCommand)) {
                return true;
            }
        }
        return false;
    }

    public boolean matches(String command, String pattern) {
        String[] commandParts = command.split(" ");
        String[] patternParts = pattern.split(" ");

        int i = 0, j = 0;
        while (i < commandParts.length && j < patternParts.length) {
            if (patternParts[j].equals("*")) {
                return true;
            } else if (patternParts[j].equals("[*]")) {
                i++;
                j++;
            } else if (patternParts[j].equals(commandParts[i])) {
                i++;
                j++;
            } else {
                return false;
            }
        }

        if (j == patternParts.length) {
            return (j > 0 && patternParts[j - 1].equals("*")) || i == commandParts.length;
        }
        return false;
    }
}
