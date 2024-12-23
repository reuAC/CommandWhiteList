package org.reuac.commandwhitelist;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import static org.bukkit.Bukkit.getLogger;

public class MainListener implements Listener {
    private final CommandWhiteList plugin;

    public MainListener(CommandWhiteList plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("commandwhitelist.bypass")){return;}
        String command = event.getMessage().substring(1);
        World world = player.getWorld();

        CommandWhiteList.GroupConfig groupConfig = plugin.getGroupConfig(player);
        CommandWhiteList.WorldConfig configToUse;

        if (groupConfig != null) {
            configToUse = new CommandWhiteList.WorldConfig(groupConfig.getWhitelist(), groupConfig.isEnabledMessage(), groupConfig.getMessage());
        }else {
            configToUse = plugin.getWorldConfigs().get(world.getName());
            if (configToUse == null && plugin.isDefaultEnabled()) {
                configToUse = plugin.getWorldConfigs().get(plugin.getDefaultConfig());
            }
        }

        if (configToUse != null) {
            if (!plugin.isCommandAllowed(command, configToUse.getCommandWhitelist())) {
                event.setCancelled(true);
                if (configToUse.isEnabledMessage()) {
                    for (String line : configToUse.getCommandNotAllowTip()) {
                        player.sendMessage(line);
                    }
                }
            }
        }
    }

}
