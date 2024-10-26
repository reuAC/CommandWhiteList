package org.reuac.commandwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainListener implements Listener {
    public static Map<String, WorldCommandSettings> worldCommandSettingsMap = new HashMap<>();
    public static String DefaultConfig;
    public static boolean DefaultEnable;

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();
        if (player.hasPermission("commandwhitelist.bypass." + worldName)) {return;}
        WorldCommandSettings settings = worldCommandSettingsMap.get(worldName);

        // 如果世界有相关配置，则进行判断
        if (settings != null) {
            String command = event.getMessage().substring(1).split(" ")[0];

            if (!settings.getWhitelist().contains(command)) {
                event.setCancelled(true);

                if (settings.isEnabledMessage()) {
                    for (String tip : settings.getNotAllowTip()) {
                        player.sendMessage(tip);
                    }
                }
            }
        }else if(DefaultEnable){
            WorldCommandSettings default_settings = worldCommandSettingsMap.get(DefaultConfig);

            if (default_settings != null) {
                String command = event.getMessage().substring(1).split(" ")[0];

                if (!default_settings.getWhitelist().contains(command)) {
                    event.setCancelled(true);

                    if (default_settings.isEnabledMessage()) {
                        for (String tip : default_settings.getNotAllowTip()) {
                            player.sendMessage(tip);
                        }
                    }
                }
            }

        }
    }
}
