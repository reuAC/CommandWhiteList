package org.reuac.commandwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class MainListener implements Listener {
    static public List<String> commandWhitelist;
    static public List<String> messages;
    private static List<String> translatedMessages = new ArrayList<>();
    static public boolean enabledMessage = true;

    static public void loadMessages(){
        for (String message : messages) {
            translatedMessages.add(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("commandwhitelist.bypass")) {return;}

        String command = event.getMessage();
        String[] parts = command.split(" ");
        String baseCommand = parts[0].substring(1);

        if (!commandWhitelist.contains(baseCommand)) {
            event.setCancelled(true);

            if (enabledMessage) {
                for (String translatedMessage : translatedMessages) {
                    player.sendMessage(translatedMessage);
                }
            }
        }
    }
}
