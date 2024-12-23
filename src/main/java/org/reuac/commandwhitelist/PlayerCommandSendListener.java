package org.reuac.commandwhitelist;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Iterator;

public class PlayerCommandSendListener implements Listener {
    private final CommandWhiteList plugin;
    public PlayerCommandSendListener(CommandWhiteList plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommandSend(PlayerCommandSendEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("commandwhitelist.bypass")){return;}
        World world = player.getWorld();

        CommandWhiteList.GroupConfig groupConfig = plugin.getGroupConfig(player);
        CommandWhiteList.WorldConfig configToUse;

        if (groupConfig != null) {
            configToUse = new CommandWhiteList.WorldConfig(groupConfig.getWhitelist(), groupConfig.isEnabledMessage(), groupConfig.getMessage());
        } else {
            configToUse = plugin.getWorldConfigs().get(world.getName());
            if (configToUse == null && plugin.isDefaultEnabled()) {
                configToUse = plugin.getWorldConfigs().get(plugin.getDefaultConfig());
            }
        }

        if (configToUse != null) {
            Iterator<String> it = event.getCommands().iterator();
            while (it.hasNext()) {
                String command = it.next();
                if (!plugin.isCommandAllowed(command, configToUse.getCommandWhitelist())) {
                    it.remove();
                }
            }
        }
    }
}
