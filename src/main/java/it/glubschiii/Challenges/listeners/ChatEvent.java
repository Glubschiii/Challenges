package it.glubschiii.Challenges.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.6
 */
public class ChatEvent implements Listener {

    @EventHandler
    private void onMessageSend(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        event.setCancelled(true);
        for(Player all : Bukkit.getOnlinePlayers()) {
            all.sendMessage(ChatColor.GRAY + player.getDisplayName() + ChatColor.DARK_GRAY + " » " + ChatColor.RESET + message);
        }
    }
}
