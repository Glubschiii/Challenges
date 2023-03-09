package it.glubschiii.Challenges.challenges;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.8
 */
public class NoArmorChallenge implements Listener {

    @EventHandler
    private void onArmorChange(PlayerArmorChangeEvent event) {
        if(Config.contains("challenges.noarmor") && Config.getBoolean("challenges.noarmor").booleanValue()) {
            if(Timer.isRunning()) {
                Player player = event.getPlayer();
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD.toString() + ChatColor.BOLD + player.getDisplayName() + " " +
                            ChatColor.RESET + "" + ChatColor.GRAY + "hat Rüstung angezogen.");
                }
                player.setHealth(0);
            }
        }
    }

}
