package it.glubschiii.Challenges.challenges;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
        if (Config.contains("challenges.nosneak") && Config.getBoolean("challenges.nosneak").booleanValue()) {
            if(Timer.isRunning()) {
                Player player = event.getPlayer();
                if (player.getGameMode() != GameMode.CREATIVE || player.getGameMode() != GameMode.SPECTATOR) {
                    if (player.isSneaking()) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD.toString() + ChatColor.BOLD + player.getDisplayName() + " " +
                                    ChatColor.RESET + "" + ChatColor.GRAY + "hat gesneakt.");
                        }
                        player.setHealth(0);
                    }
                }
            }
        }
    }
}
