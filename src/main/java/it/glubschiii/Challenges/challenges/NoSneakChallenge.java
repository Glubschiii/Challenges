package it.glubschiii.Challenges.challenges;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.3
 */
public class NoSneakChallenge implements Listener {

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if(player.isSneaking()) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD.toString() + ChatColor.BOLD + player.getDisplayName() + " " +
                        ChatColor.RESET + "" + ChatColor.GRAY + "hat gesneakt.");
            }
            player.setHealth(0);
        }
    }
}
