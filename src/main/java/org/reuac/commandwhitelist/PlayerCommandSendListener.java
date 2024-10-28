package org.reuac.commandwhitelist;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Collection;
import java.util.List;

import static org.reuac.commandwhitelist.MainListener.*;

public class PlayerCommandSendListener implements Listener {
    @EventHandler
    public void PlayerCommandSendEvent(PlayerCommandSendEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();
        if (player.hasPermission("commandwhitelist.bypass." + worldName)) {return;}
        WorldCommandSettings settings = worldCommandSettingsMap.get(worldName);

        // 如果世界有相关配置，则进行判断
        if (settings != null) {
            Collection<String> command = event.getCommands();
            List<String> whitelist = settings.getWhitelist();
            // 如果 cmd 不在 whitelist 中，则移除
            command.removeIf(cmd -> !whitelist.contains(cmd));
        }else if(DefaultEnable){
            WorldCommandSettings default_settings = worldCommandSettingsMap.get(DefaultConfig);

            if (default_settings != null) {
                Collection<String> command = event.getCommands();
                List<String> whitelist = default_settings.getWhitelist();
                command.removeIf(cmd -> !whitelist.contains(cmd));
            }
        }
    }
}
